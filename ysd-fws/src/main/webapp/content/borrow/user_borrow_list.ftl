<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-还款管理-还款列表</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
</head>

<body class="body_bg">

	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">

<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/borrow/userBorrowNoDone.do">项目还款</a></li>
            <li>还款列表</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/borrow/userBorrowNoDone.do">
        	<span class="block">
	    		<select class="kaqu_w100" name="searchBy">
	               <option value="name" <#if searchBy?exists && searchBy == "name"> selected="selected"</#if> >项目标题</option>
	               <option value="borrowerRealName" <#if searchBy?exists && searchBy == "borrowerRealName"> selected="selected"</#if> >借款人</option>
				</select>
			</span>
			<span class="block" style="margin-left:10px;">
	    		&nbsp;&nbsp;<input type="text" name="keywords" value="${keywords}" class="searchText" />&nbsp;
			</span>
            <span class="block">最近还款日：</span>           
            <span class="block">
           		<input type="text" id = "minDate" name="minDate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'maxDate\')}'})" value="${(minDate)}" class="searchText" >&nbsp;
            </span><span class="block">到&nbsp;</span> 
            <span class="block">
            	<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'minDate\')}'})" value="${(maxDate)}" class="searchText" > &nbsp;
            </span>
        
        <input type="button" onclick="gotoPage(1);" class = "kaqu_w120" value = "搜索"/>
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>最近还款日</td>
				<td>项目标题</td>
		        <td>借款人</td>
		        <td>最终还款日</td>
		        <td>项目余额</td>
<!--		        <td>已还本金</td>
		        <td>已还利息</td>
		        <td>未还本金</td>
		        <td>未还利息</td>-->
		         <td>本期应还本金</td>
		        <td>本期应还利息</td>
		        <td>操作</td>	
            </tr>
          </thead>
          <tbody>
              <#list pager.result as borrow>
				<#assign flag = "">
				<#if borrow_index %2 != 0>
					<#assign flag = "td1">
				</#if>
					<tr height="39">
						<td class="${flag}"><#if borrow.nextRepayDate?exists>${borrow.nextRepayDate?string("yyyy-MM-dd")}</#if></td>
						<td class="${flag}"><a href="${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${borrow.id}" target="_blank" title="${borrow.name}">${substring(borrow.name, 30, "")}</a></td>                                            
						<td class="${flag}">${borrow.realName}</td>
						<td class="${flag}"><#if borrow.finalRepayDate?exists>${borrow.finalRepayDate?string("yyyy-MM-dd")}</#if></td>
						<td class="${flag}">￥${borrow.borrowMoney!'0'}</td>
						<!--
						<td class="${flag}">￥${borrow.payedCapital}</td>
						<td class="${flag}">￥${borrow.payedInterest}</td>
						<td class="${flag}">￥${borrow.repayCapital}</td>
						<td class="${flag}">￥${borrow.repayInterest}</td>-->
						<td class="${flag}">￥${borrow.benRepayCapital}</td>
						<td class="${flag}">￥${borrow.benRepayInterest}</td>
						<td class="${flag}"><a href="${base}/borrow/borrowRepayList.do?borrow.id=${borrow.id}">【还款】</a></td>
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
	$(".sssj").attr("id","sssj");
	$("#li_a_whk").addClass("li_a_select");
});
</script>
</html>
