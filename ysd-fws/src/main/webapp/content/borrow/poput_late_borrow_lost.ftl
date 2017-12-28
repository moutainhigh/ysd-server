
<div class="P10">
	<div class="tenderbox" style=" background:url(${base}/static/img/11_bg.png) no-repeat 0 top; background-color:#f2f1f0; padding:20px 0;">
       	<div style=" width:390px; margin:0 auto; color:#666; font-size:14px;">
       			<form id="lostForm">
              	<table width="100%" cellpadding="0" cellspacing="0" style=" border:1px solid #cccbca; ">
              		<input type="hidden" name="bid" value="${borrow.id}"/>
					<tr height='34'>
						<td class="kaqu_fuwu" width="50%">逾期罚金：</td>
						<td class="kaqu_xinxi kaqu_xinxi_x2">
							${borrow.latePenalty}元
						</td>
					</tr>
					<tr height='34'>
						<td class="kaqu_fuwu" >逾期本金：</td>
						<td class="kaqu_xinxi kaqu_xinxi_x2">
							${borrow.lateAccount}元
						</td>
					</tr>
					<tr height='34'>
						<td class="kaqu_fuwu" >逾期还款金额(不含罚金)：</td>
						<td class="kaqu_xinxi kaqu_xinxi_x2">
							<input type="text" id="backMoney" name="backMoney" />
						</td>
					</tr>
					<tr height='40'>
						<td class="kaqu_xinxi kaqu_xinxi_x2" colspan="2" style="text-align: center;">
							<input type="button" value="还款" id="btnPayLost" onclick="ajaxLateRepaymentBackLost();" style="background:url(${base}/static/img/btns.png) no-repeat -480px 0; width:100px; height:33px; line-height:33px; border:0 none; text-align:center; color:#fff;font-family:'微软雅黑'; font-size:16px; margin-top:5px;" >
						</td>
					</tr>
				</table>
				</form>
		</div>
	</div>
</div>