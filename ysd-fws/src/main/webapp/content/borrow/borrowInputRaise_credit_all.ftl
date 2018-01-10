						<tbody>
							<tr>
								<td class="text_r org grayBg" width="40"></td>
								<td  class="kaqu_z1">业务类型：</td>
								<td style="">
									<select id="businessType" name="borrow.businessType" onchange="showContractInfo()">
<#if btp==7>
										<option value="01" <#if raiseTypeCode=='01'>selected</#if> >房产</option>
										<option value="02" <#if raiseTypeCode=='02'>selected</#if> >汽车</option>
<#elseif btp==8>
										<option value="01" <#if raiseTypeCode=='01'>selected</#if> >房产</option>
										<option value="02" <#if raiseTypeCode=='02'>selected</#if> >汽车</option>
										<option value="03" <#if raiseTypeCode=='03'>selected</#if> >动产</option>
										<option value="04" <#if raiseTypeCode=='04'>selected</#if> >保证</option>
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
								<td class="text_r grayBg" width="100">融资客户姓名：</td>
								<td style="">
									<input type="text" value=""  name ="financeName" id="financeName" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">融资客户法定代表人：</td>
								<td style="">
									<input type="text" value="" name ="financeLegalPerson" id="financeLegalPerson" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">融资客户住址：</td>
								<td style="">
									<input type="text"  value="" name ="financeAddress" id="financeAddress" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td class="text_r grayBg" width="100">融资客户邮编：</td>
								<td style="">
									<input type="text" value="" name ="financePost" id="financePost" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td >融资客户联系电话：</td>
								<td style="">
									<input type="text" value="" name ="financePhone" id="financePhone" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td >融资客户签章：</td>
								<td style="">
									<input type="file" name="imageFile" id="imageItem_financeSignImg" style="height:34px;margin-bottom:10px;"/>
									<input type="hidden" name="financeSignImg" id="hidden_financeSignImg"/>
									<a id="btnUpload_financeSignImg" onclick="ajaxFileUploadImageWithRtn(\'3\','imageItem_financeSignImg','${base}/file/ajaxUploadImage.do','hidden_financeSignImg',uploadFileHideBack());" href="javascript:void(0);" class="kaqu_se">上传</a>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td>合同签订地址：</td>
								<td style="">
									<input type="text" value="" name ="contractAddress" id="contractAddress" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td >*服务费比例：</td>
								<td style="">
									<input type="text" value="" name ="chargeRate" id="chargeRate" class="input2 w_252"/>%
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td >服务费汇款银行：</td>
								<td style="">
									<input type="text" value="" name ="chargeBankName" id="chargeBankName" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td >服务费银行卡号：</td>
								<td style="">
									<input type="text" value="" name ="chargeBankCard" id="chargeBankCard" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td >服务费开户行：</td>
								<td style="">
									<input type="text" value="" name ="chargeBankOpen" id="chargeBankOpen" class="input2 w_252"/>
								</td>
							</tr>
							<tr class="contract_param"><td class="text_r org grayBg" width="40"></td>
								<td >借款用途：</td>
								<td style="">
									<input type="text" value="" name ="purpose" id="purpose" class="input2 w_252"/>
								</td>
							</tr>
							
						</tbody>


			