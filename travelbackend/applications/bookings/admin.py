from django.contrib import admin

from . import models


class PassengerInline(admin.TabularInline):
	model = models.Passenger
	extra = 0


@admin.register(models.Booking)
class BookingAdmin(admin.ModelAdmin):
	inlines = [PassengerInline]
	list_display = ('booking_number', 'customer_id', 'total_amount', 'status', 'payment_status', 'booking_date')
	list_filter = ('status', 'booking_date')
	search_fields = ('booking_number',)


@admin.register(models.Passenger)
class PassengerAdmin(admin.ModelAdmin):
	list_display = ('first_name', 'last_name', 'passenger_type', 'booking_id')
	search_fields = ('first_name', 'last_name', 'passport_number')

