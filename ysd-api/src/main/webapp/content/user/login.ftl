<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>${Application ["qmd.setting.name"]}-登录</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>

	  
<script type="text/javascript">
 $(function() {
	    var $modal = $('#modal-alert');
	    var $username = $("#username");
	     var $password = $("#password");
	     var $loginForm = $("#loginForm");
		var str="";
	    $(".logina").on('click', function(e) {
	    if($username ==""){
	    	str ="用户名不能为空";
	    }
	     if($password ==""){
	    	str ="密码不能为空";
	    }
	    if(str.length >2){
		      var $target = $(e.target);
		      if (($target).hasClass('js-modal-open')) {
		        $modal.modal();
		         $("#str").val(str);
		      }
	      }else{
	      	$.ajax({
						url: "${base}/ajaxLogin.do",
						data: $loginForm.serialize(),
						type: "POST",
						dataType: "json",
						success: function(data) {
							if(data.status=="success"){
								window.location.href = data.message;
							}else if(data.status=="warn"){
								$.message({type: data.status, content: data.message});
								 var $target = $(e.target);
							      if (($target).hasClass('js-modal-open')) {
							        $modal.modal();
							         $("#str").val(data.message);
							      }
							}else{
								randimg();
								$password.val('');
								$.message({type: data.status, content: data.message});
								 var $target = $(e.target);
							      if (($target).hasClass('js-modal-open')) {
							        $modal.modal();
							         $("#str").val(data.message);
							      }
							}
						}
					});
	      }
	    });
	  });
	  
	
		$code_c.click(function() {
			$code_img.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		});
		
		function randimg(){
			$code_img.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		}
		
	});
	
	function verifyCode(){
		$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();'><img id = 'code_img' src='' width='80' height='30' style='vertical-align:middle; position:relative; left:7px; top:-2px;' /></a>");
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		$("#vCode").attr("style", "");
		$("#mycode").select();
	}
	
	function verifyCodeLink(){
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
</script>
 <style>
  	.am-form-group {margin-bottom: 1rem;}
  	.am-form .am-form-group input{border: 1px solid #e3e3e3;}
  </style>
</head>
<body class="bgc5" style="height:100%;overflow:hidden;">

  <header data-am-widget="header" class="am-header am-header-default bgc h40 lh40">
	      <div class="am-header-left am-header-nav">
	      	<a href="#right-link">
	          	  <i class="am-icon-angle-left am-icon-fw" style="font-size:1.5em;"></i>
	          </a>
	      </div>
	      <h1 class="am-header-title">
	          	登录
	      </h1>
	      <div class="am-header-right am-header-nav"></div>
	  </header><!-- header end-->
   		<div class="am-g" style="margin-top:1em;">
		  <div class="am-u-md-8 am-u-sm-centered">
		   <form name="loginForm" id="loginForm" class="am-form"  method="post">
		      <fieldset class="am-form-set">   	
		      	<div class="am-form-group">
		      		<input type="text" name="user.username" id="username" placeholder="请输入手机号码" style="height:2.4em;">
		      	</div>
				<div class="am-form-group">
		      		<input type="password" name="user.password" id="password"  placeholder="请输入登录密码" style="height:2.4em;">
		      	</div>
				<div class="tar" style="margin:-0.6em 0 0.6em;"><a href="" class="color">忘记密码？</a></div>
				<button type="button"  class="am-btn am-btn-block am-radius color1 bgc" data-am-modal="{target: '#my-alert',height: 100}" >登录</button>	
				<div class="tac" style="margin-top:1em;"><a href="" class="color">立即注册</a></div>
				
				<!-- 弹框开始 -->
				<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
				  <div class="am-modal-dialog">
				    <div class="am-modal-bd" name="str" id="str">
				     	
				    </div>
				    <div class="am-modal-footer">
				      <span class="am-modal-btn">确定</span>
				    </div>
				  </div>
				</div>
				<!-- 弹框结束 -->
		      </fieldset>     
		    </form>
		  </div>
		</div>
</html>
