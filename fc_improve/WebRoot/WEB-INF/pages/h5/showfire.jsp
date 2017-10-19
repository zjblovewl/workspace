<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="${ctx}/css/basexin.css?v=122ddddddddddd2" />
<script type="text/javascript" src="${ctx}/js/wx/adaptive.js"></script>
</head>

<body>
	<article class="affairs page-on-center">
		<div class="scroller">
			<section class="affairs-tongzhi">
				<div class="tongzhi-first float" style="margin:0">
					<div class="first-left"></div>
					<div class="flex tongzhi-title">火情通知</div>
					<div class="first-right"></div>
				</div>
				
				<div class="tongzhi-shenhe" style="background-color: #fff;">
					<div class="show-fire-descirbe">
						${fire.fireDescribe }
					</div>
					
					<div class="show-fire">
						${fire.fire }
					</div>
					
					<div class="show-fire-descirbe" style="margin-top: .2rem;">
						地址：${fire.postion }
					</div>
					
					<div class="show-btn">
						<a href="${ctx }/fire/showmap.htm?postion=${fire.postion}&lng=${fire.longitude}&lat=${fire.latitude}">地图展示</a>
					</div>
				</div>
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
	</script>
</body>
</html>