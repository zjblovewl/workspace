<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<script type="text/javascript" src="js/fc.common.js"></script>
<title>通讯录</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		var windowWidth = 600;
		var windowHeight = 540;
						
		$('#submit-search').click(function(){
			var queryParams = $('#dg').datagrid('options').queryParams;  
			queryParams.userName = $('#userName').val();
			queryParams.dept = $('#dept').val();
			queryParams.startDate = $('#startDate').datetimebox('getValue');
			queryParams.endDate = $('#endDate').datetimebox('getValue');
			$('#dg').datagrid('reload');
		});
		
		myself.openViewWindow = function(id)
		{
			$.window({
					winId:"view-addrbook-win",  
			        title:"数据统计详情",
			        url:'report/view.htm?signId='+id,
			        width:windowWidth,
			        height:windowHeight,  
			        onClose:function(){
			        	closeHandler();
				    }
			 	}); 
		}
		
		$('#btn_exp').click(function(){
			$("#searchform").attr("action", "report/exportRecord.htm");
			$("#searchform").submit();	
		});			
	});
})(jQuery);


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
			data-options="pagination:true,rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'report/queryList.htm',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'deptName',width:200">部门</th>
			  	<th data-options="field:'userName',width:200">用户姓名</th>
			  	<th data-options="field:'userPhone',width:200">电话</th>
			  	<th data-options="field:'description',width:200">反馈问题</th>
			  	<th data-options="field:'appealerAddress',width:200">处理地点</th>
			  	<th data-options="field:'signTime',width:200,formatter:formatterdate">结束时间</th>
			  	<th data-options="field:'signId',width:200,formatter:doFormatter,align:'center'">详情</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<form name="searchform" method="post" action="" id ="searchform" style="padding-top:5px;">
				所在部门:
				<input class="easyui-validatebox" maxlength="16" type="text" name="dept"  id="dept"></input>
			办理人:
				<input class="easyui-validatebox" maxlength="16" type="text" name="userName"  id="userName"></input>
			周期:	
				从
				<input class="easyui-datetimebox" maxlength="20" type="text" name="startDate"  id="startDate"></input>
				至
				<input class="easyui-datetimebox" maxlength="20" type="text" name="endDate"  id="endDate"></input>
			<a id="submit-search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
			<a id="btn_exp" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
		</form>
	</div>
</body>
</html>