from rest_framework import viewsets
from rest_framework.permissions import AllowAny
from core.permissions import IsAdminUser
from .models import Inquiry
from .serializers import InquirySerializer


class InquiryViewSet(viewsets.ModelViewSet):
    """
    ViewSet para consultas
    - POST: Público (cualquiera puede enviar consulta)
    - GET/PUT/DELETE: Solo admin
    """
    queryset = Inquiry.objects.all()
    serializer_class = InquirySerializer
    
    def get_permissions(self):
        """
        - create: Público
        - list, retrieve, update, destroy: Solo admin
        """
        if self.action == 'create':
            return [AllowAny()]
        return [IsAdminUser()]