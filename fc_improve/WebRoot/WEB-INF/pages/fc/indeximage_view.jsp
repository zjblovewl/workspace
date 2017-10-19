<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页图片-详情查看</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">首页图片:</td>
		    			<td>
		    					<div>
		    						<img src="${bean.imagePath}"/>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr style="display:none">
		    			<td width="10%" nowrap="nowrap">点击路径:</td>
		    			<td>
		    					<div>
		    						${bean.clickUrl}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr style="display:none">
		    			<td width="10%" nowrap="nowrap">内容:</td>
		    			<td>
		    					<div>
		    						${bean.content}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr style="display:none">
		    			<td width="10%" nowrap="nowrap">创建日期:</td>
		    			<td>
		    					<div>
		    						${bean.createDate}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr style="display:none">
		    			<td width="10%" nowrap="nowrap">创建人:</td>
		    			<td>
		    					<div>
		    						${bean.createUser}
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
