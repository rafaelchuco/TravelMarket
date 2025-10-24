from rest_framework import viewsets, filters
from django_filters.rest_framework import DjangoFilterBackend
from .models import Destination
from .serializers import DestinationSerializer


class DestinationViewSet(viewsets.ModelViewSet):
    """
    ViewSet para CRUD de destinos
    
    Endpoints:
    - GET    /api/v1/destinations/           - Listar destinos
    - POST   /api/v1/destinations/           - Crear destino
    - GET    /api/v1/destinations/{id}/      - Detalle
    - PUT    /api/v1/destinations/{id}/      - Actualizar
    - DELETE /api/v1/destinations/{id}/      - Eliminar
    
    Filtros disponibles:
    - ?country=México
    - ?continent=América
    - ?is_popular=true
    - ?search=Cancún
    """
    
    queryset = Destination.objects.all()
    serializer_class = DestinationSerializer
    
    # Configurar filtros
    filter_backends = [
        DjangoFilterBackend,
        filters.SearchFilter,
        filters.OrderingFilter
    ]
    
    # Campos por los que se puede filtrar
    filterset_fields = ['country', 'continent', 'is_popular']
    
    # Campos por los que se puede buscar
    search_fields = ['name', 'country', 'description']
    
    # Campos por los que se puede ordenar
    ordering_fields = ['name', 'country', 'created_at']
    ordering = ['-created_at']
