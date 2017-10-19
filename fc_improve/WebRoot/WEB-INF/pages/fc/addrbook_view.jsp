<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯录-详情查看</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">姓名:</td>
		    			<td>
		    					<div>
		    						${bean.name}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">部门:</td>
		    			<td>
		    					<div>
		    						${bean.department}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">手机号:</td>
		    			<td>
		    					<div>
		    						${bean.phoneNum}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">镇街/社区:</td>
		    			<td>
		    					<div>
		    						${bean.town}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">村/小区:</td>
		    			<td>
		    					<div>
		    						${bean.village}
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
