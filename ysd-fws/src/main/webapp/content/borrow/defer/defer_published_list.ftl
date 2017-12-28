<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-我的账户-展期管理-展期已发布</title>
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
            <li>展期已发布</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/defer/published.do" >
        	<span class="block">展期起始时间：</span>
            <span class="block">
            	<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)!}" class="searchText" style = "width :80px">
            	<span class="block">&nbsp;到&nbsp;</span>
					 	<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)!}" class="searchText" style = "width :80px">
				  <span class="block">&nbsp;&nbsp;编号：</span>
				  <input type="text" class="searchText" name = "keywords" value="${(keywords)!}"/>&nbsp;&nbsp;&nbsp;
				<input type="button" onclick="gotoPage(1);" value = "搜索"/>
			</span>                   
        
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
                <td>展期项目标题</td>
				<td>编号</td>
				<td>金额</td>
				<td>期限</td>
				<td>利率</td>
				<td>展期起始时间</td>
				<td>展期金额</td>
				<td>状态</td>
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
						<#--<td><input type="checkbox"></td>-->
						<td class="${flag}"><a href = "${Application ["qmd.setting.memberUrl"]}/borrow/detail.do?bId=${borrow.id}" title = "" target="_blank">${substring(borrow.name, 26, "")}</a></td>
						<td class="${flag}">${borrow.businessCode}</td>
						<td class="${flag}">${(borrow.account)!}元</td>
						<td class="${flag}">${borrow.timeLimit}天</td>
						<td class="${flag}">${borrow.rateYearLow}%-${borrow.rateYearHeight}%</td>
						<td class="${flag}">${borrow.deferTime?string("yyyy-MM-dd")}</td>
						<td class="${flag}"><@defer_money bid="${borrow.oldBorrowId}" userid="${borrow.userId}";money>${money?string.currency}</@defer_money></td>
						<td class="${flag}">${borrow.dstatus}</td>
						<td class="${flag}">
							<a href = "javascript:void(0);" class = "zq_view" borrowId="${borrow.oldBorrowId}">【同意展期会员】</a>
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
          </form>
        </table>
        <!--table end!-->               
    </div>
    
</div>
	<#include "/content/common/footer.ftl">

</body>
<script type="text/javascript">
$(function(){
		$().ready( function() { 
		$(".sssj").attr("id","lssj");
		$("#li_a_zqyfb").addClass("li_a_select");
	});
	
	//查看展期会员信息
	$(".zq_view").click(function(){
		var testTime = new Date().getTime();
		var $this = $(this);
		var $borrowId = $this.attr('borrowId');
    	KP.options.drag = true;
		KP.show("同意展期会员", 500, 560);
		$(KP.options.content).load(qmd.base+"/defer/poputDeferUsers.do?bid="+$borrowId+"&testTime="+testTime);
	});
		
});

</script>
</html>
