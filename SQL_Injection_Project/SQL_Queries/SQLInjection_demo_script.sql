-- ----------------------------------------------------------------
-- Create Schema and User
--
-- DROP SCHEMA IF EXISTS `SQLInjection`;
-- CREATE SCHEMA IF NOT EXISTS `SQLInjection` DEFAULT CHARACTER SET utf8mb4;
-- DROP USER IF EXISTS `cst8276`@`localhost`;
-- CREATE USER IF NOT EXISTS 'cst8276'@'localhost' IDENTIFIED BY '8276';
-- GRANT ALL ON `SQLInjection`.* TO 'cst8276'@'localhost';
--
-- ----------------------------------------------------------------

-- -----------------------------------------------------
-- Create Table `SQLInjection`.`useraccount`
-- -----------------------------------------------------
USE `SQLInjection`;
CREATE TABLE IF NOT EXISTS `SQLInjection`.`useraccount`(
  `userid` INT NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `username` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(10) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `SQLInjection`.`products`(
  `product_name` VARCHAR(60) NOT NULL)
ENGINE = InnoDB;

INSERT INTO `SQLInjection`.`products`(`product_name`) VALUES ('Oracle 19C Cracked Key');
INSERT INTO `SQLInjection`.`products`(`product_name`) VALUES ('Unlimited Burger King Coupons');
INSERT INTO `SQLInjection`.`products`(`product_name`) VALUES ('TempleOS Key');
INSERT INTO `SQLInjection`.`products`(`product_name`) VALUES ('Bulk Supply of Werthers Original');
INSERT INTO `SQLInjection`.`products`(`product_name`) VALUES ('iPhone 14 Pro Max');

INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Zangerl', 'Alan', 'zang0005', '6137683838', 'iloveoracle');
INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Parker', 'Nathan', 'park2342', '6138348292', 'ilovemysql');
INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Taylor', 'Rhys', 'tayl7685', '6130986232', 'ilovepostgres');
INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Goulding', 'Aaron', 'goul1262', '6134332323', 'ilovemsaccess');
INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Kim', 'Dongkwan', 'kim4353', '8194657373', 'ilovemariadb');
INSERT INTO `SQLInjection`.`useraccount`(`last_name`, `first_name`, `username`, `phone`, `password`) VALUES ('Kim', 'Taeyeon', 'kim8672', '6473832821', 'ilovemongodb');

