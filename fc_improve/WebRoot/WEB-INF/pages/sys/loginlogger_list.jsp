<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<title>登录日志</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		var windowWidth = 1000;
		var windowHeight = 600;
		
		$('#icon-add').click(function(){
			openSaveWindow('');
		});
		
		function openSaveWindow(id)
		{
			$.window({
   				winId:"new-loginlogger-win",  
   		        title:(id==''?'新增':'修改')+"登录日志",
   		        url:'loginlogger/toSave.htm?m=${m}&id='+id,
   		        width:windowWidth,
   		        height:windowHeight,  
   		        onClose:function(){
   		        	closeHandler();
   			    },     
   		        toolbar:[{text:'保存',id:"icon-save",iconCls:"icon-save",handler:dealHandler},
   		              {text:'关闭',id:"icon-cancel",iconCls:"icon-cancel",handler:closeHandler}]  
   		 	}); 
		}
		
		$('#icon-remove').click(function(){
			var row = $('#dg').datagrid('getSelected');
	    	if(!row)
	    	{
	    		myself.msgShow("提示","请选择需要删除的记录!","info");	   
	    		return;
	    	}
			$.messager.confirm('删除提示', '确定要删除所选的记录吗?', function(r){
		        if (r){
		        	$.ajax({
		        		url:'loginlogger/delete.htm?m=${m}',
		        		data:'id='+row.id,		
		        		success:function(data){
		        			if(myself.ajaxCheck(data)){
		        				if(data=='true')
			        			{
				        			myself.msgShow('提示','删除成功!');
			        				$('#dg').datagrid('reload');
			        			}else{
			        				myself.msgShow('提示','删除失败,请重试!');
				        		}
			        		}
		        		},
		        		cache:false				
		        	});
		        }
		    });
		});
		
		
		$('#icon-edit').click(function(){
			var row = $('#dg').datagrid('getSelected');
	    	   if(row){
	    		   openSaveWindow(row.id);
	           }else{
	        	   myself.msgShow("提示","请选择需要修改的记录!","info");
	           }
		});
		
	});
})(jQuery);
function dealHandler()
{
	var form = top.document.getElementById("saveform");
	$(form).ajaxSubmit(function(data){
		if(myself.ajaxCheck(data)){
			if(data=='true')
			{
				closeHandler();
			}
		}
	});
}

function closeHandler(){
	$('#dg').datagrid('reload');
	top.closeWindow();
}

</script>
<body>
<table id="dg" class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:700px;height:250px;padding-left:4px;"
			data-options="pagination:true,rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'loginlogger/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
			  	<th data-options="field:'id',width:200">登录帐号</th>
			  	<th data-options="field:'userId',width:200">登录用户名</th>
			  	<th data-options="field:'lastLoginTime',width:200">最后登录时间</th>
			  	<th data-options="field:'loginIp',width:200">登录IP</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
	</div>
</body>
</html>