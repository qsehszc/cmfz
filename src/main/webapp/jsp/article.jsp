<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid({
            url:"${pageContext.request.contextPath}/article/selectPageArticle",
            datatype:"json",
            colNames:["ID","标题","封面","内容","创建时间","出版时间","状态","上师id","操作"],
            colModel:[
                {name:"id",align:"center",hidden:true},
                {name:"title",align:"center",editable:true,editrules:{required:true}},
                {name:"img",align:"center",editable: true,formatter:function(data){
                        return "<img style='width: 180px;height: 80px' src='"+data+"'>"
                    },edittype:"file",editoptions: {enctype:"multipart/form-data"}},
                {name:"content",align:"center",editable:true,editrules:{required:true}},
                {name:"createDate",align:"center",editable:true,editrules:{required:true},edittype: "date"},
                {name:"publishDate",align:"center",editable:true,editrules:{required:true},edittype: "date"},
                {name:"status",align:"center",editable:true,formatter:function (data) {
                        if(data=="1"){
                            return "激活";
                        }else{
                            return "冻结";
                        }
                    },editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}},
                {name:"guruId",align:"center"},
                {name:"xx",align:"center",formatter:function (cellvalue, options, rowObject) {
                        var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('"+rowObject.id+"')\">修改</button>&nbsp;";
                        button+= "<button type=\"button\" class=\"btn btn-success\" onclick=\"del('"+rowObject.id+"')\">删除</button>";
                        return button;
                    }}

            ],
            sortname : 'id',
            mtype : "post",
            autowidth:true,
            pager:"#articelPage",
            rowNum:3,
            rowList:[3,5,10,20],
            viewrecords:true,
            caption : "文章信息",
            multiselect:true,
            styleUI:"Bootstrap",
            height:"500px",
        });
    });
    // 点击添加文章时触发事件
    function showArticle() {
        $("#kindForm")[0].reset();
        KindEditor.html("#editor_id","");
        $("#id").val("");
        $.ajax({
            url: "${pageContext.request.contextPath}/guru/selectAllGuru",
            datatype: "json",
            type: "post",
            success: function (data) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option = "<option value=\"0\">请选择所属上师</option>";
                data.forEach(function (guru) {
                    option += "<option value=" + guru.id + ">" + guru.name + "</option>"
                });
                $("#guru_list").html(option);
            }
        });
        $("#myModal").modal("show");
    }
    function update(id) {
        var data = $("#articleTable").jqGrid("getRowData",id);
        $("#id").val(data.id);
        $("#title").val(data.title);
        KindEditor.html("#editor_id",data.content)
        // 处理状态信息
        $("#status").val(data.status);
        var option = "";
        if(data.status=="激活"){
            option += "<option selected value=\"1\">激活</option>";
            option += "<option value=\"2\">冻结</option>";
        }else{
            option += "<option value=\"1\">激活</option>";
            option += "<option selected value=\"2\">冻结</option>";
        }
        $("#status").html(option);
        // 处理上师信息

        $.ajax({
            url: "${pageContext.request.contextPath}/guru/selectAllGuru",
            datatype: "json",
            type: "post",
            success: function (gurulist) {
                var option2 = "<option value=\"0\">请选择所属上师</option>";
                gurulist.forEach(function (guru) {
                    if (guru.id==data.guruId){
                        option2 += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                    }
                    option2 += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guru_list").html(option2);
            }
        });
        $("#myModal").modal("show");
    }
    // 文件添加及修改方法
    function sub() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/insertArticle",
            type: "post",
            data: {
                "id": $("#id").val(),
                "title": $("#title").val(),
                "content": $("#editor_id").val(),
                "status": $("#status").val(),
                "guruId": $("#guru_list").val()
            },
            datatype: "json",
            fileElementId: "inputfile",
            success: function (data) {

            }
        })
    }
    // 点击删除时触发事件
    function del(id) {
        var data = $("#articleTable").jqGrid("getRowData",id);
        $("#id").val(data.id);
        $.ajax({
            url: "${pageContext.request.contextPath}/article/deleteArticle?id="+$("#id").val(),
            datatype: "json",
            type: "post",
            success: function (gurulist) {
                $("#articleTable").trigger("reloadGrid");
            }
        });
    }
</script>


<div class="page-header">
    <h4>文章管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>文章信息</a></li>
    <li><a onclick="showArticle()">添加文章</a></li>
</ul>
<table id="articleTable"></table>
<div id="articelPage" style="height: 50px"></div>