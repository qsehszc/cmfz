<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-374e6325f91646eeb2bec6f977717e53", //替换为您的应用appkey
    });
    goEasy.subscribe({
        channel: "cmfz", //替换为您自己的channel
        onMessage: function (message) {
            var data = JSON.parse(message.content);
            myChart.setOption({
                series: [{
                    name: '男',
                    // line 折线形式(反应趋势) bar 柱状图(统计数量)  pie 饼状图(反应比例)
                    type: 'bar',
                    data: data.man
                },{
                    name: '女',
                    type: 'bar',
                    data: data.women
                }]
            });
        }
    });
</script>

<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 1000px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '持明法洲用户注册趋势'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["今天注册的用户数量","一周内注册的用户数量","一个月内注册的用户数量","一年内注册的用户数量"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    $.ajax({
        url:"${pageContext.request.contextPath}/user/showUserTime",
        type:"get",
        datatype:"json",
        success:function (data) {
            myChart.setOption({
                series: [{
                    name: '男',
                    // line 折线形式(反应趋势) bar 柱状图(统计数量)  pie 饼状图(反应比例)
                    type: 'bar',
                    data: data.man
                },{
                    name: '女',
                    type: 'bar',
                    data: data.women
                }]
            });
        }
    })
</script>