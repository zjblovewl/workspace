<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>部门管理操作说明</title>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
	    	<table style="width:90%;line-height:30px;padding-left:20px;padding-top:20px">
	    		<tr>
	    			<td><b>部门管理操作帮助</b></td>
	    		</tr>
	    		<tr style="padding-left:15px;">
	    			<td>1、选择树形部门菜单显示该部门的详细信息，此时可以对部门信息进行修改保存!</td>
	    		</tr>
	    		<tr>
	    			<td>2、选择树形部门菜单，点击“增下级”按钮，可以创建下级部门!</td>
	    		</tr>
	    		<tr>
	    			<td>3、选择树形部门菜单前的复选框，点击“删除”按钮，可以删除部门，部门被删除后，部门下所属人员进行无部门状态，<font color="red">请谨慎执行删除操作</font>!</td>
	    		</tr>
	    	</table>
</div>	    
</body>
</html>