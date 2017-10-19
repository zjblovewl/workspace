<%@page import="com.tyunsoft.base.utils.PageUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<script type="text/javascript" src="js/window.js"></script>
<link rel="stylesheet" href="js/zTree3.3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.core-3.3.js"></script>
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.excheck-3.3.js"></script>
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.exedit-3.3.js"></script>
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.exhide-3.3.js"></script>
<title>签到签退信息</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		var windowWidth = 400;
		var windowHeight = 200;
		
		$('#icon-add').click(function(){
			openSaveWindow('');
		});
		
		function openSaveWindow(id)
		{
			$.window({
   				winId:"new-address-win",  
   		        title:(id==''?'新增':'修改')+"联络人管理",
   		        url:'address/toSave.htm?m=${m}&id='+id,
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
		        		url:'address/delete.htm?m=${m}',
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
				
		
	});
})(jQuery);
function dealHandler()
{
	var form = top.document.getElementById("saveform");
	$(form).ajaxSubmit(function(data){
		if(myself.ajaxCheck(data)){
			if(data=='true')
			{
				closeHandler();
			}
		}
	});
}

function closeHandler(){
	$('#dg').datagrid('reload');
	top.closeWindow();
}
</script>

<script type="text/javascript">
(function($){
	$(function(){
		
		$('#submit-search').click(function(){
			var queryParams = $('#dg').datagrid('options').queryParams;  
			queryParams.townName = $('#townName').val();
			queryParams.villageName = $('#villageName').val();
			$('#dg').datagrid('reload');
		});
		
		
	});
})(jQuery);

function doFormatter(data){
	return '<span style="text-align:center;width:100%" align="center"><a href="javascript:selectUsers(\''+data+'\');">选择人员</a></span>';
}


</script>
<body>
<input type="hidden" id="jsonStr" value="${jsonStr}">
<table id="dg" class="easyui-datagrid" title="DataGrid Complex Toolbar" style="width:700px;height:250px"
			data-options="pagination:true,rownumbers:true,striped:true,fit:true,fitColumns:true,title:false,border:false,singleSelect:true,url:'area/list.htm',toolbar:'#tb'">
		<thead>
			<tr>
			  	<th data-options="field:'townName',width:200">镇街/社区</th>
			  	<th data-options="field:'villageName',width:200">村/小区</th>
			  	<th data-options="field:'users',width:200">联络员</th>
			  	<th data-options="field:'id',width:200,formatter:doFormatter,align:'center'">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		${ functionStr }
		 
		<hr width="100%" size="1" color="#A5A5A5"/>
		<form name="searchform" method="post" action="" id ="searchform" style="padding-top:5px;">
			镇街/社区:
				<input class="easyui-validatebox" maxlength="16" type="text" name="townName"  id="townName"></input>
			村/小区:
				<input class="easyui-validatebox" maxlength="16" type="text" name="villageName"  id="villageName"></input>
		<a id="submit-search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		</form>
	</div>
	<div id="dlg" class="easyui-dialog" closed="true" style="width:500px;height:420px" scrolling="false" data-options="closable:true">
	 	<div id="dlg-buttons" align="left" style="background:#F4F4F4;height:35px;line-height:35px;padding-left:3px;">
	 			<a id="submit-search" onclick="setAjaxValues()" data-options="plain:true" class="easyui-linkbutton" iconCls="icon-ok">选择人员</a>
	 			<a id="submit-search" onclick="$('#dlg').dialog('close')" data-options="plain:true" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
		</div>
		 <table>
		       <tr>
		            <td width="150" style="vertical-align: top;">
					<ul id="treeDemo"  class="ztree" style="overflow-y:hidden;padding-bottom:15pt;width:100%;vertical-align: top;"></ul>
					</div>
		            </td>
					<td width="15">&nbsp;</td>
		            <td width="150">
		            <div id="treedatas" style="height:220x !important;width:100%;" >
				       &nbsp;
					</div>
		            </td>
		       </tr>
		 </table>       
	 </div>     
</body>
</html>
<script language="javascript">
var valueName="";
var varids="";
var selectedId = "";
function onCheck(e, treeId, treeNode) 
{
    var  phototListHtml="";
	if(treeNode.isParent && treeNode.checked)
	{
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		if(treeNode.children=="")
		{
		   treeObj.reAsyncChildNodes(treeNode, "refresh");
		}
	}
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getCheckedNodes(true),
    v = "";
    uid=new Array();
    var k=0;
    for(var i=0, l=nodes.length; i<l; i++)
    {
         if(nodes[i].isParent!=true)
         {
             v += nodes[i].name + "<br/>";
			 if(valueName.indexOf(nodes[i].name)<0){
				valueName +=nodes[i].name+",";
			 }	             
             varids +=nodes[i].nid + ",";
             uid[k]=nodes[i].nid;   //获取选中节点的id值
             k++; 
         }
    }
	phototListHtml = phototListHtml + "<div id='deptUserTemp' style='height: 220px;width:99%;overflow: auto;border:solid 1px #95B8E7 ;'>"+v+"</div>";
	$("#treedatas").html("");
	$("#treedatas").append(phototListHtml);
}

function setAjaxValues(){		
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getCheckedNodes(true),
		relText="";
	var relCode = "";
    for(var i=0, l=nodes.length; i<l; i++)
    {
         if(nodes[i].isParent!=true)
         {
			 if(relText.indexOf(nodes[i].name)<0){
				relText +=nodes[i].name+",";
				relCode += nodes[i].nid +"|";
			 }	             
         }
    }	
	$.ajax({
		url:'area/saveUsers.htm?id='+selectedId+"&users="+relCode,
		dataType:'json',
		success:function(data){
			$('#dlg').dialog('close');
			$('#dg').datagrid('reload');
		},
		cache:false				
	});
	
}	

$(document).ready(function(){
	var setting = {
			check: {
				enable: true,
				chkStyle: "checkbox"
			},
			data: 
			{
				simpleData: {
					enable: true,
					idKey: "nid",
					pIdKey: "nPid",
					name:"name",
					rootPId: 0,
				}
		    },
			callback: {
				onCheck: onCheck
			}
		  };	    
    var selData1=null;
	if(document.getElementById("jsonStr").value!=null&&
		document.getElementById("jsonStr").value!="")
	eval("selData1="+document.getElementById("jsonStr").value);
	$.fn.zTree.init($("#treeDemo"), setting, selData1);		
	
	if($("#userNameSeleced").size() && $("#userNameSeleced").val().length > 0 ){
		var nameSelected = $("#userNameSeleced").val();
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");		
		if(nameSelected.lastIndexOf(",")+1 == nameSelected.length){
			nameSelected = nameSelected.substring(0,nameSelected.length-1);
		}
		var nameArr = nameSelected.split(",");
		for(var i = 0,j= nameArr.length;i<j;i++){
			var node = zTree.getNodesByParam("name", nameArr[i], null);
			zTree.checkNode(node[0],true, true, true);
		}
	}
})

function selectUsers(id){
	$('#dlg').dialog({title:'员工选择'}).dialog('open');
	 if(selectedId != id)
     {//清空已经选中的用户
		 var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		    treeObj.cancelSelectedNode();
		  $("#treedatas").html("");
	  } 
	  
	 
	  selectedId = id;
}

function CancelSelected(event) {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.cancelSelectedNode();
    var nodes = treeObj.getNodes();
    for (var i = 0; i < nodes.length; i++) {
        EachChildNodes(nodes[i], treeObj);
    }
}
//递归遍历取消子节点的选择
function EachChildNodes(node,treeObj) {
    //首先取消当前节点的选择
    treeObj.cancelSelectedNode(node);
    //判断是否有子节点
    if (node.childs == undefined)
        return;
    for (var i = 0; i < node.childs.length; i++) {
        EachChildNodes(node.childs[i], treeObj);
    }
}
</script>