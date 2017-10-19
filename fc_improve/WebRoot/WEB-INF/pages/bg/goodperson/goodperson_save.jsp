<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汶南好人</title>
<style>
.crm-input { width: 200px; height: 30px; border: 1px solid #d3d3d3; float: left; background-color: #fff; margin-right:15px; }
.a-upload { width:100px; height: 32px; line-height: 32px; position: relative; cursor: pointer; color: #888; background: #fafafa; border: 1px solid #ddd;overflow: hidden; display: inline-block;  *display: inline;*zoom: 1; 
 float:left; font-size:12px; text-align:center;}
.a-upload input { position: absolute; font-size: 20px; right: 0; top: 0; opacity: 0; filter: alpha(opacity=0); cursor: pointer; width:100px; }
</style>
</head>
<script type="text/javascript" src="js/ueditor/ueditor.wx.material.config.fc.js?a=33"></script>
<script type="text/javascript" src="js/ueditor/ueditor.all.js?a=355545564"></script>
<script type="text/javascript" src="js/ueditor/lang/zh-cn/zh-cn.js"></script>
<body>

<div style="padding:10px 0 10px 60px">
    <form id="saveform" method="post" action="goodpersion/save.htm" enctype="multipart/form-data">
    <input type="hidden" name="id" id="id" value="${bean.id}"/>
    <input type="hidden" name="action" value="${action}">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">标题:</td>
		    			<td>
		    					<input class="easyui-validatebox crm-input" required="true" missingMessage="标题不能为空" maxlength="32" value="${bean.title}" type="text" style="width:200px;" name="title"  id="title" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">发布时间:</td>
		    			<td>
		    				<input class="easyui-datetimebox" value="${bean.createTime }" data-options="required:true,showSeconds:false" name="createTime" style="width: 200px; height: 30px; border: 1px solid #d3d3d3; float: left; background-color: #fff; margin-right:15px;">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">附件:</td>
		    			<td>
		    				<input class="crm-input" type="text"  disabled="disable" title="${accessoryPath }" value="文件名" id="upfileinnerOne">
		    				<script type="text/javascript">
		    					var accessoryPath = '${accessoryPath}';
		    					var strFullPath = window.document.location.href;
		    					var strPath = window.document.location.pathname;
		    					var pos = strFullPath.indexOf(strPath);
		    					var prePath = strFullPath.substring(0, pos);
		    					var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
		    					var path = prePath+postPath+"/";
		    					if(accessoryPath!=''){
		    						$("#downloadAccessory").show();
		    						$("#downloadAccessoryDiv").show();
		    						$("#upfileinnerOne").val(accessoryPath);
		    						$("#downloadAccessory").attr('href',path+accessoryPath);
		    					}
		    				</script>
		    				
							<div class="a-upload">
							  <input type="file" name="upFileInnerOne" onchange="document.getElementById('upfileinnerOne').value=this.value" ><span style="color: black;">浏览...</span>
							</div>
							
							<div class="a-upload" id="downloadAccessoryDiv" style="margin-left: 15px;display: none;">
							  <a href="" id="downloadAccessory" style="display: none;">下载附件</a>
							</div>
							
							<div style="margin-left: -32px;width:250px; height: 32px; line-height: 32px;font-size:14px; position: relative; cursor: pointer; color: black; overflow: hidden; display: inline-block;  *display: inline;*zoom: 1;float:left; font-size:12px; text-align:center;">
							  <span>仅支持world、excel、pdf文件上传</span>
							</div>
		    			</td>
		    		</tr>
		    		
<!-- 		    		<tr> -->
<!-- 		    			<td width="10%" nowrap="nowrap">视频:</td> -->
<!-- 		    			<td> -->
<%-- 		    				<input class="crm-input" type="text" disabled="disable" title="${videoPath }" value="文件名" id="upfileinnerSecond"> --%>
<!-- 		    				<script type="text/javascript"> -->
<%-- // 			    				var videoPath = '${videoPath}'; --%>
<!-- // 		    					var strFullPath = window.document.location.href; -->
<!-- // 		    					var strPath = window.document.location.pathname; -->
<!-- // 		    					var pos = strFullPath.indexOf(strPath); -->
<!-- // 		    					var prePath = strFullPath.substring(0, pos); -->
<!-- // 		    					var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1); -->
<!-- // 		    					var path = prePath+postPath+"/"; -->
<!-- // 		    					if(videoPath!=''){ -->
<!-- // 		    						$("#downVideo").show(); -->
<!-- // 		    						$("#downVideoDiv").show(); -->
<!-- // 		    						$("#upfileinnerSecond").val(videoPath); -->
<!-- // 		    						$("#downVideo").attr('href',path+videoPath); -->
<!-- // 		    					} -->
<!-- 		    				</script> -->
<!-- 							<div class="a-upload"> -->
<!-- 							  <input type="file" name="upFileInnerTwo" onchange="document.getElementById('upfileinnerSecond').value=this.value" ><span style="color: black;">浏览...</span> -->
<!-- 							</div> -->
							
<!-- 							<div class="a-upload" id="downVideoDiv" style="margin-left: 15px;display: none"> -->
<!-- 							  <a href="" id="downVideo" style="display: none;">下载视频</a> -->
<!-- 							</div> -->
							
<!-- 							<div style="margin-left: -32px;width:250px; height: 32px; line-height: 32px;font-size:14px; position: relative; cursor: pointer; color: black; overflow: hidden; display: inline-block;  *display: inline;*zoom: 1;float:left; font-size:12px; text-align:center;"> -->
<!-- 							  <span>仅支持视频文件上传</span> -->
<!-- 							</div> -->
<!-- 		    			</td> -->
<!-- 		    		</tr> -->
		    		
		    		<tr>
		    			<td width="10%" nowrap="nowrap">内容:</td>
		    			<td>
		    				<textarea name="content" id="contentId" >${bean.content}</textarea>
		    			</td>
		    		</tr>
    	</table>
    </form>
    </div>
</body>
</html>

<script type="text/javascript">
  
  $(function(){
	  /*********************************初始化副本本编辑器*******************************/
		var ue;	 
		$(function(){
		    //实例化编辑器
		    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		    ue = UE.getEditor('contentId',{
		        initialFrameWidth : 700,
		        initialFrameHeight: 240
		    });
		    ue.addListener('afterSelectionChange', function( editor ) {
			});
		});
		/*************************************end***********************************/
  });
  
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