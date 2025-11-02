from django.contrib import admin
from .models import Inquiry


@admin.register(Inquiry)
class InquiryAdmin(admin.ModelAdmin):
    list_display = [
        'id',
        'name',
        'email',
        'subject',
        'status',
        'package',
        'created_at',
    ]
    
    list_filter = [
        'status',
        'created_at',
        'package',
    ]
    
    search_fields = [
        'name',
        'email',
        'subject',
        'message',
        'phone',
    ]
    
    readonly_fields = ['created_at', 'updated_at']
    
    fieldsets = (
        ('Información del Cliente', {
            'fields': ('name', 'email', 'phone')
        }),
        ('Consulta', {
            'fields': ('subject', 'message', 'package')
        }),
        ('Gestión', {
            'fields': ('status', 'admin_response')
        }),
        ('Información Adicional', {
            'fields': ('created_at', 'updated_at')
        }),
    )
    
    actions = ['mark_as_in_progress', 'mark_as_responded', 'mark_as_closed']
    
    def mark_as_in_progress(self, request, queryset):
        updated = queryset.update(status='in_progress')
        self.message_user(request, f'{updated} consulta(s) marcada(s) como en progreso.')
    mark_as_in_progress.short_description = 'Marcar como en progreso'
    
    def mark_as_responded(self, request, queryset):
        updated = queryset.update(status='responded')
        self.message_user(request, f'{updated} consulta(s) marcada(s) como respondida(s).')
    mark_as_responded.short_description = 'Marcar como respondida'
    
    def mark_as_closed(self, request, queryset):
        updated = queryset.update(status='closed')
        self.message_user(request, f'{updated} consulta(s) cerrada(s).')
    mark_as_closed.short_description = 'Cerrar consultas'
    
    def get_queryset(self, request):
        qs = super().get_queryset(request)
        return qs.select_related('package')