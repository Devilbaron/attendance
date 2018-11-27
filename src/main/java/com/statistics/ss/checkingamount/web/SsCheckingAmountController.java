/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkingamount.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.statistics.ss.checkingin.entity.SsCheckingIn;
import com.statistics.ss.checkingin.service.SsCheckingInService;
import com.statistics.ss.checkinginlogs.entity.SsCheckingInLogs;
import com.statistics.ss.checkinginlogs.service.SsCheckingInLogsService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.statistics.ss.checkingamount.entity.SsCheckingAmount;
import com.statistics.ss.checkingamount.service.SsCheckingAmountService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 考勤统计Controller
 *
 * @author DB
 * @version 2018-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/checkingamount/ssCheckingAmount")
public class SsCheckingAmountController extends BaseController {

    @Autowired
    private SsCheckingAmountService ssCheckingAmountService;

    @Autowired
    private SsCheckingInLogsService ssCheckingInLogsService;

    @Autowired
    private SsCheckingInService ssCheckingInService;

    @ModelAttribute
    public SsCheckingAmount get(@RequestParam(required = false) String id) {
        SsCheckingAmount entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = ssCheckingAmountService.get(id);
        }
        if (entity == null) {
            entity = new SsCheckingAmount();
        }
        return entity;
    }

    @RequiresPermissions("checkingamount:ssCheckingAmount:view")
    @RequestMapping(value = {"list", ""})
    public String list(SsCheckingAmount ssCheckingAmount, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SsCheckingAmount> page = ssCheckingAmountService.findPage(new Page<SsCheckingAmount>(request, response), ssCheckingAmount);

        //获取一段时间的考勤规则
        List<SsCheckingIn> inList = new ArrayList<SsCheckingIn>();

        //迟到天数
        int cd = 0;
        //早退天数
        int zt = 0;
        //迟到早退天数
        int cz = 0;
        //缺勤天数
        int qq = 0;


        for (SsCheckingIn in : ssCheckingInService.findList(new SsCheckingIn())
                ) {
            if (ssCheckingAmount.getBeginCreateDate() != null & ssCheckingAmount.getBeginCreateDate() != null) {
                String begin = ssCheckingAmount.getBeginCreateDate();
                String checkin = in.getId();
                String end = ssCheckingAmount.getEndCreateDate();
                if (compare_date(begin, checkin) <= 0) {
                    inList.add(in);
                }

                if (ssCheckingAmount.getEndCreateDate() != null & ssCheckingAmount.getEndCreateDate() != null) {
                    if (compare_date(end, checkin) <= 0) {
                        inList.remove(in);
                    }
                }
            }
        }
        if (inList.size() > 0) {
            //人员考勤统计
            for (int i = 0; i < page.getList().size(); i++) {
                //人员id
                SsCheckingInLogs scils = new SsCheckingInLogs();
                List<SsCheckingInLogs> scilsList = new ArrayList<SsCheckingInLogs>();
                scils.setPersonname(page.getList().get(i).getPersonname());
                List<SsCheckingAmount> scaList = new ArrayList<SsCheckingAmount>();

                //获取员工单人记录
                if (ssCheckingInLogsService.findList(scils).size() > 0)
                    for (SsCheckingInLogs scil : ssCheckingInLogsService.findList(scils)) {
                        scilsList.add(scil);
                    }


                //员工考勤和考勤规则统计
                List<SsCheckingAmount> sca = new ArrayList<SsCheckingAmount>();

                for (SsCheckingIn si : inList) {
                    //考勤记录
                    SsCheckingAmount jl = new SsCheckingAmount();

                    if (si.getHoliday() > 0 | si.getWeekend() > 0) {
                        //节假日不统计
                    } else {
                        //考勤记录
                        for (SsCheckingInLogs s : scilsList) {
                            if (si.getId().equals(s.getRecorddate())) {
                                if (s.getIntime() == null && s.getOuttime() == null) {
                                    //缺勤
                                    qq += 1;
                                } else if (s.getIntime() == null) {
                                    if (s.getOuttime() != null) {
                                        //迟到
                                        cd += 1;
                                    }
                                } else if (s.getOuttime() == null) {
                                    //迟到
                                    if (s.getIntime() != null){
                                        cz += 1;
                                    }
                                } else if (compare_time(si.getAm(), s.getIntime()) < 1) {
                                    if (compare_time(si.getPm(), s.getOuttime()) > 1) {
                                        //早退
                                        cz += 1;
                                    }else{
                                    //迟到
                                    cd += 1;
                                    }
                                } else if (compare_time(si.getPm(), s.getOuttime()) > 1) {
                                    if (compare_time(si.getAm(), s.getIntime()) < 1) {
                                        cz += 1;
                                    }else {
                                        //早退
                                        zt += 1;
                                    }
                                } else {
                                    //正常
                                }
                            }else {
                                qq += 1;
                            }
                        }
                    }
                }

                page.getList().get(i).setIntimeamount(String.valueOf(cd));
                page.getList().get(i).setOuttimeamount(String.valueOf(zt));
                page.getList().get(i).setCheckamount(String.valueOf(qq));
                page.getList().get(i).setAmount(String.valueOf(cz));
            }
        }
        SsCheckingAmount ss = new SsCheckingAmount();
        model.addAttribute("ssCheckingAmount", ss);
        model.addAttribute("page", page);
        return "ss/checkingamount/ssCheckingAmountList";
    }

    @RequiresPermissions("checkingamount:ssCheckingAmount:view")
    @RequestMapping(value = "form")
    public String form(SsCheckingAmount ssCheckingAmount, Model model) {
        model.addAttribute("ssCheckingAmount", ssCheckingAmount);
        return "ss/checkingamount/ssCheckingAmountForm";
    }

    @RequiresPermissions("checkingamount:ssCheckingAmount:edit")
    @RequestMapping(value = "save")
    public String save(SsCheckingAmount ssCheckingAmount, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, ssCheckingAmount)) {
            return form(ssCheckingAmount, model);
        }
        ssCheckingAmountService.save(ssCheckingAmount);
        addMessage(redirectAttributes, "保存统计成功");
        return "redirect:" + Global.getAdminPath() + "/checkingamount/ssCheckingAmount/?repage";
    }

    @RequiresPermissions("checkingamount:ssCheckingAmount:edit")
    @RequestMapping(value = "delete")
    public String delete(SsCheckingAmount ssCheckingAmount, RedirectAttributes redirectAttributes) {
        ssCheckingAmountService.delete(ssCheckingAmount);
        addMessage(redirectAttributes, "删除统计成功");
        return "redirect:" + Global.getAdminPath() + "/checkingamount/ssCheckingAmount/?repage";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(SsCheckingAmount ssCheckingAmount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            Page<SsCheckingAmount> page = ssCheckingAmountService.findPage(new Page<SsCheckingAmount>(request, response), ssCheckingAmount);
            //获取一段时间的考勤规则
            List<SsCheckingIn> inList = new ArrayList<SsCheckingIn>();

            //迟到天数
            int cd = 0;
            //早退天数
            int zt = 0;
            //迟到早退天数
            int cz = 0;
            //缺勤天数
            int qq = 0;


            for (SsCheckingIn in : ssCheckingInService.findList(new SsCheckingIn())
                    ) {
                if (ssCheckingAmount.getBeginCreateDate() != null & ssCheckingAmount.getBeginCreateDate() != null) {
                    String begin = ssCheckingAmount.getBeginCreateDate();
                    String checkin = in.getId();
                    String end = ssCheckingAmount.getEndCreateDate();
                    if (compare_date(begin, checkin) <= 0) {
                        inList.add(in);
                    }

                    if (ssCheckingAmount.getEndCreateDate() != null & ssCheckingAmount.getEndCreateDate() != null) {
                        if (compare_date(end, checkin) <= 0) {
                            inList.remove(in);
                        }
                    }
                }
            }
            if (inList.size() > 0) {
                //人员考勤统计
                for (int i = 0; i < page.getList().size(); i++) {
                    //人员id
                    SsCheckingInLogs scils = new SsCheckingInLogs();
                    List<SsCheckingInLogs> scilsList = new ArrayList<SsCheckingInLogs>();
                    scils.setPersonname(page.getList().get(i).getPersonname());
                    List<SsCheckingAmount> scaList = new ArrayList<SsCheckingAmount>();

                    //获取员工单人记录
                    if (ssCheckingInLogsService.findList(scils).size() > 0)
                        for (SsCheckingInLogs scil : ssCheckingInLogsService.findList(scils)) {
                            scilsList.add(scil);
                        }


                    //员工考勤和考勤规则统计
                    List<SsCheckingAmount> sca = new ArrayList<SsCheckingAmount>();

                    for (SsCheckingIn si : inList) {
                        //考勤记录
                        SsCheckingAmount jl = new SsCheckingAmount();

                        if (si.getHoliday() > 0 | si.getWeekend() > 0) {
                            //节假日不统计
                        } else {
                            //考勤记录
                            for (SsCheckingInLogs s : scilsList) {
                                if (si.getId().equals(s.getRecorddate())) {
                                    if (s.getIntime() == null && s.getOuttime() == null) {
                                        //缺勤
                                        qq += 1;
                                    } else if (s.getIntime() == null) {
                                        if (s.getOuttime() != null) {
                                            //迟到
                                            cd += 1;
                                        }
                                    } else if (s.getOuttime() == null) {
                                        //迟到
                                        if (s.getIntime() != null){
                                            cz += 1;
                                        }
                                    } else if (compare_time(si.getAm(), s.getIntime()) < 1) {
                                        if (compare_time(si.getPm(), s.getOuttime()) > 1) {
                                            //早退
                                            cz += 1;
                                        }else{
                                            //迟到
                                            cd += 1;
                                        }
                                    } else if (compare_time(si.getPm(), s.getOuttime()) > 1) {
                                        if (compare_time(si.getAm(), s.getIntime()) < 1) {
                                            cz += 1;
                                        }else {
                                            //早退
                                            zt += 1;
                                        }
                                    } else {
                                        //正常
                                    }
                                }else {
                                    qq += 1;
                                }
                            }
                        }
                    }

                    page.getList().get(i).setIntimeamount(String.valueOf(cd));
                    page.getList().get(i).setOuttimeamount(String.valueOf(zt));
                    page.getList().get(i).setCheckamount(String.valueOf(qq));
                    page.getList().get(i).setAmount(String.valueOf(cz));
                }
            }
            String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            new ExportExcel("用户数据", SsCheckingAmount.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/checkingamount/ssCheckingAmount/list?repage";
    }

    public int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
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

//    public SsCheckingAmount amount(SsCheckingInLogs s, SsCheckingIn a) {
////        if (compare_time(a.getAm(), s.getIntime()) >)
//    }
}