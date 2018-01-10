<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-展期管理-申请展期</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
	<style>
		.kaqu_w70_zqzr {
		    background: url("${base}/static/img/right.png") no-repeat scroll 125px 8px rgba(0, 0, 0, 0);
		    color: #363636;
		    float: left;
		    font-size: 20px;
		    width: 115px;
		}
	</style>
</head>
<body class="body_bg">

	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">

<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/defer/apply.do">展期管理</a></li>
            <li>申请展期</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/defer/apply.do">
        	<span class="block">编号：</span>
            <span class="block">
            	<input type="text" name="keywords" value="${(keywords)!}" class="searchText"/>&nbsp;
            </span>    
            <span class="block">最后还款日：</span>           
            <span class="block">
           		<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)!}" class="searchText" >&nbsp;
            </span><span class="block">到&nbsp;</span> 
            <span class="block">
            	<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)!}" class="searchText" > &nbsp;
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
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <#--<td width="19"><input type="checkbox"></td>-->
                <td>申请项目标题</td>
				<td>编号</td>
				<td>金额</td>
				<td>期限</td>
				<td>利率</td>
				<td>发布时间</td>
				<td>最后还款日</td>
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
							<td class="${flag}"><a href = "${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${borrow.id}" title = "" target="_blank">${substring(borrow.name, 26, "")}</a></td>
							<td class="${flag}">${borrow.businessCode}</td>
							<td class="${flag}">${(borrow.accountYes)!}元</td>
							<td class="${flag}">${borrow.timeLimit}天</td>
							<td class="${flag}">${borrow.rateYearLow}%-${borrow.rateYearHeight}%</td>
							<td class="${flag}">${(borrow.verifyTime?string("yyyy-MM-dd"))!}</td>
							<td class="${flag}">${(borrow.finalRepayDate?string("yyyy-MM-dd"))!}</td>
							<td class="${flag}">
								<@defer_apply bid="${borrow.id}" time_limit="${borrow.timeLimit}" repayment_time="${(borrow.finalRepayDate?datetime)!}"; deferInfo>
									<#if deferInfo.status ==1>
										<a href = "${base}/defer/toApply.do?bid=${borrow.id}" title = "${deferInfo.title}">【申请】</a>
									<#elseif deferInfo.status ==0>
										<a href = "javascript:void(0);" title = "${deferInfo.title}">【不可申请】</a>
									<#else>
										【${deferInfo.title}】
									</#if>
							   </@defer_apply>
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
	<#include "/content/common/footer.ftl">
</body>

<script type="text/javascript">
$(function(){
	$().ready( function() { 
		$(".sssj").attr("id","lssj");
		$("#li_a_sqzq").addClass("li_a_select");
	});
});

</script>
</html>
