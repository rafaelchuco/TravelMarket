from django.apps import AppConfig

class InquiriesConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'applications.inquiries'

    def ready(self):
        # Importa las señales solo aquí, después de que la app esté lista
        from . import signals
