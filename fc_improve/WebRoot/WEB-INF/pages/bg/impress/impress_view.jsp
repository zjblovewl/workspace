<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>印象汶南-详情</title>
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
		    			<td width="10%" nowrap="nowrap">视频:</td>
		    			<td>
		    				<input class="crm-input" type="text" disabled="disable" value="文件名" id="upfileinnerSecond">
		    				<script type="text/javascript">
			    				var videoPath = '${videoPath}';
		    					var strFullPath = window.document.location.href;
		    					var strPath = window.document.location.pathname;
		    					var pos = strFullPath.indexOf(strPath);
		    					var prePath = strFullPath.substring(0, pos);
		    					var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
		    					var path = prePath+postPath+"/";
		    					if(videoPath!=''){
		    						$("#downVideo").show();
		    						$("#downVideoDiv").show();
		    						$("#upfileinnerSecond").val(videoPath);
		    						$("#downVideo").attr('href',path+videoPath);
		    					}
		    				</script>
<!-- 							<div class="a-upload"> -->
<!-- 							  <input type="file" name="upFileInnerTwo" onchange="document.getElementById('upfileinnerSecond').value=this.value" ><span style="color: black;">浏览...</span> -->
<!-- 							</div> -->
							
							<div class="a-upload" id="downVideoDiv" style="margin-left: 15px;display: none">
							  <a href="" id="downVideo" style="display: none;">下载视频</a>
							</div>
		    			</td>
		    		</tr>
		    		
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
</script>

