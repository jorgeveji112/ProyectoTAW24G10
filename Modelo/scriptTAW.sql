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
-- Table `bdgym`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombreUsuario` VARCHAR(30) NOT NULL,
  `contraseña` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nombreUsuario_UNIQUE` (`nombreUsuario` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdgym`.`administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`administrador` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NULL,
  `apelidos` VARCHAR(100) NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_administrador_usuario1_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_administrador_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`cliente` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `apellidos` VARCHAR(100) NOT NULL,
  `fechaNacimiento` DATE NOT NULL,
  `dni` VARCHAR(10) NULL DEFAULT NULL,
  `genero` VARCHAR(30) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(10) NOT NULL,
  `altura` FLOAT NOT NULL,
  `peso` FLOAT NOT NULL,
  `tipoEntrenamiento` ENUM('fuerza', 'cross') NOT NULL,
  `objetivos` VARCHAR(250) NULL DEFAULT NULL,
  `fechaIngreso` DATE NULL DEFAULT NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) ,
  UNIQUE INDEX `telefono_UNIQUE` (`telefono` ASC) ,
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) ,
  INDEX `fk_cliente_usuario1_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_cliente_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`ejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`ejercicio` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `tipo` VARCHAR(100) NOT NULL,
  `subtipo` VARCHAR(100) NULL DEFAULT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  `video` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`entrenador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`entrenador` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `apellidos` VARCHAR(100) NOT NULL,
  `fechaNacimiento` DATE NOT NULL,
  `genero` VARCHAR(30) NOT NULL,
  `dni` VARCHAR(10) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(10) NOT NULL,
  `tipoEntrenamiento` ENUM('fuerza', 'cross') NOT NULL,
  `fechaIngreso` DATE NOT NULL,
  `usuario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) ,
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) ,
  UNIQUE INDEX `telefono_UNIQUE` (`telefono` ASC) ,
  INDEX `fk_entrenador_usuario_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_entrenador_usuario`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_predefinida`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_predefinida` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(100) NULL,
  `objetivos` VARCHAR(250) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_asignada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_asignada` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(100) NULL DEFAULT NULL,
  `objetivos` VARCHAR(250) NULL DEFAULT NULL,
  `rutina_predefinida_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_rutina_asignada_rutina_predefinida1_idx` (`rutina_predefinida_id` ASC) ,
  CONSTRAINT `fk_rutina_asignada_rutina_predefinida1`
    FOREIGN KEY (`rutina_predefinida_id`)
    REFERENCES `bdgym`.`rutina_predefinida` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`sesionentrenamiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`sesionentrenamiento` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`sesionejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`sesionejercicio` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `series` INT NULL,
  `repeticiones` INT NULL,
  `duracion` INT NULL,
  `ejercicio_id` INT(11) NOT NULL,
  `sesionentrenamiento_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sesionejercicio_ejercicio1_idx` (`ejercicio_id` ASC) ,
  INDEX `fk_sesionejercicio_sesionentrenamiento1_idx` (`sesionentrenamiento_id` ASC) ,
  CONSTRAINT `fk_sesionejercicio_ejercicio1`
    FOREIGN KEY (`ejercicio_id`)
    REFERENCES `bdgym`.`ejercicio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sesionejercicio_sesionentrenamiento1`
    FOREIGN KEY (`sesionentrenamiento_id`)
    REFERENCES `bdgym`.`sesionentrenamiento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`solicitudcliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`solicitudcliente` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(100) NOT NULL,
  `fechaNacimiento` DATE NOT NULL,
  `genero` VARCHAR(30) NOT NULL,
  `dni` VARCHAR(10) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(10) NOT NULL,
  `altura` FLOAT NOT NULL,
  `peso` FLOAT NOT NULL,
  `tipoEntrenamiento` ENUM('fuerza', 'cross') NOT NULL,
  `objetivos` VARCHAR(250) NULL DEFAULT NULL,
  `nombreUsuario` VARCHAR(30) NOT NULL,
  `contraseña` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) ,
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) ,
  UNIQUE INDEX `telefono_UNIQUE` (`telefono` ASC) ,
  UNIQUE INDEX `nombreUsuario_UNIQUE` (`nombreUsuario` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`solicitudentrenador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`solicitudentrenador` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(100) NOT NULL,
  `fechaNacimiento` DATE NOT NULL,
  `genero` VARCHAR(30) NOT NULL,
  `dni` VARCHAR(10) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(10) NOT NULL,
  `tipoEntrenamiento` ENUM('fuerza', 'cross') NOT NULL,
  `nombreUsuario` VARCHAR(30) NOT NULL,
  `contraseña` VARCHAR(30) NOT NULL,
  `cv` VARCHAR(50) NULL DEFAULT NULL,
  `comentario` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nombreUsuario_UNIQUE` (`nombreUsuario` ASC) ,
  UNIQUE INDEX `telefono_UNIQUE` (`telefono` ASC) ,
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) ,
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`valoracion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`valoracion` (
  `cliente_id` INT(11) NOT NULL,
  `rutina_asignada_id` INT(11) NOT NULL,
  `sesionejercicio_id` INT(11) NOT NULL,
  `puntuacion` INT(11) NULL DEFAULT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  INDEX `fk_valoracion_cliente1_idx` (`cliente_id` ASC) ,
  PRIMARY KEY (`cliente_id`, `rutina_asignada_id`, `sesionejercicio_id`),
  INDEX `fk_valoracion_sesionejercicio1_idx` (`sesionejercicio_id` ASC) ,
  INDEX `fk_valoracion_rutina_asignada1_idx` (`rutina_asignada_id` ASC) ,
  CONSTRAINT `fk_valoracion_cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `bdgym`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_valoracion_sesionejercicio1`
    FOREIGN KEY (`sesionejercicio_id`)
    REFERENCES `bdgym`.`sesionejercicio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_valoracion_rutina_asignada1`
    FOREIGN KEY (`rutina_asignada_id`)
    REFERENCES `bdgym`.`rutina_asignada` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_sesionentrenamiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_sesionentrenamiento` (
  `sesionentrenamiento_id` INT(11) NOT NULL,
  `rutina_predefinida_id` INT NOT NULL,
  INDEX `fk_rutina_sesionentrenamiento_sesionentrenamiento1_idx` (`sesionentrenamiento_id` ASC) ,
  PRIMARY KEY (`sesionentrenamiento_id`, `rutina_predefinida_id`),
  INDEX `fk_rutina_sesionentrenamiento_rutina_predefinida1_idx` (`rutina_predefinida_id` ASC) ,
  CONSTRAINT `fk_rutina_sesionentrenamiento_sesionentrenamiento1`
    FOREIGN KEY (`sesionentrenamiento_id`)
    REFERENCES `bdgym`.`sesionentrenamiento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rutina_sesionentrenamiento_rutina_predefinida1`
    FOREIGN KEY (`rutina_predefinida_id`)
    REFERENCES `bdgym`.`rutina_predefinida` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_cliente` (
  `rutina_id` INT(11) NOT NULL,
  `cliente_id` INT(11) NOT NULL,
  INDEX `fk_rutina_cliente_rutina1_idx` (`rutina_id` ASC),
  INDEX `fk_rutina_cliente_cliente1_idx` (`cliente_id` ASC),
  PRIMARY KEY (`rutina_id`, `cliente_id`),
  CONSTRAINT `fk_rutina_cliente_rutina1`
    FOREIGN KEY (`rutina_id`)
    REFERENCES `bdgym`.`rutina_asignada` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rutina_cliente_cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `bdgym`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
