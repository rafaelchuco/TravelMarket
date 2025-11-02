from rest_framework import viewsets, filters, status
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.decorators import action
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import IsOwnerOrAdmin
from .models import Review
from .serializers import ReviewSerializer
from applications.bookings.models import Booking


class ReviewViewSet(viewsets.ModelViewSet):
    """ViewSet para reseñas (como comentarios)"""
    queryset = Review.objects.select_related('customer', 'package')
    serializer_class = ReviewSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['package', 'overall_rating', 'is_approved']
    search_fields = ['title', 'comment']
    ordering_fields = ['overall_rating', 'created_at']
    ordering = ['-created_at']
    
    def get_permissions(self):
        if self.action in ['list', 'retrieve']:
            return [AllowAny()]
        elif self.action in ['create', 'my_reviews', 'bookings_without_review']:
            return [IsAuthenticated()]
        return [IsOwnerOrAdmin()]
    
    def get_queryset(self):
        """Filtrado dinámico según acción y usuario"""
        queryset = Review.objects.select_related('customer', 'package')
        
        # Admin puede ver TODO siempre
        if self.request.user.is_authenticated and self.request.user.user_type == 'admin':
            return queryset.all()
        
        # Usuario autenticado
        if self.request.user.is_authenticated:
            # Para operaciones de modificación: solo sus propias reviews
            if self.action in ['destroy', 'update', 'partial_update', 'retrieve']:
                from django.db.models import Q
                return queryset.filter(
                    Q(is_approved=True) | Q(customer=self.request.user)
                )
            
            # Para list: aprobadas + propias
            from django.db.models import Q
            return queryset.filter(
                Q(is_approved=True) | Q(customer=self.request.user)
            )
        
        # Usuario anónimo: solo aprobadas
        return queryset.filter(is_approved=True)
    
    @action(detail=False, methods=['get'], url_path='my-reviews')
    def my_reviews(self, request):
        """Ver SOLO las reseñas del usuario logueado"""
        queryset = Review.objects.filter(customer=request.user).order_by('-created_at')
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Tienes {queryset.count()} reseñas',
            'resenas': serializer.data
        })
    
    @action(detail=False, methods=['get'], url_path='bookings-without-review')
    def bookings_without_review(self, request):
        """Obtiene reservas del usuario sin reseña"""
        try:
            customer = request.user
            
            bookings = Booking.objects.filter(
                customer=customer
            ).exclude(
                id__in=Review.objects.filter(customer=customer).values_list('booking_id', flat=True)
            ).select_related('package_id').order_by('-booking_date')
            
            data = []
            for booking in bookings:
                data.append({
                    'id': booking.id,
                    'booking_number': booking.booking_number,
                    'package_id': booking.package_id_id,
                    'package_name': booking.package_id.name if booking.package_id else 'Sin paquete',
                    'booking_date': booking.booking_date.strftime('%Y-%m-%d'),
                    'status': booking.status
                })
            
            return Response({
                'exito': True,
                'mensaje': f'{len(data)} reservas disponibles',
                'reservas': data
            })
        except Exception as e:
            return Response({'exito': False, 'mensaje': str(e)}, status=500)
    
    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        page = self.paginate_queryset(queryset)
        
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response({
                'exito': True,
                'mensaje': f'Se encontraron {queryset.count()} reseñas',
                'resenas': serializer.data
            })
        
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Se encontraron {queryset.count()} reseñas',
            'resenas': serializer.data
        })
    
    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        
        if not serializer.is_valid():
            return Response({
                'exito': False,
                'mensaje': 'Error al crear la reseña',
                'errores': serializer.errors
            }, status=status.HTTP_400_BAD_REQUEST)
        
        serializer.save(customer=request.user)
        
        return Response({
            'exito': True,
            'mensaje': '¡Gracias por compartir tu opinión! Tu reseña será visible una vez aprobada',
            'resena': serializer.data
        }, status=status.HTTP_201_CREATED)
    
    def update(self, request, *args, **kwargs):
        partial = kwargs.pop('partial', False)
        instance = self.get_object()
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        
        if not serializer.is_valid():
            return Response({
                'exito': False,
                'mensaje': 'Error al actualizar la reseña',
                'errores': serializer.errors
            }, status=status.HTTP_400_BAD_REQUEST)
        
        self.perform_update(serializer)
        return Response({
            'exito': True,
            'mensaje': 'Tu reseña ha sido actualizada',
            'resena': serializer.data
        })
    
    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        self.perform_destroy(instance)
        return Response(status=status.HTTP_204_NO_CONTENT)
    
    def perform_create(self, serializer):
        serializer.save(customer=self.request.user)
