<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汶南好人</title>
<style>
.crm-input { width: 200px; height: 30px; border: 1px solid #d3d3d3; float: left; background-color: #fff; margin-right:15px; }
.a-upload { width:100px; height: 32px; line-height: 32px; position: relative; cursor: pointer; color: #888; background: #fafafa; border: 1px solid #ddd;overflow: hidden; display: inline-block;  *display: inline;*zoom: 1; 
 float:left; font-size:12px; text-align:center;}
.a-upload input { position: absolute; font-size: 20px; right: 0; top: 0; opacity: 0; filter: alpha(opacity=0); cursor: pointer; width:100px; }
</style>
</head>
<script type="text/javascript" src="js/ueditor/ueditor.wx.material.config.fc.js?a=33"></script>
<script type="text/javascript" src="js/ueditor/ueditor.all.js?a=355545564"></script>
<script type="text/javascript" src="js/ueditor/lang/zh-cn/zh-cn.js"></script>
<body>

<div style="padding:10px 0 10px 60px">
    <form id="saveform" method="post" action="fire/save.htm">
    <input type="hidden" name="id" id="id" value="${bean.id}"/>
    <input type="hidden" name="action" value="${action}">
    	<table width="95%">
		    		<tr>
		    			<td width="10%" nowrap="nowrap">地点:</td>
		    			<td>
<%-- 		    					<input class="easyui-validatebox crm-input" required="true" missingMessage="地点不能为空" maxlength="32" value="${bean.postion}" type="text" style="width:200px;" name="postion"  id="postion" validType=""></input> --%>
							<select id="city" name="city" style=" width:20%; height:30px;line-height:30px; ">
								<option value ="南京市">南京市 </option>
								</select>
								<select name="country" id="country" style=" width:20%; height:30px;line-height:30px; ">
									<option value="玄武区">玄武区</option>
									<option value="白下区">白下区</option>
									<option value="秦淮区">秦淮区</option>
									<option value="建邺区">建邺区</option>
									<option value="鼓楼区">鼓楼区</option>
									<option value="下关区">下关区</option>
									<option value="浦口区">浦口区</option>
									<option value="六合区">六合区</option>
									<option value="栖霞区">栖霞区</option>
									<option value="雨花台区">雨花台区</option>
									<option value="江宁区">江宁区</option>
									<option value="溧水县">溧水县</option>
									<option value="永阳镇">永阳镇</option>
									<option value="高淳县">高淳县</option>
									<option value="淳溪镇">淳溪镇</option>
								</select>
								<input class="easyui-validatebox crm-input" required="true" missingMessage="详细地址不能为空" maxlength="32" type="text" style="width:200px;float: right;margin-right: 203px;" name="detailplace"  id="detailplace" validType=""></input>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td width="10%" nowrap="nowrap">时间:</td>
		    			<td>
		    				<input class="easyui-datetimebox" value="${bean.tempTime }" data-options="required:true,showSeconds:false" name="tempTime" style="width: 200px; height: 30px; border: 1px solid #d3d3d3; float: left; background-color: #fff; margin-right:15px;">
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td width="10%" nowrap="nowrap">火情等级:</td>
		    			<td>
		    				<select name = "fire" style="width: 200px;height: 27px;">
		    					<option value="一级">一级</option>
		    					<option value="二级">二级</option>
		    					<option value="三级">三级</option>
		    				</select>
<%-- 		    					<input class="easyui-validatebox crm-input" required="true" missingMessage="火情不能为空" maxlength="32" value="${bean.fire}" type="text" style="width:200px;" name="fire"  id="fire" validType=""></input> --%>
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td width="10%" nowrap="nowrap">描述:</td>
		    			<td>
		    				<textarea name="fireDescribe" style="width: 700px;height: 283px;">${bean.fireDescribe}</textarea>
		    			</td>
		    		</tr>
    	</table>
    </form>
    </div>
</body>
</html>

<script type="text/javascript">
// 	var china=new Object();
// 	china['北京市']=new Array('北京市区','北京市辖区');
// 	china['上海市']=new Array('上海市区','上海市辖区');
// 	china['天津市']=new Array('天津市区','天津市辖区');
// 	china['重庆市']=new Array('重庆市区','重庆市辖区');
// 	china['河北省'] = new Array('石家庄', '唐山市', '邯郸市', '秦皇市岛', '保市定', '张家市口', '承德市', '廊坊市', '沧州市', '衡水市', '邢台市');
// 	china['山西省']=new Array('太原市','大同市','阳泉市','长治市','晋城市','朔州市','晋中市','运城市','忻州市','临汾市','吕梁市');
// 	china['辽宁省']=new Array('沈阳市','大连市','鞍山市','抚顺市','本溪市','丹东市','锦州市','营口市','阜新市','辽阳市','盘锦市','铁岭市','朝阳市','葫芦岛市');
// 	china['吉林省']=new Array('长春市','吉林市','四平市','辽源市','通化市','白山市','松原市','白城市','延边州','长白山管委会');
// 	china['黑龙江省']=new Array('哈尔滨市','齐齐哈尔市','大庆市','佳木斯市','牡丹江市','七台河市','双鸭山市','黑河市','鸡西市','伊春市','绥化市','鹤岗市','加格达奇市');
// 	china['江苏省']=new Array('南京市','苏州市','无锡市','常州市','南通市','扬州市','镇江市','泰州市','盐城市','连云港市','宿迁市','淮安市','徐州市');
// 	china['浙江省']=new Array('杭州市','宁波市','温州市','嘉兴市','湖州市','绍兴市','金华市','衢州市','舟山市','台州市','丽水市');
// 	china['安徽省']=new Array('合肥市','芜湖市','蚌埠市','淮南市','马鞍山市','淮北市','铜陵市','安庆市','黄山市','滁州市','阜阳市','宿州市','巢湖市','六安市','亳州市','池州市','宣城市');
// 	china['福建省']=new Array('福州市','厦门市','莆田市','三明市','泉州市','漳州市','南平市','龙岩市','宁德市');
// 	china['江西省']=new Array('南昌市','景德镇市','萍乡市','九江市','新余市','鹰潭市','赣州市','吉安市','宜春市','抚州市','上饶市');
// 	china['山东省']=new Array('济南市','青岛市','淄博市','枣庄市','东营市','烟台市','潍坊市','济宁市','泰安市','威海市','日照市','莱芜市','临沂市','德州市','聊城市','滨州市','菏泽市');
// 	china['河南省']=new Array('郑州市','开封市','洛阳市','平顶山市','安阳市','鹤壁市','新乡市','焦作市','濮阳市','许昌市','漯河市','三门峡市','南阳市','商丘市','信阳市','周口市','驻马店市');
// 	china['湖北省']=new Array('武汉市','黄石市','十堰市','荆州市','宜昌市','襄樊市','鄂州市','荆门市','孝感市','黄冈市','咸宁市','随州市');
// 	china['湖南省']=new Array('长沙市','株洲市','湘潭市','衡阳市','邵阳市','岳阳市','常德市','张家界市','益阳市','郴州市','永州市','怀化市','娄底市');
// 	china['广东省']=new Array('广州市','深圳市','珠海市','汕头市','韶关市','佛山市','江门市','湛江市','茂名市','肇庆市','惠州市','梅州市','汕尾市','河源市','阳江市','清远市','东莞市','中山市','潮州市','揭阳市','云浮市');
// 	china['海南省']=new Array('文昌市','琼海市','万宁市','五指山市','东方市','儋州市');
// 	china['四川省 ']=new Array('成都市','自贡市','攀枝花市','泸州市','德阳市','绵阳市','广元市','遂宁市','内江市','乐山市','南充市','眉山市','宜宾市','广安市','达州市','雅安市','巴中市','资阳市');
// 	china['贵州省']=new Array('贵阳市','六盘水市','遵义市','安顺市');
// 	china['云南省']=new Array('昆明市','曲靖市','玉溪市','保山市','昭通市','丽江市','普洱市','临沧市');
// 	china['陕西省']=new Array('西安市','铜川市','宝鸡市','咸阳市','渭南市','延安市','汉中市','榆林市','安康市','商洛市');
// 	china['甘肃省']=new Array('兰州市','金昌市','白银市','天水市','嘉峪关市','武威市','张掖市','平凉市','酒泉市','庆阳市','定西市','陇南市');
// 	china['青海省']=new Array('西宁市');
// 	china['台湾省'] = new Array('台北市','高雄市','基隆市','台中市','台南市','新竹市','嘉义市');
// 	china['广西壮族自治区']=new Array('南宁市','柳州市','桂林市','梧州市','北海市','防城港市','钦州市','贵港市','玉林市','百色市','贺州市','河池市','来宾市','崇左市');
// 	china['内蒙古自治区']=new Array('呼和浩特市','包头市','乌海市','赤峰市','通辽市','鄂尔多斯市','呼伦贝尔市','巴彦淖尔市','乌兰察布市'); 
// 	china['西藏自治区']=new Array('拉萨市');
// 	china['宁夏回族自治区']=new Array('银川市','石嘴山市','吴忠市','固原市','中卫市');
// 	china['新疆维吾尔自治区']=new Array('乌鲁木齐市','克拉玛依市');
// 	china['香港特别行政区']=new Array('台北市','高雄市','基隆市','台中市','台南市','新竹市','嘉义市');
// 	function chinaChange(province, city) {
// 		var pv, cv;
// 		var i, ii;
// 		pv = province.value;
// 		cv = city.value;
// 		city.length = 1;
// 		if (pv == '0') return;
// 		if (typeof (china[pv]) == 'undefined') return;
		
		
// 		for (i = 0; i < china[pv].length; i++) { 
// 			ii = i;
// 			city.options[ii] = new Option();
// 			city.options[ii].text = china[pv][i];
// 			city.options[ii].value = china[pv][i];
// 		}
//   };


  $(function(){
	  /*********************************初始化副本本编辑器*******************************/
// 		var ue;	 
// 		$(function(){
// 		    //实例化编辑器
// 		    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
// 		    ue = UE.getEditor('contentId',{
// 		        initialFrameWidth : 700,
// 		        initialFrameHeight: 240
// 		    });
// 		    ue.addListener('afterSelectionChange', function( editor ) {
// 			});
// 		});
		
// 		chinaChange(document.getElementById('provience'),document.getElementById('city'));
		/*************************************end***********************************/
  });
  
  //加入校验功能
  function showProgressDialog(){
	if($("#progressDialog").size() == 0){
		var s = $("<div id=\"progressDialog\" style=\"width:400px;height:300px;z-index:100000;text-align:center; border:0;\">正在处理中。。。</div>");
		$('body').append(s);
	}
	$("#progressDialog").dialog({
		modal: true,
		closeOnEscape: false,
		draggable: false,
		resizable: false,
		hide: 'slide',
		forceClose:true,
		forceOpen:true,
		forceDestroy:true,
		zIndex:100000,
		closable: false,
		width: 135,
		height: 30,
		title: ""
	});
}
/**
 * @author Grahor
 * 操作成功后 销毁模态窗口
 */
top.destroyProgressDialog = function(){
	try{
		$("#progressDialog").dialog("destroy");
	}catch(e){
		
	}
}
top.formValidate = function(){
	var r = $("#saveform").form("validate");
	if (r) {
		showProgressDialog();
	}
	return r;
}
</script>