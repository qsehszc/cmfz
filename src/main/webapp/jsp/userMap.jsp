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

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="userMap" style="width: 600px;height: 500px"></div>

<script type="text/javascript">
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('userMap'));
        var option = {
            title: {
                text: '用户分布图',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['男', '女']
            },
            visualMap: {
                max:10,
                min:1,
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },

        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $.get("${pageContext.request.contextPath}/user/showMap", "json", function (data) {
            myChart.setOption({
                series: [{
                    name: '男',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: data.man,
                }, {
                    name: '女',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: data.women,
                }]
            })

        })


    });
</script>


