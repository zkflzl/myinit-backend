/*
 Navicat Premium Data Transfer

 Source Server         : mySql
 Source Server Type    : MySQL
 Source Server Version : 50719 (5.7.19)
 Source Host           : localhost:3306
 Source Schema         : my_init

 Target Server Type    : MySQL
 Target Server Version : 50719 (5.7.19)
 File Encoding         : 65001

 Date: 08/06/2023 19:17:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `account` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户',
  `userName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `icon` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/imgs/icons/defaulticon' COMMENT '头像',
  `introduction` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `userRole` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user' COMMENT '权限',
  `gender` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, '18679260020', 'd664fbc237a2d26c58040cf0a6f2fc06', 'INIT_83ef2cba1884c4396ae79e16babf4bb3', '张三', '/imgs/icons/defaulticon', 'hello', 'admin', '男', '2023-06-01 19:24:41', '2023-06-08 16:51:27', 0);
INSERT INTO `user_info` VALUES (2, '18666666666', 'd664fbc237a2d26c58040cf0a6f2fc06', 'INIT_83ef2cba1884c4396ae79e16babf4bb5', NULL, '/imgs/icons/defaulticon', NULL, 'user', NULL, '2023-06-01 19:31:20', '2023-06-08 16:54:41', 0);
INSERT INTO `user_info` VALUES (3, '18666666667', 'd664fbc237a2d26c58040cf0a6f2fc06', 'INIT_83ef2cba1884c4396ae79e16babf4bb4', NULL, '/imgs/icons/defaulticon', NULL, 'user', NULL, '2023-06-01 19:31:52', '2023-06-08 16:54:42', 0);
INSERT INTO `user_info` VALUES (4, '18679260021', 'd664fbc237a2d26c58040cf0a6f2fc06', 'INIT_a1ede37b918828ffab7a1abd942e0c66', NULL, '/imgs/icons/defaulticon', NULL, 'user', NULL, '2023-06-01 19:32:17', '2023-06-08 16:54:43', 0);

SET FOREIGN_KEY_CHECKS = 1;
