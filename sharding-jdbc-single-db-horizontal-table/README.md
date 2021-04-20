# 单库水平分表

# 环境准备

1. `docker run -d --name=sharding-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7.23`

2. 运行resource下的init.sql