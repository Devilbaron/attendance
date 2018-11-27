<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>统计管理</title>
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
		<li><a href="${ctx}/checkingamount/ssCheckingAmount/">统计列表</a></li>
		<li class="active"><a href="${ctx}/checkingamount/ssCheckingAmount/form?id=${ssCheckingAmount.id}">统计<shiro:hasPermission name="checkingamount:ssCheckingAmount:edit">${not empty ssCheckingAmount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="checkingamount:ssCheckingAmount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ssCheckingAmount" action="${ctx}/checkingamount/ssCheckingAmount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">部门名称：</label>
			<div class="controls">
				<form:input path="departmentname" htmlEscape="false" maxlength="35" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门ID：</label>
			<div class="controls">
				<form:input path="departmentcode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员名称：</label>
			<div class="controls">
				<form:input path="personname" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">人员ID：</label>
			<div class="controls">
				<form:input path="personcode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">迟到结论：</label>
			<div class="controls">
				<form:input path="intimeamount" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">早退结论：</label>
			<div class="controls">
				<form:input path="outtimeamount" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">全天结论：</label>
			<div class="controls">
				<form:input path="checkamount" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缺勤结论：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="checkingamount:ssCheckingAmount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>