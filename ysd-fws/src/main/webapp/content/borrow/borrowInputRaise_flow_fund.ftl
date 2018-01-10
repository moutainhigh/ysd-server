						<tbody>
							<tr><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">业务类型：</td>
								<td style="">
									<select id="businessType" name="borrow.businessType" onchange="showContractInfo()" class="kaqu_w101">
<#if btp==7>
										<option value="01" <#if raiseTypeCode=='01'>selected</#if> >房产</option>
										<option value="02" <#if raiseTypeCode=='02'>selected</#if> >汽车</option>
										<option value="12" <#if raiseTypeCode=='12'>selected </#if> >奢品贷</option>
										<option value="16" <#if raiseTypeCode=='16'>selected</#if> >随心宝（稳健型）</option>
										<option value="17" <#if raiseTypeCode=='17'>selected</#if> >随心宝（积极型）</option>
										<option value="18" <#if raiseTypeCode=='18'>selected</#if> >基金</option>
<#elseif btp==8>
										<option value="01" <#if raiseTypeCode=='01'>selected</#if> >房产</option>
										<option value="02" <#if raiseTypeCode=='02'>selected</#if> >汽车</option>
										<option value="03" <#if raiseTypeCode=='03'>selected</#if> >动产</option>
										<option value="04" <#if raiseTypeCode=='04'>selected</#if> >保证</option>
										<option value="13" <#if raiseTypeCode == '13'>selected </#if> >益老贷</option>
										<option value="14" <#if raiseTypeCode == '14'>selected </#if> >券贷通</option>
										<option value="15" <#if raiseTypeCode=='15'>selected</#if> >转贷宝</option>
<#elseif btp==9>
										<option value="11" <#if raiseTypeCode=='11'>selected</#if> >信用</option>
<#elseif btp==10>
										<option value="99" <#if raiseTypeCode=='99'>selected</#if> >体验</option>
										
<#elseif btp==11>						
										<option value="01" <#if raiseTypeCode=='01'>selected</#if> >房产</option>
										<option value="02" <#if raiseTypeCode=='02'>selected</#if> >汽车</option>
										<option value="03" <#if raiseTypeCode=='03'>selected</#if> >动产</option>
										<option value="04" <#if raiseTypeCode=='04'>selected</#if> >保证</option>
</#if>
									</select>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="150">受托人姓名：</td>
								<td style="">
									<input type="text" value=""  name ="consignee" id="contract_consignee" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" >受托人身份证：</td>
								<td style="">
									<input type="text" value=""  name ="consigneeCardId" id="contract_consigneeCardId" class="input2 w_252"/>
								</td>
							</tr><tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">法定代表人：</td>
								<td style="">
									<input type="text" value=""  name ="legalPerson" id="contract_legalPerson" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">注册地：</td>
								<td style="">
									<input type="text" value="" name ="address" id="contract_address" class="input2 w_252"/>
								</td>
							</tr>
							
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">基金份额：</td>
								<td style="">
									<input type="text" value="" name ="jjSize" id="contract_jjSize" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">基金名称：</td>
								<td style="">
									<input type="text" value="" name ="jjName" id="contract_jjName" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">基金合同名称：</td>
								<td style="">
									<input type="text" value="" name ="jjhtName" id="contract_jjhtName" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">基金编号：</td>
								<td style="">
									<input type="text" value="" name ="jjCode" id="contract_jjCode" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">基金总价值：</td>
								<td style="">
									<input type="text" value="" name ="jjAccount" id="contract_jjAccount" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">预期收益年利率：</td>
								<td style="">
									<input type="text" value="" name ="jjApr" id="contract_jjApr" class="input2 w_252"/>
								</td>
							</tr>
							
							
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">基金投资目标：</td>
								<td style="">
									<input type="text" value="" name ="jjtzmb" id="contract_jjtzmb" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">基金担保情况：</td>
								<td style="">
									<input type="text" value="" name ="jjdbqk" id="contract_jjdbqk" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">基金管理人：</td>
								<td style="">
									<input type="text" value="" name ="jjglr" id="contract_jjglr" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">基金托管人：</td>
								<td style="">
									<input type="text" value="" name ="jjtgr" id="contract_jjtgr" class="input2 w_252"/>
								</td>
							</tr>
							
							
							
							
							
						</tbody>
					
     
