function recharge(){
    $('.recharge_success_wrap').show();
    return false;
}

$(function(){
    $('.recharge_success').find('.close').click(function(){
        $('.recharge_success_wrap').hide();
    });
});