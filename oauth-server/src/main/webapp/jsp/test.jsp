<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="http://localhost:9080/oauth-server/accessToken" method="post">
<input type="text" name="client_id" value="c1ebe466-1cdc-4bd3-ab69-77c3561b9dee">
<input type="text" name="client_secret" value="d8346ea2-6017-43ed-ad68-19c0f971738b">
<input type="text" name="redirect_uri" value="http://localhost:9080/oauth-client/oauth2-login">
<input type="text" name="grant_type" value="authorization_code">
<input type="text" name="code" value="ba0248dd96aa907b541a9d4c7de159f1">
<button type="submit">提交</button>
</form>
</body>
</html>