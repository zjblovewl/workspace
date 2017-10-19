<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<title>系统设置</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		var windowWidth = 400;
		var windowHeight = 330;
		
		$('#icon-add').click(function(){
			openSaveWindow('');
		});
		
		function openSaveWindow(id)
		{
			$.window({
   				winId:"new-setter-win",  
   		        title:(id==''?'新增':'修改')+"系统设置",
   		        url:'setter/toSave.htm?m=${m}&id='+id,
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
		        		url:'setter/delete.htm?m=${m}',
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
					winId:"view-setter-win",  
			        title:"查看系统设置",
			        url:'setter/view.htm?m=${m}&id='+id,
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
	var id = top.document.getElementById("setterId").value;
	var name = top.document.getElementById("setterName").value;
	$.ajax({
       		url:'setter/checkNameExist.htm',
       		data:'id='+id + "&name="+name,		
       		success:function(data){
       			if(myself.ajaxCheck(data)){
       				if(data=='false')
        			{
       					var form = top.document.getElementById("saveform");
       					$(form).ajaxSubmit({
       						beforeSubmit:function(){
       							return $(this).form('validate');
       						},
       						success:function(data){
       							if(myself.ajaxCheck(data)){
       								if(data=='true')
       								{
       									closeHandler();
       								}
       							}
       						}
       					});
        			}else{
        				myself.msgShow('提示','设置项名称已经存在，请检查!');
	        		}
        		}
       		},
       		cache:false				
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
			data-options="pagination:true,rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'setter/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
			  	<th data-options="field:'setterName',width:200">设置名称</th>
			  	<th data-options="field:'setterValue',width:200">设置值</th>
			  	<th data-options="field:'setterRemark',width:200">设置说明</th>
			  	<th data-options="field:'id',width:200,formatter:doFormatter,align:'center'">详情</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		${ functionStr }
		
	</div>
</body>
</html>