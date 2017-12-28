<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-手机认证</title>
	<#include "/content/common/meta.ftl">
	
	<script type="text/javascript">

	//发送验证码
	var wait2=60;
	function sendAuthCode(o){
		if (wait2 ==0) {
			        o.removeAttribute("disabled");         
			        o.value="获取验证码";
			        $("#yzm_button").removeClass("yzm_s2");
					$("#yzm_button").addClass("yzm_s1");
			        wait2 = 60;
		} else {
		    	if(wait2==60){
				    		var phoneReg = $("#phone").val();
				    		if(phoneReg.length<=0){
				    			alert("手机号码不能为空！");
				    			return false;
				    		}
				    		var regPhone =/^(13[0-9]|14[0-9]|17[0-9]|18[0-9]|15[0|2|3|6|7|8|9]|18[8|9])\d{8}$/;
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
							    			  $("#yzm_button").removeClass("yzm_s1");
											  $("#yzm_button").addClass("yzm_s2");
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
										        $("#yzm_button").removeClass("yzm_s2");
												$("#yzm_button").addClass("yzm_s1");
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
 <!-- header -->
<#include "/content/common/header.ftl">

<!-- content -->
<div style="width:100%; background:url(${base}/static/img/d6.png) 0 0 repeat-x; min-width:1000px; padding-bottom:30px;" class="biaoti_bg">
<div class="content">
 <div class="biaoti">
  <a href="${base}/">${Application ["qmd.setting.name"]} > </a>
  <a href="${base}/userCenter/index.do">我的账户 > </a>
  <a class="liebiao">手机认证</a>
 </div><!--biaoti end-->

<div class="admin clearfix">

	<#include "/content/common/user_center_left.ftl">



<div class="right_jiluneirong">
  <div class="tabsblk">
    <a href="" class="checked">手机认证</a>
  </div>
  <div class="sepblk"></div>
  <div class="formblk">
   	<form id="checkPhoneForm" method="post" action="checkUserPhone.do">
    <table width="100%" cellpadding="0" cellspacing="0" class="checked_v4">
     <tbody>
    <#if loginUser.phoneStatus==0>
	       <tr>
	         <td class="checked_v5">手机号码：</td>
	         <td class="checked_v6">
	         	<input name="user.phone" id="phone" value = "${loginUser.phone}" type="text" style="width:178px; height:24px; border:1px solid #7d7d7d;"/>
	         	<input type = "button" id="yzm_button" onclick="sendAuthCode(this)" value="获取验证码" class="yzm_s1" style=" display:inline-block; width:120px; height:38px; line-height:45px; text-align:center; font-size:16px; border-radius:20px;"/>
	         </td>
	       </tr>
	       <tr height="18"></tr>
	       <tr>
	         <td class="checked_v5">短信验证码：</td>
	         <td class="checked_v6">
	         	<input name="codeReg" id="mycodePhone"  type="text" style="width:178px; height:24px; border:1px solid #7d7d7d;"/>
	         </td>
	       </tr>
	       <tr height="18"></tr>
	       <tr>
	         <td class="checked_v5"></td>
	         <td class="checked_v6">
	          <input type="button" onclick="subForm()" id="sub_button" class="queding" value="确定"/>
	          <input type="reset" class="quxiao" value="取消"/>
	         </td>
	       </tr> 
       <#else>
       	<tr>
         <td class="checked_v5">手机号码：</td>
         <td class="checked_v6">${loginUser.phone}</td>
       </tr>
       <tr height="18"></tr>
       	<tr>
         <td class="checked_v5">
	         <#if loginUser.phoneStatus==1>
		  		<input type="button" id = "Button" value="已通过" class="queding" />
		  	<#else>
		  		<input type="button" id = "Button" value="审核中" class="queding" />
		  	</#if>
		  	</td>
      	 </tr>
		</#if>
     </tbody>
    </table>
    </form>
  </div>
  <div class="fanye"></div>
</div>

<div style="clear:both;"></div>
</div><!--big_content end-->
</div><!--content end-->
</div><!--biaoti_bg end-->
<#-- 备份9-->
 

<#-- 备份9-->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript">
$().ready( function() {
	$("#phone_certification").addClass("current");
	var $phoneForm = $("#phoneForm");
	 // 表单验证
	$phoneForm.validate({
		rules: {
			"user.phone":{
				required: true,
				phone:true,
				remote:"${base}/user/valPhone.do"
			},
			"user.payPassword":{
				required:true,
				remote:"${base}/userCenter/ajaxPayPassword.do"
			}
		},
		messages: {
			"user.phone":{
				required: "手机号码不能为空",
				phone:"手机号格式错误",
				remote:"手机号已存在"
			},
			"user.payPassword":{
				required:"请输入交易密码",
				remote:"交易密码输入错误"
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
	$("#member_phone").addClass("nsg nsg_a1");
});
</script>

</html>
