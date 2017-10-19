<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户的应用信息设置</title>
</head>
<script type="text/javascript">
(function($){
	   $(function(){
		 $('#icon-cancel').click(function(){
			 closeWindow();	
	     });
 
		   
		 myself.addAppToRole = function(appId){
			save('0',appId);
	     };

	     myself.cancelAppToRole = function(appId){
			save('1',appId);
	     };

	     function save(flag,appId){
	    	 $.ajax({
	     		url:'app/cancelOrAddUserApp.htm',
	     		data:'appId='+appId+'&flag='+flag,		
	     		success:function(data){
	     			if(myself.ajaxCheck(data)){
	     				if(data=='true')
		        			{
		        				$('#appdg').datagrid('reload');
		        				$('#roleappdg').datagrid('reload');
		        			}else{
			        			if(flag == '0'){
			        				myself.msgShow('提示','添加应用失败,请重试!');
				        		}else{
				        			myself.msgShow('提示','取消应用失败,请重试!');
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
	return "<a href=\"javascript:myself.addAppToRole('"+val+"');\">添加</a>"; 	
}

function cancelAppToRole(val,row){
	return "<a href=\"javascript:myself.cancelAppToRole('"+val+"');\">取消</a>";
}
</script>
<body class="easyui-layout">
<table width="100%">
  <tr>
  	<td style="width:50%" valign="top">
		<table id="appdg" class="easyui-datagrid"
					data-options="rownumbers:true,title:'可选择应用',border:false,singleSelect:true,checkOnSelect:false,selectOnCheck:false,
					idField: 'appId',fitColumns:true,url:'app/listUserApp.htm?flag=0'">
				<thead>
					<tr>
						<th data-options="field:'appName',width:150">应用名称</th>
						<th data-options="field:'appId',width:80,formatter:addAppToRole">操作</th>
					</tr>
				</thead>
		</table>
  	</td>
  	<td style="width:50%" valign="top">
		<table id="roleappdg" class="easyui-datagrid"
					data-options="rownumbers:true,title:'已选择应用',border:false,singleSelect:true,checkOnSelect:false,selectOnCheck:false,
					idField: 'appId',fitColumns:true,url:'app/listUserApp.htm?flag=1'">
				<thead>
					<tr>
						<th data-options="field:'appName',width:150">应用名称</th>
						<th data-options="field:'appId',width:80,formatter:cancelAppToRole">操作</th>
					</tr>
				</thead>
		</table>
  	</td>
  </tr>
</table>
</body>
</html>