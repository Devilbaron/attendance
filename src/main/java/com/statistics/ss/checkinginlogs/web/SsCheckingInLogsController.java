/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.statistics.ss.checkinginlogs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.statistics.ss.checkinginlogs.entity.SsCheckingInLogs;
import com.statistics.ss.checkinginlogs.service.SsCheckingInLogsService;

/**
 * 考勤记录Controller
 * @author DB
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/checkinginlogs/ssCheckingInLogs")
public class SsCheckingInLogsController extends BaseController {

	@Autowired
	private SsCheckingInLogsService ssCheckingInLogsService;
	
	@ModelAttribute
	public SsCheckingInLogs get(@RequestParam(required=false) String recordid) {
		SsCheckingInLogs entity = null;
		if (StringUtils.isNotBlank(recordid)){
			entity = ssCheckingInLogsService.get(recordid);
		}
		if (entity == null){
			entity = new SsCheckingInLogs();
		}
		return entity;
	}
	
	@RequiresPermissions("checkinginlogs:ssCheckingInLogs:view")
	@RequestMapping(value = {"list", ""})
	public String list(SsCheckingInLogs ssCheckingInLogs, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SsCheckingInLogs> page = ssCheckingInLogsService.findPage(new Page<SsCheckingInLogs>(request, response), ssCheckingInLogs);

		model.addAttribute("ssCheckingInLogs",ssCheckingInLogs);
		model.addAttribute("page", page);
		return "ss/checkinginlogs/ssCheckingInLogsList";
	}

	@RequiresPermissions("checkinginlogs:ssCheckingInLogs:view")
	@RequestMapping(value = "form")
	public String form(SsCheckingInLogs ssCheckingInLogs, Model model) {
		model.addAttribute("ssCheckingInLogs", ssCheckingInLogs);
		return "ss/checkinginlogs/ssCheckingInLogsForm";
	}

	@RequiresPermissions("checkinginlogs:ssCheckingInLogs:edit")
	@RequestMapping(value = "save")
	public String save(SsCheckingInLogs ssCheckingInLogs, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ssCheckingInLogs)){
			return form(ssCheckingInLogs, model);
		}
//		ssCheckingInLogsService.delete(ssCheckingInLogs);
		ssCheckingInLogsService.save(ssCheckingInLogs);
		addMessage(redirectAttributes, "保存考勤记录成功");
		return "redirect:"+Global.getAdminPath()+"/checkinginlogs/ssCheckingInLogs/?repage";
	}
	
	@RequiresPermissions("checkinginlogs:ssCheckingInLogs:edit")
	@RequestMapping(value = "delete")
	public String delete(SsCheckingInLogs ssCheckingInLogs, RedirectAttributes redirectAttributes) {
		ssCheckingInLogsService.delete(ssCheckingInLogs);
		addMessage(redirectAttributes, "删除考勤记录成功");
		return "redirect:"+Global.getAdminPath()+"/checkinginlogs/ssCheckingInLogs/?repage";
	}

}