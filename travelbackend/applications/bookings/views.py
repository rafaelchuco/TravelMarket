from decimal import Decimal
from uuid import uuid4

from rest_framework import permissions, viewsets

from . import models, serializers


class BookingViewSet(viewsets.ModelViewSet):
    
    queryset = models.Booking.objects.all().order_by('-booking_date')
    permission_classes = [permissions.IsAuthenticatedOrReadOnly]

    def get_serializer_class(self):
        mapping = {
            'list': serializers.BookingListSerializer,
            'retrieve': serializers.BookingDetailSerializer,
            'create': serializers.BookingCreateSerializer,
        }
        return mapping.get(self.action, serializers.BookingDetailSerializer)

    def perform_create(self, serializer):
        data = serializer.validated_data

        subtotal = data.get('subtotal')
        if subtotal is None:
            subtotal = Decimal('0.00')
            # hotels
            for h in data.get('hotel_bookings', []):
                if h.get('total_price'):
                    subtotal += Decimal(str(h.get('total_price')))
                else:
                    price = Decimal(str(h.get('price_per_night', 0) or 0))
                    nights = Decimal(str(h.get('total_nights', 1) or 1))
                    rooms = Decimal(str(h.get('num_rooms', 1) or 1))
                    subtotal += price * nights * rooms

            # flights
            for f in data.get('flight_bookings', []):
                if f.get('total_price'):
                    subtotal += Decimal(str(f.get('total_price')))
                else:
                    price = Decimal(str(f.get('price_per_person', 0) or 0))
                    nump = Decimal(str(f.get('num_passengers', 1) or 1))
                    subtotal += price * nump

        discount = Decimal(str(data.get('discount_amount', 0) or 0))
        tax = Decimal(str(data.get('tax_amount', 0) or 0))
        total = subtotal - discount + tax

        booking_number = data.get('booking_number')
        if not booking_number:
            booking_number = uuid4().hex[:12].upper()

        serializer.save(booking_number=booking_number, subtotal=subtotal, total_amount=total)

