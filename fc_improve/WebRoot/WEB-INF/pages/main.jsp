<%@page import="com.tyunsoft.base.common.CacheFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<%@page import="com.tyunsoft.base.utils.PageUtil"%>
<%@page import="com.tyunsoft.base.utils.Read"%>
<base href="<%=PageUtil.getBasePath(request) %>" />
<title><%=CacheFactory.getInstance( ).getSetter( "system.name" ) %> <%=CacheFactory.getInstance( ).getSetter( "system.version" )  %></title>
<link rel="shortcut icon" href="/favicon.ico"/>
<link rel="bookmark" href="/favicon.ico"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9"/>
<link rel="stylesheet" type="text/css" href="css/default.css"/>
<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/themes/icon.css" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/easyloader.js"></script>
<script type="text/javascript" src='js/outlook2.js'> </script>
<script type="text/javascript" src='js/sys.js'> </script>
<script type="text/javascript" src='js/window.js'> </script>
<script type="text/javascript" charset="gbk">
   window.UEDITOR_HOME_URL = "<%=PageUtil.getBasePath(request) %>plugins/ueditor/";//编辑器项目路径
</script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

<style type="text/css">
a:hover{text-decoration:none;}
</style>	
<script type="text/javascript">
var basePath = '<%=PageUtil.getBasePath(request) %>';

var _menus = ${menuString};
if(null == _menus || _menus == '')
{
	alert('页面过期，请重新登录!');
	window.top.location.href= top.basePath;
}

      $(function() {

          //修改密码
          $('#editpass').click(function() {
              //$('#w').window('open');
        	  $.window({
  				winId:"change-password-win",  
  		        title:"修改密码",
  		        url:'user/forwardChangePswd.htm',
  		        height:170,  
  		        width:300,     
  		        onClose:function(){
  			    } 
  		 	});
          });
          

          $('#btnEp').click(function() {
              serverLogin();
          });

         

          $('#loginOut').click(function() {
              $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                  if (r) {
					myself.location('free/logout.htm');
                  }
              });

          });
		/*
          $('#tabs').tabs({
        		tools:[{
        			iconCls:'icon-reload',
        			text:'刷新',
        			handler:function(){
        				$('#tabs').tabs('getSelected').panel('refresh');
        			}
        		}]
        	});*/
      });
      
      var winData;

      var callHandler;
      function callFunction(callFun){
		callHandler = callFun;
      }
      
      function closeTab()
      {
    	  $('.tabs-selected>.tabs-close').click();
      }
      
</script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 50px;
        background:url(images/top.png) left top repeat fixed;line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float: left;height:50px;padding-top:5px;padding-left:10px;"><div style="font-weight: bold;font-size: 20px;padding-top:8px;height:100%;"><%=CacheFactory.getInstance(  ).getSetter("system.name") %><span style="font-size:9px;color:red"><i><%=CacheFactory.getInstance(  ).getSetter("system.version") %></i></span></div> </span>
        <span style="float:right; padding-right:20px;" class="head">欢迎 ${userName} 
        	<a href="javascript:;" id="editpass">修改密码</a> 
        	<a href="javascript:;" id="loginOut">安全退出</a></span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer"><%=CacheFactory.getInstance( ).getSetter( "system.version.info" ) %></div>
    </div>
    <div region="west" split="true" title="导航菜单" style="width:180px;" id="west">
            <div class="easyui-accordion" fit="true" border="false">
				<!--  导航内容 -->
			</div>

    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false">
        	<div title="菜单展示" style="padding:0px;overflow:hidden;" id="home">
				<iframe src="<%=CacheFactory.getInstance().getSetter( "default.main.page.url" )%>" style="width:100%;height:100%;overflow-x: hidden" frameborder="0"/>
			</div>
		</div>
    </div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>


</body>
</html>    