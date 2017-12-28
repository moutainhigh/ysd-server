<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-投资奖励明细</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${base}/static/js/base.js"></script>
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
			<a style="color:#646464;font-family:'宋体';" >投资奖励明细</a>
            
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a class="hr" href="${base}/spread/toGetLink.do" >好友管理</a>
			<a  class="hr" href="${base}/spread/registerAward.do">红包明细</a>
			<a class="hr hre"  href="javascript:void(0);">投资奖励明细</a>   
          </div>
           <div style="height:23px;line-height:23px;text-align:center;color:#000000;font-family:'宋体';background:#ebdec6; margin-top:70px; margin-bottom:20px; clear:both;">
           			<span style=" margin-right:150px;margin-left:20px;font-size:14px;">累计奖励:${(totalMoney?string.currency)!}</span>
              	    <span style=" margin-right:150px;font-size:14px;">累计一级好友奖励:${(totalOneMoney?string.currency)!}</span>
          </div>
			<form id="listForm" method="post" action="bidBorrowDetailList.do" >
         	 <div style="font-family:'宋体'; color:#000; margin-top:50px;clear:both;">
					时间：
					<input type="text" id = "minDate" name="userSpreadDetail.minDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(userSpreadDetail.minDate?string("yyyy-MM-dd HH:mm:ss"))!}" style="width:75px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>&nbsp;到&nbsp;
					<input type="text" id = "maxDate" name="userSpreadDetail.maxDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${(userSpreadDetail.maxDate?string("yyyy-MM-dd HH:mm:ss"))!}" style="width:75px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
					&nbsp;&nbsp;好友用户名：<input type="text" id = "username" name = "userSpreadDetail.username" value = "${(userSpreadDetail.username)!}" style="width:75px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>&nbsp;
					<input type="button" onclick="gotoPage(1);" value="搜&nbsp;索" style="display:inline-block; width:65px; height:35px; line-height:35px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;"/>
			</div>          
          <div style=" margin-top:10px;">
            <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
				<thead bgcolor="#efebdf" align="center">
					      <tr height="35">
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">时间</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">好友用户名</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">好友类型</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">好友投资金额</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">投资项目编号</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">奖励金额</th>
						  </tr>
						</thead>
						<tbody align="center">
							<#list pager.result as spreadDetail>
							<tr  height="42">
								<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(spreadDetail.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${spreadDetail.username}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if spreadDetail.statusCode == 1>一级<#else>二级</#if></td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${spreadDetail.tenderMoney?string.currency}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${spreadDetail.remark}</td>
							   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${spreadDetail.tgMoney?string.currency}</td>
							   	
							</tr>
							</#list>
						</tbody>
					</table>   
				<@pagination pager=pager baseUrl="/spread/total.do" parameterMap = parameterMap>
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
	$("#member_friend").addClass("nsg nsg_a1");
});
</script>
</body>
</html>
