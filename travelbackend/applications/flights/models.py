# applications/flights/models.py
from django.db import models
from django.utils import timezone 

class Flight(models.Model):
    airline_name = models.CharField(max_length=100)
    airline_code = models.CharField(max_length=10)
    flight_number = models.CharField(max_length=20)
    origin_city = models.CharField(max_length=100)
    destination_city = models.CharField(max_length=100)
    origin_airport = models.CharField(max_length=100)
    destination_airport = models.CharField(max_length=100)
    departure_time = models.DateTimeField()
    arrival_time = models.DateTimeField()
    flight_class = models.CharField(max_length=50)
    price = models.DecimalField(max_digits=10, decimal_places=2)
    available_seats = models.PositiveIntegerField()
    baggage_allowance = models.CharField(max_length=100, blank=True, null=True)
    created_at = models.DateTimeField(default=timezone.now)  # <-- agregado

    class Meta:
        db_table = 'vuelos'
        verbose_name = 'Vuelo'
        verbose_name_plural = 'Vuelos'

    def __str__(self):
        return f"{self.airline_name} {self.flight_number} ({self.origin_city} a {self.destination_city})"