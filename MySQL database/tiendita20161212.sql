CREATE DATABASE  IF NOT EXISTS `tiendita` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `tiendita`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: tiendita
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `almacenes`
--

DROP TABLE IF EXISTS `almacenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `almacenes` (
  `almacenid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id del almacen',
  `nombre` varchar(45) NOT NULL COMMENT 'nombre del almacen',
  `descripcion` varchar(45) NOT NULL COMMENT 'descripcion del almacen',
  PRIMARY KEY (`almacenid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='Tabla donde se guardan los almacenes';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `articulos`
--

DROP TABLE IF EXISTS `articulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articulos` (
  `articuloid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id del articulo',
  `codigo` varchar(45) DEFAULT NULL COMMENT 'codigo del articulo',
  `nombre` varchar(45) NOT NULL COMMENT 'nombre del articulo',
  `descripcion` varchar(45) NOT NULL COMMENT 'descripcion del articulo',
  `precio` float NOT NULL COMMENT 'precio de venta del articulo',
  `costo` float NOT NULL COMMENT 'costo de compra del articulo',
  `imagen` longblob COMMENT 'Imagen del articulo',
  PRIMARY KEY (`articuloid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=latin1 COMMENT='tabla donde se guardan los articulos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `articuloscomodin`
--

DROP TABLE IF EXISTS `articuloscomodin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articuloscomodin` (
  `articulocomid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id del articulo comodin',
  `nombre` varchar(45) NOT NULL COMMENT 'nombre',
  `descripcion` varchar(45) NOT NULL COMMENT 'descripcion del articulo comodin',
  `precio` float NOT NULL COMMENT 'precio del articulo comodin',
  `costo` float NOT NULL COMMENT 'costo del articulo',
  PRIMARY KEY (`articulocomid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=911 DEFAULT CHARSET=latin1 COMMENT='tabla que guarda articulos comodin';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `clienteid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id del cliente',
  `nombre` varchar(45) NOT NULL COMMENT 'nombre del cliente',
  `apellidopat` varchar(45) NOT NULL COMMENT 'apellido paterno del cliente',
  `apellidomat` varchar(45) NOT NULL COMMENT 'apellido materno del cliente',
  `email` varchar(45) DEFAULT NULL COMMENT 'email del cliente',
  `usuario` varchar(45) DEFAULT NULL COMMENT 'usuario del cliente',
  `password` varchar(45) DEFAULT NULL COMMENT 'password del cliente',
  `foto` longblob COMMENT 'foto del cliente',
  `fechaalta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha en que se dio de alta el cliente',
  `info` text COMMENT 'information adicional del cliente',
  `creditomax` decimal(20,2) NOT NULL DEFAULT '10000.00' COMMENT 'el credito maximo que tiene el cliente',
  PRIMARY KEY (`clienteid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=latin1 COMMENT='tabla donde se guardan los clientes';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `insertPassTR` BEFORE INSERT ON `clientes` FOR EACH ROW BEGIN


#ENCRIPTAR LA CONTRASENA EN SHA1
IF new.password IS NOT NULL THEN
	SET new.password = sha1(new.password);
END IF;


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updatePassTR` BEFORE UPDATE ON `clientes` FOR EACH ROW BEGIN


/* */
 -- SET new.password = sha1(new.password);
/* */


#ENCRIPTAR LA CONTRASENA EN SHA1
IF length(new.password) > 0 THEN


	IF new.password != old.password THEN 
		SET new.password = sha1(new.password);
		#tambien actualizar la contrasena en la tabla mysql.user
        /*
        UPDATE mysql.user u 
        SET    u.password = new.password
        WHERE  u.host = vHost
        AND    u.user = new.usuclave; */
	END IF;
ELSE 
	SET new.password = old.password;
END IF; 

/* */

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `creditos`
--

DROP TABLE IF EXISTS `creditos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cliente_id` int(10) unsigned NOT NULL,
  `articulo_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `detdeudaxcliente`
--

DROP TABLE IF EXISTS `detdeudaxcliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detdeudaxcliente` (
  `deudaid` int(10) unsigned NOT NULL COMMENT 'id de la deuda',
  `partida` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'partida',
  `articuloid` int(10) unsigned DEFAULT NULL COMMENT 'id del articulo',
  `cantidad` int(10) unsigned NOT NULL COMMENT 'cantidad del articulo comprado',
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha de compra del articulo',
  `almacenid` int(10) unsigned DEFAULT NULL COMMENT 'almacen de donde se tomo el articulo',
  `articulocomid` int(10) unsigned DEFAULT NULL COMMENT 'articulo comidin id',
  `precio` float NOT NULL COMMENT 'precio con el que se vendio el articulo',
  `costo` float NOT NULL COMMENT 'costo de compra del articulo',
  PRIMARY KEY (`deudaid`,`partida`) USING BTREE,
  KEY `FK_detdeudaxcliente_articuloid` (`articuloid`),
  KEY `FK_detdeudaxcliente_almacenid` (`almacenid`),
  KEY `FK_detdeudaxcliente_articulocomid` (`articulocomid`),
  KEY `FK_detdeudaxcliente_existencia` (`articuloid`,`almacenid`),
  CONSTRAINT `FK_detdeudaxcliente_almacenid` FOREIGN KEY (`almacenid`) REFERENCES `almacenes` (`almacenid`),
  CONSTRAINT `FK_detdeudaxcliente_articulocomid` FOREIGN KEY (`articulocomid`) REFERENCES `articuloscomodin` (`articulocomid`),
  CONSTRAINT `FK_detdeudaxcliente_articuloid` FOREIGN KEY (`articuloid`) REFERENCES `articulos` (`articuloid`),
  CONSTRAINT `FK_detdeudaxcliente_deudaid` FOREIGN KEY (`deudaid`) REFERENCES `deudaxcliente` (`deudaid`),
  CONSTRAINT `FK_detdeudaxcliente_existencia` FOREIGN KEY (`articuloid`, `almacenid`) REFERENCES `existencias` (`articuloid`, `almacenid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='detalle de una deuda de un cliente';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TR_BEF_INSERT_DETDEUD` BEFORE INSERT ON `detdeudaxcliente` FOR EACH ROW BEGIN



#DECLARACIONES DE VARIABLES
DECLARE vPartidaSig INTEGER;

#DECLARACION DE CURSORES

DECLARE curSigPartida CURSOR FOR 
    SELECT IFNULL(max(partida)+1,1)
    FROM   detDeudaXCliente 
    WHERE deudaid = new.deudaid;

DECLARE curMonto CURSOR FOR 
    SELECT  sum(precio * cantidad)
    FROM   detDeudaXCliente 
    WHERE deudaid = new.deudaid;

    OPEN curSigPartida;

        FETCH curSigPartida INTO vPartidaSig;

    CLOSE curSigPartida;

    SET new.partida = vPartidaSig;

   #DESCONTAR DEL INVENTARIO
   UPDATE existencias e 
   SET e.cantidad = e.cantidad - new.cantidad,
          e.tipomov = 'VE'
   WHERE e.articuloid = new.articuloid
   AND     e.almacenid = new.almacenid;


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TR_AFT_INSERT_DETDEUD` AFTER INSERT ON `detdeudaxcliente` FOR EACH ROW BEGIN

#DECLARACIONES DE VARIABLES
DECLARE vMonto FLOAT;

#DECLARACION DE CURSORES

DECLARE curMonto CURSOR FOR 
    SELECT  sum(precio * cantidad)
    FROM   detDeudaXCliente 
    WHERE deudaid = new.deudaid;


   OPEN curMonto ;

    FETCH curMonto INTO vMonto;

    CLOSE curMonto ;


   #ACTUALIZAR EL MONTO TOTAL DE LA DEUDA
   UPDATE deudaxcliente
   SET monto = vMonto
   WHERE deudaid = new.deudaid;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `detdeudaxcliente_BUPD` BEFORE UPDATE ON `detdeudaxcliente` FOR EACH ROW

BEGIN

	IF new.cantidad > old.cantidad THEN
		#DESCONTAR DEL INVENTARIO
	   UPDATE existencias e 
	   SET e.cantidad = e.cantidad - (new.cantidad - old.cantidad),
			  e.tipomov = 'VE'
	   WHERE e.articuloid = new.articuloid
	   AND     e.almacenid = new.almacenid;

	else	
		#agregar al inventario
	   UPDATE existencias e 
	   SET e.cantidad = e.cantidad + (old.cantidad - new.cantidad),
			  e.tipomov = 'VE'
	   WHERE e.articuloid = new.articuloid
	   AND     e.almacenid = new.almacenid;
	END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TR_AFT_UPDATE_DETDEUD` AFTER UPDATE ON `detdeudaxcliente` FOR EACH ROW BEGIN



#DECLARACIONES DE VARIABLES
DECLARE vMonto FLOAT;

#DECLARACION DE CURSORES

DECLARE curMonto CURSOR FOR 
    SELECT  sum(precio * cantidad)
    FROM   detDeudaXCliente 
    WHERE deudaid = new.deudaid;


   OPEN curMonto ;

    FETCH curMonto INTO vMonto;

    CLOSE curMonto ;


   #ACTUALIZAR EL MONTO TOTAL DE LA DEUDA
   UPDATE deudaxcliente
   SET monto = vMonto
   WHERE deudaid = new.deudaid;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `detdeudaxcliente_BDEL` BEFORE DELETE ON `detdeudaxcliente` FOR EACH ROW
-- Edit trigger body code below this line. Do not edit lines above this one


BEGIN 


INSERT INTO historicoeliminado (descripcion, fecha )
                        VALUES (concat('detdeudaxcliente deudaid =', old.deudaid, 
                                        'partida = ' ,old.partida,
                                         'articuloid = ', ifnull(old.articuloid,''),
                                         'cantidad =',old.cantidad,
                                         'fecha added = ',old.fecha,
                                          'articuocomodinid = ',ifnull(old.articulocomid,''),
                                            'precio = ', old.precio),
                               now());


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TR_AFT_DELETE_DETDAUD` AFTER DELETE ON `detdeudaxcliente` FOR EACH ROW BEGIN

#DECLARACIONES DE VARIABLES
DECLARE vMonto FLOAT;

#DECLARACION DE CURSORES

DECLARE curMonto CURSOR FOR 
    SELECT  sum(precio * cantidad)
    FROM   detDeudaXCliente 
    WHERE deudaid = old.deudaid;


   OPEN curMonto ;

    FETCH curMonto INTO vMonto;

    CLOSE curMonto ;


   #ACTUALIZAR EL MONTO TOTAL DE LA DEUDA
   UPDATE deudaxcliente
   SET monto = ifnull(vMonto,0)
   WHERE deudaid = old.deudaid;


    #AGREGAR AL  INVENTARIO
   UPDATE existencias e 
   SET e.cantidad = e.cantidad + old.cantidad,
          e.tipomov = 'DE'
   WHERE e.articuloid = old.articuloid
   AND     e.almacenid = old.almacenid;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `deudaxcliente`
--

DROP TABLE IF EXISTS `deudaxcliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deudaxcliente` (
  `deudaid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id de la deuda',
  `clienteid` int(10) unsigned NOT NULL COMMENT 'id del clinete',
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha en que se le vendio el articulo al cliente',
  `fechaliquidada` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'fehca en que se liquida por completo la deuda',
  `liquidada` int(10) unsigned NOT NULL COMMENT 'indica si ya fue liquidada',
  `monto` float NOT NULL DEFAULT '0' COMMENT 'monto total de la deuda',
  PRIMARY KEY (`deudaid`) USING BTREE,
  KEY `fk_deudaxcliente_clienteid` (`clienteid`),
  CONSTRAINT `fk_deudaxcliente_clienteid` FOREIGN KEY (`clienteid`) REFERENCES `clientes` (`clienteid`)
) ENGINE=InnoDB AUTO_INCREMENT=954 DEFAULT CHARSET=latin1 COMMENT='se guarda una lista por cada cliente de todo lo que debe';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `existencias`
--

DROP TABLE IF EXISTS `existencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `existencias` (
  `articuloid` int(10) unsigned NOT NULL COMMENT 'id del articulo',
  `cantidad` int(10) NOT NULL COMMENT 'cantidad que hay del articulo en el almacen',
  `almacenid` int(10) unsigned NOT NULL COMMENT 'id del almacen',
  `tipomov` varchar(45) NOT NULL COMMENT 'tipo de movimiento EN (entrada) SA (salida) VE (venta) DE(devolucion)',
  PRIMARY KEY (`articuloid`,`almacenid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='tabla donde se guardan las existencias';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TR_INSERT_AFT_EXIST` AFTER INSERT ON `existencias` FOR EACH ROW BEGIN

#DECLARACIONES DE VARIABLES
DECLARE vCosto FLOAT;

#DECLARACION DE CURSORES
DECLARE curCostoArt CURSOR FOR 
    SELECT  costo
    FROM   articulos a 
    WHERE a.articuloid = new.articuloid;

   OPEN curCostoArt ;

   
   FETCH curCostoArt INTO vCosto;
  
   CLOSE curCostoArt ;


   #ACTUALIZAR EL MONTO TOTAL DE LA DEUDA

INSERT INTO historicoensa(articuloid, costo, cantidad, tipo)
     VALUES(new.articuloid, vCosto, new.cantidad, new.tipomov);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TR_UPDATE_AFT_EXIST` AFTER UPDATE ON `existencias` FOR EACH ROW BEGIN

#DECLARACIONES DE VARIABLES
DECLARE vCosto FLOAT;

#DECLARACION DE CURSORES
DECLARE curCostoArt CURSOR FOR 
    SELECT  costo
    FROM   articulos a 
    WHERE a.articuloid = new.articuloid;

   OPEN curCostoArt ;

   
   FETCH curCostoArt INTO vCosto;
  
   CLOSE curCostoArt ;


   #ACTUALIZAR EL MONTO TOTAL DE LA DEUDA

INSERT INTO historicoensa(articuloid, costo, cantidad, tipo)
     VALUES(new.articuloid, vCosto, new.cantidad, new.tipomov);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `historicoeliminado`
--

DROP TABLE IF EXISTS `historicoeliminado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historicoeliminado` (
  `idhistoricoeliminado` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` text NOT NULL,
  `fecha` timestamp NOT NULL,
  PRIMARY KEY (`idhistoricoeliminado`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='tabla que se creo despues para ver que es lo que se elimina';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `historicoensa`
--

DROP TABLE IF EXISTS `historicoensa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historicoensa` (
  `articuloid` int(10) unsigned NOT NULL COMMENT 'id del articulo',
  `costo` float NOT NULL COMMENT 'costo del articulo cuando entro',
  `cantidad` int(10) unsigned NOT NULL COMMENT 'cantidad que entro',
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha en que se hiso la entrada',
  `historicoid` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'id del historico',
  `tipo` varchar(45) NOT NULL COMMENT 'tipo EN (entrada) SA (salida)',
  PRIMARY KEY (`articuloid`,`historicoid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='historico de los productos que entran o salen';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TR_INSERT_BEF_HIS` BEFORE INSERT ON `historicoensa` FOR EACH ROW BEGIN

#DECLARACIONES DE VARIABLES
DECLARE vSecuencia INTEGER;

#DECLARACION DE CURSORES

DECLARE curSecuencia CURSOR FOR 
    SELECT IFNULL(max(historicoid)+1,1)
    FROM   historicoensa;


    OPEN curSecuencia ;

        FETCH curSecuencia INTO vSecuencia;

    CLOSE curSecuencia ;

    SET new.historicoid = vSecuencia ;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagos` (
  `deudaid` int(10) unsigned NOT NULL COMMENT 'deudaid ',
  `pagoid` int(10) unsigned NOT NULL DEFAULT '1' COMMENT 'partida o pagoid',
  `pago` float NOT NULL COMMENT 'monto del pago',
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha en que realizo el pago',
  PRIMARY KEY (`deudaid`,`pagoid`),
  CONSTRAINT `FK_pagos_deudaid` FOREIGN KEY (`deudaid`) REFERENCES `deudaxcliente` (`deudaid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='pagos que se realizan por deuda';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TR_INSERT_SEQPAG` BEFORE INSERT ON `pagos` FOR EACH ROW BEGIN

DECLARE vPagoSig INTEGER;

DECLARE curSigPago CURSOR FOR 
    SELECT IFNULL(max(pagoid)+1,1)
    FROM   pagos 
    WHERE deudaid = new.deudaid;

    OPEN curSigPago;

        FETCH curSigPago INTO vPagoSig;

    CLOSE curSigPago;

    SET new.pagoid = vPagoSig;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TR_AFT_INSERT_PAGOS` AFTER INSERT ON `pagos` FOR EACH ROW BEGIN

#DECLARACIONES DE VARIABLES
DECLARE vTotalPagos FLOAT;
DECLARE vMontoDeuda FLOAT;

#DECLARACION DE CURSORES

DECLARE curTotalPago CURSOR FOR 
    SELECT  sum(pago)
    FROM   pagos 
    WHERE deudaid = new.deudaid;

DECLARE curTotalDeuda CURSOR FOR 
    SELECT  monto
    FROM   DeudaXCliente 
    WHERE deudaid = new.deudaid;


   OPEN curTotalPago ;
    FETCH curTotalPago INTO vTotalPagos; 
    CLOSE curTotalPago ;


    OPEN curTotalDeuda ;
    FETCH curTotalDeuda INTO vMontoDeuda; 
    CLOSE curTotalDeuda ;

    IF vMontoDeuda = vTotalPagos THEN

           #ACTUALIZAR LIQUIDAA
   UPDATE deudaxcliente
   SET liquidada = 1,
   fechaliquidada = sysdate()
   WHERE deudaid = new.deudaid;


    END IF;


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `pagos_BDEL` BEFORE DELETE ON `pagos` FOR EACH ROW
-- Edit trigger body code below this line. Do not edit lines above this one

BEGIN


INSERT INTO historicoeliminado (descripcion, fecha )
                        VALUES (concat('pagos deudaid =', old.deudaid, 
                                        'pagoid = ' ,old.pagoid,
                                         'fecha = ', old.fecha,
                                         'pago', old.pago),
                               now());


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'tiendita'
--

--
-- Dumping routines for database 'tiendita'
--
/*!50003 DROP FUNCTION IF EXISTS `getTotalDeuda` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getTotalDeuda`(pClienteId INT) RETURNS decimal(20,2)
BEGIN

DECLARE vDeuda DECIMAL(20,2);

DECLARE curSumDeuda CURSOR FOR
	SELECT SUM(cantidad * precio)
     FROM (SELECT d.deudaid, d.clienteid, d.monto, dt.fecha,
	            dt.partida,dt.articuloid, dt.precio,dt.cantidad,
	            a.nombre, a.descripcion, a.imagen
		FROM deudaxcliente d, articulos a, detdeudaxcliente dt
		WHERE d.clienteid = pClienteId
		AND d.liquidada = 0
		AND dt.deudaid = d.deudaid
		AND a.articuloid = dt.articuloid
		UNION
		SELECT d.deudaid, d.clienteid,d.monto ,dt.fecha,
			   dt.partida,dt.articulocomid, dt.precio,dt.cantidad, 
			   a.nombre, a.descripcion, ifnull(null,'')
		FROM deudaxcliente d, articuloscomodin a, detdeudaxcliente dt
		WHERE d.clienteid = pClienteId
		AND d.liquidada = 0
		AND dt.deudaid = d.deudaid
		AND a.articulocomid = dt.articulocomid
		UNION
		SELECT p.deudaid, ifnull(null,''),p.pago*-1 monto,p.fecha,
				 ifnull(null,''), ifnull(null,''),p.pago*-1 precio, 1,
				 ifnull(null,''), 'pago', ''
		FROM pagos p, clientes c, deudaxcliente d
		WHERE d.clienteid = c.clienteid
		AND p.deudaid = d.deudaid
		AND d.liquidada = 0
		AND c.clienteid = pClienteId) v;


OPEN curSumDeuda;
FETCH curSumDeuda INTO vDeuda;
CLOSE curSumDeuda;


RETURN vDeuda;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-15 16:56:25
