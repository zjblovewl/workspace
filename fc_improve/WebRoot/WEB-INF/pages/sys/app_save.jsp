<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>桌面应用List</title>
</head>
<script type="text/javascript">
(function($){
   $(function(){
	   //保存
	   $("a[iconCls='icon-save']").click(function(){
		   $('#ff').form('submit',{
			   
			   success : function(data) {
			   	if(myself.ajaxCheck(data)){
			   		if(data == 'exist'){
						  myself.msgShow('提示','应用名称已经存在，请更换应用名称!','info');
					  }else if(data == 'no'){
						  myself.msgShow('提示','创建应用失败,请重试!','error');
				      }else{
					   	  myself.location('app/search.htm?m=${m}');
					  }
				}
			   }
		   });
	   });
	   //返回
       $("a[iconCls='icon-back']").click(function(){
			myself.location('app/search.htm?m=${m}');
       });

    });
})(jQuery);
</script>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
       <div id="tb" style="padding: 5px; height: auto; " class="datagrid-toolbar">
		<div style="margin-bottom:5px">
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-back" plain="true">返回</a>
		</div>
	</div>
	<div style="padding:10px 0 10px 60px">
    <form id="ff" method="post" action="app/save.htm">
    <input type="hidden" name="appId" value="${app.appId}"/>
    	<table width="95%">
    		<tr>
    			<td width="10%" nowrap="nowrap">应用名称:</td>
    			<td width="40"><input class="easyui-validatebox" value="${app.appName}" type="text" style="width:200px;" name="appName" maxlength="16" data-options="required:true"  validType="name"></input></td>
    			<td width="10%" nowrap="nowrap">应用链接:</td>
    			<td width="40"><input class="easyui-validatebox" value="${app.appLink}" type="text" style="width:200px;" name="appLink" data-options="required:true"  validType="url"></input></td>
    		</tr>
    		<tr>
    			<td width="10%" nowrap="nowrap">应用显示高度:</td>
    			<td nowrap="nowrap">
    				<input class="easyui-validatebox" value="${app.appHeight}" type="text" style="width:200px;" name="appHeight" maxlength="20" data-options="required:true"  validType="number"></input>
    				<label style="color: red">0表示自适应高度</label>
    			</td>
    			<td>支持最小化</td>
    			<td>
    				<select class="easyui-combobox" id="appIsSmall" name="appIsSmall" data-options="editable:false,width:200,required:true,panelHeight:'auto'">
						<option value="0" <c:if test="${app.appIsSmall=='0'}">selected</c:if>>是</option>
						<option value="1" <c:if test="${app.appIsSmall=='1'}">selected</c:if>>否</option>
					</select>  
    			</td>
    		</tr>
    		<!-- 
    		<tr>
    			<td>应用图标:</td>
    			<td colspan="3"><input class="easyui-validatebox" value="${app.appIcon}" type="text" style="width:200px;" name="appIcon" maxlength="20" data-options="required:true"  validType="name"></input></td>
    		</tr>
    		 -->
    		<tr>
    			<td>应用说明:</td>
    			<td colspan="3">
    				<textarea rows="5" style="width: 80%" name="appDesc">${app.appDesc}</textarea>
    			</td>
    		</tr>
    	</table>
    </form>
    </div>
</div>
</body>
</html>