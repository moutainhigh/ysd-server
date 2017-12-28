<div class="P10">
	<div class="data-list">
		<table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
			<tbody>
				<tr height="34">
					<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">添加时间：</td>
					<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(userAccountDetail.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
				</tr>				
				<tr height="34">
					<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">类型：</td>
					<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
						<@listing_childname sign="account_type" key_value="${userAccountDetail.type}"; type>
							${type}
						</@listing_childname>
					</td>
				</tr>				
				<tr height="34">
					<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">操作金额：</td>
					<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userAccountDetail.money?string.currency}</td>
				</tr>				
				<tr height="34">
					<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">账户资产总额：</td>
					<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userAccountDetail.total?string.currency}</td>
				</tr>				
				<tr height="34">
					<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">可用金额：</td>
					<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userAccountDetail.useMoney?string.currency}</td>
				</tr>	
				<#if Application ["qmd.setting.isXutou"]>			
					<tr height="34">
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">续单金额：</td>
						<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userAccountDetail.continueTotal?string.currency}</td>
					</tr>	
				</#if>			
				<tr height="34">
					<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">冻结账户：</td>
					<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userAccountDetail.noUseMoney?string.currency}</td>
				</tr>				
				<tr height="34">
					<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">红包金额：</td>
					<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userAccountDetail.awardMoney?string.currency}</td>
				</tr>				
				
				<#if loginUser.typeId == 0>	
				
					<tr height="34">
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">待收总额：</td>
						<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${((userAccountDetail.investorCollectionCapital!0)+(userAccountDetail.investorCollectionInterest!0))?string.currency}</td>
					</tr>				
					<tr height="34">
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">待收本金：</td>
						<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(userAccountDetail.investorCollectionCapital!0)?string.currency}</td>
					</tr>				
	
					<tr height="34">
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">待收利息：</td>
						<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(userAccountDetail.investorCollectionInterest!0)?string.currency}</td>
					</tr>				
				<#elseif loginUser.typeId==1>	
					<tr height="34">
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">待付总额：</td>
						<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${((userAccountDetail.borrowerCollectionCapital!0)+(userAccountDetail.borrowerCollectionInterest!0))?string.currency}</td>
					</tr>				
					<tr height="34">
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">待付本金：</td>
						<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(userAccountDetail.borrowerCollectionCapital!0)?string.currency}</td>
					</tr>				
	
					<tr height="34">
						<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">待付利息：</td>
						<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${(userAccountDetail.borrowerCollectionInterest!0)?string.currency}</td>
					</tr>								
				</#if>	
				<tr height="34">
					<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; background:#fff;text-align:right;">备注：</td>
					<td  style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${userAccountDetail.remark!}</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>