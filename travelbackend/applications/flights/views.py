from rest_framework import viewsets, filters
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import ReadOnlyOrAdmin
from .models import Flight
from .serializers import FlightSerializer


class FlightViewSet(viewsets.ModelViewSet):
    """
    ViewSet para vuelos
    - GET: Público
    - POST/PUT/DELETE: Solo admin
    """
    queryset = Flight.objects.all()
    serializer_class = FlightSerializer
    permission_classes = [ReadOnlyOrAdmin]
    
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['origin_city', 'destination_city', 'flight_class', 'airline_name']
    search_fields = ['flight_number', 'airline_name', 'origin_city', 'destination_city']
    ordering_fields = ['departure_time', 'price', 'available_seats']
    ordering = ['departure_time']