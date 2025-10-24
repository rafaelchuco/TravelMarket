from django.db import models
from applications.packages.models import Package


class Inquiry(models.Model):
    """
    Consultas de clientes
    """
    
    STATUS_CHOICES = (
        ('new', 'Nueva'),
        ('in_progress', 'En progreso'),
        ('responded', 'Respondida'),
        ('closed', 'Cerrada'),
    )
    
    name = models.CharField(
        max_length=200,
        verbose_name='Nombre'
    )
    
    email = models.EmailField(
        verbose_name='Email'
    )
    
    phone = models.CharField(
        max_length=20,
        blank=True,
        null=True,
        verbose_name='Teléfono'
    )
    
    subject = models.CharField(
        max_length=200,
        verbose_name='Asunto'
    )
    
    message = models.TextField(
        verbose_name='Mensaje'
    )
    
    package = models.ForeignKey(
        Package,
        on_delete=models.SET_NULL,
        blank=True,
        null=True,
        related_name='inquiries',
        verbose_name='Paquete relacionado'
    )
    
    status = models.CharField(
        max_length=20,
        choices=STATUS_CHOICES,
        default='new',
        verbose_name='Estado'
    )
    
    admin_response = models.TextField(
        blank=True,
        null=True,
        verbose_name='Respuesta del administrador'
    )
    
    created_at = models.DateTimeField(
        auto_now_add=True,
        verbose_name='Fecha de creación'
    )
    
    updated_at = models.DateTimeField(
        auto_now=True,
        verbose_name='Última actualización'
    )
    
    class Meta:
        db_table = 'consultas'
        verbose_name = 'Consulta'
        verbose_name_plural = 'Consultas'
        ordering = ['-created_at']
    
    def __str__(self):
        return f"{self.subject} - {self.name}"