<%@page import="com.tyunsoft.base.utils.Read"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String deptEnabled = Read.getMsg("system.dept.enabled");
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>用户管理框架</title>
</head>
<script type="text/javascript">
$(function() {
	$("#tree").tree( {
		url : 'dept/list.htm',
		onClick : doMenuClick
	});
   
	var addDeptId = '0'; 
	var addDeptName = '无';
	
	function doMenuClick(node) {
		var elIframe = $("#edit").get(0);
		addDeptId = node.id;
		addDeptName = node.text;
		elIframe.src = "user/search.htm?m=${m}&deptId="+node.id;
	}
	
	// TIP: 配合body解决页面跳动和闪烁问题
	$("body").css({visibility:"visible"});
});

</script>
<body class="easyui-layout" style="visibility:hidden;">
	<%
	if("true".equals(deptEnabled))
	{
	%>
	<div region="west" title="部门树" icon="icon-forward" style="width:210px;overflow:auto;" split="true" border="false" >
	   	<form id="form" method="post" >	   		   	
			<ul id="tree"></ul>
	    </form>
    </div>
    <%} %>
    <div region="center" style="overflow:hidden;" border="false">
		<iframe id="edit" src="user/search.htm?m=${m}" scrolling="no" frameborder="0" style="width:100%;height:100%;"></iframe>
    </div>
</body>
</html>