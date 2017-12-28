<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-充值结果信息</title>
	<#include "/content/common/meta.ftl">
	
	<link rel="stylesheet" href="${base}/css/common.css">
        <script>
			function redirectUrl() {
				<#if msgUrl!=''>
					window.location.href = "${msgUrl}"
				<#else>
					window.history.back();
				</#if>
			}
	</script> 
<style>
body{ margin:0 ; padding:0;font-family:"微软雅黑";}
ul,li{ list-style:none; margin:0 ; padding:0;}
#header {
	background: #fff;
}
</style>

</head>
<body>
<#include "/content/common/header.ftl">

	<link rel="stylesheet" href="${base}/css/payment.css">
 <div class="find">
  <div class='findCont'>
      <img src='${base}/img/payment_error.png' />
      <p class='Terror'>${msg!'页面不存在'}</p>
      <p>如遇问题请拨打乐商贷客服电话:<span>400-057-7820</span>，服务时间：周一至周五<span>09:00-20:00</span></p>
      <div>
      	<a href="javascript:void(0);" onclick="redirectUrl();" class='red'>返回</a>
      </div>
  </div>
</div><!-- 找回密码 end --> 
<#include "/content/common/footer.ftl">
 <script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>

</body>
</html>