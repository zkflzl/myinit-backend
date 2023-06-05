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

 Date: 05/06/2023 10:00:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for post_msg
-- ----------------------------
DROP TABLE IF EXISTS `post_msg`;
CREATE TABLE `post_msg`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` bigint(20) NOT NULL COMMENT '创建用户 id',
  `postId` bigint(20) NOT NULL COMMENT '帖子 id',
  `isThumb` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否点赞(0否 1是)',
  `isFavour` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否点赞(0否 1是)',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '帖子点赞、收藏' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
