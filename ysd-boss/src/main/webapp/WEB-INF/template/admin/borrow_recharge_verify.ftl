<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看标页面 </title>
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

function check_form(){
	 var frm = document.forms['validateForm'];
	 var verify_remark = frm.elements['verify_remark'].value;
	 var errorMsg = '';
	  if (verify_remark.length == 0 ) {
		errorMsg += '备注必须填写' + '\n';
	  }
	  
	  
	  if($("#borrowRecharge_type").val()==2 && !$("#borrowRecharge_status").attr('checked')) {
	  	errorMsg += '请选中状态' + '\n';
	  }
	  
	  if (errorMsg.length > 0){
		alert(errorMsg); return false;
	  } else{  
	  	$.dialog({type: "warn", content: "确定执行此操作吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
		
	  }
}

function right(){
	var frm = document.forms['validateForm'];
	frm.submit();
}

</script>
</head>
<body class="input">
	<div class="body">
		<form id="validateForm" action="${base}/admin/borrow_recharge!update.action" onsubmit="return check_form();"  method="post">
			<input type="hidden" id="borrowRecharge_type" value="${borrowRecharge.type}"/>
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="borrowId" value="${borrowRecharge.borrow.id}"/>
			<#if borrowRecharge.borrowRepayment?exists>
			<input type="hidden" name="borrowRepaymentDetailId" value="${borrowRecharge.borrowRepayment.id}"/>
			</#if>
			<div class="bar">详情</div>
			<table class="inputTable">
				<tr>
					<th>服务商: </th>
					<td>${borrowRecharge.agency.companyName}</td>
				</tr>
				<tr>
					<th>项目: </th>
					<td>${borrowRecharge.borrow.name}</td>
				</tr>
				<tr>
					<th>类型: </th>
					<td><#if borrowRecharge.type==1>还款充值
							<#elseif borrowRecharge.type==2>退还保证金
							</#if></td>
				</tr>
				<tr>
					<th>方式: </th>
					<td><#if borrowRecharge.rechargeType==1>转账<#elseif borrowRecharge.rechargeType==2>现金</#if></td>
				</tr>
				<tr>
					<th>金额: </th>
					<td>${borrowRecharge.money}</td>
				</tr>
				
				<tr>
					<th>
						状&nbsp;&nbsp;&nbsp;&nbsp;态:
					</th>
					<td>
						
						
						
						<#if borrowRecharge.type==1>
							<input type="radio" checked="checked" name="status" value="1"/>通过 <input type="radio" name="status" value="2"  />不通过 
						<#elseif borrowRecharge.type==2>
							<input type="checkbox" id="borrowRecharge_status" name="status" value="1"/>通过
						</#if>
						
					</td>
				</tr>
				<tr>
					<th>
						审核备注:
					</th>
					<td>
						<div class="c">
							<textarea id="verify_remark" name="borrowRecharge.verifyRemark" cols="45" rows="5" ></textarea>
						</div>
					</td>
				<tr>
				
			</table>
			
		 	
			<div class="buttonArea">
				<input type="button" class="formButton" value="审核" onclick="check_form();" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>