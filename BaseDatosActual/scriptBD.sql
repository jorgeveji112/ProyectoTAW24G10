DROP SCHEMA IF EXISTS `bdgym` ;

-- -----------------------------------------------------
-- Schema bdgym
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bdgym` DEFAULT CHARACTER SET latin1 ;
USE `bdgym` ;

-- -----------------------------------------------------
-- Table `bdgym`.`tipoentrenamiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`tipoentrenamiento` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('body-building', 'cross-training') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`tRol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`tRol` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rol` ENUM('admin', 'entrenador', 'cliente') NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdgym`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`usuario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `apellidos` VARCHAR(100) NOT NULL,
  `fechaNacimiento` DATE NOT NULL,
  `dni` VARCHAR(10) NOT NULL,
  `genero` VARCHAR(30) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(10) NOT NULL,
  `fechaIngreso` DATE NULL DEFAULT NULL,
  `nombreUsuario` VARCHAR(30) NOT NULL,
  `contraseña` VARCHAR(30) NOT NULL,
  `validado` TINYINT(4) NOT NULL,
  `tipoEntrenamiento_id` INT(11) NOT NULL,
  `tRol_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC) ,
  UNIQUE INDEX `telefono_UNIQUE` (`telefono` ASC) ,
  UNIQUE INDEX `dni_UNIQUE` (`dni` ASC) ,
  UNIQUE INDEX `nombreUsuario_UNIQUE` (`nombreUsuario` ASC) ,
  INDEX `fk_usuario_tipoEntrenamiento1_idx` (`tipoEntrenamiento_id` ASC) ,
  INDEX `fk_usuario_tRol1_idx` (`tRol_id` ASC) ,
  CONSTRAINT `fk_usuario_tipoEntrenamiento1`
    FOREIGN KEY (`tipoEntrenamiento_id`)
    REFERENCES `bdgym`.`tipoentrenamiento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_tRol1`
    FOREIGN KEY (`tRol_id`)
    REFERENCES `bdgym`.`tRol` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`cliente` (
  `usuario_id` INT(11) NOT NULL,
  `peso` FLOAT NULL DEFAULT NULL,
  `altura` FLOAT NULL DEFAULT NULL,
  `objetivos` VARCHAR(250) NULL DEFAULT NULL,
  INDEX `fk_cliente_usuario1_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_cliente_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`tipoejerciciobodybuilding`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`tipoejerciciobodybuilding` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('pierna', 'hombro', 'pecho', 'espalda', 'biceps', 'triceps') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`tipoejerciciocrosstraining`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`tipoejerciciocrosstraining` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('fuerza/resistencia', 'capacidad aeróbica', 'velocidad/potencia', 'estabilidad', 'movilidad') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`ejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`ejercicio` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(250) NOT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  `video` VARCHAR(250) NULL DEFAULT NULL,
  `tipoEntrenamiento_id` INT(11) NULL DEFAULT NULL,
  `tipoEjercicioBodyBuilding_id` INT(11) NULL DEFAULT NULL,
  `tipoEjercicioCrossTraining_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ejercicio_tipoEntrenamiento1_idx` (`tipoEntrenamiento_id` ASC) ,
  INDEX `fk_ejercicio_tipoEjercicioBodyBuilding1_idx` (`tipoEjercicioBodyBuilding_id` ASC) ,
  INDEX `fk_ejercicio_tipoEjercicioCrossTraining1_idx` (`tipoEjercicioCrossTraining_id` ASC) ,
  CONSTRAINT `fk_ejercicio_tipoEjercicioBodyBuilding1`
    FOREIGN KEY (`tipoEjercicioBodyBuilding_id`)
    REFERENCES `bdgym`.`tipoejerciciobodybuilding` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ejercicio_tipoEjercicioCrossTraining1`
    FOREIGN KEY (`tipoEjercicioCrossTraining_id`)
    REFERENCES `bdgym`.`tipoejerciciocrosstraining` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ejercicio_tipoEntrenamiento1`
    FOREIGN KEY (`tipoEntrenamiento_id`)
    REFERENCES `bdgym`.`tipoentrenamiento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_predefinida`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_predefinida` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `objetivos` VARCHAR(250) NULL DEFAULT NULL,
  `usuario_id` INT(11) NOT NULL,
  `tipoEntrenamiento_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_rutina_predefinida_usuario1_idx` (`usuario_id` ASC) ,
  INDEX `fk_rutina_predefinida_tipoEntrenamiento1_idx` (`tipoEntrenamiento_id` ASC) ,
  CONSTRAINT `fk_rutina_predefinida_tipoEntrenamiento1`
    FOREIGN KEY (`tipoEntrenamiento_id`)
    REFERENCES `bdgym`.`tipoentrenamiento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rutina_predefinida_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_asignada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_asignada` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `rutina_predefinida_id` INT(11) NOT NULL,
  `usuario_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_rutina_asignada_rutina_predefinida1_idx` (`rutina_predefinida_id` ASC) ,
  INDEX `fk_rutina_asignada_usuario1_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_rutina_asignada_rutina_predefinida1`
    FOREIGN KEY (`rutina_predefinida_id`)
    REFERENCES `bdgym`.`rutina_predefinida` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rutina_asignada_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`sesionentrenamiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`sesionentrenamiento` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  `usuario_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sesionentrenamiento_usuario1_idx` (`usuario_id` ASC) ,
  CONSTRAINT `fk_sesionentrenamiento_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`rutina_sesionentrenamiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`rutina_sesionentrenamiento` (
  `sesionentrenamiento_id` INT(11) NOT NULL,
  `rutina_predefinida_id` INT(11) NOT NULL,
  `posicion` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`sesionentrenamiento_id`, `rutina_predefinida_id`),
  INDEX `fk_rutina_sesionentrenamiento_sesionentrenamiento1_idx` (`sesionentrenamiento_id` ASC) ,
  INDEX `fk_rutina_sesionentrenamiento_rutina_predefinida1_idx` (`rutina_predefinida_id` ASC) ,
  CONSTRAINT `fk_rutina_sesionentrenamiento_rutina_predefinida1`
    FOREIGN KEY (`rutina_predefinida_id`)
    REFERENCES `bdgym`.`rutina_predefinida` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rutina_sesionentrenamiento_sesionentrenamiento1`
    FOREIGN KEY (`sesionentrenamiento_id`)
    REFERENCES `bdgym`.`sesionentrenamiento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`sesionejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`sesionejercicio` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `series` INT(11) NULL DEFAULT NULL,
  `repeticiones` INT(11) NULL DEFAULT NULL,
  `duracion` INT(11) NULL DEFAULT NULL,
  `ejercicio_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sesionejercicio_ejercicio1_idx` (`ejercicio_id` ASC) ,
  CONSTRAINT `fk_sesionejercicio_ejercicio1`
    FOREIGN KEY (`ejercicio_id`)
    REFERENCES `bdgym`.`ejercicio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`sesionentrenamiento_has_sesionejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`sesionentrenamiento_has_sesionejercicio` (
  `sesionentrenamiento_id` INT(11) NOT NULL,
  `sesionejercicio_id` INT(11) NOT NULL,
  `posicion` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`sesionentrenamiento_id`, `sesionejercicio_id`),
  INDEX `fk_sesionentrenamiento_has_sesionejercicio_sesionejercicio1_idx` (`sesionejercicio_id` ASC) ,
  INDEX `fk_sesionentrenamiento_has_sesionejercicio_sesionentrenamie_idx` (`sesionentrenamiento_id` ASC) ,
  CONSTRAINT `fk_sesionentrenamiento_has_sesionejercicio_sesionejercicio1`
    FOREIGN KEY (`sesionejercicio_id`)
    REFERENCES `bdgym`.`sesionejercicio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sesionentrenamiento_has_sesionejercicio_sesionentrenamiento1`
    FOREIGN KEY (`sesionentrenamiento_id`)
    REFERENCES `bdgym`.`sesionentrenamiento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `bdgym`.`valoracion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bdgym`.`valoracion` (
  `puntuacion` INT(11) NULL DEFAULT NULL,
  `descripcion` VARCHAR(250) NULL DEFAULT NULL,
  `usuario_id` INT(11) NOT NULL,
  `sesionejercicio_id` INT(11) NOT NULL,
  `rutina_asignada_id` INT(11) NOT NULL,
  PRIMARY KEY (`usuario_id`, `sesionejercicio_id`, `rutina_asignada_id`),
  INDEX `fk_valoracion_usuario1_idx` (`usuario_id` ASC) ,
  INDEX `fk_valoracion_sesionejercicio1_idx` (`sesionejercicio_id` ASC) ,
  INDEX `fk_valoracion_rutina_asignada1_idx` (`rutina_asignada_id` ASC) ,
  CONSTRAINT `fk_valoracion_rutina_asignada1`
    FOREIGN KEY (`rutina_asignada_id`)
    REFERENCES `bdgym`.`rutina_asignada` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_valoracion_sesionejercicio1`
    FOREIGN KEY (`sesionejercicio_id`)
    REFERENCES `bdgym`.`sesionejercicio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_valoracion_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `bdgym`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
