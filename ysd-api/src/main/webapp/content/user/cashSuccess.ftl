<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>${Application ["qmd.setting.name"]}-我的账户 -提现成功页面</title>
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
            <a  style="color:#646464;font-family:'宋体';">提现成功</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a href="${base}/userCenter/getMoney.do" class="hr">我要提现</a>
            <a href="${base}/userCenter/cashList.do" class="hr">提现记录</a>
            <a style="border:1px solid #a6a6a6; height:28px; float:left;margin:7px 2px 0px 2px;"></a>
            <a class="hr hre" href="">提现成功</a>          
          </div>
          <div style="clear:both;color:#646464;font-family:'宋体';margin-top:80px;">
				<dl style="background:url(${base}/static/img/c13.png) 0 0 no-repeat; padding-top:3px;">
					<dt style="font-size:16px;color:#646464;font-family:'宋体';font-weight:bold;">提现成功</dt>
					<dd style="font-size:12px; font-weight:normal;color:#646464;font-family:'宋体';">您的提现申请已提交成功</dd>
				</dl>
				<div style="color:#f74405;font-family:'宋体'; border:0 none; text-align:left; margin-top:5px; margin-bottom:10px; ">
					如果您的银行卡信息设置不正确导致提现失败，资金将会自动退回到您的账户
				</div>
          </div>  
          <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
				<thead bgcolor="#efebdf" align="center">
					<tr height="35">
						<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">提现银行</th>
						<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">银行卡号</th>
						<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">提现金额</th>
						<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">到账金额</th>
						<th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">到账时间</th>
					</tr>
				</thead>
				<tbody align="center">
					<tr height="50">
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountCash.name}</td>
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountCash.account}</td>
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountCash.total}</td>
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountCash.credited}</td>
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">预计${lastTime}点前</td>
					</tr>
				</tbody>
			</table>  
          <div style=" text-align:center; padding:10px 0;">您可能需要：
             <a style="color:#f74405;font-family:'宋体';" href="${base}/userCenter/cashList.do">[查询提现记录]</a> 
             <a style="color:#f74405;font-family:'宋体';" href="${base}/userCenter/getMoney.do">[继续提现]</a> 
             <a style="color:#f74405;font-family:'宋体';" href="${base}/userCenter/index.do">[返回我的账户]</a>
          </div>         
       </div>
	<#-- 		右边内容块 结束				-->
	
	</div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->

 <!--footer-->
<#include "/content/common/footer.ftl">
<script type="text/javascript">
$(function(){
	$("#my_deposit").addClass("nsg nsg_a1");
});
</script>

</body>
</html>
