$(function(){
    $('.partner_wrap').find('.fr').click(function(){
        $(this).parent('.partner_wrap').toggleClass('on');
    });
    $('.partner_wrap').find('.partner_content').click(function(){
        $(this).parent('.partner_wrap').toggleClass('on');
    });
});