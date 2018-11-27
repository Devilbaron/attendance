/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkinginlogs.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考勤统计Entity
 * @author DB
 * @version 2018-11-23
 */
public class SsCheckingInLogs extends DataEntity<SsCheckingInLogs> {
	
	private static final long serialVersionUID = 1L;
	private String recordid;		// 考勤ID
	private String recorddate;		// 考勤日期
	private String personname;		// 人员名称
	private String departmentcode;		// 部门ID
	private String intime;		// 最晚签到
	private String personcode;		// 人员ID
	private String departmentname;		// 部门名称
	private String outtime;		// 最早签退
	private String beginRecorddate;		// 开始 考勤日期
	private String endRecorddate;		// 结束 考勤日期
	
	public SsCheckingInLogs() {
		super();
	}

	public SsCheckingInLogs(String id){
		super(id);
	}

	@Length(min=1, max=64, message="考勤ID长度必须介于 1 和 64 之间")
	public String getRecordid() {
		return recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}
	
	@Length(min=0, max=32, message="考勤日期长度必须介于 0 和 32 之间")
	public String getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate;
	}
	
	@Length(min=0, max=32, message="人员名称长度必须介于 0 和 32 之间")
	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}
	
	@Length(min=0, max=64, message="部门ID长度必须介于 0 和 64 之间")
	public String getDepartmentcode() {
		return departmentcode;
	}

	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	
	@Length(min=0, max=16, message="最晚签到长度必须介于 0 和 16 之间")
	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}
	
	@Length(min=0, max=64, message="人员ID长度必须介于 0 和 64 之间")
	public String getPersoncode() {
		return personcode;
	}

	public void setPersoncode(String personcode) {
		this.personcode = personcode;
	}
	
	@Length(min=0, max=35, message="部门名称长度必须介于 0 和 35 之间")
	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	
	@Length(min=0, max=16, message="最早签退长度必须介于 0 和 16 之间")
	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	
	public String getBeginRecorddate() {
		return beginRecorddate;
	}

	public void setBeginRecorddate(String beginRecorddate) {
		this.beginRecorddate = beginRecorddate;
	}
	
	public String getEndRecorddate() {
		return endRecorddate;
	}

	public void setEndRecorddate(String endRecorddate) {
		this.endRecorddate = endRecorddate;
	}
		
}