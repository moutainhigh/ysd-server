<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-推荐有奖-我的好友</title>
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
	
 	<div style="width:670px; float:right; padding:0px 15px 20px 18px; ">
          <div style="padding-top:30px;">
            <a href="" style="color:#646464;font-family:'宋体';">我的账户</a>
            <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
            <a href="" style="color:#646464;font-family:'宋体';">我的红包</a>
          </div>
          <div style=" width:651px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="${base}/award/toGetLink.do" class="hr">邀请有奖</a>
            <a href="#" class="hr hre">我的好友</a>
          </div>
           <form id="listForm" method="post" action="${base}/award/myFriend.do">
          <div class="neirong_table" style="display:block; clear:both; width:660px; padding-top:25px;">
                  <table width="100%" cellspacing="0" cellpadding="0" style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
					<thead bgcolor="#ede3cb" align="center">
						<tr height="39">
							<th style="color:#000;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:12px;">好友手机</th>
							<th style="color:#000;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:12px;">加入时间</th>
							<th style="color:#000;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:12px;">账户认证</th>
							<th style="color:#000;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:12px;">邮箱认证</th>
							<th style="color:#000;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:12px;">累计投资额</th>
							<th style="color:#000;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:12px;">投资奖励</th>
						</tr>
					</thead>
					<tbody align="center">
					<#list pager.result as us>
						<tr height="50">
							<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${us.username}</td>
							<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${us.createDate?string("yyyy-MM-dd")}<br />${us.createDate?string("HH:mm:ss")}</td>
							<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
							<#if us.realStatus ==1>
								<span style="background:#be9964;border-radius:10px;color:#fff;display:inline-block;width:20px;height:20px;line-height:20px;text-align:center;">√</span>
							<#else>
                              <span style="background:#f6f6f6;border-radius:10px;color:#646464;display:inline-block;width:20px;height:20px;line-height:20px;text-align:center;">×</span>
                            </#if>
                            </td>
							<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
	                            <#if us.emailStatus ==1>
									<span style="background:#be9964;border-radius:10px;color:#fff;display:inline-block;width:20px;height:20px;line-height:20px;text-align:center;">√</span>
								<#else>
	                              <span style="background:#f6f6f6;border-radius:10px;color:#646464;display:inline-block;width:20px;height:20px;line-height:20px;text-align:center;">×</span>
	                            </#if>
	                        </td>
							<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(us.bdSumMoney?string.currency)!'0.00'}</td>
							<td style="color:#be9964;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(us.uadSumMoney?string.currency)!'0.00'}</td>
						</tr>
						</#list>
					</tbody>
				</table>                 
             </div><!--neirong_table -->
            <@pageFlip pager=pager>
				<#include "/content/common/pager.ftl">
			</@pageFlip>
         </form>
       </div>
   </div>
     <div style=" clear:both; padding-bottom:60px;"></div>
    <div style=" clear:both"></div>
    <div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
   
   
</div><!--user_content end-->
<div style=" clear:both; padding-bottom:50px;"></div>
</div><!--user_middle end-->
<div style=" clear:both"></div>
<#include "/content/common/footer.ftl">

<script type="text/javascript">
$().ready( function() {
		$("#menuUser").addClass("kaqu_bg");
		$("#menuUser_ul").addClass("user_content_top1");
		$("#menuUser_SpreadList").addClass("user_dlk");
		 $("#tjyj").addClass("nsg nsg_a1");
});
</script>

</body>
</html>
