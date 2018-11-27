/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkingin.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考勤规则Entity
 * @author DB
 * @version 2018-09-26
 */
public class SsCheckingIn extends DataEntity<SsCheckingIn> {

	private static final long serialVersionUID = 1L;
	private Date dateline;		// 日期
	private Long weekend;		// 是否周末
	private Long holiday;		// 是否节假日
	private String am;		// 上午时间
	private String pm;		// 下午时间

	public SsCheckingIn() {
		super();
	}

	public SsCheckingIn(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDateline() {
		return dateline;
	}

	public void setDateline(Date dateline) {
		this.dateline = dateline;
	}
	
	public Long getWeekend() {
		return weekend;
	}

	public void setWeekend(Long weekend) {
		this.weekend = weekend;
	}
	
	public Long getHoliday() {
		return holiday;
	}

	public void setHoliday(Long holiday) {
		this.holiday = holiday;
	}
	
	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}
	
	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	@Override
	public boolean getIsNewRecord() {
		return super.getIsNewRecord();
	}

	@Override
	public void setIsNewRecord(boolean isNewRecord) {
		super.setIsNewRecord(isNewRecord);
	}
}