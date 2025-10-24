from django.contrib.auth.password_validation import validate_password
from django.db import IntegrityError
from rest_framework import serializers
from .models import User

class UserSerializer(serializers.ModelSerializer):
    created_at = serializers.DateTimeField(read_only=True)
    updated_at = serializers.DateTimeField(read_only=True)

    class Meta:
        model = User
        fields = ('id', 'username', 'email', 'first_name', 'last_name', 'phone', 'nationality', 'passport_number', 'address', 'city', 'country', 'user_type', 'is_active', 'is_staff', 'is_superuser', 'last_login', 'date_joined', 'created_at', 'updated_at')

        read_only_fields = fields


class UserRegistrationSerializer(serializers.ModelSerializer):
    password = serializers.CharField(style={'input_type': 'password'}, write_only=True)

    password_confirm = serializers.CharField(style={'input_type': 'password'}, write_only=True) # Campo temporal para confirmar contraseña

    class Meta:
        model = User
        fields = ('username', 'email', 'password', 'password_confirm', 'first_name', 'last_name', 'phone', 'nationality', 'passport_number', 'address', 'city', 'country')

    def validate(self, data):
        if data['password'] != data.get('password_confirm'):
            raise serializers.ValidationError({"password_confirm": "Las contraseñas ingresadas no son iguales"}) # Comparación de contraseñas
        
        data.pop('password_confirm')
        validate_password(data['password'], user=User(**data)) # Validación de contraseña en base a reglas
        return data

    def create(self, validated_data):
        validated_data['user_type'] = 'customer'  # Rol por defecto (cliente)
        try:
            user = User.objects.create_user(**validated_data)
            return user
        except IntegrityError:
            raise serializers.ValidationError({"detail": "Usuario o email ya registrado"})
        

class UserUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = User

        fields = (
            'first_name', 
            'last_name', 
            'phone', 
            'address', 
            'city', 
            'country', 
            'passport_number'
        )
        
        read_only_fields = ('username', 'email', 'user_type', 'is_active', 'nationality')