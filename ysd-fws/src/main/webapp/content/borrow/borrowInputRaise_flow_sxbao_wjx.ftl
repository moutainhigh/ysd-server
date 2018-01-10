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
								<td class="text_r grayBg" width="150">原始借款日期：</td>
								<td style="">
									<input type="text" value=""  name ="originalBorrowDate" id="contract_originalBorrowDate" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg">原始借款人姓名：</td>
								<td style="">
									<input type="text" value=""  name ="originalBorrowerName" id="contract_originalBorrowerName" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg">原始借款金额：</td>
								<td style="">
									<input type="text" value=""  name ="originalBorrowAccount" id="contract_originalBorrowAccount" class="input2 w_252"/>万元
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg">借款欠款金额：</td>
								<td style="">
									<input type="text" value="" name ="originalBorrowAccountOwe" id="contract_originalBorrowAccountOwe" class="input2 w_252"/>万元
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg">乐商贷项目总额：</td>
								<td style="">
									<input type="text" value="" name ="originalBorrowAccountSxbao" id="contract_originalBorrowAccountSxbao" class="input2 w_252"/>万元
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg">乐商贷项目（稳健）：</td>
								<td style="">
									<input type="text" value="" name ="originalBorrowAccountSxbaoWjx" id="contract_originalBorrowAccountSxbaoWjx" class="input2 w_252"/>万元
								</td>
							</tr>
							<tr class="contract_param" ><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg">乐商贷项目（积极）：</td>
								<td style="">
									<input type="text" value="" name ="originalBorrowAccountSxbaoJjx" id="contract_originalBorrowAccountSxbaoJjx" class="input2 w_252"/>万元
								</td>
							</tr>
							
						</tbody>
					
     
