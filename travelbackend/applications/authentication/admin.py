from django.contrib import admin
from django.contrib.auth.admin import UserAdmin as BaseUserAdmin
from .models import User


@admin.register(User)
class UserAdmin(BaseUserAdmin):
    """
    Configuración personalizada del Admin para el modelo User
    """
    
    # Campos a mostrar en la lista de usuarios
    list_display = [
        'username',
        'email',
        'first_name',
        'last_name',
        'user_type',
        'is_active',
        'created_at'
    ]
    
    # Filtros laterales
    list_filter = [
        'user_type',
        'is_active',
        'is_staff',
        'created_at',
        'country'
    ]
    
    # Campos por los que se puede buscar
    search_fields = [
        'username',
        'email',
        'first_name',
        'last_name',
        'phone',
        'passport_number'
    ]
    
    # Organización de campos en el formulario
    fieldsets = (
        ('Información de Cuenta', {
            'fields': ('username', 'password')
        }),
        ('Información Personal', {
            'fields': ('first_name', 'last_name', 'email')
        }),
        ('Tipo de Usuario', {
            'fields': ('user_type',)
        }),
        ('Información de Contacto', {
            'fields': ('phone', 'address', 'city', 'country')
        }),
        ('Información de Viaje', {
            'fields': ('passport_number', 'nationality'),
            'classes': ('collapse',)  # Colapsado por defecto
        }),
        ('Permisos', {
            'fields': ('is_active', 'is_staff', 'is_superuser', 'groups', 'user_permissions'),
            'classes': ('collapse',)
        }),
        ('Fechas Importantes', {
            'fields': ('last_login', 'date_joined', 'created_at', 'updated_at'),
            'classes': ('collapse',)
        }),
    )
    
    # Campos de solo lectura
    readonly_fields = [
        'created_at',
        'updated_at',
        'last_login',
        'date_joined'
    ]
    
    # Ordenar por fecha de creación (más recientes primero)
    ordering = ['-created_at']
    
    # Campos a mostrar en la página de creación de usuario
    add_fieldsets = (
        ('Información de Cuenta', {
            'classes': ('wide',),
            'fields': ('username', 'password1', 'password2'),
        }),
        ('Información Personal', {
            'fields': ('first_name', 'last_name', 'email'),
        }),
        ('Tipo de Usuario', {
            'fields': ('user_type',),
        }),
    )
