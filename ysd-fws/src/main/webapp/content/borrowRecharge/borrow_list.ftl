<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-还款充值</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
	
<script type="text/javascript">

</script>
	
</head>
<body class="body_bg">

	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_account.ftl">

<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/borrowRecharge/borrowList.do">还款充值</a></li>
            <li>还款充值</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/borrowRecharge/borrowList.do">
        	<span class="block">项目：</span>
            <span class="block">
            	<input type="text" name="keywords" value="${keywords}" class="searchText"/>&nbsp;
            </span>    
            <span class="block">最近还款日：</span>           
            <span class="block">
           		<input type="text" id = "minDate" name="minDate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'maxDate\')}'})" value="${(minDate)}" class="searchText" >&nbsp;
            </span><span class="block">到&nbsp;</span> 
            <span class="block">
            	<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'minDate\')}'})" value="${(maxDate)}" class="searchText" > &nbsp;
            </span>
        
        <input type="button" onclick="gotoPage(1);" class = "kaqu_w120" value = "搜索"/>
		<#--<a id="btnadd" class="l-btn l-btn-plain fl" href="javascript:void(0)"><span class="l-btn-tool"><span class="l-btn-text pic-add" style="padding-left:20px;">添加</span></span></a>		
		<a id="btnedit" class="l-btn l-btn-plain fl" href="javascript:void(0)"><span class="l-btn-tool"><span class="l-btn-text pic-edit" style="padding-left:20px;">编辑</span></span></a>		
		<a id="btndel" class="l-btn l-btn-plain fl" href="javascript:void(0)"><span class="l-btn-tool"><span class="l-btn-text pic-delete" style="padding-left:20px;padding-right:8px;">删除</span></span></a>
		-->
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
		<input type="hidden" id="currentTime" value="${currentTime?string("yyyy-MM-dd")}"/>
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <#--<td width="19"><input type="checkbox"></td>-->
                <td>最近还款日</td>
				<td>项目标题</td>
				<td>借款人</td>
				<td>最终还款日</td>
				<td>项目余额</td>
				<td>已还本金</td>
				<td>已还利息</td>
				<td>未还本金</td>
				<td>未还利息</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
              <#list pager.result as item>
					<#--<td><input type="checkbox"></td>-->
					<tr>
						<td>
					   		<#if item.nextRepayDate>
					   			${item.nextRepayDate?string("yyyy-MM-dd")}
					   		</#if>
					   	</td>
					   	<td>${item.name}</td>   
					   	<td>${item.username}</td>   
					   	<td>
					   		<#if item.finalRepayDate>
					   			${item.finalRepayDate?string("yyyy-MM-dd")}
					   		</#if>
					   	</td>
						<td>${item.borrowMoney?string.currency}</td>
					   	<td>${item.payedCapital?string.currency}</td>
					   	<td>${item.payedInterest?string.currency}</td>
					   	<td>${item.repayCapital?string.currency}</td>
					   	<td>${item.repayInterest?string.currency}</td>
						<td>
							<#if item.status==3>
							<a href="${base}/borrowRecharge/borrowInfo.do?id=${item.id}" >还款充值</a>
							<#else>--
							</#if>
						</td>
					</tr>
				</#list>
          </tbody>
          <tfoot>
              <tr>
                <td colspan="10">
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
	<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$("#id_tbody tr:odd").find("td").each(function(i){
    	$(this).addClass("td1");
  	});

	$(".lssj").attr("id","lssj");
	$("#li_a_hkcz").addClass("li_a_select");
});
</script>
</html>
