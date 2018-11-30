<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤记录管理</title>
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
		<li><a href="${ctx}/checkinginlogs/ssCheckingInLogs/">考勤记录列表</a></li>
		<li class="active"><a href="${ctx}/checkinginlogs/ssCheckingInLogs/form?id=${ssCheckingInLogs.id}">考勤记录<shiro:hasPermission name="checkinginlogs:ssCheckingInLogs:edit">${not empty ssCheckingInLogs.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="checkinginlogs:ssCheckingInLogs:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ssCheckingInLogs" action="${ctx}/checkinginlogs/ssCheckingInLogs/save" method="post" class="form-horizontal">
		<form:hidden path="recordid"/>
		<sys:message content="${message}"/>		
		<%--<div class="control-group">--%>
			<%--<label class="control-label">考勤ID：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:input path="recordid" htmlEscape="false" maxlength="64" class="input-xlarge required"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">考勤日期：</label>
			<div class="controls">
				<form:input path="recorddate" readonly="readonly" disabled="true" htmlEscape="false" maxlength="35" class="input-xlarge "
                />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门名称：</label>
			<div class="controls">
				<form:input path="departmentname" readonly="readonly" disabled="true" htmlEscape="false" maxlength="35" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员名称：</label>
			<div class="controls">
				<form:input path="personname" readonly="readonly" disabled="true" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签到时间：</label>
			<div class="controls">
				<form:input path="intime" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签退时间：</label>
			<div class="controls">
				<form:input path="outtime" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="checkinginlogs:ssCheckingInLogs:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>