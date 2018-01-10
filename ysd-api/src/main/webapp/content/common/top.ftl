<#include "/content/common/meta.ftl">
<body>
<!-- header -->
<div id="header" class="header">
     <div class="headerc"> 
     <h1>您好<span id="headerLoginUsername"></span>, 欢迎来到${Application ["qmd.setting.name"]} .
      <a href="${base}/user/login.do" id="headerShowLoginWindow">登录</a>
      <a href="#"></a> <a href="${base}/user/reg.do" id="headerShowRegisterWindow">免费注册</a> 
      <span class="headfr">
      <a href="${base}/userCenter/index.do" id="headerUserCenter">我的账户 | </a>
      <a href="${base}/userCenter/logout.do" id="headerLogout">退出 |</a> 
      <a href="#">帮助</a> | 
      <a href="#">收藏</a> | 
      <a href="#">+分享到</a>
      </span></h1>
     <#include "../../content/common/logo.ftl">
	</div>
</div>