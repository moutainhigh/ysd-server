<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户-银行账户</title>
	<#include "/content/common/meta.ftl">
</head>
<body class="body_bg">
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">

<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/userCenter/toBankInput.do">银行账户</a></li>
            <li>银行账户列表</li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/agency_pt/bankList.do">       
       
		<a id="btnadd" class="l-btn l-btn-plain fl" href="${base}/agency_pt/toBankInput.do"><span class="l-btn-tool"><span class="l-btn-text pic-add" style="padding-left:20px;">添加银行账户</span></span></a>		
		
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                
               <#-- <td width="19"><input type="checkbox"></td>-->
                <td>借款人用户名</td>
				<td>银行名称</td>
				<td>银行卡号</td>
				<td>开户名</td>
				<td>支行名称</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
              
             <#list pager.result as accountBank>
				<#assign flag = "">
				<#if accountBank_index %2 != 0>
					<#assign flag = "td1">
				</#if>
				  <tr height="35">
				  		<td class="${flag}">${accountBank.username}
				  		</td>
				  		<#--<td><input type="checkbox"></td>-->
					   <td class="${flag}">
					   		<#--><@listing_childname sign="account_bank" key_value="${accountBank.bankId}"; accountBankName>
								${accountBankName}
							</@listing_childname>-->
							${accountBank.name}
					   </td>
					   <td class="${flag}">****${accountBank.account?substring(4,accountBank.account?length-4)} ****</td>
					   <td class="${flag}">${accountBank.realName}</td>
					   <td class="${flag}">${accountBank.branch}</td>
					   <td class="${flag}"> <a href="javascript:void(0);" class="kaqu_shanchu deleteBank" bankid="${accountBank.id}">删除</a>
					   </td>
					</tr>
			</#list>     
          </tbody>
          <tfoot>
               <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">记录为空</div>
					</#if>
					<@pageFlip pager=pager>
						<#include "/content/common/pager.ftl">
					</@pageFlip>              
                
                </td>
              </tr>
          </tfoot>
        </table>
         </form>
        <!--table end!-->               
    </div>
    
</div>

</body>
<script type="text/javascript" src="${base}/js/common/bankInput.js"></script>
    <script type="text/javascript">
		$().ready( function() {
	
			$deleteBank = $(".deleteBank");
			$deleteBank.click(function(){
				$this=$(this);
				if (confirm("确认删除此条记录吗？")) {
					$.ajax({
							url: "${base}/agency_pt/ajaxBankDelete.do",
							data: {"accountBank.id":$this.attr("bankid")},
							type: "POST",
							dataType: "json",
							cache: false,
							success: function(data) {
								alert(data.message);
								if(data.status=="success"){
									$this.parent().parent().remove();
								}
							}
					});
				}
			
			});
		});
	</script>
<script type="text/javascript">
$(function(){
	$(".sssj").attr("id","lssj");
	$("#li_a_yhzhlb").addClass("li_a_select");
	
});
</script>
</html>
