/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkingin.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.statistics.ss.checkingin.entity.SsCheckingIn;

/**
 * 考勤规则DAO接口
 * @author DB
 * @version 2018-09-26
 */
@MyBatisDao
public interface SsCheckingInDao extends CrudDao<SsCheckingIn> {
	
}