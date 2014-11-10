<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>登录并授权</title>
    <style>.error{color:red;}</style>
</head>
<body>

<div>使用你的Shiro示例Server帐号访问 [${client.clientName}] ，并同时登录Shiro示例Server</div>
<div class="error">${error}</div>

<form action="" method="post" name="loginform" onsubmit="return check();">
    用户名：<input type="text" name="username"   value="<shiro:principal/>"><br/>
    密码：<input type="password" name="password"  ><br/>
     下次自动登录：<input type="checkbox" name="autologin"   checked="checked"><br/>
     <input type="hidden" name="rememberMe">
    <input type="submit" value="登录并授权">
</form>
<script type="text/javascript">
	function check(){
		var username=document.forms.loginform.username.value;
		if(username==null||username==''){
			alert("请输入用户名");
			return false;
		}
		var password=document.forms.loginform.password.value;
		if(password==null||password==''){
			alert("请输入密码");
			return false;
		}
		var autologin=document.forms.loginform.autologin.checked;
		document.forms.loginform.rememberMe.value=autologin;
		return true;
	}
</script>
</body>
</html>