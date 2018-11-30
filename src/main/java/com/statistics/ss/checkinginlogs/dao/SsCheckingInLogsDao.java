/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkinginlogs.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.statistics.ss.checkinginlogs.entity.SsCheckingInLogs;

/**
 * 考勤记录DAO接口
 * @author DB
 * @version 2018-11-28
 */
@MyBatisDao
public interface SsCheckingInLogsDao extends CrudDao<SsCheckingInLogs> {
	
}