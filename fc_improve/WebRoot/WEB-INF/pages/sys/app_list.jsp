<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>桌面应用List</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		$('#icon-add').click(function(){
			myself.location('app/toSave.htm?m=${m}');
		});

		$('#icon-edit').click(function(){
			var checks = $('#dg').datagrid('getChecked');
			if(checks.length == 0){
				myself.msgShow('提示','请选择需要修改的应用!');
			}else{
				myself.location('app/toSave.htm?m=${m}&appId='+checks[0].appId);
			}
		});

		$('#icon-remove').click(function(){
			  var checks = $('#dg').datagrid('getChecked');
			  if(checks.length == 0){
			 	 myself.msgShow('提示','请选择需要删除的应用!');
			 	 return;
			  }
			  $.messager.confirm('删除提示', '确定要删除所选的应用吗?', function(r){
			        if (r){
			        	$.ajax({
			        		url:'app/delete.htm',
			        		data:'appId='+checks[0].appId,		
			        		success:function(data){
			        			if(myself.ajaxCheck(data)){
			        				if(data=='true')
				        			{
					        			myself.msgShow('提示','成功删除应用!');
				        				$('#dg').datagrid('reload');
				        			}else{
				        				myself.msgShow('提示','删除应用失败,请重试!');
					        		}
				        		}
			        		},
			        		cache:false				
			        	});
			        }
			    });
		  });
		
	});
})(jQuery);
</script>
<body>
<table id="dg" class="easyui-datagrid"
			data-options="rownumbers:true,fit:true,fitColumns:true,pagination:true,
				url:'app/list.htm',title:false,border:false,singleSelect:true,toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="checkbox:true,width:60"></th>
				<th data-options="field:'appName',width:150">应用名称</th>
				<th data-options="field:'appLink',width:150">应用链接</th>
				<th data-options="field:'appHeight',width:70">应用高度</th>
				<th data-options="field:'appIsSmallStr',width:70">是否可缩小</th>
				<!-- 
				<th data-options="field:'appIcon',width:200">应用图标</th>
				<th data-options="field:'appCreator',width:250">创建者</th>
				 -->
				<th data-options="field:'appCreateDate',width:200">创建日期</th>
			</tr>
		</thead>
</table>
<div id="tb" style="padding:5px;height:auto">
	${functionStr}
</div>
</body>
</html>