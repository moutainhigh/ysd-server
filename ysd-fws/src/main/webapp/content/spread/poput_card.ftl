<div class="P10">
	<div class="tenderbox" style=" background:url(${base}/static/img/11_bg.png) no-repeat 0 top; background-color:#f2f1f0; padding:20px 0;">
       <div style=" width:480px; margin:0 auto; color:#666; font-size:14px;">
              <table width="100%" cellpadding="0" cellspacing="0" style=" border:1px solid #cccbca; border-bottom:none;" class = "clubCardTable">
                 <tbody>
				<tr height='34'>
					<td class="kaqu_fuwu" width="10%"></td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						 一次最多下发100张会员卡，可分批次操作。<input type = "hidden" name = "spreadMember.id" id = "spreadMemberId" value = "${(spreadMember.id)!}">
					</td>
				</tr>
				<tr height='34'>
					<td class="kaqu_fuwu" width="10%">类型：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						<select name="clubCard.type" id="selectClubCardTypeId" onchange="cardTypeSelectChange(this);">
							<option value="">
								请选择
							</option>
							<@listing_childlist sign="club_card_type"; listingList>
								<#list listingList as listing>
									<option value="${listing.sign}" >${listing.name}
									</option>
								</#list>
							</@listing_childlist>
						</select>
					</td>
				</tr>		
				<tr height='34'>
					<td class="kaqu_fuwu" width="10%">选择：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						<label><input type="checkbox" class="allCheck" checked/>全选/取消<span id = "cardNumSpanId"></span></label>
					</td>
				</tr>	
							
				<tr>
					<td class="kaqu_fuwu" >卡号：</td>
					<td class="kaqu_xinxi kaqu_xinxi_x2" id = "cardsTd">
						<#--<#list clubCardList as cc>
							<label style="display:inline-block;width:75px;margin-bottom:10px;"><input type = "checkbox" name = "idss" value = "${cc.id}" checked/>${cc.cardNo}</label>&nbsp;&nbsp;&nbsp;
						</#list>
						-->
					</td>
				</tr>	
				<tr height='40'>
					<td class="kaqu_fuwu"></td>
					<td class="kaqu_xinxi kaqu_xinxi_x2">
						<input type="button" value="确定" id="xfOk" onclick="xfFinish();" style="background:url(${base}/static/img/btns.png) no-repeat -480px 0; width:100px; height:33px; line-height:33px; border:0 none; text-align:center; color:#fff;font-family:'微软雅黑'; font-size:16px; margin-top:5px;" >
						
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>


<script>
$().ready( function() {
		var $allCheck = $(".clubCardTable input.allCheck");// 全选复选框
		
		
		// 全选
		$(".clubCardTable input.allCheck").bind("click", function () {
            var $this = $(this);
            var $idss = $(".clubCardTable input[name='idss']");// ID复选框
			if ($this.attr("checked")) {
				$idss.attr("checked", true);
				
			} else {
				$idss.attr("checked", false);
				
			}
        });
		
});

</script>


