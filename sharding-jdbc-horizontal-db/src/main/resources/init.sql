-- 库1
DROP DATABASE IF EXISTS order_db_1;
CREATE DATABASE order_db_1;

USE order_db_1;

CREATE TABLE `t_order_1` (
  `order_id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_order_2` (
  `order_id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 库2
DROP DATABASE IF EXISTS order_db_2;
CREATE DATABASE order_db_2;

USE order_db_2;

CREATE TABLE `t_order_1` (
  `order_id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_order_2` (
  `order_id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;