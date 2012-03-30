delimiter $$

CREATE DATABASE `carro` /*!40100 DEFAULT CHARACTER SET latin1 */$$

delimiter $$

CREATE TABLE `item` (
  `item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_nome` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `dono` (
  `dono_cpf` bigint(20) NOT NULL,
  `dono_nome` varchar(50) DEFAULT NULL,
  `dono_sexo` char(1) DEFAULT NULL,
  PRIMARY KEY (`dono_cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `carro` (
  `carr_chassi` bigint(20) NOT NULL,
  `carr_modelo` varchar(50) DEFAULT NULL,
  `carr_ano` int(11) DEFAULT NULL,
  `dono_cpf` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`carr_chassi`),
  KEY `fk_carro_dono` (`dono_cpf`),
  CONSTRAINT `fk_carro_dono` FOREIGN KEY (`dono_cpf`) REFERENCES `dono` (`dono_cpf`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `item_carro` (
  `itca_id` bigint(20) NOT NULL,
  `carr_chassi` bigint(20) NOT NULL,
  PRIMARY KEY (`itca_id`,`carr_chassi`),
  KEY `fk_itca_item` (`itca_id`),
  KEY `fk_itca_carr` (`carr_chassi`),
  CONSTRAINT `fk_itca_item` FOREIGN KEY (`itca_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_itca_carr` FOREIGN KEY (`carr_chassi`) REFERENCES `carro` (`carr_chassi`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

