from rest_framework import serializers
from .models import Category, Package, Itinerary


class CategorySerializer(serializers.ModelSerializer):
    """Serializer para categorías"""
    
    class Meta:
        model = Category
        fields = ['id', 'name', 'description', 'icon']


class ItinerarySerializer(serializers.ModelSerializer):
    """Serializer para itinerarios"""
    
    class Meta:
        model = Itinerary
        fields = [
            'id',
            'day_number',
            'title',
            'description',
            'activities',
            'meals_included'
        ]


class PackageListSerializer(serializers.ModelSerializer):
    """Serializer para listado de paquetes con camelCase"""
    
    destinationName = serializers.CharField(
        source='destination.name',
        read_only=True
    )
    
    categoryName = serializers.CharField(
        source='category.name',
        read_only=True
    )
    
    # ✅ Mapeo explícito a camelCase
    shortDescription = serializers.CharField(source='short_description')
    durationDays = serializers.IntegerField(source='duration_days')
    durationNights = serializers.IntegerField(source='duration_nights')
    priceAdult = serializers.DecimalField(source='price_adult', max_digits=10, decimal_places=2)
    priceChild = serializers.DecimalField(source='price_child', max_digits=10, decimal_places=2)
    isFeatured = serializers.BooleanField(source='is_featured')
    createdAt = serializers.DateTimeField(source='created_at')
    
    class Meta:
        model = Package
        fields = [
            'id',
            'name',
            'slug',
            'categoryName',
            'destinationName',
            'shortDescription',
            'durationDays',
            'durationNights',
            'priceAdult',
            'priceChild',
            'image',
            'isFeatured',
            'createdAt'
        ]


class PackageDetailSerializer(serializers.ModelSerializer):
    """Serializer detallado de paquete con itinerario completo"""
    
    destination = serializers.StringRelatedField()
    category = CategorySerializer(read_only=True)
    itinerary = ItinerarySerializer(many=True, read_only=True)
    
    # Mapeo a camelCase
    shortDescription = serializers.CharField(source='short_description')
    durationDays = serializers.IntegerField(source='duration_days')
    durationNights = serializers.IntegerField(source='duration_nights')
    priceAdult = serializers.DecimalField(source='price_adult', max_digits=10, decimal_places=2)
    priceChild = serializers.DecimalField(source='price_child', max_digits=10, decimal_places=2)
    maxPeople = serializers.IntegerField(source='max_people')
    minPeople = serializers.IntegerField(source='min_people')
    includesFlight = serializers.BooleanField(source='includes_flight')
    includesHotel = serializers.BooleanField(source='includes_hotel')
    includesMeals = serializers.BooleanField(source='includes_meals')
    includesTransport = serializers.BooleanField(source='includes_transport')
    includesGuide = serializers.BooleanField(source='includes_guide')
    isActive = serializers.BooleanField(source='is_active')
    isFeatured = serializers.BooleanField(source='is_featured')
    availableFrom = serializers.DateField(source='available_from')
    availableUntil = serializers.DateField(source='available_until')
    createdAt = serializers.DateTimeField(source='created_at')
    updatedAt = serializers.DateTimeField(source='updated_at')
    
    totalDuration = serializers.SerializerMethodField()
    
    class Meta:
        model = Package
        fields = [
            'id',
            'name',
            'slug',
            'category',
            'destination',
            'description',
            'shortDescription',
            'durationDays',
            'durationNights',
            'totalDuration',
            'priceAdult',
            'priceChild',
            'maxPeople',
            'minPeople',
            'includesFlight',
            'includesHotel',
            'includesMeals',
            'includesTransport',
            'includesGuide',
            'image',
            'isActive',
            'isFeatured',
            'availableFrom',
            'availableUntil',
            'itinerary',
            'createdAt',
            'updatedAt'
        ]
    
    def get_totalDuration(self, obj):
        return f"{obj.duration_days} días / {obj.duration_nights} noches"


class PackageCreateSerializer(serializers.ModelSerializer):
    """Serializer para crear paquetes"""
    
    class Meta:
        model = Package
        fields = [
            'name',
            'slug',
            'category',
            'destination',
            'description',
            'short_description',
            'duration_days',
            'duration_nights',
            'price_adult',
            'price_child',
            'max_people',
            'min_people',
            'includes_flight',
            'includes_hotel',
            'includes_meals',
            'includes_transport',
            'includes_guide',
            'image',
            'is_featured',
            'available_from',
            'available_until'
        ]
    
    def validate(self, attrs):
        if attrs.get('duration_days', 0) < 1:
            raise serializers.ValidationError({
                "duration_days": "La duración debe ser al menos 1 día"
            })
        
        if attrs.get('duration_nights', 0) < 0:
            raise serializers.ValidationError({
                "duration_nights": "Las noches no pueden ser negativas"
            })
        
        if attrs.get('price_adult', 0) <= 0:
            raise serializers.ValidationError({
                "price_adult": "El precio debe ser mayor a 0"
            })
        
        if attrs.get('max_people', 0) < attrs.get('min_people', 1):
            raise serializers.ValidationError({
                "max_people": "El máximo debe ser mayor o igual al mínimo"
            })
        
        return attrs
