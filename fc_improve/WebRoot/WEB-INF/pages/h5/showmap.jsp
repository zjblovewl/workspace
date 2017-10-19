<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="${ctx}/css/basexin.css?v=122dddd2" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=T41dxZzi7EsKijB13Xq9QcZYxHNw1Qbj"></script>
<script type="text/javascript" src="${ctx}/js/wx/adaptive.js"></script>
<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
</head>

<body>
	<article class="affairs page-on-center">
		<div class="scroller">
			<section class="affairs-tongzhi">
				<div id="container" 
		            style="position: absolute;
		                margin-top:30px; 
		                width: 100%; 
		                height:500px;
		                top: 50; 
		                border: 1px solid gray;
		                overflow:hidden;">
		        </div>
		        
		        <input id="text_" type="hidden" value="${ postion}"/>
		        <input id="result_" type="hidden" value="${lng },${lat}" />
			</section>
		</div>
	</article>
	<script type="text/javascript" src="${ctx}/js/wx/jquery.min-2.1.4.js"></script>
	<script type="text/javascript" src="${ctx}/js/wx/iscroll5.js"></script>
	<script>
	var page = 1;
	var myScroll,
    loadajax = false;
	
    function loaded () {
    	myScroll = new IScroll('.page-on-center', { mouseWheel: true, click: true, probeType: 3 });
	     
    	myScroll.on('scrollEnd', function(){
	        if(this.maxScrollY == this.y){
	            if(loadajax){return false;}
	        }
	    });
    }
	$(function() {
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
           $(window).on("load", function(event){
               loaded();
           });
	});
	
	var postion = "${postion}";
	var centerPlace = "${centerPlace}";
	var lng = "${lng}";
	var lat = "${lat}";
	
	var map = new BMap.Map("container");
    map.centerAndZoom(centerPlace, 12);
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
   // map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
   // map.addControl(new BMap.OverviewMapControl({ isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT }));   //右下角，打开

    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
    
    searchByStationName();
    
	function searchByStationName() {
	    map.clearOverlays();//清空原来的标注
	    var keyword = document.getElementById("text_").value;
	    localSearch.setSearchCompleteCallback(function (searchResult) {
	        document.getElementById("result_").value = lng + "," + lat;
	        var point = new BMap.Point(lng,lat)
	        map.centerAndZoom(point, 16);
	        var marker = new BMap.Marker(point);  // 创建标注，为要查询的地方对应的经纬度
	        map.addOverlay(marker);
	        var content = document.getElementById("text_").value + "<br/><br/>经度：" + lng + "<br/>纬度：" + lat;
	        var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
	        marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
	        // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	      
	    	var label = new BMap.Label($('#text_').val(),{offset:new BMap.Size(-40,-20)});
	    	marker.setLabel(label);
	    });
	    
	    localSearch.search(keyword);
	} 
	
	</script>
</body>
</html>