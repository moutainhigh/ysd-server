$(function(){
    $(".assets_bottom .nav").find('div').click(function(){
        var index=$(this).index();
        $(this).addClass('current').siblings().removeClass('current');
        $('.option_content').hide();
        $('.option_content').eq(index).show();
    });
    $(".fund_record .nav").find('li').click(function(){
        var index=$(this).index();
        $(this).addClass('current').siblings().removeClass('current');
        $(this).parents('.fund_record').find('div').hide();
        $(this).parents('.fund_record').find('div').eq(index).show();
    });
    $('.copy').click(function(){
        $('#copycont').select();
        document.execCommand("Copy");
    })
});