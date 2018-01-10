<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑对接服务商 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {
	var $tab = $("#tab");
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	var $areaSelect = $("#areaSelect");
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/admin/area!ajaxArea.action"// AJAX数据获取url
	});
	
	
	var $fileListTable = $("#fileListTable");

	var fileListIndex = 0;
	var $addButton = $("#addButton");
	// 增加上传图片
	$addButton.click( function() {
		<@compress single_line = true>
			var fileTrHtml = 
			'<tr>
				<td><input type = "text" name = "fileName"> </td>
					<td><input type="file" name="fileList" /></td>
					&nbsp;&nbsp;
					<td>
						<input type = "button" class = "up_move" value = "上移">
						<input type = "button" class = "down_move" value = "下移">
						<span class="deleteIcon deleteFileIcon" title="删 除">&nbsp;</span>
					</td>
			</tr>';
		</@compress>
		
		$fileListTable.append(fileTrHtml);
		fileListIndex ++;
	});
	
	// 删除上传图片
	$("#fileListTable .deleteFileIcon").live("click", function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteFile});
		function deleteFile() {
			$this.parent().parent().remove();
		}
	});
	
	// 上移图片
	$("#fileListTable .up_move").live("click", function() {
		var $this = $(this);
		var obj = $this.parent().parent();
		var prev = obj.prev();
		prev.before(obj);
	});
	
	// 下移图片
	$("#fileListTable .down_move").live("click", function() {
		var $this = $(this);
		var obj = $this.parent().parent();
		var next = obj.next();
		next.after(obj);
	});
	
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");

	// 表单验证
	<#-->$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"user.email": {
				email: true,
				remote: "user!checkEmail.action?id=${user.id}"
			},
			"user.phone": {
				phone: true,
				remote: "user!checkPhone.action?id=${user.id}"
			},
			"agency.capital":{
				digits: true
			}
		},
		messages: {
			"user.email": {
				email: "E-mail格式不正确",
				remote:"E-mail已存在"
			},
			"user.phone": {
				phone: "手机号格式不正确",
				remote: "手机号已存在"
			},
			"agency.capital":{
				digits: "注册资本为正数"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});-->

});
</script>
</head>
<body class="input admin">
	<div class="bar">
		修改对接服务商【${user.username}】的信息
	</div>
	<div class="body">
	
		<div id="validateErrorContainer" class="validateErrorContainer">
			<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
			<ul></ul>
		</div>
		
		<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
				<li>
					<input type="button" value="工商注册信息" hidefocus />
				</li>
				<li>
					<input type="button" value="网站账号信息" hidefocus />
				</li>
				<li>
					<input type="button" value="子页面管理" hidefocus />
				</li>
			</ul>
		<form id="validateForm" class="form" action="agency!update.action" enctype="multipart/form-data" method="post">
			<input type = "hidden" name = "id" value = "${user.id}">
			<table class="inputTable tabContent">
				<tr>
					<th>
						对接服务商名称:
					</th>
					<td>
						<input type="text" name="agency.companyName" maxlength="100" class="formText" value="${(agency.companyName)!}" />
						<#--&nbsp;&nbsp;可否发布：&nbsp;
						&nbsp;<label><input type="checkbox" name="agency.flowRule" value="1" <#if agency.flowRule ==1>checked</#if>/>债权流转</label>
						&nbsp;<label><input type="checkbox" name="agency.pledgeRule" value="1" <#if agency.pledgeRule ==1>checked</#if>/>抵押质押</label>
						&nbsp;<label><input type="checkbox" name="agency.creditRule" value="1" <#if agency.creditRule ==1>checked</#if>/>信用</label>
						&nbsp;<label><input type="checkbox" name="agency.tasteRule" value="1" <#if agency.tasteRule ==1>checked</#if>/>体验</label>
						-->
					</td>
				</tr>
				<tr>
					<th>
						所在地区:
					</th>
					<td>
						  <input type="text" id="areaSelect" name="areaId" class="hidden" value="<#if user.area!>${user.area}<#elseif user.city!>${user.city}<#else>${user.province}</#if>" defaultSelectedPath="${user.province!},${user.city!},${user.area!}" /></td>
					</td>
				</tr>
				<tr>
					<th>
						联系电话:
					</th>
					<td>
						<input type="text" name="user.phone" class="formText" maxlength="50" value="${(user.phone)!}" />
					</td>
				</tr>
				<tr>
					<th>
						联系地址:
					</th>
					<td>
						<input type="text" name="user.address" maxlength="100" class="formText" style="width:375px" value="${(user.address)!}" />
					</td>
				</tr>
				<tr>
					<th>
						电子邮箱:
					</th>
					<td>
						<input type="text" name="user.email" class="formText" maxlength="100" value="${(user.email)!}" />
					</td>
				</tr>
				<tr>
					<th>
						联系人姓名:
					</th>
					<td>
						<input type="text" name="agency.linkman" class="formText" maxlength="50" value="${(agency.linkman)!}" />
					</td>
				</tr>
				<tr>
					<th>
						联系人手机:
					</th>
					<td>
						<input type="text" name="agency.linkmanMobile" class="formText"  maxlength="11" value="${(agency.linkmanMobile)!}" />
					</td>
				</tr>
				<tr>
					<th>
						注册赠送体验金：
					</th>
					<td>
						<input type = "text" name = "tasteMoney" value = "${(agency.tasteMoney)!}" maxlength = "8" onkeyup="value=value.replace(/[^0-9\.]/g,'')">
					</td>
				</tr>
				<tr>
					<th>
						主营业务:
					</th>
					<td>
						<textarea type="text" name="agency.mainBusiness" class="formTextarea">${(agency.mainBusiness)!}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						备注:
					</th>
					<td>
						<textarea type="text" name="agency.remark" class="formTextarea">${(agency.remark)!}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="agency.orderList" class="formText" maxlength="5" value="${(agency.orderList)!}" />
					</td>
				</tr>
			</table>
			
			<table class="inputTable tabContent">
				<tr>
					<th>
						法人姓名:
					</th>
					<td>
						<input type="text" name="user.realName" class="formText"  maxlength="50" value="${(user.realName)!}" />
					</td>
				</tr>
				<tr>
					<th>
						法人身份证上传:
					</th>
					<td>
						<input type="file" name="cardPic1"  class="fileListClass"/><#if user.cardPic1!><a href="${imgUrl}${user.cardPic1}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<#--<tr>
					<th>
						法人身份证反面:
					</th>
					<td>
						<input type="file" name="cardPic2"  class="fileListClass"/><#if  user.cardPic2!><a href="${imgUrl}${user.cardPic2}" target="_blank">查看</a></#if>
					</td>
				</tr>-->
				<tr>
					<th>
						营业执照号码:
					</th>
					<td>
						<input type="text" name="agency.privateCharter" class="formText" maxlength="50" value="${(agency.privateCharter)!}" />
					</td>
				</tr>
				<tr>
					<th>
						营业执照上传:
					</th>
					<td>
						<input type="file" name="privateCharterImg"  class="fileListClass"/><#if  agency.privateCharterImg!><a href="${imgUrl}${agency.privateCharterImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						税务登记证号:
					</th>
					<td>
						<input type="text" name="agency.taxRegistration" class="formText"  maxlength="50" value="${(agency.taxRegistration)!}" />
					</td>
				</tr>
				<tr>
					<th>
						税务登记证上传:
					</th>
					<td>
						<input type="file" name="privateTaxImg"  class="fileListClass"/><#if  agency.privateTaxImg!><a href="${imgUrl}${agency.privateTaxImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				
				<tr>
					<th>
						机构代码证号:
					</th>
					<td>
						<input type="text" name="agency.organization" class="formText" maxlength="50" value="${(agency.organization)!}" />
					</td>
				</tr>
				<tr>
					<th>
						机构代码证上传:
					</th>
					<td>
						<input type="file" name="privateOrganizationImg"  class="fileListClass"/><#if  agency.privateOrganizationImg!><a href="${imgUrl}${agency.privateOrganizationImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						开户许可证上传:
					</th>
					<td>
						<input type="file" name="accountLicenceImg"  class="fileListClass"/><#if  agency.accountLicenceImg!><a href="${imgUrl}${agency.accountLicenceImg}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						合同签章上传:
					</th>
					<td>
						<input type="file" name="signImage"  class="fileListClass"/><#if  agency.signImage!><a href="${imgUrl}${agency.signImage}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						成立日期:
					</th>
					<td>
						<input type="text" name="agency.establishDate" class="formText" value="${(agency.establishDate?string("yyyy-MM-dd"))!}" onclick="WdatePicker()"  style="width:75px"/>
					</td>
				</tr>
				
				<tr>
					<th>
						营业期限:
					</th>
					<td><input type="text" name="agency.businessStart" class="formText" value="${(agency.businessStart?string("yyyy-MM-dd"))!}" onclick="WdatePicker()"  style="width:75px"/> - 
					    <input type="text" name="agency.businessEnd" class="formText" value="${(agency.businessEnd?string("yyyy-MM-dd"))!}" onclick="WdatePicker()"  style="width:75px"/>
					</td>
				</tr>
				<tr>
					<th>
						注册地址:
					</th>
					<td>
						<input type="text" name="agency.address" class="formText"  maxlength="100" value="${(agency.address)!}"  autocomplete="off"/>
					</td>
				</tr>
				<tr>
					<th>
						注册资本:
					</th>
					<td>
						<input type="text" name="agency.capital" class="formText" maxlength="20" value="${(agency.capital)!}"  autocomplete="off"/>
					</td>
				</tr>
				<tr>
					<th>
						经营范围:
					</th>
					<td>
						<textarea name="agency.scope" class="formTextarea" >${(agency.scope)!}</textarea>
					</td>
				</tr>
				
			</table>
			<table class="inputTable tabContent">
				<tr>
					<th>
						服务商登录名:
					</th>
					<td>
						<input type="username" name="user.username" id = "username" maxlength="50" class="formText" value="${user.username}" />
					</td>
				</tr>
				<tr>
					<th>
						登录密码:
					</th>
					<td>
						<input type="password" name="user.password" id = "upassword" maxlength="50" class="formText" value="" title="留空则不修改"/>
						<div class="blank"></div>
					</td>
				</tr>
				<!--
				<tr>
					<th>
						重复密码:
					</th>
					<td>
						<input type="password" id ="reupasswordId" name="reupassword"  maxlength="50" class="formText" value="" title="留空则不修改"/>
						<div class="blank"></div>
					</td>
				</tr>
				-->
			</table>
			
			<table class="inputTable tabContent" >
				<tr>
					<th>
						公司LOGO:
					</th>
					<td>
						<input type="file" name="img"  class="fileListClass"/><#if agency.logo!><a href="${imgUrl}${agency.logo}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						顶部广告图:
					</th>
					<td>
						<input type="file" name="img1"  class="fileListClass"/><#if agency.logo1!><a href="${imgUrl}${agency.logo1}" target="_blank">查看</a></#if>
					</td>
				</tr>
				<tr>
					<th>
						公司介绍:
					</th>
					<td>
						<textarea name="agency.introduction" class="formTextarea" >${(agency.introduction)!}</textarea>
					</td>
				</tr>
				
				<tr>
					<th>
						联系电话:
					</th>
					<td>
						<input type="text" name="agency.subpagePhone" class="formText" value="${(agency.subpagePhone)!}" />
					</td>
				</tr>
				<tr>
					<th>
						联系地址:
					</th>
					<td>
						<input type="text" name="agency.subpageAddress" class="formText" value="${(agency.subpageAddress)!}" />
					</td>
				</tr>
				
				<tr>
					<th>
						其它宣传图:
					</th>
					<td>
						<input type="button" id="addButton" class="formButton" value="添加图片" hidefocus />&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<th>
						
					</th>
					<td>
						<table>
							<tr>
								<td>标题</td>
								<td style = "width:225px;">图片</td>
								<td>操作</td>
							</tr>
							<tbody id="fileListTable">
								<#list voucherImgList as vil>
								<tr>
									<td><input type = "text" name = "imgName[${vil_index}]" value = "${vil.name}"> &nbsp;&nbsp;&nbsp;</td>
									<td>
										<input type = "hidden" name = "imgValue[${vil_index}]" value = "${vil.url}">
										<a href = "${imgUrl}${vil.url}" target = "_blank" title = "点击查看大图"><img src = "${imgUrl}${vil.url}" width = "100" height = "75"/>查看</a>
									</td>
									<td><#--><input type = "button" class = "up_move" value = "上移">
										<input type = "button" class = "down_move" value = "下移">-->
										<span class="deleteIcon deleteFileIcon" title="删 除">&nbsp;</span>
									</td>
								</tr>
								</#list>
							</tbody>
						</table>
					</td>
				</tr>
					<#--<#list voucherImgList as vil>
						<tr>
							<th>
							</th>
							<td>标题：<input type = "text" name = "imgName[${vil_index}]" value = "${vil.name}"> &nbsp;&nbsp;&nbsp;
								<input type = "hidden" name = "imgValue[${vil_index}]" value = "${vil.url}">
								<a href = "${imgUrl}${vil.url}" target = "_blank" title = "点击查看大图"><img src = "${imgUrl}${vil.url}" width = "100" height = "75"/></a>
								<span class="deleteIcon deleteFileIcon" title="删 除">&nbsp;</span>
							</td>
						</tr>
					</#list>-->
			</table>
			
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>