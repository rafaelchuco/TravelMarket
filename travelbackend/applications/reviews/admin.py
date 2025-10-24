from django.contrib import admin
from .models import Review


@admin.register(Review)
class ReviewAdmin(admin.ModelAdmin):
    list_display = [
        'id',
        'title',
        'customer',
        'package',
        'overall_rating',
        'is_verified',
        'is_approved',
        'created_at'
    ]
    
    list_filter = [
        'is_approved',
        'is_verified',
        'overall_rating',
        'created_at',
    ]
    
    search_fields = [
        'title',
        'comment',
        'customer__username',
        'customer__email',
        'package__name'
    ]
    
    readonly_fields = ['created_at', 'average_rating']
    
    fieldsets = (
        ('Información Básica', {
            'fields': ('booking', 'customer', 'package', 'title')
        }),
        ('Calificaciones', {
            'fields': (
                'overall_rating',
                'accommodation_rating',
                'transport_rating',
                'guide_rating',
                'value_rating',
                'average_rating'
            )
        }),
        ('Contenido', {
            'fields': ('comment', 'pros', 'cons')
        }),
        ('Estado', {
            'fields': ('is_verified', 'is_approved')
        }),
        ('Información Adicional', {
            'fields': ('created_at',)
        }),
    )
    
    actions = ['approve_reviews', 'verify_reviews', 'disapprove_reviews']
    
    def approve_reviews(self, request, queryset):
        updated = queryset.update(is_approved=True)
        self.message_user(request, f'{updated} reseña(s) aprobada(s) exitosamente.')
    approve_reviews.short_description = 'Aprobar reseñas seleccionadas'
    
    def verify_reviews(self, request, queryset):
        updated = queryset.update(is_verified=True)
        self.message_user(request, f'{updated} reseña(s) verificada(s) exitosamente.')
    verify_reviews.short_description = 'Verificar reseñas seleccionadas'
    
    def disapprove_reviews(self, request, queryset):
        updated = queryset.update(is_approved=False)
        self.message_user(request, f'{updated} reseña(s) desaprobada(s) exitosamente.')
    disapprove_reviews.short_description = 'Desaprobar reseñas seleccionadas'
    
    def get_queryset(self, request):
        qs = super().get_queryset(request)
        return qs.select_related('customer', 'package', 'booking')