from rest_framework import viewsets, filters, status
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated, AllowAny
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import IsOwnerOrAdmin
from .models import Review
from .serializers import ReviewSerializer


class ReviewViewSet(viewsets.ModelViewSet):
    """ViewSet para reseñas (como comentarios)"""
    queryset = Review.objects.select_related('customer', 'package').filter(is_approved=True)
    serializer_class = ReviewSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['package', 'overall_rating', 'is_approved']
    search_fields = ['title', 'comment']
    ordering_fields = ['overall_rating', 'created_at']
    ordering = ['-created_at']
    
    def get_permissions(self):
        if self.action in ['list', 'retrieve']:
            return [AllowAny()]
        elif self.action == 'create':
            return [IsAuthenticated()]
        return [IsOwnerOrAdmin()]
    
    def get_queryset(self):
        queryset = Review.objects.select_related('customer', 'package')
        
        if self.request.user.is_authenticated and self.request.user.user_type == 'admin':
            return queryset.all()
        
        if self.request.user.is_authenticated:
            from django.db.models import Q
            return queryset.filter(
                Q(is_approved=True) | Q(customer=self.request.user)
            )
        
        return queryset.filter(is_approved=True)
    
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
        return Response({
            'exito': True,
            'mensaje': 'Tu reseña ha sido eliminada'
        }, status=status.HTTP_204_NO_CONTENT)
    
    def perform_create(self, serializer):
        serializer.save(customer=self.request.user)
