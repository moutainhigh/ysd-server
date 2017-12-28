<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-我的账户-银行账户</title>
	<#include "/content/common/meta.ftl">
</head>
<body class="body_bg">
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/userCenter/toBankInput.do">银行账户</a></li>
			<li>新增银行账户</li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>新增银行账户</h3>
           <form id="bankInputForm" method="post" action="${base}/agency_pt/bankInput.do">		
			<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="86">开户名：</td>
                <td>
					<select id="user_id" class="kaqu_r3" name="user.id">
	               		<option value="" selected="selected">请选择</option>
							<#list userList as user>
								<option value="${user.id}" <#if buserid == user.id>selected</#if>>${user.username}（${user.realName}）</option>
							</#list>
					</select>
				</td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">开户银行：</td>
                <td><select id="bankid" class="kaqu_r3" name="accountBank.bankId">
					<@listing_childlist sign="account_bank"; listingList>
						<#list listingList as listing>
							<option value="${listing.keyValue}" <#if (listing.keyValue == accountBank.bankId)!> selected</#if>>${listing.name}
							</option>
						</#list>
					</@listing_childlist></select>
				</td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">银行账号：</td>
                <td><input class="input2 w_252" name="accountBank.account" type="text" id="account" value="${(accountBank.account)!}"/></td>
              </tr>
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">开户行：</td>
                <td>
                 <input class="input2 w_252" id="branch" name="accountBank.branch" type="text" value="${(accountBank.branch)!}" maxlength = "50">
				</td>
              </tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">安全密码：</td>
                <td><#if loginUser.payPassword!>
		          		<input type="password" class="input2 w_252 " name="user.payPassword" id="payPassword" maxlength = "50"/>
		          	<#else>
		          		<a href="${base}/userCenter/toPayPassword.do" >请先设置一个安全密码</a>
		          		<input type="hidden"  name="user.payPassword">
		          	</#if></td>
              </tr>
                                                                                
              <tr>
                <td class="text_r grayBg">&nbsp;</td>
                <td class="text_r grayBg">&nbsp;</td>
            	<td height="40">
                	<input class="btn1 white" type="submit" value="提交">
       	   			<#--<input class="btn2 ml_15" type="reset" value="重置">-->
                </td>
              </tr>
        	</table> 
            </form>       
        </div>                
    </div>
    
</div>
	<#include "/content/common/footer.ftl">
</body>
 <!-- header -->
<#include "/content/common/user_center_header.ftl">

<div class="user_middle">
	<div class="user_content">
    	<div class="user_content_bottom kaqu_w00">
			<div class="kaqu_x0">
            	<div class="kaqu_x1">
                	<span  class="kaqu_x2"> 
                  		<span class="kaqu_x3">              
		                   <a href="${base}/index.do">乐商贷首页</a>&nbsp;
		                   <a>></a>&nbsp;
		                   <a href="${base}/userCenter/index.do">账户中心</a>&nbsp;
		                   <a>></a>&nbsp;
		                   <a href="${base}/userCenter/toBankInput.do">银行账户</a>
                  		</span>
                	</span>
            	</div><!--kaqu_x1 end-->
			</div><!--kaqu_x0 end--> 
		<div class="kaqu_w50">
          <ul class="fl kaqu_w60">
          	  <li class="kaqu_w70">银行账户</li>
              <li class="list_fs kaqu_w80"><a href="javascript:void(0);">新增银行账户</a></li>
              <li class="list_fr kaqu_w80"><a href = "${base}/agency_pt/bankList.do" class="my_chongzhi">银行账户列表</a></li>
         
 	  </ul>
		<div style="" class="kaqu_w90">
		 <div class="kaqu_renzheng">
				<br/><br/>
				<div class="frm">
				<form id="bankInputForm" method="post" action="${base}/agency_pt/bankInput.do">		
					<table width="100%">
								<tr>
									<td class="item">开户名：</td>
									<td>
									 	<select id="user_id" class="kaqu_r3" name="user.id">
			                           		<option value="" selected="selected">请选择</option>
												<#list userList as user>
													<option value="${user.id}" <#if buserid == user.id>selected</#if>>${user.username}（${user.realName}）</option>
												</#list>
										</select>
									</td>
								</tr>
	                   			<tr height="10"><td></td><td></td></tr>
									<tr>
										<td class="item">开户银行：</td>
										<td> <select id="bankid" class="kaqu_r3" name="accountBank.bankId">
				                           		<option value="" selected="selected">请选择</option>
				                                <@listing_childlist sign="account_bank"; listingList>
													<#list listingList as listing>
														<option value="${listing.keyValue}" <#if (listing.keyValue == accountBank.bankId)!> selected</#if>>${listing.name}
														</option>
													</#list>
												</@listing_childlist>
											</select>
										</td>
									</tr>
	                    		<tr height="10"><td></td><td></td></tr>
									<tr>
										<td class="item">银行账号：</td>
										<td><input type="text" name="accountBank.account" class="kaqu_w100 " id="account" value="${(accountBank.account)!}" maxlength = "50"></td>
									</tr>
	                  			  <tr height="10"><td></td><td></td></tr>
									<tr>
										<td class="item">开户行：</td>
										<td><input id="branch" name="accountBank.branch" class="kaqu_w100 " type="text" value="${(accountBank.branch)!}" maxlength = "50"></td>
									</tr>
	                   				 <tr height="10"><td></td><td></td></tr>
									<tr>
							          	<td class="item">安全密码：</td>
							          	<td> 
								          	<#if loginUser.payPassword!>
								          		<input type="password" class="kaqu_w100 " name="user.payPassword" id="payPassword" maxlength = "50"/>
								          	<#else>
								          		<a href="${base}/userCenter/toPayPassword.do" >请先设置一个安全密码</a>
								          		<input type="hidden"  name="user.payPassword">
								          	</#if>
							          	</td>
							          </tr>
	                   				 <tr height="10"><td></td><td></td></tr>
									 <tr>
							           <td class="item"></td>
										<td>								
				                        	<input type="submit" value="确 定" class = "kaqu_btns_001" />
										</td>
						            </tr>					
								</table>
							</form>
					</div>
		</div>
			</div>
         <div style=" clear:both; padding-bottom:60px;"></div>
      </div>
    <div style=" clear:both"></div>
    </div><!--user_content_bottom end-->
   <div style=" background-image:url(${base}/static/img/3_bg.png); background-position:0 bottom; background-repeat:repeat-x; height:9px;"></div>
</div><!--user_content end-->
<div style=" clear:both; padding-bottom:50px;"></div>
</div><!--user_middle end-->
<div style=" clear:both"></div>
<#include "/content/common/footer.html">
</body>
<script type="text/javascript" src="${base}/js/common/bankInput.js"></script>
    <script type="text/javascript">
		$().ready( function() {
			$(".sssj").attr("id","lssj");
			$("#li_a_yhzhlb").addClass("li_a_select");
			
			var $bankInputForm = $("#bankInputForm");
			$("#account").bankInput();
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
	</script>
</html>
