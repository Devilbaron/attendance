package com.statistics.ss.checkingin.entity;

import com.sun.tools.javac.util.List;

public class AttendancesData {
    public Integer RecordId;//人员id
    public String RecordDate;//2018-06-06"
    public String RecordTime;
    public String PersonName;//renmengjie"
    public Integer PersonCode;//renmengjie"

    public String DepartmentName;//宇视科技"
    public Integer DepartmentCode;//iccsid"
    public String InTime;//16:49:10"
    public String OutTime;

    public String Duration;
    public String PassTime;
    public String Inorout;
    public Integer DeviceCode;
    public String DeviceName;
    public List<PicturePath> PicturePaths;
    public Integer Seqid;

    public String PersonPicturePaths;

    public List<PicturePath> getPicturePath() {
        return PicturePaths;
    }

    public void setPicturePath(List<PicturePath> picturePaths) {
        PicturePaths = picturePaths;
    }

    public Integer getRecordId() {
        return RecordId;
    }

    public void setRecordId(Integer recordId) {
        RecordId = recordId;
    }

    public String getRecordDate() {
        return RecordDate;
    }

    public void setRecordDate(String recordDate) {
        RecordDate = recordDate;
    }

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String recordTime) {
        RecordTime = recordTime;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public Integer getPersonCode() {
        return PersonCode;
    }

    public void setPersonCode(Integer personCode) {
        PersonCode = personCode;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public Integer getDepartmentCode() {
        return DepartmentCode;
    }

    public void setDepartmentCode(Integer departmentCode) {
        DepartmentCode = departmentCode;
    }

    public String getInTime() {
        return InTime;
    }

    public void setInTime(String inTime) {
        InTime = inTime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public void setOutTime(String outTime) {
        OutTime = outTime;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getPassTime() {
        return PassTime;
    }

    public void setPassTime(String passTime) {
        PassTime = passTime;
    }

    public String getInorout() {
        return Inorout;
    }

    public void setInorout(String inorout) {
        Inorout = inorout;
    }

    public Integer getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(Integer deviceCode) {
        DeviceCode = deviceCode;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }


    public Integer getSeqid() {
        return Seqid;
    }

    public void setSeqid(Integer seqid) {
        Seqid = seqid;
    }

    public String getPersonPicturePaths() {
        return PersonPicturePaths;
    }

    public void setPersonPicturePaths(String personPicturePaths) {
        PersonPicturePaths = personPicturePaths;
    }
}
