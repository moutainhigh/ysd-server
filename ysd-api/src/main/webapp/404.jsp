<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>错误页面提示</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="robots" content="all" />
	<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="<%=basePath%>/static/base/css/base.css" type="text/css" />
	<link rel="stylesheet" href="<%=basePath%>/static/css/style.css" />
	<link rel="stylesheet" href="<%=basePath%>/static/css/kefu.css" />
	
	<script src="<%=basePath%>/static/js/jquery.min.js"></script>
	<script src="<%=basePath%>/static/js/base.js"></script>
	<script src="<%=basePath%>/static/base/js/base.js"></script>
	<script src="<%=basePath%>/static/js/common/qmd.js"></script>	
	<script src="<%=basePath%>/static/js/kefu.js"></script>
	
</head>
<body>
 <!-- header -->

<!-- content -->
<div class="content clearfix">
	
	<div class="tab-con">
		
		<!-- frm -->
		<div class="errorpage frm">
          <img src="<%=basePath%>/static/img/error_icon.jpg" class="fl">
          <ul class="errorc">
          <li class="sorry">抱歉，您请求的页面现在无法打开</li>
          <li class="s-ser">你可以：稍后再试 或 <a href="#">联系客服</a></li>
          </ul>
								
		</div>
	</div>
	<div class="c_bottom"></div>
</div>

</body>
</html>
