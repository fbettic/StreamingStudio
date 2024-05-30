--################################################################################################
--###################                       PLATAFORMA 1                       ###################
--################################################################################################

USE anunciante1_db
GO

-- Inicialización de datos para Conexiones de Servicio
EXEC CreateServiceConnection 'Streaming Studio', 'STREAMING_STUDIO_SECRET_KEY'
GO

-- Inicialización de datos para Prioridades
EXEC CreatePriority "LOW"
EXEC CreatePriority "MEDIUM"
EXEC CreatePriority "HIGHT" 
GO

-- Inicialización de datos para Banners
EXEC CreateBanner 'Nuevo modelo disponible', 'http://localhost:4210/images/advertisings/large-cars-1.jpg', 'https://ejemplo.com/producto'
EXEC CreateBanner 'Oferta especial por tiempo limitado', 'http://localhost:4210/images/advertisings/medium-cars-1.jpg', 'https://ejemplo.com/oferta'
EXEC CreateBanner '¡No te pierdas nuestra promoción!', 'http://localhost:4210/images/advertisings/small-cars-1.jpg', 'https://ejemplo.com/promocion'
GO

-- Inicialización de datos para Anuncios Publicitarios
DECLARE @bannerId1 INT, @bannerId2 INT, @bannerId3 INT, @serviceId INT
SELECT @bannerId1 = bannerId FROM Banner WHERE text = 'Nuevo modelo disponible'
SELECT @bannerId2 = bannerId FROM Banner WHERE text = 'Oferta especial por tiempo limitado'
SELECT @bannerId3 = bannerId FROM Banner WHERE text = '¡No te pierdas nuestra promoción!'
SELECT @serviceId = serviceId FROM ServiceConnection WHERE name = 'Streaming Studio'

EXEC CreateAdvertising @bannerId1, @serviceId, 3, '2024-03-16', '2024-03-31'
EXEC CreateAdvertising @bannerId2, @serviceId, 2, '2024-03-16', '2024-03-25'
EXEC CreateAdvertising @bannerId3, @serviceId, 1, '2024-03-16', '2024-03-20'
GO

--################################################################################################
--###################                       PLATAFORMA 2                       ###################
--################################################################################################

USE anunciante2_db
GO

-- Inicialización de datos para Conexiones de Servicio
EXEC CreateServiceConnection 'Streaming Studio', 'STREAMING_STUDIO_SECRET_KEY'
GO

-- Inicialización de datos para Prioridades
EXEC CreatePriority "LOW"
EXEC CreatePriority "MEDIUM"
EXEC CreatePriority "HIGHT"
GO

-- Inicialización de datos para Banners
EXEC CreateBanner 'Nuevo producto disponible', 'http://localhost:4210/images/advertisings/large-hobbies-1.jpg', 'https://ejemplo.com/producto'
EXEC CreateBanner 'Oferta especial por tiempo limitado', 'http://localhost:4210/images/advertisings/large-hobbies-2.jpg', 'https://ejemplo.com/oferta'
EXEC CreateBanner '¡No te pierdas nuestra promoción!', 'http://localhost:4210/images/advertisings/small-hobbies-1.jpg', 'https://ejemplo.com/promocion'
EXEC CreateBanner 'Descuentos exclusivos para suscriptores', 'http://localhost:4210/images/advertisings/small-hobbies-2.jpg', 'https://ejemplo.com/descuentos'
EXEC CreateBanner 'Envío gratuito en todas las compras', 'http://localhost:4210/images/advertisings/small-hobbies-3.jpg', 'https://ejemplo.com/envio-gratis'

-- Inicialización de datos para Anuncios Publicitarios
DECLARE @bannerId1 INT, @bannerId2 INT, @bannerId3 INT, @bannerId4 INT, @bannerId5 INT, @serviceId INT
SELECT @bannerId1 = bannerId FROM Banner WHERE text = 'Nuevo producto disponible'
SELECT @bannerId2 = bannerId FROM Banner WHERE text = 'Oferta especial por tiempo limitado'
SELECT @bannerId3 = bannerId FROM Banner WHERE text = '¡No te pierdas nuestra promoción!'
SELECT @bannerId4 = bannerId FROM Banner WHERE text = 'Descuentos exclusivos para suscriptores'
SELECT @bannerId5 = bannerId FROM Banner WHERE text = 'Envío gratuito en todas las compras'
SELECT @serviceId = serviceId FROM ServiceConnection WHERE name = 'Streaming Studio'

EXEC CreateAdvertising @bannerId1, @serviceId, 1, '2024-03-16', '2024-03-31'
EXEC CreateAdvertising @bannerId2, @serviceId, 2, '2024-03-16', '2024-03-25'
EXEC CreateAdvertising @bannerId3, @serviceId, 3, '2024-03-16', '2024-03-20'
EXEC CreateAdvertising @bannerId4, @serviceId, 1, '2024-03-16', '2024-03-28'
EXEC CreateAdvertising @bannerId5, @serviceId, 2, '2024-03-16', '2024-03-22'


--################################################################################################
--###################                       PLATAFORMA 3                       ###################
--################################################################################################

USE anunciante3_db
GO

-- Inicialización de datos para Conexiones de Servicio
EXEC CreateServiceConnection 'Streaming Studio', 'STREAMING_STUDIO_SECRET_KEY'
GO

-- Inicialización de datos para Prioridades
EXEC CreatePriority "LOW"
EXEC CreatePriority "MEDIUM"
EXEC CreatePriority "HIGHT"
GO

-- Inicialización de datos para Banners
EXEC CreateBanner 'Gran venta de fin de semana', 'http://localhost:4210/images/advertisings/large-travels-1.jpg', 'https://ejemplo.com/venta'
EXEC CreateBanner '¡Nuevos productos en stock!', 'http://localhost:4210/images/advertisings/medium-travels-1.jpg', 'https://ejemplo.com/nuevos-productos'

-- Inicialización de datos para Anuncios Publicitarios
DECLARE @bannerId6 INT, @bannerId7 INT, @serviceId INT
SELECT @bannerId6 = bannerId FROM Banner WHERE text = 'Gran venta de fin de semana'
SELECT @bannerId7 = bannerId FROM Banner WHERE text = '¡Nuevos productos en stock!'
SELECT @serviceId = serviceId FROM ServiceConnection WHERE name = 'Streaming Studio'

EXEC CreateAdvertising @bannerId6, @serviceId, 3, '2024-03-16', '2024-03-20'
EXEC CreateAdvertising @bannerId7, @serviceId, 1, '2024-03-16', '2024-03-25'

select * from Advertising