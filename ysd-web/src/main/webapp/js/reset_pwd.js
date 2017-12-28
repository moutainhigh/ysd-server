var isMobile = /^(?:13\d|15\d|18\d|177)\d{5}(\d{3}|\*{3})$/;
function checktel(){
    var tel=$('.phone_num').val();
    if(!isMobile.test(tel)){
        $('.input_tips').find('span').html('手机号格式错误！');
        $('.input_tips').css('visibility','visible');
        return false;
    }
    var code1=$('input[name=identify_num1]').val();
    if(code1=='' || typeof(code1)=='undefined'){
        $('.input_tips').find('span').html('请输入验证码');
        $('.input_tips').css('visibility','visible');
        return false;
    }
    $('.account_name').hide();
    $('.identify').show();
    $('.progress').find('li').eq(2).addClass('current');
    return false;
}
function checkidentify(){
    var code2=$('input[name=identify_num2]').val();
    if(code2=='' || typeof(code2)=='undefined'){
        $('.input_tips').find('span').html('请输入验证码');
        $('.input_tips').css('visibility','visible');
        return false;
    }
    var code3=$('input[name=identify_num3]').val();
    if(code3=='' || typeof(code3)=='undefined'){
        $('.input_tips').find('span').html('请输入短信验证码');
        $('.input_tips').css('visibility','visible');
        return false;
    }
    $('.identify').hide();
    $('.reset_pwd').show();
    $('.progress').find('li').eq(4).addClass('current');
    return false;
}
function checkpwd(){
    var pwd=$('input[name=pwd]').val();
    if(pwd=='' || typeof(pwd)=='undefined'){
        $('.input_tips').find('span').html('请输入新密码');
        $('.input_tips').css('visibility','visible');
        return false;
    }
    if(pwd.length<6){
        $('.input_tips').find('span').html('密码由6~16位字母、数字及符号组成');
        $('.input_tips').css('visibility','visible');
        return false;
    }
    var pwd2=$('input[name=pwd2]').val();
    if( pwd!=pwd2){
        $('.input_tips').find('span').html('请重复输入新密码');
        $('.input_tips').css('visibility','visible');
        return false;
    }
    $('.reset_pwd').hide();
    $('.complete').show();
    $('.progress').find('li').eq(6).addClass('current');
    return false;
}