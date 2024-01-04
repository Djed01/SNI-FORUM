-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema FORUM
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema FORUM
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `FORUM` DEFAULT CHARACTER SET utf8 ;
USE `FORUM` ;

-- -----------------------------------------------------
-- Table `FORUM`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FORUM`.`USER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(45) NOT NULL,
  `PasswordHash` VARCHAR(256) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Status` TINYINT NOT NULL DEFAULT 0,
  `Role` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FORUM`.`TOPIC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FORUM`.`TOPIC` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FORUM`.`COMMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FORUM`.`COMMENT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TopicID` INT NOT NULL,
  `UserID` INT NOT NULL,
  `Content` VARCHAR(1000) NOT NULL,
  `Time` DATETIME NOT NULL,
  `Status` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  INDEX `fk_COMMENT_TOPIC1_idx` (`TopicID` ASC) VISIBLE,
  INDEX `fk_COMMENT_USER1_idx` (`UserID` ASC) VISIBLE,
  CONSTRAINT `fk_COMMENT_TOPIC1`
    FOREIGN KEY (`TopicID`)
    REFERENCES `FORUM`.`TOPIC` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_COMMENT_USER1`
    FOREIGN KEY (`UserID`)
    REFERENCES `FORUM`.`USER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FORUM`.`USER_PERMISSION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FORUM`.`USER_PERMISSION` (
  `UserID` INT NOT NULL,
  `TopicID` INT NOT NULL,
  `Add` TINYINT NOT NULL,
  `Edit` TINYINT NOT NULL,
  `Delete` TINYINT NOT NULL,
  PRIMARY KEY (`UserID`, `TopicID`),
  INDEX `fk_PERMISSION_has_USER_USER1_idx` (`UserID` ASC) VISIBLE,
  INDEX `fk_USER_PERMISSION_TOPIC1_idx` (`TopicID` ASC) VISIBLE,
  CONSTRAINT `fk_PERMISSION_has_USER_USER1`
    FOREIGN KEY (`UserID`)
    REFERENCES `FORUM`.`USER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_PERMISSION_TOPIC1`
    FOREIGN KEY (`TopicID`)
    REFERENCES `FORUM`.`TOPIC` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
