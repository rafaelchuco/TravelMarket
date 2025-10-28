from rest_framework import viewsets, filters, status
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import IsOwnerOrAdmin
from .models import Booking
from .serializers import (
    BookingListSerializer,
    BookingDetailSerializer,
    BookingCreateSerializer
)


class BookingViewSet(viewsets.ModelViewSet):
    """ViewSet para reservas"""
    queryset = Booking.objects.select_related(
        'customer', 'package_id'
    ).prefetch_related(
        'passengers',
        'hotel_bookings',
        'flight_bookings'
    ).all()
    
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['customer', 'package_id', 'status', 'payment_status']
    search_fields = ['booking_number', 'customer__email', 'customer__first_name']
    ordering_fields = ['booking_date', 'travel_date', 'total_amount']
    ordering = ['-booking_date']
    
    def get_permissions(self):
        if self.action == 'create':
            return [IsAuthenticated()]
        return [IsOwnerOrAdmin()]
    
    def get_serializer_class(self):
        if self.action == 'list':
            return BookingListSerializer
        elif self.action == 'retrieve':
            return BookingDetailSerializer
        elif self.action == 'create':
            return BookingCreateSerializer
        return BookingDetailSerializer
    
    def get_queryset(self):
        queryset = super().get_queryset()
        
        if self.request.user.user_type == 'admin':
            pass
        else:
            queryset = queryset.filter(customer=self.request.user)
        
        travel_date_from = self.request.query_params.get('travel_date_from', None)
        travel_date_to = self.request.query_params.get('travel_date_to', None)
        
        if travel_date_from:
            queryset = queryset.filter(travel_date__gte=travel_date_from)
        
        if travel_date_to:
            queryset = queryset.filter(travel_date__lte=travel_date_to)
        
        return queryset
    
    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        page = self.paginate_queryset(queryset)
        
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response({
                'exito': True,
                'mensaje': f'Tienes {queryset.count()} reservas',
                'reservas': serializer.data
            })
        
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Tienes {queryset.count()} reservas',
            'reservas': serializer.data
        })
    
    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        
        if not serializer.is_valid():
            return Response({
                'exito': False,
                'mensaje': 'Error al crear la reserva',
                'errores': serializer.errors
            }, status=status.HTTP_400_BAD_REQUEST)
        
        booking = serializer.save()
        
        return Response({
            'exito': True,
            'mensaje': '¡Tu reserva ha sido confirmada exitosamente!',
            'numero_reserva': booking.booking_number,
            'detalles': BookingDetailSerializer(booking).data
        }, status=status.HTTP_201_CREATED)
    
    @action(detail=False, methods=['get'], permission_classes=[IsAuthenticated])
    def my_bookings(self, request):
        """GET /api/v1/bookings/my_bookings/ - Mis reservas"""
        bookings = self.queryset.filter(customer=request.user)
        serializer = BookingListSerializer(bookings, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Tienes {bookings.count()} reservas',
            'reservas': serializer.data
        })
    
    @action(detail=True, methods=['patch'], permission_classes=[IsOwnerOrAdmin])
    def cancel(self, request, pk=None):
        """PATCH /api/v1/bookings/{id}/cancel/ - Cancelar reserva"""
        booking = self.get_object()
        
        if booking.status == 'cancelled':
            return Response({
                'exito': False,
                'mensaje': 'Esta reserva ya fue cancelada anteriormente'
            }, status=status.HTTP_400_BAD_REQUEST)
        
        if booking.status == 'completed':
            return Response({
                'exito': False,
                'mensaje': 'No es posible cancelar una reserva que ya fue completada'
            }, status=status.HTTP_400_BAD_REQUEST)
        
        booking.status = 'cancelled'
        booking.save()
        
        return Response({
            'exito': True,
            'mensaje': 'Tu reserva ha sido cancelada exitosamente',
            'numero_reserva': booking.booking_number,
            'detalles': BookingDetailSerializer(booking).data
        })
        
    def destroy(self, request, *args, **kwargs):
        """DELETE /api/v1/bookings/{id}/ - Eliminar reserva (usuario o admin)"""
        booking = self.get_object()

        # Si no es dueño ni admin, no puede eliminar
        if not (request.user.user_type == 'admin' or booking.customer == request.user):
            return Response({
                'exito': False,
                'mensaje': 'No tienes permiso para eliminar esta reserva.'
            }, status=status.HTTP_403_FORBIDDEN)

        # No permitir eliminar reservas completadas
        if booking.status == 'completed':
            return Response({
                'exito': False,
                'mensaje': 'No puedes eliminar una reserva que ya fue completada.'
            }, status=status.HTTP_400_BAD_REQUEST)

        booking_number = booking.booking_number
        booking.delete()

        return Response({
            'exito': True,
            'mensaje': f'La reserva {booking_number} ha sido eliminada correctamente.'
        }, status=status.HTTP_204_NO_CONTENT)