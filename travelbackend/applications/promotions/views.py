from rest_framework import viewsets, status
from rest_framework.response import Response
from rest_framework.permissions import AllowAny, IsAuthenticated
from .models import Coupon, Wishlist
from .serializers import CouponSerializer, WishlistSerializer


class CouponViewSet(viewsets.ReadOnlyModelViewSet):
    """ViewSet para cupones"""
    queryset = Coupon.objects.filter(is_active=True)
    serializer_class = CouponSerializer
    permission_classes = [AllowAny]
    
    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Hay {queryset.count()} cupones disponibles',
            'cupones': serializer.data
        })


class WishlistViewSet(viewsets.ModelViewSet):
    """ViewSet para lista de deseos"""
    queryset = Wishlist.objects.select_related('package')
    serializer_class = WishlistSerializer
    permission_classes = [IsAuthenticated]
    
    def get_queryset(self):
        if self.request.user.user_type == 'admin':
            return Wishlist.objects.all()
        return Wishlist.objects.filter(user=self.request.user)
    
    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Tienes {queryset.count()} paquetes en favoritos',
            'favoritos': serializer.data
        })
    
    def create(self, request, *args, **kwargs):
        # Verificar si ya existe
        package_id = request.data.get('package')
        if Wishlist.objects.filter(user=request.user, package_id=package_id).exists():
            return Response({
                'exito': False,
                'mensaje': 'Este paquete ya está en tus favoritos'
            }, status=status.HTTP_400_BAD_REQUEST)
        
        serializer = self.get_serializer(data=request.data)
        
        if not serializer.is_valid():
            return Response({
                'exito': False,
                'mensaje': 'Error al agregar a favoritos',
                'errores': serializer.errors
            }, status=status.HTTP_400_BAD_REQUEST)
        
        serializer.save(user=request.user)
        
        return Response({
            'exito': True,
            'mensaje': '¡Paquete agregado a tus favoritos!',
            'favorito': serializer.data
        }, status=status.HTTP_201_CREATED)
    
    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        self.perform_destroy(instance)
        return Response({
            'exito': True,
            'mensaje': 'Paquete eliminado de tus favoritos'
        }, status=status.HTTP_204_NO_CONTENT)
    
    def perform_create(self, serializer):
        serializer.save(user=self.request.user)
