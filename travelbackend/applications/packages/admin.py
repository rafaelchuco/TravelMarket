from django.contrib import admin
from .models import Category, Package, Itinerary

class ItineraryInline(admin.TabularInline):
    model = Itinerary
    extra = 1
    fields = ['day_number', 'title', 'description', 'activities', 'meals_included']

@admin.register(Category)
class CategoryAdmin(admin.ModelAdmin):
    list_display = ['name', 'icon']
    search_fields = ['name']

@admin.register(Package)
class PackageAdmin(admin.ModelAdmin):
    list_display = [
        'name', 'destination', 'category', 'price_adult', 
        'duration_days', 'is_featured', 'is_active', 'created_at'
    ]
    list_filter = ['category', 'destination', 'is_featured', 'is_active', 'created_at']
    search_fields = ['name', 'description', 'destination__name']
    prepopulated_fields = {'slug': ('name',)}
    list_editable = ['is_featured', 'is_active']
    readonly_fields = ['created_at', 'updated_at']
    
    fieldsets = (
        ('Información Básica', {
            'fields': ('name', 'slug', 'category', 'destination')
        }),
        ('Descripción', {
            'fields': ('short_description', 'description')
        }),
        ('Duración y Capacidad', {
            'fields': (('duration_days', 'duration_nights'), ('min_people', 'max_people'))
        }),
        ('Precios', {
            'fields': ('price_adult', 'price_child')
        }),
        ('Inclusiones', {
            'fields': (
                'includes_flight', 'includes_hotel', 'includes_meals', 
                'includes_transport', 'includes_guide'
            )
        }),
        ('Multimedia', {
            'fields': ('image',)
        }),
        ('Disponibilidad', {
            'fields': (('available_from', 'available_until'), 'is_active', 'is_featured')
        }),
        ('Fechas', {
            'fields': ('created_at', 'updated_at'),
            'classes': ('collapse',)
        }),
    )
    
    inlines = [ItineraryInline]
