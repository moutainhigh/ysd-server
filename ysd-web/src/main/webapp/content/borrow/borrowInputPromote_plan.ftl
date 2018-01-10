<#list repaymentInfo.repaymentDetailList as d>
	<tr height="30">
		<td  style="text-align:right;padding-right:45px;border-bottom:1px solid #ccc;" >${d.orderNum}</td>
		<td class="kaqu_r8" style="text-align:right;padding-right:45px;border-bottom:1px solid #ccc;" >第${d.repaymentDateInt}<#if borrowIsday==0>月<#elseif borrowIsday==1>天</#if></td>
		<td class="kaqu_r8" style="text-align:right;padding-right:30px;border-bottom:1px solid #ccc;" >${d.account}</td>
		<td class="kaqu_r8" style="text-align:right;padding-right:30px;border-bottom:1px solid #ccc;" >${d.capital}</td>
		<td class="kaqu_r8" style="text-align:right;padding-right:30px;border-bottom:1px solid #ccc;" >${d.interest}</td>
	</tr>
</#list>
	<tr height="30">
		<td class="kaqu_r8" style="text-align:right;padding-right:30px;border-bottom:1px solid #ccc;" >合计</td>
		<td class="kaqu_r8" style="text-align:right;padding-right:30px;border-bottom:1px solid #ccc;" >&nbsp;</td>
		<td class="kaqu_r8" style="text-align:right;padding-right:30px;border-bottom:1px solid #ccc;" >${repaymentInfo.account}</td>
		<td class="kaqu_r8" style="text-align:right;padding-right:30px;border-bottom:1px solid #ccc;">${repaymentInfo.capital}</td>
		<td class="kaqu_r8" style="text-align:right;padding-right:30px;border-bottom:1px solid #ccc;">${repaymentInfo.interest}</td>
	</tr>