function initTree(winId,title,width,height,url){
	$.window({
			winId:winId,  
	        title:title,
	        url:url,
	        width:width,
	        height:height,  
	        onClose:function(){
	        	closeHandler();
		    },     
	        toolbar:[{text:'保存',id:"icon-save",iconCls:"icon-save"},
	              {text:'关闭',id:"icon-cancel",iconCls:"icon-cancel",handler:closeHandler}]  
	 	}); 		
}

function closeHandler(winId){
	$('#notice-selectUsers').window('close');
}

function commonAdd(domId,pageUrl,jsonUrl,width,height,title,loadingText,callback){
	var dlg = document.openDialog(domId,{ 
			title: title,
			width: width,  
			height: height,  
			loadingText: loadingText,
			top:($(window).height() - height) * 0.5,   
			left:($(window).width() - width) * 0.5,   
			onClose:function(){
			   destroy(domId);
			}
	});
	dlg.load(pageUrl,function(){
		if($.isFunction(callback)){
			callback();
		}
	});
}

document.openDialog = function(domId, params) {
	if(!domId){
		domId = "_noid_dialog";
	};
	var param = params;
	if("undefined" == params.loadingText || "undefined" == typeof(params.loadingText) || params.loadingText.length < 1){
		param = $.extend({
			loadingText : "正在加载页面..."
		},params||{});
	}
	if(($("#" + domId).window('open').html(param.loadingText)).size() === 0){
		initDialog(domId, param);
		$("#" + domId).window('open').html(param.loadingText);
	}
	return $("#" + domId);
};

function initDialog(domId, params) {
	param = jQuery.extend({  
			title: "标题",
  			width: 200,  
   			height: 150,  
   			top:($(window).height() - 150) * 0.5,   
           	left:($(window).width() - 200) * 0.5,
           	closable:true,
   			collapsible:false,
      		minimizable:false,
       		maximizable:true,	
       		resizable:true,
       		modal:true,
   			draggable:true
	},params||{});
	if($("#"+domId).size() === 0){
		$('body').append('<div id="'+ domId +'" style="overflow-y:visible;"></div>');
	}
	$("#"+domId).window(param);
}