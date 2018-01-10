<!DOCTYPE html>
<html>
<head>
	<title>${Application ["qmd.setting.name"]}${tempStr}结果信息</title>
	<#include "/content/common/meta.ftl">
	
</head>
<body>
 <!-- header -->
<#include "/content/common/header.html">

<div class="main"><div style=" margin:0 auto; height:160px; width:1000px; background:url(${base}/static/img/kaqu_v.png) no-repeat;"></div></div><!--main end -->
<div class="wrap">
   <div class="content">
   <div style="background:url(${base}/static/img/yjiao.png) no-repeat 0 top; width:1000px; height:20px;"></div>
    <div class="kaqu_name">
      <div class="kaqu_name0">
       <h2 class="kaqu_youxi">系统消息</h2>
       <div class="kaqu_name1">
       <#if userAccountRecharge.rechargeSource==2>
          <div class="kaqu_name2_2">
          <style></style>
             <div class="kaqu_gongxinin">
                <div class="kaqu_chenggong kaqu_cuowu kaqu_cuowu">您通过"${tempStr}" 给客户"${userAccountRecharge.username}"成功充值${(userAccountRecharge.money)!}元!</div>
             </div>
             <div class="kaqu_chakanjilu">
                <a href="${base}/account/rechargeAgencyList.do" class="kaqu_jiluxinxi">查看充值记录</a>
                <a class="kaqu_shuxian">|</a>
                <a href="${base}/payment/rechargeToAgency.do" class="kaqu_jixuchongzhi">继续充值</a>
                <a class="kaqu_shuxian">|</a>
                <a href="${base}/userCenter/index.do" class="kaqu_fanhui">返回我的乐商贷</a>
             </div>
          </div>
         <#elseif userAccountRecharge.rechargeSource==3> <#--还款充值-->
          <div class="kaqu_name2_2">
          <style></style>
             <div class="kaqu_gongxinin">
                <div class="kaqu_chenggong kaqu_cuowu kaqu_cuowu">成功充值${(userAccountRecharge.money)!}元!</div>
             </div>
             <div class="kaqu_chakanjilu">
                <a href="${base}/account/detail.do?type=recharge" class="kaqu_jiluxinxi">查看充值记录</a>
                <a class="kaqu_shuxian">|</a>
                <a href="${base}/payment/rechargeTo.do" class="kaqu_jixuchongzhi">继续充值</a>
                <a class="kaqu_shuxian">|</a>
                <a href="${base}/userCenter/index.do" class="kaqu_fanhui">返回我的乐商贷</a>
             </div>
          </div>
        <#else>
	        <div class="kaqu_name2_2">
	          <style></style>
	             <div class="kaqu_gongxinin">
	                <div class="kaqu_chenggong kaqu_cuowu kaqu_cuowu">您通过"${tempStr}" 成功充值${(userAccountRecharge.money)!}元，请耐心等待客服审核...</div>
	             </div>
	             <div class="kaqu_chakanjilu">
	                <a href="${base}/account/detail.do?type=recharge" class="kaqu_jiluxinxi">查看充值记录</a>
	                <a class="kaqu_shuxian">|</a>
	                <a href="${base}/payment/rechargeTo.do" class="kaqu_jixuchongzhi">继续充值</a>
	                <a class="kaqu_shuxian">|</a>
	                <a href="${base}/userCenter/index.do" class="kaqu_fanhui">返回我的乐商贷</a>
	             </div>
	          </div>
        </#if>  
          
          <div style=" float:left; width:360px; height:420px; background:url(${base}/static/img/suanpan.png) no-repeat 0 0;"></div>
       </div>
      </div>
    <div style=" clear:both"></div>
    </div>
    <div style=" clear:both"></div>
       <div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
      </div><!-- middle end -->
   </div><!-- content end -->
   <div style="clear:both"></div>
</div><!--wrap end-->
<#include "/content/common/footer.html">

</body>
</html>
