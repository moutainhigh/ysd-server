<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑管理员 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $tab = $("#tab");

	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"fangkuan.verifyRemark": {
				required: true
			},
			"fangkuan.status":{
				required: true
			}
		},
		messages: {
			"fangkuan.verifyRemark": {
				required: "请输入备注信息"
			},
			"fangkuan.status":{
				required: "请选择审核状态"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
</script>
</head>
<body class="input admin">
	<div class="bar">
		放款审核
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="fangkuan!verify.action" method="post">
			<input type="hidden" name="fangkuan.id" value="${fangkuan.id}" />
			<input type="hidden" name="fangkuan.type" value="${fangkuan.type}" />
			
			<table class="inputTable tabContent">
				<tr>
					<th>
						放款申请时间: 
					</th>
					<td>
						${fangkuan.createDate?string("yyyy-MM-dd HH:mm:ss")}
					</td>
				</tr>
				<tr>
					<th>
						放款项目: 
					</th>
					<td>
						${fangkuan.borrow.name}
					</td>
				</tr>
				<tr>
					<th>
						审核类型: 
					</th>
					<td>
						${fangkuan.showType}
					</td>
				</tr>
				<tr>
					<th>
						借款人: 
					</th>
					<td>
						${fangkuan.user.realName}
					</td>
				</tr>
				<tr>
					<th>
						放款金额: 
					</th>
					<td>
						￥${(fangkuan.fangkuanMoney)!'0'}
					</td>
				</tr>
				<tr>
					<th>
						放款账号: 
					</th>
					<td>
						${(fangkuan.bankCard)!}
					</td>
				</tr>
				<tr>
					<th>
						开户银行: 
					</th>
					<td>
						${(fangkuan.bankBranch)!}
					</td>
				</tr>
				<tr>
					<th>
						状&nbsp;&nbsp;&nbsp;&nbsp;态:
					</th>
					<td>
					<#-->
						<input type="radio" checked="checked" name="fangkuan.status" value="1"/>通过
						<input type="radio" name="fangkuan.status" value="0"  />不通过 </div>
					-->	
					<label>	<input type="checkbox" name = "fangkuan.status" value="1"/>审核通过</label>
					</td>
				</tr>
				<tr>
					<th>
						审核备注:
					</th>
					<td>
						<div class="c">
							<textarea id="verify_remark" name="fangkuan.verifyRemark" cols="45" rows="5" ></textarea>
						</div>
					</td>
				<tr>
				
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>