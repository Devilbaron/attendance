/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkinginlogs.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考勤记录Entity
 * @author DB
 * @version 2018-11-28
 */
public class SsCheckingInLogs extends DataEntity<SsCheckingInLogs> {
	
	private static final long serialVersionUID = 1L;
	private String recordid;		// 考勤ID
	private String recorddate;		// 考勤日期
	private String departmentcode;		// 部门ID
	private String departmentname;		// 部门名称
	private String personcode;		// 人员ID
	private String personname;		// 人员名称
	private String intime;		// 上班时间
	private String intimermk;	//下班描述
	private String outtime;		// 下班时间
	private String outtimermk;	//下班描述
	private String lateTime;	//迟到时间
	private String leaveEarlyTime;	//早退时间
	private String attendanceconc;	//出勤结论
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
	@ExcelField(title = "核算日期", type = 1, align = 2, sort = 3)
	public String getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate;
	}
	
	@Length(min=1, max=64, message="部门ID长度必须介于 1 和 64 之间")
	public String getDepartmentcode() {
		return departmentcode;
	}

	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	
	@Length(min=0, max=35, message="部门名称长度必须介于 0 和 35 之间")
	@ExcelField(title = "部门", type = 1, align = 2, sort = 1)
	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	
	@Length(min=0, max=64, message="人员ID长度必须介于 0 和 64 之间")
	public String getPersoncode() {
		return personcode;
	}

	public void setPersoncode(String personcode) {
		this.personcode = personcode;
	}
	
	@Length(min=0, max=32, message="人员名称长度必须介于 0 和 32 之间")
	@ExcelField(title = "姓名", type = 1, align = 2, sort = 2)
	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}
	
	@Length(min=0, max=16, message="签到时间长度必须介于 0 和 16 之间")
	@ExcelField(title = "上班打卡", type = 1, align = 2, sort = 4)
	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}
	
	@Length(min=0, max=16, message="签退时间长度必须介于 0 和 16 之间")
	@ExcelField(title = "下班打卡", type = 1, align = 2, sort = 6)
	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}

	@ExcelField(title = "上班打卡描述", type = 1, align = 2, sort = 5)
	public String getIntimermk() {
		return intimermk;
	}

	public void setIntimermk(String intimermk) {
		this.intimermk = intimermk;
	}
	@ExcelField(title = "下班打卡描述", type = 1, align = 2, sort = 7)
	public String getOuttimermk() {
		return outtimermk;
	}

	public void setOuttimermk(String outtimermk) {
		this.outtimermk = outtimermk;
	}

	public String getLateTime() {
		return lateTime;
	}

	public void setLateTime(String lateTime) {
		this.lateTime = lateTime;
	}

	public String getLeaveEarlyTime() {
		return leaveEarlyTime;
	}

	public void setLeaveEarlyTime(String leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	@ExcelField(title = "出勤结论", type = 1, align = 2, sort = 8)
	public String getAttendanceconc() {
		return attendanceconc;
	}

	public void setAttendanceconc(String attendanceconc) {
		this.attendanceconc = attendanceconc;
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