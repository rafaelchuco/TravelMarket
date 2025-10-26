from rest_framework import viewsets, filters, status
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import IsOwnerOrAdmin, IsAdminUser
from .models import Booking
from .serializers import (
    BookingListSerializer,
    BookingDetailSerializer,
    BookingCreateSerializer
)


class BookingViewSet(viewsets.ModelViewSet):
    """
    ViewSet para reservas
    - POST: Usuario autenticado (crear su reserva)
    - GET list: Solo ve sus propias reservas (admin ve todas)
    - GET detail/PUT/DELETE: Solo el owner o admin
    """
    # ✅ CORREGIDO: Quitamos 'package' del select_related
    queryset = Booking.objects.select_related(
        'customer'  # Solo customer es ForeignKey
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
        """
        - create: Usuario autenticado
        - list, retrieve, update, destroy: Owner o Admin
        """
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
        """
        Admin: ve todas las reservas
        Customer: solo ve sus propias reservas
        """
        queryset = super().get_queryset()
        
        # Si es admin, ve todo
        if self.request.user.user_type == 'admin':
            pass
        # Si es customer, solo ve sus reservas
        else:
            queryset = queryset.filter(customer=self.request.user)
        
        # Filtros de fecha
        travel_date_from = self.request.query_params.get('travel_date_from', None)
        travel_date_to = self.request.query_params.get('travel_date_to', None)
        
        if travel_date_from:
            queryset = queryset.filter(travel_date__gte=travel_date_from)
        
        if travel_date_to:
            queryset = queryset.filter(travel_date__lte=travel_date_to)
        
        return queryset
    
    def create(self, request, *args, **kwargs):
        """Crear reserva con respuesta personalizada"""
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        booking = serializer.save()
        
        return Response({
            'message': 'Reserva creada exitosamente',
            'booking': BookingDetailSerializer(booking).data
        }, status=status.HTTP_201_CREATED)
    
    @action(detail=False, methods=['get'], permission_classes=[IsAuthenticated])
    def my_bookings(self, request):
        """GET /api/v1/bookings/my_bookings/ - Mis reservas"""
        bookings = self.queryset.filter(customer=request.user)
        serializer = BookingListSerializer(bookings, many=True)
        return Response(serializer.data)
    
    @action(detail=True, methods=['patch'], permission_classes=[IsOwnerOrAdmin])
    def cancel(self, request, pk=None):
        """PATCH /api/v1/bookings/{id}/cancel/ - Cancelar reserva"""
        booking = self.get_object()
        
        if booking.status == 'cancelled':
            return Response({
                'error': 'La reserva ya está cancelada'
            }, status=status.HTTP_400_BAD_REQUEST)
        
        if booking.status == 'completed':
            return Response({
                'error': 'No se puede cancelar una reserva completada'
            }, status=status.HTTP_400_BAD_REQUEST)
        
        booking.status = 'cancelled'
        booking.save()
        
        return Response({
            'message': 'Reserva cancelada exitosamente',
            'booking': BookingDetailSerializer(booking).data
        })
