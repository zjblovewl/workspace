<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统首页</title>
<link rel="stylesheet" type="text/css" href="../js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/portal.css">
<style type="text/css">
	.title{
		font-size:16px;
		font-weight:bold;
		padding:20px 10px;
		background:#eee;
		overflow:hidden;
		border-bottom:1px solid #ccc;
	}
	.t-list{
		padding:5px;
	}
</style>
<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/jquery.portal.js"></script>
<script type="text/javascript" src="../js/window.js"></script>
<script>
	var local = '';
	var portal;
	$(function(){
		portal = $('#pp');
		$('#pp').portal({
			border:false,
			fit:true,
			onStateChange: function () {
				var location = getPanelsLoaction();
				$.ajax({
	    			url:'saveAppLocation.htm',
	    			data:'location='+location,		
	    			cache:false				
	    		});
            }
		});
		
		local = '${result}';
		
	});

	
	//获取当前位置状态
    function getPanelsLoaction() {
        var locals = [];
        var cols = local.split(":").length;
        for (var i = 0; i < cols; i++) {
            var temp = [];
            var pl = portal.portal("getPanels", i);
            for (var j = 0; j < pl.length; j++) {
                temp.push(pl[j].attr("id"));
            }
            locals.push(temp.join(","));
        }
        return locals.join(':');
    }

	
	function remove() {
		$('#pp').portal('remove', $('#pgrid'));
		$('#pp').portal('resize');
	}
	( function($) {
		$( function() {
			$('#icon-reload').click( function() {
				location.reload(function(){alert('reload');});
			});

			$('#icon-setter').click( function() {
				$.window( {
					winId : "user-app-win",
					title : "桌面应用配置",
					url : 'app/toUserApp.htm',
					height : 400,
					width : 500,
					onClose : function() {
						location.reload();
					},
					toolbar : [ {
						text : '关闭',
						id : "icon-cancel",
						iconCls : "icon-cancel",
						handler : closeHandler
					} ]
				});
			});
		});
	})(jQuery);

	function closeHandler() {
		location.reload();
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north" class="title" border="false" style="height:35px;padding-top:5px;padding-bottom:5px;">
		<div style="float: right;">
			<a id="icon-reload" href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-reload">刷新</a>
			<a id="icon-setter" href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-setter">桌面设置</a>
		</div>
	</div>
	<div region="center" border="false" style="overflow-x:hidden">
		<div id="pp" style="position:relative">
			<div style="width:28%;">
			    <!-- 查找第一列的数据 -->
			    <c:forEach items="${list}" var="app">
			    	<c:if test="${app.appColumn == 1}">
			    		<div title="${app.appName}" id="${app.appId}" collapsible="${app.appIsSmall=='0'}" closable="false" style='<c:if test="${app.appHeight!=0}">height:${app.appHeight}px;</c:if><c:if test="${app.appHeight==0}">height:100%;</c:if>padding:0px;position:relative;overflow:hidden'>
			    		  
				    		<iframe src="${app.appLink}" frameborder="0" style="position:absolute;width:100%; height:100%;overflow: hidden"></iframe>
			    		   
				    	</div>
			    	</c:if>
			    </c:forEach>
			</div>
			<div style="width:38%;">
				<!-- 查找第二列的数据 -->
			    <c:forEach items="${list}" var="app">
			    	<c:if test="${app.appColumn == 2}">
			    		<div title="${app.appName}" id="${app.appId}" collapsible="${app.appIsSmall=='0'}" closable="false" style='<c:if test="${app.appHeight!=0}">height:${app.appHeight}px;</c:if>padding:0px;position:relative;overflow:hidden'>
			    		  
				    		<iframe src="${app.appLink}" frameborder="0" style="position:absolute;width:100%; height:100%;overflow: hidden"></iframe>
			    		   
				    	</div>
			    	</c:if>
			    </c:forEach>
			</div>
			<div style="width:28%;">
				<!-- 查找第三列的数据 -->
			    <c:forEach items="${list}" var="app">
			    	<c:if test="${app.appColumn == 3}">
			    		<div title="${app.appName}" id="${app.appId}" collapsible="${app.appIsSmall=='0'}" closable="false" style='<c:if test="${app.appHeight!=0}">height:${app.appHeight}px;</c:if>padding:0px;position:relative;overflow:hidden'>
			    		  
				    		<iframe src="${app.appLink}" frameborder="0" style="position:absolute;width:100%; height:100%;overflow: hidden"></iframe>
			    		   
				    	</div>
			    	</c:if>
			    </c:forEach>
			</div>
		</div>
	</div>
</body>
</html>