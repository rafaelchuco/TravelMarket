from rest_framework import viewsets, filters
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import ReadOnlyOrAdmin
from .models import Hotel
from .serializers import HotelSerializer


class HotelViewSet(viewsets.ModelViewSet):
    """
    ViewSet para hoteles
    - GET: Público
    - POST/PUT/DELETE: Solo admin
    """
    queryset = Hotel.objects.select_related('destination').filter(is_active=True)
    serializer_class = HotelSerializer
    permission_classes = [ReadOnlyOrAdmin]
    
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['destination', 'star_rating']
    search_fields = ['name', 'address', 'destination__name']
    ordering_fields = ['name', 'price_per_night', 'star_rating', 'created_at']
    ordering = ['name']
    
    def get_queryset(self):
        queryset = super().get_queryset()
        
        min_price = self.request.query_params.get('min_price', None)
        max_price = self.request.query_params.get('max_price', None)
        
        if min_price:
            queryset = queryset.filter(price_per_night__gte=min_price)
        
        if max_price:
            queryset = queryset.filter(price_per_night__lte=max_price)
        
        return queryset