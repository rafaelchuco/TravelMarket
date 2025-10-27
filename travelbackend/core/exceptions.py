"""
Manejador de excepciones personalizado con mensajes amigables
"""
from rest_framework.views import exception_handler
from rest_framework.exceptions import (
    ValidationError,
    AuthenticationFailed,
    NotAuthenticated,
    PermissionDenied,
    NotFound,
    MethodNotAllowed,
    Throttled
)
from rest_framework import status
from django.core.exceptions import ObjectDoesNotExist


def custom_exception_handler(exc, context):
    """
    Manejador personalizado que traduce errores técnicos a mensajes amigables
    """
    # Llamar al manejador por defecto primero
    response = exception_handler(exc, context)
    
    # Si no hay response, crear una
    if response is None:
        return response
    
    # Personalizar mensajes según el tipo de error
    custom_response_data = {
        'exito': False,
        'mensaje': '',
        'detalles': {}
    }
    
    # 400 Bad Request - Errores de validación
    if isinstance(exc, ValidationError):
        custom_response_data['mensaje'] = 'Por favor verifica los datos ingresados'
        custom_response_data['detalles'] = response.data
    
    # 401 Unauthorized - No autenticado
    elif isinstance(exc, (NotAuthenticated, AuthenticationFailed)):
        custom_response_data['mensaje'] = 'Debes iniciar sesión para acceder a este recurso'
        custom_response_data['detalles'] = {
            'accion': 'Por favor inicia sesión con tu usuario y contraseña'
        }
    
    # 403 Forbidden - Sin permisos
    elif isinstance(exc, PermissionDenied):
        custom_response_data['mensaje'] = 'No tienes permiso para realizar esta acción'
        custom_response_data['detalles'] = {
            'accion': 'Esta operación requiere permisos especiales'
        }
    
    # 404 Not Found
    elif isinstance(exc, (NotFound, ObjectDoesNotExist)):
        custom_response_data['mensaje'] = 'No encontramos lo que buscas'
        custom_response_data['detalles'] = {
            'accion': 'Verifica que la información sea correcta'
        }
    
    # 405 Method Not Allowed
    elif isinstance(exc, MethodNotAllowed):
        custom_response_data['mensaje'] = 'Esta operación no está disponible'
        custom_response_data['detalles'] = {
            'metodo_permitido': str(exc.default_detail)
        }
    
    # 429 Too Many Requests
    elif isinstance(exc, Throttled):
        custom_response_data['mensaje'] = 'Has realizado demasiadas solicitudes'
        custom_response_data['detalles'] = {
            'accion': f'Espera {exc.wait} segundos antes de intentar nuevamente'
        }
    
    # 500 Internal Server Error u otros
    elif response.status_code >= 500:
        custom_response_data['mensaje'] = 'Ocurrió un error en el servidor'
        custom_response_data['detalles'] = {
            'accion': 'Por favor intenta más tarde o contacta con soporte'
        }
    
    # Cualquier otro error
    else:
        custom_response_data['mensaje'] = 'Ocurrió un error al procesar tu solicitud'
        custom_response_data['detalles'] = response.data
    
    response.data = custom_response_data
    return response
