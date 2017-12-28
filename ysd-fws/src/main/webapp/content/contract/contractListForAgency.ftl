<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-合同列表</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
</head>

	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">
	
	
<body class="body_bg">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="#">合同管理</a></li>
            <li>合同列表</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
       <form id="listForm" method="post" action="${base}/contractBorrow/contractBorrowList.do">
        	<span class="block">记录时间：</span>
            <span class="block">
            	<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${minDate?default('')}" class="searchText" style = "width :75px">
				<span class="block"> &nbsp;到&nbsp;</span>
				<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${maxDate?default('')}" class="searchText" style = "width :75px">
            </span>                   
       <input type="button" onclick="gotoPage(1);" class = "kaqu_w120" value = "搜索"/>
       <#-- <span class="datagrid-btn-separator"></span>
		<a id="btnadd" class="l-btn l-btn-plain fl" href="javascript:void(0)"><span class="l-btn-tool"><span class="l-btn-text pic-add" style="padding-left:20px;">添加</span></span></a>		
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
              
                <td>项目标题</td>
				<td>项目编号</td>
				<td>项目金额</td>
				<td>期限</td>
				<td>利率</td>
				<td>添加时间</td>
				<td>合同数</td>
				<td>状态</td>
				<td>操作</td>
            </tr>
          </thead>
          		<tbody>
                    	<#list pager.result as item>
	                    	<#assign flag = "">
							<#if item_index %2 != 0>
								<#assign flag = "td1">
							</#if>
							<tr height="39">
						 		<#--<td><input type="checkbox"></td>-->
								<#--<td class = "${flag}">${(item.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>-->
								<#--<td class = "${flag}"><a target="_blank" href="${base}/contract/contractDetail.do?id=${item.id}" >查看详情</a></td>-->
								<td class = "${flag}">${item.name}</td>
								<td class = "${flag}">${item.businessCode}</td>
								<td class = "${flag}">￥${item.account}</td>
								<td class = "${flag}">${item.timeLimit}</td>
								<td class = "${flag}">${item.rateYearLow}%-${item.rateYearHeight}%</td>
								<td class = "${flag}">${(item.verifyTime?string("yyyy-MM-dd"))!}</td>
								<td class = "${flag}">${item.bidNumComplete!0}/${item.bidNumSum}</td>
								<td class = "${flag}">${item.showContractStatus}</td>
								<td class = "${flag}"><a href="${base}/contractBorrow/contractBorrowProcessList.do?typeId=${item.id}" >处理合同</a></td>
								
							</tr>
					</tbody>
					</#list> 
				          <tfoot>
				              <tr>
				                <td colspan="9">
									<#if pager.totalCount==0>
										<div class="nodata">合同记录为空</div>
									</#if>
									<@pageFlip pager=pager>
										<#include "/content/common/pager.ftl">
									</@pageFlip>              
				                
				                </td>
				              </tr>
				          </tfoot>
					</table>
				 <!--table end!--> 
   </div>
 </div>
 <script>
	$().ready( function() { 
		$(".sssj").attr("id","lssj");
		$("#li_a_htlb").addClass("li_a_select");
	});
</script>
 	<#include "/content/common/footer.ftl">
 	</body>
</html>

