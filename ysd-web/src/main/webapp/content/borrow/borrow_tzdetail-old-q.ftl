<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户-收益明细</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
function redirectUrl() {		
	window.history.back();		
}
</script> 
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
      <a href="javascript:redirectUrl();"  onclick="redirectUrl();" style="display:block; width:55px; height:26px;  line-height:26px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a; margin-bottom:15px;">返回</a>
      <div style="border:1px solid #e6e6e6; background:#fff; padding-bottom:0; float:left; margin-bottom:20px;">
         <div style=" width:940px; height:64px; color:#666666; font-size:16px;">
           <div style="float:left; margin:20px 0 0 25px;"><span>${(borrowTender.title)!}</span><span style="padding:0 25px;">丨</span><span>${(borrowTender.borrowAccount)!}</span><span style="padding:0 25px;">丨</span><span style="padding:0 25px;">${borrowTender.timeLimit}<#if borrowTender.isday==0>个月<#else>天</#if></span><span style="padding:0 25px;">丨</span><span>${borrowTender.apr}<#if borrowTender.isday==0>%<#else>‰</#if></span></div>
           <a href="" style="display:block; width:110px; height:30px;line-height:30px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a; margin:20px 15px 0 0; float:right;">更多项目信息</a>
         </div>
         <ul class="detail_s8" style="background:#f5f5f5; width:100%; padding:15px 0; margin: 0;">
          <li class="detail_s9" style="width:249px; border-right:1px solid #e6e6e6; text-align:center;">
            <div class="detail_s10" style="padding-bottom:8px;">投资本金（元）</div>
            <div class="detail_s11">${(borrowTender.account)!}</div>
          </li>
          <li class="detail_s12" style="width:389px; border-right:1px solid #e6e6e6; text-align:center; padding:0;">
            <div class="detail_s13" style="padding-bottom:8px;">到期收益（奖励收益）</div>
            <div class="detail_s14">${(borrowTender.interest)!}</div>
          </li>
          <li class="detail_s15" style="width:289px; text-align:center; padding:0; position:relative;">
            <div class="detail_s16" style="padding-bottom:8px;">还款时间</div>
            <div class="detail_s17">${reTime?string("yyyy-MM-dd")}</div>
            <div style="position:absolute; right:12px; top:-10px;">
            <#if borrowTender.borrowStatus==3>
      			还款中
      		<#elseif borrowTender.borrowStatus==7>
      			<img src="/img/a18.png" width="70" height="68" />
      		<#elseif borrowTender.borrowStatus==1>
      			募集中
      		</#if>
      		</div>
          </li>
        </ul>
        <ul class="detail_s18" style="color:#666666; font-size:14px; padding:15px 0; float:left; width:100%;">
             <li class="detail_s27">支付方式：账户余额 支付 ${(borrowTender.ableAmount)!},红包支付${(borrowTender.hongbaoAmount)!}</li>
             <li class="detail_s27">还款方式：
             	<#if borrowTender.style == 1>分期付息
				<#elseif borrowTender.style == 2>到期付本息
				<#elseif borrowTender.style == 3>等额本金
				</#if>
			</li>
             <li class="detail_s27">投资时间：${borrowTender.createDate?string("yyyy-MM-dd HH:mm")}</li>
             <li class="detail_s27"><img src="/img/a19.png" width="17" height="21" style="position:relative; top:-2px;" /><a href="${base}/borrow/borrowAgreement.do?borrow.id=${borrowTender.borrowId}&id=${borrowTender.id}" style="color:#7c96e8; padding-left:10px;">投资协议查看</a></li>
          </ul>
        </div>
        <div style="border:1px solid #e6e6e6; background:#fff; padding-bottom:60px; clear:both;">
         <div style="width:100%; clear:both; padding-top:20px;">
           <div style="display:block; width:900px; margin:0 auto;">
             <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <thead bgcolor="#fd7c1a">
                 <tr height="38">
                   <th style="color:#fff; font-size:14px;">回款时间</th>
                   <th style="color:#fff; font-size:14px;">回款本金</th>
                   <th style="color:#fff; font-size:14px;">到期收益</th>
                   <th style="color:#fff; font-size:14px;">回款至</th>
                   <th style="color:#fff; font-size:14px;">状态</th>
                 </tr>
               </thead>
               <tbody align="center">
                <#list userRepDetailList as urd>
                  <tr height="72">
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${urd.repaymentDate?string("yyyy-MM-dd")}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${urd.waitAccount}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${urd.waitInterest}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">账户余额</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">
                    	  <#if urd.status==0>	
            				待回款
            			 <#else>
            				已回款
            			</#if>
                    </td>
                  </tr>
                  </#list>
               </tbody>
             </table>
           </div>
         </div>
      </div>
    </div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->
<#include "/content/common/footer.ftl">
<script type="text/javascript">
$(function(){
	$("#investment_income").addClass("nsg nsg_a1");
});
</script>
</body>
</html>
