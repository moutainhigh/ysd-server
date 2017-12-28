<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-我要充值</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/recharge.css" />
</head>
    <body id="bg">
    <!-- 头部 -->
   
	<#include "/content/common/header.ftl">

    <!--内容区域-->
    <div id="main">
        <!--左边导航栏-->
        <#include "/content/common/user_center_left.ftl">
        <!--右边内容区-->
        <div class="content fr">
            <!--充值-->
            <div class="recharge">
                
                 <form id="rechargeForm" method="post" action="${base}/userCenter/toBankInput.do" target="_blank">
                    <div class="recharge_value">
                        充值金额： 
          <input type="text" name="rechargeMoney" id="money" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'');"   placeholder="请输入充值金额" value='${money}' />&nbsp;&nbsp;元        
                    <p></p>
                    </div>
                    <div class="identify_num">
                        验证码： 
      <input type="text"  placeholder="请输入验证码" name="mycode" onfocus="verifyCode()" id="mycode"> <a  onclick="verifyCodeLink();" title="点击更换"><img  src="${base}/rand.do" id="code_img"></a>
                    <p></p>
                    </div>
                    <input type="submit" id="paymentRechargeButton" class="sub" value="充值"/>
                </form>
            </div>
        </div>
        <div class='recharge_uncard'>
        	<div class='uncard_content'>
        		<div class="close fr"></div>
        		<div class="uncard_title tips">温馨提示</div>
        		<div class="uncard_cont tips">绑定银行卡后才能使用快捷支付及提现功能</div>
        		<div class="options">
        			<a href="${base}/userCenter/toBank.do"><span>立即绑定</span></a>
        			<a href="javascript:void(0);" class='uncar_qx'><span>取消</span></a>
        			
        		</div>
        	</div>
        </div>
       
        <div class="clear"></div>
    </div>

    <!--底部-->
    <#include "/content/common/footer-center.ftl">
</body>
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>

<script src="${base}/js/common.js"></script>


<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>
<script>
	$("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
	$("#header_gywm").find('a').css('border',0);
	$(".center_left_wdzc").addClass("current");<#-- user_center_left.ftl 选中样式 -->

	function verifyCode(){
		$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();'><img id = 'code_img' src='' width='80' height='44' style='cursor:pointer; margin-left:20px;' /></a>");
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		$("#mycode").select();
	}
	
	function verifyCodeLink(){
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
	
	function alertMessage(that,text){
		$(that).find('p').html(text);
	}
	$('#money').focus(function(){
		$(this).parent('div').find('p').html('');
	})
	
		$("#paymentRechargeButton").click(function(){
			var $rechargeForm = $("#rechargeForm");
			var $money = $("#money");
			var flag = false;
			
			if($money.val()=='' || $money.val().length<0 ){
				alertMessage('.recharge_value','请输入正确的充值金额');
				return false;
			}
			if(parseFloat($money.val()) < 3){
				alertMessage('.recharge_value','充值金额不少于3元');
				return false;
			}
			var rechargeto=$('input[name=payment]:checked').val();
			if(rechargeto=='80'){
				if(parseInt($money.val())>50000){
					alertMessage('.recharge_value','快捷支付单笔最高可充值50000元');
					return false;
				}
			}
			alertMessage('.recharge_value','');
			//$.ajax({
				//url: "${base}/user/ajaxVerifyCode.do",
				//data: {"mycode":$("#mycode").val()},
				//type: "POST",
				//dataType: "json",
				//cache: false,
				//success: function(data) {
					//if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
						//alertMessage('验证码输入错误');
						//flag = false;
					//}else if(data.status == 'error'){
						//alertMessage(data.message);
						//flag = false;
					//}else{
						//var testTime = new Date().getTime();
						//KP.options.drag = true;
						//KP.show("请在新打开的页面上完成充值", 524, 260);
						//$(KP.options.content).load(qmd.base+"/payment/poputContent.do?testTime="+testTime);
					//}
				//}
			//});
		
		});
	// 表单验证
	$("#rechargeForm").validate({
		errorClass:"alrm",
		rules: {
			
			"mycode":{
				required:true,
				remote:{
					type:"POST",
					url:"${base}/user/checkVerifyCode.do"
				}
			}
		},
		messages: {
			"mycode":{
				required:"验证码不能为空!",
				remote:"验证码错误"
			}
			
		},
		errorClass:"erron",
			errorPlacement: function(error, element) {
			//element.parents(".mlogin").find('.input_tips').css("visibility","visible");
			//element.parents(".mlogin").find('.input_tips').find("span").html('');
			//error.appendTo(element.parents(".mlogin").find('.input_tips').find("span"));
			alertMessage('.identify_num',error);
		},
		
		
		submitHandler: function(form) {				
			form.submit();
		}
	});	
	$('.recharge_uncard').find('.close').click(function(){
		$('.recharge_uncard').hide();
	});
	$('.recharge_uncard').find('.uncar_qx').click(function(){
		$('.recharge_uncard').hide();
	});
</script>  
</body>
</html>
