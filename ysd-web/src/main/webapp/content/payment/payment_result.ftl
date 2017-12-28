<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-充值结果信息</title>
	<#include "/content/common/meta.ftl">
	<link rel="stylesheet" href="${base}/css/common.css">
<style>
	body{ margin:0 ; padding:0;font-family:"微软雅黑";}
	ul,li{ list-style:none; margin:0 ; padding:0;}
	#header { background: #fff;}
</style>

</head>
<body>
<#include "/content/common/header.ftl">
<link rel="stylesheet" href="${base}/css/payment.css">
<div class="find recharge">
  <div class='rechargeCont findCont'>
      <p class='title'>恭喜你，充值成功</p>
      <p>您已经成功充值<span>${(userAccountRecharge.money)!}</span>元</p>
      <p>如遇问题请拨打乐商贷客服电话:<span>400-057-7820</span>，服务时间：周一至周五<span>09:00-20:00</span></p>
      <div class='rechargebtn'>
      	<a href='${base}/userCenter/index.do' class='red'>返回账户</a>
      	<a href='${base}/payment/rechargeTo.do' class='blue'>继续充值</a>
      	<a href='${base}/borrow/listNew.do' class='yellow'>立即投资</a>
      </div>
  </div>
</div>
<#include "/content/common/footer.ftl">
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
</body>
</html>