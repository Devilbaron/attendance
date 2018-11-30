<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
    String ctx = request.getContextPath();
    %>
<html>
<head>
    <title>考勤规则管理</title>
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
    <link rel="stylesheet" href="${ctxStatic}/layui-calendar/layui/css/layui.css">
    <link rel="stylesheet" href="${ctxStatic}/layui-calendar/css/date.css">
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/checkingin/ssCheckingIn/">考勤规则列表</a></li>
    <shiro:hasPermission name="checkingin:ssCheckingIn:edit">
        <li><a href="${ctx}/a/checkingin/ssCheckingIn/calendar">考勤规则删除</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="ssCheckingIn" action="${ctx}/a/checkingin/ssCheckingIn/cLogTables" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <li><label>年份：</label>
        <input name="yyyy" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
               value="<fmt:formatDate  value="<%=new Date() %>" pattern="yyyy"/>"
               onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
    </li>
    <li><label>上午时间：</label>
        <input name="am" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
               value="<fmt:formatDate value="${ssCheckingIn.am}" pattern="HH:mm:ss"/>"
               onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
    </li>
    <li><label>下午时间：</label>
        <input name="pm" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
               value="<fmt:formatDate value="${ssCheckingIn.pm}" pattern="HH:mm:ss"/>"
               onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
    </li>
    <%--<input type="hidden" name="yyyy" value="<fmt:formatDate value="<%=new Date() %>" pattern="yyyy"/>">--%>
    <li class="btns"><input id="cLogTablesBtnSubmit" class="btn btn-primary" type="submit" value="生成考勤规则"/></li>

    <li class="clearfix"></li>
</form:form>
<sys:message content="${message}"/>
<%--<table id="contentTable" class="table table-striped table-bordered table-condensed">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th>日期</th>--%>
<%--<th>是否周末</th>--%>
<%--<th>是否节假日</th>--%>
<%--<th>上午时间</th>--%>
<%--<th>下午时间</th>--%>
<%--<th>更新时间</th>--%>
<%--<th>备注</th>--%>
<%--<shiro:hasPermission name="checkingin:ssCheckingIn:edit">--%>
<%--<th>操作</th>--%>
<%--</shiro:hasPermission>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<tbody>--%>
<%--<c:forEach items="${page.list}" var="ssCheckingIn">--%>
<%--<tr>--%>
<%--<td><a href="${ctx}/checkingin/ssCheckingIn/form?id=${ssCheckingIn.id}">--%>
<%--<fmt:formatDate value="${ssCheckingIn.dateline}" pattern="yyyy-MM-dd"/>--%>
<%--</a></td>--%>
<%--<td>--%>
<%--${ssCheckingIn.weekend<=0?"无假":"有假"}--%>
<%--</td>--%>
<%--<td>--%>
<%--${ssCheckingIn.holiday<=0?"无假":"有假"}--%>
<%--</td>--%>
<%--<td>--%>
<%--${ssCheckingIn.am}--%>
<%--</td>--%>
<%--<td>--%>
<%--${ssCheckingIn.pm}--%>
<%--</td>--%>
<%--<td>--%>
<%--<fmt:formatDate value="${ssCheckingIn.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
<%--</td>--%>
<%--<td>--%>
<%--${ssCheckingIn.remarks}--%>
<%--</td>--%>
<%--<shiro:hasPermission name="checkingin:ssCheckingIn:edit">--%>
<%--<td>--%>
<%--<a href="${ctx}/checkingin/ssCheckingIn/form?id=${ssCheckingIn.id}">修改</a>--%>
<%--<a href="${ctx}/checkingin/ssCheckingIn/delete?id=${ssCheckingIn.id}"--%>
<%--onclick="return confirmx('确认要删除该考勤规则吗？', this.href)">删除</a>--%>
<%--</td>--%>
<%--</shiro:hasPermission>--%>
<%--</tr>--%>
<%--</c:forEach>--%>
<%--</tbody>--%>
<%--</table>--%>
<%--<div class="pagination">${page}</div>--%>

<div class="layui-container" style="padding: 15px;">
    <div class="layui-row">
        <div class="layui-col-md12">
            <blockquote class="layui-elem-quote">考勤规则设置</blockquote>
            <div class="layui-inline" id="test-n2"></div>

        </div>
    </div>
</div>


<script src="${ctxStatic}/layui-calendar/layui/layui.js"></script>
<script>
    var ctx = "${ctx}";

    layui.use(['layer', 'form', 'jquery', 'laydate'], function () {
        var layer = layui.layer,
            $ = layui.jquery,
            laydate = layui.laydate,
            form = layui.form;

        //定义json
        var data = {};
        var dataArr = [];
        var new_date = new Date();
        loding_date(new_date, data);

        //日历插件调用方法
        function loding_date(date_value, data) {

            laydate.render({
                elem: '#test-n2'
                , type: 'date'
                , theme: 'grid'
                , max: '2099-06-16 23:59:59'
                , position: 'static'
                , range: false
                , value: date_value
                , isInitValue: false
                , calendar: true
                , btns: false
                , ready: function (value) {
                    //console.log(value);
                    hide_mr(value);
                }
                , done: function (value, date, endDate) {
                    //console.log(value); //得到日期生成的值，如：2017-08-18
                    // console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                    //console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                    //layer.msg(value)

                    //调用弹出层方法
                    date_chose(value, data);
                }
                , change: function (value, date) {
                    hide_mr(date);
                }
                , mark: data//重要json！

            });
        }

        //遍历对象
        // function holiday() {
        //     for(var i in obj) {
        //
        //         console.log(i,":",obj[i]);
        //
        //     }
        // }
        function hide_mr(value) {
            var mm = value.year + '-' + value.month + '-' + value.date;
            var holidaynum = 0;
            $('.laydate-theme-grid table tbody').find('[lay-ymd="' + mm + '"]').removeClass('layui-this');
            //console.log(value)
        }

        //获取隐藏的弹出层内容
        var date_choebox = $('.date_box').html();

        //定义弹出层方法
        function date_chose(obj_date, data) {
            var index = layer.open({
                type: 2,
                skin: 'layui-layer-rim', //加上边框
                title: '添加记录',
                area: ['800px', '500px'], //宽高
                btn: ['确定', '撤销', '取消'],
                content: ctx+'/a/checkingin/ssCheckingIn/form?id=' + obj_date
//		  '<div class="text_box">'+
//	      		'<form class="layui-form" action="">'+
//	      		 '<div class="layui-form-item layui-form-text">'+
//						     ' <textarea id="text_book" placeholder="请输入内容"  class="layui-textarea"></textarea>'+
//						  '</div>'+
//	      		'</form>'+
//	      		'</div>'
                , success: function () {
//	      		$('#text_book').val(data[obj_date])
                }
                , yes: function () {
                    //调用添加/编辑标注方法
                    if ($('#text_book').val() != '') {
                        00
                        chose_moban(obj_date, data);
                        layer.close(index);
                    } else {
                        layer.msg('不能为空', {icon: 2});
                    }

                }, btn2: function () {
                    chexiao(obj_date, data);
                }
            });
        }

//         //定义添加/编辑标注方法
//         function chose_moban(obj_date,markJson){
//             //获取弹出层val
//             var chose_moban_val = $('#text_book').val();
//
//             $('#test-n2').html('');//重要！由于插件是嵌套指定容器，再次调用前需要清空原日历控件
//             //添加属性
//             markJson[obj_date] = chose_moban_val;
//
//             data = {
//                 time:obj_date,
//                 value:chose_moban_val
//             }
//
//             //添加修改数值
//             for (var i in dataArr) {
//                 if(dataArr[i].time==obj_date){
//                     dataArr[i].value = chose_moban_val;
//                     dataArr.splice(i, 1);
//                 }
//             }
//             dataArr.push(data);
//
//             //按时间正序排序
//
//             dataArr.sort(function(a,b){
//                 return Date.parse(a.time) - Date.parse(b.time);//时间正序
//             });
//
//             //按时间倒序排列
// //				dataArr.sort(function(a,b){
// //					return Date.parse(b.time) - Date.parse(a.time);//时间正序
// //				});
//
//             //console.log(JSON.stringify(data))
//             // console.log(JSON.stringify(markJson));
//             console.log(JSON.stringify(dataArr))
//
//             //再次调用日历控件，
//             loding_date(obj_date,markJson);//重要！，再标注一个日期后会刷新当前日期变为初始值，所以必须调用当前选定日期。
//
//         }
//
//         //撤销选择
//         function chexiao(obj_date,markJson){
//             //删除指定日期标注
//             delete markJson[obj_date];
//             //console.log(JSON.stringify(markJson));
//             for (var i in dataArr) {
//                 if(dataArr[i].time==obj_date){
//                     dataArr.splice(i, 1);
//                 }
//             }
//             console.log(JSON.stringify(dataArr))
//             //原理同添加一致
//             $('#test-n2').html('');
//             loding_date(obj_date,markJson);
//         }
    });</script>
</body>
</html>