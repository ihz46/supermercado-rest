-- --------------------------------------------------------
-- Host:                     	127.0.0.1
-- Versión del servidor:     	10.3.16-MariaDB - mariadb.org binary distribution
-- SO del servidor:          	Win64
-- HeidiSQL Versión:         	10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para mercadona
CREATE DATABASE IF NOT EXISTS `mercadona` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mercadona`;

-- Volcando estructura para tabla mercadona.categoria
CREATE TABLE IF NOT EXISTS `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla mercadona.categoria: ~13 rows (aproximadamente)
DELETE FROM `categoria`;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`id`, `nombre`) VALUES
    (10, 'basicos'),
    (9, 'bolleria'),
    (3, 'carne'),
    (7, 'electrodomesticos'),
    (6, 'electronica'),
    (12, 'embutido de cerdo'),
    (1, 'frescos'),
    (2, 'fruta'),
    (5, 'hogar'),
    (4, 'limpieza'),
    (11, 'menaje'),
    (8, 'panaderia'),
    (23, 'telefonia');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;

-- Volcando estructura para tabla mercadona.historico
CREATE TABLE IF NOT EXISTS `historico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `precio` float NOT NULL DEFAULT 0,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp(),
  `id_producto` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_producto` (`id_producto`),
  CONSTRAINT `FK_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla mercadona.historico: ~0 rows (aproximadamente)
DELETE FROM `historico`;
/*!40000 ALTER TABLE `historico` DISABLE KEYS */;
/*!40000 ALTER TABLE `historico` ENABLE KEYS */;

-- Volcando estructura para tabla mercadona.producto
CREATE TABLE IF NOT EXISTS `producto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `precio` float NOT NULL DEFAULT 0,
  `imagen` varchar(150) NOT NULL DEFAULT '0',
  `descripcion` varchar(150) NOT NULL DEFAULT '0',
  `descuento` int(11) NOT NULL DEFAULT 0 COMMENT 'porcentaje descuento de 0 a 100',
  `fecha_baja` timestamp NULL DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `id_categoria` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FK_usuario` (`id_usuario`),
  KEY `FK_categoria` (`id_categoria`),
  CONSTRAINT `FK_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `FK_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla mercadona.producto: ~1 rows (aproximadamente)
DELETE FROM `producto`;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`id`, `nombre`, `precio`, `imagen`, `descripcion`, `descuento`, `fecha_baja`, `fecha_creacion`, `fecha_modificacion`, `id_categoria`, `id_usuario`) VALUES
    (23, 'colacao', 43, '0', '0', 2, NULL, '2020-01-10 11:49:50', '2020-01-10 11:50:02', 9, 4),
    (37, 'leche', 2, 'https://images-na.ssl-images-amazon.com/images/I/416Ik-HVV1L.jpg', 'de cabra', 0, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 10, 1),
    (38, 'cafe', 1.3, 'https://www.baque.com/wp-content/uploads/2018/11/26115014-capsulas-la-coleccion-ecommerce-14uds-intenso-varanasi.jpg', 'mucha cafeina', 0, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 1, 1),
    (39, 'tortilla', 1.2, 'https://a0.soysuper.com/ea5cf12e0d8cf9d13c05804aec1ed8c8.1500.0.0.0.wmark.4dc90424.jpg', 'la buena tortilla', 0, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 1, 1),
    (40, 'queso', 34, 'https://quesospeinaovejas.com/119-large_default/queso-iberico-de-oveja-con-jamon-cuna.jpg', 'manchego', 0, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 1, 1),
    (41, 'fanta naranja', 1.2, 'http://sushimore.com/wp-content/uploads/2019/02/fantanaranjamore.jpg', 'con mucho gas', 10, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 1, 4),
    (42, 'bacon ahumado', 2, 'https://www.supermercadosmas.com/media/catalog/product/cache/e4d64343b1bc593f1c5348fe05efa4a6/i/m/import_catalog_images_01_40_014001_v8_7.jpg', 'oferta', 50, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 1, 9),
    (43, 'bacon', 2.2, 'https://image.flaticon.com/icons/png/512/372/372627.png', 'ekesokeo', 10, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 1, 9),
    (44, 'sal', 1.2, 'https://image.flaticon.com/icons/png/512/372/372627.png', 'eofkeokef ', 0, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 1, 9),
    (45, 'mandarinas', 1.2, 'https://image.flaticon.com/icons/png/512/372/372627.png', 'okekoedkedok', 20, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 2, 9),
    (46, 'minigalletitas', 2.1, 'https://image.flaticon.com/icons/png/512/372/372627.png', 'malisimas', 20, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 10, 9),
    (47, 'queso de burgos', 25, 'https://cadenaser00.epimg.net/ser/imagenes/2019/04/02/ser_ciudad_real/1554203477_864568_1554203890_noticia_normal_recorte1.jpg', 'qalite', 100, NULL, '2020-01-09 13:47:11', '2020-01-10 11:57:27', 1, 5);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

-- Volcando estructura para tabla mercadona.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '1: Usuario normal   2: Administrador',
  `nombre` varchar(15) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla mercadona.rol: ~2 rows (aproximadamente)
DELETE FROM `rol`;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` (`id`, `nombre`) VALUES
    (2, 'administrador'),
    (1, 'usuario');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;

-- Volcando estructura para tabla mercadona.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `contrasenia` varchar(50) NOT NULL DEFAULT '0',
  `id_rol` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FK_rol` (`id_rol`),
  CONSTRAINT `FK_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla mercadona.usuario: ~7 rows (aproximadamente)
DELETE FROM `usuario`;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nombre`, `contrasenia`, `id_rol`) VALUES
    (1, 'admin', 'admin', 2),
    (4, 'Dolores', '123456', 1),
    (5, 'jorge lorenzo', 'jorgeiii', 1),
    (6, 'angelon', 'angel', 1),
    (7, 'administrador', 'admin', 2),
    (8, 'felix', 'felix1234', 2),
    (9, 'pepita', '123456', 1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

-- Volcando estructura para procedimiento mercadona.pa_categoria_delete
DELIMITER //
CREATE PROCEDURE `pa_categoria_delete`(
	IN `pId` INT
)
BEGIN
 
	DELETE FROM categoria WHERE id = pId;
 
END//
DELIMITER ;

-- Volcando estructura para procedimiento mercadona.pa_categoria_getall
DELIMITER //
CREATE PROCEDURE `pa_categoria_getall`()
BEGIN
 
   	-- nuestro primer PA
   	/*  tiene pinta que tambien comentarios de bloque */
	SELECT id, nombre FROM categoria ORDER BY nombre ASC LIMIT 500;
    
	-- desde java executeQuery
	-- retorna ResultSet
 
END//
DELIMITER ;

-- Volcando estructura para procedimiento mercadona.pa_categoria_get_by_id
DELIMITER //
CREATE PROCEDURE `pa_categoria_get_by_id`(
	IN `pId` INT
)
BEGIN
 
	SELECT id, nombre FROM categoria WHERE id = pId;
 
END//
DELIMITER ;

-- Volcando estructura para procedimiento mercadona.pa_categoria_insert
DELIMITER //
CREATE PROCEDURE `pa_categoria_insert`(
	IN `p_nombre` VARCHAR(100),
	OUT `p_id` INT
)
BEGIN
 
	-- crear nuevo registro
	INSERT INTO categoria ( nombre ) VALUES ( p_nombre );
    
	-- obtener el ID generado y SETearlo al parametro salida p_id
	SET p_id = LAST_INSERT_ID();
    
 
END//
DELIMITER ;

-- Volcando estructura para procedimiento mercadona.pa_categoria_update
DELIMITER //
CREATE PROCEDURE `pa_categoria_update`(
	IN `pId` INT,
	IN `pNombre` VARCHAR(100)
)
BEGIN
 
 
	UPDATE categoria SET nombre = pNombre WHERE id = pId;
    
	-- desde java executeUpdate, retorna affectedRows int
 
END//
DELIMITER ;

-- Volcando estructura para función mercadona.HELLO_WORLD
DELIMITER //
CREATE FUNCTION `HELLO_WORLD`() RETURNS varchar(100) CHARSET utf8
BEGIN
 
	RETURN "hola mundo";
 
END//
DELIMITER ;

-- Volcando estructura para función mercadona.HELLO_WORLD2
DELIMITER //
CREATE FUNCTION `HELLO_WORLD2`(
	`pNombre` VARCHAR(50)
) RETURNS varchar(100) CHARSET utf8
BEGIN
 
	DECLARE nombre VARCHAR(100) DEFAULT 'anonimo';
 
   IF( TRIM(pNombre) != '' ) THEN
   	SET nombre = TRIM(pNombre);
   END IF;
 
	--	RETURN "Hello" +  pNombre;
	RETURN CONCAT("hello"," ",nombre);
 
END//
DELIMITER ;

-- Volcando estructura para disparador mercadona.tau_producto_historico
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tau_producto_historico` AFTER UPDATE ON `producto` FOR EACH ROW BEGIN
 
	-- meter un registro en la tabla historico con el precio del producto cambiado
    
	-- comprobar que inserte solo si cambia el precio
    
	IF ( NEW.precio <> OLD.precio ) THEN
    
    	INSERT INTO historico (precio, id_producto ) VALUES ( OLD.precio , OLD.id );
   	 
	END IF;
   	 
 
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador mercadona.tbi_producto
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tbi_producto` BEFORE INSERT ON `producto` FOR EACH ROW BEGIN
 
	/*
    	comprobar que el descuento sea entre 0 y 100
    	si descuento < 0 = 0
    	si descuento > 100 = 100
	*/
 
	IF NEW.descuento < 0 THEN
    	SET NEW.descuento = 0;
	END IF;
    
	IF NEW.descuento > 100 THEN
    	SET NEW.descuento = 100;
	END IF;    
 
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador mercadona.tbu_producto
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tbu_producto` BEFORE UPDATE ON `producto` FOR EACH ROW BEGIN
 
	IF NEW.descuento < 0 THEN
    	SET NEW.descuento = 0;
	END IF;
    
 
	IF NEW.descuento > 100 THEN
    	SET NEW.descuento = 100;
	END IF;    
 
	-- INSERT INTO categoria (nombre) VALUE ( CONCAT(NEW.descuento, 'categoria'));
 
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


