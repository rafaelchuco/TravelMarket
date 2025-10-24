from rest_framework import serializers
from .models import Activity

class ActivitySerializer(serializers.ModelSerializer):
    class Meta:
        model = Activity
        fields = '__all__'

    def validate_duration_hours(self, value):
        if value <= 0:
            raise serializers.ValidationError("La duracion debe ser mayor que cero.")
        return value

    def validate_price_per_person(self, value):
        if value < 0:
            raise serializers.ValidationError("El precio no puede ser negativo.")
        return value
