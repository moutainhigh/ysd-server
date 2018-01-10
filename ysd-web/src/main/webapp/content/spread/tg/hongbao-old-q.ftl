<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户-红包及奖励</title>
	<#include "/content/common/meta.ftl">
	<style>
  .status{overflow: hidden;margin:20px 0 0 12px;font-size: 14px;}
  .status li{float:left;color:#666666;}
  .status li a{color:#666666;padding:2px 4px;margin:0 9px;}
  .status li a.cur{color:#fff;background-color:#fd7c1a;border-radius:3px;display: inline-block;}
</style>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">


<#include "/content/common/user_center_header.ftl">

<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px; margin-top:20px;">
    <!--left-->
	<#include "/content/common/user_center_left.ftl">
    <!--right-->
   <div style="width:942px; float:right;">
      <div style="border:1px solid #e6e6e6; background:#fff;margin-bottom:40px;">
         <ul style="width:940px; height:50px; background:#eee;" cla8ss="user_list_qh">
           <li class="tzlist_user">红包</li>
           <li onclick="javascript:window.location.href='/award/cash.do'">奖励</li>
         </ul>
         <div style="position:relative;">
           <ul class="status" style="border-bottom:1px solid #dbdbdb;padding:6px 0 22px;">
             <li><a <#if status =='' || status == 0> class="cur" </#if> href="${base}/award/hongbao.do">未使用</a>|</li>
             <li><a <#if status?exists && status ==1> class="cur" </#if> href="${base}/award/hongbao.do?status=1">已使用</a>|</li>
             <li><a <#if status?exists && status ==2> class="cur" </#if> href="${base}/award/hongbao.do?status=2">已失效</a></li>
           </ul>
           <a href="${base}/award/hongbaoDetail.do" style="display:block;color:#fd7c1a;border:1px solid #fd7c1a;padding:7px 10px;border-radius:5px;position:absolute;top:3px;right:34px;font-size: 14px;">查看红包明细></a>
         </div>
     
     <form id="listForm" method="post" action="${base}/award/hongbao.do<#if status?exists>?status=${status}</#if>">
         <div style="width:100%; clear:both;">
           <div style="display:block; width:940px; margin:0 auto;" class="user_div_qh">
             <div style="overflow:hidden;margin-top:30px">
             <#list pager.result as hb>
                <ul style="margin:0 20px 50px;width:192px;height:150px;float:left;overflow:hidden;">
                 <#if hb.status == 0>
                  <li style="float:left;width:192px;height:150px;background:url(/img/hb1.png) no-repeat;background-size: cover;font-size:14px;color:#fff;text-align:center;">
                 <#else>
                  <li style="float:left;width:192px;height:150px;background:url(/img/hb2.png) no-repeat;background-size: cover;font-size:14px;color:#fff;text-align:center;">
                 </#if>
                   <#if hb.status == 0>
	                    <p style="margin: 12px 0 5px;color: #fffba5;"><#if hb.isPc==1 && hb.isApp ==1>通用<#else><#if hb.isApp==1 || hb.isHfive==1>移动端<#else>电脑端</#if></#if></p>
	                    <p class="hbMoney" style="color:#fffba5;font-weight:bold;font-size:22px;margin:18px 0 5px;">${hb.money}元红包</p>
	                    <p>${hb.overDays}天后过期</p>
	                    <p>失效时间：${hb.endTime?string("yyyy-MM-dd")} </p>
	                    <p>使用期限:${hb.limitStart}-${hb.limitEnd}天 </p>
                  <#elseif hb.status ==1>
               			<p style="margin: 12px 0 5px;color: #fffba5;"><#if hb.isPc==1 && hb.isApp ==1>通用<#else><#if hb.isApp==1 || hb.isHfive==1>移动端<#else>电脑端</#if></#if></p>
	                    <p class="hbMoney" style="color:#fffba5;font-weight:bold;font-size:22px;margin:18px 0 5px;">${hb.money}元红包</p>
	                    <p>已使用</p>
	                    <p>使用时间：${hb.modifyDate?string("yyyy-MM-dd")} </p>
                  <#elseif hb.status ==2>
                   		<p style="margin: 12px 0 5px;color: #fffba5;"><#if hb.isPc==1 && hb.isApp ==1>通用<#else><#if hb.isApp==1 || hb.isHfive==1>移动端<#else>电脑端</#if></#if></p>
	                    <p class="hbMoney" style="color:#fffba5;font-weight:bold;font-size:22px;margin:18px 0 5px;">${hb.money}元红包</p>
	                    <p>已过期</p>
	                    <p>过期时间：${hb.endTime?string("yyyy-MM-dd")} </p>
                  </#if>
                  
                  </li>
                </ul>
             </#list>   
                
              </div>
	            <@pageFlip pager=pager>
					<#include "/content/common/pager.ftl">
				</@pageFlip>
           
          </div>
      </div>
	<!--
      <div style="width:910px;height:146px;background:#fff;padding:20px 0px 0px 30px;margin-top:12px;border-top:1px solid #e6e6e6;color:#666;font-size:14px;">
          <h3 style="height:32px;line-height:32px;background:url(/img/tips.png) no-repeat;padding-left:40px;font-weight:normal;">红包使用提示：</h3>
          <p style="line-height:32px;padding-left:40px;">1、每个红包都有不同的红包类型和使用有效期，红包只能用于投资抵扣，不能用来兑换提现。</p>
          <p style="line-height:32px;padding-left:40px;">2、投资抵扣红包金额不能大于投资金额的2%，将按实际投资金额进行抵扣，不反剩余部分。</p>
          <p style="line-height:32px;padding-left:40px;">列：投资金额500元，可使用红包最多10元，抵扣完成后10元红包被标记为已使用。</p>
      </div>
	-->
    </div>
    </form>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->     

<#include "/content/common/footer.ftl">
    <script type="text/javascript">
		$().ready( function() {
			
			$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
			$("#hbjjl").addClass("click_current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
</body>
</html>
