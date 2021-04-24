DROP DATABASE IF EXISTS user_db;
CREATE DATABASE user_db;
USE user_db;

CREATE TABLE `t_user`  (
  `user_id` bigint(20) NOT NULL COMMENT 'id',
  `fullname` varchar(255) NOT NULL COMMENT '用户姓名',
  `user_type` char(1) NULL COMMENT '用户类型',
  PRIMARY KEY (`user_id`)
);
