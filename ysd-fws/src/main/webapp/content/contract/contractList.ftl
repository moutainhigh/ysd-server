<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<title>${Application ["qmd.setting.name"]}-我的合同</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
</head>
<body>
<!-- header -->


<#include "/content/common/user_center_header.ftl">

<div class="user_middle">
<div class="user_content">
    <div class="user_content_bottom kaqu_w00">
          <div class="kaqu_x0">
            <div class="kaqu_x1">
                <span  class="kaqu_x2"> 
                  <span class="kaqu_x3">              
		                   <a href="${base}/index.do">我就爱车首页</a>&nbsp;
		                   <a>></a>&nbsp;
		                   <a href="${base}/userCenter/index.do">账户中心</a>&nbsp;
		                   <a>></a>&nbsp;
		                   <a href="${base}/contract/contractList.do">我的合同</a>
                  </span>
                </span>
            </div><!--kaqu_x1 end-->
           </div><!--kaqu_x0 end--> 
       <div class="kaqu_w50">
          <ul class="fl kaqu_w60">
              <li class="kaqu_w70">我的合同</li>
              <li class="list_fs kaqu_w80"><a href = "javascript:void(0);">合同列表</a></li>
         </ul>
			<form id="listForm" method="post" action="${base}/contract/contractList.do">
				<div style="" class="kaqu_w90">
					记录时间：<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${minDate?default('')}" class="kaqu_w100" style = "width :75px">
							   &nbsp;&nbsp;到&nbsp;&nbsp;
							 <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${maxDate?default('')}" class="kaqu_w100" style = "width :75px">
					<input type="button" onclick="gotoPage(1);" class = "kaqu_w120" value = "搜索"/>
				</div>
				<table width="100%" cellpadding="0" cellspacing="0" class="tb">
		            <thead bgcolor="#e6e4e3">
	             		<tr height="40">
                    		<th width="140">添加时间</th>
                			<th width="300">合同编号</th>
							<th>合同名称</th>
							<#--<th>状态</th>-->
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
                    	<#list pager.result as item>
	                    	<#assign flag = "">
							<#if item_index %2 != 0>
								<#assign flag = "td1">
							</#if>
							<tr height="39">
								<td class = "${flag}">${(item.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
								<td class = "${flag}">${item.contractCode}</td>
								<td class = "${flag}">${item.contractTitle}</td>
								<#--<td class = "${flag}"><#if item.statusSign =1>已签<#else>--</#if></td>-->
								<#if item.statusCode == 3>
									<td class = "${flag}"><a target="_blank" href="${base}/contractBorrow/showPdf.do?contractBorrowId=${item.id}" >查看详情</a></td>
								<#else>
									<td class = "${flag}"><a target="_blank" href="${base}/contract/contractDetail.do?id=${item.id}" >查看详情</a></td>
								</#if>
							</tr>
					</tbody>
						</#list>                        
					</table>
						<#if pager.totalCount==0>
							<div class="nodata">合同记录为空</div>
						</#if>
                        <@pageFlip pager=pager>
						<#include "/content/common/pager.ftl">
						</@pageFlip>
			</form>
<div style=" clear:both"></div>
</div><!--user_content_bottom end-->
<div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
</div><!--user_content end-->
<div style=" clear:both; padding-bottom:50px;"></div>
</div><!--user_middle end-->
<div style=" clear:both"></div>	
<#include "/content/common/footer.html">
<script type="text/javascript">
$(function(){
	$("#menuInvestor").addClass("kaqu_bg");
	$("#menuInvestor_ul").addClass("user_content_top1");
	$("#menuInvestor_contract").addClass("user_dlk");
});
</script>

</body>
</html>
