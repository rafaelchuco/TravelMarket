from django.core.management.base import BaseCommand
from django.utils import timezone
from datetime import datetime, timedelta
from applications.packages.models import Category, Package, Itinerary
from applications.destinations.models import Destination


class Command(BaseCommand):
    help = 'Crear datos de prueba para paquetes turísticos'

    def handle(self, *args, **options):
        self.stdout.write('Creando datos de prueba...')
        
        # Crear categorías
        categories_data = [
            {
                'name': 'Aventura',
                'description': 'Paquetes de aventura y deportes extremos',
                'icon': 'adventure'
            },
            {
                'name': 'Cultural',
                'description': 'Tours culturales y históricos',
                'icon': 'culture'
            },
            {
                'name': 'Relax',
                'description': 'Paquetes de relajación y spa',
                'icon': 'relax'
            },
            {
                'name': 'Romántico',
                'description': 'Escapadas románticas para parejas',
                'icon': 'romantic'
            },
            {
                'name': 'Familiar',
                'description': 'Paquetes ideales para familias',
                'icon': 'family'
            }
        ]
        
        categories = []
        for cat_data in categories_data:
            category, created = Category.objects.get_or_create(
                name=cat_data['name'],
                defaults=cat_data
            )
            categories.append(category)
            if created:
                self.stdout.write(f'Categoría creada: {category.name}')
        
        # Crear destinos
        destinations_data = [
            {
                'name': 'París',
                'country': 'Francia',
                'city': 'París',
                'description': 'La ciudad de la luz, famosa por su arte, moda y gastronomía',
                'short_description': 'La ciudad más romántica del mundo'
            },
            {
                'name': 'Tokio',
                'country': 'Japón',
                'city': 'Tokio',
                'description': 'Una mezcla perfecta de tradición y modernidad',
                'short_description': 'La capital del futuro'
            },
            {
                'name': 'Nueva York',
                'country': 'Estados Unidos',
                'city': 'Nueva York',
                'description': 'La ciudad que nunca duerme, llena de energía y oportunidades',
                'short_description': 'La gran manzana'
            },
            {
                'name': 'Roma',
                'country': 'Italia',
                'city': 'Roma',
                'description': 'La ciudad eterna, cuna de la civilización occidental',
                'short_description': 'La ciudad eterna'
            },
            {
                'name': 'Londres',
                'country': 'Reino Unido',
                'city': 'Londres',
                'description': 'Una ciudad rica en historia y cultura',
                'short_description': 'La ciudad de los reyes'
            }
        ]
        
        destinations = []
        for dest_data in destinations_data:
            destination, created = Destination.objects.get_or_create(
                name=dest_data['name'],
                defaults=dest_data
            )
            destinations.append(destination)
            if created:
                self.stdout.write(f'Destino creado: {destination.name}')
        
        # Crear paquetes
        packages_data = [
            {
                'name': 'París Romántico - 3 Días',
                'category': categories[3],  # Romántico
                'destination': destinations[0],  # París
                'description': 'Una escapada romántica perfecta en la ciudad de la luz. Incluye visitas a los lugares más emblemáticos y cenas románticas.',
                'short_description': 'Escapada romántica de 3 días en París',
                'duration_days': 3,
                'duration_nights': 2,
                'price_adult': 1200.00,
                'price_child': 800.00,
                'max_people': 2,
                'min_people': 2,
                'includes': ['hotel', 'meals', 'transport', 'guide'],
                'is_featured': True,
                'available_from': datetime.now().date(),
                'available_until': datetime.now().date() + timedelta(days=365)
            },
            {
                'name': 'Aventura en Tokio - 5 Días',
                'category': categories[0],  # Aventura
                'destination': destinations[1],  # Tokio
                'description': 'Descubre la cultura japonesa con actividades emocionantes y visitas a templos tradicionales.',
                'short_description': 'Aventura cultural de 5 días en Tokio',
                'duration_days': 5,
                'duration_nights': 4,
                'price_adult': 1800.00,
                'price_child': 1200.00,
                'max_people': 8,
                'min_people': 2,
                'includes': ['hotel', 'meals', 'transport', 'guide'],
                'is_featured': True,
                'available_from': datetime.now().date(),
                'available_until': datetime.now().date() + timedelta(days=365)
            },
            {
                'name': 'Nueva York Express - 4 Días',
                'category': categories[2],  # Relax
                'destination': destinations[2],  # Nueva York
                'description': 'Conoce los lugares más icónicos de Nueva York con un ritmo relajado.',
                'short_description': 'Tour relajado de 4 días por Nueva York',
                'duration_days': 4,
                'duration_nights': 3,
                'price_adult': 1500.00,
                'price_child': 1000.00,
                'max_people': 6,
                'min_people': 1,
                'includes': ['hotel', 'meals', 'transport'],
                'is_featured': False,
                'available_from': datetime.now().date(),
                'available_until': datetime.now().date() + timedelta(days=365)
            },
            {
                'name': 'Roma Cultural - 6 Días',
                'category': categories[1],  # Cultural
                'destination': destinations[3],  # Roma
                'description': 'Sumérgete en la historia de Roma con visitas guiadas a los monumentos más importantes.',
                'short_description': 'Tour cultural de 6 días por Roma',
                'duration_days': 6,
                'duration_nights': 5,
                'price_adult': 1600.00,
                'price_child': 1100.00,
                'max_people': 10,
                'min_people': 2,
                'includes': ['hotel', 'meals', 'transport', 'guide'],
                'is_featured': True,
                'available_from': datetime.now().date(),
                'available_until': datetime.now().date() + timedelta(days=365)
            },
            {
                'name': 'Londres Familiar - 5 Días',
                'category': categories[4],  # Familiar
                'destination': destinations[4],  # Londres
                'description': 'Perfecto para familias con niños, incluye actividades divertidas y educativas.',
                'short_description': 'Vacaciones familiares de 5 días en Londres',
                'duration_days': 5,
                'duration_nights': 4,
                'price_adult': 1400.00,
                'price_child': 900.00,
                'max_people': 6,
                'min_people': 3,
                'includes': ['hotel', 'meals', 'transport', 'guide'],
                'is_featured': False,
                'available_from': datetime.now().date(),
                'available_until': datetime.now().date() + timedelta(days=365)
            }
        ]
        
        packages = []
        for pkg_data in packages_data:
            package, created = Package.objects.get_or_create(
                name=pkg_data['name'],
                defaults=pkg_data
            )
            packages.append(package)
            if created:
                self.stdout.write(f'Paquete creado: {package.name}')
        
        # Crear itinerarios para cada paquete
        itineraries_data = [
            # París Romántico - 3 Días
            {
                'package': packages[0],
                'day_number': 1,
                'title': 'Llegada y Torre Eiffel',
                'description': 'Llegada a París, check-in en hotel y visita a la Torre Eiffel al atardecer.',
                'activities': ['Check-in hotel', 'Visita Torre Eiffel', 'Cena romántica en restaurante'],
                'meals_included': ['desayuno', 'cena']
            },
            {
                'package': packages[0],
                'day_number': 2,
                'title': 'Louvre y Montmartre',
                'description': 'Visita al Museo del Louvre por la mañana y paseo por Montmartre por la tarde.',
                'activities': ['Museo del Louvre', 'Paseo por Montmartre', 'Sacré-Cœur'],
                'meals_included': ['desayuno', 'almuerzo', 'cena']
            },
            {
                'package': packages[0],
                'day_number': 3,
                'title': 'Notre Dame y Champs-Élysées',
                'description': 'Visita a Notre Dame y paseo por los Champs-Élysées antes de la partida.',
                'activities': ['Notre Dame', 'Champs-Élysées', 'Arco del Triunfo'],
                'meals_included': ['desayuno']
            },
            # Aventura en Tokio - 5 Días
            {
                'package': packages[1],
                'day_number': 1,
                'title': 'Llegada y Asakusa',
                'description': 'Llegada a Tokio y visita al templo Senso-ji en Asakusa.',
                'activities': ['Check-in hotel', 'Templo Senso-ji', 'Mercado Nakamise'],
                'meals_included': ['desayuno', 'cena']
            },
            {
                'package': packages[1],
                'day_number': 2,
                'title': 'Shibuya y Harajuku',
                'description': 'Exploración de los barrios más modernos de Tokio.',
                'activities': ['Cruce de Shibuya', 'Harajuku', 'Meiji Shrine'],
                'meals_included': ['desayuno', 'almuerzo', 'cena']
            },
            {
                'package': packages[1],
                'day_number': 3,
                'title': 'Palacio Imperial y Ginza',
                'description': 'Visita al Palacio Imperial y compras en Ginza.',
                'activities': ['Palacio Imperial', 'Ginza', 'Tsukiji Fish Market'],
                'meals_included': ['desayuno', 'almuerzo', 'cena']
            },
            {
                'package': packages[1],
                'day_number': 4,
                'title': 'Monte Fuji',
                'description': 'Excursión de día completo al Monte Fuji.',
                'activities': ['Monte Fuji', 'Lago Kawaguchi', 'Templo Chureito'],
                'meals_included': ['desayuno', 'almuerzo', 'cena']
            },
            {
                'package': packages[1],
                'day_number': 5,
                'title': 'Akihabara y Partida',
                'description': 'Visita a Akihabara, el distrito de la electrónica, antes de la partida.',
                'activities': ['Akihabara', 'Compras de souvenirs'],
                'meals_included': ['desayuno']
            }
        ]
        
        for itin_data in itineraries_data:
            itinerary, created = Itinerary.objects.get_or_create(
                package=itin_data['package'],
                day_number=itin_data['day_number'],
                defaults=itin_data
            )
            if created:
                self.stdout.write(f'Itinerario creado: {itinerary.package.name} - Día {itinerary.day_number}')
        
        self.stdout.write(
            self.style.SUCCESS('¡Datos de prueba creados exitosamente!')
        )
        self.stdout.write(f'Categorías: {Category.objects.count()}')
        self.stdout.write(f'Destinos: {Destination.objects.count()}')
        self.stdout.write(f'Paquetes: {Package.objects.count()}')
        self.stdout.write(f'Itinerarios: {Itinerary.objects.count()}')
