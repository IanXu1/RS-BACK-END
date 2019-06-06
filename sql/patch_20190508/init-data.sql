use basestation;

SET SQL_SAFE_UPDATES = 0;

-- 密码都是123456
insert into sys_user(username, password, salt, real_name, cell_phone_number, state, created_by, deleted)
values('admin', '96428944f947117a1c4ade8dc9beb55e', '78d92ba9477b3661bc8be4bd2e8dd8c0', '管理员', 'xx', 0, 1, 0);
insert into sys_user(username, password, salt, real_name, cell_phone_number, state, created_by, deleted)
values('letmein', '96428944f947117a1c4ade8dc9beb55e', '78d92ba9477b3661bc8be4bd2e8dd8c0', '管理员', 'xx', 0, 1, 0);


INSERT INTO sys_role(role_name, remarks, deleted, created_by) VALUES ('超级管理员', '拥有全部权限', 0, 1);
INSERT INTO sys_role(role_name, remarks, deleted, created_by) VALUES ('普通用户', '拥有全部查看权限，以及角色的增删改权限', 0, 1);

insert into sys_role_user_mapping(role_id, user_id) values(1, 1);
insert into sys_role_user_mapping(role_id, user_id) values(1, 2);
insert into sys_role_user_mapping(role_id, user_id) values(1, 3);

INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (1, -1, 'baseInfo', '基础信息管理', 'base-info', null, null, 0, 1);

INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (1001, 1, 'userInfo', '用户信息管理', 'user', 'user/list', 'basic-information/user-information/index', 0, 1);

INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100101, 1001, 'user:add', '用户新增', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100102, 1001, 'user:modify', '用户修改', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100103, 1001, 'user:del', '用户删除', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100104, 1001, 'user:view', '用户详情', null, null, null, 0, 1);


insert into sys_role_menu_mapping(role_id, menu_id) values(1, 1);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 1001);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100101);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100102);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100103);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100104);


-- ------------------------地基站管理---------------------
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (1002, 1, 'stationInfo', '地基站信息管理', 'station-info', 'stationinfo/list', 'basic-information/basestation-information/index', 0, 1);

INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100201, 1002, 'stationInfo:add', '新增地基站', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100202, 1002, 'stationInfo:modify', '新增地基站', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100203, 1002, 'stationInfo:del', '新增地基站', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100204, 1002, 'stationInfo:view', '地基站详情', null, null, null, 0, 1);


insert into sys_role_menu_mapping(role_id, menu_id) values(1, 1002);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100201);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100202);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100203);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100204);


-- ------------------------接入账户管理---------------------
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (1003, 1, 'terminalInfo', '接入账户管理', 'terminal', 'terminalinfo/list', 'basic-information/terminal-information/index', 0, 1);

INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100301, 1003, 'terminalInfo:add', '新增接入账户', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100302, 1003, 'terminalInfo:modify', '修改接入账户', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100303, 1003, 'terminalInfo:del', '删除接入账户', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (100304, 1003, 'terminalInfo:view', '接入账户详情', null, null, null, 0, 1);


insert into sys_role_menu_mapping(role_id, menu_id) values(1, 1003);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100301);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100302);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100303);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 100304);



-- 新增一级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (2, -1, 'upgrade', '终端升级与运维', 'upgrade-operation', null, null, 0, 1);

-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (2001, 2, 'uploadFile', '文件上传', 'upload-file', 'uploadfile/list', 'upgrade-operation/upload-file/index', 0, 1);

INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (200101, 2001, 'uploadFile:add', '新增升级文件', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (200102, 2001, 'uploadFile:del', '删除升级文件', null, null, null, 0, 1);


-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (2002, 2, 'versionIssue', '版本下发', 'version-issue', 'versionissue/list', 'upgrade-operation/version-issue/index', 0, 1);

INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (200201, 2002, 'versionIssue:add', '新增升级版本', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (200202, 2002, 'versionIssue:del', '删除升级版本', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (200203, 2002, 'versionIssue:issue', '下发升级版本', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (200204, 2002, 'versionIssue:result', '升级结果查询', null, null, null, 0, 1);


-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (2003, 2, 'terminalStatus', '终端状态', 'terminal-status', 'terminalstatus/show', 'upgrade-operation/terminal-status/index', 0, 1);


insert into sys_role_menu_mapping(role_id, menu_id) values(1, 2);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 2001);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 200101);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 200102);

insert into sys_role_menu_mapping(role_id, menu_id) values(1, 2002);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 200201);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 200202);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 200203);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 200204);

insert into sys_role_menu_mapping(role_id, menu_id) values(1, 2003);



-- 新增一级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (3, -1, 'statistics', '记录与统计', 'statistics', null, null, 0, 1);

-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (3001, 3, 'operateLog', '操作日志', 'operate-log', 'operatelog/list', 'record-statistics/operate-log/index', 0, 1);

-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (3002, 3, 'authLog', '鉴权日志', 'auth-log', 'authlog/list', 'record-statistics/authentication-log/index', 0, 1);

-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (3003, 3, 'warningInfo', '告警信息', 'warning-info', 'warninginfo/list', 'record-statistics/warning-information/index', 0, 1);

INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (300301, 3003, 'warninginfo:export', '告警导出', null, null, null, 0, 1);



insert into sys_role_menu_mapping(role_id, menu_id) values(1, 3);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 3001);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 3002);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 3003);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 300301);




-- 新增一级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (4, -1, 'systemSetting', '系统管理', 'system-setting', null, null, 0, 1);

-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (4001, 4, 'roleManage', '角色管理', 'role-manage', 'role/list', 'system-setting/role-information/index', 0, 1);

INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (400101, 4001, 'role:add', '添加角色', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (400102, 4001, 'role:del', '删除角色', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (400103, 4001, 'role:modify', '修改角色', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (400104, 4001, 'role:assign', '分配用户', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (400105, 4001, 'role:permissions', '设定权限', null, null, null, 0, 1);


-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (4002, 4, 'userLocked', '系统账户解锁', 'un-locked', 'userlocked/list', 'system-setting/user-locked/index', 0, 1);

-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (4003, 4, 'accountLocked', '终端账户解锁', 'un-locked', 'accountlocked/list', 'system-setting/account-locked/index', 0, 1);

-- 新增二级菜单信息
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (4004, 4, 'whiteList', '白名单设置', 'white-list', 'whitelist/list', 'system-setting/white-list/index', 0, 1);

-- 新增二级菜单信息-告警配置
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (4005, 4, 'warningType', '告警配置', 'warning-config', 'warningConfig/list', 'system-setting/warning-config/index', 0, 1);

-- 告警配置-按钮权限
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (400501, 4005, 'warningType:modify', '修改告警配置', null, null, null, 0, 1);
INSERT INTO sys_menu(id, parent_id, name, title, icon, url, component, deleted, created_by)
VALUES (400502, 4005, 'warningType:push', '告警推送配置', null, null, null, 0, 1);


insert into sys_role_menu_mapping(role_id, menu_id) values(1, 4);

insert into sys_role_menu_mapping(role_id, menu_id) values(1, 4001);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 400101);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 400102);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 400103);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 400104);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 400105);

insert into sys_role_menu_mapping(role_id, menu_id) values(1, 4002);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 4003);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 4004);

insert into sys_role_menu_mapping(role_id, menu_id) values(1, 4005);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 400501);
insert into sys_role_menu_mapping(role_id, menu_id) values(1, 400502);

-- 数据词典
insert into dictionary_type(dic_code, dic_name) values (1, '操作日志级别');

insert into dictionary_item(dic_code, item_code, item_name) values (1, 1, '低');
insert into dictionary_item(dic_code, item_code, item_name) values (1, 2, '中');
insert into dictionary_item(dic_code, item_code, item_name) values (1, 3, '高');

-- 数据词典
insert into dictionary_type(dic_code, dic_name) values (2, '接入账户类型');

insert into dictionary_item(dic_code, item_code, item_name) values (2, 1, '车载终端');
insert into dictionary_item(dic_code, item_code, item_name) values (2, 2, '采集器');

-- 数据词典
insert into dictionary_type(dic_code, dic_name) values (3, '告警类型');

insert into dictionary_item(dic_code, item_code, item_name) values (3, 1, '硬件类');
insert into dictionary_item(dic_code, item_code, item_name) values (3, 2, '通讯类');
insert into dictionary_item(dic_code, item_code, item_name) values (3, 3, '定位类');
