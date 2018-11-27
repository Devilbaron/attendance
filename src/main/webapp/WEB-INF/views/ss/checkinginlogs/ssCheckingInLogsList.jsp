<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>考勤统计管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/checkinginlogs/ssCheckingInLogs/">考勤统计列表</a></li>
    <shiro:hasPermission name="checkinginlogs:ssCheckingInLogs:edit">
        <li><a href="${ctx}/checkinginlogs/ssCheckingInLogs/form">考勤统计添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="ssCheckingInLogs" action="${ctx}/checkinginlogs/ssCheckingInLogs/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>考勤日期：</label>
                <form:input path="recorddate" htmlEscape="false" maxlength="32" class="input-medium"/>
            <%--<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                   <%--value="<fmt:formatDate value="${ssCheckingInLogs.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
                   <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> ---%>
            <%--<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                   <%--value="<fmt:formatDate value="${ssCheckingInLogs.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
                   <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
        </li>

        <li><label>人员名称：</label>
            <form:input path="personname" htmlEscape="false" maxlength="32" class="input-medium"/>
        </li>

        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>考勤日期</th>
        <th>部门ID</th>
        <th>部门名称</th>
        <th>人员ID</th>
        <th>人员名称</th>
        <th>最晚签到</th>
        <th>最早签退</th>
        <th>更新者</th>
        <th>备注</th>
        <shiro:hasPermission name="checkinginlogs:ssCheckingInLogs:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="ssCheckingInLogs">
        <tr>
            <td><a href="${ctx}/checkinginlogs/ssCheckingInLogs/form?id=${ssCheckingInLogs.id}">
                    ${ssCheckingInLogs.recorddate}
            </a></td>
            <td>
                    ${ssCheckingInLogs.departmentcode}
            </td>
            <td>
                    ${ssCheckingInLogs.departmentname}
            </td>
            <td>
                    ${ssCheckingInLogs.personcode}
            </td>
            <td>
                    ${ssCheckingInLogs.personname}
            </td>
            <td>
                    ${ssCheckingInLogs.intime}
            </td>
            <td>
                    ${ssCheckingInLogs.outtime}
            </td>
            <td>
                    ${ssCheckingInLogs.updateBy.id}
            </td>
            <td>
                    ${ssCheckingInLogs.remarks}
            </td>
            <shiro:hasPermission name="checkinginlogs:ssCheckingInLogs:edit">
                <td>
                    <a href="${ctx}/checkinginlogs/ssCheckingInLogs/form?id=${ssCheckingInLogs.id}">修改</a>
                    <a href="${ctx}/checkinginlogs/ssCheckingInLogs/delete?id=${ssCheckingInLogs.id}"
                       onclick="return confirmx('确认要删除该考勤统计吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>