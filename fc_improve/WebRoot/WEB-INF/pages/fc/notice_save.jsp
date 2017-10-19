<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="js/zTree3.3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.core-3.3.js"></script>
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.excheck-3.3.js"></script>
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.exedit-3.3.js"></script>
<script type="text/javascript" src="js/zTree3.3/js/jquery.ztree.exhide-3.3.js"></script>
<title>通知公告</title>
<style>
</style>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    <form id="saveform" method="post" action="notice/save.htm">
    <input type="hidden" name="id" id="id" value="${bean.id}"/>
    <input type="hidden" name="action" value="${action}">
    <input type="hidden" id="jsonStr" value="${jsonStr}">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">标题:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="256" value="${bean.title}" type="text" style="width:200px;" name="title"  id="title" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">发布时间:</td>
		    			<td>
		    					<input type="text" class="easyui-datetimebox"  editable="false"  name="pubTime"  id="pubTime" value="${bean.pubTime}"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">人员:</td>
		    			<td>
		    				<div style="float: left;">
		    					<input id="userNameSeleced" name="relUserName" width="200"  readonly="readonly" value="${bean.relUserName}"/>
		    					<input id="userIdSeleced" name="relUser"  value="${bean.relUser}" type="hidden"/>
		    				</div>	
		    				<div style="float: left;">
		    					&nbsp;<a id="submit-search" onclick="selectUsers()" data-options="plain:true" class="easyui-linkbutton" iconCls="icon-choose-user">选择人员</a>
		    				</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">公告图片:</td>
		    			<td>
		    					<input class="easyui-validatebox" type="file" style="width:200px;" name="pubImageFile"  id="pubImageFile"></input>
		    					<div style="width:200px">
		    					<c:if test="${bean.pubImage != '' && bean.pubImage != null}">
									<img src="${bean.pubImage}" height="150"/>
		    					</c:if>
		    					</div>	
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">附件:</td>
		    			<td>
		    					<input class="easyui-validatebox" type="file" style="width:200px;" name="noticeFileFile"  id="noticeFile"></input>
		    					<div style="width:200px">
		    					<c:if test="${bean.noticeFile != '' && bean.noticeFile != null}">
									<a target="_blank" href="${bean.noticeFile}"  style="color:blue">附件下载</a>
		    					</c:if>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">短信提醒:</td>
		    			<td>
		    					<input id="sendMsg" name="sendMsg" value="${bean.sendMsg}" class="easyui-combobox" data-options="url:'dic/listDicValues.htm?dicId=yesno',valueField:'dicValueId',textField:'dicValueLabel',required:true,editable:false,panelHeight:'auto',width:200">
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td width="10%" nowrap="nowrap">作者:</td>
		    			<td>
		    					<input class="easyui-validatebox" maxlength="32" value="${bean.author}" type="text" style="width:200px;" name="author"  id="author" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">内容:</td>
		    			<td>
		    			   		<input type="hidden" name="content"  id="content"/>
		    					<script id="contentEditor" type="text/plain" style="width:540px;height:230px;">${bean.content}</script>
		    			</td>
		    		</tr>
    	</table>
    </form>
	 <div id="dlg" class="easyui-dialog" closed="true" style="width:550px;height:420px" scrolling="false" data-options="closable:true">
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
</div>    
</body>
</html>
<script type="text/javascript">
	valueName="";
	varids="";
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
	
	function getValues(){		
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			relText="";
		var userIds = ",";	
	    for(var i=0, l=nodes.length; i<l; i++)
	    {
	         if(nodes[i].isParent!=true)
	         {
				 if(relText.indexOf(nodes[i].name)<0){
					relText +=nodes[i].name+",";
					userIds+=nodes[i].nid + ",";
				 }	             
	         }
	    }	
		
		$("#userNameSeleced").val(relText);
		$("#userIdSeleced").val(userIds);
		
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

	function selectUsers(){
		  $('#dlg').dialog({title:'员工选择'}).dialog('open');
	}

    var contentEditor = UE.getEditor('contentEditor');
  
  //加入校验功能
  function showProgressDialog(){
	if($("#progressDialog").size() == 0){
		var s = $("<div id=\"progressDialog\" style=\"width:400px;height:300px;z-index:100000;text-align:center; border:0;\">正在处理中。。。</div>");
		$('body').append(s);
	}
	$("#progressDialog").dialog({
		modal: true,
		closeOnEscape: false,
		draggable: false,
		resizable: false,
		hide: 'slide',
		forceClose:true,
		forceOpen:true,
		forceDestroy:true,
		zIndex:100000,
		closable: false,
		width: 135,
		height: 30,
		title: ""
	});
}
/**
 * @author Grahor
 * 操作成功后 销毁模态窗口
 */
top.destroyProgressDialog = function(){
	try{
		$("#progressDialog").dialog("destroy");
	}catch(e){
		
	}
}
top.formValidate = function(){
	var r = $("#saveform").form("validate");
	if (r) {
		showProgressDialog();
	}
	return r;
}
</script>