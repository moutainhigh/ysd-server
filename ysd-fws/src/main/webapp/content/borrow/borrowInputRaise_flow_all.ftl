						<tbody>
							<tr><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="86">业务类型：</td>
								<td>
									<select id="businessType" name="borrow.businessType" onchange="showContractInfo()" class="kaqu_w101" <#if bid != null>disabled</#if> >
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
										<option value="01" <#if raiseTypeCode == '01'>selected </#if>  >房产</option>
										<option value="02" <#if raiseTypeCode == '02'>selected </#if>  >汽车</option>
										<option value="03" <#if raiseTypeCode == '03'>selected </#if>  >动产</option>
										<option value="11" <#if raiseTypeCode == '11'>selected </#if>  >信用</option>
</#if>
									</select>
								</td>
							</tr>
							
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">债权出让人姓名：</td>
								<td>
									<input type="text" name ="creditAssignmenterName" id="creditAssignmenterName" class="input2 w_252" value = "${(contractParam.creditAssignmenterName)!}"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="86">原始借款人姓名：</td>
								<td>
									<input type="text" name ="originalBorrowerName" id="originalBorrowerName" class="input2 w_252" value = "${(contractParam.originalBorrowerName)!}"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="86">原始抵押人姓名：</td>
								<td>
									<input type="text" name ="originalBorrowPledger" id="originalBorrowPledger" class="input2 w_252" value = "${(contractParam.originalBorrowPledger)!}"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="86">原始借款时间：</td>
								<td>
									<input type="text" name ="originalBorrowDate" id="originalBorrowDate" class="input2 w_252" value = "${(contractParam.originalBorrowDate)!}"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="86">原始借款金额：</td>
								<td>
									<input type="text" name ="originalBorrowAccount" id="originalBorrowAccount" class="input2 w_252" value = "${(contractParam.originalBorrowAccount)!}"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="86">原始借款期限：</td>
								<td>
									<input type="text" name ="originalBorrowTimelimit" id="originalBorrowTimelimit" class="input2 w_252" value = "${(contractParam.originalBorrowTimelimit)!}"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="86">原始借款利率：</td>
								<td>
									<input type="text" name ="originalBorrowApr" id="originalBorrowApr" class="input2 w_252" value = "${(contractParam.originalBorrowApr)!}"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="86">原始还款方式：</td>
								<td>
									<input type="text" name ="originalBorrowStyle" id="originalBorrowStyle" class="input2 w_252" value = "${(contractParam.originalBorrowStyle)!}"/>
								</td>
							</tr>
						</tbody>


			