<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户终端信息-详情查看</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">型号名称:</td>
		    			<td>
		    					<div>
		    						${bean.phoneName}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">手机版本:</td>
		    			<td>
		    					<div>
		    						${bean.phoneVersion}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">软件版本:</td>
		    			<td>
		    					<div>
		    						${bean.softVersion}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">IMSI号:</td>
		    			<td>
		    					<div>
		    						${bean.imsi}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">IMEI号:</td>
		    			<td>
		    					<div>
		    						${bean.imei}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">登录帐号:</td>
		    			<td>
		    					<div>
		    						${bean.loginName}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">所在地:</td>
		    			<td>
		    					<div>
		    						${bean.ownship}
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
