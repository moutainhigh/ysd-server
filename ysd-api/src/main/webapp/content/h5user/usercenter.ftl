<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>我的账户</title>
	<#include "/content/common/meta.ftl">
</head>
    <body class="bgc5">
       <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	      <div class="am-header-left am-header-nav">
	      	<a href="#left-link" class="">
	        <#--   <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>-->
	        </a>
	      </div>
	      <h1 class="am-header-title">
	          	会员中心
	      </h1>
	      <div class="am-header-right am-header-nav">
	      	<a href="${base}/userCenter/detail.do" class="">
	          <i class="am-header-icon am-icon-cog" style="font-size:1.1em;"></i>
	        </a>
	      </div>
	  </header><!-- header end-->
	   <div class="bgc color1" style="overflow:hidden;">
	   		<div style="width:4.5em;height:4.5em;border:2px solid #fcd1d1;border-radius:50%;margin:0.5em auto;box-sizing:content-box;">
	   		<img src="${Application["qmd.img.baseUrl"]}/mobile/${userCenter.litpic}" alt="" style="width:4.5em;height:4.5em;border-radius:50%;display:inline-block;"></div>
	   		<p class="tac">${userCenter.username}</p>
	   		<div class="tac" style="margin:0.8em 0 1.5em;">
	   			<#if userCenter.realStatus==1>
	   				<a href="javascript:void(0);" style="color:#fff;padding:0.3em 0.8em;border-radius:30px;border:1px solid #fff;">${userCenter.realName}</a>
	   			<#else>
	   				<a href="${base}/bank/bankTo.do" style="color:#fff;padding:0.3em 0.8em;border-radius:30px;border:1px solid #fff;">实名认证</a>
	   			</#if>
	   		</div>
	   </div>
	   <div class="tac bgc" style="height:2.8em;line-height:2.8em;color:#fbbfc0;">累计收益
	    <span class="fwb f12" style="color:#fff;">${userCenter.repaymentMoney?string("0.00")}</span> 元</div>
	   <div>
			<ul class="color4 bgc1" style="overflow:hidden;padding:0.5em 0;">
				<li class="fl w50" style="padding:0 0.6em 0 1em;border-right:1px solid #e3e3e3; position: relative;" onclick="window.location.href='${base}/content/h5user/myInvestList.html'">
					<p>投资记录<span style="color:#b3b3b3;float:right;">${(userCenter.tenderTime?string("MM/dd"))!}</span></p>
					<p><span class="color f12">${(userCenter.tenderMoney?string("0.00"))!'0.00'}</span> 元</p>
					<span style="width:1.5em;height:1.5em;background:url(../mobile_img/jiao.png) no-repeat;position: absolute;bottom:-1em;right:0.6em;"></span>
				</li>
				<li class="fl w50" style="padding:0 0.6em 0 1em; position: relative;" onclick="window.location.href='${base}/content/h5user/returnedNoMoney.html'">
					<p>回款记录<span style="color:#b3b3b3;float:right;">${(userCenter.repaymentTime?string("MM/dd"))!}</span></p>
					<p><span class="color f12">${(userCenter.repaymentMoney?string("0.00"))!'0.00'}</span> 元</p>
					<span style="width:1.5em;height:1.5em;background:url(../mobile_img/jiao.png) no-repeat;position: absolute;bottom:-1em;right:0.6em;"></span>
				</li>
			</ul>
		</div>
   		<ul class="am-list am-list-static am-list-border bgc2 color6" style="margin-top:20px;">

	      <li class="" style="border:0;" onclick="window.location.href='${base}/payment/rechargeTo.do'">
	      <i class="am-icon-sign-in am-icon-fw" style="color: #f6ae54;" ></i> 我要充值
	        <span style="float:right;">${userCenter.ableMoney?string("0.00")}元 <i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	      
		  <li class="" onclick="window.location.href='${base}/cash/cashTo.do'">
	      <i class="am-icon-sign-out am-icon-fw" style="color: #f6ae54;" ></i> 我要提现
	        <span style="float:right;"> <i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>      
	      <li class=""  onclick="linkTo('hongbao_list');">
	        <i class="am-icon-cubes am-icon-fw" style="color: #f24949;"></i> 红包及奖励
	        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	      <li class="" onclick="linkTo('account_detail_list');">
	        <i class="am-icon-list am-icon-fw" style="color: #016cbc;"></i> 资金记录
	        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	      <li class=""  onclick="window.location.href='${base}/zdtzTo.do'">
	        <i class="am-icon-toggle-<#if userCenter.autoTenderStatus==1>on<#else>off</#if> am-icon-fw" style="color: #22ac39;"></i> 自动投资
	        <span style="float:right;"><i class="am-icon-angle-right am-icon-fw color5"></i></span>
	      </li>
	    </ul>
		<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default  am-no-layout" id="" >
		   <ul class="am-navbar-nav am-cf am-avg-sm-4" style="background-color:#f9f9f9;">
		     <li>
		      <a  href="${base}/index.do">
		        <span class="am-icon home"></span>
		        <span class="am-navbar-label">首页</span>
		      </a>
		    </li>
		     <li>
		      <a href="${base}/content/h5invest/investList.html">
		        <span class="am-icon list"></span>
		        <span class="am-navbar-label">产品列表</span>
		      </a>
		    </li>
		     <li>
		      <a class="active" href="${base}/userCenter/index.do">
		        <span class="am-icon account"></span>
		        <span class="am-navbar-label">我的钱袋</span>
		      </a>
		    </li>
		     <li>
		      <a href="${base}/userCenter/detail.do">
		        <span class="am-icon Set_up"></span>
		        <span class="am-navbar-label">设置</span>
		      </a>
		    </li>
		     <li>
		      <a href="${base}/moreTo.do">
		        <span class="am-icon more"></span>
		        <span class="am-navbar-label">更多</span>
		      </a>
		    </li>
		  </ul>
		</div><!-- footer end-->
    </body>
    
     <script>
     	function linkTo(link){
     		window.location.href=qmd.base+'/content/h5hongbao/'+link+'.html';
     	}
     </script>
</html>
