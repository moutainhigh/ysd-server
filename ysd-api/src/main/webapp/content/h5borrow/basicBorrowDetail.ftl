<!doctype html>
<html class="no-js">
<head>
   <meta charset="utf-8">
  	<title>基本信息</title>
 	<#include "/content/common/meta.ftl">
  <style>
  	.am-form-group {margin-bottom: 1rem;}
    h3{font-size: 0.95em;background-color: #edeef0; padding: 0.3em 1em;}
    h4{padding: 0.5em 0;font-weight: normal;font-size: 1em;}
    hr{height:1px;}
    .content{padding:0 1em 1em;}
    .content p{text-indent: 2em;}   
    hr.am-divider{margin: 1.5rem auto 0.5rem;}
  </style>
</head>
    <body class="color2 bgc3">
        <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40 color1">
	      <div class="am-header-left am-header-nav">
	      	<a href="#left-link" onclick="window.history.back(); return false;">
	          	  <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
	        </a>
	      </div>
	      <h1 class="am-header-title">
	          项目基本信息
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->
   		<div>
   			<h3>项目详情</h3>
   			<div class="content bgc2">
   				<h4>债务人信息</h4>
   				${(bri.debtMess)!}
   				 <hr data-am-widget="divider" style="" class="am-divider am-divider-default"/>
   				<h4>项目描述</h4>
   				${bri.content}
   				  <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
   				<h4>资金用途</h4>
   				${(bri.useReason)!}
   				  <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
   				<h4>还款来源</h4>
   				${bri.paymentSource}
   				
   			</div>
   		</div>
   		<div>
   			<h3>还款计划</h3>
        <div  class="content bgc2" style="padding-top:1em;">
          <table width="100%" cellpadding="0" cellspacing="0" style="border-bottom:none; color:#666;">
                <thead align="center">
                   <tr height="40">
                      <th class="color6 tac" style="font-weight:bold;border-bottom:1px solid #dbdbdb;">期数</th>
                      <th class="color6 tac" style="font-weight:bold;border-bottom:1px solid #dbdbdb;">还款日期</th>
                      <th class="color6 tac" style="font-weight:bold;border-bottom:1px solid #dbdbdb;">还款总额</th>
                      <th class="color6 tac" style="font-weight:bold;border-bottom:1px solid #dbdbdb;">还款本金</th>
                      <th class="color6 tac" style="font-weight:bold;border-bottom:1px solid #dbdbdb;">还款利息</th>
                   </tr>
                </thead>
                <tbody align="center">
                <#list bri.repaymentDetailList as d>
                  <tr height="40">
                     <td style="border-bottom:1px solid #dbdbdb; ">${d.orderNum}</td>
                     <td style="border-bottom:1px solid #dbdbdb; ">第${d.repaymentDateInt}天</td>
                     <td style="border-bottom:1px solid #dbdbdb; ">${d.account}</td>
                     <td style="border-bottom:1px solid #dbdbdb; ">${d.capital}</td>
                     <td style="border-bottom:1px solid #dbdbdb; ">${d.interest}</td>
                  </tr>
                </#list>
                  <tr height="40">
                     <td style="border-bottom:1px solid #dbdbdb; " colspan="2">小计</td>
                     <td class="color" style="color:#f3485a;border-bottom:1px solid #dbdbdb;">${bri.account}</td>
                     <td style="border-bottom:1px solid #dbdbdb; ">${bri.capital}</td>
                     <td style="border-bottom:1px solid #dbdbdb; ">${bri.interest}</td>
                  </tr>
              </tbody>
            </table>
        </div>
   		</div>
	  <script>
	 
	  </script>
    </body>
</html>
