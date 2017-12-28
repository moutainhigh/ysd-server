<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>热门活动</title>
	<#include "/content/common/meta.ftl">
  <style>
    .list{margin:1em 0.5em;padding-bottom:0.5em;box-shadow: 1px 1px 1px #ccc;}
    .list img{width:100%;height:6em;}
    .list p{line-height: 2em;padding-left:1em;}
  </style>
</head>
  <body class="bgc5">
    <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	    <div class="am-header-left am-header-nav">
	      	<a href="#left-link">
	          	  <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
	       </a>
	      </div>
	      <h1 class="am-header-title">
	          	热门活动
	      </h1>
	    <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->
	  <ul>
	  <#list activityList.activityList as value>
	      <li class="list bgc2" onclick="window.location.href='${base}/activity/detail.do?id=${value.id}'">
	         <img src="<@imageUrlDecode imgurl="${value.imgApp}"; imgurl>${imgurl}</@imageUrlDecode>" alt="">
	         <p>${value.createDate?string("yyyy-MM-dd")}</p>
	         <p>${value.title}</p>
	      </li>
      </#list>
    </ul>
  </body>
</html>
