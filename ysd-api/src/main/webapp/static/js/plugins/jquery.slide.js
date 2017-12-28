var slide =  {
	options : {
		cur			: 0,
		speed		: 6000,
		duration	: 800,
		box			: "#slide",
		item		: ".slideItem",
		pic			: "#slidePic"
	},

	// 初始化
	init : function () {

		// 创建小点点
		$(this.options.box).prepend('<ul class="slideItem"></ul>');

		if (slideData.length > 0) {
			$.each(slideData, function(i, n){
				var s = slide.options.cur==i ? ' class=\"on\"' : "";
				$(".slideItem")
					.css("opacity", 0.85)
					.append('<li'+ s +'>'+(i+1)+'</li>');
			});

			var $item = $(slide.options.item).find("LI");				
			
			$item.each(function(i){
				$(this).click(function(){
					slide.start.smallPicShow(i);
				});
			});
			
			this.start.smallPicShow(this.options.cur);	
			
			$(this.options.box)
								.hover(	function(){clearInterval(slideStart);},
										function(){	slideStart = setInterval("slide.start.loop()", slide.options.speed);});
		
			var slideStart = setInterval("slide.start.loop()", slide.options.speed);			
		}
	},
		
	// 开始
	start : {		
		// 小点点
		smallPicShow : function (n) {			
			var $item = $(slide.options.item).find("LI");			
			$item.removeClass("on").eq(n).addClass("on");			
			$(slide.options.pic)
								.attr("title", slideData[n].title)
								.hide()
								.css("background-image","url("+ slideData[n].pic +")")
								.fadeIn(slide.options.duration);								
			
			
			if (slideData[n].link != undefined){ // 有链接
				$(slide.options.pic).html("<a href="+ slideData[n].link +"  title="+ slideData[n].title +"></a>");
			}
			slide.options.cur = n;		
		},
		// 循环
		loop : function () {
			var $item = $(slide.options.item).find("LI");
			slide.options.cur ++;
			if (slide.options.cur > $item.length - 1) slide.options.cur = 0; 
			
			slide.start.smallPicShow(slide.options.cur);
		}
	}
};

slide.init();