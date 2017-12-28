<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>活动详情</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
 <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
    <div class="am-header-left am-header-nav">
    	<a href="#left-link">
        <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
     </a>
    </div>
    <h1 class="am-header-title">
      	 ${activity.title}
    </h1>
    <div class="am-header-right am-header-nav"></div>
 </header><!-- header end-->
 <div class="infoDetail">
   <div class="cont">
   	<img src="${activity.content}"/>
   </div>
 </div>
</body>
</html>
