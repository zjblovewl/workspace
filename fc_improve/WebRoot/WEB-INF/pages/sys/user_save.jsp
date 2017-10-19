<%@page import="com.tyunsoft.base.utils.Read"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%
	String deptEnabled = Read.getMsg("system.dept.enabled");
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>用户新增/修改</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		//保存
		$('#icon-save').click(function(){
			$('#ff').form('submit',{
				   success : function(data) {
				      if(data == 'true'){
						 myself.location('user/search.htm?m=${m}&deptId=${deptId}'); 
				      }
				   }
			   });
		});
		//返回
		$('#icon-back').click(function(){
			myself.location('user/search.htm?m=${m}&deptId=${deptId}');
		});

		//角色数据赋值
		setTimeout(function(){
			var values = '${user.roleIds}'.split(',');
			$('#roleIds').combobox('setValues',values);
		},500);
		
	});
})(jQuery);
function changeHandler(node){
	$('#deptId').val(node.id);
}


</script>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
        <div id="tb" style="padding: 5px; height: auto; " class="datagrid-toolbar">
			<div>
				<a href="javascript:;" class="easyui-linkbutton" id="icon-save" iconCls="icon-save" plain="true">保存</a>
				<a href="javascript:;" class="easyui-linkbutton" id="icon-back" iconCls="icon-back" plain="true">返回</a>
			</div>
		</div>
		<div style="padding:10px 0 10px 60px">
	    <form id="ff" method="post" action="user/save.htm">
	    	<input type="hidden" name="operator" value="${operator}" />
	    	<table class="edit-table" style="width:90%;line-height:30px;">
	    		<tr>
	    			<td class="edit-table-header">用户名:</td>
	    			<td id="content-td"><input class="easyui-validatebox" id="userId" <c:if test="${operator=='edit'}"> readonly="readonly"</c:if> value="${user.userId}" type="text" style="width:195px" name="userId" maxlength="20" data-options="required:true"  validType="name"></input></td>
	    			<td class="edit-table-header">真实姓名:</td>
	    			<td>
	    				<input class="easyui-validatebox" value="${user.userName}" type="text" style="width:195px" name="userName" maxlength="20" data-options="required:true"  validType="name"></input>
	    			</td>	
	    		</tr>
	    		<tr>
	    			<%
					if("true".equals(deptEnabled))
					{
					%>
	    			<td class="edit-table-header">所属部门:</td>
	    			<td>
	    				
		    				<input type="hidden" name="deptId" id="deptId" value="${user.deptId}">
		    				<input id="deptName" name="deptName" value="${user.deptId == '0' ? '' : user.deptId}" class="easyui-combotree" data-options="url:'cache/deptTree.htm',valueField:'id',textField:'text',required:true,onSelect:changeHandler,editable:false,panelHeight:300,width:200">
	    			</td>
	    			<%
	    				}
						else
    				{
    				%>
    					<td align="right" colspan="2">&nbsp;</td>
    					
    				<%
    				}
    				%>
    				<td class="edit-table-header">角色:</td>
	    			<td>
	    				<input id="roleIds" name="roleIds" class="easyui-combobox" data-options="url:'role/list.htm',valueField:'roleId',textField:'roleName',required:true,editable:false,multiple:true,panelHeight:'auto',width:200"><font color="red">(可多选)</font>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="edit-table-header">手机号码:</td>
	    			<td>
	    				<input class="easyui-validatebox" value="${user.userPhone}" type="text" style="width:195px" name="userPhone" maxlength="20"></input>
	    			</td>
	    			<td class="edit-table-header">镇街/社区:</td>
	    			<td>
	    				<input class="easyui-validatebox" value="${user.town}" type="text" style="width:195px" name="town" maxlength="20"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="edit-table-header">村/小区:</td>
	    			<td colspan="3">
	    				<input class="easyui-validatebox" value="${user.village}" type="text" style="width:195px" name="village" maxlength="20"></input>
	    			</td>
	    		</tr>
	    		<c:if test="${operator=='edit'}">
		    		<tr style="display:none">
		    			<td class="edit-table-header">在职状态:</td>
		    			<td colspan="3">
		    				<select class="easyui-combobox" id="isOnJob" name="isOnJob" data-options="editable:false,width:200,required:true,panelHeight:'auto'">
								<option value="0" <c:if test="${user.isOnJob=='0'}">selected</c:if>>在职</option>
								<option value="1" <c:if test="${user.isOnJob=='1'}">selected</c:if>>已离职</option>
							</select> 
						</td>
		    		</tr>
	    		</c:if>
	    	</table>
	    </form>
	    </div>
	</div>
</body>
</html>