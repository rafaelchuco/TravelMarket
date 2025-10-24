from rest_framework import viewsets
from .models import Coupon, Wishlist
from .serializers import CouponSerializer, WishlistSerializer


class CouponViewSet(viewsets.ReadOnlyModelViewSet):
    """ViewSet para cupones (solo lectura)"""
    queryset = Coupon.objects.filter(is_active=True)
    serializer_class = CouponSerializer


class WishlistViewSet(viewsets.ModelViewSet):
    """ViewSet para wishlist"""
    queryset = Wishlist.objects.select_related('package')
    serializer_class = WishlistSerializer