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
								<td class="text_r grayBg" width="100">抵押人姓名：</td>
								<td style="">
									<input type="text" value=""  name ="pawner" id="cpme_pawner" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">借款用途：</td>
								<td style="">
									<input type="text" value="" name ="purpose" id="cpme_purpose" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">抵押物地址：</td>
								<td style="">
									<input type="text"  value="" name ="pawnAddress" id="cpme_pawnAddress" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">抵押物面积：</td>
								<td style="">
									<input type="text" value="" name ="pawnArea" id="cpme_pawnArea" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">抵押物价值：</td>
								<td style="">
									<input type="text" value="" name ="pawnMoney" id="cpme_pawnMoney" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">房屋所有权证：</td>
								<td style="">
									<input type="text"  value="" name ="pawnWarrant" id="cpme_pawnWarrant" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">国有土地使用证：</td>
								<td style="">
									<input type="text" value="" name ="pawnEarth" id="cpme_pawnEarth" class="input2 w_252"/>
								</td>
							</tr>
					</tbody>