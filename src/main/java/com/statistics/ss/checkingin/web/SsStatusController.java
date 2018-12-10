package com.statistics.ss.checkingin.web;
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */

import com.statistics.ss.checkingamount.entity.SsCheckingAmount;
import com.statistics.ss.checkingamount.service.SsCheckingAmountService;
import com.statistics.ss.checkingin.entity.AttendancesData;
import com.statistics.ss.checkingin.entity.RequestData;
import com.statistics.ss.checkingin.entity.SsCheckingIn;
import com.statistics.ss.checkingin.service.SsCheckingInService;
import com.statistics.ss.checkinginlogs.entity.SsCheckingInLogs;
import com.statistics.ss.checkinginlogs.service.SsCheckingInLogsService;
import com.statistics.ss.checkinginlogs.utils.RequestUrl;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 考勤规则Controller
 * @author DB
 * @version 2018-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/checkingin/ssCheckingIn")
public class SsStatusController extends BaseController {

    @Autowired
    private SsCheckingInService ssCheckingInService;

    @Autowired
    private SsCheckingAmountService ssCheckingAmountService;

    @Autowired
    private SsCheckingInLogsService ssCheckingInLogsService;

    @ModelAttribute
    public SsCheckingIn get(@RequestParam(required=false) String id) {
        SsCheckingIn entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = ssCheckingInService.get(id);
        }
        if (entity == null){
            entity = new SsCheckingIn();
        }
        return entity;
    }

    @RequiresPermissions("checkingin:ssCheckingIn:view")
    @RequestMapping(value = "calendar")
    public String calendar(SsCheckingIn ssCheckingIn, Model model) {
        model.addAttribute("ssCheckingIn", ssCheckingIn);
        return "ss/checkingin/ssCheckingInIndex";
    }

    @RequiresPermissions("checkingin:ssCheckingIn:view")
    @RequestMapping(value = "holiday")
    public String holiday(SsCheckingIn ssCheckingIn,HttpServletRequest request, HttpServletResponse response, Model model) {
        List<SsCheckingIn> list = ssCheckingInService.findList(ssCheckingIn);

        model.addAttribute("list", list);
        return "ss/checkingin/ssCheckingInIndex";
    }


    @RequiresPermissions("checkingin:ssCheckingIn:edit")
    @RequestMapping(value = "updata")
    public String updata(SsCheckingIn ssCheckingIn, Model model, RedirectAttributes redirectAttributes) throws Exception{
        if (!beanValidator(model, ssCheckingIn)){
//            return form(ssCheckingIn, model);
        }
        RequestUrl requestUrl = new RequestUrl();
        String url ="";
        if (Global.getConfig("updataurl") == null){
            url = "http://130.1.198.12:8088/fastgate/attendances?conditionParam={\"date\":\"2010/01/01-2020/12/31\",\"page\":\"1\",\"size\":\"4\"}";
        }else{
            url = Global.getConfig("updataurl");
        };
            //        List<Kqjls> kqjlsList = requestUrl.Result(url);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateNowStr = sdf.format(d);


        RequestData requestData = requestUrl.JsonResult(url);
        if (requestData.getErrCode() == null) {

            for (AttendancesData a : requestData.getData()
            ) {
                SsCheckingAmount ssCheckingAmount1 = new SsCheckingAmount();
                ssCheckingAmount1.setPersoncode(a.getPersonCode().toString());
                List<SsCheckingAmount> ssalist = ssCheckingAmountService.findList(ssCheckingAmount1);
                if (ssalist.size() == 0) {
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
                    if (a.getDepartmentCode() != null){
                        s.setDepartmentcode(a.getDepartmentCode().toString());
                    }
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

        addMessage(redirectAttributes, "更新考勤记录成功");
        return "redirect:"+ Global.getAdminPath()+"/checkinginlogs/ssCheckingInLogs/?repage";
    }
    //生成考勤规则
    @RequiresPermissions("checkingin:ssCheckingIn:edit")
    @RequestMapping(value = "cLogTables")
    public String cLogTables(SsCheckingIn ssCheckingIn, Model model, RedirectAttributes redirectAttributes,String am,String pm,String yyyy) throws Exception{
        if (!beanValidator(model, ssCheckingIn)){
//            return form(ssCheckingIn, model);
        }

        if (!yyyy.equals("") & !yyyy.equals(null)){
            SsCheckingIn ssCheckingIn1 = ssCheckingInService.get(yyyy+"-01-01");
                if(ssCheckingInService.get(yyyy+"-01-01") != null){
                    if (ssCheckingIn1 != null & Integer.valueOf(ssCheckingIn1.getDelFlag()) != 1){
                        addMessage(redirectAttributes, "已存在不能添加");
                        return "redirect:"+Global.getAdminPath()+"/checkingin/ssCheckingIn/?repage";
                    }
                }
            }


        //创建全年考勤规则表
        List<String> list = getWorkDays(Integer.valueOf(yyyy));

        //添加考勤数据
        SsCheckingIn ssCheckingIn1 = new SsCheckingIn();
        for (int i = 0 ; i < list.size(); i++){
            String[] StrArray = list.get(i).split(",");
            ssCheckingIn1.setIsNewRecord(true);

            ssCheckingIn1.setDateline(DateUtils.parseDate(StrArray[0], "yyyy-MM-dd"));
            ssCheckingIn1.setId(StrArray[0]);
//            ssCheckingIn1.setDateline(DateUtils.parseDate(StrArray[0], "yyyy-MM-dd"));
            if (!am.isEmpty()&!pm.isEmpty()){
                ssCheckingIn1.setAm(am);
                ssCheckingIn1.setPm(pm);
            }else {
                ssCheckingIn1.setAm(ssCheckingIn.getAm());
                ssCheckingIn1.setPm(ssCheckingIn.getPm());
            }

            if (StrArray[1].equals("1")){
                ssCheckingIn1.setWeekend(Long.valueOf(1));
            }else {
                ssCheckingIn1.setWeekend(Long.valueOf(0));
            }
            if (StrArray[1].equals("2")){
                ssCheckingIn1.setHoliday(Long.valueOf(1));
            }else {
                ssCheckingIn1.setHoliday(Long.valueOf(0));
            }
            ssCheckingInService.save(ssCheckingIn1);

        }

//        ssCheckingInService.save(ssCheckingIn);
        addMessage(redirectAttributes, "保存考勤规则成功");
        return "redirect:"+Global.getAdminPath()+"/checkingin/ssCheckingIn/?repage";
    }

    @RequiresPermissions("checkingin:ssCheckingIn:view")
    @RequestMapping(value = "yDel")
    public String yDel(SsCheckingIn ssCheckingIn, Model model, Integer yyyy)throws Exception {
        List<String> list = getYearDay(Integer.valueOf(yyyy));
        for (String day:list
             ) {
            SsCheckingIn ssCheckingIn1 = new SsCheckingIn();
            ssCheckingIn1.setId(day);
            ssCheckingInService.delete(ssCheckingIn1);
        }

        model.addAttribute("ssCheckingIn", ssCheckingIn);
        return "ss/checkingin/ssCheckingInIndex";
    }

    public List<String> getWorkDays(int i) throws Exception {
        List<String> list = new ArrayList<String>();
        Calendar a = Calendar.getInstance();
        String httpUrl = "http://api.goseek.cn/Tools/holiday";
//        String t = a.get(Calendar.YEAR + i) + "0101";// 开始的日期
        String t = i + "0101";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();// 开始日期，并要累积加 一
        Calendar calendar2 = Calendar.getInstance();// 结束的日期
        Date time = sdf.parse(t);
        calendar.setTime(time);
        calendar2.setTime(time);
        calendar2.add(Calendar.YEAR, 1);// 加上一年的后的日期
        Date first = calendar.getTime();
        Date next = calendar2.getTime();
        while (first.getTime() < next.getTime()) { // 判断是否是节假日
            String fdate = "date=" + sdf.format(first.getTime());
            String jsonResult = request(httpUrl, fdate);
            // 判断是否是节假日
//            if ("0".equals(jsonResult.trim())) {
//                list.add(sdfDateFormat.format(first.getTime()));
//            }
            if ("10000".equals(jsonResult.substring(8,13))) {
//                if ("0".equals(jsonResult.substring(21,22))) {
                    list.add(sdfDateFormat.format(first.getTime()) + ","+ jsonResult.substring(21,22));
//                }
            }
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
            first = calendar.getTime(); // 这个时间就是日期往后推一天的结果
            calendar.getTime();
        }
        return list;
    }

    public List<String> getYearDay(int i) throws Exception {
        List<String> list = new ArrayList<String>();
        Calendar a = Calendar.getInstance();
        String t = i + "0101";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();// 开始日期，并要累积加 一
        Calendar calendar2 = Calendar.getInstance();// 结束的日期
        Date time = sdf.parse(t);
        calendar.setTime(time);
        calendar2.setTime(time);
        calendar2.add(Calendar.YEAR, 1);// 加上一年的后的日期
        Date first = calendar.getTime();
        Date next = calendar2.getTime();
        while (first.getTime() < next.getTime()) { // 判断是否是节假日
            String fdate = "date=" + sdf.format(first.getTime());
            // 判断是否是节假日
//            if ("0".equals(jsonResult.trim())) {
                list.add(sdfDateFormat.format(first.getTime()));
//            }
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
            first = calendar.getTime(); // 这个时间就是日期往后推一天的结果
            calendar.getTime();
        }
        return list;
    }

        /**
         * @param httpUrl :请求接口
         * @param httpArg :参数
         * @return 返回结果x`
         */
        public String request(String httpUrl, String httpArg) {
            BufferedReader reader = null;
            String result = null;
            StringBuffer sbf = new StringBuffer();
            httpUrl = httpUrl + "?" + httpArg;
            try {
                URL url = new URL(httpUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                // 填入apikey到HTTP header
//                connection.setRequestProperty("apikey", "abfa5282a89706affd2e4ad6651c9648");
                connection.connect();
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sbf.append(strRead);
                    sbf.append("\r\n");
                }
                reader.close();
                result = sbf.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

    public int compare_time(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
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