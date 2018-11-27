package com.statistics.ss.checkinginlogs.entity;

/**
 * Created by Administrator on 2018/10/7.
 */
public class Kqjls {
    //考勤记录唯一标识 -->
//    @XmlTransient
    public String id;
    //单位名称 -->
//    @XmlTransient
    public String companyName;
    //单位ID -->
//    @XmlTransient
    public String companyId;
    //部门名称 -->
//    @XmlTransient
    public String departmentName;
    //部门ID -->
//    @XmlTransient
    public String departmentID;
    //人员姓名 -->
//    @XmlTransient
    public String personName;
    //人员ID -->
//    @XmlTransient
    public String personId;
    //核算日期 -->
//    @XmlTransient
    public String dateOfAccounting;
    //上班打卡时间 hh:mm:ss -->
//    @XmlTransient
    public String workingSignIn;
    //上班描述 -->
//    @XmlTransient
    public String workingDescribe;
    //下班打卡时间 hh:mm:ss -->
//    @XmlTransient
    public String offDutySignIn;
    //下班描述 -->
//    @XmlTransient
    public String offDutyDescribe;
    //迟到时间 -->
//    @XmlTransient
    public String lateTime;
    //早退时间 -->
//    @XmlTransient
    public String leaveEarlyTime;
    //出勤结论 -->
//    @XmlTransient
    public String attendanceConclusion;

    public String getAttendanceConclusion() {
        return attendanceConclusion;
    }

    public void setAttendanceConclusion(String attendanceConclusion) {
        this.attendanceConclusion = attendanceConclusion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDateOfAccounting() {
        return dateOfAccounting;
    }

    public void setDateOfAccounting(String dateOfAccounting) {
        this.dateOfAccounting = dateOfAccounting;
    }

    public String getWorkingSignIn() {
        return workingSignIn;
    }

    public void setWorkingSignIn(String workingSignIn) {
        this.workingSignIn = workingSignIn;
    }

    public String getWorkingDescribe() {
        return workingDescribe;
    }

    public void setWorkingDescribe(String workingDescribe) {
        this.workingDescribe = workingDescribe;
    }

    public String getOffDutySignIn() {
        return offDutySignIn;
    }

    public void setOffDutySignIn(String offDutySignIn) {
        this.offDutySignIn = offDutySignIn;
    }

    public String getOffDutyDescribe() {
        return offDutyDescribe;
    }

    public void setOffDutyDescribe(String offDutyDescribe) {
        this.offDutyDescribe = offDutyDescribe;
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

    public Kqjls() {
    }

    public Kqjls(String id, String companyName, String companyId, String departmentName,
                 String departmentID, String personName, String personId,
                 String dateOfAccounting, String workingSignIn, String workingDescribe,
                 String offDutySignIn, String offDutyDescribe, String lateTime,
                 String leaveEarlyTime, String attendanceConclusion) {
        this.id = id;
        this.companyName = companyName;
        this.companyId = companyId;
        this.departmentName = departmentName;
        this.departmentID = departmentID;
        this.personName = personName;
        this.personId = personId;
        this.dateOfAccounting = dateOfAccounting;
        this.workingSignIn = workingSignIn;
        this.workingDescribe = workingDescribe;
        this.offDutySignIn = offDutySignIn;
        this.offDutyDescribe = offDutyDescribe;
        this.lateTime = lateTime;
        this.leaveEarlyTime = leaveEarlyTime;
        this.attendanceConclusion = attendanceConclusion;
    }
}
