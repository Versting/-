/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306
 Source Schema         : webdisk

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 03/01/2024 00:27:19
*/
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
                           `id` int DEFAULT NULL COMMENT '公司ID',
                           `name` varchar(255) DEFAULT NULL COMMENT '公司名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公司';



BEGIN;
INSERT INTO `company` (`id`, `name`) VALUES (1, '兔兔有限公司');
COMMIT;
-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
                              `id` int DEFAULT NULL COMMENT '部门ID',
                              `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门名称',
                              `company_id` int DEFAULT NULL COMMENT '公司ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门';


BEGIN;
INSERT INTO `department` (`id`, `name`, `company_id`) VALUES (1, '技术部', 1);
INSERT INTO `department` (`id`, `name`, `company_id`) VALUES (2, '行政部', 1);
INSERT INTO `department` (`id`, `name`, `company_id`) VALUES (3, '财务部', 1);
INSERT INTO `department` (`id`, `name`, `company_id`) VALUES (4, '销售部', 1);
INSERT INTO `department` (`id`, `name`, `company_id`) VALUES (5, '运营部', 1);
INSERT INTO `department` (`id`, `name`, `company_id`) VALUES (6, '研发部', 1);
INSERT INTO `department` (`id`, `name`, `company_id`) VALUES (7, '管理部', 1);

COMMIT;
-- ----------------------------
-- Table structure for directory
-- ----------------------------
DROP TABLE IF EXISTS `directory`;
CREATE TABLE `directory` (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '目录ID',
                             `department_id` int NOT NULL COMMENT '部门ID',
                             `parent_id` int DEFAULT NULL COMMENT '父目录ID',
                             `name` varchar(255) NOT NULL COMMENT '目录名',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             PRIMARY KEY (`id`),
                             KEY `fk` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='网盘目录';




-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '文件ID',
                        `user_id` int NOT NULL COMMENT '上传用户ID',
                        `directory_id` int DEFAULT NULL COMMENT '目录ID',
                        `name` varchar(255) NOT NULL COMMENT '文件名',
                        `url` varchar(255) NOT NULL COMMENT '文件url',
                        `size` bigint NOT NULL COMMENT '文件大小',
                        `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否逻辑删除 0-未删除 1-已删除',
                        `create_time` datetime NOT NULL COMMENT '上传时间',
                        PRIMARY KEY (`id`),
                        KEY `fk_user_id` (`user_id`),
                        CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='网盘文件';

-- ----------------------------
-- Table structure for file_sharing
-- ----------------------------
DROP TABLE IF EXISTS `file_sharing`;
CREATE TABLE `file_sharing` (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT '分享ID',
                                `user_id` int NOT NULL COMMENT '用户ID',
                                `file_id_list` int NOT NULL COMMENT '文件id集合\n',
                                `directory_id_list` int NOT NULL COMMENT '目录id集合',
                                `create_time` datetime NOT NULL COMMENT '分享时间',
                                PRIMARY KEY (`id`),
                                KEY `user_id` (`user_id`),
                                KEY `shared_user_id` (`file_id_list`),
                                KEY `file_id` (`directory_id_list`),
                                CONSTRAINT `file_sharing_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `file_sharing_ibfk_2` FOREIGN KEY (`file_id_list`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `file_sharing_ibfk_3` FOREIGN KEY (`directory_id_list`) REFERENCES `file` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件分享';

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
                       `id` int NOT NULL AUTO_INCREMENT COMMENT '日志ID',
                       `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求url',
                       `method` varchar(255) DEFAULT NULL COMMENT '请求方法',
                       `stack` varchar(1000) DEFAULT NULL COMMENT '异常信息',
                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日志';

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '消息ID',
                           `target_department_id` int NOT NULL COMMENT '接收部门ID',
                           `target_user_id` int DEFAULT NULL COMMENT '接收用户ID',
                           `user_id` int NOT NULL COMMENT '用户ID',
                           `text` varchar(255) NOT NULL COMMENT '消息文本',
                           `is_read` int NOT NULL DEFAULT '0' COMMENT '是否已读 0-未读 1-已读',
                           PRIMARY KEY (`id`),
                           KEY `user_id` (`user_id`),
                           CONSTRAINT `message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息通知';

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '操作日志ID',
                             `user_id` int NOT NULL COMMENT '用户ID',
                             `department_id` int NOT NULL COMMENT '部门ID',
                             `content` varchar(255) NOT NULL COMMENT '操作内容',
                             `file_id` int NOT NULL COMMENT '文件ID',
                             `create_time` datetime NOT NULL COMMENT '操作时间',
                             PRIMARY KEY (`id`),
                             KEY `user_id` (`user_id`),
                             KEY `file_id` (`file_id`),
                             CONSTRAINT `operation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                             CONSTRAINT `operation_ibfk_2` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                        `username` varchar(255) NOT NULL COMMENT '账号',
                        `password` varchar(255) NOT NULL COMMENT '密码',
                        `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电子邮件',
                        `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
                        `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '1-员工 2-部门主管 3-ceo',
                        `company_id` int DEFAULT NULL COMMENT '公司ID',
                        `department_id` int DEFAULT NULL COMMENT '部门ID',
                        `status` int DEFAULT NULL COMMENT '状态 0-审核中 1-正常\n',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';


BEGIN;
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (1, '兔兔', 'df0cfbf6c12654f60301a7ec9c180c17', '565173594@qq.com', null,'3', 1, 1,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`,`avatar`, `role`, `company_id`, `department_id`,`status`) VALUES (2, '张三', '6785d00aab1720954c509d1de0e35906', '286150401@qq.com', null,'2', 1, 1,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (3, '李四', '5c24a12b2ac00973abcb59a70253ab00', '337278831@qq.com', null,'1', 1, 1,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (4, '王五', 'cbe505a1d1b917d9a7634908bddcc498', '113651641@qq.com', null,'2', 1, 2,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`,`avatar`, `role`, `company_id`, `department_id`,`status`) VALUES (5, '赵六', '8f8af0ad788c906a12aeb03a2f7e0b9a', '577485555@qq.com', null,'1', 1, 2,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`,`avatar`, `role`, `company_id`, `department_id`,`status`) VALUES (6, '陈七', '25269c1d694c0b67ff64557b45e620fb', '176378078@qq.com', null,'1', 1, 2,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`,`avatar`, `role`, `company_id`, `department_id`,`status`) VALUES (7, '刘八', '0b03356e086242f194dab97f76b09f3f', '808660484@qq.com', null,'1', 1, 3,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (8, '周九', 'd2b7c560d7f13544a5f8637ccdabe803', '601473315@qq.com',null,'2', 1, 3,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (9, '钱十', 'fbec0618c6e3d98dc9bbbbaf0818b6d7', '184518905@qq.com', null,'1', 1, 3,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (10, '孙十一', 'b70cbe742734676cd6224b51072ec48f', '262388003@qq.com', null,'2', 1, 4,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (11, '范十二', '8def79f4ece936d3eb6bbade247f27ae', '424865486@qq.com', null,'1', 1, 4,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (12, '罗十三', '35fa59ef0eebcb202b6285ca6e2e5254', '584898211@qq.com', null,'1', 1, 4,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (13, '马十四', '369d8832747742a21fee06f19e976e68', '212502166@qq.com', null,'1', 1, 5,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (14, '潘十五', '81c06d3bdcaa88e0d62c7c0787576977', '289762784@qq.com', null,'2', 1, 5,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`,`avatar`, `role`, `company_id`, `department_id`,`status`) VALUES (15, '章十六', '7f56271448a29ead71d8227c450fcb0b', '580495713@qq.com', null,'1', 1, 5,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (16, '李十七', 'fa68e17b3685930c7dc65b4148230948', '415189373@qq.com', null,'2', 1, 6,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (17, '柳十八', '83a5b8215c5e9adfb6febebdee56f59f', '505065207@qq.com', null,'1', 1, 6,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (18, '曾十九', '750583ecbc0b9fdb160784336e7f36d1', '785744074@qq.com', null,'1', 1, 6,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (19, '黄二十', '07c59b84efe017738e7d1943015fc0d6', '557542270@qq.com', null,'1', 1, 7,1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `avatar`,`role`, `company_id`, `department_id`,`status`) VALUES (20, '王一一', '954d90f1889e5f05cf957f3306a4d1c2', '425837210@qq.com', null,'2', 1, 7,1);
COMMIT;
SET FOREIGN_KEY_CHECKS = 1;


