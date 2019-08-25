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

/*
	创建question表
*/
CREATE TABLE `community`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `title` VARCHAR(50) NULL COMMENT '问题标题',
  `descriptionuser` TEXT NULL COMMENT '问题的详细描述，放在文本编辑框，文本类型是TEXT类型',
  `gmt_create` BIGINT NULL,
  `gmt_modified` BIGINT NULL,
  `creator` INT NULL,
  `comment_count` INT NULL DEFAULT 0,
  `view_count` INT NULL DEFAULT 0,
  `like_count` INT NULL DEFAULT 0,
  `tag` VARCHAR(256) NULL,
  PRIMARY KEY (`id`))
COMMENT = '用于提交问题的表';

