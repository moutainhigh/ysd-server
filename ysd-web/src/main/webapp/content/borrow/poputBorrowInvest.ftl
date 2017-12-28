<script>

function verifyCode(){
	$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();' style='position:relative;top:8px;'><img id = 'code_img' src='' /></a>");
	$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	$("#vCode").attr("style", "position:absolute;right:3px;bottom:13px;");
	$("#mycode").select();
}

function verifyCodeLink(){
	$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
}

function closePoput() {
	KP.close();
}


</script>

<div>
	<div style=" background:#dfe2e6; padding-left:45px; height:65px; line-height:65px; color:#363636; font-size:16px;">
		您即将投资
		<font style="color:#d14324;">
			￥${(tenderAbleMoney+tenderContinueMoney)}元
			</font>，请输入密码并确认。</div>
	<form id="investForm" style="padding-left:45px;">
	<input type="hidden" name="borrowTender.borrowId" value="${borrow.id}"/>
	<input type="hidden" name="borrowTender.ableAmount" value="${tenderAbleMoney}"/>
<#if borrow.isday=='0'>
	<input type="hidden" name="borrowTender.continueAmount" value="${tenderContinueMoney}"/>
<#else>
	<input type="hidden"  name="borrowTender.continueAmount" value="0"/>
</#if>
		<table width="80%" align="center" cellpadding="0" cellspacing="0" style="color:#4b4b4b; font-size:14px;">
			<tbody>
				<tr height="23"></tr>
<#if borrow.isDxb==1>
				<tr>
					<td class="tar">请输入定向密码：</td>
					<td><input type="password" id="dxpwd" name="dxpwd" style="border:1px solid #c6c6c6;height:30px;line-height:30px;"/></td>
				</tr>
				<tr height="10"></tr>
</#if>
				<tr>
					<td class="tar">请输入交易密码：</td>
					<td><input type="password" id="idsafepwd" name="safepwd" style="width:168px;border:1px solid #c6c6c6;height:30px;line-height:30px;" /></td>
				</tr>
				<tr height="10"></tr>
				<tr height="40">
					<td class="tar">请输入验证码：</td>
					<td style="position:relative;">
						<input type="text"  id="mycode" name="mycode" onfocus="verifyCode();" style="width:168px;border:1px solid #c6c6c6;height:30px;line-height:30px;"/>
						<span id="vCode" style=" visibility:hidden;"></span>
					</td>
				</tr>
			</tbody>
			
		</table>
		<div style="padding-top:60px; text-align:right; padding-right:145px;">
			<input type="button" value="确定" id="btnInvest" onclick="submitInverst();" style="background:url(${base}/static/img/a25.png) 0 0 no-repeat; width:80px; height:32px; line-height:32px; text-align:center; color:#fff; font-size:16px; border:0 none; font-weight:700; margin-right:15px;"/>
			<input type="button" value="投标中" id="btnInvesting" style="display:none;background:url(${base}/static/img/a4.png) 0 0 no-repeat; width:80px; height:32px; line-height:32px; text-align:center; color:#fff; font-size:16px; border:0 none; font-weight:700; margin-right:15px;">
			<input type="button" value="取消" id="btnCancle" onclick="closePoput();" style="background:url(${base}/static/img/a26.png) 0 0 no-repeat; width:80px; height:32px; line-height:32px; text-align:center; color:#666; font-size:16px; border:0 none;font-weight:700;"/>
		</div>
	</form>
</div>

