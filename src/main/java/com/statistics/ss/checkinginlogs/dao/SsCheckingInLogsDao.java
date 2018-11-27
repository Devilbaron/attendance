/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkinginlogs.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.statistics.ss.checkinginlogs.entity.SsCheckingInLogs;

/**
 * 考勤统计DAO接口
 * @author DB
 * @version 2018-11-23
 */
@MyBatisDao
public interface SsCheckingInLogsDao extends CrudDao<SsCheckingInLogs> {

	
}