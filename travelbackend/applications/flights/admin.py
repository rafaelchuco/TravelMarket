from django.contrib import admin
from .models import Flight

@admin.register(Flight)
class FlightAdmin(admin.ModelAdmin):

    list_display = (
        'airline_name', 
        'flight_number', 
        'origin_city', 
        'destination_city', 
        'departure_time', 
        'arrival_time',
        'price',
        'available_seats'
    )

    list_filter = ('airline_name', 'origin_city', 'destination_city', 'flight_class')
    search_fields = ('flight_number', 'origin_city', 'destination_city', 'origin_airport', 'destination_airport')
    #date_hierarchy = 'departure_time'