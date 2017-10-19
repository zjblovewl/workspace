<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<title>字典管理框架</title>
</head>
<script type="text/javascript">
$(function() {
	$("#tree").tree( {
		url : 'dic/dicList.htm',
		onClick : doMenuClick
	});
   
	var dicId;
	var selectedNode;
	function doMenuClick(node) {
		dicId = node.id;
		selectedNode = node;
		var elIframe = $("#edit").get(0);
		elIframe.src = "dic/search.htm?m=${m}&dicId="+node.id;
	}
	
	$('#icon-add').click(function(){
		top.callFunction(saveHandler);
		$.window({
			winId:"dic-save-win",  
	        title:"新增字典",
	        url:'dic/toSave.htm?m=${m}',
	        height:180,  
	        width:300,
	        onClose:function(){
	        	
		    },     
	        toolbar:[{text:'保存',id:"icon-save",iconCls:"icon-save"}]  
	 	}); 
	});

	$('#icon-edit').click(function(){
		if(dicId == null || dicId == '')
	    {
			myself.msgShow("提示","请选择需要修改的字典!","info");
			return;
		}
		top.callFunction(saveHandler);
		$.window({
			winId:"dic-save-win",  
	        title:"修改字典",
	        url:'dic/toSave.htm?m=${m}&dicId='+dicId,
	        height:180,  
	        width:300,
	        onClose:function(){
	        	
		    },     
	        toolbar:[{text:'保存',id:"icon-save",iconCls:"icon-save"}]  
	 	}); 
	});

	$('#icon-remove').click(function(){
		if(dicId == null || dicId == '')
	    {
			myself.msgShow("提示","请选择需要删除的字典!","info");
			return;
		}
		$.messager.confirm('删除提示', '确定删除所选择的字典信息吗?', function(r){
	        if (r){
	        	$.ajax({
	    			url:'dic/delete.htm',
	    			data:'dicId='+dicId,		
	    			success:function(data){
	    				if(myself.ajaxCheck(data)){
	    					if(data)
	    					{//成功
	    						$("#tree").tree("remove",selectedNode.target);
    							dicId = null;
	    					}
	    				}
	    	    			
	    			},
	    			cache:false				
	    		});
	        }
	    });
	});

	function saveHandler()
	{
		top.closeWindow();
		$('#tree').tree('reload');
	}
	
	// TIP: 配合body解决页面跳动和闪烁问题
	$("body").css({visibility:"visible"});
});

</script>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="west" title="字典列表" icon="icon-forward" style="width:210px;overflow:auto;" split="true" border="false" >
		<div class="datagrid-toolbar">
			${functionStr}
		</div>
		<ul id="tree"></ul>
    </div>
    
    <div region="center" style="overflow:hidden;" border="false">
		<iframe id="edit" src="dic/search.htm?m=${m}" scrolling="no" frameborder="0" style="width:100%;height:100%;"></iframe>
    </div>
</body>
</html>