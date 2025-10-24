from django.contrib import admin
from .models import Activity

@admin.register(Activity)
class ActivityAdmin(admin.ModelAdmin):
    list_display = ('name', 'destination', 'activity_type', 'difficulty_level', 'price_per_person', 'is_active')
    list_filter = ('destination', 'activity_type', 'difficulty_level', 'is_active')
    search_fields = ('name', 'description')
