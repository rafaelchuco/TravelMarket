from rest_framework import serializers
from .models import Flight
from datetime import datetime


class FlightSerializer(serializers.ModelSerializer):
    """
    Serializer para vuelos
    """
    
    duration = serializers.SerializerMethodField()
    
    class Meta:
        model = Flight
        fields = [
            'id',
            'airline_name',
            'airline_code',
            'flight_number',
            'origin_city',
            'destination_city',
            'origin_airport',
            'destination_airport',
            'departure_time',
            'arrival_time',
            'duration',
            'flight_class',
            'price',
            'available_seats',
            'baggage_allowance',
            'created_at'
        ]
        read_only_fields = ['id', 'created_at']
    
    def get_duration(self, obj):
        """Calcular duración del vuelo"""
        if obj.departure_time and obj.arrival_time:
            duration = obj.arrival_time - obj.departure_time
            hours = duration.total_seconds() / 3600
            return f"{hours:.1f} horas"
        return None
    
    def validate(self, attrs):
        """Validar fechas"""
        departure = attrs.get('departure_time')
        arrival = attrs.get('arrival_time')
        
        if departure and arrival:
            if arrival <= departure:
                raise serializers.ValidationError({
                    "arrival_time": "La hora de llegada debe ser posterior a la salida"
                })
        
        return attrs
