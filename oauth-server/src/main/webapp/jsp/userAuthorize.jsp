<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title>授权</title>
<link rel="stylesheet" href="static/css/css.css" />
<style type="text/css">
</style>
</head>
<body>
	<div id="wrap">
		<div class="header">
			<h1>OAuth2.0认证</h1>
		</div>
		<div class="content">
			<div class="piao">
				<p>
					为了能够给您提供更加优质的服务，<strong>${client.clientName}</strong> <font
						color="red"></font> 希望能得到您的授权
				</p>
				<div class="img_text">
					<div class="left_ad"><img src="${client.clientImage}"></div>
					<div class="middle_text">
						<div class="m_top">提供授权的用户信息</div>
						<div class="m_bottom">提供应用增值服务</div>
					</div>
					<div class="right_ad">
						<img src="static/images/example2.png" />
					</div>
				</div>
				<div class="cont">您授权后，此应用可以：</div>
				<div class="auth_option">
					<p class="normal">读取您的用户名等基本信息</p>
					<p class="normal">读取您的模块信息</p>
						<p class="detail">包括所有相关应用</p>
					<p class="normal">其他相关信息等</p>
				</div>
			</div>
			<div class="cont">
				授权后表明您已经同意<a target="_blank" href="javascript:void(0)">授权须知</a><br /> 您也可以随时在<a
					target="_blank" href="javascript:void(0)">授权管理</a>里查看和取消授权
			</div>
			<p class="change_user">
				账户名：${username}<a href="">换个账户名？</a>
			</p>
		</div>
	</div>
	<div class="footer">
		<div class="content">

			<form action="" method="post" name="loginform">
				<input type="hidden" name="userauthorize" value="true"> <input
					type="hidden" name="agreement" id="agreement" value="true">
				<button id="sub" class="sh" onclick="auther('true');">授权</button>
				<button class="" onclick="auther('false');">取消</button>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		function auther(select) {
			document.getElementById('agreement').value = select;
			document.loginform.submit();
		}
	</script>
</body>
</html>