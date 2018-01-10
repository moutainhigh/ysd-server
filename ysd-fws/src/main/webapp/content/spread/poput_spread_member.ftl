
<div class="P10">
	<div class="tenderbox" style=" background:url(${base}/static/img/11_bg.png) no-repeat 0 top; background-color:#f2f1f0; padding:20px 0;">
       <div style=" width:480px; margin:0 auto; color:#666; font-size:14px;">
              <table width="100%" cellpadding="0" cellspacing="0" style=" border:1px solid #cccbca; border-bottom:none;">
                 <tbody>
				<tr height='34'>
					<td class="kaqu_fuwu" width="34%">推广人员：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						<select name="spreadMember.id" id="spreadMemberId">
							<option value="">
								请选择
							</option>
							<#list spreadMemberList as list>
								<option value = "${list.id}">${list.fullName}<#--（${list.spreadNo}）--></option>
							</#list>
						</select>
					</td>
				</tr>
				
				<tr height='40'>
					<td class="kaqu_fuwu"></td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						<input type="button" value="确定" id="okzy" onclick="zyFinish();" style="background:url(${base}/static/img/btns.png) no-repeat -480px 0; width:100px; height:33px; line-height:33px; border:0 none; text-align:center; color:#fff;font-family:'微软雅黑'; font-size:16px; margin-top:5px;" >
						
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>