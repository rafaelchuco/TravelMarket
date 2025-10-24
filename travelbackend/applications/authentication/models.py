from django.db import models

# Create your models here.
from django.contrib.auth.models import AbstractUser


class User(AbstractUser):
    """
    Modelo de Usuario personalizado para la agencia de turismo
    Extiende el usuario por defecto de Django
    """
    
    # Choices para tipo de usuario
    USER_TYPES = (
        ('admin', 'Administrador'),
        ('customer', 'Cliente'),
    )
    
    # Campos adicionales
    user_type = models.CharField(
        max_length=20,
        choices=USER_TYPES,
        default='customer',
        verbose_name='Tipo de usuario'
    )
    
    phone = models.CharField(
        max_length=20,
        blank=True,
        null=True,
        verbose_name='Teléfono'
    )
    
    address = models.TextField(
        blank=True,
        null=True,
        verbose_name='Dirección'
    )
    
    city = models.CharField(
        max_length=100,
        blank=True,
        null=True,
        verbose_name='Ciudad'
    )
    
    country = models.CharField(
        max_length=100,
        blank=True,
        null=True,
        verbose_name='País'
    )
    
    passport_number = models.CharField(
        max_length=50,
        blank=True,
        null=True,
        verbose_name='Número de Pasaporte'
    )
    
    nationality = models.CharField(
        max_length=100,
        blank=True,
        null=True,
        verbose_name='Nacionalidad'
    )
    
    # Timestamps
    created_at = models.DateTimeField(
        auto_now_add=True,
        verbose_name='Fecha de creación'
    )
    
    updated_at = models.DateTimeField(
        auto_now=True,
        verbose_name='Última actualización'
    )
    
    class Meta:
        db_table = 'usuarios'
        verbose_name = 'Usuario'
        verbose_name_plural = 'Usuarios'
        ordering = ['-created_at']
    
    def __str__(self):
        return f"{self.username} - {self.get_user_type_display()}"
