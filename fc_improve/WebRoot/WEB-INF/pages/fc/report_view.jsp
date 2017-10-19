<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯录-详情查看</title>
</head>
<body>
<div style="padding:0">
		<div style="background:#275C8E;height:30px;line-height:30px;padding-left:10px;font-weight: bold;color:#fff">签到信息</div>
    	<table width="100%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">姓名:</td>
		    			<td>
		    					<div>
		    						${map.user_name}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">签到地址:</td>
		    			<td>
		    					<div>
		    						${map.in_hand_address}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">签到时间:</td>
		    			<td>
		    					<div>
		    						<fmt:formatDate value="${map.in_sign_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">签到图片:</td>
		    			<td>
		    					<div>
		    						<img alt="" src="data:image/png;base64,${map.in_image_path}" style="width:200px">
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    	
    	<div style="background:#275C8E;height:30px;line-height:30px;padding-left:10px;font-weight: bold;color:#fff">工作记录信息</div>
    	<table width="100%">
    				<tr>
		    			<td width="10%" nowrap="nowrap">工作名称:</td>
		    			<td>
		    					<div>
		    						${map.performance}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">会议纪要:</td>
		    			<td>
		    					<div>
		    						${map.meeting}
		    					</div>
		    			</td>
		    		</tr>
		    		<!-- 
		    		<tr>
		    			<td width="10%" nowrap="nowrap">会议地点:</td>
		    			<td>
		    					<div>
		    						${map.record_address}
		    					</div>
		    			</td>
		    		</tr>
		    		 -->
		    		<tr>
		    			<td width="10%" nowrap="nowrap">会议图片:</td>
		    			<td>
		    					<div>
		    						<c:forEach items="${recordImages}" var="image">
		    							<img src="${image}"  style="width:200px">
		    						</c:forEach>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">记录时间:</td>
		    			<td>
		    					<div>
		    						<fmt:formatDate value="${map.record_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    	
    	<div style="background:#275C8E;height:30px;line-height:30px;padding-left:10px;font-weight: bold;color:#fff">问题办理信息</div>
    	<table width="100%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">问题描述:</td>
		    			<td>
		    					<div>
		    						${map.description}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">群众姓名:</td>
		    			<td>
		    					<div>
		    						${map.appealer_name}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">群众地址:</td>
		    			<td>
		    					<div>
		    						${map.appealer_address}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">群众电话:</td>
		    			<td>
		    					<div>
		    						${map.appealer_phone}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">问题取照:</td>
		    			<td>
		    					<div>
		    						<c:forEach items="${appealImages}" var="image">
		    							<img src="${image}"  style="width:200px">
		    						</c:forEach>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">记录时间:</td>
		    			<td>
		    					<div>
		    						<fmt:formatDate value="${map.report_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
		    					</div>
		    			</td>
		    		</tr>
    	</table>    	    
    	
    	<div style="background:#275C8E;height:30px;line-height:30px;padding-left:10px;font-weight: bold;color:#fff">签退信息</div>
    	<table width="100%">
    				<!-- 
		    		<tr>
		    			<td width="10%" nowrap="nowrap">签退地址:</td>
		    			<td>
		    					<div>
		    						${map.out_address}
		    					</div>
		    			</td>
		    		</tr>
		    		 -->
		    		<tr>
		    			<td width="10%" nowrap="nowrap">签退时间:</td>
		    			<td>
		    					<div>
		    						<fmt:formatDate value="${map.out_sign_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">签退图片:</td>
		    			<td>
		    					<div>
		    						<img alt="" src="data:image/png;base64,${map.out_img_path}" style="width:200px">
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
