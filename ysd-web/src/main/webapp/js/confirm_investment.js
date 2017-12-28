/**
 * Created by admin on 2016/11/10.
 */
$(".redbag_model").hover(function() {
    $(this).parent().addClass("hover");

}, function() {
    $(this).parent().removeClass("hover");
});
$(".red_bag_warp").click(function() {
	var selected = $(this).attr("hb-selected");
    if(selected) {
    	//不选中
    	$(this).attr("hb-selected","");
        $(this).removeClass("selected");
    }else {
    	//选中
    	$(this).attr("hb-selected","true");
    	$(this).addClass("selected");
    }
    selectHongbao(this);
});

$(function(){
	$('#header_sy a').css('border',0);
	$('#header_xmzx').addClass('current');
	$(".errorMassage").find('.close').click(function(){
		$('.errorMassage').hide();
	})
	$(".errorMassage").find('.error_know').click(function(){
		$('.errorMassage').hide();
	})
});