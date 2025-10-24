from django.db import models
from django.core.validators import MinValueValidator, MaxValueValidator


class Review(models.Model):
    booking = models.ForeignKey(
        'bookings.Booking',
        on_delete=models.CASCADE,
        related_name='reviews',
        verbose_name='Reserva'
    )
    
    customer = models.ForeignKey(
        'authentication.User',
        on_delete=models.CASCADE,
        related_name='reviews',
        verbose_name='Cliente'
    )
    
    package = models.ForeignKey(
        'packages.Package',
        on_delete=models.CASCADE,
        related_name='reviews',
        verbose_name='Paquete'
    )
    
    overall_rating = models.IntegerField(
        validators=[MinValueValidator(1), MaxValueValidator(5)],
        verbose_name='Calificación General'
    )
    
    accommodation_rating = models.IntegerField(
        validators=[MinValueValidator(1), MaxValueValidator(5)],
        verbose_name='Calificación Alojamiento',
        null=True,
        blank=True
    )
    
    transport_rating = models.IntegerField(
        validators=[MinValueValidator(1), MaxValueValidator(5)],
        verbose_name='Calificación Transporte',
        null=True,
        blank=True
    )
    
    guide_rating = models.IntegerField(
        validators=[MinValueValidator(1), MaxValueValidator(5)],
        verbose_name='Calificación Guía',
        null=True,
        blank=True
    )
    
    value_rating = models.IntegerField(
        validators=[MinValueValidator(1), MaxValueValidator(5)],
        verbose_name='Calificación Relación Calidad-Precio',
        null=True,
        blank=True
    )
    
    title = models.CharField(max_length=200, verbose_name='Título')
    comment = models.TextField(verbose_name='Comentario')
    pros = models.TextField(verbose_name='Aspectos Positivos', blank=True, null=True)
    cons = models.TextField(verbose_name='Aspectos Negativos', blank=True, null=True)
    
    is_verified = models.BooleanField(default=False, verbose_name='Verificada')
    is_approved = models.BooleanField(default=False, verbose_name='Aprobada')
    created_at = models.DateTimeField(auto_now_add=True, verbose_name='Fecha de Creación')
    
    class Meta:
        db_table = 'resenas'
        verbose_name = 'Reseña'
        verbose_name_plural = 'Reseñas'
        ordering = ['-created_at']
        indexes = [
            models.Index(fields=['package', '-created_at']),
            models.Index(fields=['customer']),
            models.Index(fields=['is_approved', '-created_at']),
        ]
    
    def __str__(self):
        return f"{self.title} - {self.customer.username} ({self.overall_rating}⭐)"
    
    @property
    def average_rating(self):
        ratings = [
            self.overall_rating,
            self.accommodation_rating,
            self.transport_rating,
            self.guide_rating,
            self.value_rating
        ]
        valid_ratings = [r for r in ratings if r is not None and r > 0]
        return sum(valid_ratings) / len(valid_ratings) if valid_ratings else 0