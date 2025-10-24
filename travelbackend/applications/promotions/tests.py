from django.test import TestCase
from django.utils import timezone
from rest_framework.test import APITestCase, APIClient
from rest_framework import status
from datetime import date, timedelta
from decimal import Decimal

from .models import Coupon


class CouponModelTest(TestCase):
    def setUp(self):
        self.coupon_percentage = Coupon.objects.create(
            code='TEST10',
            description='10% de descuento',
            discount_type='percentage',
            discount_value=10,
            valid_from=date.today(),
            valid_until=date.today() + timedelta(days=30),
            is_active=True
        )
        
        self.coupon_fixed = Coupon.objects.create(
            code='FIXED50',
            description='$50 de descuento',
            discount_type='fixed',
            discount_value=50,
            valid_from=date.today(),
            valid_until=date.today() + timedelta(days=30),
            is_active=True
        )
    
    def test_coupon_creation(self):
        self.assertEqual(self.coupon_percentage.code, 'TEST10')
        self.assertEqual(self.coupon_percentage.discount_type, 'percentage')
    
    def test_percentage_discount_calculation(self):
        purchase = Decimal('1000')
        discount = self.coupon_percentage.calculate_discount(purchase)
        self.assertEqual(discount, Decimal('100'))
    
    def test_fixed_discount_calculation(self):
        purchase = Decimal('1000')
        discount = self.coupon_fixed.calculate_discount(purchase)
        self.assertEqual(discount, Decimal('50'))


class CouponAPITest(APITestCase):
    def setUp(self):
        self.client = APIClient()
        self.coupon = Coupon.objects.create(
            code='TEST20',
            description='20% de descuento',
            discount_type='percentage',
            discount_value=20,
            valid_from=date.today(),
            valid_until=date.today() + timedelta(days=30),
            is_active=True
        )
    
    def test_list_coupons(self):
        response = self.client.get('/api/promotions/coupons/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
    
    def test_validate_coupon(self):
        data = {
            'code': 'TEST20',
            'purchase_amount': 1000
        }
        response = self.client.post('/api/promotions/coupons/validate_coupon/', data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertTrue(response.data['valid'])