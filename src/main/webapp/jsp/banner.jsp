1`saVXZ<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url : '${pageContext.request.contextPath}/banner/findByPage',
                datatype : "json",
                // 时间格式的处理在后台进行
                colNames : [ 'ID', '标题', '图片', '超链接', '创建时间','描述', '状态' ],
                colModel : [
                    {name : 'id',align:"center",hidden:true},
                    {name : 'title',align:"center",editable: true,editrules:{required:true}},
                    {name : 'url',align:"center",formatter:function (data) {
                            return "<img style='width: 180px;height: 80px' src='"+data+"'>"
                        },editable: true,edittype:"file",editoptions: {enctype:"multipart/form-data"}},
                    {name : 'href',align:"center",editable:true},
                    {name : 'createDate',align:"center",editable:true,editrules:{required:true},edittype: "date"},
                    {name : 'desc',align:"center",editable:true,editrules:{required:true}},
                    {name : 'status',align:"center",formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else return "冻结";
                        },editable:true,editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}}
                ],

                rowList : [ 10, 20, 30 ],
                pager : '#bannerPage',
                sortname : 'id',
                mtype : "post",
                page:1,
                rowNum : 2,
                viewrecords : true,
                sortorder : "desc",
                caption : "轮播图信息",
                autowidth: true,
                multiselect:true,
                styleUI:"Bootstrap",
                height:"200px",
                editurl: "${pageContext.request.contextPath}/banner/edit"
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerPage', {edit : true,add : true,del : true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {
                closeAfterEdit: true,
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        type:"post",
                        datatype: "json",
                        data:{bannerId:bannerId},
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },{
                closeAfterAdd:true,
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        type:"post",
                        datatype: "json",
                        data:{bannerId:bannerId},
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },{
                closeAfterDel:true
            });
    })
    //导出轮播图信息
    $("#outBanner").click(function () {
        $.ajax({
            url:"${pageContext.request.contextPath}/banner/outBannerInformation",
            type:"post",
            datatype: "json",
            success:function (data) {
                if(data.status=="200"){
                    alert("下载成功");
                }
            }
        });
    });
    //Excel模板下载
    $("#outBannerModel").click(function () {
        $.ajax({
            url:"${pageContext.request.contextPath}/banner/outBannerModel",
            type:"post",
            datatype: "json",
            success:function (data) {
                if(data.status=="200"){
                    alert("下载成功");
                }
            }
        });
    });
</script>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>
    <li><a id="outBanner">导出轮播图信息</a></li>
    <li><a id="outBannerModel">Excel模板下载</a></li>
</ul>
<div class="panel">
    <table id="bannerTable"></table>
    <div id="bannerPage" style="height: 50px"></div>
</div>