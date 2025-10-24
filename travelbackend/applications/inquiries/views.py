from rest_framework import viewsets
from .models import Inquiry
from .serializers import InquirySerializer


class InquiryViewSet(viewsets.ModelViewSet):
    """ViewSet para consultas"""
    queryset = Inquiry.objects.all()
    serializer_class = InquirySerializer