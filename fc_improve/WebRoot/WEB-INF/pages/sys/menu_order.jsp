<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统菜单排序</title>
</head>
<script type="text/javascript">
function selectTopMenu(val,row){
	$('#childmenu').treegrid({   
	    url:'menu/queryChildrenMenus.htm',
	    queryParams:{pMenuId:row.menuId},
	    method:'post',
	    border:true
	});
}

(function($){
	$(function(){
		$('#icon-cancel').click(function(){
			closeWindow();
		});

		$('#icon-p-up').click(function(){
			var tp = $('#topmenu').prev('.datagrid-view2').find('.datagrid-body');
			var trs = $(tp).find('.datagrid-btable>>tr.datagrid-row');
			var selectedTr = $(tp).find('.datagrid-btable>>tr.datagrid-row.datagrid-row-selected');			
			var afterTr = $(trs).get($(trs).index(selectedTr)-1);
			if($(trs).index(selectedTr)<=0){
				return;
			}

			saveOrder(selectedTr,afterTr);
			
			$(selectedTr).after(afterTr);
		});

		$('#icon-p-down').click(function(){
			var tp = $('#topmenu').prev('.datagrid-view2').find('.datagrid-body');
			var trs = $(tp).find('.datagrid-btable>>tr.datagrid-row');
			var selectedTr = $(tp).find('.datagrid-btable>>tr.datagrid-row.datagrid-row-selected');
			var beforeTr = $(trs).get($(trs).index(selectedTr)+1);
			if($(trs).index(selectedTr)<0 || $(trs).index(selectedTr) == $(trs).length-1){
				return;
			}

			saveOrder(selectedTr,beforeTr);
			
			$(selectedTr).before(beforeTr);
		});

		$('#icon-up').click(function(){
			var tp = $('#childmenu').prev('.datagrid-view2').find('.datagrid-body');
			var trs = $(tp).find('.datagrid-btable>>tr.datagrid-row');
			var selectedTr = $(tp).find('.datagrid-btable>>tr.datagrid-row.datagrid-row-selected');
			var afterTr = $(trs).get($(trs).index(selectedTr)-1);
			if($(trs).index(selectedTr)<=0){
				return;
			}

			saveOrder(selectedTr,afterTr);
			
			$(selectedTr).after(afterTr);
		});

		$('#icon-down').click(function(){
			var tp = $('#childmenu').prev('.datagrid-view2').find('.datagrid-body');
			var trs = $(tp).find('.datagrid-btable>>tr.datagrid-row');
			var selectedTr = $(tp).find('.datagrid-btable>>tr.datagrid-row.datagrid-row-selected');
			var beforeTr = $(trs).get($(trs).index(selectedTr)+1);
			if($(trs).index(selectedTr)<0 || $(trs).index(selectedTr) == $(trs).length-1){
				return;
			}

			saveOrder(selectedTr,beforeTr);
			
			$(selectedTr).before(beforeTr);
		});
	});
})(jQuery);

function saveOrder(selectedTr,changeTr){

	var selectedOrder = $(selectedTr).find('td[field=menuOrder]>div').text();
	var selectedMenuId = $(selectedTr).find('td[field=menuName]>div>span.val').attr('id');
	var changeOrder = $(changeTr).find('td[field=menuOrder]>div').text();
	var changeMenuId = $(changeTr).find('td[field=menuName]>div>span.val').attr('id');
	

	var jsonData = '[';
	jsonData += '{"menuId":"'+selectedMenuId+'","menuOrder":"'+changeOrder+'"},';
	jsonData += '{"menuId":"'+changeMenuId+'","menuOrder":"'+selectedOrder+'"}';
	jsonData += ']';
	$(selectedTr).find('td[field=menuOrder]>div').text(changeOrder);
	$(changeTr).find('td[field=menuOrder]>div').text(selectedOrder);
	
	$.ajax({
		url:'menu/order.htm',
		data:'orderData='+jsonData,		
		success:function(data){
			if(data=='true')
			{
				//排序成功，不做操作
			}else
    		{
        		//alert(data);
    		}
    			
		},
		cache:false				
	});
	
}


function columnFormatter(val,row){
	return '<img src="images/menuicons/'+row.menuIcon+'" width="16" height="16"/>&nbsp;<span class="val" id="'+row.menuId+'">'+val+'</span>';
}
</script>
<body>
<table style="width: 100%;height:100%">
	<tr>
		<td width="5%" align="center">
		</td>
		<td width="43%" align="center" valign="top" style="border:1px;">
			<div id="ptb" style="padding:5px;height:auto" class="datagrid-toolbar">
				<a id="icon-p-up" href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-up">上移</a>
				<a id="icon-p-down" href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-down">下移</a>
			</div>
				<table id="topmenu" class="easyui-datagrid"
					data-options="idField:'menuId',singleSelect:true,fixColumns:true,fit:true,
					url:'menu/queryTopMenus.htm',border:true,title:false,onClickRow:selectTopMenu,toolbar:'#ptb'">
					<thead>
						<tr>
							<th data-options="field:'menuName',width:181,formatter:columnFormatter">顶级菜单</th>
							<th data-options="field:'menuOrder',width:60">排序</th>
						</tr>
					</thead>
				</table>
		</td>
		<td width="4%">&nbsp;</td>
		<td width="43%" align="center" valign="top" style="border:1px;">
			<div id="ctb" style="padding:5px;height:auto" class="datagrid-toolbar">
				<a id="icon-up" href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-up">上移</a>
				<a id="icon-down" href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-down">下移</a>
			</div>
			<table id="childmenu" class="easyui-datagrid" 
				data-options="idField:'menuId',border:true,fit:true,singleSelect:true,fixColumns:true,title:false,toolbar:'#ctb'">
				<thead>
					<tr>
						<th data-options="field:'menuName',width:180,formatter:columnFormatter">下级菜单</th>
						<th data-options="field:'menuOrder',width:60">排序</th>
					</tr>
				</thead>
			</table>
		</td>
		<td width="5%" align="center">
		</td>
	</tr>
</table>
</body>
</html>