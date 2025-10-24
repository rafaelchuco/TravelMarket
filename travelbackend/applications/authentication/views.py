from rest_framework import generics, viewsets
from rest_framework.permissions import AllowAny, IsAuthenticated, IsAdminUser
from rest_framework.response import Response
from rest_framework import status
from .models import User
from .serializers import UserSerializer, UserRegistrationSerializer, UserUpdateSerializer

class RegisterView(generics.CreateAPIView):
    queryset = User.objects.all()
    serializer_class = UserRegistrationSerializer
    permission_classes = (AllowAny,) 
    
    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        self.perform_create(serializer)
        
        return Response(
            {"detail": "Usuario registrado exitosamente", "user": serializer.data}, 
            status=status.HTTP_201_CREATED
        )


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all().order_by('-created_at')
    permission_classes = (IsAdminUser,)
    def get_serializer_class(self):
        if self.action == 'create':
            return UserRegistrationSerializer
        
        if self.action in ['update', 'partial_update']:
            return UserUpdateSerializer
        
        return UserSerializer


class ProfileView(generics.RetrieveUpdateAPIView):
    permission_classes = (IsAuthenticated,)
    
    def get_object(self):
        return self.request.user

    def get_serializer_class(self):
        if self.request.method == 'GET':
            return UserSerializer 
        
        return UserUpdateSerializer