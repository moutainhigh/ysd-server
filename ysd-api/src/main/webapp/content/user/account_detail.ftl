<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-我的账户-资金详情</title>
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
            <a href="javascript:void(0);" style="color:#646464;font-family:'宋体';">资金明细</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="${base}/account/detail.do?type=statistics" class="hr">账户详情</a>
            <a href="javascript:void(0);" class="hr hre">资金明细</a>
          </div>
         <form id="listForm" method="post" action="${base}/account/detail.do?type=detail">
          <div style="font-family:'宋体'; color:#000; margin-top:80px; clear:both;">
				记录时间：
                <input type="text" id = "minDate" name="minDate"  onclick="WdatePicker()" value="${minDate?default('')}"  style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/> 到 
                <input type="text" id = "maxDate" name="maxDate"  onclick="WdatePicker()" value="${maxDate?default('')}"  style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
				<select style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
					<option selected="selected"  value="all" >全部</option>
					<@listing_childlist sign="account_type"; listingList>
							<#list listingList as listing>
								<option value="${listing.keyValue}" <#if recodeType == listing.keyValue>selected</#if> >${substring(listing.name, 24, "...")}
							</option>
							</#list>
					</@listing_childlist>
				</select>
				<a href="" onclick="gotoPage(1);"  style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;">搜 索</a>
				
			</div>          
            <div style=" margin-top:10px;">
               <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
					<thead bgcolor="#efebdf" align="center">
						<tr height="39">
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">添加时间</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">类型</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">操作金额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">账户资产总额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">可用金额</th>
							<#if user.typeId==0>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">续投金额</th>
							</#if>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">冻结金额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;"><#if user.typeId==0>待收总额<#elseif user.typeId==1>待付总额</#if></th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">操作</th>
						</tr>
					</thead>
					<tbody align="center">
						
						<#list pager.result as accountDetail>
							<tr height="50">
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									${(accountDetail.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
								</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<@listing_childname sign="account_type" key_value="${accountDetail.type}"; type>
										${type}
									</@listing_childname>
								</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									${accountDetail.money?string.currency}
								</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									${accountDetail.total?string.currency}
								</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									${accountDetail.useMoney?string.currency}
								</td>
								<#if user.typeId==0>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									${(accountDetail.continueTotal?string.currency)!'￥0.00'}
								</td>
								</#if>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									${accountDetail.noUseMoney?string.currency}
								</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if user.typeId==0>${(((accountDetail.investorCollectionCapital!0) + (accountDetail.investorCollectionInterest!0))?string.currency)!}</#if>
									<#if user.typeId==1>${(((accountDetail.borrowerCollectionCapital!0) + (accountDetail.borrowerCollectionInterest!0))?string.currency)!}</#if>
								</td>
								
								<td style="border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
	                               <a style="color:#f74405;font-family:'宋体';" href="javascript:void(0)"" id="${accountDetail.id}" class="Funds">查看详情</a>
	                            </td>
							</tr>
						</#list> 
					</tbody>
				</table>
					<#if pager.totalCount==0>
						<div class="nodata">资金明细记录为空</div>
					</#if>
                   <@pagination pager=pager>
						<#include "/content/pager.ftl">
					</@pagination>
			</form>
            </div>
       </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$("#fund_statistics").addClass("nsg nsg_a1");
});
</script>
</html>
