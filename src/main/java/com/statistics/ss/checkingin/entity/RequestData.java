package com.statistics.ss.checkingin.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/11/23.
 */
public class RequestData {

    public List<AttendancesData> data;
    public Integer errCode;
    public String errMsg;

    public List<AttendancesData> getData() {
        return data;
    }

    public void setData(List<AttendancesData> data) {
        this.data = data;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
