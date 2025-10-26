from rest_framework import viewsets, filters
from rest_framework.permissions import IsAuthenticated, AllowAny
from django_filters.rest_framework import DjangoFilterBackend
from core.permissions import IsAdminUser
from .models import Review
from .serializers import ReviewSerializer


class ReviewViewSet(viewsets.ModelViewSet):
    """
    ViewSet para reviews
    - GET: Público (solo reviews aprobadas)
    - POST: Usuario autenticado
    - PUT/DELETE: Solo admin
    """
    queryset = Review.objects.select_related('customer', 'package').filter(is_approved=True)
    serializer_class = ReviewSerializer
    
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['package', 'overall_rating', 'is_approved']
    search_fields = ['title', 'comment']
    ordering_fields = ['overall_rating', 'created_at']
    ordering = ['-created_at']
    
    def get_permissions(self):
        """
        - list, retrieve: Público
        - create: Usuario autenticado
        - update, destroy: Solo admin
        """
        if self.action in ['list', 'retrieve']:
            return [AllowAny()]
        elif self.action == 'create':
            return [IsAuthenticated()]
        return [IsAdminUser()]
    
    def perform_create(self, serializer):
        """Asignar automáticamente el usuario autenticado"""
        serializer.save(customer=self.request.user)
