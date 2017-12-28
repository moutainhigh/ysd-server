<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户 -提现成功页面</title>
<#include "/content/common/meta.ftl">
 </head>

 <body>
<!-- header -->
<#include "/content/common/header.ftl">


<#include "/content/common/user_center_header.ftl">

<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px; margin-top:20px;">
    <!--left-->
	<#include "/content/common/user_center_left.ftl">
	
	<#-- 		右边内容块 开始 				-->
 <div style="width:942px; float:right;">
      <div style="border:1px solid #e6e6e6; background:#fff; ">
         <div class="bank_zh" style="margin-top: 30px; padding-bottom:29px;padding-left: 30px;">
         
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
