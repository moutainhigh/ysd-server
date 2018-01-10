/**
 * Created by admin on 2016/10/28.
 */
// 是否登录提示
$(function() {
	//2017-9-14--zn
	$(".droupdown").hover(function(){
		$(this).addClass("active");
	},function(){
        $(this).removeClass("active");
	});
    $("#header .headArea .nav .last_li").mouseover(function() {
        $("#header .headArea .nav .last_li > div").show();
    });
    $("#header .headArea .nav .last_li").mouseout(function() {
        $("#header .headArea .nav .last_li > div").hide();
        $(this).removeClass("active");
    });
    $(".rightNav").find('.navtop img').hover(function(){
    	$(this).attr('src','../img/top.png');
    	//alert($(this).attr('src'));
    },function(){
    	$(this).attr('src','../img/top1.png');
    });
    $("div.already_login a, div.un_login a").hover(function() {
    	$(this).css("color", "#ef3e44");
    }, function() {
    	$(this).css("color", "#333");
    });
    
    //右侧
    $('.calc').click(function(){
    	$('.calculator').show();
    });
    $(".calcubtn").find('a').click(function(){
		var Tmoney=parseInt($('input[name=calcumoney]').val());
		if(Tmoney==0||Tmoney=='undefined'||typeof(Tmoney)=='undefined'){
			alert('请输入正确的投资金额');
		}
		var Interest=parseFloat($('input[name=calcuInterest]').val())/100;
		if(Interest==0||Interest=='undefined'||typeof(Interest)=='undefined'){
			alert('请输入正确的年化利率');
		}
		var date=parseInt($('input[name=calcudate]').val());
		if(date==0||date=='undefined'||typeof(date)=='undefined'){
			alert('请输入正确的项目期限');
		}
		var calcu=Tmoney*Interest*date/360;
		calcu=Math.round(calcu*100)/100;
		$('.moneyBX').find('span').html(Tmoney+calcu);
		$('.moneyLX').find('span').html(calcu);
	});
	$('.calcuCont div').hover(function(){
		$(this).find('ul').show();
	},function(){
		$(this).find('ul').hide();
	});
	$('.calcuCont ul').find('li').click(function(){
		$(this).parents('.interest').find('input').val($(this).html());
		$(this).parent('ul').hide();
	})
	$('.calcuClose').click(function(){
		$('.calculator').hide();
    	$('.calculator').find('input').val('');
    	$('.moneyBX').find('span').html('0.00');
    	$('.moneyLX').find('span').html('0.00');
	})
	$(window).scroll(function(){
		if($(window).scrollTop()>140){
		$(".main_nav").addClass("guding");
		 }else{
		$(".main_nav").removeClass("guding");
		}
	});
});