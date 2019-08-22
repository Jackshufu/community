CREATE DATABASE community;
show databases;
use community;

/*
	创建user表
*/
CREATE TABLE `community`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` VARCHAR(100) NULL,
  `name` VARCHAR(50) NULL,
  `token` CHAR(36) NULL COMMENT '使用UUID类型，设置36',
  `gmt_create` BIGINT NULL,
  `gmt_modified` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;