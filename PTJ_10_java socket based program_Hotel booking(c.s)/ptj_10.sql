/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50514
Source Host           : localhost:3306
Source Database       : ptj_10

Target Server Type    : MYSQL
Target Server Version : 50514
File Encoding         : 65001

Date: 2014-04-13 21:30:28
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `in_date` date DEFAULT NULL,
  `out_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hotel_name` (`hotel_name`),
  KEY `city` (`city`),
  CONSTRAINT `city` FOREIGN KEY (`city`) REFERENCES `hotel` (`city`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hotel_name` FOREIGN KEY (`hotel_name`) REFERENCES `hotel` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('2', '2', 'Hilton Hotel', 'city', '2014-04-04', '2014-04-07');
INSERT INTO `book` VALUES ('3', '3', 'Hilton Hotel', 'Sydney', '2014-04-05', '2014-04-06');
INSERT INTO `book` VALUES ('4', '4', 'hotel name', 'city', '2014-04-09', '2014-04-15');
INSERT INTO `book` VALUES ('5', 'your name', 'hotel name', 'city', '2014-04-04', '2014-04-06');
INSERT INTO `book` VALUES ('6', 'your name', 'hotel name', 'city', '2014-04-04', '2014-04-06');
INSERT INTO `book` VALUES ('7', 'your name', 'hotel name', 'city', '2014-04-04', '2014-04-06');
INSERT INTO `book` VALUES ('8', 'test1', 'Hilton Hotel', 'Sydney', '2014-04-14', '2014-04-17');
INSERT INTO `book` VALUES ('9', 'test2', 'Hilton Hotel', 'Perth', '2014-04-03', '2014-04-06');

-- ----------------------------
-- Table structure for `hotel`
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `rooms` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `city` (`city`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` VALUES ('1', 'hotel name', 'city', '100', '10');
INSERT INTO `hotel` VALUES ('5', 'Hilton Hotel', 'Melbourne', '100', '10');
INSERT INTO `hotel` VALUES ('6', 'Hilton Hotel', 'Sydney', '100', '10');
INSERT INTO `hotel` VALUES ('7', 'Hilton Hotel', 'Perth', '100', '10');
INSERT INTO `hotel` VALUES ('8', 'Hilton Hotel', 'city', '100', '10');
INSERT INTO `hotel` VALUES ('9', 'test1', 'city', '100', '10');

-- ----------------------------
-- Procedure structure for `test`
-- ----------------------------
DROP PROCEDURE IF EXISTS `test`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `test`()
BEGIN
	#Routine body goes here...
SELECT count(*) as count from book where  hotel_name = 'hotel name' and city = 'city' and (in_date BETWEEN '2014-04-03' AND '2014-04-06' or out_date BETWEEN '2014-04-03' AND '2014-04-06') ;
END
;;
DELIMITER ;
