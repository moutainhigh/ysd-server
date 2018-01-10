<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <meta charset="gb2312" />
  <title>全民贷-手机找回密码</title>
  <meta name="application-name" content="网络P2P借贷" />
   <meta name="msapplication-tooltip" content="网络P2P借贷-上海借贷-杭州借贷" />
   <meta http-equiv="x-ua-compatible" content="ie=7" />
	<meta name="keywords" content="${Application ["qmd.setting.metaKeywords"]}" />
	<meta name="description" content="${Application ["qmd.setting.metaDescription"]}" />
	<meta name="robots" content="all" />
	<link rel="shortcut icon" href="${Application["qmd.static.baseUrl"]}/img/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="${base}/static/css/base.css" media="screen" />
	<link rel="stylesheet" href="${base}/static/css/login.css" media="screen" />
	<link rel="stylesheet" href="${base}/static/css/reg.css" media="screen" />
	<link rel="stylesheet" href="${base}/static/base/css/base.css" type="text/css" />
	
    <script type="text/javascript" src="${base}/static/js/jquery/jquery-1.8.1.js"></script>
    <script type="text/javascript" src="${base}/static/js/jquery/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/static/base/js/base.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
		
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
	<!--
	<script type="text/javascript">
	
		$(document).ready(
			function(){
				alert("jquery加载成功");
			}
		);
		
	</script>
	-->
	<script type="text/javascript">
	$().ready(function() {
	
		var $lostpassPhoneForm = $("#lostpassPhoneForm");
		var $lostpassPhoneButton = $("#lostpassPhoneButton");
		var $sendPhoneButton = $("#sendPhoneButton");
		var $phone=$("#phone");
		var $username=$("#username");
		var $mycode = $("#mycode");
		
		$lostpassPhoneButton.click( function() {
			$lostpassPhoneForm.submit();
		})
		
		 var autoPlay = function(obj, i) {
			//obj.attr("class", "");给a标签添加灰色不可点击背景
            obj.html("<div class='submitStyle'>(<font>" + i + "</font>）秒后获取验证码</div>");
            i = i - 1;
            if (i == 0) {
                obj.siblings(".txt").attr("disabled", false);
                obj.html("<div class='submitStyle'>重新获取验证码</div>");
            } else {
                setTimeout(function() {
                    autoPlay(obj, i);
                }, 1000);
            }
        };
		
		$("#regphone").delegate(".submit-btn", "click", function() {
            sendPhoneCapture(this);
        });
		
		$sendPhoneButton.click(function(){
			$this = $(this);
			$this.siblings(".txt").attr("disabled", true);
			$.ajax({
				url: "${base}/user/ajaxGetPhoneCode.do",
				data: {"user.phone":$.trim($phone.val()),"user.username":$.trim($username.val())},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
				},
				success: function(data) {
					if(data.status =="success"){
						autoPlay($this,90);
					}
					$mycode.val(data.code);
					$.message({type: data.status, content: data.message});
				}
			});
			
		})
		
		 // 表单验证
			$lostpassPhoneForm.validate({
				rules: {
					"user.username":{
						required: true,
						remote: "${base}/user/checkUsername.do"
					},
					"user.phone":{
						required: true,
						phone:true
					},
					"mycode":{
						required:true
					}
				},
				messages: {
					"user.username": {
						required: "请填写用户名",
						remote: "用户名不存在"
					},
					"user.phone": {
						required: "手机号必填",
						phone:"手机号格式不正确"
					},
					"mycode":{
						required:"验证码必填"
					}
				},
				errorPlacement: function(error, element) {
				  error.appendTo(element.parent());
				},
				submitHandler: function(form) {
					form.submit();
				}
			});
			
	})
	</script>
	
	
  </head>
  
  <body>
     <div class="wrapper">
        <div class="logo">
            <img src="img/logo.jpg" alt="全民贷" title="全民贷" onclick="javascript:window.location.href='http://www.swdai.com'" /></div>
        <!--logo-->
        <div class="regist">
            <h1>手机找回密码</h1><a href="${base}/user/lostpass.do?p=${p}">邮件找回密码</a>
                 <form id="lostpassPhoneForm" method="post" action="findPwdByPhone.do">
                 <input type = "hidden" name = "p" value=${p}>
            <div class="content_left">
                <!--tag-->
                <div class="infor clearfix">
                    <div class="email">
                        <ul id="v2regListInfo_bar">
                            <li>
                                <label>用户名：</label>
                                <div class="v2regListInfo_rtCont">
                                    <input maxlength="16" type="text" name="user.username" id="username" class="reginput_01" /><label id="username_label" for="user.username"></label>
                                </div>
                             </li>
                            <li>
                                <label>   
                            <li>
                                <label>手机号:</label>
                                <div class="v2regListInfo_rtCont">
                                    <input maxlength="11" type="text" name="user.phone" id="phone" class="reginput_01  txt" />
                                     <a href="javascript:void(0);" id="sendPhoneButton" onclick="#"><div class="submitStyle">获取验证码</div></a>
                                    <label id="password_label" for="user.password"></label>
                                </div>
                                <span class="Rclear">
                                <!--验证密码强度-->
                                </span>
                            </li>
                            <li id='livalidatecode'>
                                <label>验证码：</label>
                                <div class="">
                                    <input  type="text" name="mycode" id="mycode" class=" txt" />
                                    <label id = "mycode_label" for="mycode"></label>
                                </div>
                            </li>   
                            <li>
                                <div class="v2reg_bt">
                                    
                                   <a href="javascript:void(0);" id="lostpassPhoneButton" onclick="#"><div id="#" class="submitStyle">找回密码</div></a>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <!--email-->
                </div>
                <!--infor-->
            </div>
            <!--content_left-->
            </form>
        </div>
        <!--bottom-->
        
    </div>
    <!--wrapper-->
      
    
    
    
    
    
    
    
  </body>
</html>
