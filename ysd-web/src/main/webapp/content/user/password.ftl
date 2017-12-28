<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-密码修改</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/password_modify.css" />
    <style>
   		#main {
   			margin-bottom:100px;
   		}	
    </style>
</head>
<body id="bg">
    <!-- 头部 -->
    <#include "/content/common/header.ftl">

    <!--内容区域-->
    <div id="main">
        <!--左边导航栏-->
        <#include "/content/common/user_center_left.ftl">
        <!--右边主内容区-->
        <div class="content fr">
            <div class="options">
                <div><a href="${base}/member/toPassword.do">登录密码修改</a></div>
                <div><a href="${base}/member/toPayPassword.do">交易密码修改</a></div>
            </div>
            <!--登录密码修改-->
            <div class="deal" >
	            <div class="input_tips" style='visibility:hidden;'>
	                <img src="../img/tips.png" alt="">
	                <span>手机号格式错误！</span>
	            </div>
                <form action="/member/passwordUpdate.do" method="post" id="passwordForm" class="login">
                <input type="hidden" name="p" value="1"/>
                    <table>
                        <tr class="first">
                            <td>当前密码：</td>
                            <td><input type="password" title="now_pwd" name="user.password"></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="forget"><a href="${base}/user/findPsw.do">忘记密码？</a></td>
                        </tr>
                        <tr class="new">
                            <td>新密码：</td>
                            <td><input type="password"  placeholder="密码由8~16位字母、数字及符号组成" name="newPassword" id="newPassword1"></td>
                        </tr>
                        <tr class="check">
                            <td>确认新密码：</td>
                            <td><input type="password"  id="newPassword2"  name="newPassword2"></td>
                        </tr>
                        <tr class="last">
                            <td colspan="2"><input type="submit" value="提交"></td>
                        </tr>
                    </table>
                </form>
            </div>
           
           
        </div>
        <div class="clear"></div>
    </div>

    <!--底部-->
    


<#include "/content/common/footer-center.ftl">
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${base}/js/modify.js"></script>
<script src="${base}/js/common.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>
<!--<script type="text/javascript" src="${base}/js/jquery/password_strength_plugin.js"></script>-->
<script>
$().ready( function() {
	$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
	$("#header_gywm").find('a').css('border',0);
	$(".center_left_zhgl").addClass("current");
	$(".options").find('div').eq(0).addClass('current');
	

	
	
	var $passwordForm = $("#passwordForm");
	// 表单验证	 
	$passwordForm.validate({
		rules: {
				"user.password":{
					required: true,
					remote:"${base}/member/ajaxPassword.do"
				},
				"newPassword":{
					required: true,
					strongTxt:true,
					minlength: 8,
					maxlength: 20
				},
				"newPassword2":{
					required: true,
					equalTo: "#newPassword1"
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
				  element.parents(".deal").find('.input_tips').css("visibility","visible");
				  element.parents(".deal").find('.input_tips').find("span").html('');
				  error.appendTo(element.parents(".deal").find('.input_tips').find("span"));
			},
			submitHandler: function(form) {
				form.submit();
			}
		});
		
});
</script>  
 </body>
</html>
