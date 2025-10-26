"""
Permisos personalizados para el sistema
"""
from rest_framework import permissions


class IsAdminUser(permissions.BasePermission):
    """
    Solo usuarios con rol admin pueden acceder
    """
    def has_permission(self, request, view):
        return (
            request.user and 
            request.user.is_authenticated and 
            request.user.user_type == 'admin'
        )


class IsCustomerUser(permissions.BasePermission):
    """
    Solo usuarios con rol customer pueden acceder
    """
    def has_permission(self, request, view):
        return (
            request.user and 
            request.user.is_authenticated and 
            request.user.user_type == 'customer'
        )


class IsOwnerOrAdmin(permissions.BasePermission):
    """
    Solo el propietario del recurso o un admin pueden acceder
    """
    def has_permission(self, request, view):
        return request.user and request.user.is_authenticated
    
    def has_object_permission(self, request, view, obj):
        # Admin puede acceder a todo
        if request.user.user_type == 'admin':
            return True
        
        # El propietario puede acceder a lo suyo
        if hasattr(obj, 'customer'):
            return obj.customer == request.user
        if hasattr(obj, 'user'):
            return obj.user == request.user
        
        return False


class ReadOnlyOrAdmin(permissions.BasePermission):
    """
    Lectura para todos, escritura solo para admin
    """
    def has_permission(self, request, view):
        # Métodos de lectura (GET, HEAD, OPTIONS) permitidos para todos
        if request.method in permissions.SAFE_METHODS:
            return True
        
        # Métodos de escritura (POST, PUT, PATCH, DELETE) solo para admin autenticado
        return (
            request.user and 
            request.user.is_authenticated and 
            request.user.user_type == 'admin'
        )