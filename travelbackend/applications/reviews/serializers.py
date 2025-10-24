from rest_framework import serializers
from .models import Review


class ReviewSerializer(serializers.ModelSerializer):
    customer_name = serializers.CharField(source='customer.get_full_name', read_only=True)
    package_name = serializers.CharField(source='package.name', read_only=True)
    average_rating = serializers.FloatField(read_only=True)
    
    class Meta:
        model = Review
        fields = [
            'id',
            'booking',
            'customer',
            'customer_name',
            'package',
            'package_name',
            'overall_rating',
            'accommodation_rating',
            'transport_rating',
            'guide_rating',
            'value_rating',
            'average_rating',
            'title',
            'comment',
            'pros',
            'cons',
            'is_verified',
            'is_approved',
            'created_at',
        ]
        read_only_fields = ['id', 'created_at', 'is_verified', 'is_approved', 'customer']
    
    def validate_overall_rating(self, value):
        if value < 1 or value > 5:
            raise serializers.ValidationError("La calificación debe estar entre 1 y 5")
        return value


class ReviewListSerializer(serializers.ModelSerializer):
    customer_name = serializers.CharField(source='customer.get_full_name', read_only=True)
    
    class Meta:
        model = Review
        fields = [
            'id',
            'customer_name',
            'overall_rating',
            'title',
            'comment',
            'created_at',
        ]


class ReviewCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Review
        fields = [
            'booking',
            'package',
            'overall_rating',
            'accommodation_rating',
            'transport_rating',
            'guide_rating',
            'value_rating',
            'title',
            'comment',
            'pros',
            'cons',
        ]
    
    def validate(self, data):
        booking = data.get('booking')
        request = self.context.get('request')
        
        if booking and request and request.user:
            if booking.customer != request.user:
                raise serializers.ValidationError(
                    "No puedes hacer una reseña de una reserva que no te pertenece"
                )
            
            if Review.objects.filter(booking=booking).exists():
                raise serializers.ValidationError(
                    "Ya existe una reseña para esta reserva"
                )
        
        return data