<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-投资记录</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">
<!-- content -->
<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
	<#include "/content/common/user_center_header.ftl">
<div style="width:955px; float:left; background:#fff; clear:both;">
        <#include "/content/common/user_center_left.ftl">
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
            
			<a style="color:#646464;font-family:'宋体';">${Application ["qmd.setting.name"]}</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" >投资记录</a>
            
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="javascript:void(0);" class="hr hre">投资记录</a>
          </div>
			<form id="listForm" method="post" action="borrowDetailList.do" >
          <div style="font-family:'宋体'; color:#000; margin-top:80px;clear:both;">
          <#-->
					投标时间:
					<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)!}" style="width:75px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>&nbsp;&nbsp;到
					<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)!}" style="width:75px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
					&nbsp;&nbsp;关键字：<input type="text" name = "keywords" value="${keywords}" style="width:75px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>&nbsp;
					<select  name = "status" style="width:130px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
						<option value=""  <#if status ==''>selected</#if> >所有状态</option>
		      			<option value="3" <#if status =='3'>selected</#if> >还款中</option>
		      			<option value="7" <#if status =='7'>selected</#if> >已还完</option>
					</select>
					&nbsp;
					<input type="button" onclick="gotoPage(1);" value="搜&nbsp;索" style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;"/>
			-->
			</div>          
          <div style=" margin-top:-10px;">
            <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
				<thead bgcolor="#efebdf" align="center">
					      <tr height="35">
					      		<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">投标时间</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">标题</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">年化利率</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">投资本金</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">到期收益</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">借款状态</th>
<!--								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">协议</th>-->
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">【查看详情】</th>	
						</tr>
						</thead>
						<tbody align="center">
							
							<#list pager.result as borrowTemp>
							<tr height="50">
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${borrowTemp.repaymentDate?string("yyyy-MM-dd HH:mm:ss")}</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><a href = "${base}/borrow/detail.do?bId=${borrowTemp.borrowId}" title = "${borrowTemp.title}" target="_blank" style="color:#646464;">${substring(borrowTemp.title, 16, "")}</a></td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><@numberPercent val="${borrowTemp.varyYearRate}"; npt>${npt}</@numberPercent></td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${borrowTemp.loanAmountFee?string.currency}</td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(borrowTemp.interest)!}</td>
								
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
									<#if borrowTemp.status = 3>还款中
									<#elseif borrowTemp.status=0>发布审批中
									<#elseif borrowTemp.status=1>正在招标中
									<#elseif borrowTemp.status=2>发标审核拒绝
									<#elseif borrowTemp.status=4>满标审核失败
									<#elseif borrowTemp.status=5>等待满标审核
									<#elseif borrowTemp.status=6>过期或撤回
									<#elseif borrowTemp.status=7>已还完
									<#else>${borrowTemp.status}</#if></td>
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if borrowTemp.status=3||borrowTemp.status=7><a href="${base}/borrow/tzborrowDetail.do?id=${borrowTemp.id}"  style="color:#646464;">查看</a><#else>--</#if></td>
							</tr>
							</#list>
						</tbody>
					</table>   
				<@pagination pager=pager baseUrl="/borrow/borrowDetailList.do" parameterMap = parameterMap>
					<#include "/content/pager.ftl">
				</@pagination>       
          </div>
       </div>
    </div>
</form>
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->       
<#include "/content/common/footer.ftl">
<script type="text/javascript">
$(function(){
	$("#investment_record").addClass("nsg nsg_a1");
});
</script>
</body>
</html>
