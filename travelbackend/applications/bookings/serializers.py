from uuid import uuid4
from rest_framework import serializers
from . import models


class PassengerSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.Passenger
        fields = [
            'id',
            'passenger_type',
            'title',
            'first_name',
            'last_name',
            'date_of_birth',
            'gender',
            'passport_number',
            'nationality',
        ]


class HotelBookingSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.HotelBooking
        fields = [
            'id',
            'hotel_id',
            'check_in_date',
            'check_out_date',
            'num_rooms',
            'room_type',
            'price_per_night',
            'total_nights',
            'total_price',
            'confirmation_number',
        ]


class FlightBookingSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.FlightBooking
        fields = [
            'id',
            'flight_id',
            'booking_type',
            'num_passengers',
            'seat_numbers',
            'price_per_person',
            'total_price',
            'pnr_number',
        ]


class BookingListSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.Booking
        fields = [
            'id',
            'booking_number',
            'customer_id',
            'travel_date',
            'return_date',
            'num_adults',
            'num_children',
            'num_infants',
            'total_amount',
            'status',
            'payment_status',
            'booking_date',
        ]


class BookingDetailSerializer(serializers.ModelSerializer):
    passengers = PassengerSerializer(many=True, read_only=True)
    hotel_bookings = HotelBookingSerializer(many=True, read_only=True)
    flight_bookings = FlightBookingSerializer(many=True, read_only=True)

    class Meta:
        model = models.Booking
        fields = '__all__'


class BookingCreateSerializer(serializers.ModelSerializer):
    passengers = PassengerSerializer(many=True, required=False)
    hotel_bookings = HotelBookingSerializer(many=True, required=False)
    flight_bookings = FlightBookingSerializer(many=True, required=False)

    class Meta:
        model = models.Booking
        fields = [
            'id',
            'booking_number',
            'customer_id',
            'package_id',
            'travel_date',
            'return_date',
            'num_adults',
            'num_children',
            'num_infants',
            'subtotal',
            'discount_amount',
            'tax_amount',
            'total_amount',
            'paid_amount',
            'status',
            'payment_status',
            'special_requests',
            'booking_date',
            'updated_at',
            'passengers',
            'hotel_bookings',
            'flight_bookings',
        ]
        read_only_fields = ['id', 'booking_date', 'updated_at', 'booking_number', 'customer_id']
        extra_kwargs = {
            'package_id': {'required': True},
            'travel_date': {'required': True},
            'num_adults': {'required': True},
            'total_amount': {'required': True},
            'subtotal': {'required': False, 'default': 0},
            'discount_amount': {'required': False, 'default': 0},
            'tax_amount': {'required': False, 'default': 0},
            'paid_amount': {'required': False, 'default': 0},
            'status': {'required': False, 'default': 'pending'},
            'payment_status': {'required': False, 'default': 'unpaid'},
            'special_requests': {'required': False, 'allow_blank': True, 'default': ''},
        }

    def validate(self, attrs):
        return attrs

    def create(self, validated_data):
        passengers_data = validated_data.pop('passengers', [])
        hotel_bookings_data = validated_data.pop('hotel_bookings', [])
        flight_bookings_data = validated_data.pop('flight_bookings', [])

        # ✅ ASIGNAR customer del request
        validated_data['customer_id'] = self.context['request'].user.id

        # ✅ GENERAR booking_number
        validated_data['booking_number'] = uuid4().hex[:12].upper()

        booking = models.Booking.objects.create(**validated_data)

        for p in passengers_data:
            models.Passenger.objects.create(booking_id=booking, **p)

        for h in hotel_bookings_data:
            models.HotelBooking.objects.create(booking_id=booking, **h)

        for f in flight_bookings_data:
            models.FlightBooking.objects.create(booking_id=booking, **f)

        return booking
