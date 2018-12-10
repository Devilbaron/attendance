package com.statistics.ss.checkinginlogs.utils;

import com.statistics.ss.checkingamount.entity.SsCheckingAmount;
import com.statistics.ss.checkingamount.service.SsCheckingAmountService;
import com.statistics.ss.checkingin.entity.Attendances;
import com.statistics.ss.checkingin.entity.AttendancesData;
import com.statistics.ss.checkingin.entity.RequestData;
import com.statistics.ss.checkingin.service.SsCheckingInService;
import com.statistics.ss.checkinginlogs.entity.SsCheckingInLogs;
import com.statistics.ss.checkinginlogs.service.SsCheckingInLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/10/8.
 */
@Component
//@Lazy(false)
public class TimerTask {

    @Autowired
    private SsCheckingInLogsService ssCheckingInLogsService;

    @Autowired
    private SsCheckingAmountService ssCheckingAmountService;

    /*
    *  每天22:30启动任务
    * */

    RequestUrl requestUrl = new RequestUrl();

    @Scheduled(cron = "0/5 * *  * * ? ")
    public void test1() throws IOException{
        String url = "http://192.168.1.90:8088/fastgate/attendances?conditionParam={\"date\":\"2010/01/01-2020/12/32\",\"page\":\"1\",\"size\":\"4\"}";
//        List<Kqjls> kqjlsList = requestUrl.Result(url);
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);


        RequestData requestData = requestUrl.JsonResult(url);
        if (requestData.getErrCode() == null) {

            for (AttendancesData a : requestData.getData()
                    ) {
                if (StringUtils.isEmpty(ssCheckingAmountService.get(a.getPersonCode().toString()))) {
                    SsCheckingAmount ssCheckingAmount = new SsCheckingAmount();
                    ssCheckingAmount.setIsNewRecord(true);
                    ssCheckingAmount.setId(a.getPersonCode().toString());
                    if (a.getDepartmentCode() != null) {
                        ssCheckingAmount.setDepartmentcode(a.getDepartmentCode().toString());
                    }
                    if (a.getDepartmentName() != null) {
                        ssCheckingAmount.setDepartmentname(a.getDepartmentName());
                    }
                    if (a.getPersonCode() != null) {
                        ssCheckingAmount.setPersoncode(a.getPersonCode().toString());
                    }
                    if (a.getPersonName() != null) {
                        ssCheckingAmount.setPersonname(a.getPersonName().toString());
                    }
                    ssCheckingAmountService.save(ssCheckingAmount);
                }
                //增加考勤记录
                SsCheckingInLogs ssCheckingInLogs = new SsCheckingInLogs();
                ssCheckingInLogs.setRecorddate(a.getRecordDate());
                ssCheckingInLogs.setPersoncode(a.getPersonCode().toString());
//                 & ssCheckingInLogsService.findList(ssCheckingInLogs).size() > 0
                if (ssCheckingInLogsService.findList(ssCheckingInLogs).size() == 0) {
                    SsCheckingInLogs s = new SsCheckingInLogs();
                    s.setIsNewRecord(true);
                    s.setRecordid(a.getRecordId().toString());
                    s.setRecorddate(a.getRecordDate());
                    s.setPersonname(a.getPersonName());
                    s.setPersoncode(a.getPersonCode().toString());
                    s.setDepartmentname(a.getDepartmentName());
                    s.setDepartmentcode(a.getDepartmentCode().toString());
                    if (a.getRecordTime() != null) {
                        s.setIntime(a.getRecordTime());
                        s.setOuttime(a.getRecordTime());
                    }

                    ssCheckingInLogsService.save(s);
                } else {
                    ssCheckingInLogs.setRecorddate(a.getRecordDate());
                    ssCheckingInLogs.setPersoncode(a.getPersonCode().toString());
                    List<SsCheckingInLogs> sslist = ssCheckingInLogsService.findList(ssCheckingInLogs);
                    SsCheckingInLogs ss = ssCheckingInLogsService.get(sslist.get(0).getRecordid());

                    ss.setIsNewRecord(true);
                    if (a.getRecordTime() != null) {
                        if (compare_time(a.getRecordTime(), ss.getIntime()) < 0) {
                            ss.setIntime(a.getRecordTime());
                            ssCheckingInLogsService.delete(ss);
                            ssCheckingInLogsService.save(ss);
                        } else if (compare_time(a.getRecordTime(), ss.getOuttime()) > 0) {
                            ss.setOuttime(a.getRecordTime());
                            ssCheckingInLogsService.delete(ss);
                            ssCheckingInLogsService.save(ss);
                        } else {
                            System.out.println("无效");
                        }
                    }

                }

            }

        }

    }
    public int compare_time(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

}