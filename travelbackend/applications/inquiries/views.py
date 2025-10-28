from rest_framework import viewsets, status
from rest_framework.response import Response
from rest_framework.permissions import AllowAny, IsAuthenticated
from rest_framework.decorators import action
from core.permissions import IsAdminUser
from .models import Inquiry
from .serializers import InquirySerializer


class InquiryViewSet(viewsets.ModelViewSet):
    """
    ViewSet para consultas
    
    - create: Público (sin login)
    - my_inquiries: Usuario logueado ve solo las suyas
    - list/update/delete: Solo ADMIN
    """
    queryset = Inquiry.objects.all()
    serializer_class = InquirySerializer
    
    def get_permissions(self):
        if self.action == 'create':
            return [AllowAny()]  # ✅ Cualquiera puede crear
        if self.action == 'my_inquiries':
            return [IsAuthenticated()]  # ✅ Solo usuarios logueados
        return [IsAdminUser()]  # ✅ ADMIN para list/update/delete
    
    def list(self, request, *args, **kwargs):
        """Solo para ADMIN - Ver todas las consultas"""
        queryset = self.filter_queryset(self.get_queryset())
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Hay {queryset.count()} consultas',
            'consultas': serializer.data
        })
    
    @action(detail=False, methods=['get'], url_path='my-inquiries')
    def my_inquiries(self, request):
        """Ver SOLO las consultas del usuario logueado (filtradas por email)"""
        user_email = request.user.email
        queryset = Inquiry.objects.filter(email=user_email).order_by('-created_at')
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            'exito': True,
            'mensaje': f'Tienes {queryset.count()} consultas',
            'consultas': serializer.data
        })
    
    def create(self, request, *args, **kwargs):
        """Crear consulta sin autenticación"""
        serializer = self.get_serializer(data=request.data)
        
        if not serializer.is_valid():
            return Response({
                'exito': False,
                'mensaje': 'Error al enviar la consulta',
                'errores': serializer.errors
            }, status=status.HTTP_400_BAD_REQUEST)
        
        self.perform_create(serializer)
        
        return Response({
            'exito': True,
            'mensaje': '¡Tu consulta ha sido enviada! Te responderemos pronto',
            'consulta': serializer.data
        }, status=status.HTTP_201_CREATED)
