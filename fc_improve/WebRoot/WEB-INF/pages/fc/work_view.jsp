<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作记录-详情查看</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    	<table width="95%">
    				<tr>
		    			<td width="10%" nowrap="nowrap">工作名称:</td>
		    			<td>
		    					<div>
		    						${bean.performance}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">会议纪要:</td>
		    			<td>
		    					<div>
		    						${bean.meeting}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">会议照片:</td>
		    			<td>
		    					<div>
		    						<c:forEach items="${images}" var="image">
		    							<img src="${image}" height="140px">
		    						</c:forEach>
		    						
		    					</div>
		    			</td>
		    		</tr>
		    		<!-- 
		    		<tr>
		    			<td width="10%" nowrap="nowrap">会议地点:</td>
		    			<td>
		    					<div>
		    						${bean.recordAddress}
		    					</div>
		    			</td>
		    		</tr>
		    		 -->
		    		<tr>
		    			<td width="10%" nowrap="nowrap">驻村(小区)联络员:</td>
		    			<td>
		    					<div>
		    						${bean.recorderName}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">记录时间:</td>
		    			<td>
		    					<div>
		    						${bean.recordTime}
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
