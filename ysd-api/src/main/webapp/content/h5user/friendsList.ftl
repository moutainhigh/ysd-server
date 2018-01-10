<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>邀请好友</title>
	<#include "/content/common/meta.ftl">
  <style>
  	 .am-form-group {margin-bottom: 1rem;}
  	.am-form .am-form-group input{border: 1px solid #e3e3e3;}
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
	          邀请好友
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->
    <div class="bgc2" style="padding:0 0.5em 2em;margin-bottom:1em;">
      <h3 class="fwn" style="padding:0.5em 0 0.2em;">邀请好友奖励细则：</h3>
     	${friendbean.spreadTextarea}
    </div>
   	<div data-am-widget="list_news" class="am-list-news am-list-news-default bgc2" style="margin:0;">
        <div class="color2" style="padding:0.5em;">我的好友</div>
        <div class="am-list-news-bd" >
            <ul class="am-list">
             <#list friendbean.friendList as friend>
                <li class="am-g am-list-item-dated">
                    <div class="am-list-item-hd ">
                        <p>${friend.username}</p>
                        <p class="color9">${friend.createDate?string("yyyy-MM-dd HH:mm:ss")}</p>
                    </div>
                    <div class="am-list-date" style="top:0px;padding:0.2em;">
                      <p>投资奖励</p>
                      <p class="tar color">￥10</p>
                    </div>
                </li>
             </#list>   
            </ul>
        </div>
    </div>
    <div style="position: fixed;left: 0;  bottom: 0;  width: 100%;  z-index: 1010;">
      <a class="am-btn am-btn-danger color1" href="#" target="_blank" style="display:block;">邀请好友一起理财</a>
    </div>
</body>
	

</html>

