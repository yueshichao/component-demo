create database  if not exists test_write_db;

use test_write_db;


CREATE TABLE `test_write_db`.`t_write`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` bigint(20) NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);

INSERT INTO `test_write_db`.`t_write`(`id`, `value`) VALUES (1, 1000);
