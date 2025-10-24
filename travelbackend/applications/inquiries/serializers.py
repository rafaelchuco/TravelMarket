from rest_framework import serializers
from .models import Inquiry


class InquirySerializer(serializers.ModelSerializer):
    class Meta:
        model = Inquiry
        fields = [
            'id', 'name', 'email', 'phone', 'subject',
            'message', 'package', 'status', 'admin_response',
            'created_at', 'updated_at'
        ]
        read_only_fields = ['id', 'status', 'admin_response', 'created_at', 'updated_at']
