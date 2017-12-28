<div class="P10">
	<div class="tenderbox" style=" background: background-color:#fff; padding:20px 0;">
       <div style=" width:480px; margin:0 auto; color:#666; font-size:14px;">
                                     同意展期金额：${agreeDeferMoney?string.currency}
              <table width="100%" cellpadding="0" cellspacing="0" style=" border:1px solid #cccbca; border-bottom:none;margin-top:15px;" class="kaqu_center_style">
             		 <thead bgcolor="#e6e4e3" align="center">
		             	<tr height="40">
								<th>用户名</th>
								<th>金额</th>
								<th>利率</th>
							</tr>
					</thead>
                 <tbody>
                 <#list borrowTempList as bt>
					<tr height='34'>
						<td class="kaqu_xinxi" width="34%">${bt.name}</td>
						<td class="kaqu_xinxi kaqu_xinxi_x2">${bt.loanAmountFee?string.currency}</td>
						<td class="kaqu_xinxi kaqu_xinxi_x2">${rateHight!'0'}%</td>
					</tr>
				</#list>
				</tbody>
		</table>
	</div>
</div>