<%@page import="com.tyunsoft.base.common.CacheFactory"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String city = CacheFactory.getInstance( ).getSetter( "map.city" );
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>百度地图</title>

<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>

</head>
<body>
<!-- 	<div style="display:none"><span style="font-weight:bold">当前在线人员：</span><span id="nowuser"></span></div> -->
<!-- 	<div -->
<!-- 		style="width: 100%; height: 99%; border: 1px solid gray; float: left;" -->
<!-- 		id="container"></div> -->
</body>
</html>

<script type="text/javascript">
	var map = new BMap.Map("container");
	map.centerAndZoom('<%=city%>', 11);
	map.addControl(new BMap.NavigationControl());
	map.addControl(new BMap.ScaleControl());
	map.addControl(new BMap.OverviewMapControl());
	//map.addControl(new BMap.MapTypeControl());
	
	window.onload = function(){
		loadTaskUser();
		setInterval(function(){
			loadTaskUser();
		},1000*60);
	};
	
	//進行人員定位
	//map.addOverlay(new BMap.Marker(point));
	
	
	
	
	map.enableScrollWheelZoom();

	function loadTaskUser(){
		map.clearOverlays();
		$.ajax({
    		url:'<%=basePath%>index/online.htm',
    		dataType:'json',
    		success:function(data){
    			
    			for(var i = 0;i<data.length;i++)
    			{
    				
    				    var userName = data[i].userName;
    				    var userPhone = data[i].userPhone;
    				    var position = data[i].position;
    				    var  point = position.split(',');
    				    var p = new BMap.Point(point[0], point[1]);
	    				var marker = new BMap.Marker(p);
	    				map.addOverlay(marker);
	    				var label = new BMap.Label(userName);  
	    	            marker.setLabel(label);
	    	            
	    	           	var html = '<div style="margin:0;line-height:20px;padding:2px;">' +
	    	            '<span>姓名：'+userName+'</span><br/><span>手机：'+userPhone+'</span><br/>';
	    	           	var opts = { 
	    	           				width:120,
	    	                    	height:60
	    	                       };
	    	            marker.addEventListener("click", function(e){
	                        this.openInfoWindow(new BMap.InfoWindow(html,opts));
	               		 });
    			}
    			
    			
    			
    			//$('#nowuser').html('在线人数:' +data.length+"人|总人数:"+obj.countVolunteer+"人|离线人员："+obj.offLineUsers);
    		},
    		error:function(data){
    			//alert('服务器异常，请刷新后再试');
    		},
    		cache:false				
    	});
	}
</script>
