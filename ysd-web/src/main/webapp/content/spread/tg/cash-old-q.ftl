<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户-添加银行卡</title>
	<#include "/content/common/meta.ftl">
<style>
  .status{overflow: hidden;margin:20px 0 0 12px;font-size: 14px;}
  .status li{float:left;color:#666666;}
  .status li a{color:#666666;padding:2px 4px;margin:0 9px;}
  .status li a.cur{color:#fff;background-color:#fd7c1a;border-radius:3px;display: inline-block;}
</style>
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
      <div style="border:1px solid #e6e6e6; background:#fff; margin-top:16px;padding-bottom:60px;">
         <ul style="width:940px; height:50px; background:#eee;" class="user_list_qh">
           <li onclick="javascript:window.location.href='/award/hongbao.do'">红包</li>
           <li class="tzlist_user">奖励</li>
   	     </ul>
         <ul class="status">
           <li><a  <#if sign ==''> class="cur"</#if>  href="${base}/award/cash.do">全部</a>|</li>
           <li><a <#if sign?exists && sign==1> class="cur"</#if>  href = "${base}/award/cash.do?sign=1">好友投资</a>|</li>
           <li><a <#if sign?exists && sign==-1> class="cur"</#if>  href = "${base}/award/cash.do?sign=-1">投资奖励</a></li>
         </ul>
         <div style="width:100%; clear:both; padding-top:20px;">
           <div style="width:900px; margin:0 auto;" class="">
          	 <form id="listForm" method="post" action="${base}/award/cash.do<#if sign?exists>?sign=${sign}</#if>">
             <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <thead bgcolor="#fd7c1a">
                 <tr height="38">
                   <th style="color:#fff; font-size:14px;">时间</th>
                   <th style="color:#fff; font-size:14px;">类型</th>
                   <th style="color:#fff; font-size:14px;">奖励金额</th>
                   <th style="color:#fff; font-size:14px;">名称</th>
                 </tr>
               </thead>
               <tbody align="center">
                <#list pager.result as accountDetail>
                  <tr height="72">
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;width:200px;"> ${(accountDetail.createDate?string("yyyy-MM-dd HH:mm"))!}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">
                    	<@listing_childname sign="account_link" key_value="${accountDetail.type}"; accountBankName>
							${accountBankName}
						</@listing_childname>
                    </td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;">${accountDetail.money?string.currency}</td>
                    <td style="border-bottom:1px solid #e6e6e6; color:#666; font-size:14px;width:250px;">${accountDetail.remark}</td>
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
    <script type="text/javascript">
		$().ready( function() {
			
			$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
			$("#hbjjl").addClass("click_current");<#-- user_center_left.ftl 选中样式 -->
		});

	</script>
</body>
</html>
