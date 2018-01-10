<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>审核标页面 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script> 
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {
	var $tab = $("#tab");
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
});
function check_form(){
	 var frm = document.forms['validateForm'];
	 var verify_remark = frm.elements['verify_remark'].value;
	 var errorMsg = '';
	  if (verify_remark.length == 0 ) {
		errorMsg += '备注必须填写' + '\n';
	  }
	  
	  /**
	  if($("#auto_tender").length>0){
	  	var at= $("#auto_tender").val();
	  	if (at=='') {
	  		alert("请选择自动投标选项"); return false;
	  	}
	  }
	  */
	   if($("#continueAwardRate").length>0){
	  	var at= $("#continueAwardRate").val();
	  	if (at=='') {
	  		alert("请选择续投奖励利率"); return false;
	  	}
	  }
	  var baseApr = parseFloat($("#borrow_base_apr").val());
	var awardApr = parseFloat($("#borrow_award_apr").val());
	var yearRate = parseFloat($("#borrowVaryYearRate").val());
	if(isNaN(baseApr)){
		baseApr=0;
	}
	if(isNaN(awardApr)){
		awardApr=0;
	}
		
	if(FloatAdd(baseApr,awardApr)>0 && parseFloat(yearRate) != FloatAdd(baseApr,awardApr) ){
		alert('显示年利率 跟 实际年利率不符'+parseFloat(yearRate)+'   '+ FloatAdd(baseApr,awardApr));
		return false;
	}
	  
	  if (errorMsg.length > 0){
		alert(errorMsg); return false;
	  } else{  
	  	$.dialog({type: "warn", content: "确定执行此操作吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback: right});
		
	  }
}

function commit(obj) 
{
	var baseApr = parseFloat($("#borrow_base_apr").val());
	var awardApr = parseFloat($("#borrow_award_apr").val());
	var yearRate = parseFloat($("#borrowVaryYearRate").val());
	if(isNaN(baseApr)){
		baseApr=0;
	}
	if(isNaN(awardApr)){
		awardApr=0;
	}
		$("#apr_error").html('');
	if(parseFloat(yearRate) !=FloatAdd(baseApr,awardApr)){
		$("#apr_error").html('显示年利率 跟 实际年利率不符');
		return false;
	}
	
}

function FloatAdd(arg1,arg2){
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    return (arg1*m+arg2*m)/m;
}



function right(){
	$("#shenghe_button").val('处理中');
	$("#shenghe_button").attr("disabled", true);
	var frm = document.forms['validateForm'];
	frm.submit();
}

function change_is(type){
  var obj=document.getElementsByName(type);  
  for(var i=0; i<obj.length; i++){
    if(obj[i].checked) {
    	$("#verify_"+type).val(obj[i].value);
    }else{
    	$("#verify_"+type).val(0);
    }
   }
}
 function clearNoNum(obj){

		obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符

		obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是

		obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的

		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");

		obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数

	}
function clickborrowFee() {
	
	if($("#feeType1").attr("checked")) {
		$("#borrowFee0").hide();
		$("#borrowFee1").show();
	} else {
		$("#borrowFee1").hide();
		$("#borrowFee0").show();
	}
}
</script>
</head>
<body class="input admin">
	<div class="bar">
	<#if (borrow.status)==0>
		审核借款标  
	<#else>
		已满额借款标审核
	</#if>
	</div>
	<div class="body">
	  <ul id="tab" class="tab">
		<li>
			<input type="button" value="基本信息" hidefocus />
		</li>
		
		</ul>
		<form id="validateForm" action="borrow!updateNew.action" method="post"  enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="borrow.user.id" value="${borrow.user.id}" />
			<input type="hidden" name="borrow.name" value="${borrow.name}" />
			<input type="hidden" name="borrow.type" value="${borrow.type}" />
			<input type="hidden" name="borrowStatus" value="${borrow.status}" />
			<input type="hidden" id="borrowVaryYearRate" value="${borrow.varyYearRate*100}" />
<!--			
			<input type="hidden" id="verify_idCard" name="verifyMessJson.idCard" value="0" />
			<input type="hidden" id="verify_household" name="verifyMessJson.household" value="0" />
			<input type="hidden" id="verify_zhengXin" name="verifyMessJson.zhengXin" value="0" />
			<input type="hidden" id="verify_income" name="verifyMessJson.income" value="0" />
			<input type="hidden" id="verify_anCase" name="verifyMessJson.anCase" value="0" />
			<input type="hidden" id="verify_cardDriving" name="verifyMessJson.cardDriving" value="0" />
			<input type="hidden" id="verify_gouZhi" name="verifyMessJson.gouZhi" value="0" />
			<input type="hidden" id="verify_guJia" name="verifyMessJson.guJia" value="0" />
			<input type="hidden" id="verify_gcfp" name="verifyMessJson.gcfp" value="0" />
			<input type="hidden" id="verify_jdczs" name="verifyMessJson.jdczs" value="0" />
-->
			<table class="inputTable tabContent">
			<!-- 初审-->
			<#if (borrow.status)==0>
				<tr>
					<th>用户名: </th>
					<td>${borrow.user.username}</td>
				</tr>
				<tr>
					<th>标&nbsp;&nbsp;&nbsp;&nbsp;题: </th>
					<td>${borrow.name}</td>
				</tr>
				<tr>
					<th>类&nbsp;&nbsp;&nbsp;&nbsp;型: </th>
					<td>
						<#if borrow.type==0>秒标
						<#elseif borrow.type==14>天标
						<#elseif borrow.type==2>流转标
						<#elseif borrow.type==3>信用标
						<#elseif borrow.type==4>月标
						<#elseif borrow.type==11>天标
						<#elseif borrow.type==12>月标
						<#elseif borrow.type==13>流转标
						<#elseif  (borrow.type)== 15>月标
						<#elseif  (borrow.type)== 16>新手标
						<#elseif  (borrow.type)== 17>体验标
						<#else>其他
						</#if>
					</td>
				</tr>
<#--				
				<tr>
					<th>担保抵押: </th>
					<td>
						<#if borrow.isVouch==0>无担保，无抵押
						<#elseif borrow.isVouch==1>有担保，无抵押
						<#elseif borrow.isVouch==2>无担保，有抵押
						<#elseif borrow.isVouch==3>有担保，有抵押
						</#if>
					</td>
				</tr>
-->				
				<tr>
					<th>状&nbsp;&nbsp;&nbsp;&nbsp;态: </th>
					<td>
						<#if (borrow.status)==0>发布审批中</#if>
					</td>
				</tr>
				<tr>
					<th>借款期限: </th>
					<td>${borrow.timeLimit}<#if borrow.isday=='0'>个月<#elseif borrow.isday=='1'>天</#if></td>
				
				</tr>
				<tr>
					<th>借贷总金额: </th>
					<td>${borrow.account}</td>
				</tr>
				<#if borrow.type==2>
				<tr>
					<th>借贷总份数: </th>
					<td>${borrow.wanderPieceSize} 份</td>
				</tr>
				<tr>
					<th>单份金额: </th>
					<td>${borrow.wanderPieceMoney} 元/份</td>
				</tr>
				</#if>
				<tr>
					<#if borrow.isday=='0'>
						<th>年利率:</th>
						<td>${borrow.apr}%</td>
					<#elseif borrow.isday=='1'>
						<th>天利率:</th>
						<td>${borrow.apr}‰ &nbsp;&nbsp;&nbsp;年利率:${borrow.varyYearRate*100}%</td>
					</#if>
				</tr>
				
				<#if borrow.type!=2>
				<tr>
					<th>最低投标金额: </th>
					<td>${borrow.lowestAccount}</td>
				</tr>
				<tr>
					<th>最多投标总额: </th>
					<td>
						${borrow.mostAccount!'没有限制'}
					</td>
				</tr>
				<tr>
					<th>有效时间: </th>
					<td>${borrow.validTime}天</td>
				</tr>
				<#else>
				<tr>
					<th>最低投标金额: </th>
					<td>${borrow.lowestAccount}份</td>
				</tr>
				<tr>
					<th>最多投标总额: </th>
					<td>
						<#if !borrow.mostAccount&&borrow.mostAccount!=''>
							${borrow.mostAccount}份
						<#else>
							没有限制
						</#if>
					</td>
				</tr>
				</#if>

				<tr>
					<th>奖&nbsp;&nbsp;&nbsp;&nbsp;励: </th>
					<td>
						<#if borrow.award==1> ${borrow.funds}%,满标发放
						<#elseif borrow.award==2>${borrow.funds}%,还款结束时发放
						<#else>无
						</#if>
					</td>
				</tr>
				<tr>
					<th>项目详情: </th>
					<td>
						${(borrow.content)!}
					</td>
				</tr>
				<tr>
					<th>项目图片: </th>
					<td>
					<table>
						<#if (varImgList?size > 0)>	
		                <#list varImgList as cari>
			                                
					        
						<!--个人借款人-->
							<tr>
							
								<td>
									<#if cari.url!><a href="<@imageUrlDecode imgurl="${cari.url}"; imgurl>${imgurl}</@imageUrlDecode>" target ="_blank" title="点击查看"><img src="<@imageUrlDecode imgurl="${cari.url}"; imgurl>${imgurl}</@imageUrlDecode>" width="100" heigth="100"/></a><#else><font color="red">没有图片;</font></#if>
								</td>
							</tr>
						</#list>
	        			</#if>
	        		</table>
					</td>
					</tr>
			<tr>
			<td colspan="2">	
			<div class="bar">
				审核此借款标
			</div>
			<table >
				<#if borrow.type!='5'>
				<!--
				<tr>
					<th>是否自动投标: </th>
					<td>
						<select id="auto_tender" name="borrow.autoTenderStatus">
							<option value="">请选择</option>
							<option value="0">不自动</option>
							<option value="1">自动</option>
						</select>
					</td>
				</tr>
				<select id="auto_tender" name="borrow.autoTenderStatus">		
							<option value="0">不自动</option>			
				</select>
				-->
				<tr>
					<th>pc首页展示: </th>
					<td>
						<select id="rong_xun_flg" name="borrow.rongXunFlg">
							<option value="0">普通</option>
							<option value="1">热门推荐</option>
							<option value="2">新手福利</option>
							<option value="3">精品理财</option>
							<option value="4">定期优选</option>
						</select>
					</td>
				</tr>
			
				<input type="hidden" id="auto_tender" name="borrow.autoTenderStatus" value="0">
			</#if>
				<#if borrow.isday=='0'||borrow.type=='5'>
				<tr>
					<th>续投奖励比例: </th>
					<td>
						<select id="continueAwardRate" name="borrow.continueAwardRate">
							<option value="">请选择</option>
							<#list continueAwardRateList as carate>
							<option value="${carate.keyValue}">${carate.name}</option>
							</#list>
						</select>
					</td>
				</tr>
				</#if>
	<#if borrow.type=='17'>
			<tr>
			<th>证明选择： </th>
			<td>
				<input type="checkbox"  name="idCard" id="idCard" onclick="change_is('idCard')" value="1" />身份证
				<input type="checkbox"  name="household" id="household" onclick="change_is('household')" value="1" />户口本
				<input type="checkbox"  name="zhengXin" id="zhengXin" onclick="change_is('zhengXin')" value="1" />征信报告
				<input type="checkbox"  name="income" id="income" onclick="change_is('income')" value="1" />收入证明
				<input type="checkbox"  name="anCase" id="anCase" onclick="change_is('anCase')" value="1" />在执行案件查询
				<input type="checkbox"  name="cardDriving" id="cardDriving" onclick="change_is('cardDriving')" value="1" />车辆行驶证
				<input type="checkbox"  name="gouZhi" id="gouZhi" onclick="change_is('gouZhi')" value="1" />购置税凭证
				<input type="checkbox"  name="guJia" id="guJia" onclick="change_is('guJia')" value="1" />车辆评估报告
				<input type="checkbox"  name="gcfp" id="gcfp" onclick="change_is('gcfp')" value="1" />购车发票
				<input type="checkbox"  name="jdczs" id="jdczs" onclick="change_is('jdczs')" value="1" />机动车登记证书
			</td>
			</tr>
	</#if>
			<tr>
				<th>
						保证金: 
				</th>
				<td>
						<input type="text" name="borrow.depositMoney" class="formText" onkeyup="clearNoNum(this)" size="10" <#if borrow.type=='17'> value=0<#else>value="${(borrow.depositMoney)!}"</#if> />&nbsp;元
				</td>
			</tr>
			<tr>
				<th>
						借款手续费类型: 
				</th>
				<td>
					<label><input type="radio" name="borrow.feeType" id="feeType0" value="0" onclick="clickborrowFee()" <#if borrow.feeType=='' || borrow.feeType==0 >checked</#if>  />&nbsp;比例</label>
					<label><input type="radio" name="borrow.feeType" id="feeType1" value="1" onclick="clickborrowFee()" <#if borrow.feeType == 1>checked</#if>/>&nbsp;固定</label>
				</td>
			</tr>
			<tr id="borrowFee0" <#if borrow.feeType=='' || borrow.feeType==0 ><#else>style="display:none;"</#if>>
				<th>
						借款手续费: 
				</th>
				<td>
						<input type="text" name="borrow.partAccount" class="formText" onkeyup="clearNoNum(this)" size="10" <#if borrow.type=='17'> value=0<#else>value="${(borrow.partAccount)!}"</#if> />&nbsp;%
				</td>
			</tr>
			<tr id="borrowFee1" <#if borrow.feeType == 1><#else>style="display:none;"</#if> >
				<th>
						借款手续费: 
				</th>
				<td>
						<input type="text" name="borrow.feeMoney" class="formText" onkeyup="clearNoNum(this)" size="10" value="${(borrow.feeMoney)!}" />&nbsp;元
				</td>
			</tr>
			
			
			<tr id="borrowAwardScale">
				<th>
						可使用红包比例: 
				</th>
				<td>
						<input type="text" name="borrow.awardScale" class="formText" onkeyup="clearNoNum(this)" size="10" <#if borrow.type=='17'> value=0<#else>value="${(borrow.awardScale)!}" </#if> />&nbsp;%【为空或0表示不能使用红包】
				</td>
			</tr>
			<tr>
			<!--
			
			<th>
				投资先锋奖励金额:
			</th>
			<td>
				<input type="" class="formText" id="borrow_tzxf" name="borrow.tzxf" maxlength="3" onkeyup="value=value.replace(/[^0-9\.]/g,'')"/>&nbsp;元
			</td>
		<tr>
		
		<tr>
			<th>
				投资土豪奖励金额:
			</th>
			<td>
				<input type="text"  class="formText" id="borrow_tzth" name="borrow.tzth" maxlength="3" onkeyup="value=value.replace(/[^0-9\.]/g,'')"/>&nbsp;元
			</td>
		<tr>
		<tr>
			<th>
				收官大哥奖励金额:
			</th>
			<td>
				<input type="text"  class="formText" id="borrow_sgdg" name="borrow.sgdg" maxlength="3" onkeyup="value=value.replace(/[^0-9\.]/g,'')" />&nbsp;元
			</td>
		<tr>
		-->
		<tr>
			<th>
				显示年利率:
			</th>
			<td>
				<input type="text"  class="formText" id="borrow_base_apr" onblur="commit(this);"  name="borrow.baseApr" maxlength="5" onkeyup="value=value.replace(/[^0-9\.]/g,'')" style="width:65px;"  placeholder="基本年利率"/>% &nbsp; + &nbsp;
				<input type="text"  class="formText" id="borrow_award_apr" onblur="commit(this);" name="borrow.awardApr" maxlength="5" onkeyup="value=value.replace(/[^0-9\.]/g,'')" style="width:65px;" placeholder="加送年利率" />%
				&nbsp;&nbsp;&nbsp;
				<#if borrow.isday=='1'>年利率:${borrow.varyYearRate*100}%</#if>
				&nbsp;&nbsp;&nbsp;<span style="color:red" id="apr_error"></span>
			</td>
		<tr>
		
<!--			
			<tr>
			<th>借款合同类型： </th>
			<td>
				<input  name="borrow.isDo"  value="1" <#if borrow.isDo=='' || borrow.isDo==1 >checked</#if> type="radio" />&nbsp;网站直接出借</label><label><input type="radio" name="borrow.isDo" value="2" <#if borrow.isDo == 2>checked</#if>/>&nbsp;债券转让</label>
				
			</td>
			</tr>
-->	
			<tr>
				<th>
					法律意见书: 
				</th>
				<td>
					<input type="file" id="imgFile" name="file" />	 
				</td>
			</tr>		
		<tr>
			<th>
				状&nbsp;&nbsp;&nbsp;&nbsp;态:
			</th>
			<td>
				<#if (borrow.status)==0>
				<!--初审-->
				<input type="radio" checked="checked" name="borrow.status" value="1"/>通过 <input type="radio" name="borrow.status" value="2"  />不通过 </div>
				<#else>	
				<!--满标审核-->
				<input type="radio" checked="checked" name="borrow.status" value="3"/>通过 <input type="radio" name="borrow.status" value="4"  />不通过 </div>
				</#if>
			</td>
		</tr>
		<tr>
			<th>
				审核备注:
			</th>
			<td>
				<div class="c">
					<textarea id="verify_remark" name="borrow.verifyRemark" cols="45" rows="5" ></textarea>
				</div>
			</td>
		<tr>
		</table>
		</td>
		</tr>
			<!--满标审核-->
			<#else>
				<tr>
					<th>用户名: </th>
					<td>${borrow.user.username}</td>
					
					
				</tr>
				<tr>
						<th>保证金: </th>
						<td>${borrow.depositMoney}元</td>
					</tr>
					<tr>
						<th>手续费方式: </th>
						<td><#if borrow.feeType==''||borrow.feeType==0>${borrow.partAccount}%<#else>${borrow.feeMoney}元</#if></td>
					</tr>
				
				<tr>
					<th>标&nbsp;&nbsp;&nbsp;&nbsp;题:</th>
					<td>${borrow.name}</td>
					</tr>
				<tr>
					<th>标类型：</th>
					<td>		
						<#if borrow.type==0>秒标
						<#elseif borrow.type==1>天标
						<#elseif borrow.type==2>流转标
						<#elseif borrow.type==3>信用标
						<#elseif borrow.type==4>月标
						<#elseif borrow.type==11>天标
						<#elseif borrow.type==12>月标
						<#elseif borrow.type==13>流转标
						<#elseif borrow.type==14>天标
						<#elseif borrow.type==15>月标
						<#elseif borrow.type== 16>新手标
					    <#elseif borrow.type== 17>体验标
						<#else></#if>
					</td>
				</tr>
				<tr>
					<th>借款金额: </th>
					<td>${borrow.account}</td>
					</tr>
				<tr>
					<#if borrow.isday=='0'>
						<th>月利率:</th>
						<td>${borrow.apr}%</td>
					<#elseif borrow.isday=='1'>
						<th>天利率:</th>
						<td>${borrow.apr}‰</td>
					</#if>
				</tr>
				<tr>
					<th>借款期限: </th>
					<td>${borrow.timeLimit}<#if borrow.isday=='0'>个月<#elseif borrow.isday=='1'>天</#if></td>
					</tr>
				<tr>
				<#if borrow.isday=='0'>
					<th>续投奖励比例: </th>
					<td>
						${(borrow.continueAwardRate*1000)}‰
					</td>
				</#if>
				
				</tr>
				<tr>
					<th>奖&nbsp;&nbsp;&nbsp;&nbsp;励: </th>
					<td>
						<#if borrow.award==1> ${borrow.funds}%,满标发放
						<#elseif borrow.award==2>${borrow.funds}%,还款结束时发放
						<#else>无
						</#if>
					</td>
					</tr>
				<tr>
					<th>是否定向标: </th>
					<td>
						<#if borrow.isDxb==1> 是
						<#else>否
						</#if>
					</td>
				</tr>
			
		<tr>
		<td colspan="2">	
			<!--天标 增加还款计划-->
			<#if borrow.type==1>
			<div class="bar">还款计划</div>
			<table  width="80%" align="right">
				<tr><td>&nbsp;</td><td>期数</td><td>天数</td><td>还款本金</td><td>还款利息</td></tr>
				<#list retBorrow.paymentDetail as plan>
				<tr>
					<td>&nbsp;</td>
					<td>${plan_index+1}</td>
					<td>${plan.dateNum}</td>
					<td>${plan.benjinM}</td>
					<td>${plan.lixiM}</td>
				</tr>
				</#list>
			</table>
			</#if>
			<!--月标 增加还款计划-->
			<#if borrow.type==4>
			<div class="bar">还款计划</div>
			<table  width="80%" align="right">
				<tr><td>&nbsp;</td><td>期数</td><td>日期</td><td>还款本金</td><td>还款利息</td></tr>
				<#list retBorrow.paymentDetail as plan>
				<tr>
					<td>&nbsp;</td>
					<td>${plan_index+1}</td>
					<td>${plan.dateNum}</td>
					<td>${plan.benjinM}</td>
					<td>${plan.lixiM}</td>
				</tr>
				</#list>
			</table>
			</#if>
		</td>
		</tr>
		<tr>
		<td colspan="2">		
			<!--流转标 增加赎回计划-->
			<#if borrow.type==2>
			<div class="bar">赎回计划</div>
			<table  width="80%" align="right">
				<tr><td>&nbsp;</td><td>期数</td><td>回购日期</td></tr>
				<#list wanderRepayPlanDetail.wanderRepayPlanEach as plan>
				<tr>
					<td>&nbsp;</td>
					<td>${plan.issue}</td>
					<td>${plan.repayDateStr}</td>
				</tr>
				</#list>
			</table>
			</#if>
	</td>
		</tr>
		<tr>
		<td colspan="2">	
			

		<div class="bar">
			审核此借款标
		</div>
		<table >
				
		<tr>
			<th>
				状&nbsp;&nbsp;&nbsp;&nbsp;态:
			</th>
			<td>
				<#if (borrow.status)==0>
				<!--初审-->
				<input type="radio" checked="checked" name="borrow.status" value="1"/>通过 <input type="radio" name="borrow.status" value="2"  />不通过 </div>
				<#else>	
				<!--满标审核-->
				<input type="radio" checked="checked" name="borrow.status" value="3"/>通过 <input type="radio" name="borrow.status" value="4"  />不通过 </div>
				</#if>
			</td>
		</tr>
		
		<tr>
			<th>
				投资先锋奖励金额:
			</th>
			<td>
				${borrow.tzxf!'0'}&nbsp;元
			</td>
		<tr>
		
		<tr>
			<th>
				投资土豪奖励金额:
			</th>
			<td>
				${borrow.tzth!'0'}&nbsp;元
			</td>
		<tr>
		<tr>
			<th>
				收官大哥奖励金额:
			</th>
			<td>
				${borrow.sgdg!'0'}&nbsp;元
			</td>
		<tr>
		
		<tr>
			<th>
				审核备注:
			</th>
			<td>
				<div class="c">
					<textarea id="verify_remark" name="borrow.verifyRemark" cols="45" rows="5" ></textarea>
				</div>
			</td>
		<tr>
		</table>
		</#if>
	</td>
	</tr>
</table>
		<div class="buttonArea">
				<input type="button" class="formButton" value="审核" onclick="check_form();" hidefocus id="shenghe_button"/>&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>