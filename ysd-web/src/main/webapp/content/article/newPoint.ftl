<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-新手指引</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
 <link rel="stylesheet" href="${base}/css/common.css" />
	<#include "/content/common/header.ftl">

<style>
.safe{background:#fff;}
.safe_top{background:url(${base}/img/Xinverst_bg.jpg) no-repeat center center;height:690px;margin-top:-96px;}
.safe_cont{width:1200px;margin:0 auto;box-sizing:border-box;padding:0 14px 300px;}
.safe_cont li{
	height:496px;
}
.safe_cont li img{
	width:416px;
	height:416px;
}
.safe_cont li:first-child img{
	margin-right:32px;
}
.safe_cont li.Iright img{
	float:right;
}
.safe_cont li.Ileft img{
	float:left;
	margin-right:260px;
}
.safe_cont li span{
	padding-top:158px;
	font-size:18px;
	line-height:18px;
	margin-bottom:23px;
	color:#ef3e44;
	display:block;
}
.safe_cont li p{
	font-size:18px;
	line-height:32px;
	color:#333;
}
.safe_cont li a{
	display:block;
	width:116px;
	height:34px;
	line-height:34px;
	font-size:18px;
	border-radius:10px;
	color:#fff;
	background:#ef3e44;
	margin-top:35px;
	text-align:center;
}
.safe_cont li.Ileft a{
	margin-left:676px;
}
.safe_cont li:first-child{
	height:366px;
}
.safe_cont li:first-child span{
	padding-top:52px;
}
</style>
<div class='safe'>
	<div class='safe_top'>
	<!--<img src='${base}/img/Xinverst_bg.jpg'>-->
	</div>
	<div class='safe_cont'>
		<ul>
			<li class='Iright'>
				<img src='${base}/img/Xinverst1.png'>
				<span>第一步：注册账号</span>
				<p>请使用常用手机号注册,用于及时接收账户动态和最新活动</p>
				<p>注册成功即送500元现金红包</p>
				<a href='${base}/user/reg.do'>注册账号</a>
			</li>
			<li class='Ileft'>
				<img src='${base}/img/Xinverst2.png'>
				<span>第二步：绑卡认证</span>
				<p>请使用个人真实姓名、身份证号及银行卡号完成认证</p>
				<p>身份认证后资金只在您的名下流动,确保资金安全</p>
				<a href='${base}/userCenter/toBank.do'>绑卡认证</a>
			</li>
			<li class='Iright'>
				<img src='${base}/img/Xinverst3.png'>
				<span>第三步：资金充值</span>
				<p>通过国家认可的第三方支付进行资金充值，快速便捷，安全可靠</p>
				<a href='${base}/userCenter/borrowDetailList.do'>资金充值</a>
			</li>
			<li class='Ileft'>
				<img src='${base}/img/Xinverst4.png'>
				<span>第四步：选择理财项目</span>
				<p>根据您自身资金的周转情况和收益预期，选择不同的理财项目进行投资</p>
				<a href='${base}/borrow/listNew.do'>投资项目</a>
			</li>
			<li class='Iright'>
				<img src='${base}/img/Xinverst5.png'>
				<span>第五步：取得收益</span>
				<p>借款人按时还款，投资人账户立即收到所有的收益，理财就是那么轻松！</p>
			</li>
		</ul>
	</div>
</div>


<!-- safe end -->

	<#include "/content/common/footer.ftl">
	
<script src="../js/jquery-1.11.3.min.js"></script>
<script src="../js/common.js"></script>
</body>
</html>
