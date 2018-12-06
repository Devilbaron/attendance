/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkingamount.entity;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考勤统计Entity
 * @author DB
 * @version 2018-11-24
 */
public class SsCheckingAmount extends DataEntity<SsCheckingAmount> {
	
	private static final long serialVersionUID = 1L;
	private String departmentname;		// 部门名称
	private String departmentcode;		// 部门ID
	private String personname;		// 人员名称
	private String personcode;		// 人员ID
	private String beginCreateDate;		// 开始时间
	private String endCreateDate;		// 结束时间

	private String intimeamount;		// 迟到结论
	private String outtimeamount;		// 早退结论
	private String checkamount;		// 全天结论
	private String amount;		// 缺勤结论

	public SsCheckingAmount() {
		super();
	}

	public SsCheckingAmount(String id){
		super(id);
	}

	@Length(min=0, max=35, message="部门名称长度必须介于 0 和 35 之间")
	@ExcelField(title = "部门名称", type = 1, align = 2, sort = 1)
	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	
	@Length(min=0, max=64, message="部门ID长度必须介于 0 和 64 之间")
	public String getDepartmentcode() {
		return departmentcode;
	}

	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	
	@Length(min=0, max=32, message="人员名称长度必须介于 0 和 32 之间")
	@ExcelField(title = "姓名", type = 1, align = 2, sort = 2)
	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}
	
	@Length(min=0, max=64, message="人员ID长度必须介于 0 和 64 之间")
	public String getPersoncode() {
		return personcode;
	}

	public void setPersoncode(String personcode) {
		this.personcode = personcode;
	}

	public String getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(String beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	@Length(min=0, max=16, message="迟到结论长度必须介于 0 和 16 之间")
	@ExcelField(title = "迟到", type = 1, align = 2, sort = 3)
	public String getIntimeamount() {
		return intimeamount;
	}

	public void setIntimeamount(String intimeamount) {
		this.intimeamount = intimeamount;
	}
	
	@Length(min=0, max=16, message="早退结论长度必须介于 0 和 16 之间")
	@ExcelField(title = "早退", type = 1, align = 2, sort = 4)
	public String getOuttimeamount() {
		return outtimeamount;
	}

	public void setOuttimeamount(String outtimeamount) {
		this.outtimeamount = outtimeamount;
	}
	
	@Length(min=0, max=16, message="全天结论长度必须介于 0 和 16 之间")
	@ExcelField(title = "缺勤", type = 1, align = 2, sort = 5)
	public String getCheckamount() {
		return checkamount;
	}

	public void setCheckamount(String checkamount) {
		this.checkamount = checkamount;
	}
	
	@Length(min=0, max=16, message="缺勤结论长度必须介于 0 和 16 之间")
	@ExcelField(title = "迟到早退", type = 1, align = 2, sort = 6)
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}