<%@page import="com.tyunsoft.base.common.CacheFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tyunsoft.base.utils.PageUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head id="Head1">
<base href="<%=PageUtil.getBasePath(request) %>" />
<title><%=CacheFactory.getInstance( ).getSetter( "system.name" ) %> <%=CacheFactory.getInstance( ).getSetter( "system.version" )  %></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9"/>
<link rel="stylesheet" type="text/css" href="css/default.css"/>
<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/themes/icon.css" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src='js/sys.js'> </script>
<script type="text/javascript" src='js/window.js'> </script>
<script type="text/javascript" src='js/outlook2.js'> </script>
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
<style type="text/css"> 
<!-- 
*{margin:0;padding:0;border:0;} 
body { 
font-family: arial, 宋体, serif; 
font-size:12px; 
} 
#nav { 
line-height: 24px; list-style-type: none; background:#666; 
} 
#nav a { 
display: block; width: 100px; text-align:center; 
} 
#nav a:link { 
color:#666; text-decoration:none; 
} 
#nav a:visited { 
color:#666;text-decoration:none; 
} 
#nav a:hover { 
color:#FFF;text-decoration:none;font-weight:bold; 
} 
#nav li { 
float: left; width: 100px;
} 
#nav li a:hover{ 
background:#B9D1F2; 
} 
#nav li ul { 
line-height: 27px; list-style-type: none;text-align:left; 
left: -999em; width: 150px; position: absolute; 
} 
#nav li ul li{ 
float: left; width: 150px; 
background: #F6F6F6; 
} 
#nav li ul a{ 
display: block; width: 126px;text-align:left;padding-left:24px; 
} 
#nav li ul a:link { 
color:#666; text-decoration:none; 
} 
#nav li ul a:visited { 
color:#666;text-decoration:none; 
} 
#nav li ul a:hover { 
color:#416AA3;text-decoration:none;font-weight:normal; 
background:#CEDEF2; 
} 
#nav li:hover ul { 
left: auto; 
} 
#nav li.sfhover ul { 
left: auto; 
} 
#content { 
clear: left; 
} 

input{
	border: 1px solid #ccc;
}
--> 
</style> 
<script type=text/javascript>
function menuFix() { 
var sfEls = $('#nav>li');
	for (var i=0; i<sfEls.length; i++) { 
		sfEls[i].onmouseover=function() { 
			this.className+=(this.className.length>0? " ": "") + "sfhover"; 
		}; 
		sfEls[i].onMouseDown=function() { 
			this.className+=(this.className.length>0? " ": "") + "sfhover"; 
		}; 
		sfEls[i].onMouseUp=function() { 
			this.className+=(this.className.length>0? " ": "") + "sfhover"; 
		}; 
		sfEls[i].onmouseout=function() { 
			this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"), ""); 
		}; 
		
		sfEls[i].onclick=function() { 
			this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"), ""); 
		}; 
	} 
} 
</script> 


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
          

          $('#loginOut').click(function() {
              $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                  if (r) {
					myself.location('free/logout.htm');
                  }
              });

          });
          
          $('#nav>li>a').mouseover(function(){
        	  //$(this).parent().parent().children('ul').show();
          });
          
          
          $('.menuUrl').click(function(){
        	  if($(this).attr('url') == '')
        	  {
        		  return;
        	  }
        	  
        	  addTab($(this).html(),$(this).attr('url'),$(this).attr('id'));
        	  //$(this).parent().parent().hide();
          });
          
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
        <div>
	        <span style="float: left;height:50px;padding-top:5px;padding-left:10px;"><div style="font-weight: bold;font-size: 20px;padding-top:8px;height:100%;"><%=CacheFactory.getInstance( ).getSetter( "system.name" ) %><span style="font-size:9px;color:red"><i><%=CacheFactory.getInstance( ).getSetter("system.version") %></i></span></div> </span>
	        <span style="float:right; padding-right:20px;" class="head">欢迎 ${userName} 
	        	<a href="javascript:;" id="editpass">修改密码</a> 
	        	<a href="javascript:;" id="loginOut">安全退出</a>
	         </span>
         </div>
         
    </div>
      
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer"><%=CacheFactory.getInstance( ).getSetter("system.version.info") %></div>
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden;border-top: 0px;border-left:0px;border-right:0px">
    	<!-- 顶部菜单开始 -->
         <div style="width:100%;height:25px;background:#E0ECFF;border-bottom: 1px solid #95B8E7;padding-bottom:2px">
         	<ul id="nav" style="border:0px;width:100%;padding-left:15px;"> 
			 	<c:forEach items="${menus}" var="menu">
			 		<li><a href="javascript:;" url="${menu.menuLink}" class="menuUrl"  id="${child.menuId}"  valign="middle"><img src="images/menuicons/${menu.menuIcon}" style="padding-right:3px;vertical-align:middle;"/>${menu.menuName}</a> 
						<ul style="z-index:9999"> 
							<c:forEach items="${menu.children}" var="child">
								<li><a href="javascript:;" url="${child.menuLink}" class="menuUrl"  id="${child.menuId}"><img src="images/menuicons/${child.menuIcon}" style="padding-right:5px;vertical-align:middle;"/>${child.menuName}</a></li> 
							</c:forEach>
						</ul> 
					</li>
			 	</c:forEach>
			</ul>
         </div>
         <!-- 顶部菜单结束 -->
        <div id="tabs" class="easyui-tabs"  fit="true" border="false">
        	<div title="菜单展示" style="padding:0px;overflow:hidden;" id="home">
				<iframe src="<%=CacheFactory.getInstance( ).getSetter("default.main.page.url")%>" style="width:100%;height:100%;overflow-x: hidden;" frameborder="0"/>
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