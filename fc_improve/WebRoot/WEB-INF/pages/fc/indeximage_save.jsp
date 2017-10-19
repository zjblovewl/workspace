<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页图片</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    <form id="saveform" method="post" action="indeximage/save.htm">
    <input type="hidden" name="id" id="id" value="${bean.id}"/>
    <input type="hidden" name="action" value="${action}">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">首页图片:</td>
		    			<td>
		    					<input class="easyui-validatebox" type="file" style="width:200px;" name="imagePathFile"  id="imagePathFile"></input>
		    					<div style="width:200px">
		    					<c:if test="${bean.imagePath != '' && bean.imagePath != null}">
									<img src="${bean.imagePath}"/>
		    					</c:if>
		    					</div>	
		    			</td>
		    		</tr>
		    		<tr style="display:none">
		    			<td width="10%" nowrap="nowrap">点击路径:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="127" value="${bean.clickUrl}" type="text" style="width:200px;" name="clickUrl"  id="clickUrl"></input>
		    			</td>
		    		</tr>
		    		<tr style="display:none">
		    			<td width="10%" nowrap="nowrap">内容:</td>
		    			<td>
		    			   		<input type="hidden" name="content"  id="content"/>
		    					<script id="contentEditor" type="text/plain" style="width:800px;height:320px;">${bean.content}</script>
		    			</td>
		    		</tr>
		    		<tr style="display:none">
		    			<td width="10%" nowrap="nowrap">创建日期:</td>
		    			<td>
		    					<input type="text" class="easyui-datetimebox"  name="createDate"  id="createDate" value="${bean.createDate}"/>
		    			</td>
		    		</tr>
		    		<tr style="display:none">
		    			<td width="10%" nowrap="nowrap">创建人:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="16" value="${bean.createUser}" type="text" style="width:200px;" name="createUser"  id="createUser"></input>
		    			</td>
		    		</tr>
    	</table>
    </form>
    </div>
</body>
</html>
<script type="text/javascript">
    var contentEditor = UE.getEditor('contentEditor');
</script>