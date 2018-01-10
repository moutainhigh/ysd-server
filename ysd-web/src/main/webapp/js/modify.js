$(function(){
    $('.options').find('div').click(function(){
        var index=$(this).index();
        $(this).addClass('current').siblings().removeClass('current');
        $(this).parents('.content').find('.deal').hide();
        $(this).parents('.content').find('.deal').eq(index).show();
    });
});