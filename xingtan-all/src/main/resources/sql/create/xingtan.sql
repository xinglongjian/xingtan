CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '中文名称',
  `nick_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '昵称',
  `en_name` VARCHAR(255)  NOT NULL DEFAULT '' COMMENT '英文名称',
  `password` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '密码',
  `telephone` VARCHAR(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `email` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '邮箱',
  `from_source` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '来源',
  `created_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '由谁添加',
  `token` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'token',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_name`(`user_name`) USING BTREE ,
  KEY `idx_token`(`token`) USING BTREE,
  KEY `idx_status`(`status`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户表';

CREATE TABLE IF NOT EXISTS `user_base_data` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户ID',
  `sex` VARCHAR(255) NOT NULL DEFAULT 'UNKNOWN' COMMENT '性别',
  `birthday` DATETIME NULL COMMENT '生日',
  `head_image` TEXT COMMENT '头像',
  `id_card_type` VARCHAR(255) NOT NULL DEFAULT 'IDCARD' COMMENT '证件类型',
  `id_card_no` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '证件号',
  `country` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '国家',
  `province` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '省/直辖市',
  `city` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '市',
  `district` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '区',
  `address` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '其他地址',
  `introduce` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '个人介绍',
  `weixin` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '微信',
  `qq` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'qq',
  `open_id` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'openId',
  `union_id` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'unionId',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id`(`user_id`) USING BTREE ,
  KEY `idx_birthday`(`birthday`) USING BTREE,
  KEY `idx_sex`(`sex`) USING BTREE,
  KEY `idx_province_city_district`(`province`,`city`,`district`) USING BTREE,
  KEY `idx_open_id`(`open_id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户基本信息表';

CREATE TABLE IF NOT EXISTS `question_type` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '类型名称',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name`(`name`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '问题类型表';

CREATE TABLE IF NOT EXISTS `question` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` TEXT COMMENT '内容',
  `type_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '问题类型ID',
  `created_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建者',
  `status` varchar(20) NOT NULL DEFAULT 'NEW' COMMENT '名称',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_type_id`(`type_id`) USING BTREE ,
  KEY `idx_status`(`status`) USING BTREE ,
  UNIQUE KEY `idx_created_user_id`(`created_user_id`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '问题表';

CREATE TABLE IF NOT EXISTS `area` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `no` varchar(255) NOT NULL DEFAULT '' COMMENT '区号',
  `name` varchar(255) NOT NULL DEFAULT '',
  `parent_no` varchar(255) NOT NULL DEFAULT '' COMMENT '父-区号',
  `area_code` varchar(255) NOT NULL DEFAULT '' COMMENT '电话区号',
  `area_level` int NOT NULL DEFAULT 0,
  `type_name` varchar(255) DEFAULT NULL,
  `abbreviate` varchar(10) DEFAULT NULL COMMENT '简写',
  `post_code` varchar(10) DEFAULT NULL COMMENT '邮编',
  `pinyin` varchar(255) DEFAULT NULL COMMENT '拼音',
  `pinyin_brief` varchar(255) DEFAULT NULL COMMENT '拼音缩写',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_no`(`parent_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区划表';