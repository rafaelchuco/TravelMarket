from django.db import models
from django.core.validators import MinValueValidator
from applications.authentication.models import User
from applications.packages.models import Package


class Coupon(models.Model):
    """
    Cupones de descuento
    """
    
    DISCOUNT_TYPES = (
        ('percentage', 'Porcentaje'),
        ('fixed', 'Monto fijo'),
    )
    
    code = models.CharField(
        max_length=50,
        unique=True,
        verbose_name='Código del cupón'
    )
    
    description = models.CharField(
        max_length=200,
        verbose_name='Descripción'
    )
    
    discount_type = models.CharField(
        max_length=20,
        choices=DISCOUNT_TYPES,
        verbose_name='Tipo de descuento'
    )
    
    discount_value = models.DecimalField(
        max_digits=10,
        decimal_places=2,
        validators=[MinValueValidator(0)],
        verbose_name='Valor del descuento'
    )
    
    min_purchase_amount = models.DecimalField(
        max_digits=10,
        decimal_places=2,
        blank=True,
        null=True,
        verbose_name='Compra mínima'
    )
    
    max_discount_amount = models.DecimalField(
        max_digits=10,
        decimal_places=2,
        blank=True,
        null=True,
        verbose_name='Descuento máximo'
    )
    
    valid_from = models.DateField(
        verbose_name='Válido desde'
    )
    
    valid_until = models.DateField(
        verbose_name='Válido hasta'
    )
    
    max_uses = models.IntegerField(
        blank=True,
        null=True,
        verbose_name='Usos máximos'
    )
    
    times_used = models.IntegerField(
        default=0,
        verbose_name='Veces usado'
    )
    
    is_active = models.BooleanField(
        default=True,
        verbose_name='¿Está activo?'
    )
    
    created_at = models.DateTimeField(
        auto_now_add=True,
        verbose_name='Fecha de creación'
    )
    
    class Meta:
        db_table = 'cupones'
        verbose_name = 'Cupón'
        verbose_name_plural = 'Cupones'
        ordering = ['-created_at']
    
    def __str__(self):
        return f"{self.code} - {self.discount_value}{'%' if self.discount_type == 'percentage' else '$'}"


class Wishlist(models.Model):
    """
    Lista de deseos de usuarios
    """
    
    user = models.ForeignKey(
        User,
        on_delete=models.CASCADE,
        related_name='wishlist',
        verbose_name='Usuario'
    )
    
    package = models.ForeignKey(
        Package,
        on_delete=models.CASCADE,
        related_name='wishlists',
        verbose_name='Paquete'
    )
    
    added_at = models.DateTimeField(
        auto_now_add=True,
        verbose_name='Fecha de agregado'
    )
    
    class Meta:
        db_table = 'lista_deseos'
        verbose_name = 'Lista de Deseos'
        verbose_name_plural = 'Listas de Deseos'
        ordering = ['-added_at']
        unique_together = ['user', 'package']
    
    def __str__(self):
        return f"{self.user.username} - {self.package.name}"
