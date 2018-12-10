package com.statistics.ss.checkinglogsdetail.web;


import com.statistics.ss.checkingamount.entity.SsCheckingAmount;
import com.statistics.ss.checkingamount.service.SsCheckingAmountService;
import com.statistics.ss.checkingin.entity.SsCheckingIn;
import com.statistics.ss.checkingin.service.SsCheckingInService;
import com.statistics.ss.checkinginlogs.entity.SsCheckingInLogs;
import com.statistics.ss.checkinginlogs.service.SsCheckingInLogsService;
import com.sun.tools.example.debug.expr.ParseException;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 考勤明细Controller
 *
 * @author DB
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/checkinginlogsdetail/ssCheckingInLogs")
public class SsCheckingInLogsDetailController extends BaseController {

    @Autowired
    private SsCheckingInLogsService ssCheckingInLogsService;

    @Autowired
    private SsCheckingInService ssCheckingInService;

    @Autowired
    private SsCheckingAmountService ssCheckingAmountService;

    @ModelAttribute
    public SsCheckingInLogs get(@RequestParam(required = false) String recordid) {
        SsCheckingInLogs entity = null;
        if (StringUtils.isNotBlank(recordid)) {
            entity = ssCheckingInLogsService.get(recordid);
        }
        if (entity == null) {
            entity = new SsCheckingInLogs();
        }
        return entity;
    }

    @RequiresPermissions("checkinginlogs:ssCheckingInLogs:view")
    @RequestMapping(value = {"list", ""})
    public String list(SsCheckingInLogs ssCheckingInLogs, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SsCheckingInLogs> page = ssCheckingInLogsService.findPage(new Page<SsCheckingInLogs>(request, response), ssCheckingInLogs);
        List<String> nullList = getBetweenDate(ssCheckingInLogs.getBeginRecorddate(), ssCheckingInLogs.getEndRecorddate());
        int shins = ssCheckingInService.findList(new SsCheckingIn()).size();
        //去掉存在的记录
        if (page.getList().size() > 0) {
            for (SsCheckingInLogs s : page.getList()
            ) {
                nullList.remove(s.getRecorddate());
            }
        }
        SsCheckingAmount sa = new SsCheckingAmount();
        if (ssCheckingInLogs.getPersoncode() != null){
            sa.setId(ssCheckingInLogs.getPersoncode());
            sa = ssCheckingAmountService.get(sa);
            //不存在规则手动创建
            for (int i = 0; i < nullList.size(); i++) {

                SsCheckingInLogs ss = new SsCheckingInLogs();


                ss.setRecorddate(nullList.get(i));
                ss.setDepartmentname(sa.getDepartmentname());
                ss.setPersonname(sa.getPersonname());
                page.getList().add(ss);
            }
        }

        for (int i = 0; i < page.getList().size(); i++) {

            if (shins > 0) {
                SsCheckingIn ssCheckingIn = ssCheckingInService.get(page.getList().get(i).getRecorddate());
                if (ssCheckingIn != null) {
                    //节假日
                    if (ssCheckingIn.getHoliday() > 0 | ssCheckingIn.getWeekend() > 0) {
                        if (ssCheckingIn.getHoliday() > 0){
                            page.getList().get(i).setAttendanceconc("节假");
                        }else{
                            page.getList().get(i).setAttendanceconc("周末");
                        }
                    } else {
                        //考勤时间非空
                        if (page.getList().get(i).getIntime() != null && !page.getList().get(i).getIntime().equals("")) {
                            //判断迟到
                            if (compare_time(ssCheckingIn.getAm(), page.getList().get(i).getIntime()) >= 0) {
                                page.getList().get(i).setIntimermk("正常");
                            } else {
                                page.getList().get(i).setIntimermk("迟到");
                            }
                        } else {
                            page.getList().get(i).setIntimermk("未打卡");
                        }
                        if (page.getList().get(i).getOuttime() != null && !page.getList().get(i).getOuttime().equals("")) {
                            if (compare_time(ssCheckingIn.getPm(), page.getList().get(i).getOuttime()) <= 0) {
                                page.getList().get(i).setOuttimermk("正常");
                            } else {
                                page.getList().get(i).setOuttimermk("早退");
                            }
                        } else {
                            page.getList().get(i).setOuttimermk("未打卡");
                        }
                        if (page.getList().get(i).getIntimermk().equals("未打卡") || page.getList().get(i).getOuttimermk().equals("未打卡")) {
                            page.getList().get(i).setAttendanceconc("缺勤");
                        }else if (page.getList().get(i).getIntimermk().equals("迟到") || page.getList().get(i).getOuttimermk().equals("早退")){
                            if (page.getList().get(i).getIntimermk().equals("迟到")){
                                if (page.getList().get(i).getOuttimermk().equals("早退")){
                                    page.getList().get(i).setAttendanceconc("迟到早退");
                                }else{
                                    page.getList().get(i).setAttendanceconc("迟到");
                                }
                            }else{
                                page.getList().get(i).setAttendanceconc("早退");
                            }
                        } else {
                            page.getList().get(i).setAttendanceconc("正常");
                        }
                    }
                }

            }


        }


        model.addAttribute("ssCheckingInLogs", ssCheckingInLogs);
        model.addAttribute("page", page);
        return "ss/checkinglogsdetail/ssCheckingInLogsList";
    }

    @RequiresPermissions("checkinginlogs:ssCheckingInLogs:view")
    @RequestMapping(value = "form")
    public String form(SsCheckingInLogs ssCheckingInLogs, Model model) {
        model.addAttribute("ssCheckingInLogs", ssCheckingInLogs);
        return "ss/checkinglogsdetail/ssCheckingInLogsForm";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(SsCheckingInLogs ssCheckingInLogs, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            Page<SsCheckingInLogs> page = ssCheckingInLogsService.findPage(new Page<SsCheckingInLogs>(request, response), ssCheckingInLogs);
            List<String> nullList = getBetweenDate(ssCheckingInLogs.getBeginRecorddate(), ssCheckingInLogs.getEndRecorddate());
            int shins = ssCheckingInService.findList(new SsCheckingIn()).size();
            //去掉存在的记录
            if (page.getList().size() > 0) {
                for (SsCheckingInLogs s : page.getList()
                ) {
                    nullList.remove(s.getRecorddate());
                }
            }
            SsCheckingAmount sa = new SsCheckingAmount();
            sa.setId(ssCheckingInLogs.getPersoncode());
            sa = ssCheckingAmountService.get(sa);
            //不存在规则手动创建
            for (int i = 0; i < nullList.size(); i++) {
                SsCheckingInLogs ss = new SsCheckingInLogs();
                ss.setRecorddate(nullList.get(i));
                ss.setDepartmentname(sa.getDepartmentname());
                ss.setPersonname(sa.getPersonname());
                page.getList().add(ss);

            }

            for (int i = 0; i < page.getList().size(); i++) {

                if (shins > 0) {
                    SsCheckingIn ssCheckingIn = ssCheckingInService.get(page.getList().get(i).getRecorddate());
                    if (ssCheckingIn != null) {
                        //节假日
                        if (ssCheckingIn.getHoliday() > 0 | ssCheckingIn.getWeekend() > 0) {
                            if (ssCheckingIn.getHoliday() > 0){
                                page.getList().get(i).setAttendanceconc("节假");
                            }else{
                                page.getList().get(i).setAttendanceconc("周末");
                            }
                        } else {
                            //考勤时间非空
                            if (page.getList().get(i).getIntime() != null && !page.getList().get(i).getIntime().equals("")) {
                                //判断迟到
                                if (compare_time(ssCheckingIn.getAm(), page.getList().get(i).getIntime()) >= 0) {
                                    page.getList().get(i).setIntimermk("正常");
                                } else {
                                    page.getList().get(i).setIntimermk("迟到");
                                }
                            } else {
                                page.getList().get(i).setIntimermk("未打卡");
                            }
                            if (page.getList().get(i).getOuttime() != null && !page.getList().get(i).getOuttime().equals("")) {
                                if (compare_time(ssCheckingIn.getPm(), page.getList().get(i).getOuttime()) <= 0) {
                                    page.getList().get(i).setOuttimermk("正常");
                                } else {
                                    page.getList().get(i).setOuttimermk("早退");
                                }
                            } else {
                                page.getList().get(i).setOuttimermk("未打卡");
                            }
                            if (page.getList().get(i).getIntimermk().equals("未打卡") || page.getList().get(i).getOuttimermk().equals("未打卡")) {
                                page.getList().get(i).setAttendanceconc("缺勤");
                            }else if (page.getList().get(i).getIntimermk().equals("迟到") || page.getList().get(i).getOuttimermk().equals("早退")){
                                if (page.getList().get(i).getIntimermk().equals("迟到")){
                                    if (page.getList().get(i).getOuttimermk().equals("早退")){
                                        page.getList().get(i).setAttendanceconc("迟到早退");
                                    }else{
                                        page.getList().get(i).setAttendanceconc("迟到");
                                    }
                                }else{
                                    page.getList().get(i).setAttendanceconc("早退");
                                }
                            } else {
                                page.getList().get(i).setAttendanceconc("正常");
                            }
                        }
                    }

                }


            }
            String fileName = "统计数据" + ssCheckingInLogs.getBeginRecorddate() + "~" + ssCheckingInLogs.getEndRecorddate() + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            new ExportExcel("统计数据" + ssCheckingInLogs.getBeginRecorddate() + "~" + ssCheckingInLogs.getEndRecorddate(), SsCheckingInLogs.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/checkinginlogsdetail/ssCheckingInLogs/list?repage";
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


    /**
     * 获取两个日期字符串之间的日期集合
     *
     * @param startTime:String
     * @param endTime:String
     * @return list:yyyy-MM-dd
     */
    public static List<String> getBetweenDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate = calendar.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
