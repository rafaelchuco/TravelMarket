from django.contrib import admin
from .models import Destination


@admin.register(Destination)
class DestinationAdmin(admin.ModelAdmin):
    """
    Admin personalizado para Destinos
    """
    
    list_display = [
        'name',
        'country',
        'continent',
        'is_popular',
        'created_at'
    ]
    
    list_filter = [
        'continent',
        'country',
        'is_popular',
        'created_at'
    ]
    
    search_fields = [
        'name',
        'country',
        'description'
    ]
    
    list_editable = ['is_popular']
    
    readonly_fields = ['created_at']
    
    fieldsets = (
        ('Información Básica', {
            'fields': ('name', 'country', 'continent')
        }),
        ('Descripción', {
            'fields': ('short_description', 'description')
        }),
        ('Ubicación', {
            'fields': ('latitude', 'longitude')
        }),
        ('Multimedia', {
            'fields': ('image',)
        }),
        ('Configuración', {
            'fields': ('is_popular', 'best_season')
        }),
        ('Fechas', {
            'fields': ('created_at',),
            'classes': ('collapse',)
        }),
    )