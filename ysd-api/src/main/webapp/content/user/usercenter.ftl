<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-我的账户</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/highcharts/highcharts.js"></script>
	
<script type="text/javascript">
$(function () {
	
	$("#sp_header_my").addClass("hq");
		
	function ajaxReceiveTaste() {
		var testTime = new Date().getTime();
		$.ajax({
	        type: "get",
	        dataType : "json",
	        url: '/userCenter/ajaxReceiveTaste.do?testTime='+testTime+'&',
	        success: function(data, textStatus){
	        	if(typeof(data.message) == "undefined" || typeof(data.message) == "null" || data.message == null || data.message == ""){
	        		alert("领取失败");
					window.location.reload();
	        	} else if (data.message=="OK") {
	        		alert("领取成功");
	        		window.location.reload();
	        	} else {
		        	alert(data.message);
					window.location.reload();
	        	}
	        },
	        error:function(statusText){
	        	alert(statusText);
	        },
	        exception:function(){
	        	alert(statusText);
	        }
		});
	}
	
	function ajaxTasteAble() {
		var testTime = new Date().getTime();
		$.ajax({
	        type: "get",
	        dataType : "json",
	        url: '/userCenter/ajaxTasteAble.do?testTime='+testTime+'&',
	        success: function(data, textStatus){
	        	if(typeof(data.message) == "undefined" || typeof(data.message) == "null" || data.message == null || data.message == ""){
	        		alert("激活失败");
					window.location.reload();
	        	} else if (data.message=="OK") {
	        		alert("激活成功");
	        		window.location.reload();
	        	} else {
		        	alert(data.message);
					window.location.reload();
	        	}
	        },
	        error:function(statusText){
	        	alert(statusText);
	        },
	        exception:function(){
	        	alert(statusText);
	        }
		});
}
</script>
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
   <div style="color:#646464;font-family:'宋体';font-size:14px;font-weight:700; padding-bottom:10px; margin-top:15px; border-bottom:1px solid #c6c6c6;">最新公告</div>
     <div style=" padding:14px 0px 30px 0px;">
       <@article_list sign='site_notice' count=6; articleList>
		<#list articleList as article>
			<a href="${base}/article/content/${article.id}.htm" style="width:220px; display:inline-block;color:#595757;font-family:'宋体';">${substringWord(article.title, 16, "...")}</a>	
		</#list>
	</@article_list> 
	</div>
      <div style=" width:670px;color:#646464;font-family:'宋体';font-size:14px;font-weight:700; padding-bottom:10px;border-bottom:1px solid #c6c6c6; position:relative;">
        <span>账户概况</span>
         <a href="${base}/account/detail.do?type=statistics" style="position:absolute; right:0px; top:0px;font-size:14px;font-family:'宋体';color:#be9964; font-weight:normal;">[详情]</a>
       </div> 
      <div style="padding:14px 0px 20px 0px;">
            <span style="color:#727171;font-family:'宋体';display:inline-block; width:125px;">用户名：${loginUser.username}</span>
            <span style="background:url(/static/img/k_ic0.png) 0 0 no-repeat;display:inline-block; height:16px; line-height:16px;color:#727171;font-family:'宋体'; padding-left:22px; margin-right:-3px;"><#if loginUser.realStatus==1>已认证<#else>未认证</#if></span>
            <span style="background:url(/static/img/k_ic1.png) 0 0 no-repeat;display:inline-block; height:16px; line-height:16px;color:#727171;font-family:'宋体'; padding-left:20px; "><#if loginUser.phoneStatus==1>已认证<#else>未认证</#if></span>
            <span style="background:url(/static/img/k_ic2.png) 0 0 no-repeat;display:inline-block; height:16px; line-height:16px;color:#727171;font-family:'宋体'; padding-left:22px;"><#if loginUser.emailStatus==1>已认证<#else>未认证</#if></span>
            <span style="display:inline-block; width:170px; height:38px; border:1px solid #c6c6c6;line-height:26px; border-radius:5px; background:#f7f7f8; text-align:center; margin-left:13px;">
              <span><font style="color:#646464;font-family:'宋体';">可用余额</font><font style="color:#be9964; font-size:16px; position:relative;top:2px;">${selfaccount.ableMoney?string.currency}</font></span>
            </span>
            <a href="${base}/payment/rechargeTo.do" style="display:inline-block;background:url(/static/img/e9.png) 0 0 no-repeat;width:47px; height:47px;text-align:center;line-height:58px;font-family:'宋体';color:#be9964;vertical-align:top; margin-left:10px;">充值</a>
            <a href="${base}/userCenter/getMoney.do" style="display:inline-block; background:url(/static/img/e9.png) -61px 0 no-repeat;width:47px;height:47px;text-align:center;line-height:58px;font-family:'宋体';color:#727171;vertical-align:top;margin-left:10px;">提现</a>
      </div>
       <div style=" width:670px;color:#646464;font-family:'宋体';font-size:14px;font-weight:700; padding-bottom:10px;border-bottom:1px solid #c6c6c6; position:relative;">
            <span>账户详情</span>
            <a href="${base}/account/detail.do?type=statistics" style="position:absolute; right:0px; top:0px;font-size:14px;font-family:'宋体';color:#be9964; font-weight:normal;">[详情]</a>
          </div>
          <ul style="padding-bottom:30px;">
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">账户总额：<font style="color:#be9964;font-family:'宋体';">${selfaccount.total?string.currency}</font></span>
               <span>
                 <a href="${base}/account/detail.do?type=detail" style="color:#727171;font-family:'宋体';">资金明细查询</a>
                 <a style=" padding:0px 5px;color:#727171;font-family:'宋体';">|</a>
                 <a href="${base}/account/detail.do?type=statistics" style="color:#727171;font-family:'宋体';">账户资金详情</a>
               </span>
             </li>
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">可用余额：<font style="color:#be9964;font-family:'宋体';">${selfaccount.ableMoney?string.currency}</font></span>
               <span>
                 <a href="${base}/userCenter/getMoney.do" style="color:#be9964;font-family:'宋体';">提现</a>
                 <a style=" padding:0px 5px;color:#727171;font-family:'宋体';">|</a>
                 <a href="${base}/payment/rechargeTo.do" style="color:#be9964;font-family:'宋体';">充值</a>
                 <a style=" padding:0px 5px;color:#727171;font-family:'宋体';">|</a>
                 <a href="${base}/account/detail.do?type=recharge" style="color:#727171;font-family:'宋体';">充值记录查询</a>
                 <a style=" padding:0px 5px;color:#727171;font-family:'宋体';">|</a>
                 <a href="${base}/userCenter/cashList.do" style="color:#727171;font-family:'宋体';">提现记录查询</a>
               </span>
             </li>
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">冻结总额：<font style="color:#be9964;font-family:'宋体';">${selfaccount.unableMoney?string.currency}</font></span>
               <span>
                <#if loginUser.typeId==0>
                 	<a href="${base}/borrow/borrowDetailList.do" style="color:#727171;font-family:'宋体';">投资记录&nbsp;&nbsp;&nbsp;&nbsp;</a>
                 <#elseif loginUser.typeId==1>
                 	<a href="${base}/borrow/userBorrowMgmt.do" style="color:#727171;font-family:'宋体';">借款管理&nbsp;&nbsp;&nbsp;&nbsp;</a>
                 </#if>
                 <a style=" padding:0px 5px;color:#727171;font-family:'宋体';">|</a>
                 <a href="${base}/userCenter/toBankInput.do" style="color:#727171;font-family:'宋体';">银行账户设置</a>
               </span>
             </li>
            <!--
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">可用红包：<font style="color:#be9964;font-family:'宋体';">￥0</font></span>
               <span>
                 <a href="" style="color:#727171;font-family:'宋体';">续投设置&nbsp;&nbsp;&nbsp;&nbsp;</a>
                 <a style=" padding:0px 5px;color:#727171;font-family:'宋体';">|</a>
                 <a href="" style="color:#727171;font-family:'宋体';">续投转出</a>
               </span>
             </li>-->
          </ul>
          <div style=" width:670px;color:#646464;font-family:'宋体';font-size:14px;font-weight:700; padding-bottom:10px;border-bottom:1px solid #c6c6c6;">待收待还详情</span></div>
          <#if loginUser.typeId==0>
          <ul style="padding-bottom:35px;">
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">待收总额：<font style="color:#be9964;font-family:'宋体';">${(selfaccount.investorCollectionCapital+selfaccount.investorCollectionInterest)?string.currency}</font></span>
               <span style="color:#727171;font-family:'宋体';display:inline-block; ">待收利息：<font style="color:#be9964;font-family:'宋体';">${selfaccount.investorCollectionInterest?string.currency}</font></span>
             </li>
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">最近待收金额：<font style="color:#be9964;font-family:'宋体';">${(selfaccount.investorCollectionCapital+selfaccount.investorCollectionInterest)?string.currency}</font></span>
               <span style="color:#727171;font-family:'宋体';display:inline-block; ">最近待收时间：<#if userRepaymentDetai.repaymentTime??>${userRepaymentDetai.repaymentTime?string("yyyy-MM-dd")}</#if>　　<a href="${base}/borrow/bidBorrowDetailList.do?status=0" style="color:#be9964;font-family:'宋体'; padding-left:6px;">[我要收款]</a></span>
             </li>
          </ul>
         <#elseif loginUser.typeId==1>
          <ul style="padding-bottom:35px;">
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">借款总额：<font style="color:#be9964;font-family:'宋体';">${(selfaccount.borrowerCollectionCapital+selfaccount.borrowerCollectionInterest)?string.currency}</font></span>
               <span style="color:#727171;font-family:'宋体';display:inline-block; ">待还总额：<font style="color:#be9964;font-family:'宋体';">${(selfaccount.borrowerCollectionInterest)?string.currency}</font></span>
             </li>
             <li style=" padding-top:14px;">
               <span style="color:#727171;font-family:'宋体';display:inline-block; width:215px; ">最近待还金额：<font style="color:#be9964;font-family:'宋体';"><#if borrowRepaymentDetail.repaymentAccount??>${borrowRepaymentDetail.repaymentAccount}</#if></font></span>
               <span style="color:#727171;font-family:'宋体';display:inline-block; ">最近待还时间： <#if borrowRepaymentDetail.repaymentTime??>${borrowRepaymentDetail.repaymentTime?string("yyyy-MM-dd")}</#if>　　<a href="${base}/borrow/hkmx.do" style="color:#be9964;font-family:'宋体'; padding-left:6px;">[ 我要还款]</a></span>
             </li>
          </ul>
         </#if>
         
          <div style=" width:670px;color:#646464;font-family:'宋体';font-size:14px;font-weight:700; padding-bottom:10px;border-bottom:1px solid #c6c6c6; position:relative;">
            <span>最新充提</span>
            <a href="${base}/account/detail.do?type=recharge" style="position:absolute; right:0px; top:0px;font-size:14px;font-family:'宋体';color:#be9964; font-weight:normal;">[详情]</a>
          </div>
          <div style="width:645px; padding:10px 0px 20px 0px;">
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
              <thead bgcolor="#eeeeee">
                <tr height="24">
                  <th style="color:#595757;font-family:'宋体';font-weight:700;">流水单号</th>
                  <th style="color:#595757;font-family:'宋体';font-weight:700;">时间</th>
                  <th style="color:#595757;font-family:'宋体';font-weight:700;">充/提</th>
                  <th style="color:#595757;font-family:'宋体';font-weight:700;">金额（元）</th>
                  <th style="color:#595757;font-family:'宋体';font-weight:700;">状态</th>
                </tr>
              </thead>
              <tbody align="center">
                  <#list userAccRechargeList as accountRecharge>
								<#assign flag = "">
								<#if accountRecharge_index %2 != 0>
									<#assign flag = "td1">
								</#if>
		          <tr height="28">
		            <td style="color:#595757;font-family:'宋体';">${accountRecharge.id}</td>
		            <td style="color:#595757;font-family:'宋体';">${accountRecharge.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
		            <td style="color:#595757;font-family:'宋体';">充值</td>
		            <td style="color:#595757;font-family:'宋体';">${accountRecharge.money?string.currency}</td>
		            <td style="color:#595757;font-family:'宋体';"><#if accountRecharge.status==1>成功<#elseif accountRecharge.status==2>审核中<#else>失败</#if></td>
		          </tr>
          		</#list>
              </tbody>
            </table>
          </div>
       <div style=" width:670px;color:#646464;font-family:'宋体';font-size:14px;font-weight:700; padding-bottom:10px;border-bottom:1px solid #c6c6c6; position:relative;">
         <span>最新理财产品</span>
          <a href="${base}/borrow/list.do" style="position:absolute; right:0px; top:0px;font-size:14px;font-family:'宋体';color:#be9964; font-weight:normal;">[详情]</a>
         </div>
        <div style="float:left; width:670px; padding-top:15px;">
        <@borrow_list count=4; nubbleList>
	  <#list nubbleList as borNB>
		 <dl style="float:left; width:98px; margin-right:45px;">
          <dt><img src="<@imageUrlDecode imgurl="${borNB.borImageFirst}"; imgurl>${imgurl}</@imageUrlDecode>" width="98" height="98" /></dt>
           <dd style="text-align:center;"><a href="${base}/borrow/detail.do?bId=${borNB.id}" style="color:#727171;font-family:'宋体';">${substring(borNB.name, 20, "")}</a></dd>
            <dd style="text-align:center;"><a href="" style="color:#f74405;font-family:'宋体';font-weight:700;"><#if borNB.type==12>年<#else>天</#if>利率：${borNB.apr}%</a></dd>
         </dl>
     </#list>
    </@borrow_list>
        
     </div>
        </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->       

<#include "/content/common/footer.ftl">

</body>
<script type="text/javascript">
$(function(){
	$("#menuInvestor").addClass("kaqu_bg");
	$("#menuInvestor_ul").addClass("user_content_top1");
<#-->	$("#menuInvestor").addClass("user_dlk");-->
});
</script>
</html>
