<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>所有帐户记录列表 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
	$().ready( function() {
	
		$exceButton=$("#exceButton");
		$typeId = $("#typeId");
		$username=$("#username");
		
		$exceButton.click(function(){
			$.dialog({type: "warn", content: "确定导出搜索条件下记录？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			$typeId = $("#typeId");
			$amount = $("#amount");
			
			function right(){
				location.href='derive_report!exceAccount.action?user.username='+$username.val()+'&type='+$typeId.val()+'&amount='+$amount.val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
		所有帐户记录列表 &nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="account!list.action" method="post">
			<div class="listBar">
				用户名:<input type="hidden" name="pager.searchBy" value="username">
				<input type="text" id="username" name="pager.keyword" value="${pager.keyword!}" />
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<label>金&nbsp;&nbsp;额:</label>
				<select id="typeId" name="type">
					<option value="" <#if type="">selected="selected"</#if>>全部</option>
					<option value="totalgt" <#if type="totalgt">selected="selected"</#if>>总额大于</option>
					<option value="totallt" <#if type="totallt">selected="selected"</#if>>总额小于</option>
					<option value="canusegt" <#if type="canusegt">selected="selected"</#if>>可用大于</option>
					<option value="canuselt" <#if type="canuselt">selected="selected"</#if>>可用小于</option>
					<option value="nousegt" <#if type="nousegt">selected="selected"</#if>>冻结大于</option>
					<option value="nouselt" <#if type="nouselt">selected="selected"</#if>>冻结小于</option>
				</select>&nbsp;&nbsp;
				<input type="text" id="amount" name="amount" value="${amount!}" style="width: 80px;" />&nbsp;元
				<#--
				<label>会员类型: </label>
				<input type="radio" name="user.memberType" value=""<#if user=='' || user.memberType=='' >checked</#if>/>不限&nbsp;
				<input type="radio" name="user.memberType" value="0"<#if user! && user.memberType==0>checked</#if>/>个人&nbsp;
				<input type="radio" name="user.memberType" value="1"<#if user! && user.memberType==1>checked</#if>/>企业&nbsp;
				
				<label>&nbsp;&nbsp;&nbsp;</label>
				<label>会员属性: </label>
				<input type="radio" name="user.typeId" value=""<#if user=='' || user.typeId==''>checked</#if>/>不限&nbsp;
				<input type="radio" name="user.typeId" value="0"<#if user! && user.typeId==0>checked</#if>/>投资人&nbsp;
				<input type="radio" name="user.typeId" value="1"<#if user! && user.typeId==1>checked</#if>/>借款人&nbsp;
				-->
				<label>&nbsp;&nbsp;&nbsp;</label>
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				<input type="button" id="exceButton" class="formButton" value="导出数据">
				&nbsp;&nbsp;
				
			</div>
			<!--ID	用户名	真实姓名	总余额	可用余额	冻结金额	待收金额-->
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="account.id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="" name="username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="" name="user.realName" hidefocus>真实姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="account.total" hidefocus>总金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="account.ableMoney" hidefocus>可用余额</a>
					</th>
					<th>
						<a href="#" class="sort" name="account.unableMoney" hidefocus>冻结金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="account.awardMoney" hidefocus>红包总额</a>
					</th>
					<th>
						<a href="#" class="sort" name="account.investorCollectionCapital" hidefocus>投资人待收本金</a>
					</th>
					<th>
						<a href="#" class="sort" name="account.investorCollectionInterest" hidefocus>投资人待收利息</a>
					</th>
					<@sec.authorize ifAnyGranted="ROLE_DETAIL_RECORD">
						<th>
							<a href="#"  hidefocus>操作</a>
						</th>
					</@sec.authorize>
				</tr>
				<#list pager.result as user>
					<tr>
						<td>
							${(user.id)!}
						</td>
						<td>
							${(user.username)!}
						</td>
						<td>
							${(user.realName)!}
						</td>
						<td>
							${(user.account.total?string.currency)!}
						</td>
						<td>
							${(user.account.ableMoney?string.currency)!}
						</td>
						<td>
							${(user.account.unableMoney?string.currency)!}
						</td>
						<td>
							${(user.account.awardMoney?string.currency)!}
						</td>
						<td>
							${(user.account.investorCollectionCapital?string.currency)!}
						</td>
						<td>
							${(user.account.investorCollectionInterest?string.currency)!}
						</td>
						<@sec.authorize ifAnyGranted="ROLE_DETAIL_RECORD">
						<td>
							<a href="account!accountDetailList.action?pager.keyword=${user.username}&isExact=true&user.typeId=">查看资金明细</a>
						</td>
						</@sec.authorize>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="pager">
						<#include "/WEB-INF/template/admin/pager.ftl" />
					</div>
				<div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>