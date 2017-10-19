<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>

<script type="text/javascript" src="js/window.js"></script>
<title>字典列表</title>
</head>
<script type="text/javascript">
(function($){
   $(function(){
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
				var dicValueId = $(trs).eq(i).find('td[field=dicValueId]>div').text();
				var dicValueLabel = $(trs).eq(i).find('td[field=dicValueLabel]>div').text();
				jsonData +='{"dicValueId":"'+dicValueId+'","dicValueLabel":"'+dicValueLabel+'"}';
				if(i != $(trs).length-1){
					jsonData += ',';
				}
			}
			jsonData += ']';
			$('#rowdata').val(jsonData);
			$('#ff').form('submit',{
				   success : function(data) {
						if(data){
							myself.msgShow('提示','成功保存!','info');
						}
				   }
		    });

		});
   });
})(jQuery);


var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true};
	if ($('#dg').datagrid('validateRow', editIndex)){
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
</script>
<body>
<div  class="easyui-panel" data-options="fit:true,border:false">
<form id="ff" style="width:100%;height:100%;padding:0px;" method="post" action="dic/saveDicValues.htm">
<input type="hidden" name="rowdata" id="rowdata">
<input type="hidden" name="dicId" id="dicId" value="${dicId}">
<table id="dg" class="easyui-datagrid" title="DataGrid Complex Toolbar"
			data-options="fit:true,fitColumns:true,pagination:true,
				url:'dic/listDicValues.htm?dicId=${dicId}',title:false,border:false,singleSelect:false,toolbar:'#tb',onClickRow: onClickRow">
		<thead>
			<tr>
				<th data-options="field:'dicValueId',width:150,editor:{type:'validatebox',options:{required:true,validType:'letter'}}">编码</th>
				<th data-options="field:'dicValueLabel',width:600,editor:{type:'validatebox',options:{required:true,validType:'name'}}">显示名</th>
			</tr>
		</thead>
</table>
<div id="tb" style="padding:5px;height:auto">
	<a href="javascript:;" id="icon-add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加行</a>
	<a href="javascript:;" id="icon-remove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除行</a>
	<a href="javascript:;" id="icon-up" class="easyui-linkbutton" data-options="iconCls:'icon-up',plain:true">上移</a>
	<a href="javascript:;" id="icon-down" class="easyui-linkbutton" data-options="iconCls:'icon-down',plain:true">下移</a>
	<a href="javascript:;" id="icon-save" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
</div>
</form>
</div>
</body>
</html>