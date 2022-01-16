SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
                              `id` bigint(11) NOT NULL AUTO_INCREMENT,
                              `password` varchar(50) NULL DEFAULT NULL,
                              `username` varchar(20) NULL DEFAULT NULL,
                              `avatar` varchar(100) NULL DEFAULT NULL,
                              `email` varchar(50) NULL DEFAULT NULL,
                              `status` int(5) NULL DEFAULT NULL,
                              `remark` varchar(100) NULL DEFAULT NULL,
                              `create_time` int(11) NULL DEFAULT NULL,
                              `update_time` int(11) NULL DEFAULT NULL,
                              `last_login_time` int(11) NULL DEFAULT NULL,
                              `last_login_ip` varchar(50) NULL DEFAULT NULL,
                              `last_login_brand` varchar(50) NULL DEFAULT NULL,
                              `is_admin` int(1) NULL DEFAULT 0,
                              `is_active` int(1) NULL DEFAULT 0,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8 ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
# INSERT INTO `t_user` VALUES (50, 'UUKHSDDI5KPA43A8VL06V0TU2', 'admin', 'https://www.sunmale.cn/static/common/images/face/8.jpg', '', 1, '超级管理员，拥有全部权限', 1505286093, 1524364786, NULL, NULL, NULL, 1, 1);

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `title` varchar(255) DEFAULT NULL,
                          `content` longtext,
                          `firstPicture` varchar(255) DEFAULT NULL,
                          `flag` varchar(255) DEFAULT NULL,
                          `views` int(11) DEFAULT NULL,
                          `commentCount` int(11) DEFAULT NULL,
                          `appreciation` bit(1) NOT NULL,
                          `shareStatement` bit(1) NOT NULL,
                          `commentabled` bit(1) NOT NULL,
                          `published` bit(1) NOT NULL,
                          `recommend` bit(1) NOT NULL,
                          `createTime` datetime DEFAULT NULL,
                          `updateTime` datetime DEFAULT NULL,
                          `type` varchar(255) DEFAULT NULL,
                          `typeId` bigint(20) DEFAULT NULL,
                          `userId` bigint(20) DEFAULT NULL,
                          `tagId` bigint(20) DEFAULT NULL,
                          `description` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE ,
                          CONSTRAINT `FK292449gwg5yf7ocdlmswv9w4j` FOREIGN KEY (`typeid`) REFERENCES `t_type` (`id`),
                          CONSTRAINT `FK8ky5rrsxh01nkhctmo7d48p82` FOREIGN KEY (`userid`) REFERENCES `t_user` (`id`),
                          KEY `FK292449gwg5yf7ocdlmswv9w4j` (`typeid`) USING BTREE,
                          KEY `FK8ky5rrsxh01nkhctmo7d48p82` (`userid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_type
-- ----------------------------

DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) NOT NULL,
                          `typename` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_friends
-- ----------------------------

DROP TABLE `t_friends`;
CREATE TABLE `t_friends` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `blogName` varchar(255) NOT NULL,
                             `blogAddress` varchar(255) NOT NULL,
                             `pictureAddress` varchar(255) NOT NULL,
                             `createTime` datetime DEFAULT NULL,
                             `description` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------

DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `resourceName` varchar(255) DEFAULT NULL,
                              `resourceAddress` varchar(255) DEFAULT NULL,
                              `firstResource` varchar(255) DEFAULT NULL,
                              `secondResource` varchar(255) DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `title` varchar(200) NULL DEFAULT NULL COMMENT '文章标题',
                                `content` mediumtext NULL COMMENT '文章内容',
                                `remark` mediumtext NULL COMMENT '文章摘要',
                                `is_top` tinyint(1) NULL DEFAULT 0 COMMENT '文章是否置顶 0 不置顶 1 置顶',
                                `status` tinyint(1) NULL DEFAULT NULL COMMENT '文章状态 0 草稿 1 发布',
                                `is_reprint` tinyint(1) NULL DEFAULT NULL COMMENT '文章 0 原创  1转载',
                                `brows_num` int(11) NULL DEFAULT NULL COMMENT '文章浏览数',
                                `like_num` int(11) NULL DEFAULT NULL COMMENT '文章点赞数',
                                `comments` int(11) NULL DEFAULT NULL COMMENT '文章评论数',
                                `tags` varchar(500) NULL DEFAULT NULL COMMENT '文章标签',
                                `type_id` int(11) NULL DEFAULT NULL COMMENT '文章分类id',
                                `author` varchar(50) NULL DEFAULT NULL COMMENT '文章作者',
                                `user_id` int(11) NULL DEFAULT NULL COMMENT '文章作者id',
                                `cover_image` varchar(200) NULL DEFAULT NULL COMMENT '文章缩略图',
                                `create_time` datetime(0) NULL DEFAULT NULL COMMENT '文章发布时间',
                                `update_time` datetime(0) NULL DEFAULT NULL COMMENT '文章修改时间',
                                `is_recommend` tinyint(1) NULL DEFAULT 0 COMMENT '文章是否推荐 0 不推荐  1 推荐 默认不推荐',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 DEFAULT CHARSET = utf8 ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qiu_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag`;
CREATE TABLE `t_article_tag`  (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                                    `status` int(1) NULL DEFAULT NULL,
                                    `create_time` int(11) NULL DEFAULT NULL,
                                    `update_time` int(11) NULL DEFAULT NULL,
                                    `css` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
                                    `article_num` int(11) NULL DEFAULT NULL,
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 99 DEFAULT CHARSET = utf8 ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_article_type
-- ----------------------------
DROP TABLE IF EXISTS `t_article_type`;
CREATE TABLE `t_article_type`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `title` varchar(20) NOT NULL,
                                     `url` varchar(100) NULL DEFAULT NULL,
                                     `pid` int(5) NOT NULL DEFAULT 0,
                                     `status` int(2) NOT NULL DEFAULT 1,
                                     `create_time` int(20) NULL DEFAULT NULL,
                                     `update_time` int(20) NULL DEFAULT NULL,
                                     `sort` int(11) NULL DEFAULT NULL,
                                     `remark` varchar(200) NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 DEFAULT CHARSET = utf8 ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qiu_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
                             `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                             `url` char(80) NULL DEFAULT '',
                             `menu_name` char(20) NULL DEFAULT '',
                             `type` tinyint(1) NULL DEFAULT 1,
                             `status` tinyint(1) NULL DEFAULT 1,
                             `css` varchar(20) NULL DEFAULT NULL COMMENT '样式',
                             `condition` char(100) NULL DEFAULT NULL,
                             `pid` int(11) NULL DEFAULT 0 COMMENT '父栏目ID',
                             `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
                             `create_time` int(11) NULL DEFAULT 0 COMMENT '添加时间',
                             `update_time` int(11) NULL DEFAULT NULL,
                             `remark` mediumtext NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 139 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qiu_message
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `userid` int(11) NULL DEFAULT NULL,
                                `nickname` varchar(50) NULL DEFAULT NULL,
                                `email` varchar(20) NULL DEFAULT NULL,
                                `content` mediumtext NULL,
                                `create_time` datetime(0) NULL DEFAULT NULL,
                                `pid` int(11) NULL DEFAULT 0,
                                `replyCommentId` varchar(50) NULL DEFAULT NULL,
                                `articleId` int(11) NULL DEFAULT NULL COMMENT '文章自增ID,外键',
                                `status` int(1) NULL DEFAULT 1,
                                `avatar` varchar(100) NULL DEFAULT NULL,
                                `replyId` int(11) NULL DEFAULT NULL COMMENT '回复人的Id',
                                `replyNickName` varchar(255) NULL DEFAULT NULL,
                                `ip` varchar(50) NULL DEFAULT NULL,
                                `createAt` varchar(100) NULL DEFAULT NULL,
                                `os` varchar(50) NULL DEFAULT NULL,
                                `type` int(1) NULL DEFAULT NULL COMMENT '1.留言。2,文章评论',
                                `children` varchar(255) NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 192 DEFAULT CHARSET = utf8 ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
