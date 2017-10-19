<%@page import="com.tyunsoft.base.common.CacheFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tyunsoft.base.utils.PageUtil"%>
<%@page import="com.tyunsoft.base.utils.Read"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9"/>
<title><%=CacheFactory.getInstance( ).getSetter( "system.name" ) %> <%=CacheFactory.getInstance( ).getSetter( "system.version" )  %></title>
<base href="<%=PageUtil.getBasePath(request) %>" />
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<style type="text/css">
*{ padding:0; margin:0; font-family: "lucida Grande",Verdana,"Microsoft YaHei";}

.zhihuishuiwuLogin{ width:100%; height100%; background:url(images/loginBg.png) no-repeat; background-size: cover; filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/loginBg.png',
    sizingMethod='scale'); }

.loginMain{ position: absolute; left:30%; top:25%; z-index: 100; width:40%; height:auto; min-width: 460px; background:#fff; border-radius: 0.5em; -webkit-border-radius: 0.5em; -ms-border-radius: 0.5em; -moz-border-radius: 0.5em;}
.loginTitle{ width:100%; text-align:center; color:#fff; padding:10px 0; letter-spacing: 2px; -webkit-font-smoothing: subpixel-antialiased; background:url(images/loginTitleBg.png) no-repeat; background-size: 100% 100%; }

.loginMain label{ color:#e61717; width:4em; text-align:left; display: inline-block; }
.loginMain input{ border:1px solid #d8d8d8; height:2.5em; width:15em; padding-left:5px; }
<!--[if IE]> 
.loginTitle{ background:url(images/ieloginTitleBg.png) no-repeat; }
<![endif]-->
</style>
<script type="text/javascript">
$(function(){
	var viewHeight = $(document).height();
	$(".zhihuishuiwuLogin").css({
		height: viewHeight
	});
	if(/msie/.test(navigator.userAgent.toLowerCase())) {
		$(".loginTitle").css({background:"url(images/ieloginTitleBg.png) repeat-x"})
		} 
})
</script>
</head>

<body>
<form id="loginForm" method="post" action="free/login.htm">
<div class="zhihuishuiwuLogin">
	<div style="text-align:center;padding-top:40px;">
		<img src="images/logo-td.png"/>
	</div>
	<!-- <%=CacheFactory.getInstance( ).getSetter( "system.name" ) %> -->
	<div class="loginMain">
		<p class="loginTitle">系统登录</p>
		<div style="padding:5%; width:90%; text-align:center;">
		<div style="margin:10px 0;">
			<label>帐号</label>
			<input type="text" name="userId" style="width:300px;"></input>
		</div>
		<div style="margin:10px 0;">
			<label>密码</label>
			<input type="password" name="password" style="width:300px;"></input>
		</div>
		
		<div style="margin:10px 0;">
			<label style="visibility: hidden;">&nbsp;</label>
			<div style="background:#e61717; width:12.9em; display:inline-block; color:#fff; height:2.5em; line-height: 2.5em; border:0;cursor:pointer;" onclick="login()">立即登录</div>
		</div>
		<div style="padding:5px 0;text-align: center;color: red;" id="showMsg">${error}</div>
		</div>
	</div>
</div>
</form>
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
