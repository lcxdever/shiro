<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title>授权</title>
<style>
.error {
	color: red;
}
</style>
</head>
<body>

	<div>shouquan</div>
	<div class="error">${error}</div>

	<form action="" method="post" name="loginform">
		<input type="hidden" name="userauthorize" value="true"> <input
			type="hidden" name="agreement" id="agreement" value="true">
		<button onclick="auther('true');">授权</button>
		<button onclick="auther('false');">取消</button>
	</form>
	<script type="text/javascript">
		function auther(select) {
			document.getElementById('agreement').value = select;
			document.loginform.submit();
		}
	</script>
</body>
</html>