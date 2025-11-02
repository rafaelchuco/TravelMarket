from django.contrib import admin
from django.urls import path, include
from rest_framework import permissions
from drf_yasg.views import get_schema_view
from drf_yasg import openapi

schema_view = get_schema_view(
    openapi.Info(
        title="Agencia de Turismo API",
        default_version='v1',
        description="Documentación de la API de Agencia de Turismo",
        terms_of_service="https://www.google.com/policies/terms/",
        contact=openapi.Contact(email="contacto@agencia.com"),
        license=openapi.License(name="MIT License"),
    ),
    public=True,
    permission_classes=(permissions.AllowAny,),
)

urlpatterns = [
     # Grappelli primero
    path('grappelli/', include('grappelli.urls')),

    # Admin de Django
    path('admin/', admin.site.urls),
    path('api/auth/', include('applications.authentication.urls')),
    path('api/destinations/', include('applications.destinations.urls')),
    path('api/packages/', include('applications.packages.urls')),
    path('api/hotels/', include('applications.hotels.urls')),
    path('api/flights/', include('applications.flights.urls')),
    path('api/activities/', include('applications.activities.urls')),
    path('api/bookings/', include('applications.bookings.urls')),
    path('api/reviews/', include('applications.reviews.urls')),
    path('api/promotions/', include('applications.promotions.urls')),
    path('api/inquiries/', include('applications.inquiries.urls')),

    # Swagger/OpenAPI
    path('swagger.json/', schema_view.without_ui(cache_timeout=0), name='schema-json'),
    path('swagger/', schema_view.with_ui('swagger', cache_timeout=0), name='schema-swagger-ui'),
    path('redoc/', schema_view.with_ui('redoc', cache_timeout=0), name='schema-redoc'),
]