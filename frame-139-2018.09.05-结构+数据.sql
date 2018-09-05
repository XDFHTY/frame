/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.7.22 : Database - frame
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`frame` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `frame`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `admin_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '管理员基础表',
  `admin_name` varchar(16) NOT NULL COMMENT '管理员账号',
  `admin_pass` varchar(64) NOT NULL COMMENT '管理员密码',
  `salt_val` varchar(64) DEFAULT NULL COMMENT '盐值',
  `admin_type` enum('1') NOT NULL COMMENT '管理员分类，1-系统管理员',
  `admin_state` enum('1','0','-1') DEFAULT '1' COMMENT '状态，1-正常，0-禁用（删除），-1-停用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE KEY `UNIQUE` (`admin_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `admin` */

insert  into `admin`(`admin_id`,`admin_name`,`admin_pass`,`salt_val`,`admin_type`,`admin_state`,`create_time`,`update_time`) values 
(114,'asd','BDE15B0DB418B15CD92FE8E817B8589A','bbdd31a8-787a-48ed-bd8d-80806d15d9a5','1','1','2018-07-13 16:42:46','2018-08-02 18:40:02');

/*Table structure for table `auth_customer_role` */

DROP TABLE IF EXISTS `auth_customer_role`;

CREATE TABLE `auth_customer_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户—角色关系表',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '用户id-包括admin和user ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `auth_customer_role` */

insert  into `auth_customer_role`(`id`,`customer_id`,`role_id`) values 
(1,114,1),
(2,25,2),
(3,26,2),
(4,27,2),
(5,28,2),
(6,29,2),
(7,30,2),
(8,31,2),
(9,32,2),
(10,33,2),
(11,115,2),
(12,116,2),
(13,117,2),
(14,118,2),
(15,119,2),
(16,121,2),
(17,122,2),
(18,123,2);

/*Table structure for table `auth_modular` */

DROP TABLE IF EXISTS `auth_modular`;

CREATE TABLE `auth_modular` (
  `modular_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限资源表',
  `page_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `page_url` varchar(255) DEFAULT NULL COMMENT '请求路径',
  `page_method` enum('GET','POST','PUT','DELETE','OPTIONS','TRACE','HEAD','ALL') DEFAULT NULL COMMENT '请求方式resful方法头',
  `page_type` enum('0','1','2','3') NOT NULL COMMENT '分类，1-一级目录，0-请求',
  `page_sort` int(11) DEFAULT NULL COMMENT '排序',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级页面ID',
  `spare1` varchar(255) DEFAULT NULL COMMENT '备用',
  `state` enum('1','0') NOT NULL DEFAULT '1' COMMENT '状态（0-已删除，1-正常）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`modular_id`) USING BTREE,
  UNIQUE KEY `page_url` (`page_url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `auth_modular` */

insert  into `auth_modular`(`modular_id`,`page_name`,`page_url`,`page_method`,`page_type`,`page_sort`,`parent_id`,`spare1`,`state`,`create_time`) values 
(204,'系统','/**','ALL','1',1,0,'','1','2018-07-18 17:15:49'),
(205,'管理端','/api/v1/admin/**','ALL','2',1,204,NULL,'1','2018-07-18 17:15:44'),
(206,'用户端','/api/v1/user/**','ALL','2',1,204,NULL,'1','2018-07-18 17:15:47'),
(207,'审图业务','/api/v1/st','ALL','2',1,204,NULL,'1','2018-07-18 17:00:51'),
(208,'新增管理员账号','/api/v1/admin/addAdmin','POST','0',1,205,NULL,'1','2018-07-18 17:15:22'),
(209,'删除账号','/api/v1/admin/deleteAdmin','DELETE','0',2,205,NULL,'1','2018-07-18 17:15:18'),
(210,'查询所有账号','/api/v1/admin/findAllAdmin','GET','0',3,205,NULL,'1','2018-07-18 17:15:14'),
(211,'登录','/api/v1/admin/ifLogin','POST','0',4,205,NULL,'1','2018-07-18 17:15:12'),
(212,'注销','/api/v1/admin/ifLogout','GET','0',5,205,NULL,'1','2018-07-18 17:14:57'),
(213,'修改密码，不校验原密码','/api/v1/admin/updateAdmin','PUT','0',6,205,NULL,'1','2018-07-18 17:14:52'),
(214,'新增用户','/api/v1/user/addUser','POST','0',1,206,NULL,'1','2018-07-18 17:14:49'),
(215,'登录','/api/v1/user/login','POST','0',2,206,NULL,'1','2018-07-18 17:16:28'),
(216,'注销','/api/v1/user/logout','GET','0',3,206,NULL,'1','2018-07-18 17:16:45'),
(217,'修改密码','/api/v1/user/updateUserPass','PUT','0',4,206,NULL,'1','2018-07-18 17:17:11'),
(218,'传入识别到的JSON字符串进行处理','/api/v1/st/shenTu','POST','0',1,207,NULL,'1','2018-07-18 17:17:34'),
(219,'测试服务器是否正常启动','/api/v1/st/test','ALL','0',2,207,NULL,'1','2018-07-18 17:18:11'),
(222,'用户基本信息','/api/v1/user/getUserInfo','GET','0',5,206,NULL,'1','2018-07-24 16:38:13');

/*Table structure for table `auth_role` */

DROP TABLE IF EXISTS `auth_role`;

CREATE TABLE `auth_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限角色表',
  `role_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '系统使用的角色名ROLE_XXX',
  `role_description_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名用户查看用',
  `role_state` enum('0','1') COLLATE utf8_bin DEFAULT '1' COMMENT '状态 0-禁用 1-使用 默认1',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

/*Data for the table `auth_role` */

insert  into `auth_role`(`role_id`,`role_name`,`role_description_name`,`role_state`) values 
(1,'管理员','管理员','1'),
(2,'用户','用户','1');

/*Table structure for table `auth_role_modular` */

DROP TABLE IF EXISTS `auth_role_modular`;

CREATE TABLE `auth_role_modular` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色-模块化关系表',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色表主键id',
  `modular_id` bigint(20) DEFAULT NULL COMMENT '模块化表主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色—资源关系表';

/*Data for the table `auth_role_modular` */

insert  into `auth_role_modular`(`id`,`role_id`,`modular_id`) values 
(1,1,204),
(2,1,205),
(3,1,208),
(4,1,209),
(5,1,210),
(6,1,211),
(7,1,212),
(8,1,213),
(9,1,214),
(10,2,204),
(11,2,206),
(12,2,207),
(13,2,215),
(14,2,216),
(15,2,217),
(16,2,218),
(18,2,222);

/*Table structure for table `key_64` */

DROP TABLE IF EXISTS `key_64`;

CREATE TABLE `key_64` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'admin-user-唯一主键',
  `stub` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `stub` (`stub`)
) ENGINE=MyISAM AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4;

/*Data for the table `key_64` */

insert  into `key_64`(`id`,`stub`) values 
(123,'a');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `user_pass` varchar(64) NOT NULL COMMENT '密码',
  `salt_val` varchar(64) DEFAULT NULL COMMENT '盐值',
  `user_type` enum('1') DEFAULT '1' COMMENT '用户类型-普通用户',
  `valid_time` datetime DEFAULT NULL COMMENT '账号有效期',
  `phone_number` varchar(16) DEFAULT NULL COMMENT '手机号',
  `is_phone` enum('0','1') DEFAULT '0' COMMENT '是否验证，0-未验证，1-已验证，默认为0',
  `e_mail` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `is_mail` enum('0','1') DEFAULT '0' COMMENT '是否验证，0-未验证，1-已验证，默认为0',
  `qq_number` varchar(16) DEFAULT NULL COMMENT 'QQ号',
  `wx_number` varchar(16) DEFAULT NULL COMMENT 'WX号',
  `wb_number` varchar(16) DEFAULT NULL COMMENT 'WB号',
  `state` enum('0','1') DEFAULT '1' COMMENT '账号状态，0-已删除，1-正常',
  `operation_id` bigint(20) DEFAULT NULL COMMENT '操作员ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_name`,`user_pass`,`salt_val`,`user_type`,`valid_time`,`phone_number`,`is_phone`,`e_mail`,`is_mail`,`qq_number`,`wx_number`,`wb_number`,`state`,`operation_id`,`create_time`,`update_time`) values 
(115,'Gaos','90F28669D2B0A2A018738CAFD570166F','d34a7ba1-88d2-4fdd-81de-95ef3971011a','1','2018-11-24 15:30:31',NULL,'0',NULL,'0',NULL,NULL,NULL,'1',0,'2018-07-24 16:54:28','2018-07-24 15:30:31'),
(116,'dxc','7604E2E1E2F496ADDEB24AFA27773FF4','638f51c7-2109-41ab-817c-f25656aac1ae','1','2018-11-24 15:30:31',NULL,'0',NULL,'0',NULL,NULL,NULL,'1',0,'2018-07-24 16:54:38','2018-07-24 15:30:31'),
(117,'cxl','564C05E6501E0C793B7E91D54B6DFBDC','e584d004-00ba-4f9a-b07f-1816e5309cd2','1','2018-11-24 15:30:31',NULL,'0',NULL,'0',NULL,NULL,NULL,'1',0,'2018-07-24 16:54:48','2018-07-24 15:30:31'),
(118,'dzq','90B97E7B3A3623F54AF89B0FB77F5926','a7f490d6-421e-4b74-a3ba-3ad4555b5373','1','2018-11-24 15:30:31',NULL,'0',NULL,'0',NULL,NULL,NULL,'1',0,'2018-07-24 16:55:22','2018-07-24 15:30:31'),
(119,'user1','F1B90E455FA60852E6E4990CFDFB43FD','17572eca-0acf-417f-967a-3b5d9475d644','1','2018-11-24 15:30:31',NULL,'0',NULL,'0',NULL,NULL,NULL,'1',0,'2018-07-24 16:55:48','2018-07-24 15:30:31'),
(121,'test1','6111528B09B69B76F3F97DC03D6A3C23','d9e019d3-148b-4099-8f39-e73ccccf7f2f','1','2018-08-24 17:41:53',NULL,'0',NULL,'0',NULL,NULL,NULL,'1',NULL,'2018-07-24 17:41:53','2018-07-24 17:41:54'),
(122,'test2','9BAE1409EFD0D824D1B0583442D7AD10','87ab39b9-7a08-4d5a-b2c0-92a9f45a0b00','1','2018-08-24 18:25:04','13281182569','0',NULL,'0',NULL,NULL,NULL,'1',NULL,'2018-07-24 18:25:04','2018-07-24 18:25:06'),
(123,'lq','C9314C2684B9B4B642D66C3962FB1807','ddd9bc50-9969-4963-86ed-9279e4dd99fe','1','2018-11-24 15:30:31',NULL,'0',NULL,'0',NULL,NULL,NULL,'1',0,'2018-07-30 14:18:10','2018-07-24 15:30:31');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
