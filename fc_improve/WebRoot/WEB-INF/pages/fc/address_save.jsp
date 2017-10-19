<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>联络人管理</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    <form id="saveform" method="post" action="address/save.htm">
    <input type="hidden" name="id" id="id" value="${bean.id}"/>
    <input type="hidden" name="action" value="${action}">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">镇街/社区:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="64" value="${bean.town}" type="text" style="width:200px;" name="town"  id="town"></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">村/小区:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="64" value="${bean.village}" type="text" style="width:200px;" name="village"  id="village"></input>
		    			</td>
		    		</tr>
		    		<tr style="display:none">
		    			<td width="10%" nowrap="nowrap">:</td>
		    			<td>
		    					<input class="easyui-validatebox"  value="${bean.addressType}" type="text" style="width:200px;" name="addressType"  id="addressType"></input>
		    			</td>
		    		</tr>
    	</table>
    </form>
    </div>
</body>
</html>
<script type="text/javascript">
</script>