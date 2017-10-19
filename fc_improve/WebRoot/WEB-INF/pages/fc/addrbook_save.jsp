<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯录</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    <form id="saveform" method="post" action="addrbook/save.htm">
    <input type="hidden" name="id" id="id" value="${bean.id}"/>
    <input type="hidden" name="action" value="${action}">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">姓名:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="32" value="${bean.name}" type="text" style="width:200px;" name="name"  id="name" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">部门:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="128" value="${bean.department}" type="text" style="width:200px;" name="department"  id="department" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">手机号:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="64" value="${bean.phoneNum}" type="text" style="width:200px;" name="phoneNum"  id="phoneNum" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">镇街/社区:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="64" value="${bean.town}" type="text" style="width:200px;" name="town"  id="town" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">村/小区:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="64" value="${bean.village}" type="text" style="width:200px;" name="village"  id="village" validType=""></input>
		    			</td>
		    		</tr>
    	</table>
    </form>
    </div>
</body>
</html>
<script type="text/javascript">
  
  //加入校验功能
  function showProgressDialog(){
	if($("#progressDialog").size() == 0){
		var s = $("<div id=\"progressDialog\" style=\"width:400px;height:300px;z-index:100000;text-align:center; border:0;\">正在处理中。。。</div>");
		$('body').append(s);
	}
	$("#progressDialog").dialog({
		modal: true,
		closeOnEscape: false,
		draggable: false,
		resizable: false,
		hide: 'slide',
		forceClose:true,
		forceOpen:true,
		forceDestroy:true,
		zIndex:100000,
		closable: false,
		width: 135,
		height: 30,
		title: ""
	});
}
/**
 * @author Grahor
 * 操作成功后 销毁模态窗口
 */
top.destroyProgressDialog = function(){
	try{
		$("#progressDialog").dialog("destroy");
	}catch(e){
		
	}
}
top.formValidate = function(){
	var r = $("#saveform").form("validate");
	if (r) {
		showProgressDialog();
	}
	return r;
}
</script>