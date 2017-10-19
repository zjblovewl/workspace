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
<link rel="stylesheet" type="text/css" href="css/login-black.css"/>
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<style type="text/css">
body{
	background:url('images/login-black-bg.png');
	font-family:'微软雅黑';
	font-size:12px;
	color:#333;
}
</style>
</head>

<body>
<div id="loginpanelwrap">
  	
	<div class="loginheader">
    <div class="logintitle"><a href="javascript:;"><%=CacheFactory.getInstance( ).getSetter( "system.name" ) %></a></div>
    </div>

     
    <div class="loginform">
        <form id="loginForm" method="post" action="free/login.htm">
	        <div class="loginform_row">
	        <label>用户名:</label>
	        <input type="text" class="loginform_input" name="userId" />
	        </div>
	        <div class="loginform_row">
	        <label>密&nbsp;&nbsp;&nbsp;码:</label>
	        <input type="password" class="loginform_input" name="password" />
	        </div>
	        <nobr>
		        <div class="loginform_row">
		        	<input type="button" class="loginform_submit" value=" 登    录 "  onclick="login()"/>
		        </div> 
		        <div class="clear" id="showMsg" style="color: red;">
		        	${error}
		        </div>
	        </nobr>
        </form>
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