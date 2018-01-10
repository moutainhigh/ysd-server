						<tbody>
							<tr><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">业务类型：</td>
								<td style="">
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
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">出质人姓名：</td>
								<td style="">
									<input type="text" value=""  name ="pawner" id="contractMove_pawner" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">借款用途：</td>
								<td style="">
									<input type="text" value="" name ="purpose" id="contractMove_purpose" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">监管人：</td>
								<td style="">
									<input type="text" value="" name ="supervisor" id="contractMove_supervisor" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">质押率：</td>
								<td style="">
									<input type="text" value="" name ="pledgeRate" id="contractMove_pledgeRate" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">最高质押率：</td>
								<td style="">
									<input type="text" value="" name ="pledgeRateTop" id="contractMove_pledgeRateTop" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">保险名称：</td>
								<td style="">
									<input type="text" value="" name ="insuranceName" id="contractMove_insuranceName" class="input2 w_252"/>
								</td>
							</tr>
							
							<tr class="pawn_03" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">抵押物：</td>
								<td style="padding-bottom:10px;">
									<input type="button" value="新增抵押物" class="kaqu_e4" onclick="addContractPawnItem();">
									<img src="${base}/static/img/qs.png" width="20" height="20" title="新增抵押物" class="kaqu_e5">
								</td>
							</tr>
							<tr class="pawn_03" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">抵押物：</td>
								<td style="">
									<table class="tableShadow" width="100%" cellpadding="0" cellspacing="0">
										<thead>
										<tr>
											<td class="kaqu_e7" style="width:200px;text-align:center;">质押物<br/>名称</td>
											<td class="kaqu_e7" style="width:200px;text-align:center;">规格与<br/>计量单位</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">数量</th>
											<td class="kaqu_e8" style="width:200px;text-align:center;">价格<br/>（元/吨）</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">质押物<br/>价值</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">备注</td>
											<td class="kaqu_e8" style="width:200px;text-align:center;">操作</td>
										</tr>
										<tbody id="table_contract_pawn">

										</tbody>
									</table>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">抵押物总价值：</td>
								<td style="">
									<input type="text" value="" name ="pledgeMoneyTotal" id="contractMove_pledgeMoneyTotal" class="input2 w_252"/>
								</td>
							</tr>
							<tr style="display:none;">
								<td>
									<table>
										<tbody  id="init_pawn">
										<tr>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="pawnName" class="kaqu_e10" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="pawnUnit" class="kaqu_e10" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="pawnQuantity" class="kaqu_e10" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="pawnUnitPrice" class="kaqu_e10" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="pawnTotalPrice" class="kaqu_e10" style="width:50px;"/>
											</td>
											<td class="kaqu_sa" style="padding:0px 10px 10px 10px;">
												<input type="text" name="pawnRemark" class="kaqu_e10" style="width:50px;"/>
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
					
     
