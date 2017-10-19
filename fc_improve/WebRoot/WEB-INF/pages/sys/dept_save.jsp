<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<title>部门信息保存</title>
</head>
<script type="text/javascript">

var operator = '${operator}';

(function($){
   $(function(){
		$('#icon-save').click(function(){
			$('#ff').form('submit',{
			   success : function(data) {
			      if(myself.ajaxCheck(data)){
			    	  if(data.indexOf('true') != -1){
						  myself.msgShow('提示','保存部门信息成功!','info');
						  if(operator == 'add'){
							var deptId = data.substring(data.indexOf('_')+1);  
							//新增
							parent.myself.appendTree(deptId,$('#deptName').val());
							
						  }else{
							//修改
							var tree = parent.document.getElementById('tree');
							var node = $(tree).tree('getSelected');
							if(node){
								$(tree).tree('update', {
									target: node.target,
									text: $('#deptName').val()
								});
							}
						  }	
						  
					  }else if(data == 'exist'){
						  myself.msgShow('提示','上级部门下已经存在同名部门,请更换部门名称!','error');
				      }else{
					   	  alert(data);
					  }
				  }
			   }
		   });
		});	
			
	});
})(jQuery);
</script>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
        <div id="tb" style="padding: 5px; height: auto; " class="datagrid-toolbar">
			${functionStr}
		</div>
		<div style="padding:10px 0 10px 60px">
	    <form id="ff" method="post" action="dept/save.htm">
	    	<input type="hidden" name="deptId" value="${dept.deptId}">
	    	<input type="hidden" name="deptLevel" value="${dept.deptLevel}">
	    	<table class="edit-table" style="width:90%;line-height:30px;">
	    		<tr>
	    			<td class="edit-table-header">部门名称:</td>
	    			<td><input class="easyui-validatebox" value="${dept.deptName}" type="text" style="width:80%;" id="deptName" name="deptName" maxlength="20" data-options="required:true"></input></td>
	    			<td class="edit-table-header">上级部门:</td>
	    			<td>
	    				<input type="hidden" value="${dept.PDeptId}" name="pDeptId"/>
	    				${dept.PDeptName}
	    			</td>	
	    		</tr>
	    		<tr>
	    			<td class="edit-table-header">部门简称:</td>
	    			<td>
	    				<input class="easyui-validatebox" value="${dept.deptShortName}" type="text" style="width:80%;" name="deptShortName" maxlength="20"></input>
	    			</td>
	    			<td class="edit-table-header"></td>
	    			<td>
	    				
	    			</td>
	    		</tr>
	    		<tr>
	    			<td class="edit-table-header">部门电话:</td>
	    			<td><input class="easyui-validatebox" value="${dept.deptPhone}" type="text" style="width:80%;" name="deptPhone" maxlength="20" validType="number"></input></td>
	    			<td class="edit-table-header">部门传真:</td>
	    			<td><input class="easyui-validatebox" value="${dept.deptFax}" type="text" style="width:80%;" name="deptFax" maxlength="20" validType="number"></input></td>
	    		</tr>
	    		<tr>
	    			<td class="edit-table-header">部门地址:</td>
	    			<td><input class="easyui-validatebox" value="${dept.deptAddress}" type="text" style="width:80%;" name="deptAddress" maxlength="20" validType="name"></input></td>
	    			<td class="edit-table-header">部门正职:</td>
	    			<td><input class="easyui-validatebox" value="${dept.deptLeader}" type="text" style="width:80%;" name="deptLeader" maxlength="20"></input></td>
	    		</tr>
	    		<tr>
	    			<td class="edit-table-header">部门说明:</td>
	    			<td colspan="3"><textarea name="deptRemark" style="height:60px;width:80%;" onKeyDown="if(this.value.length > 1024) this.value=this.value.substr(0,1024)" onKeyUp="if(this.value.length > 1024) this.value=this.value.substr(0,1024)">${dept.deptRemark}</textarea></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
</body>
</html>