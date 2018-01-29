<%@page import="com.tyunsoft.base.common.CacheFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.tyunsoft.base.utils.PageUtil"%>
<%@page import="com.tyunsoft.base.utils.Read"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9"/>
<title><%=CacheFactory.getInstance( ).getSetter( "system.name" ) %> <%=CacheFactory.getInstance( ).getSetter( "system.version" )  %></title>
<base href="<%=PageUtil.getBasePath(request) %>" />
<link rel="stylesheet" type="text/css" href="js/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="js/themes/gray/easyui.css"/>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/easyloader.js"></script>
</head>

<body style="background: url('images/loginBg.png');">
<div id="loginWin" class="easyui-window" title="欢迎使用<%=CacheFactory.getInstance( ).getSetter( "system.name" ) %>" data-options="width:390,height:188" style="width:350px;height:188px;padding:5px;"
   minimizable="false" maximizable="false" resizable="false" collapsible="false" draggable="false" closable="false">
    <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding:3px;background:#fff;border:1px solid #ccc;overflow: hidden;">
		        <form id="loginForm" method="post" action="free/login.htm">
		            <div style="padding:5px 0;">
		                <label for="login">帐号:</label>
		                <input type="text" name="userId" style="width:300px;"></input>
		            </div>
		            <div style="padding:5px 0;">
		                <label for="password">密码:</label>
		                <input type="password" name="password" style="width:300px;"></input>
		            </div>
		             <div style="padding:5px 0;text-align: center;color: red;" id="showMsg">${error}</div>
		        </form>
            </div>
            <div region="south" border="false" style="text-align:right;padding:5px 0;">
              <nobr>
              <!-- 
              	<a class="easyui-linkbutton" iconCls="icon-choose-user" href="<%=PageUtil.getBasePath(request) %>free/yklogin.htm">在线申报</a>
                <span style="width:45px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
               -->
                <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="login()">登录</a>
                <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cleardata()">重置</a>
              </nobr>  
            </div>
    </div>
</div>
</body>
<script type="text/javascript">
document.onkeydown = function(e){
    var event = e || window.event;  
    var code = event.keyCode || event.which || event.charCode;
    if (code == 13) {
        login();
    }
}
$(function(){
    $("input[name='userId']").focus();
});

function cleardata(){
    $('#loginForm').form('clear');
}
function login(){
     if($("input[name='userId']").val()=="" || $("input[name='password']").val()==""){
         $("#showMsg").html("帐号或密码为空，请输入!");
         $("input[name='userId']").focus();
    }else{
         $('#loginForm').submit();
        } 
}
</script>
</html>