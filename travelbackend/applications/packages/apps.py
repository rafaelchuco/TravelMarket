from django.apps import AppConfig


class PackagesConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'applications.packages'
    verbose_name = 'Paquetes'
    
    def ready(self):
        pass