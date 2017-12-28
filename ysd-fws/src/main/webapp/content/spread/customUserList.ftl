
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-客户管理-客户列表</title>
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
            <li><a href="#">客户列表</a></li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
<form id="listForm" method="post" action="${base}/spread/customUserList.do">
    <div class="search shadowBread">		
        	<span class="block"> 推广渠道或人员：</span>
            <span class="block">
        			<select id = "kq_select_member_id" name = "user.spreadMemberId">
								<option value = "">请选择</option>
								<#list spreadMemberList as sm>
									<option value = "${sm.id}" <#if (user.spreadMemberId)! == sm.id>selected</#if>>
										${sm.fullName}（<#if sm.type == 0>渠道<#if sm.statusCode ==0>｜无效</#if><#else>人员<#if sm.statusCode ==0>｜离职</#if></#if>）
									</option>
								</#list>
							</select>
						用户名：<input type = "text" id = "input_username" name = "user.username" value = "${(user.username)!}" class = "kaqu_w100">
						真实姓名：<input type = "text" id = "input_realname"  name = "user.realName" value = "${(user.realName)!}" class = "kaqu_w100">&nbsp;&nbsp;&nbsp;
					<input type="button" id = "searchButton" class = "kaqu_w120" value = "搜索"/>
                   
            </span>    
        <div class="clear"></div>
	</div>
    
    <div class="mainWrap">       
        <!--table start!-->
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
          <thead>
            <tr>
                <td>用户名（卡号）</td>
        		<td>真实姓名</td>
        		<td>手机号码</td>
        		<td>邮箱</td>
        		<td>实名状态</td>
        		<td>注册时间</td>
        		<#--<td>推广号</td>-->
        		<td>操作</td>
            </tr>
          </thead>
          <tbody>
          <#list pager.result as custom>
              <tr>
                <td>${custom.username}（${custom.clubCardNo}）</td>
		  		<td>${custom.realName}<#if custom.sex ==''><#elseif custom.sex ==1>（先生）<#elseif custom.sex ==1>（女士）</#if></td>
		  		<td>${custom.phone}</td>
		  		<td>${custom.email}</td>
		  		<td><#if custom.realStatus==1>已认证<#elseif custom.realStatus==2>待审核<#else>未认证</#if></td>
		  		<td>${custom.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
		  		<td><a href="${base}/spread/customUserDetail.do?id=${custom.id}<#if (user.spreadMemberId)!>&user.spreadMemberId=${(custom.spreadMemberId)!}</#if>"><#if custom.realStatus==2>审核<#else>查看</#if></a></td>
			</tr> 
           </#list>         
          </tbody>
          <tfoot>
              <tr>
                <td colspan="9">
					<#if pager.totalCount==0>
						<div class="nodata">客户列表记录为空</div>
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
			$("#li_a_khgl").addClass("li_a_select");
		});
	</script>
</body>
</html>