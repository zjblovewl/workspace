<%@page import="com.tyunsoft.base.utils.Read"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
	String deptEnabled = Read.getMsg("system.dept.enabled");
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>

<script type="text/javascript" src="js/window.js"></script>
<title>用户列表</title>
</head>
<script type="text/javascript">
(function($){
   $(function(){
	  $('#icon-add').click(function(){
		myself.location('user/tosave.htm?m=${m}&deptId=${deptId}');
	  });	

	  $('#icon-edit').click(function(){
		var checks = $('#userdg').datagrid('getChecked');
		if(checks.length == 0){
			myself.msgShow('提示','请选择需要修改的用户!');
		}else if(checks.length > 1){
			myself.msgShow('提示','请选择唯一的用户进行修改!');
		}else{
			myself.location('user/tosave.htm?m=${m}&userId='+checks[0].userId);
		}
	  });

	  $('#icon-remove').click(function(){
		  var checks = $('#userdg').datagrid('getChecked');
		  if(checks.length == 0){
		 	 myself.msgShow('提示','请选择需要删除的用户!');
		 	 return;
		  }
		  var userIds = '';
		  for(var i=0;i<checks.length;i++){
 			 userIds += checks[i].userId + ',';
		  }
		  userIds = userIds.substring(0,userIds.length-1);
		  $.messager.confirm('删除提示', '确定要删除所选的用户吗?', function(r){
		        if (r){
		        	$.ajax({
		        		url:'user/delete.htm',
		        		data:'userIds='+userIds,		
		        		success:function(data){
		        			if(myself.ajaxCheck(data)){
		        				if(data=='true')
			        			{
				        			myself.msgShow('提示','成功删除用户!');
			        				$('#userdg').datagrid('reload');
			        			}else{
			        				myself.msgShow('提示','删除用户失败,请尝试!');
				        		}
			        		}
		        		},
		        		cache:false				
		        	});
		        }
		    });
	  });

      //授权
	  $('#icon-role').click(function(){
		  var checks = $('#userdg').datagrid('getChecked');
			if(checks.length == 0){
				myself.msgShow('提示','请选择需要授权的用户!');
			}else if(checks.length > 1){
				myself.msgShow('提示','请选择唯一的用户进行授权操作!');
			}else{
				$.window({
					winId:"role-list-win",  
			        title:"用户授权",
			        url:'user/toUserRole.htm?m=${m}&userId='+checks[0].userId,
			        height:350,  
			        width:300,     
			        onClose:function(){
			        	$('#userdg').datagrid('reload');
				    }, 
			        toolbar:[{text:'保存',id:"icon-save",iconCls:"icon-save",handler:closeHandler}]  
			 	}); 
			}
		 
	  });

	  //初始化密码
	  $('#icon-reload').click(function(){
		  var checks = $('#userdg').datagrid('getChecked');
		  if(checks.length == 0){
			 myself.msgShow('提示','请选择需要初始化密码的用户!');
		  }else{
			  var userIds = '';
			  for(var i=0;i<checks.length;i++){
	 			 userIds += checks[i].userId + ',';
			  }
			  userIds = userIds.substring(0,userIds.length-1);
			  $.messager.confirm('提示', '确定要初始化所选用户的密码吗?', function(r){
			        if (r){
			        	$.ajax({
			        		url:'user/resetPswd.htm',
			        		data:'userIds='+userIds,		
			        		success:function(data){
			        			if(myself.ajaxCheck(data)){
				        			if(data=='true')
				        			{
					        			myself.msgShow('提示','密码初始化成功!');
				        				//$('#userdg').datagrid('reload');
				        			}else{
				        				myself.msgShow('提示','密码初始化失败,请尝试!');
					        		}
			        			}
			        		},
			        		cache:false				
			        	});
			        }
			    });
		  }
	   });

	   $('#queryBtn').click(function(){
		   $('#userdg').datagrid('load',{userId:$.trim($('#userId').val()),userName:$.trim($('#userName').val()),town:$.trim($('#town').val()),village:$.trim($('#village').val())});
	   });
		
	   $('#icon-function').click(function(){
		   location.href = 'user/exportUser.htm';
	   });
   });
})(jQuery);

function closeHandler(){
	$('#userdg').datagrid('reload');
}
</script>
<body>
<table id="userdg" class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:700px;height:250px"
			data-options="rownumbers:true,fit:true,fitColumns:true,pagination:true,
				url:'user/list.htm?deptId=${deptId}',striped:true,title:false,border:false,singleSelect:false,toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="checkbox:true,width:60"></th>
				<th data-options="field:'userId',width:150">用户名</th>
				<th data-options="field:'userName',width:150">真实姓名</th>
				<th data-options="field:'userPhone',width:100">手机号码</th>
				<th data-options="field:'town',width:100">镇街/社区</th>
				<th data-options="field:'village',width:100">村/小区</th>
				<th data-options="field:'deptName',width:200">所属部门</th>
				<th data-options="field:'roleNames',width:250">角色</th>
			</tr>
		</thead>
</table>
<div id="tb" style="padding:5px;height:auto">
	${functionStr}
	<div>
		用户名: <input class="easyui-validatebox" id="userId" type="text" name="userId" maxlength="64" style="width: 100px"/>
		真实姓名: <input class="easyui-validatebox" id="userName" type="text" name="userName" maxlength="64" style="width: 100px"/>
		镇街/社区: <input class="easyui-validatebox" id="town" type="text" name="town" maxlength="64" style="width: 100px"/>
		村/小区: <input class="easyui-validatebox" id="village" type="text" name="village" maxlength="64" style="width: 100px"/>
		<a href="javascript:;" id="queryBtn" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	</div>
</div>
</body>
</html>