<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>角色应用权分配</title>
</head>
<script type="text/javascript">
(function($){
   $(function(){
	 myself.addAppToRole = function(appId){
		save('add',appId);
     };

     myself.cancelAppToRole = function(appId){
		save('edit',appId);
     };

     function save(flag,appId){
    	 $.ajax({
     		url:'role/setterAppRole.htm',
     		data:'appId='+appId+'&flag='+flag+'&roleId=${roleId}',		
     		success:function(data){
     			if(myself.ajaxCheck(data)){
     				if(data=='true')
	        			{
	        				$('#appdg').datagrid('reload');
	        				$('#roleappdg').datagrid('reload');
	        			}else{
		        			if(flag == 'add'){
		        				myself.msgShow('提示','应用分配失败,请重试!');
			        		}else{
			        			myself.msgShow('提示','分配应用取消失败,请重试!');
				        	}
		        		}
	        		}
     		},
     		cache:false				
     	});
     }
   });
})(jQuery);
function addAppToRole(val,row){
	return "<a href=\"javascript:myself.addAppToRole('"+val+"');\">分配</a>"; 	
}

function cancelAppToRole(val,row){
	return "<a href=\"javascript:myself.cancelAppToRole('"+val+"');\">取消分配</a>";
}
</script>
<body class="easyui-layout">
<form action="role/setterRole.htm?m=${m}" method="post">
<input type="hidden" name="roleId" value="${roleId}"/>
<div region="north" style="overflow:hidden;" split="false" border="false" >
	<div class="datagrid-toolbar" style="padding:5px;">
		<a href="role/search.htm?m=${m}" class="easyui-linkbutton" id="icon-back" data-options="iconCls:'icon-back',plain:true">返回</a>
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">角色名称:${roleName}</font></span>
	</div>
</div>
<div region="center" style="overflow:auto;padding:0px;" border="false">
<table width="100%">
  <tr>
  	<td style="width:50%" valign="top">
		<table id="appdg" class="easyui-datagrid"
					data-options="rownumbers:true,title:'待授权应用',border:false,singleSelect:true,checkOnSelect:false,selectOnCheck:false,
					idField: 'appId',fitColumns:true,pagination:true,url:'app/listForRole.htm?flag=0&roleId=${roleId}'">
				<thead>
					<tr>
						<th data-options="field:'appName',width:150">应用名称</th>
						<th data-options="field:'appDesc',width:300">应用说明</th>
						<th data-options="field:'appId',width:80,formatter:addAppToRole">操作</th>
					</tr>
				</thead>
		</table>
  	</td>
  	<td style="width:50%" valign="top">
		<table id="roleappdg" class="easyui-datagrid"
					data-options="rownumbers:true,title:'已授权应用',border:false,singleSelect:true,checkOnSelect:false,selectOnCheck:false,
					idField: 'appId',fitColumns:true,pagination:true,url:'app/listForRole.htm?flag=1&roleId=${roleId}'">
				<thead>
					<tr>
						<th data-options="field:'appName',width:150">应用名称</th>
						<th data-options="field:'appDesc',width:300">应用说明</th>
						<th data-options="field:'appId',width:80,formatter:cancelAppToRole">操作</th>
					</tr>
				</thead>
		</table>
  	</td>
  </tr>
</table>
</div>
</form>
</body>
</html>