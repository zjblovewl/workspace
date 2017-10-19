<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>用户权限设置</title>
</head>
<script language="javascript">
function roleFormatter(val,row){
	if(row.PMenuId == '0'){
		return '<font color="#cccccc">顶级菜单无功能</font>';
	}else
	{
		var result = '';
		var funs = row.functions;
		if(funs != null){
			for(var i = 0;i<funs.length;i++){
				if(funs[i].checked){
					result += '<input type="checkbox" checked="true" name="r_'+row.menuId+'_'+funs[i].funId+'" value="'+funs[i].funId+'" id="'+funs[i].funId+'"/><label>'+funs[i].funName+'</label>&nbsp;&nbsp;';
				}else{
					result += '<input type="checkbox" name="r_'+row.menuId+'_'+funs[i].funId+'" value="'+funs[i].funId+'" id="'+funs[i].funId+'"/><label>'+funs[i].funName+'</label>&nbsp;&nbsp;';
				}
			}
		}
		return result;
	}
}

function chooseFormatter(val,row){
	if(row.PMenuId == '0'){
		return "";
	}else{
		return '<input type="checkbox" class="chooseall" id="'+row.menuId+'" onclick="chooseAll(this)"/>';
	}
}

function viewFormatter(val,row){
	if(row.PMenuId == '0'){
		return "";
	}else{
		if(row.hasViewPower){
			return '<input type="checkbox" class="chooseall" name="m_'+row.menuId+'" value="'+row.menuId+'" checked="true"/>';
		}else{
			return '<input type="checkbox" class="chooseall" name="m_'+row.menuId+'" value="'+row.menuId+'"/>';
		}
		
	}
}

function iconFormatter(val,row){
	return '<img src="images/menuicons/'+val+'" style="width:16px;height:16px;"/>';
}

//行全选
function chooseAll(obj){
	if($(obj).attr('checked') == 'checked'){
		$('input[name^="r_'+$(obj).attr('id')+'"]').attr('checked',true);
		$('input[name="m_'+$(obj).attr('id')+'"]').attr('checked',true);
	}else{
		$('input[name^="r_'+$(obj).attr('id')+'"]').removeAttr('checked');
		$('input[name="m_'+$(obj).attr('id')+'"]').removeAttr('checked');
	}
}

(function($){
	$(function(){
		//全选
		$('#icon-redo').click(function(){
			$(':checkbox').attr('checked',true);
		});
		
		//取消全选
		$('#icon-undo').click(function(){
			$(':checkbox').removeAttr('checked');
		});

		//保存
		$('#icon-save').click(function(){
			$('form').submit();
		});

	});
})(jQuery);
</script>
<body class="easyui-layout">
<form action="role/setterRole.htm?m=${m}" method="post">
<input type="hidden" name="roleId" value="${roleId}"/>
<div region="north" style="overflow:hidden;" split="false" border="false" >
	<div class="datagrid-toolbar" style="padding:5px;">
		<a href="javascript:;" class="easyui-linkbutton" id="icon-save" data-options="iconCls:'icon-save',plain:true">保存</a>
		<a href="javascript:;" class="easyui-linkbutton" id="icon-redo" data-options="iconCls:'icon-redo',plain:true">全选</a>
		<a href="javascript:;" class="easyui-linkbutton" id="icon-undo" data-options="iconCls:'icon-undo',plain:true">全不选</a>
		<a href="role/search.htm?m=${m}" class="easyui-linkbutton" id="icon-back" data-options="iconCls:'icon-back',plain:true">返回</a>
		
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">角色名称:${roleName}</font></span>
	</div>
</div>
<div region="center" style="overflow:auto;padding:0px;" border="false">
<table id="menutg" class="easyui-treegrid"
			data-options="rownumbers:true,fit:true,title:false,border:false,singleSelect:true,checkOnSelect:false,selectOnCheck:false,
			fitColumns:true,idField: 'menuId',treeField: 'menuName',url:'menu/listWithFunctions.htm?roleId=${roleId}'">
		<thead>
			<tr>
				<th data-options="field:'menuName',width:200">菜单名称</th>
				<th data-options="field:'menuIcon',width:80,align:'center',formatter:iconFormatter">菜单图标</th>
				<th data-options="field:'menuId',width:80,align:'center',formatter:chooseFormatter">全选</th>
				<th data-options="field:'hasViewPower',width:80,align:'center',formatter:viewFormatter">访问权</th>
				<th data-options="field:'pMenuId',formatter:roleFormatter">操作权</th>
				<th data-options="field:'menuLink',width:300">菜单链接</th>
			</tr>
		</thead>
</table>
</div>
</form>
</body>
</html>