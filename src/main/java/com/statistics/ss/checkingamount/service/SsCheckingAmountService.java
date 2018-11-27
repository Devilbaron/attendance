/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkingamount.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.statistics.ss.checkingamount.entity.SsCheckingAmount;
import com.statistics.ss.checkingamount.dao.SsCheckingAmountDao;

/**
 * 考勤统计Service
 * @author DB
 * @version 2018-11-24
 */
@Service
@Transactional(readOnly = true)
public class SsCheckingAmountService extends CrudService<SsCheckingAmountDao, SsCheckingAmount> {

	public SsCheckingAmount get(String id) {
		return super.get(id);
	}
	
	public List<SsCheckingAmount> findList(SsCheckingAmount ssCheckingAmount) {
		return super.findList(ssCheckingAmount);
	}
	
	public Page<SsCheckingAmount> findPage(Page<SsCheckingAmount> page, SsCheckingAmount ssCheckingAmount) {
		return super.findPage(page, ssCheckingAmount);
	}
	
	@Transactional(readOnly = false)
	public void save(SsCheckingAmount ssCheckingAmount) {
		super.save(ssCheckingAmount);
	}
	
	@Transactional(readOnly = false)
	public void delete(SsCheckingAmount ssCheckingAmount) {
		super.delete(ssCheckingAmount);
	}
	
}