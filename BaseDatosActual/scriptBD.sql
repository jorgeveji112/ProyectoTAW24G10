-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bdgym
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `usuario_id` int NOT NULL,
  `peso` float DEFAULT NULL,
  `altura` float DEFAULT NULL,
  `objetivos` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  KEY `fk_cliente_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_cliente_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejercicio`
--

DROP TABLE IF EXISTS `ejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `video` varchar(250) DEFAULT NULL,
  `tipoentrenamiento_id` int DEFAULT NULL,
  `tipoejerciciobodybuilding_id` int DEFAULT NULL,
  `tipoejerciciocrosstraining_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ejercicio_tipoEntrenamiento1_idx` (`tipoentrenamiento_id`),
  KEY `fk_ejercicio_tipoEjercicioBodyBuilding1_idx` (`tipoejerciciobodybuilding_id`),
  KEY `fk_ejercicio_tipoEjercicioCrossTraining1_idx` (`tipoejerciciocrosstraining_id`),
  CONSTRAINT `fk_ejercicio_tipoEjercicioBodyBuilding1` FOREIGN KEY (`tipoejerciciobodybuilding_id`) REFERENCES `tipoejerciciobodybuilding` (`id`),
  CONSTRAINT `fk_ejercicio_tipoEjercicioCrossTraining1` FOREIGN KEY (`tipoejerciciocrosstraining_id`) REFERENCES `tipoejerciciocrosstraining` (`id`),
  CONSTRAINT `fk_ejercicio_tipoEntrenamiento1` FOREIGN KEY (`tipoentrenamiento_id`) REFERENCES `tipoentrenamiento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicio`
--

LOCK TABLES `ejercicio` WRITE;
/*!40000 ALTER TABLE `ejercicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `ejercicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutina_asignada`
--

DROP TABLE IF EXISTS `rutina_asignada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rutina_asignada` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rutina_predefinida_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rutina_asignada_rutina_predefinida1_idx` (`rutina_predefinida_id`),
  KEY `fk_rutina_asignada_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_rutina_asignada_rutina_predefinida1` FOREIGN KEY (`rutina_predefinida_id`) REFERENCES `rutina_predefinida` (`id`),
  CONSTRAINT `fk_rutina_asignada_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutina_asignada`
--

LOCK TABLES `rutina_asignada` WRITE;
/*!40000 ALTER TABLE `rutina_asignada` DISABLE KEYS */;
/*!40000 ALTER TABLE `rutina_asignada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutina_predefinida`
--

DROP TABLE IF EXISTS `rutina_predefinida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rutina_predefinida` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `objetivos` varchar(250) DEFAULT NULL,
  `usuario_id` int NOT NULL,
  `tipoentrenamiento_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rutina_predefinida_usuario1_idx` (`usuario_id`),
  KEY `fk_rutina_predefinida_tipoEntrenamiento1_idx` (`tipoentrenamiento_id`),
  CONSTRAINT `fk_rutina_predefinida_tipoEntrenamiento1` FOREIGN KEY (`tipoentrenamiento_id`) REFERENCES `tipoentrenamiento` (`id`),
  CONSTRAINT `fk_rutina_predefinida_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutina_predefinida`
--

LOCK TABLES `rutina_predefinida` WRITE;
/*!40000 ALTER TABLE `rutina_predefinida` DISABLE KEYS */;
/*!40000 ALTER TABLE `rutina_predefinida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutina_sesionentrenamiento`
--

DROP TABLE IF EXISTS `rutina_sesionentrenamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rutina_sesionentrenamiento` (
  `sesionentrenamiento_id` int NOT NULL,
  `rutina_predefinida_id` int NOT NULL,
  `posicion` int DEFAULT NULL,
  PRIMARY KEY (`sesionentrenamiento_id`,`rutina_predefinida_id`),
  KEY `fk_rutina_sesionentrenamiento_sesionentrenamiento1_idx` (`sesionentrenamiento_id`),
  KEY `fk_rutina_sesionentrenamiento_rutina_predefinida1_idx` (`rutina_predefinida_id`),
  CONSTRAINT `fk_rutina_sesionentrenamiento_rutina_predefinida1` FOREIGN KEY (`rutina_predefinida_id`) REFERENCES `rutina_predefinida` (`id`),
  CONSTRAINT `fk_rutina_sesionentrenamiento_sesionentrenamiento1` FOREIGN KEY (`sesionentrenamiento_id`) REFERENCES `sesionentrenamiento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutina_sesionentrenamiento`
--

LOCK TABLES `rutina_sesionentrenamiento` WRITE;
/*!40000 ALTER TABLE `rutina_sesionentrenamiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `rutina_sesionentrenamiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sesionejercicio`
--

DROP TABLE IF EXISTS `sesionejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sesionejercicio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `series` int DEFAULT NULL,
  `repeticiones` int DEFAULT NULL,
  `duracion` int DEFAULT NULL,
  `ejercicio_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sesionejercicio_ejercicio1_idx` (`ejercicio_id`),
  CONSTRAINT `fk_sesionejercicio_ejercicio1` FOREIGN KEY (`ejercicio_id`) REFERENCES `ejercicio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sesionejercicio`
--

LOCK TABLES `sesionejercicio` WRITE;
/*!40000 ALTER TABLE `sesionejercicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `sesionejercicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sesionentrenamiento`
--

DROP TABLE IF EXISTS `sesionentrenamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sesionentrenamiento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `usuario_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sesionentrenamiento_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_sesionentrenamiento_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sesionentrenamiento`
--

LOCK TABLES `sesionentrenamiento` WRITE;
/*!40000 ALTER TABLE `sesionentrenamiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `sesionentrenamiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sesionentrenamiento_has_sesionejercicio`
--

DROP TABLE IF EXISTS `sesionentrenamiento_has_sesionejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sesionentrenamiento_has_sesionejercicio` (
  `sesionentrenamiento_id` int NOT NULL,
  `sesionejercicio_id` int NOT NULL,
  `posicion` int DEFAULT NULL,
  PRIMARY KEY (`sesionentrenamiento_id`,`sesionejercicio_id`),
  KEY `fk_sesionentrenamiento_has_sesionejercicio_sesionejercicio1_idx` (`sesionejercicio_id`),
  KEY `fk_sesionentrenamiento_has_sesionejercicio_sesionentrenamie_idx` (`sesionentrenamiento_id`),
  CONSTRAINT `fk_sesionentrenamiento_has_sesionejercicio_sesionejercicio1` FOREIGN KEY (`sesionejercicio_id`) REFERENCES `sesionejercicio` (`id`),
  CONSTRAINT `fk_sesionentrenamiento_has_sesionejercicio_sesionentrenamiento1` FOREIGN KEY (`sesionentrenamiento_id`) REFERENCES `sesionentrenamiento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sesionentrenamiento_has_sesionejercicio`
--

LOCK TABLES `sesionentrenamiento_has_sesionejercicio` WRITE;
/*!40000 ALTER TABLE `sesionentrenamiento_has_sesionejercicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `sesionentrenamiento_has_sesionejercicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoejerciciobodybuilding`
--

DROP TABLE IF EXISTS `tipoejerciciobodybuilding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipoejerciciobodybuilding` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('pierna','hombro','pecho','espalda','biceps','triceps') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoejerciciobodybuilding`
--

LOCK TABLES `tipoejerciciobodybuilding` WRITE;
/*!40000 ALTER TABLE `tipoejerciciobodybuilding` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoejerciciobodybuilding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoejerciciocrosstraining`
--

DROP TABLE IF EXISTS `tipoejerciciocrosstraining`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipoejerciciocrosstraining` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('fuerza/resistencia','capacidad aeróbica','velocidad/potencia','estabilidad','movilidad') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoejerciciocrosstraining`
--

LOCK TABLES `tipoejerciciocrosstraining` WRITE;
/*!40000 ALTER TABLE `tipoejerciciocrosstraining` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoejerciciocrosstraining` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoentrenamiento`
--

DROP TABLE IF EXISTS `tipoentrenamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipoentrenamiento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('body-building','cross-training') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoentrenamiento`
--

LOCK TABLES `tipoentrenamiento` WRITE;
/*!40000 ALTER TABLE `tipoentrenamiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipoentrenamiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trol`
--

DROP TABLE IF EXISTS `trol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trol` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rol` enum('admin','entrenador','cliente') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trol`
--

LOCK TABLES `trol` WRITE;
/*!40000 ALTER TABLE `trol` DISABLE KEYS */;
/*!40000 ALTER TABLE `trol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `dni` varchar(10) NOT NULL,
  `genero` varchar(30) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `fecha_ingreso` date DEFAULT NULL,
  `nombre_usuario` varchar(30) NOT NULL,
  `contraseña` varchar(30) NOT NULL,
  `validado` tinyint NOT NULL,
  `tipoentrenamiento_id` int NOT NULL,
  `trol_id` int NOT NULL,
  `usuario_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo_UNIQUE` (`correo`),
  UNIQUE KEY `telefono_UNIQUE` (`telefono`),
  UNIQUE KEY `dni_UNIQUE` (`dni`),
  UNIQUE KEY `nombreUsuario_UNIQUE` (`nombre_usuario`),
  KEY `fk_usuario_tipoEntrenamiento1_idx` (`tipoentrenamiento_id`),
  KEY `fk_usuario_tRol1_idx` (`trol_id`),
  KEY `fk_usuario_usuario1_idx` (`usuario_id`),
  CONSTRAINT `fk_usuario_tipoEntrenamiento1` FOREIGN KEY (`tipoentrenamiento_id`) REFERENCES `tipoentrenamiento` (`id`),
  CONSTRAINT `fk_usuario_tRol1` FOREIGN KEY (`trol_id`) REFERENCES `trol` (`id`),
  CONSTRAINT `fk_usuario_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valoracion`
--

DROP TABLE IF EXISTS `valoracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `valoracion` (
  `puntuacion` int DEFAULT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `usuario_id` int NOT NULL,
  `sesionejercicio_id` int NOT NULL,
  `rutina_asignada_id` int NOT NULL,
  PRIMARY KEY (`usuario_id`,`sesionejercicio_id`,`rutina_asignada_id`),
  KEY `fk_valoracion_usuario1_idx` (`usuario_id`),
  KEY `fk_valoracion_sesionejercicio1_idx` (`sesionejercicio_id`),
  KEY `fk_valoracion_rutina_asignada1_idx` (`rutina_asignada_id`),
  CONSTRAINT `fk_valoracion_rutina_asignada1` FOREIGN KEY (`rutina_asignada_id`) REFERENCES `rutina_asignada` (`id`),
  CONSTRAINT `fk_valoracion_sesionejercicio1` FOREIGN KEY (`sesionejercicio_id`) REFERENCES `sesionejercicio` (`id`),
  CONSTRAINT `fk_valoracion_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valoracion`
--

LOCK TABLES `valoracion` WRITE;
/*!40000 ALTER TABLE `valoracion` DISABLE KEYS */;
/*!40000 ALTER TABLE `valoracion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-06 21:00:36
