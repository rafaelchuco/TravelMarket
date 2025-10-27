"""
Mensajes personalizados para la API
"""

MESSAGES = {
    # Autenticación
    'login_success': '¡Bienvenido de nuevo!',
    'login_failed': 'Usuario o contraseña incorrectos',
    'logout_success': 'Has cerrado sesión exitosamente',
    'register_success': '¡Cuenta creada exitosamente!',
    'profile_updated': 'Tu perfil ha sido actualizado',
    
    # Reservas
    'booking_created': '¡Tu reserva ha sido confirmada!',
    'booking_updated': 'Reserva actualizada correctamente',
    'booking_cancelled': 'Tu reserva ha sido cancelada',
    'booking_not_found': 'No encontramos esta reserva',
    'booking_already_cancelled': 'Esta reserva ya fue cancelada',
    'booking_completed_no_cancel': 'No puedes cancelar una reserva completada',
    
    # Reviews
    'review_created': '¡Gracias por compartir tu opinión!',
    'review_updated': 'Tu reseña ha sido actualizada',
    'review_deleted': 'Tu reseña ha sido eliminada',
    'review_pending_approval': 'Tu reseña será visible una vez aprobada',
    
    # Wishlist
    'wishlist_added': 'Agregado a tus favoritos',
    'wishlist_removed': 'Eliminado de tus favoritos',
    'wishlist_already_exists': 'Ya tienes este paquete en favoritos',
    
    # Errores generales
    'not_found': 'No encontramos lo que buscas',
    'unauthorized': 'Debes iniciar sesión para continuar',
    'forbidden': 'No tienes permiso para realizar esta acción',
    'validation_error': 'Por favor verifica los datos ingresados',
    'server_error': 'Algo salió mal, intenta más tarde',
}
