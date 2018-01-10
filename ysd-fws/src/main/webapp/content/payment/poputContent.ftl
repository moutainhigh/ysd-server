<div class="P20">
	<div class="tenderbox" style=" background:url(${base}/static/img/11_bg.png) no-repeat 0 top; background-color:#f2f1f0; padding:40px 0;">
       <div style=" width:480px; margin:0 auto; color:#363636; text-align:center; font-size:24px;">
          充值完成前请不要关闭此窗口!
       </div>
	</div><!--tenderbox end-->
   <div style=" text-align:center; margin-top:15px;">
      <input type="button" value="已完成付款" style=" width:96px; height:35px; line-height:33px; color:#fff; font-size:14px; background:url(${base}/static/img/btns.png) -480px 0; border:0 none; margin-right:5px; " onclick="window.location.href='<#if flag == ''>${base}/account/rechargeAgencyList.do<#else>${base}/account/detail.do?type=recharge</#if>'"/>
      <input type="button" value="付款遇到问题" style=" width:96px; height:35px; line-height:33px; color:#000; font-size:14px; background:url(${base}/static/img/btns.png) -586px 0; border:0 none; " onclick="window.location.href='<#if flag == ''>${base}/payment/rechargeToAgency.do<#else>${base}/payment/rechargeTo.do</#if>'"/>
   </div>
</div><!--P20 end-->