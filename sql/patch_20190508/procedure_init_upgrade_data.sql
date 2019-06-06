-- ----------------------------------------
-- -----------------WARNING----------------
-- 该脚本仅初始化使用，当存在数据时务必三思
-- -----------------WARNING----------------
-- ----------------------------------------
use basestation;
SET SQL_SAFE_UPDATES = 0;

delete from upgrade_file;
delete from upgrade_version;
delete from upgrade_result;

DELIMITER $$
DROP PROCEDURE IF EXISTS `init_upgrade_data` $$
CREATE PROCEDURE `init_upgrade_data`()
BEGIN
	DECLARE lastId int default -1;
    DECLARE userId int default -1;

    -- 查询用户id
    select min(id) from sys_user into userId;
    -- 插入升级文件初始数据
	insert into upgrade_file(file_name, file_path, file_type, md5, file_size, state, deleted, created_by)
	values ('TCH05GPS_DS_VC.bin', 'qQx2WFReYNzkdQSi685YM6PLvVTUqtv+tim+dPwyPEUXoFjRoq8kS6GsMUfF5Gw7cDFY5cWqSIt5bN/F5Huqqw==', '12000107', 'c69f823725234bc2f7dac5f2f32c2573', 158744, 9, 0, userId);
    -- 获取自增主键
	select max(id) from upgrade_file into lastId;
    -- 插入升级版本初始数据
	insert into upgrade_version(version_num, file_id, send_user_id, state, deleted, created_by)
	values ('V1.00', lastId, userId, 9, 0, userId);
END $$
DELIMITER ;

call init_upgrade_data();
