from django.db import models
from django.utils import timezone

class Activity(models.Model):
    # Tipos de actividad en español
    class ActivityType(models.TextChoices):
        SIGHTSEEING = 'sightseeing', 'Turismo'
        ADVENTURE = 'adventure', 'Aventura'
        CULTURAL = 'cultural', 'Cultural'
        SHOPPING = 'shopping', 'Compras'
        DINING = 'dining', 'Comida'
        SPORTS = 'sports', 'Deportes'
        WELLNESS = 'wellness', 'Bienestar'
        ENTERTAINMENT = 'entertainment', 'Entretenimiento'

    # Niveles de dificultad en español
    class DifficultyLevel(models.TextChoices):
        EASY = 'easy', 'Fácil'
        MODERATE = 'moderate', 'Moderado'
        DIFFICULT = 'difficult', 'Difícil'

    # Campos del modelo
    name = models.CharField("Nombre", max_length=100)
    destination = models.ForeignKey(
        'destinations.Destination',
        on_delete=models.CASCADE,
        related_name='activities',
        verbose_name="Destino"
    )
    activity_type = models.CharField("Tipo de actividad", max_length=20, choices=ActivityType.choices)
    description = models.TextField("Descripción")
    duration_hours = models.DecimalField("Duración (horas)", max_digits=4, decimal_places=1)
    difficulty_level = models.CharField("Nivel de dificultad", max_length=10, choices=DifficultyLevel.choices)
    price_per_person = models.DecimalField("Precio por persona", max_digits=10, decimal_places=2)
    max_group_size = models.PositiveIntegerField("Tamaño máximo del grupo")
    image = models.ImageField("Imagen", upload_to='activities/', blank=True, null=True)
    is_active = models.BooleanField("Activo", default=True)
    created_at = models.DateTimeField("Fecha de creación", default=timezone.now)
    updated_at = models.DateTimeField("Última actualización", auto_now=True)

    class Meta:
        db_table = 'actividades'
        ordering = ['name']
        verbose_name = 'Actividad'
        verbose_name_plural = 'Actividades'

    def __str__(self):
        return f"{self.name} ({self.get_activity_type_display()})"