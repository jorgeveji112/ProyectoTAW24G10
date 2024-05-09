-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bdgym
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bdgym
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bdgym` DEFAULT CHARACTER SET latin1 ;
USE `bdgym` ;

-- -----------------------------------------------------
-- Table `bdgym`.`tipoentrenamiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`tipoentrenamiento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('body-building', 'cross-training') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`trol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`trol` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rol` ENUM('admin', 'entrenador', 'cliente') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `apellidos` VARCHAR(100) NOT NULL,
  `fecha_nacimiento` DATE NOT NULL,
  `dni` VARCHAR(10) NOT NULL,
  `genero` VARCHAR(30) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(10) NOT NULL,
  `fecha_ingreso` DATE NULL DEFAULT NULL,
  `nombre_usuario` VARCHAR(30) NOT NULL,
  `contraseña` VARCHAR(30) NOT NULL,
  `validado` TINYINT NOT NULL,
  `tipoentrenamiento_id` INT NOT NULL,
  `trol_id` INT NOT NULL,
  `usuario_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) ,
  UNIQUE INDEX `telefono_UNIQUE` (`telefono` ASC) ,
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) ,
  UNIQUE INDEX `nombreUsuario_UNIQUE` (`nombre_usuario` ASC) ,
  INDEX `fk_usuario_tipoEntrenamiento1_idx` (`tipoentrenamiento_id` ASC) ,
  INDEX `fk_usuario_tRol1_idx` (`trol_id` ASC) ,
  INDEX `fk_usuario_usuario1_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_usuario_tipoEntrenamiento1`
    FOREIGN KEY (`tipoentrenamiento_id`)
    REFERENCES `bdgym`.`tipoentrenamiento` (`id`),
  CONSTRAINT `fk_usuario_tRol1`
    FOREIGN KEY (`trol_id`)
    REFERENCES `bdgym`.`trol` (`id`),
  CONSTRAINT `fk_usuario_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`cliente` (
  `usuario_id` INT NOT NULL,
  `peso` FLOAT NULL DEFAULT NULL,
  `altura` FLOAT NULL DEFAULT NULL,
  `objetivos` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  INDEX `fk_cliente_usuario1_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_cliente_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`tipoejerciciobodybuilding`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`tipoejerciciobodybuilding` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('pierna', 'hombro', 'pecho', 'espalda', 'biceps', 'triceps') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`tipoejerciciocrosstraining`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`tipoejerciciocrosstraining` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('fuerza/resistencia', 'capacidad aeróbica', 'velocidad/potencia', 'estabilidad', 'movilidad') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`ejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`ejercicio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(250) NOT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  `video` VARCHAR(250) NULL DEFAULT NULL,
  `tipoentrenamiento_id` INT NULL DEFAULT NULL,
  `tipoejerciciobodybuilding_id` INT NULL DEFAULT NULL,
  `tipoejerciciocrosstraining_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ejercicio_tipoEntrenamiento1_idx` (`tipoentrenamiento_id` ASC) ,
  INDEX `fk_ejercicio_tipoEjercicioBodyBuilding1_idx` (`tipoejerciciobodybuilding_id` ASC) ,
  INDEX `fk_ejercicio_tipoEjercicioCrossTraining1_idx` (`tipoejerciciocrosstraining_id` ASC),
  CONSTRAINT `fk_ejercicio_tipoEjercicioBodyBuilding1`
    FOREIGN KEY (`tipoejerciciobodybuilding_id`)
    REFERENCES `bdgym`.`tipoejerciciobodybuilding` (`id`),
  CONSTRAINT `fk_ejercicio_tipoEjercicioCrossTraining1`
    FOREIGN KEY (`tipoejerciciocrosstraining_id`)
    REFERENCES `bdgym`.`tipoejerciciocrosstraining` (`id`),
  CONSTRAINT `fk_ejercicio_tipoEntrenamiento1`
    FOREIGN KEY (`tipoentrenamiento_id`)
    REFERENCES `bdgym`.`tipoentrenamiento` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_predefinida`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_predefinida` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `objetivos` VARCHAR(250) NULL DEFAULT NULL,
  `usuario_id` INT NOT NULL,
  `tipoentrenamiento_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_rutina_predefinida_usuario1_idx` (`usuario_id` ASC) ,
  INDEX `fk_rutina_predefinida_tipoEntrenamiento1_idx` (`tipoentrenamiento_id` ASC) ,
  CONSTRAINT `fk_rutina_predefinida_tipoEntrenamiento1`
    FOREIGN KEY (`tipoentrenamiento_id`)
    REFERENCES `bdgym`.`tipoentrenamiento` (`id`),
  CONSTRAINT `fk_rutina_predefinida_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_asignada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_asignada` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rutina_predefinida_id` INT NOT NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_rutina_asignada_rutina_predefinida1_idx` (`rutina_predefinida_id` ASC) ,
  INDEX `fk_rutina_asignada_usuario1_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_rutina_asignada_rutina_predefinida1`
    FOREIGN KEY (`rutina_predefinida_id`)
    REFERENCES `bdgym`.`rutina_predefinida` (`id`),
  CONSTRAINT `fk_rutina_asignada_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`sesionentrenamiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`sesionentrenamiento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sesionentrenamiento_usuario1_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_sesionentrenamiento_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_sesionentrenamiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_sesionentrenamiento` (
  `sesionentrenamiento_id` INT NOT NULL,
  `rutina_predefinida_id` INT NOT NULL,
  `posicion` INT NULL DEFAULT NULL,
  PRIMARY KEY (`sesionentrenamiento_id`, `rutina_predefinida_id`),
  INDEX `fk_rutina_sesionentrenamiento_sesionentrenamiento1_idx` (`sesionentrenamiento_id` ASC) ,
  INDEX `fk_rutina_sesionentrenamiento_rutina_predefinida1_idx` (`rutina_predefinida_id` ASC) ,
  CONSTRAINT `fk_rutina_sesionentrenamiento_rutina_predefinida1`
    FOREIGN KEY (`rutina_predefinida_id`)
    REFERENCES `bdgym`.`rutina_predefinida` (`id`),
  CONSTRAINT `fk_rutina_sesionentrenamiento_sesionentrenamiento1`
    FOREIGN KEY (`sesionentrenamiento_id`)
    REFERENCES `bdgym`.`sesionentrenamiento` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`sesionejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`sesionejercicio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `series` INT NULL DEFAULT NULL,
  `repeticiones` INT NULL DEFAULT NULL,
  `duracion` INT NULL DEFAULT NULL,
  `ejercicio_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sesionejercicio_ejercicio1_idx` (`ejercicio_id` ASC) ,
  CONSTRAINT `fk_sesionejercicio_ejercicio1`
    FOREIGN KEY (`ejercicio_id`)
    REFERENCES `bdgym`.`ejercicio` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`sesionentrenamiento_has_sesionejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`sesionentrenamiento_has_sesionejercicio` (
  `sesionentrenamiento_id` INT NOT NULL,
  `sesionejercicio_id` INT NOT NULL,
  `posicion` INT NULL DEFAULT NULL,
  PRIMARY KEY (`sesionentrenamiento_id`, `sesionejercicio_id`),
  INDEX `fk_sesionentrenamiento_has_sesionejercicio_sesionejercicio1_idx` (`sesionejercicio_id` ASC) ,
  INDEX `fk_sesionentrenamiento_has_sesionejercicio_sesionentrenamie_idx` (`sesionentrenamiento_id` ASC) ,
  CONSTRAINT `fk_sesionentrenamiento_has_sesionejercicio_sesionejercicio1`
    FOREIGN KEY (`sesionejercicio_id`)
    REFERENCES `bdgym`.`sesionejercicio` (`id`),
  CONSTRAINT `fk_sesionentrenamiento_has_sesionejercicio_sesionentrenamiento1`
    FOREIGN KEY (`sesionentrenamiento_id`)
    REFERENCES `bdgym`.`sesionentrenamiento` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`valoracion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`valoracion` (
  `puntuacion` INT NULL DEFAULT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  `usuario_id` INT NOT NULL,
  `sesionejercicio_id` INT NOT NULL,
  `rutina_asignada_id` INT NOT NULL,
  PRIMARY KEY (`usuario_id`, `sesionejercicio_id`, `rutina_asignada_id`),
  INDEX `fk_valoracion_usuario1_idx` (`usuario_id` ASC) ,
  INDEX `fk_valoracion_sesionejercicio1_idx` (`sesionejercicio_id` ASC) ,
  INDEX `fk_valoracion_rutina_asignada1_idx` (`rutina_asignada_id` ASC) ,
  CONSTRAINT `fk_valoracion_rutina_asignada1`
    FOREIGN KEY (`rutina_asignada_id`)
    REFERENCES `bdgym`.`rutina_asignada` (`id`),
  CONSTRAINT `fk_valoracion_sesionejercicio1`
    FOREIGN KEY (`sesionejercicio_id`)
    REFERENCES `bdgym`.`sesionejercicio` (`id`),
  CONSTRAINT `fk_valoracion_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
