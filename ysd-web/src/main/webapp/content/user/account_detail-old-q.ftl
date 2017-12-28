<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户-资金记录</title>
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
      <div style="border:1px solid #e6e6e6; background:#fff; padding-bottom:60px;">
         <ul style="width:940px; height:50px; background:#eee;" class="user_list_qh">
           <li style="width:120px" onclick="javascript:window.location.href='/account/detail.do?type=detail'" <#if recodeType == 'all'> class="tzlist_user"</#if> >全部</li>
           <li style="width:120px" onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_borrow_tender'" <#if recodeType?exists && recodeType=='account_borrow_tender'> class="tzlist_user"</#if> >投资</li>
           <li style="width:120px" onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_borrow_hk'" <#if recodeType?exists && recodeType=='account_borrow_hk'> class="tzlist_user"</#if> >回款</li>
           <li style="width:120px" onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_recharge'" <#if recodeType?exists && recodeType=='account_recharge'> class="tzlist_user"</#if> >充值</li>
           <li style="width:120px" onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_borrow_cash'" <#if recodeType?exists && recodeType=='account_borrow_cash'> class="tzlist_user"</#if> >提现</li>
           <li style="width:120px" onclick="javascript:window.location.href='/account/detail.do?type=detail&recodeType=account_link'" <#if recodeType?exists && recodeType=='account_link'> class="tzlist_user"</#if> >奖励</li>
         </ul>
         <div style="width:100%; clear:both; padding-top:20px;font-size:14px;">
           <div style="display:block; width:900px; margin:0 auto;" class="user_div_qh">
         <form id="listForm" method="post" action="${base}/account/detail.do?type=detail">
             <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <thead bgcolor="#fd7c1a">
                 <tr height="38">
                   <th style="color:#fff;">时间</th>
                   <th style="color:#fff;">类型</th>
                   <th style="color:#fff;">金额</th>
                   <th style="color:#fff;">可用金额</th>
                   <th style="color:#fff;">描述</th>
                 </tr>
               </thead>
               <tbody align="center">
					<#list pager.result as accountDetail>
	                  <tr height="72">
	                    <td style="border-bottom:1px solid #e6e6e6; color:#666;">${(accountDetail.createDate?string("yyyy-MM-dd HH:mm"))!}</td>
	                    <td style="border-bottom:1px solid #e6e6e6; color:#666;">${(accountDetail.typeName)!}</td>
	                    <#if accountDetail.acctype=0>
	                    	<td style="border-bottom:1px solid #e6e6e6; color:#006dc1;">-${accountDetail.money?string.currency}</td>
	                    <#else>
	                    	<td style="border-bottom:1px solid #e6e6e6; color:#fd7c1a;">+${accountDetail.money?string.currency}</td>
	                    </#if>
	                    <td style="border-bottom:1px solid #e6e6e6; color:#666; ">${accountDetail.useMoney?string.currency}</td>
	                    <td style="border-bottom:1px solid #e6e6e6; color:#666;width: 250px;">${(accountDetail.remark)!}</td>
	                  </tr>
                   </#list>
               </tbody>
             </table>
            		 <@pagination pager=pager>
						<#include "/content/common/pager.ftl">
					</@pagination> 
			</form>
           </div><!-- 7 end -->
         </div>
      </div>
    </div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->

<#include "/content/common/footer.ftl">
    <script type="text/javascript">
		$().ready( function() {
			
			$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
			$("#zjjl").addClass("click_current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
</body>
</html>
