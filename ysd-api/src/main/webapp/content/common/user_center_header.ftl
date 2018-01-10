    <div style="background:url(${base}/static/img/e0.png) 0 0 no-repeat; width:955px; height:73px; margin-top:20px; float:left;">
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