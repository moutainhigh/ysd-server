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
      <div style="border:1px solid #e6e6e6; background:#fff; margin-top:16px;padding-bottom:40px;">
         <ul style="width:940px; height:50px; background:#eee;" class="user_list_qh">
           <li onclick="javascript:window.location.href='/award/hongbao.do'" class="tzlist_user">红包</li>
           <li onclick="javascript:window.location.href='/award/cash.do'">奖励</li>
         </ul>
         <div style="padding:20px 15px 20px 0;text-align:right;">
           <a href="#" onclick="window.history.back(); return false;" style="width:66px;height:34px;display: inline-block;background:#fd7c1a;color:#fff;border-radius:5px;line-height:34px;text-align:center;">返回</a>
         </div>
         <div style="width:100%; clear:both;">
          <div style=" width:940px; margin:0 auto;">
	<form id="listForm" method="post" action="${base}/award/hongbaoDetail.do">
             <table width="100%" cellpadding="0" cellspacing="0" border="0" style="width:900px;margin:20px auto 0;">
               <thead bgcolor="#fd7c1a">
                 <tr height="38">
                   <th style="color:#fff; font-size:14px;">时间</th>
                   <th style="color:#fff; font-size:14px;">收入</th>
                   <th style="color:#fff; font-size:14px;">支出</th>
                   <th style="color:#fff; font-size:14px;">描述</th>
                 </tr>
               </thead>
               <tbody align="center">
               <#list pager.result as awardDetail>
                  <tr height="72">
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;width:200px;">${(awardDetail.createDate?string("yyyy-MM-dd HH:mm"))!}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;"><#if awardDetail.signFlg ==1>${awardDetail.money}<#else>--</#if></td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;width:180px;">
                    <#if awardDetail.signFlg == -1>${awardDetail.money}<#else>--</#if>
                    </td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${awardDetail.remark}</td>
                  </tr>
			  </#list>
               </tbody>
             </table>
	            <@pageFlip pager=pager>
					<#include "/content/common/pager.ftl">
				</@pageFlip>
	</form>
           </div>
         </div>
      </div>
	<!--
      <div style="width:910px;height:146px;background:#fff;padding:20px 0px 0px 30px;margin-top:12px;border:1px solid #e6e6e6;color:#666;font-size:14px;">
          <h3 style="height:32px;line-height:32px;background:url(img/tips.png) no-repeat;padding-left:40px;font-weight:normal;">红包使用提示：</h3>
          <p style="line-height:32px;padding-left:40px;">1、每个红包都有不同的红包类型和使用有效期，红包只能用于投资抵扣，不能用来兑换提现。</p>
          <p style="line-height:32px;padding-left:40px;">2、投资抵扣红包金额不能大于投资金额的2%，将按实际投资金额进行抵扣，不反剩余部分。</p>
          <p style="line-height:32px;padding-left:40px;">列：投资金额500元，可使用红包最多10元，抵扣完成后10元红包被标记为已使用。</p>
      </div>
	-->
</div>
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
