
drop database basestation;

create database basestation character set utf8;

use basestation;

create table `sys_user` (
	`id` int auto_increment primary key,  -- id主键
    `username` varchar(50) not null,  -- 用户名
    `password` varchar(100) not null,  -- 密码
    `real_name` varchar(20) not null,  -- 真实姓名
    `salt` varchar(100) not null,  -- 密码md5盐
    `cell_phone_number` varchar(20),  -- 手机号
    `email` varchar(200), -- 邮箱
    `state` tinyint not null, -- 锁定标志位
    `deleted` tinyint not null,  -- 逻辑删除标志位 0-代表未删除 1-代表已删除
    `created_by` int not null,  -- 创建人
    `created_date` timestamp not null default current_timestamp,  -- 创建时间
    `last_modified_by` int,  -- 最后修改人
    `last_modified_date` timestamp default current_timestamp on update current_timestamp -- 最后修改时间
)engine=innodb default charset=utf8 comment '人员信息表';


create table `sys_menu` (
	`id` int primary key,  -- id主键
	`parent_id` varchar(100) not null,  -- 父级菜单id
	`name` varchar(100) not null,  -- 权限名字
	`title` varchar(100) not null,  -- 菜单显示名字
	`icon` varchar(100),  -- 菜单显示图标
	`url` varchar(500), -- 菜单指向路径
	`component` varchar(500),  -- 菜单指向组件
	`deleted` tinyint not null,  -- 逻辑删除标志位 0-代表未删除 1-代表已删除
	`created_by` int not null,  -- 创建人
	`created_date` timestamp not null default current_timestamp,  -- 创建时间
	`last_modified_by` int,  -- 最后修改人
	`last_modified_date` timestamp default current_timestamp on update current_timestamp -- 最后修改时间
)engine=innodb default charset=utf8 comment '菜单信息表';


create table `sys_role` (
	`id` int auto_increment primary key,  -- id主键
    `role_name` varchar(100) not null,  -- 角色名字
    `remarks` text not null,  -- 备注
    `deleted` tinyint not null,  -- 逻辑删除标志位 0-代表未删除 1-代表已删除
    `created_by` int not null,  -- 创建人
    `created_date` timestamp not null default current_timestamp,  -- 创建时间
    `last_modified_by` int,  -- 最后修改人
    `last_modified_date` timestamp default current_timestamp on update current_timestamp -- 最后修改时间
)engine=innodb default charset=utf8 comment '角色信息表';


create table `sys_role_menu_mapping` (
	`role_id` int not null,  -- 角色id
    `menu_id` int not null,  -- 菜单id
    primary key(`role_id`, `menu_id`)
)engine=innodb default charset=utf8 comment '角色菜单关联表';


create table `sys_role_user_mapping` (
	`role_id` int not null,  -- 角色id,
	`user_id` int not null,  -- 用户id,
    primary key(`role_id`, `user_id`)
)engine=innodb default charset=utf8 comment '角色人员关联表';


create table `operation_log` (
	`id` int auto_increment primary key,  -- id主键
    `log_level` tinyint not null,  -- 日志级别
    `operation_name` varchar(50) not null,  -- 操作名称
    `content` text not null,  -- 审计内容
    `created_by` int not null,  -- 创建人
    `created_date` timestamp not null default current_timestamp,  -- 创建时间
    `last_modified_by` int,  -- 最后修改人
    `last_modified_date` timestamp default current_timestamp on update current_timestamp -- 最后修改时间
)engine=innodb default charset=utf8 comment '操作日志表';


create table `upgrade_file` (
`id` int auto_increment primary key,  -- id主键
`file_name` varchar(100) not null ,  -- 文件名
`file_size` int, -- 文件大小
`file_path` varchar(500) not null,  -- 文件保存路径
`file_type` varchar(16),  -- 文件类型
`md5` varchar(64) not null, -- 文件md5
`state` tinyint not null,  -- 文件状态
`deleted` tinyint not null,  -- 逻辑删除标志位
`created_by` int not null,  -- 创建人
`created_date` timestamp not null default current_timestamp,  -- 创建时间
`last_modified_by` int,  -- 最后修改人
`last_modified_date` timestamp default current_timestamp on update current_timestamp  -- 最后修改时间
)engine=innodb default charset=utf8 comment '升级文件表';


create table `upgrade_version`(
	`id` int auto_increment primary key,  -- id主键
    `version_num` varchar(100) not null ,  -- 版本号
    `file_id` varchar(100) not null,  -- 升级文件id
    `send_user_id` int not null, -- 下发人ID
    `send_time` timestamp, -- 下发人ID
    `state` tinyint not null,  -- 版本状态
	`deleted` tinyint not null,  -- 逻辑删除标志位 0-代表未删除 1-代表已删除
    `created_by` int not null,  -- 创建人
    `created_date` timestamp not null default current_timestamp,  -- 创建时间
    `last_modified_by` int,  -- 最后修改人
    `last_modified_date` timestamp default current_timestamp on update current_timestamp -- 最后修改时间
)engine=innodb default charset=utf8 comment '升级版本表';

create table `upgrade_result`(
	`id` int auto_increment primary key,  -- id主键
    `terminal_id` int not null ,  -- 升级终端id 代表地基站id
    `pre_version_id` int not null,  -- 升级前版本
    `after_version_id` int not null,  -- 升级后版本
    `send_user_id` int not null, -- 下法人id
    `state` tinyint not null,  -- 版本状态
    `error_code` tinyint, -- 失败错误code
    `created_by` int not null,  -- 创建人
    `created_date` timestamp not null default current_timestamp,  -- 创建时间
    `last_modified_by` int,  -- 最后修改人
    `last_modified_date` timestamp default current_timestamp on update current_timestamp -- 最后修改时间
)engine=innodb default charset=utf8 comment '升级结果表';

create table `dictionary_type`(
	`id` int auto_increment primary key,  -- id主键
    `dic_code` int not null,  -- 数据字典分类code
    `dic_name` varchar(100) not null -- 数据字典分类名字
)engine=MyISAM default charset=utf8 comment '数据字典类型表';

create table `dictionary_item`(
	`id` int auto_increment primary key,  -- id主键
    `dic_code` int not null,  -- 数据字典分类code
    `item_code` int not null,  -- 数据字典项code
    `item_name` varchar(100) not null  -- 数据字典项名字
)engine=MyISAM default charset=utf8 comment '数据字典项表';
