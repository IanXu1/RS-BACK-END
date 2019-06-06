use basestation;
SET SQL_SAFE_UPDATES = 0;

alter table `sys_user` add column `duty` tinyint;
update `sys_user` set duty = 2;

drop table if exists `warning_type`;
create table `warning_type` (
	`id` int primary key auto_increment,    
    `name` varchar(64) not null comment '类型名称',
    `type` tinyint not null comment '父类类型',
    `sub_type` int not null comment '子类类型',    
    `level` tinyint comment '告警级别'
) engine=innodb default charset=utf8 comment '告警类型表';

drop table if exists `warning_level`;
create table `warning_level` (
	`id` int primary key auto_increment,
    `level` tinyint not null comment '告警级别',
    `name` varchar(64) not null comment '级别名称'
) engine=innodb default charset=utf8 comment '告警级别表';

drop table if exists `warning_visible`;
create table `warning_visible` (
	`id` int primary key auto_increment,
	`warning_type_id` int comment '告警类型id',
    `user_duty` int comment '人员职务', -- 区分客户技服或管理
    `visible` boolean comment '是否可见 0--不可见 1--可见',
	`content` varchar(64) comment '可见内容'
) engine=innodb default charset=utf8 comment '告警可见配置表';

drop table if exists `warning_user_mapping`;
create table `warning_user_mapping`(
	`warning_type_id` int not null,  -- id主键
    `user_id` int not null,  -- 配置名称
    primary key(`warning_type_id`, `user_id`)
)engine=innodb default charset=utf8 comment '告警推送映射表';


-- 告警级别初始化数据
insert into `warning_level`(`level`, `name`) values (1, '提示');
insert into `warning_level`(`level`, `name`) values (2, '一般');
insert into `warning_level`(`level`, `name`) values (3, '严重');
insert into `warning_level`(`level`, `name`) values (4, '故障');


-- 告警类型初始化数据
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (1, '通信类', -1, 1, -1);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (2, '设备下线', 1, 16842752, 2);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (3, '设备长期不在线', 1, 16908288, 4);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (4, '长时间收不到差分数据', 1, 16973824, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (5, 'SIM卡异常', 1, 17039360, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (6, '以太网/4G切换', 1, 17104896, 1);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (7, 'SIM卡切换', 1, 17039616, 1);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (8, '4G模块异常', 1, 17105152, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (9, '无信通信信号丢失', 1, 17105408, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (10, '通信模式不可用', 1, 17105664, 3);

insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (11, '定位类', -1, 2, -1);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (12, 'nmea 没有输出', 2, 33619968, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (13, '搜不到星', 2, 33685504, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (14, '卫星信号弱', 2, 33685760, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (15, '卫星解算数量异常', 2, 33686016, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (16, '差分数据龄期过大', 2, 33620224, 3);

insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (17, '电源类', -1, 3, -1);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (18, '12V电源预警', 3, 50397184, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (19, '3.3V电源预警', 3, 50462720, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (20, '4G电源预警', 3, 50528256, 3);

insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (21, '传感器类', -1, 6, -1);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (22, '单风扇告警', 6, 100728832, 1);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (23, '双风扇告警', 6, 100729088, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (24, 'RTC电池电压低', 6, 100794368, 2);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (25, '高温告警', 6, 100859904, 2);

insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (26, '系统类', -1, 7, -1);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (27, '软件回滚告警', 7, 117440512, 3);
insert into `warning_type`(`id`, `name`, `type`, `sub_type`, `level`) values (28, '软件回滚安全模式', 7, 134217727, 4);

-- 告警可见配置(duty为1代表客服管理, duty为2代表客户技服)
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (2, 1, 1, '设备下线');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (3, 1, 1, '设备长期离线');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (4, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (5, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (6, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (7, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (8, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (9, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (10, 1, 0, '');

insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (12, 1, 1, '无定位');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (13, 1, 1, '无定位');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (14, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (15, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (16, 1, 0, '');

insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (18, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (19, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (20, 1, 0, '');

insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (22, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (23, 1, 1, '硬件故障');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (24, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (25, 1, 0, '');

insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (27, 1, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (28, 1, 0, '');


-- 告警可见配置(duty为1代表客服管理, duty为2代表客户技服)
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (2, 2, 1, '设备下线');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (3, 2, 1, '设备长期离线');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (4, 2, 1, '长时间收不到差分数据');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (5, 2, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (6, 2, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (7, 2, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (8, 2, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (9, 2, 0, '无线信号不稳定');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (10, 2, 0, '无线信号不稳定');

insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (12, 2, 1, '无定位');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (13, 2, 1, '无定位');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (14, 2, 0, '卫星信号弱');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (15, 2, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (16, 2, 0, '');

insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (18, 2, 0, '电源异常');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (19, 2, 0, '电源异常');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (20, 2, 0, '电源异常');

insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (22, 2, 0, '');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (23, 2, 1, '风扇故障');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (24, 2, 0, 'RTC电池故障');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (25, 2, 0, '高温告警');

insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (27, 2, 0, '软件升级失败');
insert into `warning_visible` (`warning_type_id`, `user_duty`, `visible`, `content`) values (28, 2, 0, '软件安全模式');