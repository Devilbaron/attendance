<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>考勤规则</title>
    <meta charset="utf-8">
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnDel").click(function () {
                top.$.jBox.confirm("确认删除数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        $("#searchForm").attr("action", "${ctx}/a/checkingin/ssCheckingIn/yDel");
                        $("#searchForm").submit();
                    }
                }, {buttonsFocus: 1});
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });

            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
    <link rel="stylesheet" href="${ctxStatic}/layui-calendar/layui/css/layui.css">
    <link rel="stylesheet" href="${ctxStatic}/layui-calendar/css/date.css">
</head>
<body>

<ul class="nav nav-tabs">
    <shiro:hasPermission name="checkingin:ssCheckingIn:edit">
        <li><a href="${ctx}/checkingin/ssCheckingIn/">考勤规则列表</a></li>
        <li class="active"><a href="${ctx}/checkingin/ssCheckingIn/form?id=${ssCheckingIn.id}">考勤规则<shiro:hasPermission
                name="checkingin:ssCheckingIn:edit">${not empty ssCheckingIn.id?'修改':'删除'}</shiro:hasPermission><shiro:lacksPermission
                name="checkingin:ssCheckingIn:edit">查看</shiro:lacksPermission></a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="ssCheckingIn" action="${ctx}/checkingin/ssCheckingIn/yDel"
           method="post"
           class="breadcrumb form-search">
    <li><label>选择年份：</label>
        <input name="yyyy" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
               value="<fmt:formatDate value="<%=new Date() %>" pattern="yyyy"/>"
               onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
    </li>
    <li class="btns"><input id="btnDel" class="btn btn-primary" type="button"value="删除考勤规则"/></li>
    <li class="clearfix"></li>
</form:form>
<sys:message content="${message}"/>

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
        function holiday() {
            for (var i in obj) {

                console.log(i, ":", obj[i]);

            }
        }

        function hide_mr(value) {
            var mm = value.year + '-' + value.month + '-' + value.date;

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
                content: '/a/checkingin/ssCheckingIn/form?dateline=' + obj_date
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

        //定义添加/编辑标注方法
        function chose_moban(obj_date, markJson) {
            //获取弹出层val
            var chose_moban_val = $('#text_book').val();

            $('#test-n2').html('');//重要！由于插件是嵌套指定容器，再次调用前需要清空原日历控件
            //添加属性
            markJson[obj_date] = chose_moban_val;

            data = {
                time: obj_date,
                value: chose_moban_val
            }

            //添加修改数值
            for (var i in dataArr) {
                if (dataArr[i].time == obj_date) {
                    dataArr[i].value = chose_moban_val;
                    dataArr.splice(i, 1);
                }
            }
            dataArr.push(data);

            //按时间正序排序

            dataArr.sort(function (a, b) {
                return Date.parse(a.time) - Date.parse(b.time);//时间正序
            });

            //按时间倒序排列
//				dataArr.sort(function(a,b){
//					return Date.parse(b.time) - Date.parse(a.time);//时间正序
//				});

            //console.log(JSON.stringify(data))
            // console.log(JSON.stringify(markJson));
            console.log(JSON.stringify(dataArr))

            //再次调用日历控件，
            loding_date(obj_date, markJson);//重要！，再标注一个日期后会刷新当前日期变为初始值，所以必须调用当前选定日期。

        }

        //撤销选择
        function chexiao(obj_date, markJson) {
            //删除指定日期标注
            delete markJson[obj_date];
            //console.log(JSON.stringify(markJson));
            for (var i in dataArr) {
                if (dataArr[i].time == obj_date) {
                    dataArr.splice(i, 1);
                }
            }
            console.log(JSON.stringify(dataArr))
            //原理同添加一致
            $('#test-n2').html('');
            loding_date(obj_date, markJson);
        }
    });</script>
</body>
</html>
