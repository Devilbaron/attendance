<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function () {
                top.$.jBox.confirm("确认要导出用户数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        $("#searchForm").attr("action", "${ctx}/checkinginlogsdetail/ssCheckingInLogs/export");
                        $("#searchForm").submit();
                    }
                }, {buttonsFocus: 1});
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });
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
		<li class="active"><a href="${ctx}/checkinginlogs/ssCheckingInLogs/">考勤记录列表</a></li>
		<shiro:hasPermission name="checkinginlogs:ssCheckingInLogs:edit"><li>
			<%--<a href="${ctx}/checkinginlogs/ssCheckingInLogs/form">考勤记录添加</a>--%>
		</li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ssCheckingInLogs" action="${ctx}/checkinginlogsdetail/ssCheckingInLogs" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%--<li><label>考勤日期：</label>--%>
				<%--<form:input path="recorddate" htmlEscape="false" maxlength="32" class="input-medium"/>--%>
			<%--</li>--%>
			<li><label>考勤日期：</label>
				<fmt:parseDate value="${ssCheckingInLogs.beginRecorddate}" pattern="yyyy-MM-dd" var="beginRecorddate"/>
				<fmt:parseDate value="${ssCheckingInLogs.endRecorddate}" pattern="yyyy-MM-dd" var="endRecorddate"/>
				<input name="beginRecorddate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${beginRecorddate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endRecorddate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${endRecorddate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>部门名称：</label>
				<form:input path="departmentname" htmlEscape="false" maxlength="35" class="input-medium"/>
			</li>
			<li><label>人员名称：</label>
				<form:input path="personname" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>考勤日期</th>
				<th>部门名称</th>
				<th>人员名称</th>
				<th>上班打卡</th>
				<th>上班描述</th>
				<th>下班打卡</th>
				<th>下班描述</th>
				<%--<th>迟到时间</th>--%>
				<%--<th>早退时间</th>--%>
				<th>出勤结论</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="checkinginlogs:ssCheckingInLogs:edit">
					<%--<th>操作</th>--%>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ssCheckingInLogs">
			<tr>
				<td><a href="${ctx}/checkinginlogs/ssCheckingInLogs/form?recordid=${ssCheckingInLogs.recordid}">
					${ssCheckingInLogs.recorddate}
				</a></td>
				<td>
					${ssCheckingInLogs.departmentname}
				</td>
				<td>
					${ssCheckingInLogs.personname}
				</td>
				<td>
					${ssCheckingInLogs.intime}
				</td>
				<td>
					${ssCheckingInLogs.intimermk}
				</td>
				<td>
					${ssCheckingInLogs.outtime}
				</td>
				<td>
					${ssCheckingInLogs.outtimermk}
				</td>
				<%--<td>--%>
					<%--迟到时间--%>
				<%--</td>--%>
				<%--<td>--%>
					<%--早退时间--%>
				<%--</td>--%>
				<td>
					${ssCheckingInLogs.attendanceconc}
				</td>
				<td>
					<fmt:formatDate value="${ssCheckingInLogs.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${ssCheckingInLogs.remarks}
				</td>
				<shiro:hasPermission name="checkinginlogs:ssCheckingInLogs:edit">
					<%--<td><a href="${ctx}/checkinginlogs/ssCheckingInLogs/form?recordid=${ssCheckingInLogs.recordid}">修改</a>--%>
					<%--<a href="${ctx}/checkinginlogs/ssCheckingInLogs/delete?recordid=${ssCheckingInLogs.recordid}" onclick="return confirmx('确认要删除该考勤记录吗？', this.href)">删除</a></td>--%>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>