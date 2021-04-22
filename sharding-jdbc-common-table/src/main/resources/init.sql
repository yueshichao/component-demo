-- 库1
DROP DATABASE IF EXISTS order_db_1;
CREATE DATABASE order_db_1;

USE order_db_1;
CREATE TABLE `t_dict`  (
  `dict_id` bigint(20) NOT NULL,
  `type` varchar(255) NULL COMMENT '字典类型',
  `code` varchar(255) NULL COMMENT '字典编码',
  `value` varchar(255) NULL COMMENT '字典值',
  PRIMARY KEY (`dict_id`)
);

-- 库2
DROP DATABASE IF EXISTS order_db_2;
CREATE DATABASE order_db_2;

USE order_db_2;

CREATE TABLE `t_dict`  (
  `dict_id` bigint(20) NOT NULL,
  `type` varchar(255) NULL COMMENT '字典类型',
  `code` varchar(255) NULL COMMENT '字典编码',
  `value` varchar(255) NULL COMMENT '字典值',
  PRIMARY KEY (`dict_id`)
);


-- 库2
DROP DATABASE IF EXISTS user_db;
CREATE DATABASE user_db;

USE user_db;

CREATE TABLE `t_dict`  (
  `dict_id` bigint(20) NOT NULL,
  `type` varchar(255) NULL COMMENT '字典类型',
  `code` varchar(255) NULL COMMENT '字典编码',
  `value` varchar(255) NULL COMMENT '字典值',
  PRIMARY KEY (`dict_id`)
);