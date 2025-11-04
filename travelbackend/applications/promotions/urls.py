from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import CouponViewSet, WishlistViewSet

app_name = 'promotions'

router = DefaultRouter()
router.register(r'coupons', CouponViewSet, basename='coupon')
router.register(r'wishlist', WishlistViewSet, basename='wishlist')

urlpatterns = [
    path('', include(router.urls)),
]