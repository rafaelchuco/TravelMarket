from rest_framework import viewsets, filters, status
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated, IsAuthenticatedOrReadOnly
from django_filters.rest_framework import DjangoFilterBackend
from django.db.models import Avg, Q, Count

from .models import Review
from .serializers import ReviewSerializer, ReviewListSerializer, ReviewCreateSerializer


class ReviewViewSet(viewsets.ModelViewSet):
    queryset = Review.objects.all()
    permission_classes = [IsAuthenticatedOrReadOnly]
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['package', 'customer', 'overall_rating', 'is_approved', 'is_verified']
    search_fields = ['title', 'comment']
    ordering_fields = ['created_at', 'overall_rating']
    ordering = ['-created_at']
    
    def get_queryset(self):
        queryset = super().get_queryset()
        
        if self.request.user.is_staff:
            return queryset
        
        if self.request.user.is_authenticated:
            return queryset.filter(
                Q(is_approved=True) | Q(customer=self.request.user)
            )
        
        return queryset.filter(is_approved=True)
    
    def get_serializer_class(self):
        if self.action == 'create':
            return ReviewCreateSerializer
        elif self.action == 'list':
            return ReviewListSerializer
        return ReviewSerializer
    
    def perform_create(self, serializer):
        serializer.save(customer=self.request.user)
    
    @action(detail=False, methods=['get'], url_path='by-package/(?P<package_id>[^/.]+)')
    def by_package(self, request, package_id=None):
        reviews = self.get_queryset().filter(package_id=package_id, is_approved=True)
        
        stats = reviews.aggregate(
            average_overall=Avg('overall_rating'),
            average_accommodation=Avg('accommodation_rating'),
            average_transport=Avg('transport_rating'),
            average_guide=Avg('guide_rating'),
            average_value=Avg('value_rating'),
            total_reviews=Count('id')
        )
        
        serializer = self.get_serializer(reviews, many=True)
        
        return Response({
            'stats': stats,
            'reviews': serializer.data
        })
    
    @action(detail=False, methods=['get'], url_path='my-reviews')
    def my_reviews(self, request):
        if not request.user.is_authenticated:
            return Response(
                {'error': 'Debes estar autenticado'},
                status=status.HTTP_401_UNAUTHORIZED
            )
        
        reviews = self.get_queryset().filter(customer=request.user)
        serializer = self.get_serializer(reviews, many=True)
        return Response(serializer.data)
    
    @action(detail=True, methods=['post'], permission_classes=[IsAuthenticated])
    def approve(self, request, pk=None):
        if not request.user.is_staff:
            return Response(
                {'error': 'No tienes permisos para aprobar reseñas'},
                status=status.HTTP_403_FORBIDDEN
            )
        
        review = self.get_object()
        review.is_approved = True
        review.save()
        
        serializer = self.get_serializer(review)
        return Response(serializer.data)
    
    @action(detail=True, methods=['post'], permission_classes=[IsAuthenticated])
    def verify(self, request, pk=None):
        if not request.user.is_staff:
            return Response(
                {'error': 'No tienes permisos para verificar reseñas'},
                status=status.HTTP_403_FORBIDDEN
            )
        
        review = self.get_object()
        review.is_verified = True
        review.save()
        
        serializer = self.get_serializer(review)
        return Response(serializer.data)