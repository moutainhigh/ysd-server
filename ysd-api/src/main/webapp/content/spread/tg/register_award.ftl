<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户-红包明细</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="/static/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="/static/js/jquery/jquery.validate.methods.js"></script>
	
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
			<a href="${base}/">${Application ["qmd.setting.name"]}</a><a>></a>
			<a href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a class="liebiao">红包明细</a>
		</div>
		
			
			
		<div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
           <a href="${base}/spread/toGetLink.do"class="hr">好友管理</a>
			<a  href="javascript:void(0);" class="hr hre" href="${base}/spread/registerAward.do">红包明细</a>
			<a class="hr " href="${base}/spread/total.do">投资奖励明细</a>   
          	</div>
          	<form id="listForm" method="post" action="${base}/spread/registerAward.do">
          	<div style="font-family:'宋体'; color:#000; margin-top:80px; clear:both;">
			</div>          
          	    <div style=" margin-top:10px;">
				<table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
				<thead bgcolor="#efebdf" align="center">
							<tr height="35">
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">时间</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">类型</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">收入</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">支出</th>
								<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">剩余红包</th>
							</tr>
						</thead>
						<tbody align="center">
							<#list pager.result as awardDetail>
								<#assign flag = "">
									<#if awardDetail_index %2 != 0>
										<#assign flag = "td1">
									</#if>
								<tr  height="42">
									<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(awardDetail.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
								   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${awardDetail.typeName}</td>
								   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if awardDetail.signFlg ==1>${awardDetail.money}<#else>--</#if> </td>
								   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"><#if awardDetail.signFlg == -1>${awardDetail.money}<#else>--</#if></td>
								   	<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${awardDetail.awardMoney}</td>
								</tr>
							</#list>
						</tbody>
					</table>
				 <#if pager.totalCount==0>
					<div class="nodata"  align="center" >红包明细记录为空</div>
				  </#if>
				  <div  align="center" >
					<@pagination pager=pager >
						<#include "/content/pager.ftl">
					</@pagination>
				</div>
				<!--fanye end-->
			</form>
		  </div>
       </div>

	<#-- 		右边内容块 结束				-->
	
	</div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->

<#include "/content/common/footer.ftl">
<script type="text/javascript">
$(function(){
	$("#menuUser").addClass("kaqu_bg");
	$("#menuUser_ul").addClass("user_content_top1");
	$("#menuUser_SpreadList").addClass("user_dlk");
});
$(function(){
	$("#member_friend").addClass("nsg nsg_a1");
});
</script>
</body>
</html>
