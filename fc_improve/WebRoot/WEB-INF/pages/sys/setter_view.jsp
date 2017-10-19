<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统设置-详情查看</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">设置名称:</td>
		    			<td>
		    					<div>
		    						${bean.setterName}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">设置值:</td>
		    			<td>
		    					<div>
		    						${bean.setterValue}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">设置说明:</td>
		    			<td>
		    					<div>
		    						${bean.setterRemark}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">创建人:</td>
		    			<td>
		    					<div>
		    						${bean.createUser}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">创建时间:</td>
		    			<td>
		    					<div>
		    						${bean.createDate}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">最后更新人:</td>
		    			<td>
		    					<div>
		    						${bean.updateUser}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">最后更新时间:</td>
		    			<td>
		    					<div>
		    						${bean.updateDate}
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
