<#--    <div style="background:url(${base}/static/img/e0.png) 0 0 no-repeat; width:955px; height:73px; margin-top:20px; float:left;">
       <div style="float:left; margin:20px 0px 0px 20px;font-size:14px;font-family:'宋体';color:#fff;">
         <span style="margin-right:14px;">欢迎您 ，${loginUser.username}</span>
         <span>可用余额：${(selfaccountHeader.ableMoney?string.currency)!}</span>
       </div>
       <div style="float:right;margin:16px 14px 0px 0px;">
         <span style="font-family:'宋体';color:#fff;margin-right:4px;">上次登录时间：${loginUser.upTime?string("yyyy-MM-dd HH:mm:ss")}</span>
         <a href="${base}/payment/rechargeTo.do" style="background:url(${base}/static/img/e1.png) 0 0 no-repeat; width:44px; height:22px; line-height:22px; text-align:center; color:#fff;font-family:'宋体';margin-right:4px; display:inline-block;font-weight:700;">充值</a>
         <a href="${base}/userCenter/getMoney.do" style="background:url(${base}/static/img/e2.png) 0 0 no-repeat; width:44px; height:22px; line-height:22px; text-align:center; color:#be9964;font-family:'宋体'; display:inline-block;font-weight:700;">提现</a>
       </div>
</div>
-->
<div style="width:100%; background:url(/img/a12.png) 0 0 no-repeat; background-size:cover; height:176px; margin-top:20px;">
  <div style="width:1200px; height:176px; margin:0 auto;">
    <div style=" float:left;">
      <img id="center_header_img_id" src="<#if loginUser.litpic>${Application["qmd.img.baseUrl"]}/web${loginUser.litpic}<#else>/img/tou.png</#if>" width="90" height="90" style="border-radius:50%; float:left; margin-top:43px;" />
      <ul style="float:left; color:#ff7f00; font-size:16px; margin-top:52px; margin-left:20px;">
        <li style="border-bottom:1px solid #f7f6f6; margin-bottom:9px; padding-bottom:9px;">您好，${loginUser.username}</li>
        <li>健康、财富、喜乐全部给您</li>
      </ul>
    </div>
    <div style="float:right; width:376px;">
      <dl style="float:left;margin-top:48px;color:#ff7f00; font-size:16px; text-align:center; margin-left:30px;">
        <dt style="margin-bottom:4px;"><a href="${base}/member/toPassword.do"><div style="background:url(/img/a11.png) 7px 0 no-repeat; width:64px; height:50px;"></div></a></dt>
        <dd>密码管理</dd>
      </dl>
      <dl style="float:left;margin-top:48px;color:#ff7f00; font-size:16px; text-align:center; margin-left:30px;">
        <dt style="margin-bottom:4px;"><a href="${base}/member/toLitpic.do"><div style="background:url(/img/a11.png) -93px 0 no-repeat; width:64px; height:50px;"></div></a></dt>
        <dd>设置头像</dd>
      </dl>
    <#--
      <dl style="float:left;margin-top:48px;color:#ff7f00; font-size:16px; text-align:center; margin-left:30px;">
        <dt style="margin-bottom:4px;"><a href="${base}/member/toRealName.do"><div style="background:url(/img/a11.png) -193px 0 no-repeat; width:64px; height:50px;"></div></a></dt>
        <dd>实名认证</dd>
      </dl>
 -->
      <dl style="float:left;margin-top:48px;color:#ff7f00; font-size:16px; text-align:center; margin-left:30px;">
        <dt style="margin-bottom:4px;"><a href="${base}/award/toGetLink.do"><div style="background:url(/img/a11.png) -293px 0 no-repeat; width:64px; height:50px;"></div></a></dt>
        <dd>邀请好友</dd>
  </dl>
    </div>
  </div>
</div>