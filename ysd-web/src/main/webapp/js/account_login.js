var isMobile = /^(?:13\d|15\d|18\d|177)\d{5}(\d{3}|\*{3})$/;
/**
 *
function checkAll(){
    var tel=$('.phone_num').val();
    var code=$('.identify_num').val();
    var pwd=$('.login_pwd').val();
    if(!isMobile.test(tel)){
        $('.input_tips').find('span').html('手机号格式错误！');
        $('.input_tips').css('visibility','visible');
        return false;
    }
    if(code=='' || typeof(code)=='undefined'){
        $('.input_tips').find('span').html('请输入验证码');
        $('.input_tips').css('visibility','visible');
        return false;
    }
    if(pwd=='' || typeof(pwd)=='undefined'){
        $('.input_tips').find('span').html('请输入密码');
        $('.input_tips').css('visibility','visible');
        return false;
    }
}
**/
$(function () {
   $('.mlogin_form').find('input').focus(function(){
       $('.input_tips').css('visibility','hidden');
   })
});