<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>资金账号管理 / 提现审核查看  </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">

var clickdate = new Array();
function check_form(){
	clickdate.push(new Date());
	if (clickdate.length > 1 && (clickdate[clickdate.length - 1].getTime() - clickdate[clickdate.length - 2].getTime() < 2000)){
		return false;
	}
	 var frm = document.forms['form1'];
	 var verify_remark = frm.elements['accountCash.verifyRemark'].value;
	 var changeRemark = frm.elements['accountCash.changeRemark'].value;
	 var fee = document.getElementById("fee").value;
	 var changeNum = document.getElementById("changeNum").value;
	 var cstatus = document.getElementById("cstatus").value;
	 var errorMsg = '';
	  if(fee<0){
	  	errorMsg += '您输入正确的费率' + '\n';
	  }else if(changeNum !=0){
		  if(cstatus!=4){
		  	if(changeRemark.length ==0){
		  		errorMsg += '请填写调整提现手续费原因' + '\n';
		  	}
		  }
	  }else if (verify_remark.length < 2 ) {
		errorMsg += '备注必须填写' + '\n';
	  }
	  if (errorMsg.length > 0){
		alert(errorMsg); return false;
	  } else{  
		return true;
	  }
	  
}

<!-- 计算扣费和实际到账金额!-->
function commit(obj)
{
		var cashAmount;
		cashAmount = parseFloat(obj.value);
		if(isNaN(cashAmount)){
			cashAmount=0;
		}
		
		if(parseFloat(document.getElementById("overfee").value)+parseFloat(cashAmount)>=0){
			document.getElementById("fee1").value=parseFloat(document.getElementById("overfee").value)+parseFloat(cashAmount);
			document.getElementById("fee").value=document.getElementById("fee1").value;
			getCashFeeValue();
			
		}else{
			alert("请输入正确的调整值");
			document.getElementById("changeNum").value=0;
			return false;
		}
	
}

function getCashFeeValue(){

	var credited = document.getElementById("credited").value;
	var total = document.getElementById("total").value;
	var fee = document.getElementById("fee").value;
	var cashFee;
   	
 cashFee =parseFloat(total)-parseFloat(fee);
 document.getElementById("credited").value=cashFee;
 document.getElementById("credited1").value=cashFee;
 document.getElementById("changeRemark").focus();
}


$().ready( function() {
	$reviewInfo = $("#reviewInfo");
	$reviewInfo.click(function(){
		
		$.ajax({
			url: "${base}/admin/temporary!ajaxReview.action",
			data: {"user.username":'${accountCash.user.username}'},
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				
				if(data.status=="success"){
					$.message({type: data.status, content: data.message});
				}else{
					$.dialog({type: "warn",title:"异常订单号:", content: data.message, cancel: "关 闭", modal: true});
				}
			}
		});
	});

});
</script>
</head>
<body class="input">
	<div class="bar">
	资金账号管理 /
	<#if (accountCash.status)==0>
		<#if (accountCash.user.typeId)==0>
		 	投资人提现记录审核 
		<#else> 
			借款人提现记录审核
		</#if>
	<#else>
		 提现记录查看
	</#if>
	</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" name="form1"  action="account_cash!update.action" onsubmit="return check_form();"  method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="accountCash.total" id="total" value="${accountCash.total}" />
			<input type="hidden" name="accountCash.credited" id="credited" value="${accountCash.credited}" />
			<input type="hidden" name="accountCash.fee" id="fee" value="${accountCash.fee}" />
			<input type="hidden" name="cstatus" id="cstatus" value="${accountCash.status}" />
			<#if accountCash.status==4>
			<input type="hidden" name="accountCash.changeNum" id="changeNum" value="${accountCash.changeNum}" />
			<input type="hidden" name="accountCash.changeRemark" id="changeRemark" value="${accountCash.changeRemark}" />
			<input type="hidden" name="accountCash.verifyRemark" id="verifyRemark" value="${accountCash.verifyRemark}" />
			</#if>
			<input type="hidden" name="overfee" id="overfee" value="${accountCash.fee}" />
			<input type="hidden" name="accountCash.user.id" value="${accountCash.user.id}" />
			<table class="inputTable tabContent">
				
				<tr>
					<th>
						用户名: 
					</th>
					<td>
							${accountCash.user.username}
					</td>
				</tr>
				<tr>
					<th>
						账户总额: 
					</th>
					<td>
						${accountCash.user.account.total?string.currency}元
					</td>
				</tr>
				<#if (accountCash.user.typeId)==1>
					<tr>
					<th>
						可用总额: 
					</th>
					<td>
						${accountCash.user.account.ableMoney}元
					</td>
				</tr>
				</#if>
				<tr>
					<th>
						提现银行: 
					</th>
					<td>
						${bankName}
					</td>
				</tr>
				<tr>
					<th>
						提现支行: 
					</th>
					<td>
						${accountCash.branch}
					</td>
				</tr>
				<tr>
					<th>
						提现账号: 
					</th>
					<td>
						${accountCash.account}
					</td>
				</tr>
				<tr>
					<th>
						提现总额: 
					</th>
					<td>
						${accountCash.total?string.currency}元
					</td>
				</tr>
				<tr>
					<th>
						到账金额: 
					</th>
					<td>
						${accountCash.credited?string.currency}元
					</td>
				</tr>
				<tr>
					<th>
						费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;率:
					</th>
					<td>
						${accountCash.fee?string.currency}元
					</td>
				</tr>
				<#--
				<tr>
					<th>
						可提现金额: 
					</th>
					<td>
						${accountCash.ableCashMoney?string.currency}元
					</td>
				</tr>->
<#--				<tr>
					<th>
						免费提现总额: 
					</th>
					<td>
						${accountCash.freeCashMoney?string.currency}元
					</td>
				</tr>
-->
				<#if accountCash.status==1 >
					<tr>
					<th>
						调整金额:
					</th>
					<td>
						${accountCash.changeNum?string.currency}元
					</td>
				</tr>
				<tr>
					<th>
						调整原因:
					</th>
					<td>
						${accountCash.changeRemark}
					</td>
				</tr>
				</#if>
				<tr>
					<th>
						状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态: 
					</th>
					<td>
						<#if (accountCash.status)==0>
							审核中
						<#elseif (accountCash.status)==1>
							已通过 
						<#elseif (accountCash.status)==4>
							处理中
						<#else>
							被拒绝
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						添加时间/IP: 
					</th>
					<td>
					  <#if !accountCash.createDate>
							${accountCash.createDate}/${accountCash.addip}
						<#else>
							${accountCash.createDate?string("yyyy-MM-dd")}/${accountCash.addip}
						</#if>	
						
					</td>
				</tr>
					
		</table>
		
		<div class="bar">
		<#if (accountCash.status)==0 || (accountCash.status)==4 >
			<#-->审核此提现信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="formButton" value="提现审核" id="reviewInfo" hidefocus />&nbsp;&nbsp;-->
		</div>
		
	<table class="inputTable tabContent">
				
		<tr>
			<th>
				状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态: 
			</th>
			<td>
	    		<#if (accountCash.status)==0 >
<#--	    		<input type="radio" name="accountCash.status" checked="checked" value="4" />处理中<input type="radio" name="accountCash.status"  value="1" />提现审核成功 
	    		<#else>
	    		-->
	    		<input type="radio" name="accountCash.status" checked="checked" value="1" />提现审核成功 
	    		</#if>
	    		<input type="radio" name="accountCash.status" value="2" />审核不通过</div>
	    </td></tr>

	<tr>
			<th>
				到账金额:
			</th>
			<td>
	    		<input type="text" name="credited1" id="credited1" value="${accountCash.credited}" disabled size="10" >元,&nbsp;&nbsp;费率：<input type="text" name="fee1"  id="fee1" value="${accountCash.fee}" disabled size="5"  >元（一旦审核通过将不可再进行修改）
	    </td></tr>
	   <tr>
	   <tr>
			<th>
				调整金额:
			</th>
			<td>
			<#if accountCash.status==4>
			<input type="text" name="accountCash.changeNum"  id="changeNum" value="${accountCash.changeNum}" size="5" onblur="commit(this);" disabled>元;
			&nbsp;&nbsp;调整原因:<textarea name="accountCash.changeRemark" id="changeRemark" cols="20" rows="2" disabled><#if accountCash.changeRemark !>${accountCash.changeRemark}</#if> </textarea>
			<#else>
			<input type="text" name="accountCash.changeNum"  id="changeNum" value="${accountCash.changeNum}" size="5" onblur="commit(this);" onKeyPress="if (event.keyCode!=46 && event.keyCode!=45 && (event.keyCode<48 || event.keyCode>57)) event.returnValue=false">元;
			&nbsp;&nbsp;调整原因:<textarea name="accountCash.changeRemark" id="changeRemark" cols="20" rows="2"><#if accountCash.changeRemark !>${accountCash.changeRemark}</#if> </textarea>
			</#if>
		</td></tr>
		<tr>
			<th>
		 		审核备注:</th>
			<td>
			<#if accountCash.status==4>
				<textarea name="verifyRemark" id="verifyRemark1" cols="45" rows="5" disabled ><#if accountCash.verifyRemark !>${accountCash.verifyRemark}</#if> </textarea>
			<#else>
				<textarea name="accountCash.verifyRemark" id="verifyRemark" cols="45" rows="5" ><#if accountCash.verifyRemark !>${accountCash.verifyRemark}</#if> </textarea>
			</#if>
		</td></tr>
	</table>
	
	<div class="buttonArea">
		<input type="submit" class="formButton" value="审核此提现" hidefocus />&nbsp;&nbsp;
		<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
	</div>
	<#else>
	审核信息：审核人：${admin.username};审核时间：${accountCash.verifyTime};审核备注：${accountCash.verifyRemark}
		</div>
	<div class="buttonArea">
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
	</div>
	</#if>	
	</form>
	</div>
</body>
</html>