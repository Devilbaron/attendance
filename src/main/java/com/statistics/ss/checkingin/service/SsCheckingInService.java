/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkingin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.statistics.ss.checkingin.entity.SsCheckingIn;
import com.statistics.ss.checkingin.dao.SsCheckingInDao;

/**
 * 考勤规则Service
 * @author DB
 * @version 2018-09-26
 */
@Service
@Transactional(readOnly = true)
public class SsCheckingInService extends CrudService<SsCheckingInDao, SsCheckingIn> {

	public SsCheckingIn get(String id) {
		return super.get(id);
	}
	
	public List<SsCheckingIn> findList(SsCheckingIn ssCheckingIn) {
		return super.findList(ssCheckingIn);
	}
	
	public Page<SsCheckingIn> findPage(Page<SsCheckingIn> page, SsCheckingIn ssCheckingIn) {
		return super.findPage(page, ssCheckingIn);
	}
	
	@Transactional(readOnly = false)
	public void save(SsCheckingIn ssCheckingIn) {
		super.save(ssCheckingIn);
	}
	
	@Transactional(readOnly = false)
	public void delete(SsCheckingIn ssCheckingIn) {
		super.delete(ssCheckingIn);
	}
	
}