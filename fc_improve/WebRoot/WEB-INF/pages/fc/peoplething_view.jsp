<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>民生实事-详情查看</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    	<table width="95%">
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
		    		<tr>
		    			<td width="10%" nowrap="nowrap">名称:</td>
		    			<td>
		    					<div>
		    						${bean.content}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">进展情况:</td>
		    			<td>
		    					<div>
		    						${bean.progress}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">取照图片:</td>
		    			<td>
		    					<div>
		    						<c:forEach items="${images}" var="image">
		    							<img src="${image}" height="140px">
		    						</c:forEach>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">存在困难及问题:</td>
		    			<td>
		    					<div>
		    						${bean.problem}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">驻村(小区)联络员:</td>
		    			<td>
		    					<div>
		    						${bean.createUserName}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">创建日期:</td>
		    			<td>
		    					<div>
		    						${bean.createDate}
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
