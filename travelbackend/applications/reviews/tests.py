from django.test import TestCase
from django.contrib.auth import get_user_model
from rest_framework.test import APITestCase, APIClient
from rest_framework import status

from .models import Review

User = get_user_model()


class ReviewModelTest(TestCase):
    def setUp(self):
        self.user = User.objects.create_user(
            username='testuser',
            email='test@example.com',
            password='testpass123'
        )
    
    def test_review_creation(self):
        pass


class ReviewAPITest(APITestCase):
    def setUp(self):
        self.client = APIClient()
        self.user = User.objects.create_user(
            username='testuser',
            email='test@example.com',
            password='testpass123'
        )
    
    def test_list_reviews(self):
        response = self.client.get('/api/reviews/reviews/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
    
    def test_create_review_unauthenticated(self):
        data = {
            'booking': 1,
            'package': 1,
            'overall_rating': 5,
            'title': 'Test',
            'comment': 'Test comment'
        }
        
        response = self.client.post('/api/reviews/reviews/', data)
        self.assertIn(
            response.status_code,
            [status.HTTP_401_UNAUTHORIZED, status.HTTP_403_FORBIDDEN]
        )