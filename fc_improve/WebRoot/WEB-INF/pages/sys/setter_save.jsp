<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统设置</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    <form id="saveform" method="post" action="setter/save.htm">
    <input type="hidden" name="id" id="setterId" value="${bean.id}"/>
    <input type="hidden" name="action" value="${action}">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">设置名称:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="127" value="${bean.setterName}" type="text" style="width:250px;" name="setterName"  id="setterName" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">设置值:</td>
		    			<td>
		    					<textarea style="width:250px;height:80px"  name="setterValue"  id="setterValue">${bean.setterValue}</textarea>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">设置说明:</td>
		    			<td>
		    					<textarea style="width:250px;height:80px"  name="setterRemark"  id="setterRemark">${bean.setterRemark}</textarea>
		    			</td>
		    		</tr>
    	</table>
    </form>
    </div>
</body>
</html>
<script type="text/javascript">
</script>