<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤规则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/checkingin/ssCheckingIn/">考勤规则列表</a></li>
		<li class="active"><a href="${ctx}/checkingin/ssCheckingIn/form?id=${ssCheckingIn.id}">考勤规则<shiro:hasPermission name="checkingin:ssCheckingIn:edit">${not empty ssCheckingIn.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="checkingin:ssCheckingIn:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ssCheckingIn" action="${ctx}/checkingin/ssCheckingIn/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">日期：</label>
			<div class="controls">
				<input name="id" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${ssCheckingIn.dateline}" pattern="yyyy-MM-dd"/>"
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});--%>
					"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否周末：</label>
			<div class="controls">
				<form:select path="weekend" htmlEscape="false" maxlength="20"  class="input-xlarge digits">
					<form:option value="0" label="需工作"/>
					<form:option value="1" label="假期日"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否节假日：</label>
			<div class="controls">
				<form:select path="holiday" htmlEscape="false" maxlength="20"  class="input-xlarge digits">
					<form:option value="0" label="需工作"/>
					<form:option value="1" label="假期日"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上午时间：</label>
			<div class="controls">
				<input name="am" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="${ssCheckingIn.am}"
					onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下午时间：</label>
			<div class="controls">
				<input name="pm" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="${ssCheckingIn.pm}"
					onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="checkingin:ssCheckingIn:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>