from rest_framework import viewsets, filters
from django_filters.rest_framework import DjangoFilterBackend
from .models import Flight
from .serializers import FlightSerializer

class FlightViewSet(viewsets.ModelViewSet):
    queryset = Flight.objects.all()

    serializer_class = FlightSerializer

    filter_backends = [
        DjangoFilterBackend,
        filters.SearchFilter,
        filters.OrderingFilter
    ]

    filterset_fields = [
        'origin_city', 
        'destination_city', 
        'departure_time'
    ]

    search_fields = ['airline_name', 'flight_number', 'origin_city', 'destination_city']

    ordering_fields = ['price', 'departure_time']