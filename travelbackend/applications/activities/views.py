from rest_framework import viewsets, filters, status
from rest_framework.response import Response
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import ReadOnlyOrAdmin
from .models import Activity
from .serializers import ActivitySerializer


class ActivityViewSet(viewsets.ModelViewSet):
    """ViewSet para actividades turísticas"""
    queryset = Activity.objects.select_related('destination').filter(is_active=True)
    serializer_class = ActivitySerializer
    permission_classes = [ReadOnlyOrAdmin]
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['destination', 'activity_type', 'difficulty_level']
    search_fields = ['name', 'description', 'destination__name']
    ordering_fields = ['name', 'price_per_person', 'duration_hours', 'created_at']
    ordering = ['destination', 'name']
    
    def get_queryset(self):
        queryset = super().get_queryset()
        
        min_price = self.request.query_params.get('min_price', None)
        max_price = self.request.query_params.get('max_price', None)
        
        if min_price:
            queryset = queryset.filter(price_per_person__gte=min_price)
        
        if max_price:
            queryset = queryset.filter(price_per_person__lte=max_price)
        
        min_duration = self.request.query_params.get('min_duration', None)
        max_duration = self.request.query_params.get('max_duration', None)
        
        if min_duration:
            queryset = queryset.filter(duration_hours__gte=min_duration)
        
        if max_duration:
            queryset = queryset.filter(duration_hours__lte=max_duration)
        
        return queryset
    
    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        page = self.paginate_queryset(queryset)
        
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response({
                'exito': True,
                'mensaje': f'Se encontraron {queryset.count()} actividades',
                'actividades': serializer.data
            })
        
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Se encontraron {queryset.count()} actividades',
            'actividades': serializer.data
        })
