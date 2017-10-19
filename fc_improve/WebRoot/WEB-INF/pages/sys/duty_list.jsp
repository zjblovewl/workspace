<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>职务信息管理</title>
</head>
<script type="text/javascript">
(function($){
   $(function(){
       $("#icon-add").click(function(){
			myself.location('duty/tosave.htm?m=${m}');
       });

	   $('#icon-edit').click(function(){
		   var row = $('#dutydg').datagrid('getSelected');
    	   if(row){
    		   myself.location("duty/tosave.htm?m=${m}&dutyId="+row.dutyId);
           }else{
        	   myself.msgShow("提示","请选择需要修改的数据行!","info");
           }
	   });
	   	
       $("#icon-remove").click(function(){
    	   var row = $('#dutydg').datagrid('getSelected');
			if (row){
				$.messager.confirm('删除提示', '确定删除所选的职务信息吗?', function(r){
			        if (r){
			        	$.ajax({
			        		url:'duty/delete.htm',
			        		data:'dutyIds='+row.dutyId,		
			        		success:function(data){
			        			if(myself.ajaxCheck(data))
			        			{
			        				if(data=='true')
				        			{
				        				$('#dutydg').datagrid('reload');
				        			}
			        			}
			        		},
			        		cache:false				
			        	});
			        }
			    });
			}else{
				myself.msgShow("提示","请选择需要删除的数据行!","info");
			}
       });

    });
})(jQuery);
</script>
<body>
<table id="dutydg" class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:700px;height:250px"
		data-options="rownumbers:true,fit:true,striped:true,fitColumns:true,title:false,border:false,singleSelect:true,
		url:'duty/list.htm',toolbar:'#tb'">
	<thead>
		<tr>
			<th data-options="field:'dutyName',width:200">职务</th>
			<th data-options="field:'dutyDesc',width:500">职务说明</th>
		</tr>
	</thead>
</table>
<div id="tb" style="padding:5px;height:auto">
	${functionStr}
</div>
</body>
</html>