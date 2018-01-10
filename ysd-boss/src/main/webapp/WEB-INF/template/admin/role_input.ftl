<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑角色</title>
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
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $allChecked = $("#validateForm .allChecked");
	
	$allChecked.click( function() {
		var $this = $(this);
		var $thisCheckbox = $this.parent().parent().find(":checkbox");
		if ($thisCheckbox.filter(":checked").size() > 0) {
			$thisCheckbox.attr("checked", false);
		} else {
			$thisCheckbox.attr("checked", true);
		}
		return false;
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"role.name": "required"
		},
		messages: {
			"role.name": "请填写角色名称"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("roleAuthorityListRequired", $.validator.methods.required, "请选择管理权限");
	
	$.validator.addClassRules("roleAuthorityList", {
		roleAuthorityListRequired: true
	});
	
})
</script>
</head>
<body class="input role">
	<div class="bar">
		<#if isAddAction>添加角色<#else>编辑角色</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>role!save.action<#else>role!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						角色名称: 
					</th>
					<td>
						<input type="text" name="role.name" class="formText" value="${(role.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述: 
					</th>
					<td>
						<textarea name="role.description" class="formTextarea">${(role.description)!}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						
					</th>
					<td colspan="1">
						全选/取消<input type="checkbox" class="allRoleCheck" checked/>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">资金管理: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_ZJGK"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_ZJGK"))!> checked</#if> />资金概况
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_ZJMX"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_ZJMX"))!> checked</#if> />资金明细
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_TZRDSMX"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_TZRDSMX"))!> checked</#if> />投资人代收明细
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_JLMX"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_JLMX"))!> checked</#if> />奖励明细
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_TRZRTXSH"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_TRZRTXSH"))!> checked</#if> />投资人提现审核
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_SYTXJL"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_SYTXJL"))!> checked</#if> />所有提现记录
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_SYCZJL"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_SYCZJL"))!> checked</#if> />所有充值记录
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_CZSH"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_CZSH"))!> checked</#if> />充值审核
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_XXCZ"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_XXCZ"))!> checked</#if> />线下充值
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_KCYE"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_KCYE"))!> checked</#if> />扣除余额
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_KFSH"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_KFSH"))!> checked</#if> />扣费审核
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_HBFF"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_HBFF"))!> checked</#if> />红包单放
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_HBPL"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_HBPL"))!> checked</#if> />红包批量
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_HBMX"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_HBMX"))!> checked</#if> />红包明细
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_ACCOUNT_XTHBGL"<#if (isAddAction || role.authorityList.contains("ROLE_ACCOUNT_XTHBGL"))!> checked</#if> />系统红包管理
						</label>
						
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">项目管理: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BORROW_XMCS"<#if (isAddAction || role.authorityList.contains("ROLE_BORROW_XMCS"))!> checked</#if> />项目初审
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BORROW_MEFS"<#if (isAddAction || role.authorityList.contains("ROLE_BORROW_MEFS"))!> checked</#if> />满额复审
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BORROW_SYXMLB"<#if (isAddAction || role.authorityList.contains("ROLE_BORROW_SYXMLB"))!> checked</#if> />所有项目列表
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BORROW_JKRFKSH"<#if (isAddAction || role.authorityList.contains("ROLE_BORROW_JKRFKSH"))!> checked</#if> />借款人放款审核
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BORROW_FKLB"<#if (isAddAction || role.authorityList.contains("ROLE_BORROW_FKLB"))!> checked</#if> />放款列表
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BORROW_HKCZSH"<#if (isAddAction || role.authorityList.contains("ROLE_BORROW_HKCZSH"))!> checked</#if> />还款充值审核
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BORROW_XMHKJL"<#if (isAddAction || role.authorityList.contains("ROLE_BORROW_XMHKJL"))!> checked</#if> />项目回款记录
						</label>
					</td>
				</tr>
				
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">内容管理: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_GGTPLB"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_GGTPLB"))!> checked</#if> />广告图片轮播
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_WZFL"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_WZFL"))!> checked</#if> />文章分类
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_WZGL"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_WZGL"))!> checked</#if> />文章管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_PTHDGL"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_PTHDGL"))!> checked</#if> />平台活动管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_HTYMGX"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_HTYMGX"))!> checked</#if> />后台页面更新
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_FEEDBACK"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_FEEDBACK"))!> checked</#if> />意见反馈管理
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_QDGL"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_QDGL"))!> checked</#if> />渠道管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_LYTJ"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_LYTJ"))!> checked</#if> />路演（日常）统计
						</label>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">会员管理: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_USER_HYLB"<#if (isAddAction || role.authorityList.contains("ROLE_USER_HYLB"))!> checked</#if> />会员列表
						</label>						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_USER_HYXXBJ"<#if (isAddAction || role.authorityList.contains("ROLE_USER_HYXXBJ"))!> checked</#if> />会员信息编辑
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_USER_YHKGL"<#if (isAddAction || role.authorityList.contains("ROLE_USER_YHKGL"))!> checked</#if> />银行卡管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_YQHYXX"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_YQHYXX"))!> checked</#if> />邀请好友信息
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_YQJLTJ"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_YQJLTJ"))!> checked</#if> />邀请奖励统计
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_SCTZTJ"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_SCTZTJ"))!> checked</#if> />首次投资统计
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HTML_YHTZJL"<#if (isAddAction || role.authorityList.contains("ROLE_HTML_YHTZJL"))!> checked</#if> />用户投资记录
						</label>
						
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_USER_XZFWS"<#if (isAddAction || role.authorityList.contains("ROLE_USER_XZFWS"))!> checked</#if> />新增服务商
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_USER_FWSSH"<#if (isAddAction || role.authorityList.contains("ROLE_USER_FWSSH"))!> checked</#if> />服务商审核
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_USER_FWSLB"<#if (isAddAction || role.authorityList.contains("ROLE_USER_FWSLB"))!> checked</#if> />服务商列表
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_USER_JKRLB"<#if (isAddAction || role.authorityList.contains("ROLE_USER_JKRLB"))!> checked</#if> />借款人列表
						</label>
					</td>
				</tr>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">系统设置: </a>
					</th>
					<td>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_SETTING_XTSZ"<#if (isAddAction || role.authorityList.contains("ROLE_SETTING_XTSZ"))!> checked</#if> />系统设置
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_SETTING_WZCSGL"<#if (isAddAction || role.authorityList.contains("ROLE_SETTING_WZCSGL"))!> checked</#if> />网站参数管理
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_SETTING_XSZFTD"<#if (isAddAction || role.authorityList.contains("ROLE_SETTING_XSZFTD"))!> checked</#if> />线上支付通道
						</label>
						
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_SETTING_GLYGL"<#if (isAddAction || role.authorityList.contains("ROLE_SETTING_GLYGL"))!> checked</#if> />管理员列表
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_SETTING_JSGL"<#if (isAddAction || role.authorityList.contains("ROLE_SETTING_JSGL"))!> checked</#if> />角色管理
						</label>
					</td>
				</tr>
			
				<#if (role.isSystem)!false>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>系统提示: </b>系统内置角色不允许修改!</span>
						</td>
					</tr>
				</#if>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
<script>
$().ready( function() {
		var $allRoleCheck = $(".inputTable input.allRoleCheck");// 全选复选框
		var $authorityListCheck = $(".inputTable input[name='role.authorityList']");// ID复选框
		
		// 全选
		$allRoleCheck.click( function() {
			var $this = $(this);
			if ($this.attr("checked")) {
				$authorityListCheck.attr("checked", true);
				
			} else {
				$authorityListCheck.attr("checked", false);
				
			}
		});
});
</script>
</html>