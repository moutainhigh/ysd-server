<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>资金明细列表 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
	$().ready( function() {
	
		$exceButton=$("#exceButton");
		
		$exceButton.click(function(){
			$.dialog({type: "warn", content: "确定导出搜索条件下记录？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			
			function right(){
				location.href='derive_report!exceAccountDetail.action'+
				'?type='+$("#search_sel").val()+
				'&pager.keyword='+$("input[name='pager.keyword']").val()+
				'&startDate='+$("input[name=startDate]").val()+
				'&endDate='+$("input[name='endDate']").val()+
				'&isExact='+$("input[name='isExact']").val();
			}
		})
	});	
</script>
</head>
<body class="list">
	<div class="bar">
		资金明细列表 &nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="account!accountDetailList.action" method="post">
			<div class="listBar">
				<label>用户名:</label>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
					<@checkbox name="isExact" value="${(isExact)!false}" />精确查找
				<label>类&nbsp;&nbsp;&nbsp;&nbsp;型:</label>
				<select id="search_sel" name="type">
					<option value="" <#if type="">selected="selected"</#if>>全部</option>
					
					 <option value="recharge_offline" <#if type == 'recharge_offline'>selected</#if> >线下充值</option> 
					 <option value="recharge_offline_reward" <#if type == 'recharge_offline_reward'>selected</#if> >线下充值奖励</option> 
					<!-- <option value="first_recharge_taste" <#if type == 'first_recharge_taste'>selected</#if> >送体验金</option> -->
					 <option value="tender" <#if type == 'tender'>selected</#if> >投标</option>
					 <option value="invest_false" <#if type == 'invest_false'>selected</#if> >投资失败</option>
					 <option value="invest" <#if type == 'invest'>selected</#if> >投资成功(扣除冻结款)</option> 
					 <option value="award_add" <#if type == 'award_add'>selected</#if> >投资奖励</option> 
					 <option value="offline_deduct" <#if type == 'offline_deduct'>selected</#if> >费用扣除</option> 
					 <option value="invest_repayment" <#if type == 'invest_repayment'>selected</#if> >投资还款(本金)</option> 
					 <option value="interest_repayment" <#if type == 'interest_repayment'>selected</#if> >利息入账</option>
					<!-- <option value="tender_mange" <#if type == 'tender_mange'>selected</#if> >利息管理费</option> -->
					 <option value="recharge" <#if type == 'recharge'>selected</#if> >线上充值</option> 
					 <option value="cash_frost" <#if type == 'cash_frost'>selected</#if> >提现冻结</option> 
					 <option value="cash_cancel" <#if type == 'cash_cancel'>selected</#if> >取消提现</option> 
					 <option value="recharge_success" <#if type == 'recharge_success'>selected</#if> >提现成功</option> 
					 <option value="tui_detail_award" <#if type == 'tui_detail_award'>selected</#if> >好友投资奖励</option> 
					  
					  
					  
				</select><label>&nbsp;&nbsp;&nbsp;</label>
				<label>时&nbsp;&nbsp;&nbsp;间:</label>
					<input type="text" id="startDate" name="startDate" class="formText" value="${(startDate?string("yyyy-MM-dd"))!}" style="width:80px" title="起始时间" onclick="WdatePicker()" />&nbsp;到&nbsp;
				     <input type="text" id="endDate" name="endDate" class="formText" value="${(endDate?string("yyyy-MM-dd"))!}" style="width:80px" title="截止时间" onclick="WdatePicker()" />

				<label>&nbsp;&nbsp;&nbsp;</label>
				
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				<input type="button" id="exceButton" class="formButton" value="导出数据">
				
				
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th>
						<a href="#" class="sort" name="id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="userid" hidefocus>userid</a>
					</th>
					<th>
						<a href="#" class="" name="username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="" name="realName" hidefocus>真实姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="type" hidefocus>操作类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>记录时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="total" hidefocus>总额</a>
					</th>
					<th>
						<a href="#" class="sort" name="money" hidefocus>操作金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="useMoney" hidefocus>可用余额</a>
					</th>
					<th>
						<a href="#" class="sort" name="noUseMoney" hidefocus>冻结金额</a>
					</th>
					<th>
						<a href="#" class="sort" name="investorCollectionCapital" hidefocus>投资人待收本金</a>
					</th>
					<th>
						<a href="#" class="sort" name="investorCollectionInterest" hidefocus>投资人待收利息</a>
					</th>
				</tr>
				<#list pager.result as userAccountDetailDTO>
					<tr>
						<td>
							${userAccountDetailDTO.id}
						</td>
						<td>
							${userAccountDetailDTO.userid}
						</td>
						<td>
							${(userAccountDetailDTO.username)!}
						</td>
						<td>
							${(userAccountDetailDTO.realName)!}
						</td>
						<td>
							${(userAccountDetailDTO.typeValue)!}
						</td>
					
						<td>
							${(userAccountDetailDTO.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
						<td>
							${(userAccountDetailDTO.total?string.currency)!}
						</td>
						<td>
							${(userAccountDetailDTO.money?string.currency)!}
						</td>
						<td>
							${(userAccountDetailDTO.useMoney?string.currency)!}
						</td>
						<td>
							${(userAccountDetailDTO.noUseMoney?string.currency)!}
						</td>
						<td>
							${(userAccountDetailDTO.investorCollectionCapital?string.currency)!}
						</td>
						<td>
							${(userAccountDetailDTO.investorCollectionInterest?string.currency)!}
						</td>
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