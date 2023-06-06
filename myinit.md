### 项目搭建

- 跑通基本项目



### 用户

1. 用户注册

2. 用户登录

3. 获取当前登录用户学习

   ##### 用户sql表

```sql
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `account` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户',
  `userName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `icon` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `introduction` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `userRole` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user' COMMENT '权限',
  `gender` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
```







### 帖子开发

- ​	用户帖子包含？
  
  ​	帖子id、用户id、标题、内容、标签、点赞、收藏、转发、逻辑删除、创建时间、更新时间			评论（关联评论表，此处不添加字段信息)

```sql
-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key comment '主键',
    userId     bigint                             not null comment '创建用户 id',
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    forward    int      default 0                 not null comment '转发数量',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '帖子' collate = utf8mb4_unicode_ci;
```



1. 帖子新增√
2. 帖子删除√
3. 帖子分页查看（自己）√
4. 帖子更新（自己或管理员）√

### 帖子点赞、收藏功能完善

- 帖子点赞表包含？

  id、帖子id、用户id、是否点赞、是否收藏、创建时间、更新时间、是否删除

```sql
-- 帖子点赞、收藏表
create table if not exists post_msg
(
    id         bigint auto_increment comment 'id' primary key comment '主键',
    userId     bigint                             not null comment '创建用户 id',
    postId     bigint                             not null comment '帖子 id',
    isThumb    tinyint(1)	default 0			  not null comment '是否点赞(0否 1是)',
    isFavour   tinyint(1)	default 0			  not null comment '是否收藏(0否 1是)',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '帖子点赞、收藏' collate = utf8mb4_unicode_ci;
```



1. 帖子点赞/收藏、取消点赞/收藏√			加锁加事务
