<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-充值记录</title>
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
    <!--right-->
   <div style="width:942px; float:right;">
       <ul style=" width:940px; height:120px;border:1px solid #e6e6e6; background:#fff;margin-bottom:16px;overflow:hidden;">
         <li style="float:left;width:250px;height:68px;color:#666;margin:20px 0;padding-left:20px;border-right:1px solid #e6e6e6;font-size:16px;">
           <p style="margin-bottom:26px;">累计成功充值：
           	<span style="color:#fd7c1a;">
           		<@userAccountDetailSum_by_type_count userid="${loginUser.id}" types="recharge_offline,recharge" countType="add"; sum>
					${sum?string.currency}
				</@userAccountDetailSum_by_type_count>
           </span></p>
           <p>累计成功提现：<span style="color:#006dc1;">
           	<@userAccountDetailSum_by_type userid="${loginUser.id}" type="recharge_success"; sum>
				${sum?string.currency}
			</@userAccountDetailSum_by_type>
           </span></p>
         </li>
         <li style="float:left;margin:20px 0;padding-left:40px;">
           <div style="float:left;font-size:16px;color:#666;">
             <p style="margin-bottom:16px;width:90px;background:url(img/wen.png) no-repeat 67px 3px;">可用余额</p>
             <p style="color:#fd7c1a;font-weight:bold;">${(user.ableMoney!0)?string.currency}</p>
           </div>
           <div style="float:left;margin:18px 0 0 150px;">
           <#if noPayPsw == 1>
				<label class="alrm"><a href="${base}/member/toPayPassword.do" style="color:#000;">设置交易密码，保护账户安全</a></label>
				<#elseif user.realStatus != 1>
				<label class="alrm"><a href="" style="color:#000;">进行“绑卡认证”，保护账户安全</a></label>
				<#else>
             	<a href="${base}/payment/rechargeTo.do" style="display:inline-block; width:55px; height:26px;  line-height:26px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a; margin-right:16px;">充值</a>
             	<a href="${base}/userCenter/getMoney.do" style="display:inline-block; width:55px; height:26px;  line-height:26px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#7c96e8; ">提现</a>
           </#if> 	
           </div>
         </li>
       </ul>
      <div style="border:1px solid #e6e6e6; background:#fff; margin-top:16px;padding-bottom:60px;">
         <ul style="width:940px; height:50px; background:#eee;" class="user_list_qh">
           <li onclick="javascript:window.location.href='/account/detail.do?type=recharge'">充值记录</li>
           <li class="tzlist_user">提现记录</li>
         </ul>
         <div style="width:100%; clear:both; padding-top:20px;">
           <div style="display:block; width:900px; margin:0 auto;" class="user_div_qh">
          <form id="listForm" method="post" action="detail.do?type=recharge">
             <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <thead bgcolor="#fd7c1a">
                 <tr height="38">
                   <th style="color:#fff; font-size:14px;width:200px;">时间</th>
                   <th style="color:#fff; font-size:14px;">提现金额（元）</th>
                   <th style="color:#fff; font-size:14px;">状态</th>
                 </tr>
               </thead>
               <tbody align="center">
               <#list pager.result as accountCash>
                  <tr height="72">
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;width:200px;">
                    	${(accountCash.createDate?string("yyyy-MM-dd HH:mm"))!}
                    </td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;width:520px;">${accountCash.total?string.currency}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">
                    <#if accountCash.status==1>
                    	提现成功
                    <#else>
                    	处理中
                    </#if>
                    </td>
                  </tr>
              </#list>
               </tbody>
             </table>
				<@pageFlip pager=pager>
					<#include "/content/common/pager.ftl">
				</@pageFlip>
				</form>
           </div>
           
         </div>
      </div>
</div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->     

<#include "/content/common/footer.ftl">
<script>
	$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
	$("#cztx").addClass("click_current");<#-- user_center_left.ftl 选中样式 -->

</script>  
</body>
</html>
