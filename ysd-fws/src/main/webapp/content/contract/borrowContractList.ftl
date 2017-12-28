<!DOCTYPE html>
<html>
 <head>
	<title>${Application ["qmd.setting.name"]}-合同列表</title>
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
			<li>合同列表</li>
			
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/borrow/userBorrowMgmt.do" >
        	<span class="block">发布时间：</span>
            <span class="block">
            	<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${minDate}" class="searchText">
               <span class="block">&nbsp;&nbsp;到&nbsp;&nbsp;</span> 
				 <input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${maxDate}" class="searchText">  
				  <span class="block">&nbsp;&nbsp;</span> 
				  <span class="block">&nbsp;&nbsp;</span> 
				  <input type="button" onclick="gotoPage(1);" value = "搜索"/>
            </span> 
                              
       
	
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                
               <#-- <td width="19"><input type="checkbox"></td>-->
                <td>标题</td>
				<td>类型</td>
				<td>金额</td>
				<td>利率</td>
				<td>期限</td>
				<td>发布时间</td>
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
				<tr >
					<#--<td><input type="checkbox"></td>-->
					<td class="${flag}"><a href="https://www.yueshanggroup.cn/borrow/detail.do?bId=${borrow.id}" target="_blank" title="${borrow.name}">${substring(borrow.name, 16, "")}</a></td>
					<td class="${flag}"><#if borrow.type==0>秒标<#elseif borrow.type==14>天标<#elseif borrow.type==2>流转标
						<#elseif borrow.type==3>信用标<#elseif borrow.type==15>月标</#if>
<#--						${borrow.showBorrowType} -->
					</td>
				<td class="${flag}">￥${(borrow.account)}</td>
				<td class="${flag}"><#if borrow.type==4>
						${borrow.apr}%/年
					<#else>
						${borrow.apr}‰/天 
					</#if>
				</td>
				<td class="${flag}">${borrow.timeLimit}<#if borrow.type==4>月<#else>天</#if></td>
				<td class="${flag}">
					<#if borrow.createDate >${borrow.createDate?string("yyyy-MM-dd HH:mm:ss")}
					<#else>${borrow.createDate}
					</#if></td>
				<td class="${flag}">
					<@listing_childname sign="borrow_type" key_value="${borrow.status}"; type>
						${type}
					</@listing_childname></td>
				<td class="${flag}">
					<a href = "${base}/contractBorrow/borrowAgreement.do?borrow.id=${borrow.id}">查看合同</a>
								
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
	$(".sssj").attr("id","sssj");
	$("#li_a_jkgl").addClass("li_a_select");
	
});
</script>
</html>
