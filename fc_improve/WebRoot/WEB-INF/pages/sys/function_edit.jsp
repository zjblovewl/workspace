<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>菜单操作功能维护</title>
</head>

<script type="text/javascript">
(function($){
	$(function(){
		$('#icon-back').click(function(){
			myself.location('menu/search.htm?m=${m}');
		});

		$('#icon-up').click(function(){
			var trs = $('.datagrid-btable>>tr.datagrid-row');
			var selectedTr = $('.datagrid-btable>>tr.datagrid-row.datagrid-row-selected');
			var afterTr = $(trs).get($(trs).index(selectedTr)-1);
			if($(trs).index(selectedTr)<=0){
				return;
			}
			$(selectedTr).after(afterTr);
			
		});

		$('#icon-down').click(function(){
			var trs = $('.datagrid-btable>>tr.datagrid-row');
			var selectedTr = $('.datagrid-btable>>tr.datagrid-row.datagrid-row-selected');
			if($(trs).index(selectedTr)<0 || $(trs).index(selectedTr) == $(trs).length-1){
				return;
			}
			$(selectedTr).before($(trs).get($(trs).index(selectedTr)+1));
		});

		$('#icon-remove').click(function(){
			if (editIndex == undefined){return}
			$('#dg').datagrid('cancelEdit', editIndex)
					.datagrid('deleteRow', editIndex);
			editIndex = undefined;
		});

		$('#icon-add').click(function(){
			if (endEditing()){
				$('#dg').datagrid('appendRow',{});
				editIndex = $('#dg').datagrid('getRows').length-1;
				$('#dg').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);

			}
		});

		$('#icon-save').click(function(){
			if (endEditing()){
				$('#dg').datagrid('acceptChanges');
			}

			var trs = $('div.datagrid-body>table>>tr.datagrid-row');
			
			var jsonData = '[';
			for(var i=0;i<$(trs).length;i++)
			{
				var funId = $(trs).eq(i).find('td[field=funId]>div>span.val').text();
				var funName = $(trs).eq(i).find('td[field=funName]>div').text();
				var funLink = $(trs).eq(i).find('td[field=funLink]>div').text();
				jsonData +='{"funId":"'+funId+'","funName":"'+funName+'","funLink":"'+funLink+'"}';
				if(i != $(trs).length-1){
					jsonData += ',';
				}
			}
			jsonData += ']';
			
			$('#rowdata').val(jsonData);
			$('#ff').form('submit',{
				   success : function(data) {
						myself.location('menu/search.htm?m=${m}');
				   }
		    });

		});
	});
})(jQuery);

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#dg').datagrid('validateRow', editIndex)){
		var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'funId'});
		var funId = $(ed.target).combotree('getText');
		$('#dg').datagrid('getRows')[editIndex]['funId'] = funId;
		$('#dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#dg').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#dg').datagrid('selectRow', editIndex);
		}
	}
}

function iconFormatter(value,row){
	return '<span class="'+value+'" style="float:left;width:16px;height:16px;">&nbsp;</span>&nbsp;&nbsp;<span class="val">'+value+'</span>';
}

</script>

<body>
<div class="easyui-panel" data-options="fit:true,border:false">
<form id="ff" style="width:100%;height:100%;padding:0px;" method="post" action="menu/saveMenuFunIcons.htm">
<input type="hidden" name="rowdata" id="rowdata">
<input type="hidden" name="menuId" value="${menuId}">
       <div id="tb" style="padding: 5px; height: auto; " class="datagrid-toolbar">
		<div>
			<a href="javascript:;" id="icon-add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加行</a>
			<a href="javascript:;" id="icon-remove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除行</a>
			<a href="javascript:;" id="icon-up" class="easyui-linkbutton" data-options="iconCls:'icon-up',plain:true">上移</a>
			<a href="javascript:;" id="icon-down" class="easyui-linkbutton" data-options="iconCls:'icon-down',plain:true">下移</a>
			<a href="javascript:;" id="icon-save" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
			<a href="javascript:;" class="easyui-linkbutton" id="icon-back" iconCls="icon-back" plain="true">返回</a>
			
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">菜单名称:${menuName}</font></span>
		</div>
	</div>
    	<table id="dg" class="easyui-datagrid" style="height:auto;margin:3px;"
				data-options="
					iconCls: 'icon-edit',
					fit:true,
					singleSelect: true,
					toolbar: '#funtb',
					border:false,
					remoteSort:false,
					url: 'menu/menuFunctions.htm?menuId=${menuId}',
					onClickRow: onClickRow
				">
			<thead>
				<tr>
					<th data-options="field:'funId',width:200,
						formatter:iconFormatter,
						editor:{
							type:'combotree',
							options:{
								valueField:'id',
								textField:'text',
								url:'menu/queryFunIcons.htm',
								panelHeight: 'auto',
								fitColumns: true,
								required:true
							}
						}">菜单图标</th>
					<th data-options="field:'funName',width:300,editor:{type:'validatebox',options:{required:true,validType:'name'}}">菜单名称</th>
					<th data-options="field:'funLink',width:300,editor:{type:'validatebox',options:{required:true,validType:'link'}}">菜单访问链接</th>
				</tr>
			</thead>
		</table>
</form>
</div>
</body>
</html>