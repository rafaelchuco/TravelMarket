from rest_framework import viewsets, filters
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import ReadOnlyOrAdmin
from .models import Category, Package
from .serializers import (
    CategorySerializer,
    PackageListSerializer,
    PackageDetailSerializer,
    PackageCreateSerializer
)


class CategoryViewSet(viewsets.ModelViewSet):
    """
    ViewSet para categorías
    - GET: Público
    - POST/PUT/DELETE: Solo admin
    """
    queryset = Category.objects.all()
    serializer_class = CategorySerializer
    permission_classes = [ReadOnlyOrAdmin]
    
    filter_backends = [filters.SearchFilter]
    search_fields = ['name']


class PackageViewSet(viewsets.ModelViewSet):
    """
    ViewSet para paquetes
    - GET: Público
    - POST/PUT/DELETE: Solo admin
    """
    queryset = Package.objects.select_related('destination', 'category').all()
    permission_classes = [ReadOnlyOrAdmin]
    
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['category', 'destination', 'is_featured', 'is_active']
    search_fields = ['name', 'description', 'destination__name']
    ordering_fields = ['price_adult', 'created_at', 'duration_days']
    ordering = ['-created_at']
    
    def get_serializer_class(self):
        if self.action == 'list':
            return PackageListSerializer
        elif self.action == 'retrieve':
            return PackageDetailSerializer
        return PackageCreateSerializer
    
    def get_queryset(self):
        queryset = super().get_queryset()
        
        min_price = self.request.query_params.get('min_price', None)
        max_price = self.request.query_params.get('max_price', None)
        
        if min_price:
            queryset = queryset.filter(price_adult__gte=min_price)
        
        if max_price:
            queryset = queryset.filter(price_adult__lte=max_price)
        
        min_days = self.request.query_params.get('min_days', None)
        if min_days:
            queryset = queryset.filter(duration_days__gte=min_days)
        
        return queryset