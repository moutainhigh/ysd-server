
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-客户管理-客户数据统计-统计概括</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/datePicker/WdatePicker.js"></script>
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
            <li><a href="#">统计概括</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <form id="listForm" method="post" action="${base}/spread/tjProfile.do">
    <div class="search shadowBread">		
        	<span class="block">推广渠道或人员：</span>
            <span class="block">
            	<select id = "kq_select_member_id" name = "user.spreadMemberId">
					<option value = "">所有渠道和人员：</option>
					<#list spreadMemberList as sm>
						<option value = "${sm.id}" <#if (user.spreadMemberId)! == sm.id>selected</#if>>
								${sm.fullName}（<#if sm.type == 0>渠道<#if sm.statusCode ==0>｜无效</#if><#else>人员<#if sm.statusCode ==0>｜离职</#if></#if>）
						</option>
					</#list>
				</select>
				投资时间：<input type="text" id = "minDate" name="minDate" onclick="WdatePicker()" value="${(minDate)!}" class="kaqu_w100" style = "width :80px">
				   &nbsp;&nbsp;到&nbsp;&nbsp;
				<input type="text" id = "maxDate" name="maxDate" onclick="WdatePicker()" value="${(maxDate)!}" class="kaqu_w100" style = "width :80px">
				<input type="button" id = "searchButton" class = "kaqu_w120" value = "搜索"/>&nbsp;&nbsp;&nbsp;
                <#--排序 -->
                 <select class="text_sketch" id="kq_select_order_id" name = "order" style="">
		          <option value="1" <#if order =='' || order == 1> selected </#if>>按投资金额降序排列</option>
		          <option value="2" <#if order! == 2> selected </#if> >按投资金额升序排列</option>
		          <option value="3" <#if order! == 3> selected </#if> >按会员数量降序排列</option>
		          <option value="4" <#if order! == 4> selected </#if> >按会员数量升序排列</option>
		        </select>
            </span>    
        <div class="clear"></div>
	</div>
    <div class="mainWrap">
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                <td>所属会员数量：${userNums}</td>
                <td>所属会员投资总额：${investTotal?string.currency}</td>
                <td>佣金总额：${feeTotal?string.currency}</td>
              </tr>
            </table>      
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>名称</td>
        		<td>推广类型</td>
        		<td>所属会员数量</td>
        		<td>投资总额</td>
        		<td>所获佣金</td>
        		<td>操作</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as sm>
              <tr>
               		<td>${sm.fullName}</td>
			  		<td><#if sm.type == 0>渠道<#if sm.statusCode ==0>（无效）</#if><#elseif sm.type ==1>人员<#if sm.statusCode ==0>（离职）</#if></#if></td>
			  		<td>${sm.usernums}</td>
			  		<td>${(sm.investTotal?string.currency)!'￥0.00'}</td>
			  		<td>${(sm.feeTotal?string.currency)!'￥0.00'}</td>
			  		<td><a href="${base}/spread/tjDetailSelect.do?user.spreadMemberId=${sm.id}&minDate=${(minDate)!}&maxDate=${(maxDate)!}">查看明细</a></td>
				</tr> 
           </#list>         
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">统计数据为空</div>
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
			$("#li_a_tjgk").addClass("li_a_select");
		
		$("#kq_select_order_id").change(function(){
			$("#listForm").submit();
		});
	});
</script>
</body>
</html>