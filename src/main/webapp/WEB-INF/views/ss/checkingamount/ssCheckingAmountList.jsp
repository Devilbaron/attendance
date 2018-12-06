<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>统计管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnExport").click(function () {
                top.$.jBox.confirm("确认要导出用户数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        $("#searchForm").attr("action", "${ctx}/checkingamount/ssCheckingAmount/export");
                        $("#searchForm").submit();
                    }
                }, {buttonsFocus: 1});
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });
            function page(n, s) {
                $("#pageNo").val(n);
                $("#pageSize").val(s);
                $("#searchForm").submit();
                return false;
            }
        })
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/checkingamount/ssCheckingAmount/">统计列表</a></li>
    <shiro:hasPermission name="checkingamount:ssCheckingAmount:edit">
        <%--<li><a href="${ctx}/checkingamount/ssCheckingAmount/form">统计添加</a></li>--%>
    </shiro:hasPermission>
</ul>

<%
    String ss =(String)pageContext.getAttribute("ssCheckingAmount");
%>
<c:set var="begin" value="${ssCheckingAmount.beginCreateDate}"/>
<c:set var="end" value="${ssCheckingAmount.endCreateDate}"/>
<form:form id="searchForm" modelAttribute="ssCheckingAmount" action="${ctx}/checkingamount/ssCheckingAmount/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>日期选择：</label>
            <fmt:parseDate value="${ssCheckingAmount.beginCreateDate}" pattern="yyyy-MM-dd" var="beginCreateDate"/>
            <fmt:parseDate value="${ssCheckingAmount.endCreateDate}" pattern="yyyy-MM-dd" var="endCreateDate"/>
            <input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${beginCreateDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
            <input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${endCreateDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
        <th>部门名称</th>
        <th>人员名称</th>
        <th>迟到</th>
        <th>早退</th>
        <th>缺勤</th>
        <th>迟到早退</th>
        <th>备注</th>
        <shiro:hasPermission name="checkingamount:ssCheckingAmount:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="ssCheckingAmount">
        <tr>
            <td><a href="${ctx}/checkingamount/ssCheckingAmount/form?id=${ssCheckingAmount.id}">
                    ${ssCheckingAmount.departmentname}
            </a></td>
            <td>
                    ${ssCheckingAmount.personname}
            </td>
            <td>
                    ${ssCheckingAmount.intimeamount}
            </td>
            <td>
                    ${ssCheckingAmount.outtimeamount}
            </td>
            <td>
                    ${ssCheckingAmount.checkamount}
            </td>
            <td>
                    ${ssCheckingAmount.amount}
            </td>
            <td>
                    ${ssCheckingAmount.remarks}
            </td>
            <shiro:hasPermission name="checkingamount:ssCheckingAmount:edit">
                <td>
                    <a href="${ctx}/checkinginlogsdetail/ssCheckingInLogs/list?personname=${ssCheckingAmount.personname}&beginRecorddate=${begin}&endRecorddate=${end}">明细</a>
                    <%--<a href="${ctx}/checkingamount/ssCheckingAmount/delete?id=${ssCheckingAmount.id}"--%>
                       <%--onclick="return confirmx('确认要删除该统计吗？', this.href)">删除</a>--%>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>