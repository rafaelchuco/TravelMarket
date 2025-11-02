from rest_framework import serializers
from .models import Destination


class DestinationSerializer(serializers.ModelSerializer):
    """
    Serializer completo para Destination
    """
    
    class Meta:
        model = Destination
        fields = [
            'id',
            'name',
            'country',
            'continent',
            'description',
            'short_description',
            'latitude',
            'longitude',
            'image',
            'is_popular',
            'best_season',
            'created_at'
        ]
        read_only_fields = ['id', 'created_at']
    
    def validate_name(self, value):
        """Validar que el nombre no esté vacío"""
        if not value or value.strip() == '':
            raise serializers.ValidationError("El nombre es requerido")
        return value
    
    def validate(self, attrs):
        """Validar coordenadas si están presentes"""
        latitude = attrs.get('latitude')
        longitude = attrs.get('longitude')
        
        if latitude and (latitude < -90 or latitude > 90):
            raise serializers.ValidationError({
                "latitude": "La latitud debe estar entre -90 y 90"
            })
        
        if longitude and (longitude < -180 or longitude > 180):
            raise serializers.ValidationError({
                "longitude": "La longitud debe estar entre -180 y 180"
            })
        
        return attrs
