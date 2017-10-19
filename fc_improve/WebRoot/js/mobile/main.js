(function($) {
	var login = function(callback) {
		var loginForm = $("#loginForm");
		$.ajax({
			url:"wx/user/free/doLogin.htm",
			type:"post",
			data:loginForm.serialize(),
			dataType:"json",
			success:function(data) {
				if (data && data.success) {
					callback && callback(true);
				} else {
					callback && callback(false);
				}
			},
			error:function() {
				callback && callback(false);
			}
		});
	};

	var logout = function(callback) {
		$.ajax({
			url:"wx/user/free/doLogout.htm",
			type:"post",
			dataType:"json",
			success:function(data) {
				if (data && data.success) {
					callback && callback(true);
				} else {
					callback && callback(false);
				}
			},
			error:function() {
				callback && callback(false);
			}
		});
	};

	var register = function(callback) {
		var registerForm = $("#registerForm");
		console.log("form:" + registerForm.serialize());
		$.ajax({
			url:"wx/user/free/doRegister.htm",
			type:"post",
			data:registerForm.serialize(),
			dataType:"json",
			success:function(data) {
				if (data && data.success) {
					callback && callback(true);
				} else {
					callback && callback(false);
				}
			},
			error:function() {
				callback && callback(false);
			}
		});
	};

	$(document).on("pageinit","#loginpage",function() {
		$("#btnLogin").click(function() {
			login(function(flag) {
				if (flag) {
					$("#loginPopup").popup("close");
					alert("您已成功登录");
					window.location.href = window.location.href.split("#")[0];
				} else {
					alert("登录失败，请重试！");
				}
			});
		});

		$("#btnLogout").click(function() {
			logout(function(flag) {
				if (flag) {
					alert("注销成功！");
					window.location.href = window.location.href.split("#")[0];
				} else {
					alert("注销失败！");
				}
			});
		});

		$("#btnRegister").click(function() {
			register(function(flag) {
				if (flag) {
					$("#registerPopup").popup("close");
					alert("您已成功注册");
					window.location.href = window.location.href;
				} else {
					alert("注册失败！");
				}
			});
		});
	});

})(jQuery);