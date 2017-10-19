<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<title>签到签退信息</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		var windowWidth = 500;
		var windowHeight = 550;
		
		$('#icon-add').click(function(){
			openSaveWindow('');
		});
		
		function openSaveWindow(id)
		{
			$.window({
   				winId:"new-sign-win",  
   		        title:(id==''?'新增':'修改')+"签到签退信息",
   		        url:'sign/toSave.htm?m=${m}&id='+id,
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
		        		url:'sign/delete.htm?m=${m}',
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
			queryParams.userId = $('#userId').val();
			queryParams.type = $('#type').combobox('getValue');
			queryParams.dept = $('#dept').val();
			queryParams.startDate = $('#startDate').datetimebox('getValue');
			queryParams.endDate = $('#endDate').datetimebox('getValue');
			$('#dg').datagrid('reload');
		});
		$('#btn_exp').click(function(){
			$("#searchform").attr("action", "sign/exportRecord.htm");
			$("#searchform").submit();	
		});
		
		myself.openViewWindow = function(id)
		{
			$.window({
					winId:"view-sign-win",  
			        title:"查看签到签退信息",
			        url:'sign/view.htm?m=${m}&id='+id,
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


function formatterdate(val, row) {
	return val.substring(0,19);
}
</script>
<body>
<table id="dg" class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:700px;height:250px"
			data-options="pagination:true,rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'sign/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'deptName',width:200">用户部门</th>
			  	<th data-options="field:'userId',width:200">用户</th>
			  	<th data-options="field:'typeStr',width:200">类型</th>
			  	<!-- 
			  	<th data-options="field:'address',width:200">地址</th>
			  	 -->
			  	<th data-options="field:'handAddress',width:200">所属区域</th>
			  	<th data-options="field:'signTime',width:200,formatter:formatterdate">时间</th>
			  	<th data-options="field:'id',width:200,formatter:doFormatter,align:'center'">详情</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<!-- 
		${ functionStr }
		 
		<hr width="100%" size="1" color="#A5A5A5"/>
		-->
		<form name="searchform" method="post" action="" id ="searchform" style="padding-top:5px;">
			所在部门:
				<input class="easyui-validatebox" maxlength="16" type="text" name="dept"  id="dept"></input>
			用户:
				<input class="easyui-validatebox" maxlength="16" type="text" name="userId"  id="userId"></input>
			周期:	
				从
				<input class="easyui-datetimebox" maxlength="20" type="text" name="startDate"  id="startDate"></input>
				至
				<input class="easyui-datetimebox" maxlength="20" type="text" name="endDate"  id="endDate"></input>
			类型:
				<input id="type" name="type" class="easyui-combobox" data-options="url:'dic/listDicValues.htm?dicId=signtype',valueField:'dicValueId',textField:'dicValueLabel',editable:false,panelHeight:'auto'">
		<a id="submit-search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		<a id="btn_exp" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
		</form>
	</div>
</body>
</html>