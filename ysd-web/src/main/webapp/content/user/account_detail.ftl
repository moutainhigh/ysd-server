<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-资金记录</title>
    <link rel="stylesheet" href="../css/common.css" />
    <link rel="stylesheet" href="../css/fund_record.css" />
</head>
<body id="bg">
    <!-- 头部 -->
   <#include "/content/common/header.ftl">
   
   

    <!--内容区域-->
    <div id="main">
        <!--左边导航栏-->
       <#include "/content/common/user_center_left.ftl">
        
        <!--右边主内容区-->
      <div class="content fr">
      <div class="fund_record">
         <ul  class="nav">
           <li  onclick="javascript:window.location.href='/account/detail.do?type=detail'" <#if recodeType == 'all'> class="tzlist_user"</#if> >全部</li>
           <li  onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_borrow_tender'" <#if recodeType?exists && recodeType=='account_borrow_tender'> class="tzlist_user"</#if> >投资</li>
           <li  onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_borrow_hk'" <#if recodeType?exists && recodeType=='account_borrow_hk'> class="tzlist_user"</#if> >回款</li>
           <li  onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_recharge'" <#if recodeType?exists && recodeType=='account_recharge'> class="tzlist_user"</#if> >充值</li>
           <li  onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_borrow_cash'" <#if recodeType?exists && recodeType=='account_borrow_cash'> class="tzlist_user"</#if> >提现</li>
           <li  onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_link'" <#if recodeType?exists && recodeType=='account_link'> class="tzlist_user"</#if> >奖励</li>
         </ul>
         <div >
           <div >
           
           
          <#if recodeType == 'all'>
        		  <form id="listForm" method="post" action="${base}/account/detail.do?type=detail">  
          </#if>
      	  <#if recodeType?exists && recodeType=='account_borrow_tender'> 
      	  		  <form id="listForm" method="post" action="${base}/account/detail.do?type=detail&recodeType=account_borrow_tender"> 
      	  </#if>
      	  <#if recodeType?exists && recodeType=='account_borrow_hk'> 
      	  		  <form id="listForm" method="post" action="${base}/account/detail.do?type=detail&recodeType=account_borrow_hk"> 
      	  </#if> 	  
      	  <#if recodeType?exists && recodeType=='account_recharge'> 
      	         <form id="listForm" method="post" action="${base}/account/detail.do?type=detail&recodeType=account_recharge">   
      	  </#if>
       	  <#if recodeType?exists && recodeType=='account_borrow_cash'> 
       	         <form id="listForm" method="post" action="${base}/account/detail.do?type=detail&recodeType=account_borrow_cash">
       	  </#if>
       	  <#if recodeType?exists && recodeType=='account_link'> 
       		   <form id="listForm" method="post" action="${base}/account/detail.do?type=detail&recodeType=account_link">
       	  </#if>
        
    
            
             <table  class="all_detail">
                 <tr >
                   <th >时间</th>
                   <th >类型</th>
                   <th >金额</th>
                   <th >可用金额</th>
                   <th >描述</th>
                 </tr>
                 	<#if pager.result?size==0><tr height="72"><td colspan='5'>暂无记录</td></tr></#if>
					<#list pager.result as accountDetail>
	                  <tr >
	                    <td >${(accountDetail.createDate?string("yyyy-MM-dd HH:mm"))!}</td>
	                    <td >${(accountDetail.typeName)!}</td>
	                    <#if accountDetail.acctype=0>
	                    	<td >-${accountDetail.money?string.currency}</td>
	                    <#else>
	                    	<td >+${accountDetail.money?string.currency}</td>
	                    </#if>
	                    <td >${accountDetail.useMoney?string.currency}</td>
	                    <#if accountDetail.type=='interest_repayment'||accountDetail.type=='invest_repayment'>
	                    	<td >${(accountDetail.remark)?replace('<[^>]*>','','ri')}</td>
	                    <#else>
	                    	<td >${(accountDetail.remark)!}</td>
	                    </#if>
	                  </tr>
                   </#list>
               
             </table>
            		 <@pagination pager=pager>
						<#include "/content/borrow/pager.ftl">
					</@pagination> 
			</form>
           </div>
         </div>
      </div>
    </div>
    
  </div>
           
           
           
        <div class="clear"></div>
    </div>

    <!--底部-->
    
	<#include "/content/common/footer-center.ftl">
    
    <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="../js/my_assets.js"></script>
    <script src="${base}/js/common.js"></script>
    <script>
    	function getQueryString(name) { 
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
			var r = window.location.search.substr(1).match(reg); 
			if (r != null) return unescape(r[2]); return null; 
		} 
		$(function(){
			$("#header_wdzh").addClass("current");
			$(".center_left_zjjl").addClass("current");
			$("#header_gywm").find('a').css('border',0);
		
		var type=getQueryString('recodeType');
		//alert(type);
		if(type=='account_borrow_tender'){
			$(".fund_record .nav").find('li').eq(1).addClass('current');
		}else if(type=='account_borrow_hk'){
			$(".fund_record .nav").find('li').eq(2).addClass('current');
		}else if(type=='account_recharge'){
			$(".fund_record .nav").find('li').eq(3).addClass('current');
		}else if(type=='account_borrow_cash'){
			$(".fund_record .nav").find('li').eq(4).addClass('current');
		}else if(type=='account_link'){
			$(".fund_record .nav").find('li').eq(5).addClass('current');
		}else{
			$(".fund_record .nav").find('li').eq(0).addClass('current');
		}
		
		})
    </script>
</body>
</html>
