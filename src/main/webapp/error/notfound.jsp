<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<title>页面未找到</title>
		<link rel="stylesheet" type="text/css" href="plugins/bootstrap-3.3.2/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="css/common/custom.css" />
		<style type="text/css">
body {
	padding-top: 30px;
}
		</style>
	</head>

	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="alert alert-danger">
						<p><strong>错误：</strong>页面未找到！</p>
						<p><a href="index.html">返回首页</a></p>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<p class="text-center text-muted">财务管理系统&nbsp;&copy;&nbsp;2015</p>
				</div>
			</div>
		</div>
	</body>
</html>
<!-- By Ming -->
