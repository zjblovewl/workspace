<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<title>通知公告</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		var windowWidth = 800;
		var windowHeight = 620;
		
		$('#icon-add').click(function(){
			openSaveWindow('');
		});
		
		function openSaveWindow(id)
		{
			$.window({
   				winId:"new-notice-win",  
   		        title:(id==''?'新增':'修改')+"通知公告",
   		        url:'notice/toSave.htm?m=${m}&id='+id,
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
		        		url:'notice/delete.htm?m=${m}',
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
		
		$('#submit-search').click(function(){
			var queryParams = $('#dg').datagrid('options').queryParams;  
			queryParams.title = $('#title').val();
			$('#dg').datagrid('reload');
		});
		
		myself.openViewWindow = function(id)
		{
			$.window({
					winId:"view-notice-win",  
			        title:"查看通知公告",
			        url:'notice/view.htm?m=${m}&id='+id,
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
		top.document.getElementById("content").value = top.contentEditor.getContent();
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
		top.contentEditor.destroy();
	top.closeWindow();
}

function doFormatter(data){
	return '<span style="text-align:center;width:100%" align="center"><a href="javascript:myself.openViewWindow(\''+data+'\');">查看</a></span>';
}
</script>
<body>
<table id="dg" class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:700px;height:250px"
			data-options="pagination:true,rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'notice/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
			  	<th data-options="field:'title',width:200">标题</th>
			  	<th data-options="field:'pubTime',width:200">发布时间</th>
			  	<th data-options="field:'author',width:200">作者</th>
			  	<th data-options="field:'id',width:200,formatter:doFormatter,align:'center'">详情</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		${ functionStr }
		
		<hr width="100%" size="1" color="#A5A5A5"/>
		<form name="searchform" method="post" action="" id ="searchform" style="padding-top:5px;">
			标题:
				<input class="easyui-validatebox" maxlength="256" type="text" name="title"  id="title"></input>
		<a id="submit-search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		</form>
	</div>
</body>
</html>