<script>
function verifyCode(){
	$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();'><img id = 'code_img' src='' /></a>");
	$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	$("#vCode").attr("style", "");
	$("#mycode").select();
}

function verifyCodeLink(){
	$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
}

<#--$().ready(function() {
	var $vCode = $("#vCode");
	var $vCodeA = $("#vCodeA");
	var $codeImg = $("#code_img");
	var $mycode = $("#mycode");
	var $investForm = $("#investForm");
	
	$vCodeA.click(function() {
		$codeImg.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	});
	$("#reg_button").click(function(){
		$registerForm.submit();
	});
	
});-->
</script>

<form id="investFlowForm" enctype="multipart/form-data">
<input type="hidden" name="bId" value="${borrow.id}"/>
<input type="hidden" id="idMoneyPieceMoney" value="${borrow.wanderPieceMoney}"/>
<input type="hidden" id="idLowestAccount" value="${borrow.lowestAccount}"/>
<input type="hidden" id="idWanderPieceInterest" value="${wanderPieceInterest}"/>
<div class="P20">
<div style=" background:#fff; padding-left:65px; line-height:55px; color:#363636; font-size:16px;">
	<div class="tenderbox">
		<p>已完成：${borrow.schedule}% <br />还  差：￥${borrow.balance}(${borrow.showBalanceSize}份)	</p>
		<table width="100%" align="center">
			<tr>
				<td style="color:#363636; font-size:16px; padding-right:3px;text-aligin:right;"  width = "100">可用余额：</td>
				<td>￥${userAbleMoney}，最多认购　<span class="c1">${maxWanderPiece}</span>份</td>
			</tr>
			<tr>
				<td style="color:#363636; font-size:16px; padding-right:3px;text-aligin:right;">续投余额：</td>
				<td>￥${continueTotal}，最多认购　<span class="c1">${maxWanderPieceContinue}</span>份</td>
			</tr>
		<#--
			<tr>
				<td class="tar">认购份数：</td>
				<td><select name="tenderMoney" id="wanderPiece" class="sel" onchange="changePiece();">
					<#list minWanderPiece..maxWanderPiece as i>
						<option value="${i}">${i}</option>
					</#list>
				</select>份&nbsp;(投资金额：<span id="idPayMoney">0.00</span>)</td>
			</tr>
			<tr>
				<td class="tar">续投份数：</td>
				<td><select name="tenderMoneyContinue" id="wanderPieceContinue" class="sel" onchange="changePiece();">
					<#list minWanderPieceContinue..maxWanderPieceContinue as i>
						<option value="${i}" <#if i==maxWanderPieceContinue>selected</#if>>${i}</option>
					</#list>
				</select>份&nbsp;(投资金额：<span id="idPayMoneyContinue">0.00</span>)</td>
			</tr>
			-->
			<tr>
				<td class="tar" style="text-aligin:right;">认购份数：</td>
				<td>
					<input type = "text" id="wanderPiece" name="tenderMoney" style="border:1px solid #c6c6c6;height:30px;line-height:30px;" onblur="changePiece();" onkeyup="value=value.replace(/[^0-9\.]/g,'')" value="0">份&nbsp;(投资金额：<span id="idPayMoney">0.00</span>)
				
				</td>
			</tr>
			<tr>
				<td class="tar" style="text-aligin:right;">续投份数：</td>
				<td>
				<input type = "text" id="wanderPieceContinue" name="tenderMoneyContinue" style="border:1px solid #c6c6c6;height:30px;line-height:30px;" onblur="changePiece();" onkeyup="value=value.replace(/[^0-9\.]/g,'')" value="0">份&nbsp;(投资金额：<span id="idPayMoneyContinue">0.00</span>)
				
			</tr>
			
<#--
			<tr>
				<td class="tar" valign="top">赎回时间：</td>
				<td valign="top">
					<div class="data-list tac">
						<table>
							<thead>
								<th>选择</th>
								<th>回购日期</th>
							</thead>
							<tbody>
							<#list wanderRepayPlanDetail.wanderRepayPlanEach as nodes>
								<tr>
									<td><input type="radio" name="wanderPlan" value="${nodes.issue}" <#if nodes.clickFlg==false>disabled</#if><#if !nodes_has_next>checked</#if> onclick="clickRepayPlan('${nodes.issue}','${nodes.repayDateStr}')"  /></td>
									<td>${nodes.repayDateStr}</td>
									<input type="hidden" id="hid_each_income_${nodes.issue}" value="${nodes.incomeEachPiece}" />
								</tr>
							</#list>				
							</tbody>
						</table>
					</div>
				</td>
			</tr>
-->
<#--
			<tr>
				<td class="tar">利息收益：</td>
				<td><em class="c1"><span id="idShowInCome">￥0.00</span></em><br>（认购<span id="idShowWanderPiece">${minWanderPiece}</span>份，<span id="idShowDackDate">${wanderRepayPlanDetail.lastDate?string("yyyy-MM-dd")}</span> 赎回）</td>
				<input type="hidden" id="hid_piece_no" value="${minWanderPiece}"/>
				<input type="hidden" id="hid_days_no" value="${minWanderPiece}"/>
			</tr>
-->
			<#if borrow.isDxb==1>
				<tr>
					<td class="tar">定向密码：</td>
					<td><input type="password" id="dxpwd" style="border:1px solid #c6c6c6;height:30px;line-height:30px;" name="dxpwd"/></td>
				</tr>
			<#else>
				<input type="hidden" id="dxpwd" style="border:1px solid #c6c6c6;height:30px;line-height:30px;" value="1"/>
			</#if>
			<tr>
				<td class="tar">安全密码：</td>
				<td><input type="password" style="border:1px solid #c6c6c6;height:30px;line-height:30px;" name="safepwd"/></td>
			</tr>
			<tr>
				<td class="tar">验证码：</td>
				<td>
					<input type="text" id="mycode" name="mycode" style="border:1px solid #c6c6c6;height:30px;line-height:30px;" onfocus="verifyCode();"/>
					<span id="vCode" style=" visibility:hidden;">
	    				<#--<a id='vCodeA' title="点击更换"></a>${base}/rand.do-->
	    			</span>
	    		</td>
			</tr>
			<tr>
				<td style="color:#363636; font-size:16px; padding-right:3px;"></td>
				<td>
					<input type="button" id="btnWanderTo"  value="确定投标" onclick="submitInverstFlow();"  style="background:url(${base}/static/img/a25.png) 0 0 no-repeat; width:80px; height:32px; line-height:32px; text-align:center; color:#fff; font-size:16px; border:0 none; font-weight:700; margin-right:15px;"/>
					<input type="button" id="btnWandering"  style="display:none;background:url(${base}/static/img/a26.png) 0 0 no-repeat; width:80px; height:32px; line-height:32px; text-align:center; color:#fff; font-size:16px; border:0 none; font-weight:700; margin-right:15px;" value="投标中"/>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
<script>
$(function(){
	//changePiece();
	//fillTouziMoney();
});
</script>
