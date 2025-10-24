"""
URLs de [NOMBRE_APP]
EQUIPO X: Agregar las rutas aquí
"""
from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import RegisterView, ProfileView, UserViewSet

router = DefaultRouter()
router.register('users', UserViewSet, basename='user')

urlpatterns = [
    path('', include(router.urls)),
    path('register/', RegisterView.as_view(), name='register'),
    path('profile/', ProfileView.as_view(), name='profile-detail')
]
