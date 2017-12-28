<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户-我要充值</title>
	<#include "/content/common/meta.ftl">

</head>
<body>

<!-- header -->

<#include "/content/common/header.ftl">

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
	<#include "/content/common/user_center_header.ftl">
 <div style="width:955px; float:left; background:#fff; clear:both;">
	<#include "/content/common/user_center_left.ftl">

	<#-- 		右边内容块 开始 				-->
	<div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
            <a href="${base}/userCenter/index.do" style="color:#646464;font-family:'宋体';">我的账户</a>
            <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
            <a style="color:#646464;font-family:'宋体';">充值记录</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="${base}/payment/rechargeTo.do" class="hr">我要充值</a>
            <a href="" class="hr hre">充值记录</a>
          </div>
          <div style="font-family:'宋体'; color:#000; margin-top:80px; clear:both;">
          <form id="listForm" method="post" action="detail.do?type=recharge">
				查询时间：<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)!}" / style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"> 
				到 <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)!}" / style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;">
		                <select name = "recodeType" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;">
		                  <option value="" >所有状态</option>
					      		<option value="2" <#if recodeType == 2>selected</#if> >审核中</option>
					      		<option value="1" <#if recodeType == 1>selected</#if> >成功</option>
					      		<option value="0" <#if recodeType == 0>selected</#if> >失败</option>
		                </select>
						<input type="button" onclick="gotoPage(1);" value="搜索 "  style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;"/>
			</div>  
          <div style="height:33px;line-height:33px;text-align:center;color:#595757;font-family:'宋体';background:#efebdf; margin-top:20px; margin-bottom:20px;">
            	
          </div>
            <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
				<thead bgcolor="#efebdf" align="center">
						<tr height="35">
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">类型</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">支付方式</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">充值金额</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">充值奖励</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">充值时间</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">状态</th>
							<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">备注</th>
						</tr>
					</thead>
					<tbody align="center">
						<#list pager.result as accountRecharge>
								<#assign flag = "">
								<#if accountRecharge_index %2 != 0>
									<#assign flag = "td1">
								</#if>
							<tr height="35">
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if accountRecharge.type==0>线下<#else>线上</#if>充值
								</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountRecharge.name}</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountRecharge.money?string.currency}</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if accountRecharge.fee &gt; 0>
										-${accountRecharge.fee?string.currency}
									<#else>
										${accountRecharge.reward?string.currency}
									</#if>
								</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if accountRecharge.rechargeDate!>
										${accountRecharge.rechargeDate?string("yyyy-MM-dd HH:mm:ss")}
									<#else>
										${accountRecharge.createDate?string("yyyy-MM-dd HH:mm:ss")}
									</#if>
								</td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if accountRecharge.status==1>成功<#elseif accountRecharge.status==2>审核中<#else>失败</#if></td>
								<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountRecharge.remark}</td>
							</tr>
						</#list>             
						
					</tbody>
				</table>  
				<#if pager.totalCount==0>
					<div class="nodata">充值记录为空</div>
				</#if>
                  <div style="text-align:center;color:#646464;font-family:'宋体'; padding:15px 0px;">
						<@pagination pager=pager baseUrl="/account/detail.do?type=recharge" parameterMap = parameterMap>
							<#include "/content/pager.ftl">
						</@pagination>
					</form>
				</div>          
          </div>
          
	<#-- 		右边内容块 结束 				-->
	</div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->

 <!--footer-->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	$("#fund_recharge").addClass("nsg nsg_a1");
});
</script>
</html>
