<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<title>用户终端信息</title>
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
   				winId:"new-terminal-win",  
   		        title:(id==''?'新增':'修改')+"用户终端信息",
   		        url:'terminal/toSave.htm?m=${m}&id='+id,
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
		        		url:'terminal/delete.htm?m=${m}',
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
		
		
		myself.openViewWindow = function(id)
		{
			$.window({
					winId:"view-terminal-win",  
			        title:"查看用户终端信息",
			        url:'terminal/view.htm?m=${m}&id='+id,
			        width:windowWidth,
			        height:windowHeight,  
			        onClose:function(){
			        	closeHandler();
				    }
			 	}); 
		}
		
	});
})(jQuery);
function dealHandler()
{
	var form = top.document.getElementById("saveform");
	$(form).ajaxSubmit({
		beforeSubmit:function(){
			return top.formValidate();
		},
		success:function(data){
			top.destroyProgressDialog();
			if(myself.ajaxCheck(data)){
				if(data=='true')
				{
					closeHandler();
				}
			}
		},
		complete:function(XHR, TS){
			top.destroyProgressDialog();
		}
	});
}

function closeHandler(){
	$('#dg').datagrid('reload');
	top.closeWindow();
}

function doFormatter(data){
	return '<span style="text-align:center;width:100%" align="center"><a href="javascript:myself.openViewWindow(\''+data+'\');">查看</a></span>';
}
</script>
<body>
<table id="dg" class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:700px;height:250px"
			data-options="pagination:true,rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'terminal/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
			  	<th data-options="field:'phoneName',width:200">型号名称</th>
			  	<th data-options="field:'phoneVersion',width:200">手机版本</th>
			  	<th data-options="field:'softVersion',width:200">软件版本</th>
			  	<th data-options="field:'imsi',width:200">IMSI号</th>
			  	<th data-options="field:'imei',width:200">IMEI号</th>
			  	<th data-options="field:'loginName',width:200">登录帐号</th>
			  	<th data-options="field:'ownship',width:200">所在地</th>
			  	<th data-options="field:'id',width:200,formatter:doFormatter,align:'center'">详情</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		${ functionStr }
		
	</div>
</body>
</html>