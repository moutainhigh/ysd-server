function checkAll(){
	if(checkPhone()&&checkCode()&&checkCode1()&&checkPass()&&checkAgree()&&notExistPhone){
		return true;	
	}
	return false;
}
//手机号验证
function checkPhone(){
	var isMobile = /^(?:13\d|15\d|18\d|177|14\d)\d{5}(\d{3}|\*{3})$/;
	 var tel=$('.phone_num').val();
	 if(tel=='' || typeof(tel)=='undefined'){
		 alertInfo('手机号不能为空！',"1");
	    return false;
	 }else if(!isMobile.test(tel)){
	    	alertInfo('手机号格式错误！',"1");
	    	return false;
	 }
	 return true;
}
//验证码
function checkCode(){
	 var code=$('.identify_num').val();
	 if(code=='' || typeof(code)=='undefined'){
	    	alertInfo('请输入验证码',"2");
	    	return false;
    }
	 return true;
}
//短信验证码
function checkCode1(){
	var code1=$('.msg_identify').val();
	if(code1=='' || typeof(code1)=='undefined'){
    	alertInfo('请输入短信验证码',"3");
    	return false;
    }
	return true;
}
//密码
function checkPass(){
	var pwd=$('.pwd').val();
	var pwd2= $('.check_pwd').val();
	var ispassword=/.*[a-zA-Z]+.*$/;
	if(!ispassword.test(pwd)){
    	alertInfo('密码必须包含字母!',"4");
    	return false;
	}
	if(pwd=='' || typeof(pwd)=='undefined'){
    	alertInfo('请输入密码',"4");
    	return false;
    }else if(pwd.length<8){
    	alertInfo('密码由8~16位字母、数字及符号组成',"4");
    	return false;
    }else if( pwd!=pwd2){
    	alertInfo('密码不一致',"5");
    	return false;
    }
	return true;
}
//协议
function checkAgree(){
	if(!$("#agreeCheckbox")[0].checked){
    	alertInfo('请阅读并同意协议',"6");
    	return false;
    }
	return true;
}
	
function alertInfo(msg,al){
	 $('.input_tips').find('span').html(msg);
	 $('.input_tips').find('span').attr("al",al);
     $('.input_tips').css('visibility','visible');
}
$(function () {
	$('#header_wdzh').addClass('current');
	$('#header_gywm a').css('border',0);
	
    $('.mlogin_form').find('input').focus(function(){
    	var al ;
    	if(this.attributes.al){
    		al = this.attributes.al.value;
    	}
    	var alT = $('.input_tips').find('span').attr("al");
    	if(alT==al){
    		$('.input_tips').css('visibility','hidden');	
    	}
    })
});