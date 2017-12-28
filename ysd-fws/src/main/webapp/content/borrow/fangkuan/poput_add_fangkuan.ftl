<div class="P10">
	<div class="tenderbox" style=" background:url(${base}/static/img/11_bg.png) no-repeat 0 top; background-color:#f2f1f0; padding:20px 0;">
       <div style=" width:350px; margin:0 auto; color:#666; font-size:14px;">
        <form id="fangkuanForm">
        <input type="hidden" name="id" value="${borrowFangkuanBean.id}"/>
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
           <tbody>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" width="34%">项目标题：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${borrowFangkuanBean.name}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">借款人：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${borrowFangkuanBean.borrowerRealName}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">放款账号：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${borrowFangkuanBean.bankCard}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">开户银行：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${borrowFangkuanBean.bankBranch}</td>
				</tr>
				
				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">放款总额：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${borrowFangkuanBean.borrowTotal?string.currency}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">保证金：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">-${borrowFangkuanBean.depositMoney?string.currency}</td>
				</tr>
				
				<#if borrowFangkuanBean.feeType == 1>
					<#assign fee = borrowFangkuanBean.feeMoney>
                <#else>
                	<#assign fee = borrowFangkuanBean.borrowTotal*borrowFangkuanBean.partAccount/100>
                </#if>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">服务费：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">-${fee?string.currency}</td>
				</tr>
				
				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">借款人实收金额：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${(borrowFangkuanBean.borrowTotal - borrowFangkuanBean.depositMoney - fee)?string.currency}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">安全密码：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2"><input id="paypwdId" type="password" name="paypwd" /></td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" COLSPAN=2>
						<label>
						<input type="checkbox" id="agreeCheckbox" value="false" checked />借款人同意由『乐商贷』统一打款</label>
					</td>
				</tr>
				
				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" COLSPAN=2>
					<#if borrowFangkuanBean.bankCard?exists && borrowFangkuanBean.bankBranch?exists>
						<input type="button" value="确定" onclick="ajaxAddFangkuan();" >
						<input type="button" value="取消" onclick = "KP.close();">
					<#else>
						请添加借款人银行账号信息
					</#if>
					</td>
				</tr>
				</tbody>
		</table>
		</form>
	</div>
</div>