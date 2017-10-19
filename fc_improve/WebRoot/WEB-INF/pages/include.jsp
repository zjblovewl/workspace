<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tyunsoft.base.utils.PageUtil"%>
<base href="<%=PageUtil.getBasePath(request) %>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9"/>
<link rel="stylesheet" type="text/css" href="css/default.css"/>
<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/themes/icon.css" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/easyloader.js"></script>
<script type="text/javascript" src="js/sys.js"></script>
<script type="text/javascript" charset="gbk">
   window.UEDITOR_HOME_URL = "<%=PageUtil.getBasePath(request) %>plugins/ueditor/";//编辑器项目路径
   
   var BASE_PATH = '<%=PageUtil.getBasePath(request) %>';
</script>

