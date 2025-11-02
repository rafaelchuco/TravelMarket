from django.apps import AppConfig


class ReviewsConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'applications.reviews'
    verbose_name = 'Reseñas'
    
    def ready(self):
        pass