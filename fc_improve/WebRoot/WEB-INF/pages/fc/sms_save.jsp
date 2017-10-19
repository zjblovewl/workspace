<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="js/zTree3.3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.core-3.3.js"></script>
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.excheck-3.3.js"></script>
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.exedit-3.3.js"></script>
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.exhide-3.3.js"></script>
<title>短信信息</title>
</head>
<body>
<div>
    <form id="saveform" method="post" action="sms/save.htm">
    <input type="hidden" name="id" id="id" value="${bean.id}"/>
    <input type="hidden" name="action" value="${action}">
    <input type="hidden" id="jsonStr" value="${jsonStr}">
    	<table width="95%">
		    		<tr>
		    			<td width="7%" nowrap="nowrap">接收人:</td>
		    			<td>
		    				<div>
			    				<div style="float: left;">
			    					<textarea style="width:250px;height:80px"  name="sendUsers"  id="userNameSeleced" readonly="readonly">${bean.sendUsers}</textarea>
			    					<input id="userIdSeleced" name="createUserIds"  value="" type="hidden"/>
			    				</div>	
			    				<div style="float: left;">
			    					&nbsp;<a id="submit-search" onclick="selectUsers()" data-options="plain:true" class="easyui-linkbutton" iconCls="icon-choose-user">选择人员</a>
			    				</div>	
			    			</div>		    					
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="7%" nowrap="nowrap">短信内容:</td>
		    			<td>
		    					<textarea style="width:320px;height:80px"  name="content"  id="content">${bean.content}</textarea>
		    			</td>
		    		</tr>
    	</table>
    </form>
    </div>
	 <div id="dlg" class="easyui-dialog" closed="true" style="width:500px;height:420px" scrolling="false" data-options="closable:true">
	 	<div id="dlg-buttons" align="left" style="background:#F4F4F4;height:35px;line-height:35px;padding-left:3px;">
	 			<a id="submit-search" onclick="getValues()" data-options="plain:true" class="easyui-linkbutton" iconCls="icon-ok">选择人员</a>
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
<script type="text/javascript">
function selectUsers(){
	  $('#dlg').dialog({title:'员工选择'}).dialog('open');
}

valueName="";
varids="";
uid=new Array();
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

function getValues(){		
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getCheckedNodes(true),
		relText=[],
		relIds=[];
    for(var i=0, l=nodes.length; i<l; i++)
    {
         if(nodes[i].isParent!=true)
         {
			 if(relText.indexOf(nodes[i].name)<0){
				relText.push(nodes[i].name);
				relIds.push(nodes[i].nid);
			 }	             
         }
    }	
	$("#userNameSeleced").val(relText.join(","));
	$("#userIdSeleced").val(relIds.join(","));
	
	$('#dlg').dialog('close');
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
	
	/*if($("#userNameSeleced").size() && $("#userNameSeleced").val().length > 0 ){
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
	}*/
})
</script>