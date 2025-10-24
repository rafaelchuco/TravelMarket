from rest_framework import serializers
from .models import Hotel
from applications.destinations.models import Destination
from applications.destinations.serializers import DestinationSerializer

class HotelSerializer(serializers.ModelSerializer):
    destination = DestinationSerializer(read_only=True)
    destination_id = serializers.PrimaryKeyRelatedField(
        queryset = Destination.objects.all(),
        source='destination',
        write_only=True
    )

    class Meta:
        model = Hotel
        fields = [
            'id',
            'name',
            'destination',
            'destination_id',
            'address',
            'star_rating',
            'description',
            'amenities',
            'check_in_time',
            'check_out_time',
            'phone',
            'email',
            'price_per_night',
            'total_rooms',
            'image',
            'is_active',
        ]
        
    def validate_star_rating(self, value):
        """Validar estrellas entre 1 y 5"""
        if value < 1 or value > 5:
            raise serializers.ValidationError(
                "La clasificación debe estar entre 1 y 5 estrellas"
            )
        return value
    
    def validate_price_per_night(self, value):
        """Validar precio positivo"""
        if value <= 0:
            raise serializers.ValidationError(
                "El precio debe ser mayor a 0"
            )
        return value