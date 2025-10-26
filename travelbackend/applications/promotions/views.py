from rest_framework import viewsets
from rest_framework.permissions import AllowAny, IsAuthenticated
from core.permissions import IsAdminUser, IsOwnerOrAdmin
from .models import Coupon, Wishlist
from .serializers import CouponSerializer, WishlistSerializer


class CouponViewSet(viewsets.ReadOnlyModelViewSet):
    """
    ViewSet para cupones (solo lectura para clientes)
    - GET: Público
    - POST/PUT/DELETE: Solo admin (desde admin panel)
    """
    queryset = Coupon.objects.filter(is_active=True)
    serializer_class = CouponSerializer
    permission_classes = [AllowAny]


class WishlistViewSet(viewsets.ModelViewSet):
    """
    ViewSet para wishlist
    - GET/POST/DELETE: Usuario autenticado (solo ve lo suyo)
    """
    queryset = Wishlist.objects.select_related('package')
    serializer_class = WishlistSerializer
    permission_classes = [IsAuthenticated]
    
    def get_queryset(self):
        """Usuario solo ve su propia wishlist"""
        if self.request.user.user_type == 'admin':
            return Wishlist.objects.all()
        return Wishlist.objects.filter(user=self.request.user)
    
    def perform_create(self, serializer):
        """Asignar automáticamente el usuario autenticado"""
        serializer.save(user=self.request.user)