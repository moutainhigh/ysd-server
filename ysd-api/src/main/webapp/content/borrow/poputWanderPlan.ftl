<form id="investWanderForm" enctype="multipart/form-data">
<input type="hidden" name="bId" value="${borrow.id}"/>
<input type="hidden" id="idMoneyPieceMoney" value="${borrow.wanderPieceMoney}"/>
<input type="hidden" id="idLowestAccount" value="${borrow.lowestAccount}"/>
<div class="P20">
	<div class="tenderbox">
		<p>已完成：${borrow.schedule}% <br />还  差：￥${borrow.balance}(${borrow.showBalanceSize}份)	</p>
		<table width="80%">
			<tr>
				<td width="45%" class="tar">可用余额：</td>
				<td>￥${userAbleMoney}，最多认购　<span class="c1">${maxWanderPiece}</span>份</td>
			</tr>
			<tr>
				<td class="tar">续投余额：</td>
				<td>￥${continueTotal}，最多认购　<span class="c1">${maxWanderPieceContinue}</span>份</td>
			</tr>
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
			<tr>
				<td class="tar">利息收益：</td>
				<td><em class="c1"><span id="idShowInCome">￥0.00</span></em><br>（认购<span id="idShowWanderPiece">${minWanderPiece}</span>份，<span id="idShowDackDate">${wanderRepayPlanDetail.lastDate?string("yyyy-MM-dd")}</span> 赎回）</td>
				<input type="hidden" id="hid_piece_no" value="${minWanderPiece}"/>
				<input type="hidden" id="hid_days_no" value="${minWanderPiece}"/>
			</tr>
			<#if borrow.isDxb==1>
				<tr>
					<td class="tar">定向密码：</td>
					<td><input type="password" id="dxpwd" class="txt" name="dxpwd"/></td>
				</tr>
			<#else>
				<input type="hidden" id="dxpwd" class="txt" value="1"/>
			</#if>
			<tr>
				<td class="tar">安全密码：</td>
				<td><input type="password" class="txt" name="safepwd"/></td>
			</tr>
			<tr>
				<td class="tar"></td>
				<td>
					<input type="button" id="btnWanderTo"  class="btn s3" value="确定投标" onclick="submitInverstWander();" />
					<input type="button" id="btnWandering" class="btn s3" style="display:none" value="投标中"/>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
<script>
$(function(){
	changePiece();
	fillTouziMoney();
});
</script>
