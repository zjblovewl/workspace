<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户终端信息</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    <form id="saveform" method="post" action="terminal/save.htm">
    <input type="hidden" name="id" id="id" value="${bean.id}"/>
    <input type="hidden" name="action" value="${action}">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">型号名称:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="50" value="${bean.phoneName}" type="text" style="width:200px;" name="phoneName"  id="phoneName" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">手机版本:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="50" value="${bean.phoneVersion}" type="text" style="width:200px;" name="phoneVersion"  id="phoneVersion" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">软件版本:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="50" value="${bean.softVersion}" type="text" style="width:200px;" name="softVersion"  id="softVersion" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">IMSI号:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="50" value="${bean.imsi}" type="text" style="width:200px;" name="imsi"  id="imsi" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">IMEI号:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="50" value="${bean.imei}" type="text" style="width:200px;" name="imei"  id="imei" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">登录帐号:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="5" value="${bean.loginName}" type="text" style="width:200px;" name="loginName"  id="loginName" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">所在地:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="50" value="${bean.ownship}" type="text" style="width:200px;" name="ownship"  id="ownship" validType=""></input>
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