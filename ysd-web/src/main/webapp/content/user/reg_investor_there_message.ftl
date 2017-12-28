<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-注册完成</title>
<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
</head>
<body>
<#include "/content/common/header.ftl">

<div style="background:#fff; width:998px; height:525px; margin:20px auto; border:1px solid #b5b5b6; border-radius:20px;">
   <div style="width:963px; padding-left:35px;">
       <h3 style="color:#595757; font-size:16px; height:69px; line-height:69px;">账户登录</h3>
       <div style=""><img src="${base}/static/img/v4.png" /></div>
       <div style="border-right: 1px solid #807f80; padding:125px 30px  5px 105px; margin-right: 40px;font-size:16px; color:#807f80; float:left;background:url(${base}/static/img/v6.png) 0px 90px no-repeat; height:101px;margin-top:40px;"><font style="font-weight:bold;">恭喜您验证成功！</font></div>
	       <ul style=" color:#3e3a39; padding:110px 0px 30px 0px; position:relative; float:left;">
			 	<li style="color:#595757; font-size:14px;margin-bottom:10px;">
		         <font style="color:#807F80; padding-left: 132px;font-size:24px;">设置交易密码</font>
		        </li>  
		       	<li style="color:#595757; font-size:14px;margin-bottom:10px;">
		         <font style="color:#807F80; padding-left: 132px;">为了你的资金安全请不要外泄此密码</font>
		        </li>         
		       	<li style="color:#595757; font-size:17px;margin-bottom:10px;">
		         <font style="color:red; padding-left: 132px;">设置成功，请牢牢记住交易密码！</font>
		        </li>         
			 	<li style="color:#595757; font-size:14px;margin-bottom:20px;padding-left: 120px;">
		         	<a id="yzm_button" href="${base}/userCenter/index.do"  class="zhuce" style=" background:#ccc; display:inline-block; width:140px; height:45px; line-height:45px; text-align:center; font-size:16px; border-radius:20px;">进入我的账户</a>
		       </li>
		       	
	       </ul>
       </form>
   </div>
  <div style="clear:both;"></div>
</div>


<#include "/content/common/footer.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
  <script type="text/javascript" src="${base}/static/js/jquery/password_strength_plugin.js"></script>
		<script type="text/javascript">
		$().ready( function() {
			
			var $payPasswordForm = $("#payPasswordForm");
			 // 表单验证
			$payPasswordForm.validate({
				rules: {
					"newPayPassword":{
						required: true,
						strongTxt:true,
						minlength: 8,
						maxlength: 20
					},
					"newPayPasswordAgain":{
						required: true,
						equalTo: "#new_payPassword"
					}
				},
				messages: {
					"newPayPassword":{
						required: "请输入新交易密码",
						minlength: "密码必须大于等于8",
						maxlength: "密码必须小于等于20"
					},
					"newPayPasswordAgain":{
						required: "请再次输入新交易密码",
						equalTo: "两次交易密码输入不一致"
					}
				},
				errorClass:"erron",
				
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent());
				},
				submitHandler: function(form) {
					$.ajax({
						url: "${base}/userCenter/ajaxPasswordUpdate.do",
						data: $payPasswordForm.serialize(),
						type: "POST",
						dataType: "json",
						cache: false,
						beforeSend: function() {
							$payPasswordForm.find("submit").attr("disabled", true);
						},

						success: function(data) {
							<#--$.message({type: data.status, content: data.message});-->
							if(data.status=="error"){
								$("#old_payPassword").val("");
								$("#old_payPassword").focus();
								alert(data.message);
							}
							if(data.status=="success"){
								alert(data.message);
								setTimeout(function(){
								$("#old_payPassword").val("");
								$("#new_payPassword").val("");
								$("#new_payPassword_again").val("");
								},2000);
								window.location.href = "${base}/userCenter/realname.do";
								
							}
						}
					});
				}
			});
			
			
			
		});
	</script>
<script>
jQuery(function(){
	jQuery(".informashion").mouseover(function(){
	  jQuery(this).children('.sky').css("display","block");
		});
	jQuery(".informashion").mouseleave(function(){
		jQuery(this).children('.sky').css("display","none");
		});
	});
</script>
</body>
</html>
