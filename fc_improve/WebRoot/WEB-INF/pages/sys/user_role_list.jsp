<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户选择角色列表</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		 $('#icon-save').click(function() {
	     	    var rows = $('#dg').datagrid('getSelections');
	  			var roleIds = '';
	  			for(var i=0; i<rows.length; i++){
	  				roleIds += rows[i].roleId + ",";
	  			}
	  			$('#roleIds').val(roleIds);
	  			$('#roleForm').form('submit',{
	  				   success : function(data) {
	  				      if(data == 'true'){
	  						 closeWindow();
	  				      }
	  				   }
	  		    });
		  });
		
	});
	
})(jQuery);

function loadSuccessHandler(){
	var rows = $('#dg').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(rows[i].checked)
		{
			$('#dg').datagrid('selectRecord',rows[i].roleId);
		}
	}
}
</script>
<body style="padding: 0px;margin:0px;background:#ffffff">
<div class="easyui-panel" data-options="fit:true,border:false">
<form id="roleForm" method="post" action="user/saveUserRole.htm">
<input type="hidden" name="userId" id="userId" value="${userId}"/>
<input type="hidden" name="roleIds" id="roleIds"/>
</form>
<table id="dg" class="easyui-datagrid" style="border: 0px;height:auto"
	data-options="rownumbers:true,idField:'roleId',singleSelect:false,fixColumns:true,
	url:'user/listUserRole.htm?userId=${userId}',title:false,onLoadSuccess:loadSuccessHandler">
	<thead>
		<tr>
			<th data-options="field:'roleId',checkbox:true,checked:true">选择</th>
			<th data-options="field:'roleName',width:210">角色名称</th>
		</tr>
	</thead>
</table>
</div>
</body>
</html>