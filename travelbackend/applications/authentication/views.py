from rest_framework import generics, viewsets, status
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.permissions import AllowAny, IsAuthenticated
from django.contrib.auth import get_user_model
from rest_framework_simplejwt.views import TokenObtainPairView
from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
from core.permissions import IsAdminUser
from .serializers import (
    UserSerializer, 
    UserRegistrationSerializer, 
    UserUpdateSerializer
)

User = get_user_model()


class CustomTokenObtainPairSerializer(TokenObtainPairSerializer):
    """Serializer personalizado para incluir datos del usuario"""
    
    def validate(self, attrs):
        data = super().validate(attrs)
        
        # Agregar información del usuario al response
        data['usuario'] = {
            'id': self.user.id,
            'username': self.user.username,
            'email': self.user.email,
            'nombre_completo': f"{self.user.first_name} {self.user.last_name}",
            'tipo_usuario': self.user.user_type,
        }
        
        return data


class LoginView(TokenObtainPairView):
    """POST /api/v1/auth/login/ - Login con JWT"""
    serializer_class = CustomTokenObtainPairSerializer
    permission_classes = [AllowAny]
    
    def post(self, request, *args, **kwargs):
        try:
            response = super().post(request, *args, **kwargs)
            return Response({
                'exito': True,
                'mensaje': '¡Bienvenido de nuevo!',
                'access': response.data.get('access'),
                'refresh': response.data.get('refresh'),
                'usuario': response.data.get('usuario')
            })
        except Exception as e:
            return Response({
                'exito': False,
                'mensaje': 'Usuario o contraseña incorrectos',
                'detalles': 'Verifica tus credenciales e intenta nuevamente'
            }, status=status.HTTP_401_UNAUTHORIZED)


class RegisterView(generics.CreateAPIView):
    """POST /api/v1/auth/register/ - Registro público"""
    queryset = User.objects.all()
    serializer_class = UserRegistrationSerializer
    permission_classes = [AllowAny]
    
    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        
        if not serializer.is_valid():
            return Response({
                'exito': False,
                'mensaje': 'Por favor verifica los datos ingresados',
                'errores': serializer.errors
            }, status=status.HTTP_400_BAD_REQUEST)
        
        user = serializer.save()
        
        return Response({
            'exito': True,
            'mensaje': '¡Registro exitoso! Tu cuenta ha sido creada',
            'usuario': UserSerializer(user).data
        }, status=status.HTTP_201_CREATED)


class UserViewSet(viewsets.ModelViewSet):
    """ViewSet para CRUD de usuarios"""
    queryset = User.objects.all()
    serializer_class = UserSerializer
    
    def get_permissions(self):
        if self.action in ['list', 'create', 'update', 'partial_update', 'destroy', 'retrieve']:
            return [IsAdminUser()]
        return [IsAuthenticated()]
    
    def get_queryset(self):
        queryset = User.objects.all()
        
        user_type = self.request.query_params.get('user_type', None)
        if user_type:
            queryset = queryset.filter(user_type=user_type)
        
        is_active = self.request.query_params.get('is_active', None)
        if is_active is not None:
            queryset = queryset.filter(is_active=is_active)
        
        country = self.request.query_params.get('country', None)
        if country:
            queryset = queryset.filter(country__icontains=country)
        
        return queryset
    
    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        page = self.paginate_queryset(queryset)
        
        if page is not None:
            serializer = self.get_serializer(page, many=True)
            return self.get_paginated_response({
                'exito': True,
                'mensaje': f'Se encontraron {queryset.count()} usuarios',
                'usuarios': serializer.data
            })
        
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Se encontraron {queryset.count()} usuarios',
            'usuarios': serializer.data
        })
    
    @action(detail=False, methods=['get'], permission_classes=[IsAuthenticated])
    def me(self, request):
        """GET /api/v1/auth/users/me/ - Perfil del usuario autenticado"""
        serializer = self.get_serializer(request.user)
        return Response({
            'exito': True,
            'mensaje': 'Perfil obtenido correctamente',
            'usuario': serializer.data
        })
    
    @action(detail=False, methods=['put', 'patch'], permission_classes=[IsAuthenticated])
    def update_profile(self, request):
        """PUT/PATCH /api/v1/auth/users/update_profile/ - Actualizar perfil"""
        serializer = UserUpdateSerializer(
            request.user,
            data=request.data,
            partial=True
        )
        
        if not serializer.is_valid():
            return Response({
                'exito': False,
                'mensaje': 'Error al actualizar el perfil',
                'errores': serializer.errors
            }, status=status.HTTP_400_BAD_REQUEST)
        
        serializer.save()
        
        return Response({
            'exito': True,
            'mensaje': 'Tu perfil ha sido actualizado exitosamente',
            'usuario': UserSerializer(request.user).data
        })
    
    def destroy(self, request, *args, **kwargs):
        """DELETE /api/v1/auth/users/{id}/ - Desactivar usuario"""
        instance = self.get_object()
        
        if not instance.is_active:
            return Response({
                'exito': False,
                'mensaje': 'Este usuario ya está desactivado'
            }, status=status.HTTP_400_BAD_REQUEST)
        
        instance.is_active = False
        instance.save()
        
        return Response({
            'exito': True,
            'mensaje': 'Usuario desactivado exitosamente'
        })
