from rest_framework import viewsets, filters, status
from rest_framework.response import Response
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import ReadOnlyOrAdmin
from .models import Destination
from .serializers import DestinationSerializer
from .filters import DestinationFilter


class DestinationViewSet(viewsets.ModelViewSet):
    """
    ViewSet para destinos turísticos
    """
    queryset = Destination.objects.all()
    serializer_class = DestinationSerializer
    permission_classes = [ReadOnlyOrAdmin]
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = DestinationFilter
    search_fields = ['name', 'country', 'description', 'best_season']
    ordering_fields = ['name', 'country', 'created_at']
    ordering = ['-created_at']
    
    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        page = self.paginate_queryset(queryset)
        
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response({
                'exito': True,
                'mensaje': f'Se encontraron {queryset.count()} destinos',
                'destinos': serializer.data
            })
        
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Se encontraron {queryset.count()} destinos',
            'destinos': serializer.data
        })
    
    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = self.get_serializer(instance)
        return Response({
            'exito': True,
            'mensaje': 'Destino encontrado',
            'destino': serializer.data
        })
    
    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        
        if not serializer.is_valid():
            return Response({
                'exito': False,
                'mensaje': 'Error al crear el destino',
                'errores': serializer.errors
            }, status=status.HTTP_400_BAD_REQUEST)
        
        self.perform_create(serializer)
        return Response({
            'exito': True,
            'mensaje': 'Destino creado exitosamente',
            'destino': serializer.data
        }, status=status.HTTP_201_CREATED)
    
    def update(self, request, *args, **kwargs):
        partial = kwargs.pop('partial', False)
        instance = self.get_object()
        serializer = self.get_serializer(instance, data=request.data, partial=partial)
        
        if not serializer.is_valid():
            return Response({
                'exito': False,
                'mensaje': 'Error al actualizar el destino',
                'errores': serializer.errors
            }, status=status.HTTP_400_BAD_REQUEST)
        
        self.perform_update(serializer)
        return Response({
            'exito': True,
            'mensaje': 'Destino actualizado exitosamente',
            'destino': serializer.data
        })
    
    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        self.perform_destroy(instance)
        return Response({
            'exito': True,
            'mensaje': 'Destino eliminado exitosamente'
        }, status=status.HTTP_204_NO_CONTENT)
