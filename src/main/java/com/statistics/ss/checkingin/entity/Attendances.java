package com.statistics.ss.checkingin.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/11/23.
 */
public class Attendances {
    public Integer page;
    public Integer size;
    public AttendancesData[] data;
    public Integer totalcount;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public AttendancesData[] getData() {
        return data;
    }

    public void setData(AttendancesData[] data) {
        this.data = data;
    }

    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }
}
