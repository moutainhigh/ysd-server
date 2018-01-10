
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-资金管理-还款充值-充值记录</title>
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
			<li><a href="#">还款充值</a></li>
            <li><a href="#">充值记录</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <form id="listForm" method="post" action="${base}/account/detail.do?type=recharge">
    <div class="search shadowBread">		
        	<span class="block">充值时间：</span>
            <span class="block">
            	<#--><input class="searchText" name="searchText" type="text" value="查询"/>
                <span class="searchBtn"><a id="zoomBtn" href="#"></a></span>-->
        			<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)!}" style = "width :75px">
					   &nbsp;&nbsp;到&nbsp;&nbsp;
					 <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)!}" style = "width :75px">
					 <select name = "recodeType" class="kaqu_w110">
			      		<option value="" >所有状态</option>
			      		<option value="2" <#if recodeType == 2>selected</#if> >审核中</option>
			      		<option value="1" <#if recodeType == 1>selected</#if> >成功</option>
			      		<option value="0" <#if recodeType == 0>selected</#if> >失败</option>
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
                <td>充值时间</td>
                <td>支付方式</td>
                <td>充值金额</td>
                <td>充值奖励</td>
                <td>状态</td>
                <td>备注</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as accountRecharge>
              <tr>
                <td>${accountRecharge.id}</td>
                <td>${accountRecharge.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                <td>${accountRecharge.name}</td>
                <td>${accountRecharge.money?string.currency}</td>
                <td>
                	<#if accountRecharge.fee &gt; 0>
						-${accountRecharge.fee?string.currency}
					<#else>
						${accountRecharge.reward?string.currency}
					</#if></td>
                <td><#if accountRecharge.status==1>成功<#elseif accountRecharge.status==2>审核中<#else>失败</#if></td>
                <td>${accountRecharge.remark}</td>
              </tr> 
           </#list>         
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">充值记录为空</div>
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
		$("#li_a_czjl").addClass("li_a_select");
	});
</script>
</body>
</html>