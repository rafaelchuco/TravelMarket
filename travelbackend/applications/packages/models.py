from django.db import models
from django.utils.text import slugify

class Category(models.Model):
    name = models.CharField(max_length=100, verbose_name="Nombre")
    description = models.TextField(blank=True, verbose_name="Descripción")
    icon = models.CharField(max_length=50, blank=True, verbose_name="Icono")
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = "Categoría"
        verbose_name_plural = "Categorías"
        db_table = "categorias_paquetes"

    def __str__(self):
        return self.name


class Package(models.Model):
    name = models.CharField(max_length=200, verbose_name="Nombre del paquete")
    slug = models.SlugField(max_length=200, unique=True, blank=True, verbose_name="Slug")
    category = models.ForeignKey(Category, on_delete=models.CASCADE, related_name='packages', verbose_name="Categoría")
    destination = models.ForeignKey('destinations.Destination', on_delete=models.CASCADE, related_name='packages', verbose_name="Destino")
    description = models.TextField(verbose_name="Descripción")
    short_description = models.CharField(max_length=300, verbose_name="Descripción corta")
    duration_days = models.PositiveIntegerField(verbose_name="Días de duración")
    duration_nights = models.PositiveIntegerField(verbose_name="Noches de duración")
    price_adult = models.DecimalField(max_digits=10, decimal_places=2, verbose_name="Precio adulto")
    price_child = models.DecimalField(max_digits=10, decimal_places=2, verbose_name="Precio niño")
    max_people = models.PositiveIntegerField(verbose_name="Máximo de personas")
    min_people = models.PositiveIntegerField(default=1, verbose_name="Mínimo de personas")

    # ✅ Inclusiones como BooleanFields
    includes_flight = models.BooleanField(default=False, verbose_name="Incluye Vuelo")
    includes_hotel = models.BooleanField(default=False, verbose_name="Incluye Hotel")
    includes_meals = models.BooleanField(default=False, verbose_name="Incluye Comidas")
    includes_transport = models.BooleanField(default=False, verbose_name="Incluye Transporte")
    includes_guide = models.BooleanField(default=False, verbose_name="Incluye Guía")

    image = models.ImageField(upload_to='packages/', blank=True, null=True, verbose_name="Imagen")
    is_featured = models.BooleanField(default=False, verbose_name="Destacado")
    is_active = models.BooleanField(default=True, verbose_name="Activo")
    available_from = models.DateField(verbose_name="Disponible desde")
    available_until = models.DateField(verbose_name="Disponible hasta")
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = "Paquete Turístico"
        verbose_name_plural = "Paquetes Turísticos"
        db_table = "paquetes_turisticos"
        ordering = ['-created_at']

    def __str__(self):
        return self.name

    def save(self, *args, **kwargs):
        if not self.slug:
            self.slug = slugify(self.name)
        super().save(*args, **kwargs)



class Itinerary(models.Model):
    """Modelo para itinerarios de paquetes"""
    package = models.ForeignKey(Package, on_delete=models.CASCADE, related_name='itineraries', verbose_name="Paquete")
    day_number = models.PositiveIntegerField(verbose_name="Número de día")
    title = models.CharField(max_length=200, verbose_name="Título del día")
    description = models.TextField(verbose_name="Descripción")
    activities = models.JSONField(default=list, verbose_name="Actividades")
    meals_included = models.JSONField(default=list, verbose_name="Comidas incluidas")
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        verbose_name = "Itinerario"
        verbose_name_plural = "Itinerarios"
        db_table = "itinerarios_paquete"
        ordering = ['day_number']
        unique_together = ['package', 'day_number']

    def __str__(self):
        return f"{self.package.name} - Día {self.day_number}: {self.title}"


class Wishlist(models.Model):
    """Lista de deseos de los usuarios"""
    user = models.ForeignKey(
        'authentication.User',
        on_delete=models.CASCADE,
        related_name='wishlist_items',
        verbose_name='Usuario'
    )
    package = models.ForeignKey(
        Package,
        on_delete=models.CASCADE,
        related_name='wishlisted_by',
        verbose_name='Paquete'
    )
    added_at = models.DateTimeField(auto_now_add=True, verbose_name='Fecha de agregado')

    class Meta:
        db_table = 'wishlist_packages'  # <- nombre único para evitar conflicto
        verbose_name = 'Lista de Deseos'
        verbose_name_plural = 'Lista de Deseos'
        ordering = ['-added_at']
        unique_together = ['user', 'package']
        indexes = [
            models.Index(fields=['user', '-added_at']),
            models.Index(fields=['package']),
        ]

    def __str__(self):
        return f"{self.user.username} - {self.package.name}"