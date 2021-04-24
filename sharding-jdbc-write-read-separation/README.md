# 垂直分库

# 环境准备

> 配置文件目录：/resource/master-slave-setting

1. 准备主从配置文件

> master.cnf 和 slave.cnf

2. 创建MySQL主从库  
运行docker-compose文件  

3. 配置主服务器

- CREATE USER 'slave'@'%' IDENTIFIED BY '123456'; # 创建slave用户

- GRANT REPLICATION SLAVE, REPLICATION CLIENT ON **.** TO 'slave'@'%'; # 赋权

- show master status; # 获取`File`、`Position`字段值，在配置好slave之前，不要对master做操作

3. 配置从服务器

- change master to master_host='172.20.0.2', master_user='slave', master_password='123456', master_port=3306, master_log_file='mysql-bin.000003', master_log_pos=778; # (MySQL命令)配置

> 获取master_host: `docker inspect --format='{{.NetworkSettings.IPAddress}}' master_mysql` 

master_log_file、master_log_pos就是之前主服务器的的File、Position字段值

- show slave status; # 查看从服务器状态  

> 如果配置完成发现主从不同步，Slave_IO_Running、Slave_SQL_Running都是NO  
> 执行以下三条命令  
>
> 1. stop slave;  
> 2. set global sql_slave_skip_counter =1;  
> 3. start slave;  

4. 运行resource下的init.sql