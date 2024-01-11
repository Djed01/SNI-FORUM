-- mysql workbench forward engineering

SET @old_unique_checks=@@unique_checks, unique_checks=0;
SET @old_foreign_key_checks=@@foreign_key_checks, foreign_key_checks=0;
SET @old_sql_mode=@@sql_mode, sql_mode='only_full_group_by,strict_trans_tables,no_zero_in_date,no_zero_date,error_for_division_by_zero,no_engine_substitution';

-- -----------------------------------------------------
-- schema forum
-- -----------------------------------------------------

-- -----------------------------------------------------
-- schema forum
-- -----------------------------------------------------
create schema if not exists `forum` default character set utf8 ;
use `forum` ;

-- -----------------------------------------------------
-- table `forum`.`user`
-- -----------------------------------------------------
create table if not exists `forum`.`user` (
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
-- table `forum`.`topic`
-- -----------------------------------------------------
create table if not exists `forum`.`topic` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- table `forum`.`comment`
-- -----------------------------------------------------
create table if not exists `forum`.`comment` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TopicID` INT NOT NULL,
  `UserID` INT NOT NULL,
  `Content` VARCHAR(1000) NOT NULL,
  `Time` DATETIME NOT NULL,
  `Status` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  INDEX `fk_comment_topic1_idx` (`TopicID` ASC) VISIBLE,
  INDEX `fk_comment_user1_idx` (`UserID` ASC) VISIBLE,
  CONSTRAINT `fk_comment_topic1`
    FOREIGN KEY (`TopicID`)
    REFERENCES `forum`.`topic` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`UserID`)
    REFERENCES `forum`.`user` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- table `forum`.`user_permission`
-- -----------------------------------------------------
create table if not exists `forum`.`user_permission` (
  `UserID` INT NOT NULL,
  `TopicID` INT NOT NULL,
  `Add` TINYINT NOT NULL,
  `Edit` TINYINT NOT NULL,
  `Delete` TINYINT NOT NULL,
  PRIMARY KEY (`UserID`, `TopicID`),
  INDEX `fk_permission_has_user_user1_idx` (`UserID` ASC) VISIBLE,
  INDEX `fk_user_permission_topic1_idx` (`TopicID` ASC) VISIBLE,
  CONSTRAINT `fk_permission_has_user_user1`
    FOREIGN KEY (`UserID`)
    REFERENCES `forum`.`user` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_permission_topic1`
    FOREIGN KEY (`TopicID`)
    REFERENCES `forum`.`topic` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
