/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkinginlogs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.statistics.ss.checkinginlogs.entity.SsCheckingInLogs;
import com.statistics.ss.checkinginlogs.dao.SsCheckingInLogsDao;

/**
 * 考勤统计Service
 * @author DB
 * @version 2018-11-23
 */
@Service
@Transactional(readOnly = true)
public class SsCheckingInLogsService extends CrudService<SsCheckingInLogsDao, SsCheckingInLogs> {

	public SsCheckingInLogs get(String id) {
		return super.get(id);
	}

	public SsCheckingInLogs getList(String id) {
		return super.get(id);
	}
	
	public List<SsCheckingInLogs> findList(SsCheckingInLogs ssCheckingInLogs) {
		return super.findList(ssCheckingInLogs);
	}
	
	public Page<SsCheckingInLogs> findPage(Page<SsCheckingInLogs> page, SsCheckingInLogs ssCheckingInLogs) {
		return super.findPage(page, ssCheckingInLogs);
	}
	
	@Transactional(readOnly = false)
	public void save(SsCheckingInLogs ssCheckingInLogs) {
		super.save(ssCheckingInLogs);
	}
	
	@Transactional(readOnly = false)
	public void delete(SsCheckingInLogs ssCheckingInLogs) {
		super.delete(ssCheckingInLogs);
	}
	
}