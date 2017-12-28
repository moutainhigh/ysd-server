<div class="P10">
	<div class="tenderbox" style=" background:url(${base}/static/img/11_bg.png) no-repeat 0 top; background-color:#f2f1f0; padding:20px 0;">
       <div style=" width:480px; margin:0 auto; color:#666; font-size:14px;">
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
                 <tbody>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" width="34%">项目编号：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${accountCash.businessCode}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">项目标题：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${accountCash.bname}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">项目类型：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${accountCash.btype}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">借款人：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${accountCash.username}
					</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">借款人姓名：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${accountCash.realName}
					</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">借款总金额：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${(accountCash.jkTotal?string.currency)!'￥0.00'}元
					</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">本次放款金额：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${(accountCash.total?string.currency)!'￥0.00'}元
					</td>
				</tr>				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">本次到账金额：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${(accountCash.credited?string.currency)!'￥0.00'}元
					</td>
				</tr>			
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">手续费：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${(accountCash.fee?string.currency)!'￥0.00'} 元
					</td>
				</tr>				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">放款状态：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${accountCash.fkStatus}
					</td>
				</tr>				
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">操作时间：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						${(accountCash.modifyDate?string("yyyy-MM-dd HH:mm:ss"))!}
					</td>
				</tr>
				</tbody>
		</table>
	</div>
</div>