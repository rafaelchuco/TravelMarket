from rest_framework import viewsets, filters
from django_filters.rest_framework import DjangoFilterBackend
import django_filters
from .models import Hotel
from .serializers import HotelSerializer

class HotelFilter(django_filters.FilterSet):
    price_range = django_filters.RangeFilter(field_name='price_per_night')

    class Meta:
        model = Hotel
        fields = [
            'destination',
            'star_rating',
            'price_range',  
        ]


class HotelViewSet(viewsets.ModelViewSet):
    queryset = Hotel.objects.filter(is_active=True)

    serializer_class = HotelSerializer

    filter_backends = [
        DjangoFilterBackend,
        filters.SearchFilter,
        filters.OrderingFilter
    ]

    filterset_class = HotelFilter
    search_fields = ['name', 'address', 'destination__name']
    ordering_fields = ['price_per_night', 'star_rating']