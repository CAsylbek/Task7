CREATE DATABASE `meters_data`;

CREATE TABLE `meter_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `meter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `meter_group` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_idx` (`meter_group`),
  CONSTRAINT `group` FOREIGN KEY (`meter_group`) REFERENCES `meter_group` (`id`)
);

CREATE TABLE `meter_reading` (
  `id` int NOT NULL AUTO_INCREMENT,
  `current_reading` int NOT NULL,
  `time_stamp` datetime NOT NULL,
  `meter` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `meter_idx` (`meter`),
  CONSTRAINT `meter` FOREIGN KEY (`meter`) REFERENCES `meter` (`id`)
);
