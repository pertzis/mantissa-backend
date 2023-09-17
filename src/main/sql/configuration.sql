CREATE TABLE `configuration` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`config_key` VARCHAR(255) NOT NULL,
	`config_value` VARCHAR(255),
	PRIMARY KEY (`id`)
);