///**
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// */
//package com.statistics.ss.checkingin.web;
//
//import com.statistics.ss.checkingin.entity.SsCheckingIn;
//import com.statistics.ss.checkingin.service.SsCheckingInService;
//import com.thinkgem.jeesite.common.config.Global;
//import com.thinkgem.jeesite.common.persistence.Page;
//import com.thinkgem.jeesite.common.utils.StringUtils;
//import com.thinkgem.jeesite.common.web.BaseController;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 考勤规则Controller
// * @author DB
// * @version 2018-09-25
// */
//@Controller
//@RequestMapping(value = "${adminPath}/checkingin/ssCheckingIn")
//public class SsCheckingInController1 extends BaseController {
//
//	@Autowired
//	private SsCheckingInService ssCheckingInService;
//
//	@ModelAttribute
//	public SsCheckingIn get(@RequestParam(required=false) String id) {
//		SsCheckingIn entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = ssCheckingInService.get(id);
//		}
//		if (entity == null){
//			entity = new SsCheckingIn();
//		}
//		return entity;
//	}
//
//	@RequiresPermissions("checkingin:ssCheckingIn:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(SsCheckingIn ssCheckingIn, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<SsCheckingIn> page = ssCheckingInService.findPage(new Page<SsCheckingIn>(request, response), ssCheckingIn);
//		model.addAttribute("page", page);
//		return "ss/checkingin/ssCheckingInList";
//	}
//
//	@RequiresPermissions("checkingin:ssCheckingIn:view")
//	@RequestMapping(value = "form")
//	public String form(SsCheckingIn ssCheckingIn, Model model) {
//		model.addAttribute("ssCheckingIn", ssCheckingIn);
//		return "ss/checkingin/ssCheckingInForm";
//	}
//
//	@RequiresPermissions("checkingin:ssCheckingIn:edit")
//	@RequestMapping(value = "save")
//	public String save(HttpServletRequest request,SsCheckingIn ssCheckingIn, Model model, RedirectAttributes redirectAttributes) {
//
//		String str = request.getParameter("am");
//		String str1 = request.getParameter("pm");
//		if (!beanValidator(model, ssCheckingIn)){
//			return form(ssCheckingIn, model);
//		}
//		ssCheckingInService.save(ssCheckingIn);
//		addMessage(redirectAttributes, "保存考勤规则成功");
//		return "redirect:"+Global.getAdminPath()+"/checkingin/ssCheckingIn/?repage";
//	}
//
//	@RequiresPermissions("checkingin:ssCheckingIn:edit")
//	@RequestMapping(value = "delete")
//	public String delete(SsCheckingIn ssCheckingIn, RedirectAttributes redirectAttributes) {
//		ssCheckingInService.delete(ssCheckingIn);
//		addMessage(redirectAttributes, "删除考勤规则成功");
//		return "redirect:"+Global.getAdminPath()+"/checkingin/ssCheckingIn/?repage";
//	}
//
//}