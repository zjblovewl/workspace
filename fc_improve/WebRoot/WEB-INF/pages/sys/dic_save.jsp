<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<html>
<head>
<title>保存字典信息</title>
</head>
<script type="text/javascript">
(function($){
  $(function(){
	$('#icon-save').click(function(){
		$('#saveform').form('submit',{
			   success : function(data) {
					if(myself.ajaxCheck(data)){
						if(data){
							myself.msgShow('提示','成功保存字典信息!');
							callHandler();
						}
					}
			   }
		   });
	});
  });
})(jQuery);
</script>
<body>
<form action="dic/save.htm" method="post" id="saveform">
<input type="hidden" name="flag" value="<c:if test="${dic.dicId == null}">add</c:if>"/>
<table width="100%" style="padding-top:10px;">
<tr>
	<td align="right">字典编码：</td>
	<td><input type="text" value="${dic.dicId}" <c:if test="${dic.dicId != null}">readonly="readonly"</c:if> style="width:90%" name="dicId" class="easyui-validatebox" maxlength="16" data-options="required:true"  validType="letter"/></td>
</tr>
<tr>
	<td align="right">字典名称：</td>
	<td><input type="text" value="${dic.dicName}" style="width:90%" name="dicName" class="easyui-validatebox" maxlength="16" data-options="required:true"  validType="name"/></td>
</tr>
</table>
</form>
</body>
</html>