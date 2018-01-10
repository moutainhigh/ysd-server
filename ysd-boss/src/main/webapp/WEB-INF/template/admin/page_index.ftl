<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理中心首页</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="index">
	<div class="bar">
		${Application["qmd.setting.name"]}
	</div>
	<div class="body">
		<div class="bodyLeft">
			<table class="listTable">
				<tr>
					<th colspan="2">
						软件信息
					</th>
				</tr>
				<tr>
					<td width="110">
						系统名称: 
					</td>
					<td>
						${Application["qmd.setting.name"]}
					</td>
				</tr>
				<#--
				<tr>
					<td>
						系统版本: 
					</td>
					<td>
						${setting.systemVersion}
					</td>
				</tr> -->
				<tr>
					<td>
						官方网站: 
					</td>
					<td>
						<a href="${Application["qmd.setting.url"]}" target = "_blank">${Application["qmd.setting.url"]}</a>
					</td>
				</tr>
			</table>
			<div class="blank"></div>
			<table class="listTable">
				<tr>
					<th colspan="2">
						待办事项
					</th>
				</tr>
				<tr>
					<td width="150">
						个人实名认证待审核: 
					</td>
					<td>
						${realName0Count} <a href="user!realnameList.action">[个人实名认证待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td width="110">
						企业资料认证待审核: 
					</td>
					<td>
						${realName1Count} <a href="user!businessList.action">[企业资料认证待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td width="110">
						个人借款人资格待审核: 
					</td>
					<td>
						${borrower0Count} <a href="user!grInfoList.action">[个人借款人资格待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td width="110">
						企业借款人资格待审核: 
					</td>
					<td>
						${borrower1Count} <a href="user!qyInfoList.action">[企业借款人资格待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td>
						发标待审核: 
					</td>
					<td>
						${tenderCount} <a href="borrow!tenderList.action">[发标待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td>
						满标待审核: 
					</td>
					<td>
						${fullScaleCount} <a href="borrow!fullList.action">[满标待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td>
						线下充值待审核: 
					</td>
					<td>
						${rechargeCount} <a href="user_account_recharge!verifyList.action">[线下充值待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td>
						费用扣除待审核: 
					</td>
					<td>
						${deductCount} <a href="rewards!deductList.action">[费用扣除待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td>
						奖励待审核: 
					</td>
					<td>
						${rewardCount} <a href="rewards!rewardList.action">[奖励待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td>
						资金转入待审核: 
					</td>
					<td>
						${moneyIntoCount} <a href="rewards!moneyIntoList.action">[资金转入待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td>
						投资人提现待审核: 
					</td>
					<td>
						${tzWithdrawCount} <a href="account_cash!list.action?accountCash.status=0&accountCash.user.typeId=0&accountCash.user.username=">[投资人提现待审核列表]</a>
					</td>
				</tr>
				<tr>
					<td>
						借款人提现待审核: 
					</td>
					<td>
						${jkWithdrawCount} <a href="account_cash!list.action?accountCash.status=0&accountCash.user.typeId=1&accountCash.user.username=">[借款人提现待审核列表]</a>
					</td>
				</tr>
				
			</table>
			<table class="listTable">
				<tr>
					<th colspan="2">
						统计信息
					</th>
				</tr>
				<tr>
					<td>
						会员总数: 
					</td>
					<td>
						${userTotalCount}
					</td>
				</tr>
				<tr>
					<td>
						文章总数: 
					</td>
					<td>
						${articleTotalCount}
					</td>
				</tr>
			</table>
			
		</div>
		
		<div class="bodyRight">
			<table class="listTable">
				<tr>
					<th colspan="2">
						收入统计
					</th>
				</tr>
				
				<tr>
					<td width="110">
						线上充值总额:
					</td>
					<td>
						${rechargeOnLineSuccessFee?string.currency}
					</td>
				</tr>
				<tr>
					<td width="110">
						线下充值总额:
					</td>
					<td>
						${rechargeOffLineSuccessFee?string.currency}
					</td>
				</tr>
				<tr>
					<td width="110">
						充值手续费:
					</td>
					<td>
						${rechargeableCommissionFee?string.currency}
					</td>
				</tr>
				<tr>
					<td width="110">
						利息管理费用:
					</td>
					<td>
						${interestManagementFee?string.currency}
					</td>
				</tr>
				<#-->
				<tr>
					<td width="110">
						实名认证费用:
					</td>
					<td>
						${realNameFee?string.currency}
					</td>
				</tr>
				<tr>
					<td width="110">
						VIP会员费:
					</td>
					<td>
						${vipMemberFee?string.currency}
					</td>
				</tr>
				<-->
				
				
				
				<tr>
					<td width="110">
						成功借出总额:
					</td>
					<td>
						${loanAccountTotalFee?string.currency}
					</td>
				</tr>
				<tr>
					<td width="110">
						已还款总额:
					</td>
					<td>
						${repaymentAccountTotalFee?string.currency}
					</td>
				</tr>
				<tr>
					<td width="110">
						未还款总额:
					</td>
					<td>
						${noRepaymentAccountTotalFee?string.currency}
					</td>
				</tr>
				<tr>
					<td width="110">
						逾期总额:
					</td>
					<td>
						${overdueTotalFee?string.currency}
					</td>
				</tr>
				<tr>
					<td width="110">
						逾期已还款总额:
					</td>
					<td>
						${overdueYesTotalFee?string.currency}
					</td>
				</tr>
				<tr>
					<td width="110">
						逾期未还款总额:
					</td>
					<td>
						${overdueNoTotalFee?string.currency}
					</td>
				</tr>	
				<tr>
					<td>
						用户可用资金总额:
					</td>
					<td>
						${userAccountAbleMoneyAll?string.currency}
					</td>
				</tr>
				
				<tr>
					<td>
						投资可用资金总额:
					</td>
					<td>
						${userAccountAbleMoneyAllInvestor?string.currency}
					</td>
				</tr>
				<tr>
					<td>
						续投资金总额:
					</td>
					<td>
						${userAccountContinueMoneyInvestor?string.currency}
					</td>
				</tr>
				
			</table>
		</div>	
		
		<div class="bodyRight">
			<table class="listTable">
				<tr>
					<th colspan="2">
						系统信息
					</th>
				</tr>
				<tr>
					<td width="110">
						Java版本: 
					</td>
					<td>
						${javaVersion}
					</td>
				</tr>
				<tr>
					<td>
						操作系统名称: 
					</td>
					<td>
						${osName}
					</td>
				</tr>
				<tr>
					<td>
						操作系统构架: 
					</td>
					<td>
						${osArch}
					</td>
				</tr>
				<tr>
					<td>
						操作系统版本: 
					</td>
					<td>
						${osVersion}
					</td>
				</tr>
				<tr>
					<td>
						Server信息: 
					</td>
					<td>
						${serverInfo}
					</td>
				</tr>
				<tr>
					<td>
						Servlet版本: 
					</td>
					<td>
						${servletVersion}
					</td>
				</tr>
			</table>
			<div class="blank"></div>
			
		</div>
		<!--<p class="copyright">Copyright © 2005-2011 shopxx.net All Rights Reserved.</p>-->
	</div>
</body>
</html>