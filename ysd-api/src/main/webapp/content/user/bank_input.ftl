<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户-银行账户</title>
	<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">


<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
     <#include "/content/common/user_center_header.ftl">
    
    <div style="width:955px; float:left; background:#fff; clear:both;">
        <#include "/content/common/user_center_left.ftl">
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<a style="color:#646464;font-family:'宋体';">${Application ["qmd.setting.name"]}</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" >银行账户</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            <a class="hr hre">银行账户</a>
          </div>
          <div style=" font-size:16px; color:#64664;font-family:'宋体'; padding-top:25px; margin-bottom:5px; clear:both;">银行账户</div>
            <div style=" margin-top:10px;">
                <table width="100%" cellpadding="0" cellspacing="0"  style="border:1px solid #c6c6c6; border-right:none; border-bottom:none;">
					<thead bgcolor="#efebdf" align="center">
						<tr height="39">
					 		 <th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">银行名称</th>
							 <th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">银行卡号</th>
							 <th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">开户名</th>
							 <th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">支行名称</th>
							 <th style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6; font-size:14px;">操作</th>
						</tr>
					</thead>
					<tbody align="center">

							<#list accountBankList as accountBank>
							   <tr height="50">
								   <td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">
								   		<@listing_childname sign="account_bank" key_value="${accountBank.bankId}"; accountBankName>
											${accountBankName}
										</@listing_childname>
								   </td>
								   <td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">****${accountBank.account?substring(4,accountBank.account?length-4)} ****</td>
								   <!--<td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${substring(loginUser.realName, 1, "***")}</td>-->
								   <td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${loginUser.showRealName}</td>
								   <td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;">${accountBank.branch}</td>
								   <td style="color:#646464;font-family:'宋体'; border-right:1px solid #c6c6c6;border-bottom:1px solid #c6c6c6;"> <a href="javascript:void(0);" class="deleteBank" bankid="${accountBank.id}">删除</a>
								   </td>
								</tr>
							</#list>
					</tbody>
				</table>              
            </div>
          <div style=" font-size:14px; color:#f74405;font-family:'宋体'; padding-top:25px; margin-top:10px; clear:both; text-align:right;"><a>+ 添加银行新账户</a></div>
           <div style="height:23px;line-height:23px;text-align:center;color:#595757;font-family:'宋体';background:#efebdf;margin-top:10px; margin-bottom:30px;">
				 温馨提示：银行账户的开户人姓名必须为"<font style="color:#f74405; font-weight:bold;">${substring(loginUser.realName, 1, "***")}**</font>"，否则将导致提现失败
				 
			</div>  
			<form id="bankInputForm" method="post" action="bankInput.do">
	<table width="100%" cellpadding="0" cellspacing="0">
	   <tbody>
         <tr>
			<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">开户名：</td>
			<td style="color:#646464;font-family:'宋体';">${loginUser.showRealName}</td>
		</tr>  <tr height="10"><td></td><td></td></tr>
		<tr>
			<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">开户银行：</td>
			<td style="color:#646464;font-family:'宋体';">
              <select id="bankid" class="base_sel" name="accountBank.bankId"  style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
                <@listing_childlist sign="account_bank"; listingList>
					<#list listingList as listing>
						<option value="${listing.keyValue}" <#if (listing.keyValue == accountBank.bankId)!> selected</#if>>${substring(listing.name, 24, "...")}</a>
						</option>
					</#list>
				</@listing_childlist>
			  </select>
            </td>
		  </tr>  <tr height="10"><td></td><td></td></tr>
		<tr>
			<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">银行卡号：</td>
			<td style="color:#646464;font-family:'宋体';"><input name="accountBank.account" id="account" value="${(accountBank.account)!}" size = "32" type="text" style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></td>
		</tr>  <tr height="10"><td></td><td></td></tr>
		<tr>
			<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">开户行：</td>
			<td style="color:#646464;font-family:'宋体';"><input id="branch" name="accountBank.branch" value="${(accountBank.branch)!}"size = "32" type="text" style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></td>
		</tr>  <tr height="10"><td></td><td></td></tr>
		  <tr>
			<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">安全密码：</td>
			<td style="color:#646464;font-family:'宋体';">
				<#if loginUser.payPassword!>
	      			<input name="user.payPassword" id="payPassword" type="password" style="width:200px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
		      	<#else>
		      		<a href="${base}/userCenter/toPayPassword.do" >请先设置一个安全密码</a>
		      		<input type="hidden"  name="user.payPassword">
		      	</#if>
			</td>
		 </tr>  <tr height="10"><td></td><td></td></tr>
		 <tr>
			<td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"></td>
			<td style="color:#646464;font-family:'宋体';"><input type="submit" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value="确定"/></td>
			<#--<input type="submit" class="quxiao" value="取消"/>-->
		</tr>  <tr height="10"><td></td><td></td></tr>					
	   </tbody>
     </table>	
     </form>
        </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->



<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript" src="${base}/static/js/common/bankInput.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
    <script type="text/javascript">
		$().ready( function() {
			$("#bank_account_left").addClass("current");
			var $bankInputForm = $("#bankInputForm");
			$("#account").bankInput();
			
			$deleteBank = $(".deleteBank");
			$deleteBank.click(function(){
				$this=$(this);
				if (confirm("确认删除此条记录吗？")) {
					$.ajax({
							url: "${base}/userCenter/ajaxBankDelete.do",
							data: {"accountBank.id":$this.attr("bankid")},
							type: "POST",
							dataType: "json",
							cache: false,
							success: function(data) {
								$.message({type: data.status, content: data.message});
								if(data.status=="success"){
									$this.parent().parent().remove();
								}
							}
					});
				}
			
			});
			
			 // 表单验证
			$bankInputForm.validate({
				rules: {
					"accountBank.bankId":{
						required: true
					},
					"accountBank.account":{
						required: true,
						minlength: 12,
						maxlength:26
					},
					"accountBank.branch":{
						required: true,
						minlength: 5,
						maxlength: 50
					},
					"user.payPassword":{
						required:true,
						remote:"${base}/userCenter/ajaxPayPassword.do"
					}
				},
				messages: {
					"accountBank.bankId":{
						required: "请选择开户银行",
					},
					"accountBank.account":{
						required: "请输入银行账号",
						minlength: "银行账号最少10位",
						maxlength: "银行账号最多21位"
					},
					"accountBank.branch":{
						required: "请输入开户行",
						minlength: "最少5个字符",
						maxlength: "最多50个字符"
					},
					"user.payPassword":{
						required:"请输入安全密码",
						remote:"安全密码输入错误"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent());
				},
				submitHandler: function(form) {
					form.submit();
				}
			});
		});
$(function(){
	$("#member_banks").addClass("nsg nsg_a1");
});
	</script>
</html>
