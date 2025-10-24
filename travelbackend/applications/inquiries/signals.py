# applications/inquiries/signals.py
from django.db.models.signals import post_save
from django.dispatch import receiver
from .models import Inquiry  # solo si lo necesitas

# Ejemplo de señal
@receiver(post_save, sender=Inquiry)
def example_signal(sender, instance, **kwargs):
    print(f"Inquiry guardado: {instance}")
