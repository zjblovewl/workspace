<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签到签退信息-详情查看</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">${bean.typeStr}用户:</td>
		    			<td>
		    					<div>
		    						${bean.userId}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">${bean.typeStr}类型:</td>
		    			<td>
		    					<div>
		    						${bean.typeStr}
		    					</div>
		    			</td>
		    		</tr>
		    		<!-- 
		    		<tr>
		    			<td width="10%" nowrap="nowrap">${bean.typeStr}地址:</td>
		    			<td>
		    					<div>
		    						${bean.address}
		    					</div>
		    			</td>
		    		</tr>
		    		 -->
		    		<tr>
		    			<td width="10%" nowrap="nowrap">所属区域:</td>
		    			<td>
		    					<div>
		    						${bean.handAddress}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">图片信息:</td>
		    			<td>
		    					<div>
		    						<img alt="" src="data:image/png;base64,${bean.imgPath}" style="width:200px">
		    						
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">${bean.typeStr}时间:</td>
		    			<td>
		    					<div>
		    						${bean.signTime}
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
