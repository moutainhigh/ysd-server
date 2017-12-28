<div class="P10">
	<div class="tenderbox" style=" background:url(${base}/static/img/11_bg.png) no-repeat 0 top; background-color:#f2f1f0; padding:20px 0;">
       <div style=" width:480px; margin:0 auto; color:#666; font-size:14px;">
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
                 <tbody>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" width="34%">添加时间：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${(userAccountDetailAgency.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
				</tr>				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">类型：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						<@listing_childname sign="account_type" key_value="${userAccountDetailAgency.type}"; type>
							${type}
						</@listing_childname>
					</td>
				</tr>				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" >操作金额：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${userAccountDetailAgency.money?string.currency}</td>
				</tr>				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" >账户资产总额：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${userAccountDetailAgency.total?string.currency}</td>
				</tr>				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" >可用现金金额：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${userAccountDetailAgency.useMoney?string.currency}</td>
				</tr>
				<#if Application ["qmd.setting.isXutou"]>
					<tr height='34' class = "whiteBg">
						<td class="kaqu_fuwu" >续单金额：</td>
						<td class="kaqu_xinxi kaqu_xinxi_x2">${userAccountDetailAgency.continueTotal?string.currency}</td>
					</tr>
				</#if>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" >托管账户：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${userAccountDetailAgency.noUseMoney?string.currency}</td>
				</tr>	
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" >提现中：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${(userAccountDetailAgency.cashMoney!0)?string.currency}
					</td>
				</tr>	
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" >红包账户：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${(userAccountDetailAgency.awardMoney!0)?string.currency}
					</td>
				</tr>
				<#--<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" >理财冻结资金：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${(userAccountDetailAgency.wmpsMoney!0)?string.currency}
					</td>
				</tr>-->
				
				<#if loginUser.typeId == 1>		
					<tr height='34' class = "whiteBg">
						<td class="kaqu_fuwu" >待付总额：</td>
						<td class="kaqu_xinxi kaqu_xinxi_x2">${((userAccountDetailAgency.borrowerCollectionCapital!0)+(userAccountDetailAgency.borrowerCollectionInterest!0))?string.currency}</td>
					</tr>				
					<tr height='34' class = "whiteBg">
						<td class="kaqu_fuwu" >待付本金：</td>
						<td class="kaqu_xinxi kaqu_xinxi_x2">${(userAccountDetailAgency.borrowerCollectionCapital!0)?string.currency}</td>
					</tr>				
	
					<tr height='34' class = "whiteBg">
						<td class="kaqu_fuwu" >待付利息：</td>
						<td class="kaqu_xinxi kaqu_xinxi_x2">${(userAccountDetailAgency.borrowerCollectionInterest!0)?string.currency}</td>
					</tr>
				</#if>	
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" >备注：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${userAccountDetailAgency.remark!}</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>