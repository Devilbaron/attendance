/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkingamount.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.statistics.ss.checkingamount.entity.SsCheckingAmount;

/**
 * 考勤统计DAO接口
 * @author DB
 * @version 2018-11-24
 */
@MyBatisDao
public interface SsCheckingAmountDao extends CrudDao<SsCheckingAmount> {
	
}