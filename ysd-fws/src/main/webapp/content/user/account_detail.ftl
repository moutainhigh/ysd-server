
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-资金管理-资金详情-资金明细</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_account.ftl">

<div class="mainBox">
	<!--面包屑-->
<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">资金管理</a></li>
			<li><a href="#">资金详情</a></li>
            <li><a href="#">资金明细</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <form id="listForm" method="post"  action="${base}/account/recode.do?type=detail">
    <div class="search shadowBread">		
        	<span class="block">记录时间：</span>
            <span class="block">
            	<#--><input class="searchText" name="searchText" type="text" value="查询"/>
                <span class="searchBtn"><a id="zoomBtn" href="#"></a></span>-->
        			<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)!}" style = "width :75px">
					   &nbsp;&nbsp;到&nbsp;&nbsp;
					 <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)!}" style = "width :75px">
					  <select class="kaqu_w110" name="recodeType">
					 	<option value="all">全部</option>
						<option value="borrow_fee" <#if recodeType == 'borrow_fee'>selected</#if> >借款手续费</option>
						<option value="borrow_success" <#if recodeType == 'borrow_success'>selected</#if> >借款入帐</option>
						<option value="repayment_capital" <#if recodeType == 'repayment_capital'>selected</#if> >本金还款</option>
						<option value="repayment_interest" <#if recodeType == 'repayment_interest'>selected</#if> >还款利息</option>
						<option value="recharge_hkcz" <#if recodeType == 'recharge_hkcz'>selected</#if> >还款充值</option>
						<option value="loan_cash_fee_success" <#if recodeType == 'loan_cash_fee_success'>selected</#if> >放款手续费</option>
						<option value="loan_cash_frost" <#if recodeType == 'loan_cash_frost'>selected</#if> >放款资金冻结</option>
						<option value="loan_cash_frost_cancel" <#if recodeType == 'loan_cash_frost_cancel'>selected</#if> >放款资金冻结解除</option>
						<option value="loan_cash_success" <#if recodeType == 'loan_cash_success'>selected</#if> >放款成功</option>
						<option value="tiexi_account_pay" <#if recodeType == 'tiexi_account_pay'>selected</#if> >支付贴息</option>
						
						<#--<@listing_childlist sign="account_type"; listingList>
							<#list listingList as listing>
								<option value="${listing.keyValue}" <#if recodeType == listing.keyValue>selected</#if> >${substring(listing.name, 24, "...")}</a>
							</option>
							</#list>
						</@listing_childlist>-->
					</select>
				<input type="button" id = "searchButton" class = "kaqu_w120" value = "搜索"/>
                   
            </span>    
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>编号</td>
                <td>记录时间</td>
                <td>类型</td>
				<td>操作金额</td>
				<td>账户资产总额</td>
				<td>可用现金金额</td>
				<td>托管账户</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as accountDetail>
              <tr>
				<td>${accountDetail.id}</td>
                <td>${(accountDetail.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
				<td>${accountDetail.typeName}</td>
				<td>${accountDetail.money?string.currency}</td>
				<td>${accountDetail.total?string.currency}</td>
				<td>${accountDetail.useMoney?string.currency}</td>
				<td>${accountDetail.noUseMoney?string.currency}</td>
				<td><a href="javascript:void(0)" class="Funds c2" id="${accountDetail.id}">查看详情</a></td>
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
        <!--table end!--> 
        </from>              
    </div>
    
</div>
	<#include "/content/common/footer.ftl">
	<script>
		$().ready( function() { 
			$(".lssj").attr("id","lssj");
			$("#li_a_zjmx").addClass("li_a_select");
		});
	</script>
</body>
</html>