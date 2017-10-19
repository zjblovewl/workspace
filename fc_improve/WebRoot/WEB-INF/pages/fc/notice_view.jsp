<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通知公告-详情查看</title>
</head>
<body>
<div style="padding:10px 0 10px 60px">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">标题:</td>
		    			<td>
		    					<div>
		    						${bean.title}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">发布时间:</td>
		    			<td>
		    					<div>
		    						${bean.pubTime}
		    					</div>
		    			</td>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">关联人员:</td>
		    			<td>
		    					<div>
		    						${bean.relUserName}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">公告图片:</td>
		    			<td>
		    					<div>
		    						<img src="${bean.pubImage}" height="150"/>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">公告附件:</td>
		    			<td>
		    					<div>
		    						<a target="_blank" href="${bean.noticeFile}"  style="color:blue">附件下载</a>
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">短信提醒:</td>
		    			<td>
		    					<div>
		    						${bean.sendMsgStr}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">作者:</td>
		    			<td>
		    					<div>
		    						${bean.author}
		    					</div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">内容:</td>
		    			<td>
		    					<div>
		    						${bean.content}
		    					</div>
		    			</td>
		    		</tr>
    	</table>
    </div>
</body>
</html>
