<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
body{font-size: 12px}
.error_t{ border:1px #F96 solid; background:#FFEED0 ; padding:15px 30px 10px 65px; margin:30px auto; width:700px;}
.error_t_blod{font-size:14px; font-weight:bold;}
.error_t2{color:#fff; border:1px #fff solid; background:#fff;padding:15px 30px 10px 65px; margin:30px auto; width:700px;}
</style>
</head>
<body  >
	<div id="wrap">
		<div class="header">
			<h1></h1>
		</div>
		<div class="error_t" align=left>
			<div class="error_t_blod">
				<h2>很抱歉，出错了！</h2>
				<br />
				<h4>您可以根据错误码文档来查询以下出错信息：</h4>
			</div>
			<p>错误信息: ${error_description}</p>
			<p>错误码： ${error}</p>
		</div>
	</div>
</body>
</html>