<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<script type="text/javascript">
(function($){
	$(function(){
		
		$('#btnEp').click(function(){
			
			var $newpass = $('#txtNewPass');
	        var $rePass = $('#txtRePass');

	        if ($newpass.val() == '') {
	            msgShow('系统提示', '请输入密码！', 'warning');
	            return false;
	        }
	        if ($rePass.val() == '') {
	            msgShow('系统提示', '请在一次输入密码！', 'warning');
	            return false;
	        }

	        if ($newpass.val() != $rePass.val()) {
	            msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
	            return false;
	        }
			
			$.ajax({
				url:'user/changePassword.htm',
				data:'password='+$('#txtRePass').val(),	
				dataType:'json',
				success:function(data){
					if(data)
						{
							top.myself.msgShow("提示","修改密码成功!","info");
							setTimeout(function(){closeWindow();},1000);
							
						}
						
				},
				cache:false				
			});
			
		});
		
		$('#btnCancel').click(function(){
			closeWindow();
		});
	});
})(jQuery);

</script>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
        <table cellpadding="3">
            <tr>
                <td>&nbsp;新&nbsp;密&nbsp;码：</td>
                <td><input id="txtNewPass" type="password" class="txt01" /></td>
            </tr>
            <tr>
                <td>确认密码：</td>
                <td><input id="txtRePass" type="password" class="txt01" /></td>
            </tr>
        </table>
    </div>
    <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
        <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
            确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
    </div>
</div>
</body>
</html>