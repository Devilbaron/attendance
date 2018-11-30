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

import java.text.SimpleDateFormat;
import java.util.Date;

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
    public void test1() {
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
                    ssCheckingAmount.setDepartmentcode(a.getDepartmentCode().toString());
                    ssCheckingAmount.setDepartmentname(a.getDepartmentName());
                    ssCheckingAmount.setPersoncode(a.getPersonCode().toString());
                    ssCheckingAmount.setPersonname(a.getPersonName().toString());
                    ssCheckingAmountService.save(ssCheckingAmount);
                }
                //增加考勤记录
                if (StringUtils.isEmpty(ssCheckingInLogsService.get(a.getRecordId().toString()))) {
                    SsCheckingInLogs s = new SsCheckingInLogs();
                    s.setIsNewRecord(true);
                    s.setRecordid(a.getRecordId().toString());
                    s.setRecorddate(a.getRecordDate());
                    s.setPersonname(a.getPersonName());
                    s.setPersoncode(a.getPersonCode().toString());
                    s.setDepartmentname(a.getDepartmentName());
                    s.setDepartmentcode(a.getDepartmentCode().toString());
                    s.setIntime(a.getInTime());
                    s.setOuttime(a.getOutTime());

                    ssCheckingInLogsService.save(s);
                }
                ;

            }

        }
    }
}