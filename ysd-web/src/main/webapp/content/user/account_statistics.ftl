<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-资金详情</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
</head>
<body>
<#include "/content/common/header.ftl">
</div><!-- header end -->

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
   	<#include "/content/common/user_center_header.ftl">
    
    <div style="width:955px; float:left; background:#fff; clear:both;">
       <#include "/content/common/user_center_left.ftl">
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
            <a href="${base}/userCenter/index.do" style="color:#646464;font-family:'宋体';">我的账户</a>
            <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
            <a href="javascript:void(0);" style="color:#646464;font-family:'宋体';">账户详情</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="javascript:void(0);" class="hr hre">账户详情</a>
            <a href="${base}/account/detail.do?type=detail" class="hr">资金明细</a>
          </div>
          <div style=" font-size:16px; color:#64664;font-family:'宋体'; padding-top:25px; margin-bottom:5px; clear:both;">账户详情</div>
          <div style=" margin-top:10px;">
            <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
                    <tbody>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px; text-align:right; padding-right:5px;">可用余额：</td>
							<td colspan="3" style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${(user.ableMoney!0)?string.currency}</td>
						</tr>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">账户总额：</td>
							<td style="width:125px;color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${(user.total!0)?string.currency}</td>
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">冻结金额：</td>
							<td style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${(user.unableMoney!0)?string.currency}</td>
						</tr>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">红包总额：</td>
							<td style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${(user.awardMoney)?string.currency}</td>
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">体验金：</td>
							<td style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${(user.tasteMoney!0)?string.currency}</td>
						</tr>
					</tbody>					
            </table> 
        <#if loginUser.typeId==0>
            <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none; margin-top:20px;">
                    <tbody>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px; text-align:right; padding-right:5px;">待收总额：</td>
							<td colspan="3" style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${((user.investorCollectionCapital!0)+(user.investorCollectionInterest!0))?string.currency}</td>
						</tr>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">待收本金：</td>
							<td style="width:125px;color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${(user.investorCollectionCapital!0)?string.currency}</td>
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">待收利息：</td>
							<td style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${(user.investorCollectionInterest!0)?string.currency}</td>
						</tr>
					</tbody>					
            </table>
       <#elseif loginUser.typeId==1 && loginUser.agencytype ==1>
            <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none; margin-top:20px;">
                    <tbody>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px; text-align:right; padding-right:5px;">待还总额：</td>
							<td colspan="3" style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${((user.borrowerCollectionCapital!0)+(user.borrowerCollectionInterest!0))?string.currency}</td>
						</tr>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">待还本金：</td>
							<td style="width:125px;color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${(user.borrowerCollectionCapital!0)?string.currency}</td>
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">待还利息：</td>
							<td style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">${(user.borrowerCollectionInterest!0)?string.currency}</td>
						</tr>
					</tbody>					
            </table>
        </#if>
        <div style=" font-size:16px; color:#64664;font-family:'宋体'; padding-top:25px; margin-bottom:5px; clear:both;">资金统计</div>
        	
        	 <#if loginUser.typeId==0>
	        	  <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none; margin-top:20px;">
	                    <tbody>
							<tr height="38">
								<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px; text-align:right; padding-right:5px;">累计净收利息：</td>
								<td colspan="3" style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
									<@userAccountDetailSum_by_multi_type_count userid="${loginUser.id}" types="interest_repayment,interest_repayment_continued,tender_mange" countTypes="add,sub"; sum>
											${sum?string.currency}
										</@userAccountDetailSum_by_multi_type_count>
								</td>
							</tr>
							<tr height="38">
								<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">累计已收利息：</td>
								<td style="width:125px;color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
								<@userAccountDetailSum_by_type_count userid="${loginUser.id}" types="interest_repayment,interest_repayment_continued" countType="add"; sum>
											${sum?string.currency}
										</@userAccountDetailSum_by_type_count>
								</td>
								<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">累计利息管理费：</td>
								<td style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
									<@userAccountDetailSum_by_type userid="${loginUser.id}" type="tender_mange"; sum>
											${sum?string.currency}
										</@userAccountDetailSum_by_type>
								</td>
							</tr>
						</tbody>					
	            </table>
            </#if>
            
            <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none; margin-top:20px;">
                    <tbody>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px; text-align:right; padding-right:5px;">累计充值总额：</td>
							<td colspan="3" style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
									<@userAccountDetailSum_by_type_count userid="${loginUser.id}" types="recharge_offline,recharge" countType="add"; sum>
										${sum?string.currency}
									</@userAccountDetailSum_by_type_count>
							</td>
						</tr>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">累计线下充值总额：</td>
							<td style="width:125px;color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
									<@userAccountDetailSum_by_type userid="${loginUser.id}" type="recharge_offline"; sum>
										${sum?string.currency}
									</@userAccountDetailSum_by_type>
							</td>
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">累计线下充值奖励：</td>
							<td style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
									<@userAccountDetailSum_by_type userid="${loginUser.id}" type="recharge_offline_reward"; sum>
										${sum?string.currency}
									</@userAccountDetailSum_by_type>
							</td>
						</tr>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">累计线上充值总额：</td>
							<td style="width:125px;color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
									<@userAccountDetailSum_by_type userid="${loginUser.id}" type="recharge"; sum>
										${sum?string.currency}
									</@userAccountDetailSum_by_type>
							</td>
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">累计线上充值奖励：</td>
							<td style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
									<@userAccountDetailSum_by_type userid="${loginUser.id}" type="fee"; sum>
										${sum?string.currency}
									</@userAccountDetailSum_by_type>
							</td>
						</tr>
					</tbody>					
            </table>
            
             <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none; margin-top:20px;">
                    <tbody>
						<tr height="38">
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">累计提现总额：</td>
							<td style="width:125px;color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
									<@userAccountDetailSum_by_type userid="${loginUser.id}" type="award_add"; sum>
										${sum?string.currency}
									</@userAccountDetailSum_by_type>
							</td>
							<td style="width:205px;color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;text-align:right; padding-right:5px;">累计其它奖励总额：</td>
							<td style="color:#f74405;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">
									<@userAccountDetailSum_by_type_count userid="${loginUser.id}" types="award_continued" countType="add";sum>
										${sum?string.currency}
									</@userAccountDetailSum_by_type_count>
							</td>
						</tr>
					</tbody>					
            </table>
        
        
        
          </div>
       </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->

<#include "/content/common/footer.ftl">

<script type="text/javascript">
$(function(){
	$("#fund_statistics").addClass("nsg nsg_a1");
});
</script>
</body>
</html>
