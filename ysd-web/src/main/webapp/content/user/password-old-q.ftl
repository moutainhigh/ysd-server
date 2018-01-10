<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-专业安全透明的互联网金融服务平台-我的账户</title>
<#include "/content/common/meta.ftl">
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">


<#include "/content/common/user_center_header.ftl">

<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px; margin-top:20px;">
    <!--left-->
	<#include "/content/common/user_center_left.ftl">
    <!--right-->
    <div style="width:942px; float:right; ">
      <div style="border:1px solid #e6e6e6;background:#fff;">
        <div style="  width:100%;background:#eeeeee; height:51px; border-bottom:1px solid #e6e6e6;">
            <a class="hr" href="${base}/member/toPayPassword.do">交易密码设置</a>
            <a class="hr hre" href="#">登录密码设置</a>
        </div>
        <div style=" padding-top:50px; padding-left:50px; padding-bottom:50px; clear:both;">
            <table cellspacing="0" cellpadding="0" border="0" width="100%">
            	<form action="/member/passwordUpdate.do" method="post" id="submitForm">
            		<input type="hidden" name="p" value="1"/>
		              <tbody><tr>
		                <td style="width:115px; padding-right:5px; text-align:right;color:#666; font-size:14px;"><sup style="color:#fd7c1a;">*</sup>当前密码：</td>
		                <td><input type="password" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;" name="user.password"></td>
		              </tr>
		              <tr height="20"><td></td><td></td></tr>
		              <tr>
		                <td style="width:115px; padding-right:5px; text-align:right;color:#666; font-size:14px;"><sup style="color:#fd7c1a;">*</sup>新密码：</td>
		                <td>
		                  <input type="password" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;" name="newPassword" id="newPassword1">
		                  <div style="color:#b2b2b2; font-size:14px;">8-20个字符，建议使用字母加数字或符号</div>
		                </td>
		              </tr>
		              <tr height="20"><td></td><td></td></tr>
		              <tr>
		                <td style="width:115px; padding-right:5px; text-align:right;color:#666; font-size:14px;"><sup style="color:#fd7c1a;">*</sup>确认新密码：</td>
		                <td><input type="password" style="width:294px; height:44px; line-height:44px; padding-left:4px; border:1px solid #e6e6e6; border-radius:5px;" id="newPassword2"  name="newPassword2"></td>
		              </tr>
		              <tr height="80">
		                <td></td> 
		                <td>
		                    <input class="btn_t1" style="display:inline-block;" value="确认" type="submit"/>
		                    <a style=" color:#666; margin-left:5px;" href="${base}/user/findPsw.do">忘记密码？</a>
		                </td>
		              </tr>
		            </tbody>
		       </form>
           </table>
          </div>      
        </div>
    </div>
    <div style="width:100%;clear:both; height:1px;"></div>
  </div>
</div><!-- content end -->

<#include "/content/common/footer.ftl">
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/js/jquery/password_strength_plugin.js"></script>
<script>
$().ready( function() {
	$("#header_wdzh").addClass("hq");<#-- header.ftl 选中样式 -->
	
	$('.left_kuang li').last().css('border-bottom','0');
	$('.user_list_qh li').click(function(){
		var i=$(this).index();
		$('.user_list_qh li').removeClass('tzlist_user');
		$(this).addClass('tzlist_user');
		$('.user_div_qh').css('display','none');
		$('.user_div_qh').eq(i).css('display','block');
	});
	
	$("#pwd_management_left").addClass("current");
	
	var $passwordForm = $("#passwordForm");
	// 表单验证
			 
	$passwordForm.validate({
		rules: {
				"user.password":{
					required: true,
					remote:"${base}/manager/ajaxPassword.do"
				},
				"newPassword":{
					required: true,
					strongTxt:true,
					minlength: 8,
					maxlength: 20
				},
				"newPassword2":{
					required: true,
					equalTo: "#newPassword"
				}
			},
			messages: {
				"user.password":{
					required: "请输入旧密码",
					remote:"当前密码输入错误"
				},
				"newPassword":{
					required: "请输入新密码",
					minlength: "密码长度必须大于等于8",
					maxlength: "密码长度必须小于等于20"
				},
				"newPassword2":{
					required: "请再次输入新密码",
					equalTo: "两次密码输入不一致"
				}
			},
			errorClass:"erron",
			errorPlacement: function(error, element) {
				 error.appendTo(element.parent());
			},
			submitHandler: function(form) {
				form.submit();
			}
		});
});
</script>  
 </body>
</html>
