# applications/destinations/views.py
from rest_framework import viewsets, filters
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import ReadOnlyOrAdmin
from .models import Destination
from .serializers import DestinationSerializer
from .filters import DestinationFilter

class DestinationViewSet(viewsets.ModelViewSet):
    """
    ViewSet para destinos
    - GET: Público (todos pueden ver)
    - POST/PUT/DELETE: Solo admin
    """
    queryset = Destination.objects.all()
    serializer_class = DestinationSerializer
    permission_classes = [ReadOnlyOrAdmin]

    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = DestinationFilter  # ✅ usa el filtro correcto
    search_fields = ['name', 'country', 'description', 'best_season']
    ordering_fields = ['name', 'country', 'created_at']
    ordering = ['-created_at']
