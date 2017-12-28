<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-绑卡认证--已认证</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/already_identify.css" />
</head>
<body id="bg">
<!-- 头部 -->

<#include "/content/common/header.ftl">

<!--内容区域-->
<div id="main">
    <!--左边导航栏-->
    <#include "/content/common/user_center_left.ftl">
    <!--右边主内容区-->
    
    <#if accountBank?exists >
    <div class="content fr">
       
       <#if accountBank.status==0>
       <div class="already_identify_ing">
                 前往认证
       </div>
       <#else>
       <div class="already_identify">
      	 已认证
       </div>
       </#if>
       
       
        <div class="my_bankcard">
            <span>我的银行卡：</span>
            <#if accountBank.status==0><a href="${base}/userCenter/toBankInput.do" ></#if>
            <div class="card_type">
                <div class="card_name banks_bk banks_${accountBank.bankId}" ></div>
                <div class="card_num">***************${accountBank.account?substring(accountBank.account?length-4,accountBank.account?length)}</div>
            </div>
            <#if accountBank.status==0></a></#if>
        </div>
        <div class="hint">
            <span>温馨提示：</span>只能有一张银行卡用于提现及手机端投资哦
        </div>
    </div>
    <#else> 
   
  	<!--不会进入else的代码-->
    </#if>
  
    
    <div class="clear"></div>
</div>

<!--底部-->
<#include "/content/common/footer-center.ftl">
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script type="text/javascript">
		$().ready( function() {
			
			$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
			$('#header_gywm').find('a').css('border',0);
			$(".center_left_bkrz").addClass("current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
</html>
