from rest_framework import viewsets, filters
from django_filters.rest_framework import DjangoFilterBackend
from .models import Category, Package
from .serializers import (
    CategorySerializer,
    PackageListSerializer,
    PackageDetailSerializer,
    PackageCreateSerializer
)


class CategoryViewSet(viewsets.ModelViewSet):
    """
    ViewSet para CRUD de categorías
    
    Endpoints:
    - GET    /api/v1/packages/categories/       - Listar categorías
    - POST   /api/v1/packages/categories/       - Crear categoría
    - GET    /api/v1/packages/categories/{id}/  - Detalle
    - PUT    /api/v1/packages/categories/{id}/  - Actualizar
    - DELETE /api/v1/packages/categories/{id}/  - Eliminar
    """
    
    queryset = Category.objects.all()
    serializer_class = CategorySerializer
    
    filter_backends = [filters.SearchFilter]
    search_fields = ['name']


class PackageViewSet(viewsets.ModelViewSet):
    """
    ViewSet para CRUD de paquetes
    
    Endpoints:
    - GET    /api/v1/packages/           - Listar paquetes
    - POST   /api/v1/packages/           - Crear paquete
    - GET    /api/v1/packages/{id}/      - Detalle con itinerario
    - PUT    /api/v1/packages/{id}/      - Actualizar
    - DELETE /api/v1/packages/{id}/      - Eliminar
    
    Filtros:
    - ?category=1
    - ?destination=1
    - ?is_featured=true
    - ?min_price=1000&max_price=3000
    - ?search=Cancún
    """
    
    queryset = Package.objects.select_related('destination', 'category').all()
    
    filter_backends = [
        DjangoFilterBackend,
        filters.SearchFilter,
        filters.OrderingFilter
    ]
    
    filterset_fields = ['category', 'destination', 'is_featured', 'is_active']
    search_fields = ['name', 'description', 'destination__name']
    ordering_fields = ['price_adult', 'created_at', 'duration_days']
    ordering = ['-created_at']
    
    def get_serializer_class(self):
        """
        Usar diferente serializer según la acción
        """
        if self.action == 'list':
            return PackageListSerializer
        elif self.action == 'retrieve':
            return PackageDetailSerializer
        return PackageCreateSerializer
    
    def get_queryset(self):
        """
        Filtros personalizados
        """
        queryset = super().get_queryset()
        
        # Filtrar por rango de precio
        min_price = self.request.query_params.get('min_price', None)
        max_price = self.request.query_params.get('max_price', None)
        
        if min_price:
            queryset = queryset.filter(price_adult__gte=min_price)
        
        if max_price:
            queryset = queryset.filter(price_adult__lte=max_price)
        
        # Filtrar por duración
        min_days = self.request.query_params.get('min_days', None)
        if min_days:
            queryset = queryset.filter(duration_days__gte=min_days)
        
        return queryset
