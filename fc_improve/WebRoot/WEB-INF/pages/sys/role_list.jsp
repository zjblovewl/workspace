<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>角色信息列表</title>
</head>
<script type="text/javascript">
(function($){
   $(function(){
       $("#icon-add").click(function(){
			myself.location('role/tosave.htm?m=${m}');
       });

	   $('#icon-edit').click(function(){
		   var row = $('#roledg').datagrid('getSelected');
    	   if(row){
    		   myself.location("role/tosave.htm?m=${m}&roleId="+row.roleId);
           }else{
        	   myself.msgShow("提示","请选择需要修改的角色!","info");
           }
	   });
	   	
       $("#icon-remove").click(function(){
    	   var row = $('#roledg').datagrid('getSelected');
			if (row){
				$.messager.confirm('删除提示', '确定删除所选的角色吗?', function(r){
			        if (r){
			        	$.ajax({
			        		url:'role/delete.htm',
			        		data:'roleId='+row.roleId,		
			        		success:function(data){
			        			if(data=='true')
			        			{
			        				$('#roledg').datagrid('reload');
			        			}
			        		},
			        		cache:false				
			        	});
			        }
			    });
			}else{
				myself.msgShow("提示","请选择需要删除的角色!","info");
			}
       });

       $('#icon-role').click(function(){
    	   var row = $('#roledg').datagrid('getSelected');
    	   if(row){
    		   myself.location("role/toSetterRole.htm?m=${m}&roleId="+row.roleId+'&roleName='+encodeURIComponent(row.roleName));
           }else{
        	   myself.msgShow("提示","请选择需要分配权限的角色!","info");
           }
       });

       $('#icon-function').click(function(){
    	   var row = $('#roledg').datagrid('getSelected');
    	   if(row){
    		   myself.location("role/toSetterAppRole.htm?m=${m}&roleId="+row.roleId+'&roleName='+encodeURI(row.roleName));
           }else{
        	   myself.msgShow("提示","请选择需要分配应用权的角色!","info");
           }
       });

    });
})(jQuery);
</script>
<body>
	<table id="roledg" class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:700px;height:250px"
			data-options="rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'role/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'roleName',width:200">角色名称</th>
				<th data-options="field:'roleRemark',width:500">角色说明</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		${functionStr}
		
	</div>
	
</body>
</html>