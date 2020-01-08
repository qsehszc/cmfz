<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#userTable").jqGrid({
            url:"${pageContext.request.contextPath}/user/selectPageUser",
            datatype:"json",
            colNames:["ID","手机号","密码","盐","状态","头像","姓名","法号","性别","个性签名","地区","注册时间","最后登陆时间"],
            colModel:[
                {name:"id",align:"center",hidden:true},
                {name:"phone",align:"center",editable:true,editrules:{required:true}},
                {name:"password",align:"center",editable:true,editrules:{required:true}},
                {name:"salt",align:"center",editable:true,editrules:{required:true}},
                {name:"status",align:"center",editable:true,formatter:function (data) {
                        if(data=="1"){
                            return "激活";
                        }else{
                            return "冻结";
                        }
                    },editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}},
                {name:"photo",align:"center",editable: true,formatter:function(data){
                        return "<img style='width: 180px;height: 80px' src='"+data+"'>"
                    },edittype:"file",editoptions: {enctype:"multipart/form-data"}},
                {name:"name",align:"center",editable:true,editrules:{required:true}},
                {name:"nickName",align:"center",editable:true,editrules:{required:true}},
                {name:"sex",align:"center",editable:true,editrules:{required:true}},
                {name:"sign",align:"center",editable:true,editrules:{required:true}},
                {name:"location",align:"center",editable:true,editrules:{required:true}},
                {name:"rigestDate",align:"center",editable:true,editrules:{required:true},edittype: "date"},
                {name:"lastLogin",align:"center",editable:true,editrules:{required:true},edittype: "date"},
            ],
            sortname : 'id',
            mtype : "post",
            autowidth:true,
            pager:"#userPage",
            rowNum:3,
            rowList:[3,5,10,20],
            viewrecords:true,
            caption : "用户信息",
            multiselect:true,
            styleUI:"Bootstrap",
            height:"500px",
            editurl:"${pageContext.request.contextPath}/user/saveUser"
        }).jqGrid("navGrid","#userPage",{edit: true, add: true, del: true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {closeAfterEdit: true},
            {closeAfterAdd: true,
                afterSubmit:function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/uploadUser",
                        type:"post",
                        datatype: "json",
                        // 发送添加图片的id至controller
                        data:{userId:userId},
                        fileElementId:"photo",
                        success:function (data) {
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    //防止页面报错
                    return postData;
                }
            },
            {closeAfterDel: true}
        );
    });

</script>

<div class="page-header">
    <h4>用户管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>

</ul>
<div class="panel">
    <table id="userTable"></table>
    <div id="userPage" style="height: 30px"></div>
</div>