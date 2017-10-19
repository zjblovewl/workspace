<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<title>菜单管理</title>
</head>
<script type="text/javascript">
(function($){
  $(function(){
	   $('#icon-add').click(function(){
			myself.location('menu/toSave.htm?m=${m}&operator=add');
	   });

	   $('#icon-edit').click(function(){
		   var row = $('#menutg').treegrid('getSelected');
		   if(row)
		   {
			   myself.location('menu/toSave.htm?m=${m}&operator=edit&menuId='+row.menuId);	
		   }else
		   {
			   myself.msgShow("提示","请选择需要修改的菜单项!","info");
		   }
	   });
	   

	   $('#icon-remove').click(function(){
		   var row = $('#menutg').treegrid('getSelected');
			if (row){
				$.messager.confirm('删除提示', '删除不可恢复,确定删除所选的菜单吗?', function(r){
			        if (r){
			        	$.ajax({
			        		url:'menu/delete.htm?m=${m}',
			        		data:'menuId='+row.menuId,		
			        		success:function(data){			        			
			        			if(myself.ajaxCheck(data)){
			        				if(data=='true')
				        			{
				        				$('#menutg').treegrid('reload');
				        			}
				        		}
			        		},
			        		cache:false				
			        	});
			        }
			    });
			}else{
				myself.msgShow("提示","请选择需要删除的菜单项!","info");
			}
	   });

	   $('#icon-function').click(function(){
		   var row = $('#menutg').treegrid('getSelected');

		   if(row)
		   {
			   if(row.PMenuId == '0'){
				   myself.msgShow("提示","顶级菜单项不需要进行操作功能配置!","info");
			   }else{
				   myself.location('menu/toFunction.htm?m=${m}&menuId='+row.menuId+'&menuName='+encodeURI(row.menuName));	
			   }
		   }else
		   {
			   myself.msgShow("提示","请选择需要配置操作功能的菜单项!","info");
		   }
		});

	   //排序
	   $('#icon-arrow-switch').click(function(){
		   $.window({
				winId:"menu-order-win",  
		        title:"菜单排序",
		        url:'menu/toOrder.htm?m=${m}',
		        height:350,  
		        width:600,
		        onClose:function(){
		        	$('#menutg').treegrid('reload');
			    },     
		        toolbar:[{text:'关闭',id:"icon-cancel",iconCls:"icon-cancel",handler:closeHandler}]  
		 	}); 
	   });
  });
})(jQuery);
function closeHandler(){
	$('#menutg').treegrid('reload');
}
function iconFormatter(val,row){
	return '<img src="images/menuicons/'+val+'" width="16" height="16"/>';
}
</script>
<body>
<table id="menutg" class="easyui-treegrid"
			data-options="rownumbers:true,fit:true,dnd:true,fitColumns:true,title:false,border:false,singleSelect:true,idField: 'menuId',treeField: 'menuName',url:'menu/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'menuName',width:200">菜单名称</th>
				<th data-options="field:'menuIcon',width:80,formatter:iconFormatter,align:'center'">菜单图标</th>
				<th data-options="field:'menuLink',width:500">菜单链接</th>
			</tr>
		</thead>
</table>
<div id="tb" style="padding:5px;height:auto">
	${functionStr}
</div>
</body>
</html>