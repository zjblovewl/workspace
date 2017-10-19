<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>江苏省村镇信息化服务平台-微信菜单</title>
		<script type="text/javascript">
			var ctx= "${ctx}";
		</script>
	</head>
	<body>
		<div>
			<form action="${ctx }/core/menuSubmit.htm" method="post">
				<table style="font-size: 15px;">
					<tr>
						<td>
							<c:forEach items="${partys}" var="party" varStatus="s">
							<input type="radio" name="partyId" value="${party.partyId}" <c:if test="${s.index == 0 }">checked</c:if> />&nbsp;${party.name} &nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td>
							<textarea rows="20" cols="100" name="menu" ></textarea>
						</td>
					</tr>
					<tr>
						<td><input type="submit" name="提交"/> </td>
					</tr>
				</table>
			</form>
		</div>
	</body>	
</html>
