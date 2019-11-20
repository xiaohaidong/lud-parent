/*
 Navicat Premium Data Transfer

 Source Server         : 本地机器-04 MYSQL
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 169.254.0.4:3306
 Source Schema         : lud-iot

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 11/11/2019 16:57:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lud_iot_device_info
-- ----------------------------
DROP TABLE IF EXISTS `lud_iot_device_info`;
CREATE TABLE `lud_iot_device_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project` int(255) NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `bindip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `cdnx` decimal(6, 1) NULL DEFAULT NULL,
  `cdny` decimal(6, 1) NULL DEFAULT NULL,
  `infomation` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `lastupdate` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lud_iot_device_info
-- ----------------------------
INSERT INTO `lud_iot_device_info` VALUES (1, 100202, 'server-web', 'WEB服务器', 'server', 'fad fa-atom', 'lud-cmcc-whitel lud-cmcb-blu', '192.168.188.101', 30.3, 39.6, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (2, 100202, 'server-service', '应用服务器', 'server', 'fad fa-house-flood', 'lud-cmcc-whitel lud-cmcb-blu', '192.168.188.102', 30.5, 30.6, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (4, 100202, 'server-data', '数据服务器', 'server', 'fad fa-coins', 'lud-cmcc-whitel lud-cmcb-blu', '192.168.188.103', 46.5, 11.1, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (5, 100202, 'server-file', '文件服务器', 'server', 'fad fa-ballot', 'lud-cmcc-whitel lud-cmcb-blu', '192.168.188.40', 30.5, 48.8, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (6, 100202, 'server-af01', '安防服务器', 'server', 'fad fa-jedi', 'lud-cmcc-whitel lud-cmcb-blu', '192.168.1.200', 59.8, 11.2, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (7, 100202, 'firewall-af-xp', '防火墙', 'firewall', 'fad fa-radiation-alt', 'lud-cmcc-whitel lud-cmcb-org', '', 64.4, 21.4, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (8, 100202, 'firewall-cap01', '防火墙', 'firewall', 'fad fa-radiation-alt', 'lud-cmcc-whitel lud-cmcb-org', '', 64.7, 39.5, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (9, 100202, 'switch-af-xp', '犀浦交换机', 'switch', 'fad fa-bezier-curve', 'lud-cmcc-whitel lud-cmcb-org', '192.168.1.1', 72.8, 10.7, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (10, 100202, 'switch-cap01', '校园网交换机', 'switch', 'fad fa-spider-web', 'lud-cmcc-whitel lud-cmcb-red ', '202.115.64.1', 62.6, 30.1, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (11, 100202, 'firewall-af-jl', '防火墙', 'firewall', 'fad fa-radiation-alt', 'lud-cmcc-whitel lud-cmcb-org', '', 75.4, 26.4, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (12, 100202, 'firewall-af-em', '防火墙', 'firewall', 'fad fa-radiation-alt', 'lud-cmcc-whitel lud-cmcb-org', '', 74.5, 37.0, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (13, 100202, 'switch-af-jl', '九里交换机', 'switch', 'fad fa-bezier-curve', 'lud-cmcc-whitel  lud-cmcb-org', '192.168.2.1', 86.5, 19.7, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (14, 100202, 'switch-af-em', '峨眉交换机', 'switch', 'fad fa-bezier-curve', 'lud-cmcc-whitel  lud-cmcb-org', '192.168.3.1', 87.0, 32.0, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (15, 100202, 'switch-vm', '计算平台交换机', 'switch', 'fad fa-draw-circle', 'lud-cmcc-whitel lud-cmcb-red ', '192.168.188.1', 46.0, 36.3, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (16, 100202, 'gateway-cap01', '校园网-外网网关', 'gateway', 'fad fa-claw-marks', 'lud-cmcc-whitel lud-cmcb-red ', '202.115.64.33', 46.1, 47.3, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (17, 100202, 'lud-service-core', '核心服务', 'service', 'fad fa-certificate', 'lud-cmcc-whitel lud-cmcb-purd', NULL, 16.2, 12.1, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (18, 100202, 'lud-service-business', '业务服务', 'service', 'fad fa-certificate', 'lud-cmcc-whitel lud-cmcb-purd', NULL, 1.9, 22.7, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (19, 100202, 'lud-service-smartcampus', '智慧校园', 'service', 'fad fa-bus-school', 'lud-cmcc-whitel lud-cmcb-purd', NULL, 6.3, 29.3, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (20, 100202, 'lud-web-server', '网站服务', 'service', 'fab fa-mendeley', 'lud-cmcc-whitel lud-cmcb-purd', NULL, 16.0, 29.8, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (21, 100202, 'pro-redis', 'REDIS/SER', 'programe', 'fad fa-life-ring', 'lud-cmcc-whitel lud-cmcb-gre', NULL, 30.6, 14.1, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (22, 100202, 'pro-mysql', 'MySQL/DK', 'programe', 'fas fa-coins', 'lud-cmcc-whitel lud-cmcb-gre', NULL, 30.6, 20.7, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (23, 100202, 'pro-mongo', 'MongoDB', 'programe', 'fas fa-coin', 'lud-cmcc-whitel lud-cmcb-gre', NULL, 30.7, 0.9, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (24, 100202, 'pro-rabbitmq', 'RabbitMQ', 'programe', 'fas fa-layer-group', 'lud-cmcc-whitel lud-cmcb-gre', NULL, 30.8, 7.5, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (26, 100202, 'pro-nginx', 'Nginx/GAT', 'programe', 'fad fa-igloo', 'lud-cmcc-whitel lud-cmcb-org', NULL, 15.8, 37.5, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (27, 100202, 'pro-zooker', '调度服务', 'programe', 'fad fa-dice-d20', 'lud-cmcc-whitel lud-cmcb-blu', NULL, 16.2, 21.0, '', '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (28, 100202, 'lud-iot-server', 'MQTT服务', 'service', 'fab fa-joget', 'lud-cmcc-whitel lud-cmcb-purd', NULL, 6.2, 1.9, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (29, 100202, 'lud-iot-client', 'MQTT客户', 'service', 'fab fa-joget', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 47.2, 2.1, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (32, 100202, 'lud-service-open', '开放接口', 'service', 'fab fa-jsfiddle', 'lud-cmcc-whitel lud-cmcb-purd', NULL, 2.0, 15.4, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (33, 100202, 'gata-em-east', '峨眉东门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 98.6, 27.2, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (34, 100202, 'gata-em-south', '峨眉南门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 107.8, 27.2, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (35, 100202, 'gata-em-west', '峨眉西门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 116.6, 27.3, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (36, 100202, 'gata-xp-east', '犀浦东门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 77.6, 0.5, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (37, 100202, 'gata-xp-south', '犀浦南门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 86.4, 0.5, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (38, 100202, 'gata-xp-west1', '犀浦西1门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 95.1, 0.6, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (39, 100202, 'gata-xp-west2', '犀浦西2门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 104.6, 0.5, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (40, 100202, 'gata-xp-north', '犀浦北门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 113.7, 0.7, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (41, 100202, 'gata-jl-east', '九里东门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 87.6, 11.6, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (42, 100202, 'gata-jl-south', '九里南门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 96.6, 11.7, NULL, '0', NULL);
INSERT INTO `lud_iot_device_info` VALUES (43, 100202, 'gata-jl-west', '九里西门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 105.4, 11.7, NULL, '1', NULL);
INSERT INTO `lud_iot_device_info` VALUES (44, 100202, 'gata-jl-north', '九里北门', 'gata', 'fad fa-car-building', 'lud-cmcc-whitel lud-cmcb-cus1', NULL, 114.4, 11.6, NULL, '0', NULL);

-- ----------------------------
-- Table structure for lud_iot_device_relation
-- ----------------------------
DROP TABLE IF EXISTS `lud_iot_device_relation`;
CREATE TABLE `lud_iot_device_relation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project` int(255) NULL DEFAULT NULL,
  `fp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `tp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `width` int(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lud_iot_device_relation
-- ----------------------------
INSERT INTO `lud_iot_device_relation` VALUES (1, 100202, 'switch-vm', 'server-web', '#000', 2);
INSERT INTO `lud_iot_device_relation` VALUES (2, 100202, 'switch-vm', 'server-service', '#000', 2);
INSERT INTO `lud_iot_device_relation` VALUES (3, 100202, 'switch-vm', 'server-data', '#000', 2);
INSERT INTO `lud_iot_device_relation` VALUES (4, 100202, 'switch-vm', 'server-file', '#000', 2);
INSERT INTO `lud_iot_device_relation` VALUES (5, 100202, 'switch-vm', 'switch-cap01', '#000', 3);
INSERT INTO `lud_iot_device_relation` VALUES (6, 100202, 'switch-cap01', 'firewall-cap01', '#000', 2);
INSERT INTO `lud_iot_device_relation` VALUES (7, 100202, 'firewall-cap01', 'gateway-cap01', '#000', 3);
INSERT INTO `lud_iot_device_relation` VALUES (8, 100202, 'switch-cap01', 'firewall-af-em', '#000', 2);
INSERT INTO `lud_iot_device_relation` VALUES (9, 100202, 'switch-cap01', 'firewall-af-jl', '#000', 2);
INSERT INTO `lud_iot_device_relation` VALUES (10, 100202, 'switch-cap01', 'firewall-af-xp', '#000', 2);
INSERT INTO `lud_iot_device_relation` VALUES (11, 100202, 'firewall-af-em', 'switch-af-em', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (12, 100202, 'firewall-af-jl', 'switch-af-jl', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (13, 100202, 'firewall-af-xp', 'switch-af-xp', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (14, 100202, 'switch-af-xp', 'server-af01', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (15, 100202, 'server-data', 'pro-redis', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (16, 100202, 'server-data', 'pro-mysql', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (17, 100202, 'server-data', 'pro-mongo', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (18, 100202, 'server-data', 'pro-rabbitmq', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (19, 100202, 'lud-service-core', 'pro-redis', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (20, 100202, 'lud-service-core', 'pro-mysql', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (21, 100202, 'lud-service-core', 'pro-mongo', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (22, 100202, 'lud-service-core', 'pro-rabbitmq', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (23, 100202, 'lud-service-core', 'server-service', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (26, 100202, 'lud-web-server', 'server-web', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (27, 100202, 'lud-web-server', 'pro-nginx', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (28, 100202, 'pro-nginx', 'server-file', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (31, 100202, 'pro-zooker', 'lud-service-smartcampus', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (32, 100202, 'pro-zooker', 'lud-service-business', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (33, 100202, 'pro-zooker', 'lud-service-core', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (34, 100202, 'pro-zooker', 'lud-web-server', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (35, 100202, 'pro-zooker', 'server-service', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (36, 100202, 'pro-zooker', 'lud-iot-server', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (37, 100202, 'pro-rabbitmq', 'lud-iot-server', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (38, 100202, 'pro-rabbitmq', 'lud-iot-client', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (39, 100202, 'pro-zooker', 'lud-service-open', '#09c', 1);
INSERT INTO `lud_iot_device_relation` VALUES (40, 100202, 'switch-af-em', 'gata-em-east', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (41, 100202, 'switch-af-em', 'gata-em-south', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (42, 100202, 'switch-af-em', 'gata-em-west', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (43, 100202, 'switch-af-xp', 'gata-xp-east', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (44, 100202, 'switch-af-xp', 'gata-xp-south', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (45, 100202, 'switch-af-xp', 'gata-xp-west1', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (46, 100202, 'switch-af-xp', 'gata-xp-west2', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (47, 100202, 'switch-af-xp', 'gata-xp-north', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (48, 100202, 'switch-af-jl', 'gata-jl-east', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (49, 100202, 'switch-af-jl', 'gata-jl-south', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (50, 100202, 'switch-af-jl', 'gata-jl-west', '#000', 1);
INSERT INTO `lud_iot_device_relation` VALUES (51, 100202, 'switch-af-jl', 'gata-jl-north', '#000', 1);

-- ----------------------------
-- Table structure for lud_iot_device_type
-- ----------------------------
DROP TABLE IF EXISTS `lud_iot_device_type`;
CREATE TABLE `lud_iot_device_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `service` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lud_iot_device_type
-- ----------------------------
INSERT INTO `lud_iot_device_type` VALUES (1, 'server', '服务器', NULL, '');
INSERT INTO `lud_iot_device_type` VALUES (2, 'gatway', '路由器', NULL, NULL);
INSERT INTO `lud_iot_device_type` VALUES (3, 'firewall', '防火墙', NULL, NULL);
INSERT INTO `lud_iot_device_type` VALUES (4, 'switch', '交换机', NULL, NULL);
INSERT INTO `lud_iot_device_type` VALUES (6, 'service', '软件服务', NULL, NULL);
INSERT INTO `lud_iot_device_type` VALUES (7, 'programe', '中间件', NULL, NULL);

-- ----------------------------
-- View structure for view_system_tables
-- ----------------------------
DROP VIEW IF EXISTS `view_system_tables`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `view_system_tables` AS (select `information_schema`.`TABLES`.`TABLE_NAME` AS `name`,`information_schema`.`TABLES`.`TABLE_TYPE` AS `types`,`information_schema`.`TABLES`.`ENGINE` AS `engine`,`information_schema`.`TABLES`.`VERSION` AS `version`,`information_schema`.`TABLES`.`ROW_FORMAT` AS `format`,`information_schema`.`TABLES`.`TABLE_ROWS` AS `rowsnum`,`information_schema`.`TABLES`.`AVG_ROW_LENGTH` AS `avgrow`,`information_schema`.`TABLES`.`DATA_LENGTH` AS `datalength`,`information_schema`.`TABLES`.`AUTO_INCREMENT` AS `increment`,`information_schema`.`TABLES`.`CREATE_TIME` AS `createtime`,`information_schema`.`TABLES`.`UPDATE_TIME` AS `updatetime`,`information_schema`.`TABLES`.`CHECK_TIME` AS `checktime`,`information_schema`.`TABLES`.`TABLE_COMMENT` AS `commet` from `information_schema`.`TABLES` where (`information_schema`.`TABLES`.`TABLE_SCHEMA` = 'lud-core') order by `information_schema`.`TABLES`.`TABLE_NAME`);

SET FOREIGN_KEY_CHECKS = 1;
