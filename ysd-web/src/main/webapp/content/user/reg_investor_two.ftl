<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-账户签约</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.card.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
	<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.cn.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/ajaxImageUpload.js"></script>
	<script type="text/javascript">

	//发送验证码
	var wait2=60;
	function sendAuthCode(o){
		if (wait2 ==0) {
			        o.removeAttribute("disabled");         
			        o.value="获取验证码";
			        $("#yzm_button").removeClass("kaqu_w120");
					$("#yzm_button").addClass("phoneCode");
			        wait2 = 60;
		} else {
		    	if(wait2==60){
				    		var phoneReg = $("#phone").val();
				    		if(phoneReg.length<=0){
				    			alert("手机号码不能为空！");
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0-9]|18[8|9])\d{8}$/;
				    		if (!regPhone.test(phoneReg)){
				    			alert("手机号格式不正确");
				    			return false;
				    	 	}
				    	 	
				    	 	$.ajax({ 
						        	   url: '${base}/sendPhoneCode.do',
						        	   async:false,
						        	   type: 'post', 
						        	   dataType:'json', 
						        	   data: {'phoneReg':phoneReg}, 
						        	   beforeSend: function() {
											  o.value="短信发送中...";
											  o.setAttribute("disabled", true);
							    			  $("#yzm_button").removeClass("phoneCode");
											  $("#yzm_button").addClass("kaqu_w120");
									   },
						        	   success: function (data) { 
						        		   if(data.result==0){
						        	  			  // alert("短信发送成功");
					 		   		            o.value="重新发送(" + wait2 + ")";
					 		   		            wait2--;
					 		   		            setTimeout(function(){sendAuthCode(o)}, 1000);
					 		   		         
						        	  		}
						        		   else if(data.result==1){
						        	   			alert("短信发送失败,原因是:"+data.reason);
						        	   			o.removeAttribute("disabled");         
										        o.value="获取验证码";
										        $("#yzm_button").removeClass("kaqu_w120");
												$("#yzm_button").addClass("phoneCode");
						        	   		}
						        	   }, 
						        	   error: function (XMLHttpRequest, textStatus, errorThrown) { 
					        	   			alert(errorThrown); 
						        	   } 
				        	  });
		    	}else{
				            o.value="重新发送(" + wait2 + ")";
				            wait2--;
				            setTimeout(function(){sendAuthCode(o)}, 1000);	
		    	}  
		}		 
	}
	
	function subForm(){
			var phone_cc=$("#phone").val();
			var mycodePhone_cc=$("#mycodePhone").val();
			if(phone_cc==""||phone_cc==null){
				alert("手机号不能为空");
				return false;
			}
			if(mycodePhone_cc==""||mycodePhone_cc==null){
				alert("验证码不能为空");
				return false;
			}
			$("#checkPhoneForm").submit();
	}
		
</script>
</head>
<body>
	<#include "/content/common/header.ftl">
	
	
<div class="content">
  <div style="width:100%; height:48px; background:#eae9e9; border-bottom:1px solid #c6c6c6;">
    <div style=" width:985px;height:48px; margin:0 auto; background:url(/static/img/d7.png) 0 0 no-repeat;">
    </div>
  </div>
  
  <div style="width:985px; margin:0 auto; margin-top:15px; padding-bottom:15px; clear:both;">
      <div style=" padding:20px 0px 20px 20px; background:url(/static/img/d9.png) 100% center  no-repeat #fc4e4b; width:965px; height:437px;">
        <div style=" padding:65px 30px 0px 50px; width:295px; height:380px; background:#fff;">
           <div style="width:295px;">
          	<form id="checkPhoneForm" method="post" action="checkPhone.do">   
          	  <input type="hidden" name="user.id"  value="${loginUser.id}"  />    
               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; ">手机号</li>
                 <li>
                 <input type="text" name="user.phone" id="phone" value="${(sessionUserId.phone)!}" style="width:160px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
                 <input type = "button" id="yzm_button" onclick="sendAuthCode(this)" class="phoneCode" style="background:url(/static/img/d12.png) 0 0 no-repeat; width:99px; height:33px; line-height:33px; text-align:center; color:#ffc333; font-size:16px; display:inline-block; margin-left:2px;" value="免费获取" /> 
                 </li>
                 <li style="height:20px;"></li>
               </ul>
               <ul>
                 <li style="color:#4b4b4b;font-family:'宋体'; margin-bottom:5px; ">短信验证码</li>
                 <li><input type="text" name="codeReg" id="mycodePhone"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></li>
                 <li style="height:20px;"></li>
               </ul>
               <ul>
                 <li style="background:url(/static/img/d2.png) 0 0 no-repeat; width:286px; height:39px; line-height:39px; text-align:center;">
                   <a  onclick="subForm()"><span style="color:#fff;font-size:16px;font-weight:bold;display:block;">立即认证</span></a>
                 </li>
               </ul>
			</form>
           </div>
        </div>
      </div>
     <div style="clear:both;"></div>
  </div>

</div><!-- content end -->
<#include "/content/common/footer.ftl">
<#--
	<#include "/content/common/header.ftl">
	

<div class="content clearfix">
 <div style="width:960px; margin:0 auto;">
    <h3 style=" background:url(/static/img/reg.png) 0 -136px no-repeat; width:953px; height:46px;"></h3>
    <div style=" width:951px; height:418px; border:1px solid #d4c8aa; background:url(/static/img/r3.png) 0 0 no-repeat;">
      <div style="float:left; width:400px; height:380px; background:#fff; padding:20px 0px 0px 25px; margin-left:10px; margin-top:10px; margin-bottom:10px;">
      <form id="bankInputForm"  method="post" action="${base}/trustee/signedSubmitFirst.do"><#--    ->
       <table width="100%" cellpadding="0" cellspacing="0">
        <tbody>
        
        
        
					<#if loginUser.memberType==1>
						<tr height="25">
							<td align="right" style="color:#775623; font-weight:bold;">证件名称：</td>
							<td>
								<input type="text" class="txt" name="user.realName" style=" background:url(/static/img/input.png) 0 0 no-repeat; width:211px; color:#6d6c71; height:34px;line-height:34px; padding-left:8px; border:0 none; vertical-align:top;"/>
							</td>
						</tr>
						
			          	<tr height="42">
							<td align="right" style="color:#775623; font-weight:bold;">证件类型：</td>
							<td> 
								<select id="bankid" style="background:url(/static/img/input.png) 0 -2px no-repeat; width:220px; color:#6d6c71; height:30px;line-height:30px; padding-left:4px; border:0 none; vertical-align:top;" name="accountBank.accountType">
	                           		<option value="" selected="selected">请选择</option>
	                                <@listing_childlist sign="card_type"; listingList>
										<#list listingList as listing>
											<option value="${listing.keyValue}">${listing.name}</option>
										</#list>
									</@listing_childlist>
	                           	</select>
							</td>
						</tr>
			          	
			          	<tr>
			          	 <td align="right" style="color:#775623; font-weight:bold;">证件号码：</td>
			              <td>
			                 	<input type="text" class="txt" id="branch" name="accountBank.accountCard"  value="${loginUser.cardId}" style=" background:url(/static/img/input.png) 0 0 no-repeat; width:211px; color:#6d6c71; height:34px;line-height:34px; padding-left:8px; border:0 none; vertical-align:top;" >
			              </td>
			          	</tr>
			        <#else>
			           <tr>
			             <td align="right" style="color:#775623; font-weight:bold;">真实姓名：</td>
			              <td>
			                  <input type="text" name="user.realName" id="realName" value="${(sessionUserId.realName)!}" style=" background:url(/static/img/input.png) 0 0 no-repeat; width:211px; color:#6d6c71; height:34px;line-height:34px; padding-left:8px; border:0 none; vertical-align:top;"/>
			              </td>
			           </tr>
			           <tr>
			              <td align="right" style="color:#775623; font-weight:bold;">身份证号：</td>
			              <td>
			                  <input type="text" name="user.cardId" id="cardId" value="${(sessionUserId.cardId)!}" style=" background:url(/static/img/input.png) 0 0 no-repeat; width:211px; color:#6d6c71; height:34px;line-height:34px; padding-left:8px; border:0 none; vertical-align:top;"/>
			              </td>
			           </tr>
			           <tr>
			              <td align="right" style="color:#775623; font-weight:bold;">开户人手机号：</td>
			              <td>
			                  <input type="text" name="accountBank.phone" value="${(sessionUserId.phone)!}" id="phone" style="background:url(/static/img/input.png) 0 0 no-repeat;width:211px;color:#6d6c71;height:34px;line-height:34px;padding-left:8px;border:0 none;vertical-align:top;"/>
			              </td> 
			           </tr>
			        </#if>
        
        
       

           <tr height="12"></tr>
           <tr>
              <td align="right" style="color:#775623; font-weight:bold;">性别:</td>
              <td>
                 &nbsp;&nbsp;&nbsp; 
                 <label><input type="radio" name="user.sex"  value="2"   <#if loginUser.sex == 2>checked</#if>/>&nbsp;女</label>&nbsp;&nbsp;&nbsp;&nbsp;<label><input type="radio"  name="user.sex"  value="1" <#if loginUser.sex=='' || loginUser.sex==1 >checked</#if> />&nbsp;男</label>
              </td>
           </tr>

           <tr height="12"></tr>
           <tr>
              <td align="right" style="color:#775623; font-weight:bold;">银行卡号：</td>
              <td>
                  <input type="text" name="accountBank.account"  id="account" style=" background:url(/static/img/input.png) 0 0 no-repeat; width:211px; color:#6d6c71; height:34px;line-height:34px; padding-left:8px; border:0 none; vertical-align:top;"/>
              </td>
           </tr>
           <tr height="12"></tr>
           <tr>
              <td align="right" style="color:#775623; font-weight:bold;">开户行：</td>
              <td>
	               <select id="bankid" name="accountBank.bankId" style="background:url(/static/img/input.png) 0 -2px no-repeat; width:220px; color:#6d6c71; height:30px;line-height:30px; padding-left:4px; border:0 none; vertical-align:top;">
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
           <tr height="12"></tr>
           <tr>
              <td align="right" style="color:#775623; font-weight:bold;">开户支行：</td>
              <td>
                  <input type="text" id="branch" name="accountBank.branch" style="background:url(/static/img/input.png) 0 0 no-repeat;width:211px;color:#6d6c71;height:34px;line-height:34px;padding-left:8px;border:0 none;vertical-align:top;"/>
              </td> 
           </tr>
			<tr height="12"></tr>

           <tr>
              <td></td>
              <td>
               <input type="submit" value="立即认证"  style=" background:url(/static/img/r4.png) 0 0; width:211px; height:35px; color:#fff; font-size:14px; font-weight:bold; border:0 none;"/>
              </td>
           </tr>
        </tbody>
       </table>
       </form>
      </div>
      <div style="float:right; width:515px; padding-top:50px; text-align:center;"><img src="/static/img/r5.png" width="316" height="367"/></div>
    </div>
 </div>
</div>
	
	<#include "/content/common/footer.ftl">
	-->
</body>
<#--<script type="text/javascript" src="${base}/static/js/common/bankInput.js"></script>-->
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
    <script type="text/javascript">
		$().ready( function() {
			
			var $bankInputForm = $("#bankInputForm");
			<#--$("#account").bankInput();-->
			
			$deleteBank = $("#deleteBank");
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
								alert(data.message);
								//$.message({type: data.status, content: data.message});
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
					"user.cardId":{
						required: true,
						isIdCardNo:true,
						remote:"${base}/userCenter/checkCardiId.do"
					},
					"accountBank.bankId":{
						required: true
					},
					<#--"accountBank.accountName":{
						required: true,
						minlength: 2,
						maxlength:20
					},-->
					"user.realName":{
						required: true,
						realName:true,
						minlength:2,
						maxlength:20
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
					"accountBank.phone":{
						required: true,
						minlength: 11,
						maxlength: 11
					}
				},
				messages: {
					"user.cardId":{
						required:"身份证号码不能为空",
						isIdCardNo:"身份证号码格式不对",
						remote:"该身份证号已认证"
					},
					"accountBank.bankId":{
						required: "请选择开户银行",
					},
					"user.realName":{
						required: "真实姓名不能为空",
						realName:"真实姓名格式错误",
						minlength: "最少2个字",
						maxlength: "最多20个字"
					},
					<#--"accountBank.accountName":{
						required: "请输入开户人姓名",
						minlength: "开户人姓名最少2位",
						maxlength: "开户人姓名最多20位"
					},-->
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
					"accountBank.phone":{
						required: "请输入开户人手机号",
						minlength: "最少11个字符",
						maxlength: "最多11个字符"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent("li").next("li"));
				},
				submitHandler: function(form) {
					$bankInputForm.attr("target","blank");
					KP.options.drag = true;
					KP.show("", 520, 300);
					$(KP.options.content).load(qmd.base+"/pinan/poputContent.do");
					form.submit();	
				}
			});
		});
		
	</script>
<script type="text/javascript">
$(function(){
	$("#menuUser").addClass("kaqu_bg");
	$("#menuUser_ul").addClass("user_content_top1");
	$("#bank_account_left").addClass("touzi touzi1");
});
</script>
</html>
