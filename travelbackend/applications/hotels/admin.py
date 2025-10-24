from django.contrib import admin
from .models import Hotel

@admin.register(Hotel)
class HotelAdmin(admin.ModelAdmin):

    list_display = (
        'name', 
        'destination', 
        'star_rating', 
        'price_per_night', 
        'is_active'
    )

    list_filter = ('is_active', 'star_rating', 'destination')
    search_fields = ('name', 'destination__name', 'address')
    list_editable = ('is_active', 'price_per_night', 'star_rating')

    fieldsets = (
        (None, {
            'fields': ('name', 'destination', 'address', 'image')
        }),
        ('Detalles', {
            'fields': ('description', 'amenities', 'star_rating', 'price_per_night', 'total_rooms')
        }),
        ('Contacto y Horarios', {
            'fields': ('phone', 'email', 'check_in_time', 'check_out_time')
        }),
        ('Estado', {
            'fields': ('is_active',)
        }),
    )