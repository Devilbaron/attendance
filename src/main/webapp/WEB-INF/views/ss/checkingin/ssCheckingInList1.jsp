<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤规则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/checkingin/ssCheckingIn/">考勤规则列表</a></li>
		<shiro:hasPermission name="checkingin:ssCheckingIn:edit"><li><a href="${ctx}/checkingin/ssCheckingIn/calendar">考勤规则添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ssCheckingIn" action="${ctx}/checkingin/ssCheckingIn/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日期：</label>
				<input name="dateline" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ssCheckingIn.dateline}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>上午时间：</label>
				<input name="am" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ssCheckingIn.am}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>下午时间4：</label>
				<input name="pm" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ssCheckingIn.pm}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>日期</th>
				<th>是否周末</th>
				<th>是否节假日</th>
				<th>上午时间</th>
				<th>下午时间4</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="checkingin:ssCheckingIn:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ssCheckingIn">
			<tr>
				<td><a href="${ctx}/checkingin/ssCheckingIn/form?id=${ssCheckingIn.id}">
					<fmt:formatDate value="${ssCheckingIn.dateline}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<td>
					${ssCheckingIn.weekend}
				</td>
				<td>
					${ssCheckingIn.holiday}
				</td>
				<td>
					<fmt:formatDate value="${ssCheckingIn.am}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${ssCheckingIn.pm}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${ssCheckingIn.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${ssCheckingIn.remarks}
				</td>
				<shiro:hasPermission name="checkingin:ssCheckingIn:edit"><td>
    				<a href="${ctx}/checkingin/ssCheckingIn/form?id=${ssCheckingIn.id}">修改</a>
					<a href="${ctx}/checkingin/ssCheckingIn/delete?id=${ssCheckingIn.id}" onclick="return confirmx('确认要删除该考勤规则吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>