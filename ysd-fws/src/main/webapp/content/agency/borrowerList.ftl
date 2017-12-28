<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-借款人列表</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript">
	$().ready( function() {	
		$deleteBank = $(".deleteBank");
		$deleteBank.click(function(){
			$this=$(this);
			//$.dialog({type: "warn", content: "确认要删除此用户吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			if (!confirm("确认要删除此用户吗？")) {
				return;
			}
			//function right(){
			var d = new Date().getTime();
				$.ajax({
					url: "${base}/agency/ajaxDelectUser.do",
					data: {"user.id":$this.attr("userid"),"d":d},
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						//$.message({type: data.status, content: data.message});
						if(data.status=="success"){
							//$this.parent().parent().remove();
							alert("删除成功！");
							window.location.reload();
						} else {
							alert(data.message);
							return;
						}
					}
				});
			//}
		});
	});
 </script>
</head>
<body class="body_bg">
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_customer.ftl">


<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li> <a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/agency/borrowerList.do">客户管理</a></li>
            <li>借款人列表</li>
		</ul>
	</div><div class="clear"></div>
    <!--搜索-->
    <div class="search shadowBread">		
        <form id="listForm" method="post" action="${base}/agency/borrowerList.do" >
        <span class="block">姓名：</span>
            <span class="block">
            	<input type="text" id = "keyName" name="keyName" value="${(keyName)!}" class="searchText">
               <span class="block">&nbsp;&nbsp;手机号码：&nbsp;&nbsp;</span> 
				 <input type="text" id = "keyPhone" name="keyPhone"  value="${(keyPhone)!}" class="searchText">  
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
                <td>类型</td>
                <td>姓名</td>
				<td>联系电话</td>
				<td>未还总金额</td>
				<td>未还清项目</td>
				<td>已还清项目</td>
				<td>操作</td>
            </tr>
          </thead>
          <tbody>
              <#list pager.result as user>
            	<tr height="39">
            		<td><#if user.memberType ==0>个人<#else>企业</#if></td>
            		<td>${user.username}</td>
            		<td>${(user.phone)!}</td>
            		<td>${user.borrowerCollectionCapital + user.borrowerCollectionInterest}</td>
            		<td>${user.whqxm}</td>
            		<td>${user.yhqxm}</td>
            		<td class="${flag}">
            		<#if user.memberType ==1>
	            		<a href="${base}/agency/lockqyBorrower.do?user.id=${user.id}">【详情】</a>&nbsp;&nbsp;
	            		<a href="${base}/agency/inputqyBorrower.do?user.id=${user.id}" >【编辑】</a>&nbsp;&nbsp;
            		<#else>
	            		<a href="${base}/agency/lockBorrower.do?user.id=${user.id}">【详情】</a>&nbsp;&nbsp;
	            		<a href="${base}/agency/toUpdateBorrower.do?user.id=${user.id}" >【编辑】</a>&nbsp;&nbsp;
	            	</#if>
	            		<a href="javascript:void(1);" class="deleteBank" userid="${user.id}">【删除】</a>
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
        <!--table end!-->      
        </form>         
    </div>
    
</div>


	<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$(".tjxx").attr("id","tjxx");
	$("#li_a_jkrlb").addClass("li_a_select");
});
</script>
</html>
