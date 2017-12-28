<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>账户设置</title>
	<#include "/content/common/meta.ftl">
</head>
    <body class="bgc5">
       <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	      <div class="am-header-left am-header-nav">
	      	<a href="#left-link" class="">
	           <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
	        </a>
	      </div>
	      <h1 class="am-header-title">
	        		  账户设置
	      </h1>
	  </header><!-- header end-->
   		<ul class="am-list am-list-static am-list-border bgc2 color6" style="margin-top:20px;">
	      <li class="" style="border:0;" onClick="window.location.href='${base}/userCenter/bankTo.do'">
	        <i class="am-icon-user am-icon-fw" style="color: #fcc266;"></i> 绑卡认证
	        <div style="float:right;"><i class="am-icon-check  am-icon-fw" style="color:#48c872;"></i>
	        	<#if user.realStatus==1> 已认证<#else>未认证</#if> <i class="am-icon-angle-right am-icon-fw color5"></i></div>
	      </li>
	      <li class="" onClick="window.location.href='${base}/userCenter/pwdTo.do'">
	        <i class="am-icon-list am-icon-fw" style="color: #a1dc23;"></i> 登录密码设置
	        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	      <li class="" onClick="window.location.href='${base}/userCenter/payPwdTo.do'">
	        <i class="am-icon-toggle-on am-icon-fw" style="color: #1de9e7;"></i> 交易密码设置
	        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	    </ul>
	    <div style="width:90%;margin:1em auto;">
	    	<button id="logoutButton" type="button" class="am-btn am-btn-block am-radius color1 bgc">退出</button>
	    </div>
    </body>
</html>
