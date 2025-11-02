# applications/destinations/filters.py
import django_filters
from .models import Destination

class DestinationFilter(django_filters.FilterSet):
    """Filtros disponibles para destinos."""
    country = django_filters.CharFilter(lookup_expr='icontains')
    continent = django_filters.CharFilter(lookup_expr='icontains')
    is_popular = django_filters.BooleanFilter()
    best_season = django_filters.CharFilter(lookup_expr='icontains')

    class Meta:
        model = Destination
        fields = ['country', 'continent', 'is_popular', 'best_season']