from django.core.management.base import BaseCommand
from django.contrib.auth import get_user_model
from rest_framework.test import APIClient
from applications.packages.models import Package
from applications.hotels.models import Hotel
from applications.flights.models import Flight
import datetime


class Command(BaseCommand):
    help = 'Create test data and POST 3 bookings via API'

    def handle(self, *args, **options):
        User = get_user_model()
        client = APIClient()

        # Ensure test client uses a host allowed by Django settings
        client.defaults['HTTP_HOST'] = 'localhost'

        # create or reuse test user and ensure password is set
        user = User.objects.filter(username='testuser').first()
        if not user:
            user = User.objects.create_user(username='testuser', email='test@example.com', password='password')

        # authenticate the test client directly (avoid session login issues)
        client.force_authenticate(user=user)

        # create supporting objects
        package, _ = Package.objects.get_or_create(name='Test Package')
        hotel, _ = Hotel.objects.get_or_create(name='Test Hotel')
        flight, _ = Flight.objects.get_or_create(flight_number='TST123')

        base_payload = {
            'customer_id': user.id,
            'package_id': package.id,
            'travel_date': (datetime.date.today() + datetime.timedelta(days=10)).isoformat(),
            'return_date': (datetime.date.today() + datetime.timedelta(days=15)).isoformat(),
            'num_adults': 2,
            'num_children': 0,
            'num_infants': 0,
            'discount_amount': '0.00',
            'tax_amount': '0.00',
        }

        for i in range(1, 4):
            payload = base_payload.copy()
            payload['hotel_bookings'] = [{
                'hotel_id': hotel.id,
                'check_in_date': (datetime.date.today() + datetime.timedelta(days=10)).isoformat(),
                'check_out_date': (datetime.date.today() + datetime.timedelta(days=12)).isoformat(),
                'num_rooms': 1,
                'total_nights': 2,
                'total_price': '200.00'
            }]
            payload['flight_bookings'] = [{
                'flight_id': flight.id,
                'booking_type': 'outbound',
                'num_passengers': 2,
                'price_per_person': '100.00',
                'total_price': '200.00'
            }]
            payload['passengers'] = [{'passenger_type': 'adult', 'first_name': f'User{i}', 'last_name': 'Test'}]

            resp = client.post('/api/v1/bookings/', payload, format='json')
            # resp may be a DRF Response-like object or a Django HttpResponse
            try:
                data = resp.data
            except Exception:

                try:
                    data = resp.json()
                except Exception:
                    data = resp.content.decode('utf-8', errors='replace')

            self.stdout.write(f'POST {i} -> status {resp.status_code} | {data}')


