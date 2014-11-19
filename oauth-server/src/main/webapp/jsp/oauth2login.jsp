<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title>登录</title>
<link rel="stylesheet" href="static/css/css.css" />
<style type="text/css">
.input_txt {
	width: 242px;
	height: 28px;
	line-height: 18px;
	margin-right: 3px;
	padding: 3px;
	border: solid 1px #c8c8c8;
	vertical-align: middle;
}

.submit {
	padding: 0px;
	background-color: #fafafa;
	height: 300px;
}

.label {
	display: block;
	font-weight: 700;
	padding-bottom: 3px;
	color: #666;
}

.forget-pw {
	position: absolute;
	right: 0;
	top: 0;
}

a {
	color: #6d6d6d;
	text-decoration: none;
}

.field {
	padding-bottom: 5px;
	vertical-align: middle;
}

button {
	width: 250px;
	height: 33px;
	border: 0;
	display: inline-block;
	overflow: hidden;
	vertical-align: middle;
	line-height: 31px;
	font-size: 14px;
	font-weight: 700;
	color: #FFF;
	background: url(static/images/c.png) no-repeat 0 0;
	cursor: pointer;
	zoom: 1;
}

button:hover {
	background-position: 0 -33px;
}

.entries {
	float: right;
}
</style>
</head>
<body>
	<div id="wrap">
		<div class="header">
			<h1>OAuth2.0登录</h1>
		</div>
		<div class="content">
			<div class="piao">
				<p>
					您正在使用用户帐号访问<strong>${client.clientName}</strong> <font color="red"></font>
				</p>
				<p>
					登录后<strong>${client.clientName}</strong> 可获得您的个人信息，为您提供更加优质的服务！
				</p>
				<div class="img_text">
					<div class="left_ad">
						<img src="${client.clientImage}">
					</div>
					<div class="middle_text">
						<div class="m_top">提供授权的用户信息</div>
						<div class="m_bottom">提供应用增值服务</div>
					</div>
					<div class="right_ad">
						<img src="static/images/example2.png" />
					</div>
				</div>
			</div>
			<div class="submit">
				<div style="width: 310px; margin: 0 auto;">
					<div style="padding: 10px 30px 0;">
						<form action="" method="post" name="loginform"
							onsubmit="return check();">
							<div class="field">
								<label for="username" class="label">登录名：</label> <input
									type="text" class="input_txt" name="username"
									value="<shiro:principal/>">
							</div>
							<div style="position: relative" class="field">
								<label id="password-label" class="label" for="password">登录密码：</label>
								<a href="javascript:void(0)" target="_blank" id="forget-pw-safe" class="forget-pw">忘记登录密码?</a>
								<input type="password" class="input_txt" name="password">
							</div>
							<div class="field">
								<label for="autologin" class="label" style="float: left">下次自动登录：</label>
								<input type="checkbox" name="autologin"
									style="float: left; margin-top: 3px" checked="checked"><br />
							</div>
							<div class="field">
								<input type="hidden" name="rememberMe">
								<button type="submit">登 录</button>
							</div>
						</form>
						<ul class="entries">
							<li id="registerUrl_1" class="register"><a
								href="javascript:void(0)"
								target="_blank" tabindex="8">免费注册</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function check() {
			var username = document.forms.loginform.username.value;
			if (username == null || username == '') {
				alert("请输入用户名");
				return false;
			}
			var password = document.forms.loginform.password.value;
			if (password == null || password == '') {
				alert("请输入密码");
				return false;
			}
			var autologin = document.forms.loginform.autologin.checked;
			document.forms.loginform.rememberMe.value = autologin;
			return true;
		}
	</script>
</body>
</html>