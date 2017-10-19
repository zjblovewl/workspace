<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>事件上报-详情查看</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">群众姓名:</td>
		    			<td>
		    					<div>
		    						${bean.appealerName}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">群众电话:</td>
		    			<td>
		    					<div>
		    						${bean.appealerPhone}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">群众地址:</td>
		    			<td>
		    					<div>
		    						${bean.appealerAddress}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">问题描述:</td>
		    			<td>
		    					<div>
		    						${bean.description}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">处理结果:</td>
		    			<td>
		    					<div>
		    						${bean.appealResult}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">问题取照:</td>
		    			<td>
		    					<div>
		    						<c:forEach items="${images}" var="image">
		    							<img src="${image}" height="140px">
		    						</c:forEach>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">驻村(小区)联络员:</td>
		    			<td>
		    					<div>
		    						${bean.reporterName}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">上报时间:</td>
		    			<td>
		    					<div>
		    						${bean.reportTime}
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
