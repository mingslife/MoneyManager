/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : moneymanager

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-05-07 21:18:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for billforin
-- ----------------------------
DROP TABLE IF EXISTS `billforin`;
CREATE TABLE `billforin` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `date` double DEFAULT NULL COMMENT '入账日期',
  `amount` double DEFAULT NULL COMMENT '金额',
  `fromId` varchar(36) DEFAULT NULL COMMENT '经手人ID',
  `toId` varchar(36) DEFAULT NULL COMMENT '接受人ID',
  `recordId` varchar(36) DEFAULT NULL COMMENT '记录人ID',
  `createDate` double DEFAULT NULL COMMENT '记录日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of billforin
-- ----------------------------

-- ----------------------------
-- Table structure for billforout
-- ----------------------------
DROP TABLE IF EXISTS `billforout`;
CREATE TABLE `billforout` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `date` double DEFAULT NULL COMMENT '支付日期',
  `event` varchar(255) DEFAULT NULL COMMENT '事项',
  `amount` double DEFAULT NULL COMMENT '金额',
  `payId` varchar(36) DEFAULT NULL COMMENT '支付人ID',
  `status` int(11) DEFAULT NULL COMMENT '状态（0:未支付  1:已支付  2:待审核  3:不通过）',
  `recordId` varchar(36) DEFAULT NULL COMMENT '记录人ID',
  `createDate` double DEFAULT NULL COMMENT '记录日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of billforout
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menuId` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `no` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('001', '首页', '/', '', '1', '0');
INSERT INTO `menu` VALUES ('002', '用户管理', '/view/user.html', 'user', '2', '0');
INSERT INTO `menu` VALUES ('003', '入账管理', '/view/billforin.html', 'billforin', '3', '0');
INSERT INTO `menu` VALUES ('004', '支出管理', '/view/billforout.html', 'billforout', '4', '0');
INSERT INTO `menu` VALUES ('005', '报账记录', '/view/apply.html', 'apply', '5', '0');
INSERT INTO `menu` VALUES ('006', '报账审核', '/view/review.html', 'review', '6', '0');
INSERT INTO `menu` VALUES ('007', '支付管理', '/view/pay.html', 'pay', '7', '0');
INSERT INTO `menu` VALUES ('008', '关于', '/view/about.html', 'about', '8', '0');
INSERT INTO `menu` VALUES ('101', '首页', '/', '', '1', '1');
INSERT INTO `menu` VALUES ('102', '用户管理', '/view/user.html', 'user', '2', '1');
INSERT INTO `menu` VALUES ('103', '入账管理', '/view/billforin.html', 'billforin', '3', '1');
INSERT INTO `menu` VALUES ('104', '支出管理', '/view/billforout.html', 'billforout', '4', '1');
INSERT INTO `menu` VALUES ('105', '报账记录', '/view/apply.html', 'apply', '5', '1');
INSERT INTO `menu` VALUES ('108', '关于', '/view/about.html', 'about', '8', '1');
INSERT INTO `menu` VALUES ('201', '首页', '/', '', '1', '2');
INSERT INTO `menu` VALUES ('205', '报账记录', '/view/apply.html', 'apply', '5', '2');
INSERT INTO `menu` VALUES ('208', '关于', '/view/about.html', 'about', '8', '2');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` varchar(36) NOT NULL,
  `type` varchar(1) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `createTime` double DEFAULT NULL,
  `userId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('19675568-3f75-41d4-a4fa-717a9890a30e', '1', '尊敬的黄则鸣，欢迎您使用本系统', '20160507211445', 'd30836a3-3dc0-4d67-9715-dcecbd27b4b6');

-- ----------------------------
-- Table structure for page
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page` (
  `pageId` varchar(36) NOT NULL COMMENT 'ID',
  `page` varchar(255) DEFAULT NULL COMMENT '网页路径',
  `roleId` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`pageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page
-- ----------------------------
INSERT INTO `page` VALUES ('1', '/main.html', '0');
INSERT INTO `page` VALUES ('10', '/view/welcome.html', '0');
INSERT INTO `page` VALUES ('11', '/view/welcome.html', '1');
INSERT INTO `page` VALUES ('12', '/view/welcome.html', '2');
INSERT INTO `page` VALUES ('13', '/view/apply.html', '0');
INSERT INTO `page` VALUES ('14', '/view/apply.html', '1');
INSERT INTO `page` VALUES ('15', '/view/apply.html', '2');
INSERT INTO `page` VALUES ('16', '/view/review.html', '0');
INSERT INTO `page` VALUES ('17', '/view/pay.html', '0');
INSERT INTO `page` VALUES ('2', '/main.html', '1');
INSERT INTO `page` VALUES ('3', '/main.html', '2');
INSERT INTO `page` VALUES ('4', '/view/user.html', '0');
INSERT INTO `page` VALUES ('5', '/view/user.html', '1');
INSERT INTO `page` VALUES ('6', '/view/billforin.html', '0');
INSERT INTO `page` VALUES ('7', '/view/billforin.html', '1');
INSERT INTO `page` VALUES ('8', '/view/billforout.html', '0');
INSERT INTO `page` VALUES ('9', '/view/billforout.html', '1');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` varchar(36) NOT NULL,
  `actionName` varchar(255) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'user_logout', '0', '用户注销');
INSERT INTO `permission` VALUES ('10', 'user_save', '0', '用户新增');
INSERT INTO `permission` VALUES ('11', 'user_edit', '0', '用户编辑');
INSERT INTO `permission` VALUES ('12', 'user_delete', '0', '用户删除');
INSERT INTO `permission` VALUES ('13', 'role_load', '0', '角色读取');
INSERT INTO `permission` VALUES ('14', 'role_load', '1', '角色读取');
INSERT INTO `permission` VALUES ('15', 'billForIn_load', '0', '入账读取');
INSERT INTO `permission` VALUES ('16', 'billForIn_load', '1', '入账读取');
INSERT INTO `permission` VALUES ('17', 'billForIn_count', '0', '入账计数');
INSERT INTO `permission` VALUES ('18', 'billForIn_count', '1', '入账计数');
INSERT INTO `permission` VALUES ('19', 'billForIn_loadOne', '0', '入账详情');
INSERT INTO `permission` VALUES ('2', 'user_logout', '1', '用户注销');
INSERT INTO `permission` VALUES ('20', 'billForIn_loadOne', '1', '入账详情');
INSERT INTO `permission` VALUES ('21', 'billForIn_save', '0', '入账新增');
INSERT INTO `permission` VALUES ('22', 'billForIn_edit', '0', '入账编辑');
INSERT INTO `permission` VALUES ('23', 'billForIn_delete', '0', '入账删除');
INSERT INTO `permission` VALUES ('24', 'user_loadAll', '0', '用户列表');
INSERT INTO `permission` VALUES ('25', 'user_loadAll', '1', '用户列表');
INSERT INTO `permission` VALUES ('26', 'user_currentId', '0', '用户ID');
INSERT INTO `permission` VALUES ('27', 'billForOut_load', '0', '支出读取');
INSERT INTO `permission` VALUES ('28', 'billForOut_load', '1', '支出读取');
INSERT INTO `permission` VALUES ('29', 'billForOut_count', '0', '支出计数');
INSERT INTO `permission` VALUES ('3', 'user_logout', '2', '用户注销');
INSERT INTO `permission` VALUES ('30', 'billForOut_count', '1', '支出计数');
INSERT INTO `permission` VALUES ('31', 'billForOut_loadOne', '0', '支出详情');
INSERT INTO `permission` VALUES ('32', 'billForOut_loadOne', '1', '支出详情');
INSERT INTO `permission` VALUES ('33', 'billForOut_save', '0', '支出新增');
INSERT INTO `permission` VALUES ('34', 'billForOut_edit', '0', '支出编辑');
INSERT INTO `permission` VALUES ('35', 'billForOut_delete', '0', '支出删除');
INSERT INTO `permission` VALUES ('36', 'message_loadMine', '0', '个人消息读取');
INSERT INTO `permission` VALUES ('37', 'message_loadMine', '1', '个人消息读取');
INSERT INTO `permission` VALUES ('38', 'message_loadMine', '2', '个人消息读取');
INSERT INTO `permission` VALUES ('39', 'message_deleteMine', '0', '个人消息删除');
INSERT INTO `permission` VALUES ('4', 'user_load', '0', '用户读取');
INSERT INTO `permission` VALUES ('40', 'message_deleteMine', '1', '个人消息删除');
INSERT INTO `permission` VALUES ('41', 'message_deleteMine', '2', '个人消息删除');
INSERT INTO `permission` VALUES ('42', 'message_countMine', '0', '个人消息计数');
INSERT INTO `permission` VALUES ('43', 'message_countMine', '1', '个人消息计数');
INSERT INTO `permission` VALUES ('44', 'message_countMine', '2', '个人消息计数');
INSERT INTO `permission` VALUES ('45', 'billForOut_apply', '0', '报账');
INSERT INTO `permission` VALUES ('46', 'billForOut_apply', '1', '报账');
INSERT INTO `permission` VALUES ('47', 'billForOut_apply', '2', '报账');
INSERT INTO `permission` VALUES ('48', 'user_loadMine', '0', '个人信息读取');
INSERT INTO `permission` VALUES ('49', 'user_loadMine', '1', '个人信息读取');
INSERT INTO `permission` VALUES ('5', 'user_load', '1', '用户读取');
INSERT INTO `permission` VALUES ('50', 'user_loadMine', '2', '个人信息读取');
INSERT INTO `permission` VALUES ('51', 'user_editMine', '0', '个人信息编辑');
INSERT INTO `permission` VALUES ('52', 'user_editMine', '1', '个人信息编辑');
INSERT INTO `permission` VALUES ('53', 'user_editMine', '2', '个人信息编辑');
INSERT INTO `permission` VALUES ('54', 'menu_loadMine', '0', '个人菜单读取');
INSERT INTO `permission` VALUES ('55', 'menu_loadMine', '1', '个人菜单读取');
INSERT INTO `permission` VALUES ('56', 'menu_loadMine', '2', '个人菜单读取');
INSERT INTO `permission` VALUES ('57', 'billForOut_loadMine', '0', '个人报账读取');
INSERT INTO `permission` VALUES ('58', 'billForOut_loadMine', '1', '个人报账读取');
INSERT INTO `permission` VALUES ('59', 'billForOut_loadMine', '2', '个人报账读取');
INSERT INTO `permission` VALUES ('6', 'user_count', '0', '用户计数');
INSERT INTO `permission` VALUES ('60', 'billForOut_countMine', '0', '个人报账计数');
INSERT INTO `permission` VALUES ('61', 'billForOut_countMine', '1', '个人报账计数');
INSERT INTO `permission` VALUES ('62', 'billForOut_countMine', '2', '个人报账计数');
INSERT INTO `permission` VALUES ('63', 'billForOut_loadReview', '0', '报账读取');
INSERT INTO `permission` VALUES ('64', 'billForOut_countReview', '0', '报账计数');
INSERT INTO `permission` VALUES ('65', 'billForOut_loadOneReview', '0', '报账详情');
INSERT INTO `permission` VALUES ('66', 'billForOut_reviewOk', '0', '报账审核通过');
INSERT INTO `permission` VALUES ('67', 'billForOut_reviewNo', '0', '报账审核不通过');
INSERT INTO `permission` VALUES ('68', 'billForOut_loadPay', '0', '支付读取');
INSERT INTO `permission` VALUES ('69', 'billForOut_countPay', '0', '支付读取');
INSERT INTO `permission` VALUES ('7', 'user_count', '1', '用户计数');
INSERT INTO `permission` VALUES ('70', 'billForOut_paid', '0', '支付记录更改为已支付');
INSERT INTO `permission` VALUES ('71', 'billForOut_unpaid', '0', '支付记录更改为未支付');
INSERT INTO `permission` VALUES ('72', 'billForIn_sumUp', '0', '入账统计');
INSERT INTO `permission` VALUES ('73', 'billForOut_sumUp', '0', '支出统计');
INSERT INTO `permission` VALUES ('74', 'billForOut_sumUpMine', '0', '个人报账统计');
INSERT INTO `permission` VALUES ('75', 'billForOut_sumUpMine', '1', '个人报账统计');
INSERT INTO `permission` VALUES ('76', 'billForOut_sumUpMine', '2', '个人报账统计');
INSERT INTO `permission` VALUES ('77', 'billForOut_sumUpReview', '0', '报账统计');
INSERT INTO `permission` VALUES ('78', 'billForOut_sumUpPay', '0', '支付统计');
INSERT INTO `permission` VALUES ('79', 'billForIn_sumUpMoney', '0', '余额统计');
INSERT INTO `permission` VALUES ('8', 'user_loadOne', '0', '用户详情');
INSERT INTO `permission` VALUES ('80', 'billForIn_sumUpMoney', '1', '余额统计');
INSERT INTO `permission` VALUES ('81', 'billForIn_sumUp', '1', '入账统计');
INSERT INTO `permission` VALUES ('82', 'billForOut_sumUp', '1', '支出统计');
INSERT INTO `permission` VALUES ('83', 'message_reply', '0', '意见反馈');
INSERT INTO `permission` VALUES ('84', 'message_reply', '1', '意见反馈');
INSERT INTO `permission` VALUES ('85', 'message_reply', '2', '意见反馈');
INSERT INTO `permission` VALUES ('86', 'user_welcome', '0', '首页数据');
INSERT INTO `permission` VALUES ('87', 'user_welcome', '1', '首页数据');
INSERT INTO `permission` VALUES ('88', 'user_welcome', '2', '首页数据');
INSERT INTO `permission` VALUES ('89', 'billForOut_sumUpNeed', '0', '支付还需金额');
INSERT INTO `permission` VALUES ('9', 'user_loadOne', '1', '用户详情');
INSERT INTO `permission` VALUES ('90', 'billForIn_sumUpNeed', '0', '所有支付还需金额');
INSERT INTO `permission` VALUES ('91', 'billForIn_sumUpNeed', '1', '所有支付还需金额');
INSERT INTO `permission` VALUES ('92', 'user_test', '0', '测试用户登录');
INSERT INTO `permission` VALUES ('93', 'user_test', '1', '测试用户登录');
INSERT INTO `permission` VALUES ('94', 'user_test', '2', '测试用户登录');
INSERT INTO `permission` VALUES ('95', 'role_load', '2', '角色读取');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleId` int(11) NOT NULL COMMENT 'ID',
  `roleName` varchar(50) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0', '超级管理员');
INSERT INTO `role` VALUES ('1', '管理员');
INSERT INTO `role` VALUES ('2', '普通用户');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `loginName` varchar(32) DEFAULT NULL COMMENT '登录名',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别（0:男  1:女）',
  `birthday` double DEFAULT NULL COMMENT '生日',
  `email` varchar(255) DEFAULT NULL,
  `qq` varchar(50) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL COMMENT '角色ID',
  `isActive` int(11) DEFAULT NULL COMMENT '是否激活（0:未激活  1:已激活）',
  `createDate` double DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('d30836a3-3dc0-4d67-9715-dcecbd27b4b6', '6D41FBBD7272A4C7FAAB628092B128EE', '黄则鸣', '6D41FBBD7272A4C7FAAB628092B128EE', '0', '19940503', '642203604@qq.com', '642203604', '2', '1', '20160507');
INSERT INTO `user` VALUES ('e4afd70a-90cc-4471-92ae-e5fb360223e8', 'C3284D0F94606DE1FD2AF172ABA15BF3', '超级管理员', 'C3284D0F94606DE1FD2AF172ABA15BF3', '0', '19940503', '642203604@qq.com', '642203604', '0', '1', '20150717');
