$(document).ready(function(){
	//var filterurl = '/index.php/production/filter';
	/**
	 * 登录表单
	 */
	$('.topbtn').click(function() {
		//如果点击的按钮已经选中
		if($(this).parent().parent().children('.topbox').css('visibility') == 'visible'){
			$('.topbtn').css('background','');
			$('.topbox').css('visibility','hidden');
		}
		//如果没有选中
		else{
			//先隐藏所有
			$('.topbtn').css('background','');
			$('.topbox').css('visibility','hidden');
			//显示选中
			$(this).css('background','#dddddd');
			$(this).parent().parent().children('.topbox').css('visibility','visible');
			$(this).parent().parent().children('.topbox').focus();
		}
	});
	$(".topbtn,.topbox").click(function(e){
    	e.stopPropagation();
    });
	$('html').click(function(){
		$('.topbtn').css('background','');
		$('.topbox').css('visibility','hidden');
	});
	/* $('.topbox').blur(function() {
		$(this).parent().children('.topbtn').css('background','');
		$(this).parent().children('.topbox').css('visibility','hidden');
	}); */
	/**
	 * 自定义地区弹出框
	 */
	$('.areabtn').click(function() {
		//如果点击的按钮已经选中
		if($(this).parent().children('.areabox').css('visibility') == 'visible'){
			//$('.areabtn').css('background','');
			$('.areabox').css('visibility','hidden');
		}
		//如果没有选中
		else{
			//先隐藏所有
			//$('.areabtn').css('background','');
			$('.areabox').css('visibility','hidden');
			//显示选中
			//$(this).css('background','#dddddd');
			$(this).parent().children('.areabox').css('visibility','visible');
			$(this).parent().children('.areabox').focus();
			
		}
	});
	$(".areabtn,.areabox").click(function(e){
    	e.stopPropagation();
    });
	$('html').click(function(){
		//$('.areabtn').css('background','');
		$('.areabox').css('visibility','hidden');
	});
	
	
	
	
	/**
	 * 用户中心 左侧导航菜单
	 */
	/*$('#navigation ul li ul').hover(
		function(){
			$(this).prev().addClass('hover');
		},
		function () {
			$(this).prev().removeClass("hover");
		}
	);*/
	
	

	
	
	
	/**
	 * 会员中心 ajax修改会员信息
	 */
	//隐藏文本框
	$('.editrealname').live('click',function(){
		$(this).parent().css('display','none');
		$('.saverealname').parent().css('display','inline');
	});
	$('.editphone').live('click',function(){
		$(this).parent().css('display','none');
		$('.savephone').parent().css('display','inline');
	});
	$('.editemail').live('click',function(){
		$(this).parent().css('display','none');
		$('.saveemail').parent().css('display','inline');
	});
	$('.editqq').live('click',function(){
		$(this).parent().css('display','none');
		$('.saveqq').parent().css('display','inline');
	});
	$('.editaddress').live('click',function(){
		$(this).parent().css('display','none');
		$('.saveaddress').parent().css('display','inline');
	});
	$('.editzipcode').live('click',function(){
		$(this).parent().css('display','none');
		$('.savezipcode').parent().css('display','inline');
	});
	//ajax提交修改的信息
	$('.saverealname').live('click',function(){
		var realname = $('.submitrealneme').attr('value');
		$.post('edituserinfo',{realname:realname},function(data){
			$('.submitrealneme').parent().hide();
			$('.editrealname').parent().show();
			$('.nativerealname').html(realname);
		});
	});
	$('.savephone').live('click',function(){
		var phone = $('.submitphone').attr('value');
		$.post('edituserinfo',{phone:phone},function(data){
			$('.submitphone').parent().hide();
			$('.editphone').parent().show();
			$('.nativephone').html(phone);
		});
	});
	$('.saveemail').live('click',function(){
		var email = $('.submitemail').attr('value');
		$.post('edituserinfo',{email:email},function(data){
			$('.submitemail').parent().hide();
			$('.editemail').parent().show();
			$('.nativeemail').html(email);
		});
	});
	$('.saveqq').live('click',function(){
		var qq = $('.submitqq').attr('value');
		$.post('edituserinfo',{qq:qq},function(data){
			$('.submitqq').parent().hide();
			$('.editqq').parent().show();
			$('.nativeqq').html(qq);
		});
	});
	$('.saveaddress').live('click',function(){
		var address = $('.submitaddress').attr('value');
		$.post('edituserinfo',{address:address},function(data){
			$('.submitaddress').parent().hide();
			$('.editaddress').parent().show();
			$('.nativeaddress').html(address);
		});
	});
	$('.savezipcode').live('click',function(){
		var zipcode = $('.submitzipcode').attr('value');
		$.post('edituserinfo',{zipcode:zipcode},function(data){
			$('.submitzipcode').parent().hide();
			$('.editzipcode').parent().show();
			$('.nativezipcode').html(zipcode);
		});
	});
	//取消编辑
	$('.cancelrealname').live('click',function(){
		$(this).parent().css('display','none');
		$('.nativerealname').parent().css('display','inline');
	});
	$('.cancelphone').live('click',function(){
		$(this).parent().css('display','none');
		$('.nativephone').parent().css('display','inline');
	});
	$('.cancelemail').live('click',function(){
		$(this).parent().css('display','none');
		$('.nativeemail').parent().css('display','inline');
	});
	$('.cancelqq').live('click',function(){
		$(this).parent().css('display','none');
		$('.nativeqq').parent().css('display','inline');
	});
	$('.canceladdress').live('click',function(){
		$(this).parent().css('display','none');
		$('.nativeaddress').parent().css('display','inline');
	});
	$('.cancelzipcode').live('click',function(){
		$(this).parent().css('display','none');
		$('.nativezipcode').parent().css('display','inline');
	});

	/**
	 * s设置头像
	 */
	$('.editavatar').click(function(){
		$(this).hide();
		$(this).next().show();
	});
	
	/**
	 * 自定义地区点击省份显示城市
	 */
	$('.areabox a').click(function(){
		$('.areabox a').removeClass('select');
		var areashow = $(this).attr('for');
		$('.subarea').css('display','none');
		//alert("'."+areashow+"'");
		$("."+areashow).css('display','block');
		$(this).addClass('select');
		$('.f-area a:first').removeClass('select');
	});
	
	/**
	 * 会员中心 菜单
	 */
	$("#accordion > li").click(function(){
		//箭头全部朝下
		$('.ico_arr_up').addClass('ico_arr_down');
		$('.ico_arr_down').removeClass('ico_arr_up');
		
		if(false == $(this).next().is(':visible')) {
			$('#accordion > ul').slideUp(300);
			//当前箭头向上
			$(this).children('.ico_arr_down').addClass('ico_arr_up');
			$(this).children('.ico_arr_up').addClass('ico_arr_down');
		}
		$(this).next().slideToggle(300);
	});
	$('#accordion > ul:eq(0)').show();
	
	/**
	 * 弹出登录框
	 */
	$('a.poplight[href^=#]').click(function() {
		var popID = $(this).attr('rel');
		var popURL = $(this).attr('href');
				
		var query= popURL.split('?');
		var dim= query[1].split('&');
		var popWidth = dim[0].split('=')[1];

		$('#' + popID).fadeIn().css({ 'width': Number( popWidth ) }).prepend('<a href="#" class="close"><span class="close_pop"></span></a>');
		
		var popMargTop = ($('#' + popID).height() + 80) / 2;
		var popMargLeft = ($('#' + popID).width() + 80) / 2;
		
		$('#' + popID).css({ 
			'margin-top' : -popMargTop,
			'margin-left' : -popMargLeft
		});
		
		$('body').append('<div id="fade"></div>');
		$('#fade').css({'filter' : 'alpha(opacity=80)'}).fadeIn();
		
		return false;
	});
	$('a.close, #fade').live('click', function() {
	  	$('#fade , .popup_block').fadeOut(function() {
			$('#fade, a.close').remove();  
	  	});
		return false;
	});
	
	
	/**
	 * 借款页面 抵押物所在地选择
	 */
	$('.borrow_area .needarea a').click(function(){
		//选择器的值
		var filtervalue = $(this).attr('v');
		//选择器的文本
		var filtertext = $(this).text();
		$('input[name=areatext]').val(filtertext);
		$('input[name=area]').val(filtervalue);
	});


	//显示添加银行卡表单
	$('#addcard_showform').click(function(){
		$('#addcardform').toggle(300);
	});


	//d点击验证按钮，显示验证银行卡表单
	$('.zhanghao-yzbtn').click(function(){
		var cardid = $(this).attr('cardid');
		$('.zhanghao-yzbox').each(function(){
			if($(this).attr('cardid') == cardid){
				$(this).toggle();
			}
		})
	});


});
