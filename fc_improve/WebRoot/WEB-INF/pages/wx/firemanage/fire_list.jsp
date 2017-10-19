<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<script type="text/javascript" src="js/fc.common.js"></script>
<title>微信-地图</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		var windowWidth = 870;
		var windowHeight = 630;
		
		$('#icon-add').click(function(){
			openSaveWindow('');
		});
		
		function openSaveWindow(id)
		{
			$.window({
   				winId:"new-addrbook-win",  
   		        title:(id==''?'新增':'修改')+"火情",
   		        url:'fire/toSave.htm?m=${m}&id='+id,
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
		        		url:'fourVirtues/delete.htm?m=${m}',
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
			queryParams.postion = $('#postion').val();
			$('#dg').datagrid('reload');
		});
		
		myself.openViewWindow = function(id)
		{
			$.window({
					winId:"view-addrbook-win",  
			        title:"查看四德榜",
			        url:'fourVirtues/view.htm?m=${m}&id='+id,
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

function batchImport(){
	$('#dlg').dialog({title:'通讯录导入'}).dialog('open');
}

function batchInsert(){
	//var fileName = $("#addrBook_excel").val();
	//var suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
	var ajax_option={
		url:"fourVirtues/batchInsert.htm",
		success:function(data){
			var a ;
			eval("a="+data);
			var errorMsg = a.errorMsg;
			if(errorMsg.length>1){
				alert(errorMsg);
			}else{
				$('#dlg').dialog('close');
				alert("导入成功");
				$('#dg').datagrid('reload');
			}
		}
	}

	$('#form1').ajaxSubmit(ajax_option);
}
</script>
<body>
<table id="dg" class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:700px;height:250px"
			data-options="pagination:true,rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'fire/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
			  	<th data-options="field:'postion',width:200">地点</th>
			  	<th data-options="field:'longitude',width:200">经度</th>
			  	<th data-options="field:'latitude',width:200">纬度</th>
			  	<th data-options="field:'fire',width:200">火情</th>
			  	<th data-options="field:'createTime',width:200">发布时间</th>
			  	<th data-options="field:'createUser',width:200">作者</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		${ functionStr }
		
		<hr width="100%" size="1" color="#A5A5A5"/>
		<form name="searchform" method="post" action="" id ="searchform" style="padding-top:5px;">
			位置:
				<input class="easyui-validatebox" maxlength="32" type="text" name="postion"  id="postion"></input>
			<a id="submit-search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			
			<a id="reset-search" onclick="document.getElementById('postion').value=''" class="easyui-linkbutton" iconCls="icon-no">重置</a>
		</form>
	</div>
	
	
	<div id="dlg" class="easyui-dialog" closed="true" style="width:400px;height:200px" scrolling="false" data-options="collapsible:true,minimizable:true,maximizable:true,closable:true">
		<h6 >
			请使用标准模板填写并导入，<strong><a style="color: red;" href="addrbook/templateDownload.htm">点击获取模板</a></strong>
		</h6>
		<form id="form1" name="form1" enctype="multipart/form-data" method="post" onsubmit="return false;">
			<input type="hidden" id="insertFlag" value='${insertFlag}'/>
			<div >
				<input type="file" id="addrBook_excel" name="addrBookexcel" label="File" />
			</div>
			<div style="text-align:center;">
				<button  type="button" id="btn_batchImport" onclick="batchInsert();">
					导入
				</button>
			</div>
		</form>
	</div>
</body>
</html>