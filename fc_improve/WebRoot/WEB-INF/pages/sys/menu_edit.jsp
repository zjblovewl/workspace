<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/include.htm"/>
<link rel="stylesheet" type="text/css" href="css/menuicon.css"/>
<title>菜单编辑</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		$('#choose').click(function(){
			$('#iconChoose').css('display',$('#iconChoose').css('display') == 'none' ? '' : 'none');
		});

		$('.allicon').mouseover(function(){
			$(this).attr('border','1px');
		}).mouseout(function(){
			$(this).attr('border','0px');
		}).click(function(){
			$('#menuIcon').val($(this).attr('icon'));
			$('#icon').attr('src','images/menuicons/'+$(this).attr('icon'));
			$('#iconChoose').css('display','none');
		});

		$('#icon-save').click(function(){
			if($('#pMenuId').val() == '0' || $('#pMenuId').val() == ''){
				if($('#menuLink').val()!=''){
					myself.msgShow('提示','顶级菜单不需要填写菜单链接!');
					return;	
				}
				$('#menuLink').validatebox({   
				    required: false  
				}); 	
			}else{
				$('#menuLink').validatebox({   
				    required: true  
				});
			}
			$.ajax({
        		url:'menu/checkMenuNameExist.htm',
        		data:'pMenuId='+$('#pMenuId').val()+'&menuId='+$('#menuId').val()+'&menuName='+$.trim($('#menuName').val()),		
        		success:function(data){
        			if(data=='true')
        			{
        				myself.msgShow('提示','同级已经存在同名菜单，请更换!','error');
        			}else
            		{
        				$('#ff').form('submit',{
        					   success : function(data) {
        					      if(data == 'true'){
        							  myself.msgShow('提示','保存菜单信息成功!');
        							  myself.location('menu/search.htm?m=${m}');
        						  }else{
        					    	  myself.msgShow('提示','保存菜单出错，请重新保存!','error');
        						  }
        					   }
        				   });
	        		}
        		},
        		cache:false				
        	});
			

		});

		$('#icon-back').click(function(){
			myself.location('menu/search.htm?m=${m}');
		});
	});
})(jQuery);
function changeHandler(node){
	$('#pMenuId').val(node.menuId);
}
</script>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
       <div id="tb" style="padding: 5px; height: auto; " class="datagrid-toolbar">
		<div>
			<a href="javascript:;" class="easyui-linkbutton" id="icon-save" iconCls="icon-save" plain="true">保存</a>
			<a href="javascript:;" class="easyui-linkbutton" id="icon-back" iconCls="icon-back" plain="true">返回</a>
		</div>
	</div>
	<div style="padding:10px 0 10px 60px">
    <form id="ff" method="post" action="menu/save.htm">
    	<input type="hidden" name="operator" value="${operator}" />
    	<input type="hidden" id="menuId" name="menuId" value="${menu.menuId}"/>
    	<input type="hidden" name="menuOrder" value="${menu.menuOrder }"/>
    	<table class="table-edit" style="width:90%;line-height:30px;border:1px;border-width: 1px;">
    		<tr>
    			<td align="right">菜单名称:</td>
    			<td id="content-td"><input class="easyui-validatebox" id="menuName" value="${menu.menuName}" type="text" style="width:80%;" name="menuName" maxlength="20" data-options="required:true"  validType="name"></input></td>
    			<td align="right">上级菜单:</td>
    			<td>
    				<input type="hidden" name="pMenuId" id="pMenuId" value="${menu.PMenuId}">
    				<input value="${menu.PMenuId=='0'?'':menu.PMenuId}" class="easyui-combobox" data-options="url:'menu/queryTopMenus.htm',valueField:'menuId',textField:'menuName',onSelect:changeHandler,editable:false,panelHeight:'auto'">
    				
    			</td>	
    		</tr>
    		<tr>
    			<td align="right">菜单图标:</td>
    			<td>
    				<input type="hidden" name="menuIcon" id="menuIcon" value="${menu.menuIcon}"/>
    				 
    				<img src="images/menuicons/${menu.menuIcon}" width="16" height="16" id="icon">
    				
    				<a id="choose" href="javascript:;" class="easyui-linkbutton" plain="true">选择</a>
    			</td>
    			<td align="right">菜单链接:</td>
    			<td>
    				<input class="easyui-validatebox" id="menuLink" value="${menu.menuLink}" type="text" style="width:80%;" name="menuLink" maxlength="50">   				
    			</td>
    		</tr>
    		<tr id="iconChoose" style="display:none;">
    			<td>&nbsp;</td>
    			<td colspan="3" valign="top">
    				<table border="0" cellpadding="0" cellspacing="0">
    					<tr style="height: 30px;text-align: center;">
    					<c:forEach items="${icons}" var="icon" varStatus="s">
    					<c:if test="${s.index%10 == 0 && s.index!=0}">
    						</tr>
    						<tr style="height: 30px;text-align: center;">
    					</c:if>
    					
    						<td valign="middle" style="width:25px;">
    							<img src="images/menuicons/${icon}" icon="${icon}" class="allicon" width="16" height="16" style="cursor: pointer;border-color: #cccccc">	
    						</td>
    					
    					</c:forEach>
    					</tr>
    				</table>
    			</td>
    			
    		</tr>
    	</table>
    </form>
    </div>
</div>
</body>
</html>