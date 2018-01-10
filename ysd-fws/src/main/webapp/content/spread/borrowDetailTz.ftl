
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-客户管理-客户数据统计-统计明细-投资详情</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_customer.ftl">

<div class="mainBox">
	<!--面包屑-->
<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">客户管理</a></li>
			<li><a href="#">客户数据统计</a></li>
            <li><a href="#">统计明细</a></li>
            <li><a href="#">投资详情</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <form id="listForm" method="post" action="${base}/spread/tjDetail.do">
    <div class="search shadowBread">		
        	<input type="button"  value="返回统计明细" sytle = "width:80px;" onclick="window.location.href='${base}/spread/tjDetailSelect.do?user.id=${(user.id)!}&user.spreadMemberId=${(spreadMember.id)!}&spreadMember.agencyType=${spreadMember.agencyType}&spreadMember.agencyId=${spreadMember.agencyId}&minDate=${minDate}&maxDate=${maxDate}'"/>
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">
			
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>项目编号</td>
        		<td>投资时间</td>
        		<td>项目时长</td>
        		<td>年利率</td>
        		<td>投资金额</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as borrowTender>
              <tr>
               		<td><a href = "${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${borrowTender.borrowId}" target = "_blank">${borrowTender.businessCode}</a></td>
			  		<td>${(borrowTender.repaymentDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
			  		<td>${borrowTender.timeLimit} 天</td>
			  		<td><@aprofyear apr="${borrowTender.investApr}"; apryear>${apryear}</@aprofyear>%</td>
			  		<td>${(borrowTender.loanAmountFee?string.currency)!'￥0.00'}</td>
			</tr> 
           </#list>         
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">投资详情记录为空</div>
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
		$(".tjxx").attr("id","tjxx");
		$("#li_a_tjmx").addClass("li_a_select");
	});
</script>
</body>
</html>