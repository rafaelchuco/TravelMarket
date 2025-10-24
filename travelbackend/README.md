# TAREAS DETALLADAS POR EQUIPO

## 👥 EQUIPO 1: AUTENTICACIÓN Y USUARIOS (2 personas)

📁 **Carpeta:** `apps/authentication/`

### PERSONA 1 - Modelos y Admin ⏱️ 4 horas

**Tareas:**

**Crear modelo User personalizado (2h)**
- Heredar de AbstractUser
- Agregar campo user_type con choices ('admin', 'customer')
- Agregar campos: phone, address, city, country, passport_number, nationality
- Agregar timestamps: created_at, updated_at
- Configurar Meta con db_table='usuarios'
- Hacer migraciones y migrar

**Personalizar Admin Panel (1h)**
- Registrar modelo User en admin.py
- Configurar list_display con campos principales
- Agregar list_filter por user_type, is_active
- Agregar search_fields por username, email, nombre
- Agregar fieldsets personalizados
- Hacer readonly los timestamps

**Crear 5 usuarios de prueba desde el admin (30min)**
- 2 admins
- 3 customers con datos completos

**Probar que todo funciona (30min)**
- Verificar tabla en MySQL
- Verificar admin panel
- Verificar que se pueden editar usuarios

### PERSONA 2 - API y Endpoints ⏱️ 5 horas

**Tareas:**

**Crear Serializers (2h)**
- UserSerializer: para lectura (todos los campos excepto password)
- UserRegistrationSerializer: para registro (validar password, password_confirm)
- UserUpdateSerializer: para actualizar perfil (campos editables)

**Crear Views/ViewSets (2h)**
- RegisterView: POST para registro de usuarios
- UserViewSet: CRUD completo de usuarios
- Endpoint me/: obtener usuario autenticado
- Endpoint update_profile/: actualizar perfil

**Configurar URLs (30min)**
- Crear router para UserViewSet
- Agregar ruta para register
- Verificar que esté incluido en config/urls.py

**Probar endpoints con Postman (30min)**
- POST /api/v1/auth/register/ (crear usuario)
- GET /api/v1/auth/users/ (listar usuarios)
- GET /api/v1/auth/users/{id}/ (detalle)
- PUT /api/v1/auth/users/{id}/ (actualizar)

---

## 👥 EQUIPO 2: CATÁLOGO (2 personas)

📁 **Carpetas:** `apps/destinations/` y `apps/packages/`

### PERSONA 1 - Destinos ⏱️ 5 horas

**Tareas:**

**Crear modelo Destination (1.5h)**
- Campos: name, country, continent, description, short_description
- Campos: latitude, longitude, image, is_popular, best_season
- Timestamp created_at
- Meta con db_table='destinos'
- Hacer migraciones

**Crear Serializers de Destination (1h)**
- DestinationSerializer con todos los campos
- Validar que name y country sean requeridos

**Crear ViewSet de Destination (1.5h)**
- CRUD completo con ModelViewSet
- Filtros: country, continent, is_popular
- Búsqueda por name
- Ordenamiento por created_at, name

**Configurar URLs (30min)**
- Router con DefaultRouter
- Verificar en config/urls.py

**Personalizar Admin (30min)**
- list_display, list_filter, search_fields
- Agregar 6 destinos de prueba

**Probar endpoints (1h)**
- GET /api/v1/destinations/
- POST /api/v1/destinations/
- Probar filtros y búsqueda

### PERSONA 2 - Paquetes ⏱️ 6 horas

**Tareas:**

**Crear modelos (2h)**
- Category: name, description, icon (db_table='categorias_paquetes')
- Package: name, slug, category_id, destination_id, description, duration_days, duration_nights, price_adult, price_child, max_people, includes (flight, hotel, meals, transport, guide), image, is_featured, available_from, available_until (db_table='paquetes_turisticos')
- Itinerary: package_id, day_number, title, description, activities, meals_included (db_table='itinerarios_paquete')
- Hacer migraciones

**Crear Serializers (1.5h)**
- CategorySerializer
- ItinerarySerializer
- PackageListSerializer (campos resumidos)
- PackageDetailSerializer (con itinerary nested)

**Crear ViewSets (1.5h)**
- CategoryViewSet (CRUD simple)
- PackageViewSet (CRUD completo)
- Usar PackageListSerializer en list
- Usar PackageDetailSerializer en retrieve
- Filtros: category, destination, is_featured, price

**Configurar URLs (30min)**
- Router para ambos ViewSets
- Verificar rutas

**Personalizar Admin (1h)**
- Admin de Category
- Admin de Package con ItineraryInline
- list_display, list_filter, search_fields

**Crear datos de prueba (30min)**
- 5 categorías
- 8 paquetes con itinerarios (2-3 días cada uno)

**Probar endpoints (30min)**
- GET /api/v1/packages/
- GET /api/v1/packages/{id}/ (ver itinerario nested)
- Probar filtros

---

## 👥 EQUIPO 3: SERVICIOS (2 personas)

📁 **Carpetas:** `apps/hotels/`, `apps/flights/`, `apps/activities/`

### PERSONA 1 - Hoteles y Vuelos ⏱️ 6 horas

**Tareas Hoteles:**

**Crear modelo Hotel (1h)**
- Campos: name, destination_id (FK), address, star_rating, description, amenities, check_in_time, check_out_time, phone, email, price_per_night, total_rooms, image, is_active
- db_table='hoteles'
- Hacer migraciones

**Serializers y ViewSet (1h)**
- HotelSerializer
- HotelViewSet con CRUD
- Filtros: destination, star_rating, price_range

**Admin y datos (30min)**
- Admin personalizado
- Crear 6 hoteles de prueba

**Tareas Vuelos:**

**Crear modelo Flight (1h)**
- Campos: airline_name, airline_code, flight_number, origin_city, destination_city, origin_airport, destination_airport, departure_time, arrival_time, flight_class, price, available_seats, baggage_allowance
- db_table='vuelos'
- Hacer migraciones

**Serializers y ViewSet (1h)**
- FlightSerializer
- FlightViewSet con CRUD
- Filtros: origin_city, destination_city, departure_time

**Admin y datos (30min)**
- Admin personalizado
- Crear 8 vuelos de prueba

**URLs y testing (1h)**
- Configurar URLs para ambas apps
- Probar endpoints

### PERSONA 2 - Actividades ⏱️ 4 horas

**Tareas:**

**Crear modelo Activity (1h)**
- Campos: name, destination_id (FK), activity_type (choices: sightseeing, adventure, cultural, shopping, dining, sports, wellness, entertainment), description, duration_hours, difficulty_level (choices: easy, moderate, difficult), price_per_person, max_group_size, image, is_active
- db_table='actividades'
- Hacer migraciones

**Crear Serializers (1h)**
- ActivitySerializer con todos los campos
- Validaciones

**Crear ViewSet (1h)**
- CRUD completo
- Filtros: destination, activity_type, difficulty_level
- Búsqueda por name

**Admin (30min)**
- list_display, list_filter, search_fields
- Crear 10 actividades de prueba en diferentes destinos

**URLs y testing (30min)**
- Configurar URLs
- Probar GET /api/v1/activities/
- Probar filtros

---

## 👥 EQUIPO 4: RESERVAS Y COMPLEMENTOS (2 personas)

📁 **Carpetas:** `apps/bookings/`, `apps/reviews/`, `apps/promotions/`, `apps/inquiries/`

### PERSONA 1 - Sistema de Reservas ⏱️ 8 horas

**Tareas:**

**Crear modelos (3h)**
- Booking: booking_number, customer_id (FK User), package_id (FK Package), travel_date, return_date, num_adults, num_children, num_infants, subtotal, discount_amount, tax_amount, total_amount, paid_amount, status (choices: pending, confirmed, cancelled, completed), payment_status (choices: unpaid, partial, paid, refunded), special_requests, booking_date, updated_at (db_table='reservas')
- Passenger: booking_id (FK), passenger_type (choices: adult, child, infant), title, first_name, last_name, date_of_birth, gender, passport_number, nationality (db_table='pasajeros_reserva')
- HotelBooking: booking_id (FK), hotel_id (FK), check_in_date, check_out_date, num_rooms, room_type, price_per_night, total_nights, total_price, confirmation_number (db_table='reservas_hotel')
- FlightBooking: booking_id (FK), flight_id (FK), booking_type (choices: outbound, return), num_passengers, seat_numbers, price_per_person, total_price, pnr_number (db_table='reservas_vuelo')
- Hacer migraciones

**Crear Serializers (2h)**
- PassengerSerializer
- HotelBookingSerializer
- FlightBookingSerializer
- BookingListSerializer (resumen)
- BookingDetailSerializer (con passengers, hotel_booking, flight_booking nested)
- BookingCreateSerializer (para crear reserva completa)

**Crear ViewSet (2h)**
- BookingViewSet
- POST para crear reserva completa con pasajeros
- GET para listar reservas
- GET detail con toda la info nested
- Lógica para generar booking_number automático
- Lógica para calcular total_amount

**Admin (30min)**
- Admin con PassengerInline
- list_display, list_filter por status, booking_date
- search_fields por booking_number

**URLs y testing (30min)**
- Configurar URLs
- Probar crear reserva completa
- Crear 3 reservas de prueba

### PERSONA 2 - Reviews, Cupones, Wishlist, Consultas ⏱️ 6 horas

**Tareas Reviews:**

**Crear modelo Review (1h)**
- Campos: booking_id (FK), customer_id (FK), package_id (FK), overall_rating, accommodation_rating, transport_rating, guide_rating, value_rating, title, comment, pros, cons, is_verified, is_approved, created_at
- db_table='resenas'
- Hacer migraciones

**Serializers, ViewSet y Admin (1h)**
- ReviewSerializer
- ReviewViewSet con filtros por package, rating
- Admin con filtro por is_approved

**Tareas Cupones:**

**Crear modelo Coupon (1h)**
- Campos: code, description, discount_type (choices: percentage, fixed), discount_value, min_purchase_amount, max_discount_amount, valid_from, valid_until, max_uses, times_used, is_active
- db_table='cupones'
- Hacer migraciones

**Serializers, ViewSet y Admin (1h)**
- CouponSerializer
- CouponViewSet
- Crear 5 cupones de prueba

**Tareas Wishlist:**

**Crear modelo Wishlist (30min)**
- Campos: user_id (FK), package_id (FK), added_at
- db_table='lista_deseos'
- Hacer migraciones

**Serializers y ViewSet (30min)**
- WishlistSerializer con package info
- ViewSet para agregar/quitar de wishlist

**Tareas Consultas:**

**Crear modelo Inquiry (30min)**
- Campos: name, email, phone, subject, message, package_id (FK nullable), status (choices: new, in_progress, responded, closed), admin_response, created_at, updated_at
- db_table='consultas'
- Hacer migraciones

**Serializers, ViewSet y Admin (30min)**
- InquirySerializer
- InquiryViewSet
- Admin para responder consultas

**URLs y testing (1h)**
- Configurar URLs para todas las apps
- Probar todos los endpoints
- Crear datos de prueba
