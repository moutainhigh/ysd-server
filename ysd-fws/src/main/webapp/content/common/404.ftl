<!DOCTYPE html>
<html>
  <head>
<title>${name} 系统信息</title>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="application-name" content="${name}" />
    <meta name="msapplication-tooltip" content="${name}" />
	<meta name="keywords" content="${metaKeywords}" />
	<meta name="description" content="${metaDescription}" />
	<meta name="robots" content="all" />

	<#--<link href="${base}/static/css/v2.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/css/jquery.select.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/css/default.css" rel="stylesheet" type="text/css" />-->
	<#--><link href="${base}/static/css/base.css" rel="stylesheet" type="text/css" />-->
	
	<link href="${base}/static/css/style.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
 <!-- header -->

<#include "/content/common/header.ftl">
<div class="main"><div style=" margin:0 auto; height:160px; width:1000px; background:url(${base}/static/img/kaqu_v.png) no-repeat;"></div></div><!--main end -->

<div class="wrap">
	<div class="content">
		<div style="background:url(${base}/static/img/yjiao.png) no-repeat 0 top; width:1000px; height:20px;"></div>
		<div class="kaqu_name">
			<div class="kaqu_name0">
				<h2 class="kaqu_youxi">系统消息</h2>
				<div class="kaqu_name1">
					<div class="kaqu_name2_2">
						<style>
						</style>

						<div class="kaqu_gongxinin">
							<div class="kaqu_cuowu">页面不存在或者出现系统错误！
							</div>
						</div>
 						<div class="kaqu_chakanjilu">
							<a href="${base}" class="kaqu_jiluxinxi">进入首页</a>
						</div>
					</div>
					<div style=" float:left; width:360px; height:420px; background:url(${base}/static/img/suanpan.png) no-repeat 0 0;"></div>
				</div>
			</div>
			<div style=" clear:both"></div>
		</div>
		<div style=" clear:both"></div>
		<div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
	</div>
	<!-- middle end -->
</div>
<!-- content end -->
<div style="clear:both"></div>
</div><!--wrap end-->

<#include "/content/common/footer.ftl">

</body>
</html>