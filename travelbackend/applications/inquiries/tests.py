from django.test import TestCase
from django.contrib.auth import get_user_model
from rest_framework.test import APITestCase, APIClient
from rest_framework import status

from .models import Inquiry

User = get_user_model()


class InquiryModelTest(TestCase):
    def setUp(self):
        self.inquiry = Inquiry.objects.create(
            name='Juan Pérez',
            email='juan@example.com',
            phone='123456789',
            subject='Consulta sobre viaje',
            message='Me gustaría obtener más información sobre los paquetes disponibles',
            status='new'
        )
    
    def test_inquiry_creation(self):
        self.assertEqual(self.inquiry.name, 'Juan Pérez')
        self.assertEqual(self.inquiry.status, 'new')
    
    def test_inquiry_str(self):
        expected = f"{self.inquiry.subject} - {self.inquiry.name} (Nueva)"
        self.assertEqual(str(self.inquiry), expected)


class InquiryAPITest(APITestCase):
    def setUp(self):
        self.client = APIClient()
        self.staff_user = User.objects.create_user(
            username='staff',
            email='staff@example.com',
            password='staffpass123',
            is_staff=True
        )
    
    def test_create_inquiry_anonymous(self):
        data = {
            'name': 'María González',
            'email': 'maria@example.com',
            'phone': '987654321',
            'subject': 'Pregunta sobre paquetes',
            'message': 'Quisiera información sobre paquetes a Europa'
        }
        
        response = self.client.post('/api/inquiries/inquiries/', data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
    
    def test_list_inquiries_as_staff(self):
        self.client.force_authenticate(user=self.staff_user)
        response = self.client.get('/api/inquiries/inquiries/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
    
    def test_list_inquiries_anonymous(self):
        response = self.client.get('/api/inquiries/inquiries/')
        self.assertIn(
            response.status_code,
            [status.HTTP_401_UNAUTHORIZED, status.HTTP_403_FORBIDDEN]
        )