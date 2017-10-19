<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<title>民生实事</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		var windowWidth = 600;
		var windowHeight = 400;
		
		$('#submit-search').click(function(){
			var queryParams = $('#dg').datagrid('options').queryParams;  
			queryParams.userName = $('#userName').val();
			queryParams.dept = $('#dept').val();
			queryParams.town = $('#town').val();
			queryParams.village = $('#village').val();
			queryParams.startDate = $('#startDate').datetimebox('getValue');
			queryParams.endDate = $('#endDate').datetimebox('getValue');
			$('#dg').datagrid('reload');
		});
		
		$('#submit-export').click(function(){
			$('#searchform').attr('action','peoplething/export.htm');
			$('#searchform').submit();
		});
		
		$('#icon-add').click(function(){
			openSaveWindow('');
		});
		
		function openSaveWindow(id)
		{
			$.window({
   				winId:"new-peoplething-win",  
   		        title:(id==''?'新增':'修改')+"民生实事",
   		        url:'peoplething/toSave.htm?m=${m}&id='+id,
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
		        		url:'peoplething/delete.htm?m=${m}',
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
					winId:"view-peoplething-win",  
			        title:"查看民生实事",
			        url:'peoplething/view.htm?m=${m}&id='+id,
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
			data-options="pagination:true,rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'peoplething/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'town',width:200">镇街/社区</th>
				<th data-options="field:'village',width:200">村/小区</th>
				<th data-options="field:'deptName',width:200">联系单位</th>
				<th data-options="field:'userName',width:200">驻村(小区)联络员</th>
			  	<th data-options="field:'content',width:200">名称</th>
			  	<th data-options="field:'progress',width:200">进展情况</th>
			  	<th data-options="field:'problem',width:200">存在困难及问题</th>
			  	<th data-options="field:'createDate',width:200,formatter:formatterdate">创建日期</th>
			  	<th data-options="field:'id',width:200,formatter:doFormatter,align:'center'">详情</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<form name="searchform" method="post" action="" id ="searchform" style="padding-top:5px;">
			镇街/社区:
				<input class="easyui-validatebox" maxlength="16" type="text" name="town"  id="town"></input>     
			村/小区:        
			    <input class="easyui-validatebox" maxlength="16" type="text" name="village"  id="village"></input>
			联系单位:      
				<input class="easyui-validatebox" maxlength="16" type="text" name="dept"  id="dept"></input>
			驻村（小区）联络员:
				<input class="easyui-validatebox" maxlength="16" type="text" name="userName"  id="userName"></input>
			周期:	
				从
				<input class="easyui-datetimebox" maxlength="20" type="text" name="startDate"  id="startDate"></input>
				至
				<input class="easyui-datetimebox" maxlength="20" type="text" name="endDate"  id="endDate"></input>	
			<!-- 
			所在部门:
				<input class="easyui-validatebox" maxlength="16" type="text" name="dept"  id="dept"></input>
			办理人:
				<input class="easyui-validatebox" maxlength="16" type="text" name="userName"  id="userName"></input>
			
			 -->	
			<a id="submit-search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			<a id="submit-export" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
		</form>
	</div>
</body>
</html>