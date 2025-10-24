from django.db import models

class Destination(models.Model):
    name = models.CharField(max_length=200)
    country = models.CharField(max_length=100)
    continent = models.CharField(max_length=50)
    description = models.TextField()
    short_description = models.CharField(max_length=300)
    latitude = models.DecimalField(max_digits=9, decimal_places=6)
    longitude = models.DecimalField(max_digits=9, decimal_places=6)
    image = models.ImageField(upload_to='destinations/', blank=True, null=True)
    is_popular = models.BooleanField(default=False)
    best_season = models.CharField(max_length=100)
    created_at = models.DateTimeField(auto_now_add=True)

    class Meta:
        db_table = 'destinos'
        ordering = ['-created_at']

    def __str__(self):
        return f"{self.name} ({self.country})"

