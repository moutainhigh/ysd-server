/* @since:2013/4/9　	* @author:270029616@qq.com ~~~ ! */

// Ken Popup
var KP = {
	options : {
		id			: "#KP",	
		title		: "提示",	
		content		: "#KPC",			// 内容区域
		wsize		: [300, 160],		// iframe 设置宽高
		position	: [0, 0],			// 指定位置
		drag		: false				// 不可拖拽			
	},
	// 遮罩
	Mask : {
		cfg : {
			box		: ".mask",													// 盒子
			opacity	: 0.3														// 透明度				
		},
		// 显示
		show : function () {
			var $_mask = $(this.cfg.box);										// 遮罩层

			if(!$_mask.length) {
				$_mask = $('<div class="mask" />').appendTo($("body"));							
				$_mask
					.height($(document).height())
					.css("opacity", this.cfg.opacity)
					.click( function(){
						// handler
					});
			}	
		},
		
		hide : function () {
			$(this.cfg.box).remove();
		}		
	},
	/*
	 *	add
	 *	title 窗口标题
	 *	w	  窗口宽度
	 *	h	  窗口高度
	 *
	 **/
	show : function(title, w, h){
		var $KP = $(this.options.id),												// 窗口盒子	
			$KPC_html = "";

		if (!$KP.length) {
			var wSize = [w == undefined ? KP.options.wsize[0] : w, h == undefined ? KP.options.wsize[1] : h];			// 窗口大小
			var wTitle = title == undefined ? KP.options.title : title;													// 窗口标题			

			$KP = $('<div class="KP kp1" id="KP" style="width:'+ wSize[0] +'px;height:'+  wSize[1] +'px" />')
					.appendTo($("body"));
			
			$KPC_html = '<div class="hd">'+
						'	<a href="javascript:void(0)" class="close" onclick="KP.close()"></a>'+
						'	<strong id="KPTit">'+ wTitle +'</strong>'+
						'</div>'+
						'<div class="bd" id="KPC" style="height:'+ (wSize[1]-58) +'px"></div>';			

			$KP.html($KPC_html);
		}
		
		KP.Mask.show();

		$KP.css({
					"top"	: ($(window).height() - $KP.outerHeight()) / 2 + $(window).scrollTop(),
					"left"	: ($(window).width() - $KP.outerWidth()) / 2
				}).show();

		if(KP.options.drag) KP.drag();
		

		// ESC close KP
		$(document)
			.keydown(function(e){
				if(e.which == 27) KP.close();
			});
	},
	// close
	close : function(){
		$(this.options.id).remove();
		KP.Mask.hide();
	},
	
	// 窗口拖拽
	drag : function(){
		//初始化参数
		var obj = this;
		this.bar = KP.options.id;
		this.box = KP.options.id;
		
		$(obj.bar).children(".hd").css("cursor", "move").css("color","#fff");

		// 点击开始
		function start(e){		
			obj.offsetX = e.clientX - $(obj.box).offset().left;
			obj.offsetY = e.clientY - $(obj.box).offset().top;

			$(document)
				.bind("mousemove",move)
				.bind("mouseup",stop);

			e.preventDefault();
		}
		
		// 拖动
		function move(e){
			var left  = e.clientX - obj.offsetX;
			var top  = e.clientY - obj.offsetY;
			$(obj.box)
				.css({	
					"left"	: left,
					"top"	: top
				});

			e.preventDefault();
		}
		
		// 停止拖动
		function stop(){
			$(document)
				.unbind("mousemove",move)
				.unbind("mouseup",stop);
		}		

		$(document).ready(function(){
			$(obj.bar).children(".hd")
				.bind("mousedown",start);
		});
	}
};


$(function(){

    // 咨询弹框
    function confirm(message, fn1, fn2) {
        KP.options.drag = true;
        KP.show("提示", 308, 200);
        var show_content = '<div style=" padding:5px 0 0 15px;"><div style="color:#666; font-size:14px; padding:20px 0; margin-bottom: 5px;border-bottom:1px solid #e6e6e6;">'+ message +'</div><div style="text-align:center;"><a  href="javascript:;" style="display:inline-block; width:95px; height:30px;  line-height:30px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#7c96e8; margin-top:20px;" class="confirm">确定</a><a href="javascript:;" onclick="KP.close()" style="display:inline-block; width:95px; height:30px; line-height:30px; text-align:center; border-radius:3px; margin-left: 10px; color:#fff; font-size:14px; background:#a5a5a5; margin-top:20px;">取消</a></div></div>';
        $(KP.options.content).html(show_content).find('.confirm').on('click', function () {
            fn1 && fn1.call(this);
            KP.close();
        });
    }

    // 退出系統
    $('#logout').click(function () {
        var _this = this;
        layer.confirm('是否退出系统？', {
        	title: '提示',
            btn: ['确定','取消'] //按钮
        }, function(){
            window.location.href = $(_this).data('href');
        });
    });
	
	// nav hover
	$(".nav>li")
		.hover(
			function(){	
				$(this).children("ol").css({
					"width"	: $(this).outerWidth() - 1,
					"top"	: $(this).position().top + $(this).height(),
					"left"	: $(this).position().left + 1
				});
				$(this).addClass("on"); 
			},
			function(){	$(this).removeClass("on");}
		);

	
	// 还款计划设置
	$("#repaymentPlan")
		.click(function(){
			KP.options.drag = true;
			KP.show("还款计划设置", 520, 410);
			$(KP.options.content)
				.load("popup-page/repaymentPlan.html");
		});

	// 资金明细详情
	$(".Funds")
		.click(function(){
			
			var testTime = new Date().getTime();
			
			var dfid=this.id;
			
			$.ajax({
		        type: "get",
		        dataType : "json",
		        url: qmd.base+'/user/checkByUserLogin.do',
		        success: function(data){
		        	if(data==false) {
		        		alert("请登录！");
		        		window.location.href=qmd.base+'/user/login.do?loginRedirectUrl=%2Faccount%2Fdetail.do%3Ftype%3Ddetail';
		        		return;
		        	}
		        	KP.options.drag = true;
					KP.show("资金明细详情", 540, 570);
					$(KP.options.content)
						.load(qmd.base+"/account/detailFull.do?id="+dfid+"&testTime="+testTime);
		        	
		        	/**var mydialog = art.dialog({
						lock: true,
						background: '#ccc',
						width: 520,
						height: 545,
						padding: 0,
						title: '资金明细详情'
					});
		        	
		        	$.ajax({
				        type: "get",
				        url: qmd.base+'/account/detailFull.do?id='+dfid+'&testTime='+testTime,
				        success: function(data){
				        	mydialog.content(data);
				        }
		        	});
		        	**/
		        },
		        error:function(statusText){
		        	alert("发生错误！");
		        },
		        exception:function(){
		        	alert("发生错误！");
		        }
			});
			
		});

	// 确定投标
/**	
	$("#tender")
		.click(function(){
			KP.options.drag = true;
			KP.show("投标确认", 540, 540);
			$(KP.options.content)
				.load("popup-page/tender.html");
		});
**/
	
	

	// 查看理财产品投资详情操作
	$(".wmpsBuyView_class").click(function(){
			var testTime = new Date().getTime();
			var $this = $(this);
			var $wmpsBuyId = $this.attr('wmpsBuyId');
        	KP.options.drag = true;
			KP.show("查看投资详情", 540, 540);
			$(KP.options.content).load(qmd.base+"/wmpsBuy/poputView.do?wbid="+$wmpsBuyId+"&testTime="+testTime);
		});
	
	
});