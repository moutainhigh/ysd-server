
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-资金管理-资金详情</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_account.ftl">
	<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">资金管理</a></li>
			<li><a href="#">资金详情</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div style="float:left;width:49%;">
			<div class="broderShadow">
				<h3>资金详情</h3>
				<form action="">
				<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr height="37">
					<td class="text_r grayBg" width="20%">可用余额：</td>
					<td class="red" width="30%">${(user.ableMoney!0)?string.currency}</td>
					<td class="text_r grayBg" width="20%">托管账户（投资）：</td>
					<td width="30%">${(user.unableMoney!0)?string.currency}</td>
				  </tr>
				  <tr height="37">
					<td class="text_r grayBg" width="20%">账户总额：</td>
					<td width="30%">${(user.total!0)?string.currency}</td>
					<td class="text_r grayBg" width="20%">提现冻结：</td>
					<td width="30%">${(user.cashMoney!0)?string.currency}</td>
				  </tr>
				  <#--><tr height="37">
					<td class="text_r grayBg" width="20%">理财账户：</td>
					<td width="30%">￥0.00</td>
					<td class="text_r grayBg" width="20%">冻结金额（理财）：</td>
					<td width="30%">￥0.00</td>
				  </tr>
				  <tr height="37">
					<td class="text_r grayBg" width="20%">体验金：</td>
					<td class="org" width="30%">￥3885173.15</td>
					<td class="text_r grayBg" width="20%">红包账户：</td>
					<td width="30%">￥0.00</td>
				  </tr>-->
				</table> 
				</form>       
			</div>
			<div class="broderShadow" style="margin-top:35px;">
				<form action="">
				<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr height="37">
					<td class="text_r grayBg" width="20%">待还总额：</td>
					<td class="red" width="30%">${((user.borrowerCollectionCapital!0)+(user.borrowerCollectionInterest!0))?string.currency}</td>
					<td></td>
					<td></td>
				  </tr>
				  <tr height="37">
					<td class="text_r grayBg" width="20%">待还本金：</td>
					<td width="30%">${(user.borrowerCollectionCapital!0)?string.currency}</td>
					<td class="text_r grayBg" width="20%">待还利息：</td>
					<td width="30%">${(user.borrowerCollectionInterest!0)?string.currency}</td>
				  </tr>
				</table> 
				</form>       
			</div>
      <#--  </div>     
        <div style="float:right;width:49%;">--> <br>
			<div class="broderShadow">
				<h3>资金统计</h3>
				<#--><form action="">
				<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr height="37">
					<td class="text_r grayBg" width="20%">累计净收利息：</td>
					<td class="red" width="30%">￥3885173.15</td>
					<td></td>
					<td></td>
				  </tr>
				  <tr height="37">
					<td class="text_r grayBg" width="20%">累计已收利息：</td>
					<td width="30%">￥3885173.15</td>
					<td class="text_r grayBg" width="20%">累计利息管理费：</td>
					<td width="30%">￥0.00</td>
				  </tr>
				</table> 
				</form>      
			</div>
			<div class="broderShadow" style="margin-top:35px;">--> 
				<form action="">
				<#--
				<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr height="37">
					<td class="text_r grayBg" width="20%">累计充值总额：</td>
					<td class="red" width="30%">
						<@userAccountDetailSum_by_type_count userid="${loginUser.id}" types="recharge_offline,recharge" countType="add"; sum>
							${sum?string.currency}
						</@userAccountDetailSum_by_type_count>
					</td>
					<td></td>
					<td></td>
				  </tr>
				  <tr height="37">
					<td class="text_r grayBg" width="20%">累计线下充值总额：</td>
					<td width="30%">
						<@userAccountDetailSum_by_type userid="${loginUser.id}" type="recharge_offline"; sum>
							${sum?string.currency}
						</@userAccountDetailSum_by_type>
					</td>
					<td class="text_r grayBg" width="20%">累计线下充值奖励：</td>
					<td width="30%">
						<@userAccountDetailSum_by_type userid="${loginUser.id}" type="recharge_offline_reward"; sum>
							${sum?string.currency}
						</@userAccountDetailSum_by_type>
					</td>
				  </tr>
				  <tr height="37">
					<td class="text_r grayBg" width="20%">累计线上充值总额：</td>
					<td width="30%">
						<@userAccountDetailSum_by_type userid="${loginUser.id}" type="recharge"; sum>
							${sum?string.currency}
						</@userAccountDetailSum_by_type>
					</td>
					<td class="text_r grayBg" width="20%">累计线上充值奖励：</td>
					<td width="30%">
						<@userAccountDetailSum_by_type userid="${loginUser.id}" type="fee"; sum>
							${sum?string.currency}
						</@userAccountDetailSum_by_type>
					</td>
				  </tr>
				</table> -->
				</form>       
			</div>
			<div class="broderShadow" style="margin-top:35px;">
				<form action="">
				<#--
				<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr height="37">
					<td class="text_r grayBg" width="20%">累计提现总额：</td>
					<td class="red" width="30%">
						<@userAccountDetailSum_by_type userid="${loginUser.id}" type="recharge_success"; sum>
							${sum?string.currency}
						</@userAccountDetailSum_by_type>
					</td>
					<td class="text_r grayBg" width="20%">累计提现手续费：</td>
					<td width="30%">
						<@userAccountDetailSum_by_type userid="${loginUser.id}" type="recharge_fee"; sum>
							${sum?string.currency}
						</@userAccountDetailSum_by_type>
					</td>
				  </tr>
				</table> -->
				</form>       
			</div>
			<#--<div class="broderShadow" style="margin-top:35px;">
				<form action="">
				<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr height="37">
					<td class="text_r grayBg" width="20%">累计投标奖励总额：</td>
					<td class="red" width="30%">￥3885173.15</td>
					<td class="text_r grayBg" width="20%">累计其它奖励总额：</td>
					<td class="org" width="30%">￥0.00</td>
				  </tr>
				</table> 
				</form>       
			</div>-->
        </div>                
    </div>
    
</div>


	<#include "/content/common/footer.ftl">
	<script>
		$().ready( function() { 
			$(".lssj").attr("id","lssj");
			$("#li_a_zjxq").addClass("li_a_select");
		});
	</script>
</body>
</html>