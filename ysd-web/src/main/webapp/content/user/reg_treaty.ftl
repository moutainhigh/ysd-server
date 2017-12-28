<!DOCTYPE html>
<html>
<head>
	<#include "/content/common/meta.ftl">
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-注册协议书</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
	
</head>
<body>
 <!-- header -->
<div id="header">
    <div class="headArea">
    	<div class="logo" style='top:20px;'>
    		<img src="/img/logo.png" alt="logo">
    	</div>
    </div>
</div>
<div style="width:100%; background:url(${base}/static/img/d6.png) 0 0 repeat-x; min-width:1000px; padding-bottom:30px;margin-top:10px;" class="biaoti_bg">
<div class="content">
 <div class="biaoti">
  
 </div><!--biaoti end-->
<div style="width:1000px;background-color:#fff;margin:0 auto;" class="big_content">
<div style="padding:30px;">
 <#--<h3 style="color:#363636; font-size:24px; border-bottom:1px solid #e0e0e0; font-weight:normal; padding-bottom:5px; margin-bottom:20px;text-align:center;"></h3>
	--><div style="color:#363636; font-size:14px; line-height:25px;">
		<@article_list sign='agreement' count=1; articleList>
		  	<#list articleList as article>
				${article.content}
			</#list>
		</@article_list>
	</div>
</div>



 <div style="clear:both;"></div>
</div><!--big_content end-->
</div><!--content end-->
</div><!--biaoti_bg end-->





<!-- content -->
<#--
<div class="member clearfix">

	<ul class="hd clearfix">
		<li class="fl"><h2>会员协议</h2></li>
	</ul>
	<div class="bd">
	<@article_list sign='agreement' count=1; articleList>
	  	<#list articleList as article>
			${article.content}
		</#list>
	</@article_list>
	</div>
</div>
-->
</body>
</html>
