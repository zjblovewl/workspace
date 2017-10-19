<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<jsp:include page="/include.htm"/>
<title>增加角色</title>
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
						  myself.msgShow('提示','角色名称已经存在,请更换角色名称!','info');
					  }else if(data == 'no'){
						  myself.msgShow('提示','创建角色失败,请重试!','error');
				      }else{
					   	  myself.location('role/search.htm?m=${m}');
					  }
				}
			   }
		   });
	   });
	   //返回
       $("a[iconCls='icon-back']").click(function(){
			myself.location('role/search.htm?m=${m}');
       });

    });
})(jQuery);
</script>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
        <div id="tb" style="padding: 5px; height: auto; " class="datagrid-toolbar">
			<div style="margin-bottom:5px">
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存角色</a>
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-back" plain="true">返回</a>
			</div>
		</div>
		<div style="padding:10px 0 10px 60px">
	    <form id="ff" method="post" action="role/save.htm">
	    <input type="hidden" name="roleId" value="${role.roleId}"/>
	    	<table class="edit-table" style="width:90%;line-height:30px;">
	    		<tr>
	    			<td class="edit-table-header">角色名称:</td>
	    			<td><input class="easyui-validatebox" value="${role.roleName}" type="text" style="width:300px;" name="roleName" maxlength="20" data-options="required:true"  validType="name"></input></td>
	    		</tr>
	    		<tr>
	    			<td class="edit-table-header">角色说明:</td>
	    			<td><textarea name="roleRemark" style="height:40px;width:300px;" onKeyDown="if(this.value.length > 256) this.value=this.value.substr(0,256)" onKeyUp="if(this.value.length > 256) this.value=this.value.substr(0,256)">${role.roleRemark}</textarea></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
</body>
</html>