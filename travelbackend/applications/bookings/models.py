from decimal import Decimal

from django.conf import settings
from django.db import models


class Booking(models.Model):
	STATUS_PENDING = 'pending'
	STATUS_CONFIRMED = 'confirmed'
	STATUS_CANCELLED = 'cancelled'
	STATUS_COMPLETED = 'completed'

	STATUS_CHOICES = [
		(STATUS_PENDING, 'Pending'),
		(STATUS_CONFIRMED, 'Confirmed'),
		(STATUS_CANCELLED, 'Cancelled'),
		(STATUS_COMPLETED, 'Completed'),
	]

	PAYMENT_UNPAID = 'unpaid'
	PAYMENT_PARTIAL = 'partial'
	PAYMENT_PAID = 'paid'
	PAYMENT_REFUNDED = 'refunded'

	PAYMENT_STATUS_CHOICES = [
		(PAYMENT_UNPAID, 'Unpaid'),
		(PAYMENT_PARTIAL, 'Partial'),
		(PAYMENT_PAID, 'Paid'),
		(PAYMENT_REFUNDED, 'Refunded'),
	]

	booking_number = models.CharField(max_length=64, unique=True)
	customer = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name='bookings')
	package_id = models.ForeignKey('packages.Package', on_delete=models.SET_NULL, null=True, blank=True, related_name='bookings')
	travel_date = models.DateField(null=True, blank=True)
	return_date = models.DateField(null=True, blank=True)
	num_adults = models.PositiveIntegerField(default=1)
	num_children = models.PositiveIntegerField(default=0)
	num_infants = models.PositiveIntegerField(default=0)
	subtotal = models.DecimalField(max_digits=12, decimal_places=2, default=Decimal('0.00'))
	discount_amount = models.DecimalField(max_digits=12, decimal_places=2, default=Decimal('0.00'))
	tax_amount = models.DecimalField(max_digits=12, decimal_places=2, default=Decimal('0.00'))
	total_amount = models.DecimalField(max_digits=12, decimal_places=2, default=Decimal('0.00'))
	paid_amount = models.DecimalField(max_digits=12, decimal_places=2, default=Decimal('0.00'))
	status = models.CharField(max_length=16, choices=STATUS_CHOICES, default=STATUS_PENDING)
	payment_status = models.CharField(max_length=16, choices=PAYMENT_STATUS_CHOICES, default=PAYMENT_UNPAID)
	special_requests = models.TextField(null=True, blank=True)
	booking_date = models.DateTimeField(auto_now_add=True)
	updated_at = models.DateTimeField(auto_now=True)

	class Meta:
		db_table = 'reservas'
		ordering = ('-booking_date',)

	def __str__(self):
		return f"Booking {self.booking_number} ({self.customer_id})"


class Passenger(models.Model):
	TYPE_ADULT = 'adult'
	TYPE_CHILD = 'child'
	TYPE_INFANT = 'infant'

	PASSENGER_TYPE_CHOICES = [
		(TYPE_ADULT, 'Adult'),
		(TYPE_CHILD, 'Child'),
		(TYPE_INFANT, 'Infant'),
	]

	booking_id = models.ForeignKey('bookings.Booking', on_delete=models.CASCADE, related_name='passengers')
	passenger_type = models.CharField(max_length=8, choices=PASSENGER_TYPE_CHOICES, default=TYPE_ADULT)
	title = models.CharField(max_length=16, null=True, blank=True)
	first_name = models.CharField(max_length=150)
	last_name = models.CharField(max_length=150)
	date_of_birth = models.DateField(null=True, blank=True)
	gender = models.CharField(max_length=16, null=True, blank=True)
	passport_number = models.CharField(max_length=64, null=True, blank=True)
	nationality = models.CharField(max_length=64, null=True, blank=True)

	class Meta:
		db_table = 'pasajeros_reserva'

	def __str__(self):
		return f"{self.first_name} {self.last_name} ({self.passenger_type})"


class HotelBooking(models.Model):
	booking_id = models.ForeignKey('bookings.Booking', on_delete=models.CASCADE, related_name='hotel_bookings')
	hotel_id = models.ForeignKey('hotels.Hotel', on_delete=models.SET_NULL, null=True, blank=True, related_name='hotel_reservations')
	check_in_date = models.DateField()
	check_out_date = models.DateField()
	num_rooms = models.PositiveIntegerField(default=1)
	room_type = models.CharField(max_length=64, null=True, blank=True)
	price_per_night = models.DecimalField(max_digits=10, decimal_places=2, default=Decimal('0.00'))
	total_nights = models.PositiveIntegerField(default=1)
	total_price = models.DecimalField(max_digits=12, decimal_places=2, default=Decimal('0.00'))
	confirmation_number = models.CharField(max_length=128, null=True, blank=True)

	class Meta:
		db_table = 'reservas_hotel'

	def __str__(self):
		return f"HotelBooking {self.confirmation_number or self.id} for {self.booking_id}"


class FlightBooking(models.Model):
	TYPE_OUTBOUND = 'outbound'
	TYPE_RETURN = 'return'

	BOOKING_TYPE_CHOICES = [
		(TYPE_OUTBOUND, 'Outbound'),
		(TYPE_RETURN, 'Return'),
	]

	booking_id = models.ForeignKey('bookings.Booking', on_delete=models.CASCADE, related_name='flight_bookings')
	flight_id = models.ForeignKey('flights.Flight', on_delete=models.SET_NULL, null=True, blank=True, related_name='flight_reservations')
	booking_type = models.CharField(max_length=8, choices=BOOKING_TYPE_CHOICES, default=TYPE_OUTBOUND)
	num_passengers = models.PositiveIntegerField(default=1)
	seat_numbers = models.TextField(null=True, blank=True)
	price_per_person = models.DecimalField(max_digits=10, decimal_places=2, default=Decimal('0.00'))
	total_price = models.DecimalField(max_digits=12, decimal_places=2, default=Decimal('0.00'))
	pnr_number = models.CharField(max_length=128, null=True, blank=True)

	class Meta:
		db_table = 'reservas_vuelo'

	def __str__(self):
		return f"FlightBooking {self.pnr_number or self.id} ({self.booking_id})"
