function toThousands(num) {
    return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
}
function partdata(){
	$.ajax({
		url:'/index/partData.do',
		data:{},
		type:'post',
		success:function(data){
			var datas=JSON.parse(data);
			$(".inverstment_num").html(toThousands(datas.totalTenderMoney));
			$('.person_num').html(toThousands(datas.totalUserNum));
			$('.profit_num').html(toThousands(datas.totalgetMoney));
		}
	})
	setTimeout(function(){partdata()},100000000);
}
$(function() {
	partdata();
	// 轮播
	var bannerSlider = new Slider($('.bannerArea'), {
		time: 8000,
		delay: 2000,
		event: 'hover',
		auto: true,
		mode: 'fade',
		controller: $('.dot'),
		activeControllerCls: 'active1'
	});
	//banner导航
	$('#content .bannerArea > span.left').click(function () {
		bannerSlider.prev()
	});
	$('#content .bannerArea > span.right').click(function () {
		bannerSlider.next()
	});


	
	
  
   
    $("#header_sy").addClass('current');

	// 旋转木马
	Carousel.init($(".rotate"));
	// 无缝滚动
	$('.investScroll').myScroll({
		speed: 40, //数值越大，速度越慢
		rowHeight: 58 //li的高度
	});
	// 右侧工具栏
	var top = $(".navtop");
	var win = $(window);
	win.scroll(function() {
		if (win.scrollTop() > 300) {
			top.fadeIn();
		} else {
			top.fadeOut();
		}
	})

	top.click(function() {
		$("html,body").animate({
			scrollTop: 0
		}, 1000);
	});

	// 热门推荐
	var browser=navigator.appName;
	var b_version=navigator.appVersion ;
	/*var version=b_version.split(";"); 
	var trim_Version=version[1].replace(/[ ]/g,""); */

	$(".investType li").click(function() {
		$(this).addClass('active2').siblings().removeClass('active2');
		$('.gtArea ul').css('display','none');
		$('.gtArea ul').eq($(this).index()).css('display','block');
	});
/*	$(".gtArea li").mouseover(function() {
		if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0"){
			$(this).css("marginTop", 0);
		
		};
	});
	$(".gtArea li").mouseout(function() {
		if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0"){
			$(this).css('border','1px solid #ccc');
			
		}
	});*/
	

	// app下载
	$(".ios").click(function(){
		$(this).css('background-position','0 -73px');
		$(".android").css('background-position','0 -73px');
		$(".downlaod > img").attr("src", "../img/ios11.png");
	});
	$(".android").click(function(){
		$(this).css('background-position','0 0');
		$(".ios").css('background-position','0 0');
		$(".downlaod > img").attr("src", "../img/android11.png");
	});
	
	
});