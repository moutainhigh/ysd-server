						<tbody>
							
							<tr><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">业务类型：</td>
								<td>
									<select id="businessType" name="borrow.businessType" onchange="showContractInfo()" class="kaqu_w101">
<#if btp==7>
										<option value="01" <#if raiseTypeCode=='01'>selected</#if> >房产</option>
										<option value="02" <#if raiseTypeCode=='02'>selected</#if> >汽车</option>
										<option value="12" <#if raiseTypeCode=='12'>selected </#if> >奢品贷</option>
<#elseif btp==8>
										<option value="01" <#if raiseTypeCode=='01'>selected</#if> >房产</option>
										<option value="02" <#if raiseTypeCode=='02'>selected</#if> >汽车</option>
										<option value="03" <#if raiseTypeCode=='03'>selected</#if> >动产</option>
										<option value="04" <#if raiseTypeCode=='04'>selected</#if> >保证</option>
										<option value="13" <#if raiseTypeCode == '13'>selected </#if> >益老贷</option>
										<option value="14" <#if raiseTypeCode == '14'>selected </#if> >券贷通</option>
										<option value="15" <#if raiseTypeCode == '15'>selected</#if> >转贷宝</option>
<#elseif btp==9>
										<option value="11" <#if raiseTypeCode=='11'>selected</#if> >信用</option>
<#elseif btp==10>
										<option value="99" <#if raiseTypeCode=='99'>selected</#if> >体验</option>
<#elseif btp==11>
										<option value="01" <#if raiseTypeCode == '01'>selected </#if>  >房产</option>
										<option value="02" <#if raiseTypeCode == '02'>selected </#if>  >汽车</option>
										<option value="03" <#if raiseTypeCode == '03'>selected </#if>  >动产</option>
										<option value="04" <#if raiseTypeCode == '04'>selected </#if>  >保证</option>
</#if>
									</select>
								</td>
							</tr>
							
						
						<#--汽车质押-->	
					
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">出质人姓名：</td>
								<td style="">
									<input type="text" value=""  name ="pawner" id="contractCar_pawner" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">借款用途：</td>
								<td style="">
									<input type="text" value="" name ="purpose" id="cpme_purpose" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="pawn_02" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">抵押物：</td>
								<td style="padding-bottom:10px;">
									<input type="button" value="新增抵押物" class="kaqu_e4" onclick="addContractPawnItem();">
									<input type="hidden" id="idSortPawnItem" value="0"/>
									<img src="${base}/static/img/qs.png" width="20" height="20" title="新增抵押物" class="kaqu_e5">
								</td>
							</tr>
							<tr class="pawn_02"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">抵押物：</td>
								<td>
									<table class="tableShadow" width="100%" cellpadding="0" cellspacing="0">
										<tr>
										<thead>
											<td class="kaqu_e7" style="width:200px;text-align:center;">车辆牌照号</td>
											<td class="kaqu_e7" style="width:200px;text-align:center;">轿车车型</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">厂牌型号</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">发动机号码</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">车架号/车辆识别号码</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">漆型/颜色</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;"> 注册日期</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">质押物价值</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">操作</td>
										</tr>
										</thead>
										<tbody id="table_contract_pawn">

										</tbody>
									</table>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">抵押物总价值：</td>
								<td style="">
									<input type="text" value="" name ="pledgeMoneyTotal" id="contractCar_pledgeMoneyTotal" class="kaqu_w100"/>
								</td>
							</tr>
							<tr style="display:none;">
								<td>
									<table class="tableShadow">
										<tbody  id="init_pawn">
										<tr>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="carLicense" class="input2" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="carType" class="input2" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="carModel" class="input2" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="carMotor" class="input2" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="carFrame" class="input2" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="carColor" class="input2" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="carRegister" class="input2" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="carMoney" class="input2" style="width:50px;"/>
											</td>
											<td class="kaqu_e14" style="padding:0px 10px 10px 10px;">
												<a onclick="delContractPawnItem(this)" href="javascript:void(0);"class="kaqu_close kaqu_close1">删除</a>
											</td>
										</tr>
										</tbody>
									</table>
								</td>
							</tr>
							
						</tbody>
						
        
				
