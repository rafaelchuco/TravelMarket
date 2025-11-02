from django.contrib import admin
from .models import Coupon, Wishlist


@admin.register(Coupon)
class CouponAdmin(admin.ModelAdmin):
    list_display = ['code', 'discount_type', 'discount_value', 'valid_from', 'valid_until', 'times_used', 'is_active']
    list_filter = ['discount_type', 'is_active', 'valid_from']
    search_fields = ['code', 'description']
    list_editable = ['is_active']


@admin.register(Wishlist)
class WishlistAdmin(admin.ModelAdmin):
    list_display = ['user', 'package', 'added_at']
    list_filter = ['added_at']
    search_fields = ['user__username', 'package__name']
