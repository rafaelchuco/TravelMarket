from django.db import models

class Hotel(models.Model):
    name = models.CharField(max_length=255)
    destination = models.ForeignKey(
        'destinations.Destination', 
        on_delete=models.SET_NULL, 
        null=True, 
        related_name='hotels'
    )
    address = models.CharField(max_length=255)
    star_rating = models.PositiveIntegerField(default=3)
    description = models.TextField(blank=True, null=True)
    amenities = models.TextField(blank=True, null=True)
    check_in_time = models.TimeField(null=True, blank=True)
    check_out_time = models.TimeField(null=True, blank=True)
    phone = models.CharField(max_length=25, blank=True, null=True)
    email = models.EmailField(max_length=255, blank=True, null=True)
    price_per_night = models.DecimalField(max_digits=10, decimal_places=2)
    total_rooms = models.PositiveIntegerField()
    image = models.ImageField(upload_to='hotels/', blank=True, null=True)
    is_active = models.BooleanField(default=True)

    class Meta:
        db_table = 'hoteles'
        verbose_name = 'Hotel'
        verbose_name_plural = 'Hoteles'

    def __str__(self):
        return self.name