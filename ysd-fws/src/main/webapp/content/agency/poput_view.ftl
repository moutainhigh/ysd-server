<div class="P10">
	<div class="tenderbox" style=" background:url(${base}/static/img/11_bg.png) no-repeat 0 top; background-color:#f2f1f0; padding:20px 0;">
       <div style=" width:350px; margin:0 auto; color:#666; font-size:14px;">
        <table class="tableShadow" width="100%" border="0" cellspacing="1" cellpadding="0">
                 <tbody>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu" width="34%">审核时间：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${(borrowRecharge.verifyTime?string("yyyy-MM-dd HH:mm:ss"))!}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">审核人：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${borrowRecharge.verifyAdmin}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">联系方法：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${borrowRecharge.verifyPhone}</td>
				</tr>
				<tr height='34' class = "whiteBg">
					<td class="kaqu_fuwu">审核备注：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">${borrowRecharge.verifyRemark}</td>
				</tr>
				</tbody>
		</table>
	</div>
</div>