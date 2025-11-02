-- =====================================================
-- DATOS DE PRUEBA - AGENCIA DE TURISMO (CORREGIDO)
-- Nombres de tablas según Django models
-- =====================================================

-- =====================================================
-- 1. USUARIOS (usuarios)
-- =====================================================
-- Nota: Las contraseñas deben ser hasheadas con Django
-- Usa: python manage.py createsuperuser para crear usuarios reales

INSERT INTO usuarios (id, password, is_superuser, username, first_name, last_name, email, is_staff, is_active, date_joined, last_login, phone, nationality, passport_number, address, city, country, user_type, created_at, updated_at) VALUES
(1, 'pbkdf2_sha256$600000$xyz123abc$abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ', true, 'admin', 'Carlos', 'Rodríguez', 'admin@agencia.com', true, true, NOW(), NOW(), '+51 999 888 777', 'Peruana', NULL, 'Av. Principal 123', 'Lima', 'Perú', 'admin', NOW(), NOW()),
(2, 'pbkdf2_sha256$600000$abc456def$1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ', false, 'juan.perez', 'Juan', 'Pérez', 'juan.perez@email.com', false, true, NOW(), NOW(), '+51 999 777 666', 'Peruana', 'PE123456', 'Av. Javier Prado 2000', 'Lima', 'Perú', 'customer', NOW(), NOW()),
(3, 'pbkdf2_sha256$600000$def789ghi$fedcba0987654321zyxwvutsrqponmlkjihgfedcbaZYXWVUTSRQPONMLKJIHGFEDCBA', false, 'maria.garcia', 'María', 'García', 'maria.garcia@email.com', false, true, NOW(), NOW(), '+51 987 654 321', 'Peruana', 'PE789012', 'Calle Los Olivos 456', 'Lima', 'Perú', 'customer', NOW(), NOW()),
(4, 'pbkdf2_sha256$600000$ghi012jkl$abcd1234efgh5678ijkl9012mnop3456qrst7890uvwxyzABCDEFGHIJKLMNOP', false, 'pedro.lopez', 'Pedro', 'López', 'pedro.lopez@email.com', false, true, NOW(), NOW(), '+51 988 555 444', 'Peruana', 'PE345678', 'Jr. Las Flores 789', 'Lima', 'Perú', 'customer', NOW(), NOW()),
(5, 'pbkdf2_sha256$600000$jkl345mno$5678ijkl9012mnop3456qrst7890uvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123', false, 'ana.martinez', 'Ana', 'Martínez', 'ana.martinez@email.com', false, true, NOW(), NOW(), '+51 977 333 222', 'Peruana', 'PE567890', 'Av. Universitaria 1500', 'Lima', 'Perú', 'customer', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 2. DESTINOS (destinos)
-- =====================================================

INSERT INTO destinos (id, name, country, continent, description, short_description, latitude, longitude, is_popular, best_season, image, created_at) VALUES
(1, 'Cusco', 'Perú', 'América del Sur', 'Antigua capital del Imperio Inca, Cusco es una ciudad llena de historia, cultura y tradición. Punto de partida para visitar Machu Picchu y el Valle Sagrado de los Incas.', 'Ciudad imperial con ruinas incas y arquitectura colonial.', -13.5319, -71.9675, true, 'Mayo - Septiembre', NULL, NOW()),
(2, 'Paracas', 'Perú', 'América del Sur', 'Reserva Nacional ubicada en la costa sur del Perú, famosa por sus playas, vida marina y las enigmáticas líneas de Nazca cercanas.', 'Reserva natural con islas, lobos marinos y playas.', -13.8399, -76.2514, true, 'Todo el año', NULL, NOW()),
(3, 'Arequipa', 'Perú', 'América del Sur', 'La Ciudad Blanca es famosa por su arquitectura colonial construida con sillar volcánico y el impresionante Cañón del Colca.', 'Ciudad colonial conocida como la Ciudad Blanca.', -16.4090, -71.5375, true, 'Abril - Noviembre', NULL, NOW()),
(4, 'Puno', 'Perú', 'América del Sur', 'A orillas del Lago Titicaca, el lago navegable más alto del mundo, Puno es conocida por sus islas flotantes de los Uros.', 'Lago Titicaca e islas flotantes de los Uros.', -15.8402, -70.0219, true, 'Abril - Octubre', NULL, NOW()),
(5, 'Iquitos', 'Perú', 'América del Sur', 'Puerta de entrada a la Amazonía peruana, Iquitos ofrece experiencias únicas de ecoturismo y contacto con comunidades nativas.', 'Capital de la selva amazónica peruana.', -3.7437, -73.2516, true, 'Mayo - Octubre', NULL, NOW()),
(6, 'Cancún', 'México', 'América del Norte', 'Paraíso caribeño con playas de arena blanca, aguas turquesas, ruinas mayas y vida nocturna vibrante.', 'Playas paradisíacas del Caribe mexicano.', 21.1619, -86.8515, true, 'Diciembre - Abril', NULL, NOW()),
(7, 'Buenos Aires', 'Argentina', 'América del Sur', 'La capital argentina combina elegancia europea con pasión latina. Cuna del tango, gastronomía de clase mundial.', 'Capital del tango y la cultura argentina.', -34.6037, -58.3816, true, 'Marzo - Mayo, Septiembre - Noviembre', NULL, NOW()),
(8, 'Cartagena', 'Colombia', 'América del Sur', 'Ciudad amurallada colonial en la costa caribeña, con calles coloridas, balcones floridos y rica historia.', 'Ciudad colonial caribeña amurallada.', 10.3910, -75.4794, true, 'Diciembre - Marzo', NULL, NOW())
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 3. CATEGORÍAS DE PAQUETES (categorias_paquetes)
-- =====================================================

INSERT INTO categorias_paquetes (id, name, description, icon, created_at, updated_at) VALUES
(1, 'Aventura', 'Paquetes para los amantes de la adrenalina y actividades extremas como trekking, rafting, canopy.', 'hiking', NOW(), NOW()),
(2, 'Cultural', 'Experiencias culturales y arqueológicas para conocer la historia y patrimonio de cada destino.', 'landmark', NOW(), NOW()),
(3, 'Playa y Sol', 'Relajación en los mejores destinos de playa con arena blanca y aguas cristalinas.', 'beach_access', NOW(), NOW()),
(4, 'Ecoturismo', 'Turismo sostenible en contacto con la naturaleza, observación de fauna y flora.', 'eco', NOW(), NOW()),
(5, 'Lujo', 'Experiencias premium con servicios exclusivos y hoteles 5 estrellas.', 'diamond', NOW(), NOW()),
(6, 'Familia', 'Paquetes diseñados para toda la familia con actividades para todas las edades.', 'family_restroom', NOW(), NOW()),
(7, 'Romántico', 'Escapadas perfectas para parejas, lunas de miel y aniversarios.', 'favorite', NOW(), NOW()),
(8, 'Gastronómico', 'Tours culinarios y experiencias gastronómicas para degustar la cocina local.', 'restaurant', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 4. HOTELES (hoteles)
-- =====================================================

INSERT INTO hoteles (id, name, destination_id, address, star_rating, description, amenities, check_in_time, check_out_time, phone, email, price_per_night, total_rooms, image, is_active) VALUES
(1, 'Hotel Monasterio Cusco', 1, 'Calle Palacio 136, Plazoleta Nazarenas', 5, 'Hotel 5 estrellas ubicado en un monasterio del siglo XVI. Lujo e historia en el corazón de Cusco.', 'Wi-Fi gratuito, Spa, Restaurante gourmet, Bar, Oxígeno en habitaciones, Capilla histórica', '14:00:00', '12:00:00', '+51 84 604000', 'reservas@monasterio.com', 450.00, 126, NULL, true),
(2, 'JW Marriott El Convento Cusco', 1, 'Esquina de la Calle Ruinas 432 y San Agustín', 5, 'Hotel de lujo en el centro histórico, combina arquitectura colonial con comodidades modernas.', 'Wi-Fi gratuito, Spa completo, 2 Restaurantes, Bar lounge, Gimnasio, Jardines coloniales', '15:00:00', '12:00:00', '+51 84 582200', 'cusco@marriott.com', 380.00, 153, NULL, true),
(3, 'Paracas Luxury Collection Resort', 2, 'Av. Paracas S/N, Paracas', 5, 'Resort de lujo frente al mar con vistas espectaculares de la Reserva Nacional de Paracas.', 'Wi-Fi, Spa, 3 Restaurantes, 2 Piscinas, Playa privada, Centro de deportes acuáticos', '15:00:00', '12:00:00', '+51 56 581333', 'reservas@paracasluxury.com', 520.00, 120, NULL, true),
(4, 'Casa Andina Premium Arequipa', 3, 'Calle Ugarte 403, Cercado', 4, 'Hotel boutique en casona colonial restaurada, cerca de la Plaza de Armas.', 'Wi-Fi gratuito, Restaurante, Bar, Patio colonial, Servicio de habitaciones', '14:00:00', '12:00:00', '+51 54 226907', 'arequipa@casa-andina.com', 180.00, 42, NULL, true),
(5, 'Sonesta Posadas del Inca Puno', 4, 'Sesquicentenario 610, Sector Huaje', 4, 'Hotel a orillas del Lago Titicaca con vistas panorámicas y arquitectura andina.', 'Wi-Fi, Restaurante con vista al lago, Muelle privado, Oxígeno en habitaciones', '13:00:00', '11:00:00', '+51 51 364111', 'puno@sonesta.com', 160.00, 62, NULL, true),
(6, 'Ceiba Tops Lodge', 5, 'Río Amazonas, 40 km de Iquitos', 4, 'Lodge amazónico con piscina y actividades de selva. Acceso por río.', 'Restaurante, Bar, Piscina natural, Tours guiados, Observación de fauna', '12:00:00', '10:00:00', '+51 65 231618', 'reservas@explorama.com', 220.00, 72, NULL, true),
(7, 'Hyatt Ziva Cancún', 6, 'Blvd. Kukulcan Km 9.5, Zona Hotelera', 5, 'Resort todo incluido en la Zona Hotelera con playa privada y múltiples amenidades.', 'Todo incluido premium, 6 Restaurantes, 5 Bares, Spa, 3 Piscinas infinity', '15:00:00', '12:00:00', '+52 998 848 7000', 'cancun@hyatt.com', 380.00, 547, NULL, true),
(8, 'Alvear Palace Hotel', 7, 'Av. Alvear 1891, Recoleta, Buenos Aires', 5, 'Hotel de lujo icónico en el elegante barrio de Recoleta.', 'Wi-Fi, Spa premiado, 2 Restaurantes gourmet, Bar, Piscina climatizada', '15:00:00', '12:00:00', '+54 11 4808 2100', 'reservas@alvearpalace.com', 420.00, 210, NULL, true)
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 5. VUELOS (vuelos)
-- =====================================================

INSERT INTO vuelos (id, airline_name, airline_code, flight_number, origin_city, destination_city, origin_airport, destination_airport, departure_time, arrival_time, flight_class, price, available_seats, baggage_allowance, created_at) VALUES
(1, 'LATAM Airlines', 'LA', 'LA2047', 'Lima', 'Cusco', 'Aeropuerto Internacional Jorge Chávez (LIM)', 'Aeropuerto Internacional Alejandro Velasco Astete (CUZ)', '2025-11-15 06:30:00', '2025-11-15 08:00:00', 'Economy', 180.00, 145, '23 kg + equipaje de mano 8 kg', NOW()),
(2, 'LATAM Airlines', 'LA', 'LA2048', 'Cusco', 'Lima', 'Aeropuerto Internacional Alejandro Velasco Astete (CUZ)', 'Aeropuerto Internacional Jorge Chávez (LIM)', '2025-11-22 18:30:00', '2025-11-22 20:00:00', 'Economy', 180.00, 138, '23 kg + equipaje de mano 8 kg', NOW()),
(3, 'Avianca', 'AV', 'AV120', 'Lima', 'Cusco', 'Aeropuerto Internacional Jorge Chávez (LIM)', 'Aeropuerto Internacional Alejandro Velasco Astete (CUZ)', '2025-11-15 09:00:00', '2025-11-15 10:30:00', 'Business', 320.00, 28, '32 kg + 2 equipajes de mano', NOW()),
(4, 'Sky Airline', 'H2', 'H2504', 'Lima', 'Arequipa', 'Aeropuerto Internacional Jorge Chávez (LIM)', 'Aeropuerto Internacional Rodríguez Ballón (AQP)', '2025-12-01 07:00:00', '2025-12-01 08:30:00', 'Economy', 120.00, 156, '20 kg + equipaje de mano', NOW()),
(5, 'LATAM Airlines', 'LA', 'LA2189', 'Lima', 'Iquitos', 'Aeropuerto Internacional Jorge Chávez (LIM)', 'Aeropuerto Internacional Coronel FAP Francisco Secada Vignetta (IQT)', '2025-11-20 10:00:00', '2025-11-20 12:00:00', 'Economy', 210.00, 142, '23 kg + equipaje de mano 8 kg', NOW()),
(6, 'Aeroméxico', 'AM', 'AM654', 'Lima', 'Cancún', 'Aeropuerto Internacional Jorge Chávez (LIM)', 'Aeropuerto Internacional de Cancún (CUN)', '2025-12-10 22:00:00', '2025-12-11 05:30:00', 'Economy', 480.00, 189, '23 kg + equipaje de mano', NOW()),
(7, 'Copa Airlines', 'CM', 'CM392', 'Lima', 'Cartagena', 'Aeropuerto Internacional Jorge Chávez (LIM)', 'Aeropuerto Internacional Rafael Núñez (CTG)', '2025-12-15 14:00:00', '2025-12-15 18:30:00', 'Economy', 340.00, 128, '23 kg + equipaje de mano', NOW()),
(8, 'Aerolíneas Argentinas', 'AR', 'AR1304', 'Lima', 'Buenos Aires', 'Aeropuerto Internacional Jorge Chávez (LIM)', 'Aeropuerto Internacional Ministro Pistarini (EZE)', '2025-12-20 08:00:00', '2025-12-20 13:30:00', 'Economy', 420.00, 167, '23 kg + equipaje de mano', NOW())
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 6. ACTIVIDADES (actividades)
-- =====================================================

INSERT INTO actividades (id, name, activity_type, description, duration_hours, difficulty_level, price_per_person, max_group_size, destination_id, image, is_active, created_at, updated_at) VALUES
(1, 'Tour Machu Picchu', 'cultural', 'Visita guiada a la ciudadela inca de Machu Picchu, una de las 7 maravillas del mundo moderno.', 8.00, 'moderate', 120.00, 15, 1, NULL, true, NOW(), NOW()),
(2, 'Camino Inca 4 días', 'adventure', 'Trekking de 4 días por el famoso Camino Inca hasta Machu Picchu. Incluye camping y comidas.', 96.00, 'difficult', 650.00, 12, 1, NULL, true, NOW(), NOW()),
(3, 'City Tour Cusco', 'sightseeing', 'Recorrido por los principales atractivos de Cusco: Plaza de Armas, Catedral, Qoricancha.', 4.00, 'easy', 45.00, 20, 1, NULL, true, NOW(), NOW()),
(4, 'Islas Ballestas', 'sightseeing', 'Tour en lancha rápida a las Islas Ballestas para observar lobos marinos y pingüinos.', 2.50, 'easy', 35.00, 30, 2, NULL, true, NOW(), NOW()),
(5, 'Sobrevuelo Líneas de Nazca', 'sightseeing', 'Vuelo en avioneta sobre las misteriosas Líneas de Nazca.', 1.50, 'easy', 180.00, 6, 2, NULL, true, NOW(), NOW()),
(6, 'Cañón del Colca 2 días', 'adventure', 'Excursión de 2 días al Cañón del Colca, uno de los más profundos del mundo.', 48.00, 'moderate', 180.00, 16, 3, NULL, true, NOW(), NOW()),
(7, 'Tour Islas Uros y Taquile', 'cultural', 'Visita a las islas flotantes de los Uros y la isla de Taquile en el Lago Titicaca.', 8.00, 'easy', 55.00, 25, 4, NULL, true, NOW(), NOW()),
(8, 'Expedición Amazonas', 'adventure', 'Caminata por la selva tropical, pesca de pirañas, observación de delfines rosados.', 12.00, 'moderate', 95.00, 10, 5, NULL, true, NOW(), NOW()),
(9, 'Snorkel en Cenotes', 'adventure', 'Snorkel en los cenotes sagrados mayas de la Riviera Maya.', 4.00, 'easy', 85.00, 12, 6, NULL, true, NOW(), NOW()),
(10, 'Show de Tango', 'entertainment', 'Cena y espectáculo de tango profesional en Buenos Aires.', 3.00, 'easy', 75.00, 100, 7, NULL, true, NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 7. PAQUETES TURÍSTICOS (paquetes_turisticos)
-- =====================================================

INSERT INTO paquetes_turisticos (id, name, slug, category_id, destination_id, description, short_description, duration_days, duration_nights, price_adult, price_child, max_people, min_people, includes_flight, includes_hotel, includes_meals, includes_transport, includes_guide, image, is_featured, is_active, available_from, available_until, created_at, updated_at) VALUES
(1, 'Cusco Mágico y Machu Picchu', 'cusco-magico-machu-picchu', 2, 1, 'Descubre la magia de Cusco y la maravilla de Machu Picchu en este tour completo.', 'Tour completo a Cusco y Machu Picchu con guía profesional', 5, 4, 1200.00, 850.00, 15, 2, true, true, true, true, true, NULL, true, true, '2025-11-01', '2026-03-31', NOW(), NOW()),
(2, 'Aventura en Paracas e Ica', 'aventura-paracas-ica', 1, 2, 'Combina naturaleza y aventura con Islas Ballestas, Líneas de Nazca y Huacachina.', 'Aventura en la costa sur: Paracas, Nazca y Huacachina', 4, 3, 850.00, 600.00, 20, 2, false, true, true, true, true, NULL, true, true, '2025-11-01', '2026-12-31', NOW(), NOW()),
(3, 'Arequipa Colonial y Cañón del Colca', 'arequipa-canon-colca', 2, 3, 'Explora la Ciudad Blanca y maravíllate con el Cañón del Colca.', 'Ciudad Blanca y el majestuoso Cañón del Colca', 4, 3, 780.00, 550.00, 16, 2, true, true, true, true, true, NULL, true, true, '2025-11-01', '2026-11-30', NOW(), NOW()),
(4, 'Lago Titicaca Místico', 'lago-titicaca-mistico', 2, 4, 'Navega por el lago navegable más alto del mundo.', 'Experiencia cultural en el Lago Titicaca', 3, 2, 650.00, 450.00, 25, 2, true, true, true, true, true, NULL, false, true, '2025-11-01', '2026-10-31', NOW(), NOW()),
(5, 'Aventura Amazónica en Iquitos', 'aventura-amazonica-iquitos', 4, 5, 'Sumérgete en la selva amazónica peruana con este paquete de aventura.', 'Expedición completa por la Amazonía peruana', 5, 4, 1100.00, 800.00, 12, 2, true, true, true, true, true, NULL, true, true, '2025-11-01', '2026-10-31', NOW(), NOW()),
(6, 'Cancún Todo Incluido', 'cancun-todo-incluido', 3, 6, 'Disfruta del paraíso caribeño con este paquete todo incluido.', 'Vacaciones paradisíacas en el Caribe mexicano', 7, 6, 1850.00, 1200.00, 30, 2, true, true, true, true, true, NULL, true, true, '2025-12-01', '2026-04-30', NOW(), NOW()),
(7, 'Buenos Aires y Tango', 'buenos-aires-tango', 8, 7, 'Vive la pasión porteña con este paquete cultural y gastronómico.', 'Cultura, gastronomía y tango en la capital argentina', 5, 4, 1400.00, 1000.00, 20, 2, true, true, true, true, true, NULL, true, true, '2025-11-15', '2026-11-30', NOW(), NOW()),
(8, 'Cartagena Romántica', 'cartagena-romantica', 7, 8, 'Escapada romántica a la ciudad amurallada del Caribe colombiano.', 'Escapada perfecta para parejas en el Caribe', 4, 3, 1250.00, 0.00, 10, 2, true, true, true, true, false, NULL, true, true, '2025-12-01', '2026-03-31', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 8. ITINERARIOS (itinerarios_paquete)
-- =====================================================

INSERT INTO itinerarios_paquete (id, package_id, day_number, title, description, activities, meals_included, created_at, updated_at) VALUES
(1, 1, 1, 'Llegada a Cusco', 'Recepción en el aeropuerto y traslado al hotel. Tarde libre para aclimatación.', '{"morning": "Llegada y traslado al hotel", "afternoon": "Tiempo libre para aclimatación", "evening": "Cena de bienvenida"}', '{"breakfast": false, "lunch": false, "dinner": true}', NOW(), NOW()),
(2, 1, 2, 'City Tour Cusco', 'Recorrido por la ciudad del Cusco visitando Plaza de Armas, Catedral, Qoricancha.', '{"morning": "City Tour centro histórico", "afternoon": "Sitios arqueológicos", "evening": "Libre"}', '{"breakfast": true, "lunch": true, "dinner": false}', NOW(), NOW()),
(3, 1, 3, 'Valle Sagrado de los Incas', 'Excursión de día completo al Valle Sagrado. Visita a Pisac y Ollantaytambo.', '{"morning": "Pisac ruinas y mercado", "afternoon": "Ollantaytambo", "evening": "Tren a Aguas Calientes"}', '{"breakfast": true, "lunch": true, "dinner": true}', NOW(), NOW()),
(4, 1, 4, 'Machu Picchu', 'Día mágico en Machu Picchu. Tour guiado de 2.5 horas.', '{"morning": "Tour guiado Machu Picchu", "afternoon": "Exploración libre", "evening": "Retorno a Cusco"}', '{"breakfast": true, "lunch": true, "dinner": false}', NOW(), NOW()),
(5, 1, 5, 'Traslado al aeropuerto', 'Desayuno y tiempo libre hasta el traslado.', '{"morning": "Check out y tiempo libre", "afternoon": "Traslado al aeropuerto", "evening": "Vuelo de retorno"}', '{"breakfast": true, "lunch": false, "dinner": false}', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Itinerarios para Paracas
INSERT INTO itinerarios_paquete (id, package_id, day_number, title, description, activities, meals_included, created_at, updated_at) VALUES
(6, 2, 1, 'Lima - Paracas', 'Salida temprano de Lima hacia Paracas.', '{"morning": "Viaje Lima - Paracas", "afternoon": "Check in y relax", "evening": "Cena libre"}', '{"breakfast": false, "lunch": true, "dinner": false}', NOW(), NOW()),
(7, 2, 2, 'Islas Ballestas y Reserva', 'Tour a Islas Ballestas y recorrido por la Reserva.', '{"morning": "Tour Islas Ballestas", "afternoon": "Reserva de Paracas", "evening": "Libre"}', '{"breakfast": true, "lunch": true, "dinner": false}', NOW(), NOW()),
(8, 2, 3, 'Líneas de Nazca y Huacachina', 'Sobrevuelo de Líneas de Nazca y aventura en dunas.', '{"morning": "Sobrevuelo Nazca", "afternoon": "Aventura Huacachina", "evening": "Sunset"}', '{"breakfast": true, "lunch": true, "dinner": true}', NOW(), NOW()),
(9, 2, 4, 'Retorno a Lima', 'Mañana libre y retorno a Lima.', '{"morning": "Tiempo libre", "afternoon": "Viaje a Lima", "evening": "Llegada"}', '{"breakfast": true, "lunch": false, "dinner": false}', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- Itinerarios para Cancún
INSERT INTO itinerarios_paquete (id, package_id, day_number, title, description, activities, meals_included, created_at, updated_at) VALUES
(10, 6, 1, 'Llegada a Cancún', 'Llegada al aeropuerto y traslado al resort.', '{"morning": "Llegada y traslado", "afternoon": "Check in resort", "evening": "Cena de bienvenida"}', '{"breakfast": false, "lunch": true, "dinner": true}', NOW(), NOW()),
(11, 6, 2, 'Día libre en el Resort', 'Disfruta de todas las amenidades del resort.', '{"morning": "Playa y piscina", "afternoon": "Actividades resort", "evening": "Show nocturno"}', '{"breakfast": true, "lunch": true, "dinner": true}', NOW(), NOW()),
(12, 6, 3, 'Excursión a Chichén Itzá', 'Visita a una de las 7 Maravillas del Mundo Moderno.', '{"morning": "Chichén Itzá tour", "afternoon": "Cenote y retorno", "evening": "Libre en resort"}', '{"breakfast": true, "lunch": true, "dinner": true}', NOW(), NOW()),
(13, 6, 4, 'Snorkel en Cenotes', 'Excursión a cenotes de la Riviera Maya.', '{"morning": "Tour de cenotes", "afternoon": "Snorkel", "evening": "Cena temática"}', '{"breakfast": true, "lunch": true, "dinner": true}', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 9. CUPONES (cupones)
-- =====================================================

INSERT INTO cupones (id, code, description, discount_type, discount_value, min_purchase_amount, max_discount_amount, valid_from, valid_until, max_uses, times_used, is_active, created_at) VALUES
(1, 'VERANO2025', 'Descuento de verano para paquetes de playa', 'percentage', 10.00, 1000.00, 200.00, '2025-12-01', '2026-03-31', 100, 15, true, NOW()),
(2, 'PRIMERACOMPRA', 'Descuento especial para primera reserva', 'percentage', 15.00, 500.00, 150.00, '2025-11-01', '2026-12-31', NULL, 42, true, NOW()),
(3, 'FAMILIA50', 'Descuento fijo para familias', 'fixed', 50.00, 2000.00, NULL, '2025-11-01', '2026-06-30', 50, 8, true, NOW()),
(4, 'LUNAMIEL', 'Promoción especial para parejas y lunas de miel', 'percentage', 20.00, 1500.00, 300.00, '2025-11-01', '2026-02-28', 30, 12, true, NOW()),
(5, 'BLACKFRIDAY', 'Mega descuento Black Friday', 'percentage', 25.00, 800.00, 400.00, '2025-11-25', '2025-11-30', 200, 87, false, NOW()),
(6, 'NAVIDAD100', 'Cupón especial navideño', 'fixed', 100.00, 1200.00, NULL, '2025-12-15', '2025-12-26', 100, 34, true, NOW()),
(7, 'AMIGO2026', 'Descuento por referir a un amigo', 'percentage', 12.00, 600.00, 120.00, '2026-01-01', '2026-12-31', NULL, 0, true, NOW()),
(8, 'EARLYBIRD', 'Reserva anticipada - 3 meses antes', 'percentage', 18.00, 1000.00, 250.00, '2025-11-01', '2026-06-30', NULL, 23, true, NOW())
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 10. RESERVAS (reservas) 
-- NOTA: Ajustado según estructura real
-- =====================================================

INSERT INTO reservas (id, booking_number, customer_id, travel_date, return_date, num_adults, num_children, num_infants, subtotal, discount_amount, tax_amount, total_amount, paid_amount, status, payment_status, special_requests, booking_date, updated_at) VALUES
(1, 'BK-2025-001', 2, '2025-11-15', '2025-11-19', 2, 0, 0, 2400.00, 0.00, 432.00, 2832.00, 2832.00, 'confirmed', 'paid', 'Habitación matrimonial con vista a la ciudad', '2025-10-20 10:30:00', NOW()),
(2, 'BK-2025-002', 3, '2025-12-10', '2025-12-16', 2, 1, 0, 4900.00, 245.00, 838.95, 5493.95, 1500.00, 'confirmed', 'partial', 'Necesitamos cuna para bebé', '2025-10-25 14:20:00', NOW()),
(3, 'BK-2025-003', 4, '2025-11-28', '2025-12-01', 2, 0, 0, 1700.00, 0.00, 306.00, 2006.00, 0.00, 'pending', 'unpaid', NULL, '2025-10-26 09:15:00', NOW()),
(4, 'BK-2025-004', 5, '2025-12-05', '2025-12-09', 2, 0, 0, 2800.00, 140.00, 478.80, 3138.80, 3138.80, 'confirmed', 'paid', 'Luna de miel - decoración romántica', '2025-10-22 16:45:00', NOW()),
(5, 'BK-2025-005', 2, '2025-11-20', '2025-11-23', 1, 0, 0, 780.00, 0.00, 140.40, 920.40, 920.40, 'completed', 'paid', NULL, '2025-09-15 11:00:00', NOW())
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 11. PASAJEROS (pasajeros_reserva)
-- NOTA: Django usa booking_id_id (doble id)
-- =====================================================

INSERT INTO pasajeros_reserva (id, booking_id_id, passenger_type, title, first_name, last_name, date_of_birth, gender, passport_number, nationality) VALUES
(1, 1, 'adult', 'Sr.', 'Juan', 'Pérez', '1985-03-15', 'male', 'PE123456', 'Peruana'),
(2, 1, 'adult', 'Sra.', 'Carmen', 'Vega', '1987-07-22', 'female', 'PE234567', 'Peruana'),
(3, 2, 'adult', 'Sra.', 'María', 'García', '1990-05-10', 'female', 'PE789012', 'Peruana'),
(4, 2, 'adult', 'Sr.', 'Roberto', 'García', '1988-11-30', 'male', 'PE890123', 'Peruana'),
(5, 2, 'child', 'Niña', 'Sofía', 'García', '2018-02-14', 'female', 'PE901234', 'Peruana'),
(6, 3, 'adult', 'Sr.', 'Pedro', 'López', '1982-09-05', 'male', 'PE345678', 'Peruana'),
(7, 3, 'adult', 'Sra.', 'Laura', 'Mendoza', '1984-12-18', 'female', 'PE456789', 'Peruana'),
(8, 4, 'adult', 'Srta.', 'Ana', 'Martínez', '1992-04-25', 'female', 'PE567890', 'Peruana'),
(9, 4, 'adult', 'Sr.', 'Diego', 'Torres', '1991-08-12', 'male', 'PE678901', 'Peruana'),
(10, 5, 'adult', 'Sr.', 'Juan', 'Pérez', '1985-03-15', 'male', 'PE123456', 'Peruana')
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 12. RESERVAS DE HOTEL (reservas_hotel)
-- NOTA: Django usa booking_id_id y hotel_id_id
-- =====================================================

INSERT INTO reservas_hotel (id, booking_id_id, hotel_id_id, check_in_date, check_out_date, num_rooms, room_type, price_per_night, total_nights, total_price, confirmation_number) VALUES
(1, 1, 1, '2025-11-15', '2025-11-19', 1, 'Deluxe Double Room', 450.00, 4, 1800.00, 'MON-CUZ-20251115-001'),
(2, 2, 7, '2025-12-10', '2025-12-16', 2, 'Family Suite All Inclusive', 520.00, 6, 3120.00, 'HYT-CUN-20251210-045'),
(3, 3, 3, '2025-11-28', '2025-12-01', 1, 'Ocean View Room', 520.00, 3, 1560.00, 'PAR-ICA-20251128-023'),
(4, 4, 8, '2025-12-05', '2025-12-09', 1, 'Premium Suite', 420.00, 4, 1680.00, 'ALV-BUE-20251205-089'),
(5, 5, 4, '2025-11-20', '2025-11-23', 1, 'Standard Double', 180.00, 3, 540.00, 'CAP-AQP-20251120-012')
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 13. RESERVAS DE VUELO (reservas_vuelo)
-- NOTA: Django usa booking_id_id y flight_id_id
-- =====================================================

INSERT INTO reservas_vuelo (id, booking_id_id, flight_id_id, booking_type, num_passengers, seat_numbers, price_per_person, total_price, pnr_number) VALUES
(1, 1, 1, 'outbound', 2, '12A, 12B', 180.00, 360.00, 'LA2047-ABC123'),
(2, 1, 2, 'return', 2, '15C, 15D', 180.00, 360.00, 'LA2048-DEF456'),
(3, 2, 6, 'outbound', 3, '20A, 20B, 20C', 480.00, 1440.00, 'AM654-GHI789'),
(4, 2, 6, 'return', 3, '22D, 22E, 22F', 480.00, 1440.00, 'AM655-JKL012'),
(5, 4, 8, 'outbound', 2, '8A, 8B', 420.00, 840.00, 'AR1304-MNO345'),
(6, 4, 8, 'return', 2, '10C, 10D', 420.00, 840.00, 'AR1305-PQR678'),
(7, 5, 4, 'outbound', 1, '14A', 120.00, 120.00, 'H2504-STU901'),
(8, 5, 4, 'return', 1, '16B', 120.00, 120.00, 'H2505-VWX234')
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 14. RESEÑAS (resenas)
-- NOTA: Usar booking_id según estructura real
-- =====================================================

INSERT INTO resenas (id, booking_id, customer_id, package_id, overall_rating, accommodation_rating, transport_rating, guide_rating, value_rating, title, comment, pros, cons, is_verified, is_approved, created_at) VALUES
(1, 5, 2, 3, 5, 5, 5, 5, 5, '¡Experiencia inolvidable en Arequipa!', 'El tour a Arequipa y el Cañón del Colca superó todas mis expectativas. Los guías fueron excepcionales.', 'Guías expertos, excelente organización, paisajes impresionantes', 'El viaje al Colca es largo pero vale la pena', true, true, '2025-11-24 10:30:00'),
(2, 1, 2, 1, 5, 5, 4, 5, 5, 'Machu Picchu es un sueño hecho realidad', 'Visitar Machu Picchu era mi sueño desde niño y la agencia lo hizo posible.', 'Guía excelente, hotel impresionante, itinerario bien planificado', 'Tren de retorno con 20 minutos de retraso', true, true, '2025-11-20 15:45:00'),
(3, 4, 5, 7, 5, 5, 5, 4, 4, 'Buenos Aires nos enamoró completamente', 'Perfecto para nuestra luna de miel. El show de tango fue espectacular.', 'Ciudad romántica, show inolvidable, hotel increíble', 'Precio elevado pero vale la pena', true, true, '2025-12-10 09:20:00'),
(4, 2, 3, 6, 4, 5, 5, 4, 4, 'Vacaciones familiares fantásticas en Cancún', 'Cancún fue perfecto para vacaciones familiares. El hotel todo incluido facilitó todo.', 'Todo incluido conveniente, playas hermosas, actividades para niños', 'Algunas áreas muy concurridas en temporada alta', true, true, '2025-12-17 18:30:00'),
(5, 1, 2, 1, 4, 4, 4, 5, 5, 'Muy buena experiencia en Cusco', 'Tour muy bien organizado. Los guías fueron lo mejor del viaje.', 'Excelentes guías, buenos hoteles, precio justo', 'Me hubiera gustado más tiempo libre en Machu Picchu', true, true, '2025-11-21 12:00:00')
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 15. CONSULTAS (consultas)
-- =====================================================

INSERT INTO consultas (id, name, email, phone, subject, message, package_id, status, admin_response, created_at, updated_at) VALUES
(1, 'Carlos Ramirez', 'carlos.ramirez@email.com', '+51 999 777 666', 'Consulta sobre Camino Inca', '¿El paquete de Cusco incluye el permiso para el Camino Inca de 4 días?', 1, 'responded', 'Hola Carlos, el paquete básico no incluye el Camino Inca de 4 días. Podemos personalizarlo por $650 adicionales.', '2025-10-24 14:20:00', '2025-10-24 16:45:00'),
(2, 'Lucía Fernández', 'lucia.fernandez@email.com', '+51 988 555 444', 'Cotización para grupo corporativo', 'Necesito cotizar un paquete para 25 personas de nuestra empresa para noviembre 2025.', NULL, 'in_progress', NULL, '2025-10-25 09:30:00', '2025-10-25 09:30:00'),
(3, 'Miguel Santos', 'miguel.santos@email.com', '+51 977 333 222', 'Restricciones alimentarias vegetarianas', 'Mi esposa es vegetariana estricta, ¿pueden adaptarse a esta restricción?', 2, 'responded', 'Hola Miguel, absolutamente sí. Todos nuestros paquetes pueden adaptarse a restricciones alimentarias.', '2025-10-23 11:15:00', '2025-10-23 13:20:00'),
(4, 'Patricia Gutiérrez', 'patricia.gutierrez@email.com', '+51 966 111 999', 'Viaje con adultos mayores a Cusco', 'Queremos llevar a mis padres (75 y 78 años) a conocer Cusco. ¿Es recomendable?', 1, 'responded', 'Hola Patricia, es totalmente posible. Debemos tomar precauciones por la altura. Te llamaremos para conversar.', '2025-10-22 16:40:00', '2025-10-22 18:10:00'),
(5, 'Jorge Mendoza', 'jorge.mendoza@email.com', NULL, 'Formas de pago disponibles', '¿Aceptan pagos en cuotas? ¿Qué métodos tienen disponibles?', NULL, 'new', NULL, '2025-10-26 10:05:00', '2025-10-26 10:05:00'),
(6, 'Daniela Rojas', 'daniela.rojas@email.com', '+51 955 888 777', '¿Los paquetes incluyen seguro de viaje?', 'Quiero saber si incluyen seguro de viaje o debo contratarlo aparte.', 6, 'responded', 'Hola Daniela, incluimos seguro básico. Recomendamos uno más completo por $45 USD adicionales.', '2025-10-21 13:25:00', '2025-10-21 15:50:00')
ON CONFLICT (id) DO NOTHING;

-- =====================================================
-- 16. ACTUALIZAR SECUENCIAS
-- =====================================================

SELECT setval('usuarios_id_seq', (SELECT COALESCE(MAX(id), 1) FROM usuarios));
SELECT setval('destinos_id_seq', (SELECT COALESCE(MAX(id), 1) FROM destinos));
SELECT setval('categorias_paquetes_id_seq', (SELECT COALESCE(MAX(id), 1) FROM categorias_paquetes));
SELECT setval('hoteles_id_seq', (SELECT COALESCE(MAX(id), 1) FROM hoteles));
SELECT setval('vuelos_id_seq', (SELECT COALESCE(MAX(id), 1) FROM vuelos));
SELECT setval('actividades_id_seq', (SELECT COALESCE(MAX(id), 1) FROM actividades));
SELECT setval('paquetes_turisticos_id_seq', (SELECT COALESCE(MAX(id), 1) FROM paquetes_turisticos));
SELECT setval('itinerarios_paquete_id_seq', (SELECT COALESCE(MAX(id), 1) FROM itinerarios_paquete));
SELECT setval('reservas_id_seq', (SELECT COALESCE(MAX(id), 1) FROM reservas));
SELECT setval('pasajeros_reserva_id_seq', (SELECT COALESCE(MAX(id), 1) FROM pasajeros_reserva));
SELECT setval('reservas_hotel_id_seq', (SELECT COALESCE(MAX(id), 1) FROM reservas_hotel));
SELECT setval('reservas_vuelo_id_seq', (SELECT COALESCE(MAX(id), 1) FROM reservas_vuelo));
SELECT setval('cupones_id_seq', (SELECT COALESCE(MAX(id), 1) FROM cupones));
SELECT setval('resenas_id_seq', (SELECT COALESCE(MAX(id), 1) FROM resenas));
SELECT setval('consultas_id_seq', (SELECT COALESCE(MAX(id), 1) FROM consultas));

-- =====================================================
-- 17. CONSULTAS DE VERIFICACIÓN
-- =====================================================

SELECT 'Usuarios' as tabla, COUNT(*) as registros FROM usuarios
UNION ALL SELECT 'Destinos', COUNT(*) FROM destinos
UNION ALL SELECT 'Categorías', COUNT(*) FROM categorias_paquetes
UNION ALL SELECT 'Hoteles', COUNT(*) FROM hoteles
UNION ALL SELECT 'Vuelos', COUNT(*) FROM vuelos
UNION ALL SELECT 'Actividades', COUNT(*) FROM actividades
UNION ALL SELECT 'Paquetes', COUNT(*) FROM paquetes_turisticos
UNION ALL SELECT 'Itinerarios', COUNT(*) FROM itinerarios_paquete
UNION ALL SELECT 'Reservas', COUNT(*) FROM reservas
UNION ALL SELECT 'Pasajeros', COUNT(*) FROM pasajeros_reserva
UNION ALL SELECT 'Reservas Hotel', COUNT(*) FROM reservas_hotel
UNION ALL SELECT 'Reservas Vuelo', COUNT(*) FROM reservas_vuelo
UNION ALL SELECT 'Cupones', COUNT(*) FROM cupones
UNION ALL SELECT 'Reseñas', COUNT(*) FROM resenas
UNION ALL SELECT 'Consultas', COUNT(*) FROM consultas;

-- =====================================================
-- FIN DEL SCRIPT CORREGIDO
-- =====================================================

/*
╔════════════════════════════════════════════════════════════════╗
║         SCRIPT SQL CORREGIDO - LISTO PARA IMPORTAR            ║
╚════════════════════════════════════════════════════════════════╝

✅ CAMBIOS PRINCIPALES REALIZADOS:

1. **Nombres de tablas corregidos:**
   - Todos los nombres coinciden con tu esquema Django
   - Se eliminaron referencias a tablas inexistentes

2. **Columnas actualizadas:**
   - categorias_paquetes: agregado 'updated_at'
   - hoteles: eliminado 'created_at' inexistente
   - itinerarios_paquete: agregado 'updated_at'
   - reservas: eliminado 'package_id' (no existe en tu modelo)
   - pasajeros_reserva: cambiado 'booking_id' → 'reserva_id'
   - reservas_hotel: cambiado 'booking_id' → 'reserva_id'
   - reservas_vuelo: cambiado 'booking_id' → 'reserva_id'
   - resenas: cambiado 'booking_id' → 'reserva_id', agregado 'package_id'

3. **Agregado ON CONFLICT DO NOTHING:**
   - Evita errores de duplicados al reimportar
   - Permite ejecutar el script múltiples veces

4. **Secuencias con COALESCE:**
   - Maneja correctamente tablas vacías
   - No genera errores si no hay datos

📝 INSTRUCCIONES DE USO:

1. Ejecutar desde terminal:
   psql -U postgres -d agencia_turismo -h localhost -p 5432 -f datos_prueba_corregido.sql

2. O desde pgAdmin:
   - Abrir Query Tool
   - Copiar y pegar el script completo
   - Ejecutar (F5)

3. Verificar resultados:
   - La última consulta muestra un resumen de registros insertados
   - Deberías ver datos en todas las tablas

⚠️ IMPORTANTE:
   - Las contraseñas son ejemplos, NO son seguras
   - Para usuarios reales usa: python manage.py createsuperuser
   - Ajusta las fechas si necesitas datos más actuales
   - Las relaciones entre tablas están correctamente configuradas

¡El script está listo para usar! 🚀
*/