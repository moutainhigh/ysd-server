<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-注册成功</title>
	<#include "/content/common/meta.ftl">
	 <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
 	<script type="text/javascript" src="${base}/js/common.js"></script>
	<link rel="stylesheet" href="${base}/css/common.css">
<style>
body{ margin:0 ; padding:0;font-family:"微软雅黑";}
#header {
background-color: #fff;
}
ul,li{ list-style:none; margin:0 ; padding:0;}
</style>
<link rel="stylesheet" href="${base}/css/loginSuccess.css">

</head>
<body>
<#include "/content/common/header.ftl">
  <div class="find operation">
  <div class='operationCont findCont'>
  	<img src='${base}/img/register_success_11.png'>
  	<span>恭喜您，注册成功</span>
  	<#--<span><a href="${base}/app/download.do">点击下载手机APP</a></span>-->
  	<span><a href="http://download.yueshanggroup.cn/app/download.do">点击下载手机APP</a></span>
  	<p>您已获得<span>${rechageHBMoney}元</span>新人专享红包！</p>
    <div class='qybtn'>
        <a href="${base}/userCenter/toBank.do">实名认证</a>
    </div>
  </div>
</div>
<!-- end -->
<#include "/content/common/footer.ftl">
</body>
</html>
