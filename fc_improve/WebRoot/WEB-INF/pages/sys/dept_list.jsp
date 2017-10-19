<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>部门管理</title>
</head>
<script type="text/javascript">
$(function() {
	$("#tree").tree( {
		url : 'dept/list.htm',
		//checkbox : true,
		radoi:true,
		onClick : doMenuClick
	});


	myself.appendTree = function(id,txt){
		
		var node = $('#tree').tree('getSelected');
		 var nodes = [{
		        "id":id,
		        "text":txt
		    }];
									
		
		if(node){
			$('#tree').tree('append', {
				parent:node.target,
				data:nodes
			});
		}else{
			$(tree).tree('append', {
				data:nodes
			});
		}
	};
   
	var addDeptId = '0'; 
	var addDeptName = '无';
	
	function doMenuClick(node) {
		var elIframe = $("#edit").get(0);
		addDeptId = node.id;
		addDeptName = node.text;
		elIframe.src = "dept/tosave.htm?operator=edit&m=${m}&deptId="+node.id+'&deptName='+encodeURI(addDeptName);
	}

	//新增部门信息
	$("#icon-add").click(function(){
		var elIframe = $("#edit").get(0);
		elIframe.src = "dept/tosave.htm?operator=add&m=${m}&deptId="+addDeptId+'&deptName='+encodeURI(addDeptName);
	});
	
	$("#icon-cancel").click(function(){
		// 检查是否有选中的节点		
		var node = $("#tree").tree("getSelected");
		if (!node){
			myself.msgShow('提示','请勾选要删除的节点!');
			return;
		}
		var children = $('#tree').tree('getChildren',node.target);
		if(children.length > 0){
			myself.msgShow('提示','请先删除下级部门后再删除该部门!');
			return;
		}
		$.messager.confirm('删除提示', '删除部门后该部门的用户部门信息将被置空，确定删除吗?', function(r){
	        if (r){
	        	$.ajax({
	    			url:'dept/delete.htm',
	    			data:'deptId='+node.id,		
	    			success:function(data){
	    				if(myself.ajaxCheck(data)){
	    					if(data=='true')
	    					{//成功
	    						$("#tree").tree("remove",node.target);
	    						$('#edit').attr('src','dept/instruction.htm');
	    					}
	    				}
	    	    			
	    			},
	    			cache:false				
	    		});
	        }
	    });
	});

	$('#icon-help').click(function(){
		var elIframe = $("#edit").get(0);
		elIframe.src = 'dept/instruction.htm';
	});
	
	// TIP: 配合body解决页面跳动和闪烁问题
	$("body").css({visibility:"visible"});
});

</script>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="west" title="部门树" icon="icon-forward" style="width:210px;overflow:auto;" split="true" border="false" >
		<div class="datagrid-toolbar">
			${functionStr}
			<!-- 	
			<a id="icon-add" href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-add">增下级</a>
			<a id="icon-cancel" href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-cancel">删除</a>
			<a id="icon-help" href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-help">帮助</a>
			 -->
		</div>

	   	<form id="form" method="post" >	   		   	
			<ul id="tree"></ul>
	    </form>
    </div>
    
    <div region="center" style="overflow:hidden;" border="false">
		<iframe id="edit" src="dept/instruction.htm" scrolling="no" frameborder="0" style="width:100%;height:100%;"></iframe>
    </div>
</body>
</html>