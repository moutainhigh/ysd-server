<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看标页面 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">

function check_form(){
	 var frm = document.forms['validateForm'];
	 var verify_remark = frm.elements['verify_remark'].value;
	 var errorMsg = '';
	  if (verify_remark.length == 0 ) {
		errorMsg += '备注必须填写' + '\n';
	  }
	  
	  if (errorMsg.length > 0){
		alert(errorMsg); return false;
	  } else{  
		return true;
	  }
}

</script>
</head>
<body class="input">
	<div class="body">
		<form id="validateForm" action="borrow!update.action" onsubmit="return check_form();"  method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="borrow.user.id" value="${borrow.user.id}" />
			<input type="hidden" name="borrow.name" value="${borrow.name}" />
			<div class="bar">标的详情</div>
			<table class="inputTable">
				<tr>
					<th>用户名: </th>
					<td>${borrow.user.username}</td>
				</tr>
				<tr>
					<th>借款标题: </th>
					<td>${borrow.name}</td>
				</tr>
				<tr>
					<th>担保抵押: </th>
					<td>
						<#if borrow.isVouch==0>无担保，无抵押
						<#elseif borrow.isVouch==1>有担保，无抵押
						<#elseif borrow.isVouch==2>无担保，有抵押
						<#elseif borrow.isVouch==3>有担保，有抵押
						</#if>
					</td>
				</tr>
				<tr>
					<th>借款期限: </th>
					<td>${borrow.timeLimit}<#if borrow.type==4>月<#else>天</#if></td>
				</tr>
				<tr>
					<th>借贷总金额: </th>
					<td>${borrow.account}</td>
				</tr>
				<#if borrow.type==2>
					<tr>
						<th>借贷总份数: </th>
						<td>${borrow.wanderPieceSize} 份</td>
					</tr>
					<tr>
						<th>单份金额: </th>
						<td>${borrow.wanderPieceMoney} 元/份</td>
					</tr>
					<tr>
						<th>天利率:</th>
						<td>${borrow.apr}‰</td>
					</tr>
					<tr>
					<th>最低投标金额: </th>
					<td>${borrow.lowestAccount}份</td>
					</tr>
					<tr>
						<th>最多投标总额: </th>
						<td>
							<#if !borrow.mostAccount&&borrow.mostAccount!=''>
								${borrow.mostAccount}份
							<#else>
								没有限制
							</#if>
						</td>
					</tr>
				<#else>
					<tr>
						<#if borrow.type==4>
							<th>年利率:</th>
							<td>${borrow.apr}%</td>
						<#else>
							<th>天利率:</th>
							<td>${borrow.apr}‰</td>
						</#if>
					</tr>
					<tr>
						<th>最低投标金额: </th>
						<td>${borrow.lowestAccount}</td>
					</tr>
					<tr>
						<th>最多投标总额: </th>
						<td>
							${borrow.mostAccount!'没有限制'}
						</td>
					</tr>
					<tr>
						<th>有效时间: </th>
						<td>${borrow.validTime}天</td>
					</tr>
				</#if>
					<tr>
						<th>保证金: </th>
						<td>${borrow.depositMoney}元</td>
					</tr>
					<tr>
						<th>手续费: </th>
						<td><#if borrow.feeType==''||borrow.feeType==0>${borrow.partAccount}%<#else>${borrow.feeMoney}元</#if></td>
					</tr>
					
				<tr>
					<th>
						投资先锋奖励金额:
					</th>
					<td>
						${borrow.tzxf!'0'}&nbsp;元
					</td>
				<tr>
				
				<tr>
					<th>
						投资土豪奖励金额:
					</th>
					<td>
						${borrow.tzth!'0'}&nbsp;元
					</td>
				<tr>
				<tr>
					<th>
						收官大哥奖励金额:
					</th>
					<td>
						${borrow.sgdg!'0'}&nbsp;元
					</td>
				<tr>
					
			</table>
			
			<#if (borrow.status)!=0 >
			<div class="bar">投资记录</div>
			<table class="inputTable">
				<tr>
					<td>ID</td>
					<td>用户名称</td>
					<td>投资金额</td>
					<td>有效金额</td>
					<td>状态</td>
					<td>投标时间</td>
				</tr>
				<#list borrow.borrowDetailSet as borrowDetail>
	
				<tr>
					<td>${borrowDetail.id}</td>
					<td>${borrowDetail.user.username}</td>
					<td>${borrowDetail.money}</td>
					<td>${borrowDetail.account}</td>
					<td>
						<#if (borrowDetail.money)== (borrowDetail.account)>全部通过
						<#else>部分通过
						</#if>
					</td>
					<td>${borrowDetail.createTime}</td>
				</tr>
				</#list>
			</table>
			<!--
			<div class="pager">
				<#include "/WEB-INF/template/admin/pager.ftl" />
			</div>
			</#if>
			-->
			<#if borrow.type==1>
			<div class="bar">还款计划</div>
			<table class="inputTable tabContent" width="80%" align="right">
				<tr><td>&nbsp;</td><td>期数</td><td>天数</td><td>还款本金</td><td>还款利息</td></tr>
				<#list retBorrow.paymentDetail as plan>
				<tr>
					<td>&nbsp;</td>
					<td>${plan_index+1}</td>
					<td>${plan.dateNum}</td>
					<td>${plan.benjinM}</td>
					<td>${plan.lixiM}</td>
				</tr>
				</#list>
			</table>
			</#if>
			
			<#if borrow.type==4>
			<div class="bar">还款计划</div>
			<table class="inputTable tabContent" width="80%" align="right">
				<tr><td>&nbsp;</td><td>期数</td><td>日期</td><td>还款本金</td><td>还款利息</td></tr>
				<#if borrow.status==3>
				<#list borrowRepaymentDetailList as borrowRepaymentDetail>
					<tr>
						<td>&nbsp;</td>
						<td>${borrowRepaymentDetail.orderNum}</td>
						<td>${borrowRepaymentDetail.repaymentTime?string("yyyy-MM-dd")}</td>
						<td>${borrowRepaymentDetail.capital}</td>
						<td>${borrowRepaymentDetail.interest}</td>
					</tr>
					</#list>
				<#else>
				<#list retBorrow.paymentDetail as plan>
				<tr>
					<td>&nbsp;</td>
					<td>${plan_index+1}</td>
					<td>${plan.dateNum}</td>
					<td>${plan.benjinM}</td>
					<td>${plan.lixiM}</td>
				</tr>
				</#list>
				</#if>
			</table>
			</#if>
			<!--流转标 增加赎回计划-->
			<#if borrow.type==2>
			<div class="bar">赎回计划</div>
			<table class="inputTable tabContent" width="80%" align="right">
				<tr><td>&nbsp;</td><td>期数</td><td>回购日期</td></tr>
				<#list wanderRepayPlanDetail.wanderRepayPlanEach as plan>
				<tr>
					<td>&nbsp;</td>
					<td>${plan.issue}</td>
					<td>${plan.repayDateStr}</td>
				</tr>
				</#list>
			</table>
			</#if>
			
		 	<div id="selRoom" class="tab-con ">
		 	<div class="bar">借款详情：</div>
                <div class="showtab1">${borrow.content}</div>
				<div class="buttonArea">
					<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
				</div>
			</div>
		</form>
	</div>
</body>
</html>