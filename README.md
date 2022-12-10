# [Mapty Pro (Backend)](https://github.com/vonbrank/Mapty-Pro-Backend)
使用 Spring Boot 构建的后端应用。

![zWH7ZQ.md.png](https://s1.ax1x.com/2022/12/10/zWH7ZQ.md.png)

## 使用

+ 配置数据库

  在 `src\main\resources\application.properties` 中配置 MySQL 用户名和密码：

  ```properties
  server.port=5000
  spring.datasource.url=<部署好的数据库的URL>
  spring.datasource.username=<用户名>
  spring.datasource.password=<密码>
  spring.datasource.driver-class-name=com.mysql.jdbc.Driver
  spring.jpa.hibernate.ddl-auto = none
  spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
  ```

  具体的 MySQL 服务请自行搭建，此处给出创建数据表的参考代码：

  ```sql
  -- Table structure for table `journey`
  DROP TABLE IF EXISTS `journey`;
  CREATE TABLE `journey` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL DEFAULT '',
    `description` varchar(1023) DEFAULT '',
    `uid` int(11) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_journey_owner_on_uid_idx` (`uid`),
    CONSTRAINT `fk_journey_owner_on_uid` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
  
  -- Table structure for table `user`
  DROP TABLE IF EXISTS `user`;
  CREATE TABLE `user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `email` varchar(255) DEFAULT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
  
  -- Table structure for table `waypoint`
  DROP TABLE IF EXISTS `waypoint`;
  CREATE TABLE `waypoint` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `label` varchar(255) NOT NULL DEFAULT '',
    `time` varchar(45) NOT NULL DEFAULT '8-0',
    `coordinate` varchar(45) NOT NULL DEFAULT '(0, 0)',
    `jid` int(11) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_waypoint_belong_to_jid_idx` (`jid`),
    CONSTRAINT `fk_waypoint_belong_to_jid` FOREIGN KEY (`jid`) REFERENCES `journey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  ) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
  ```

+ 构建项目

  ```bash
  ./mvnw install
  ```

+ 创建 Docker 镜像

  ```bash
  docker build -t mapty-pro .
  ```

+ 运行 Docker 容器

  ```bash
  docker run --name mapty-pro-dev -p 5000:5000 -d mapty-pro
  ```


## 更多内容

前端项目：https://github.com/vonbrank/Mapty-Pro-Frontend

后端项目：https://github.com/vonbrank/Mapty-Pro-Backend

Restful API 规约：[Mapty Pro Restful API Specification](https://github.com/vonbrank/Mapty-Pro/blob/main/docs/Restful-API-Specification.md)