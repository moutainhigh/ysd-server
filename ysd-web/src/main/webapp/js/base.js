
// Ken Popup
qmd = {
	base: "https://www.yueshanggroup.cn",
	img:"https://www.yueshanggroup.cn/img"
};

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

			$KP = $('<div class="KP" id="KP" style="width:'+ wSize[0] +'px;height:'+  wSize[1] +'px" />')
					.appendTo($("body"));
			
			$KPC_html = '<div class="hd">'+
						'	<a href="javascript:void(0)" class="ic1 close" onclick="KP.close()"></a>'+
						'	<strong id="KPTit">'+ wTitle +'</strong>'+
						'</div>'+
						'<div class="bd" id="KPC" style="height:'+ (wSize[1]-32) +'px"></div>';			

			$KP.html($KPC_html);
		}
		
		KP.Mask.show();

		$KP.css({
					"top"	: ($(window).height() - $KP.outerHeight()) / 4 + $(window).scrollTop(),
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

	
	// 保存投标设置
	$("#bcsz_detail")
		.click(function(){
			KP.options.drag = true;
			KP.show("", 308, 168);
			$(KP.options.content)
				.load("popup-page/bcsz_detail.html");
		});

	// 充值
	$("#user_chongzhi_df")
		.click(function(){
			KP.options.drag = true;
			KP.show("充值确认", 524, 260);
			$(KP.options.content)
				.load("popup-page/user_chongzhi_df.html");
		});

	// 确定投标
	$("#tender")
		.click(function(){
			
			var testTime = new Date().getTime();
			var tenderAbleMoney = $("#tenderAbleMoney").val();
			if (tenderAbleMoney=='')tenderAbleMoney=0;
			var tenderContinueMoney = $("#tenderContinueMoney").val();
			if(tenderContinueMoney=='')tenderContinueMoney=0;
			
			var totalTenderMoney1=parseFloat(tenderAbleMoney)+parseFloat(tenderContinueMoney);
			if (totalTenderMoney1<=0) {
				alert("请输入投资金额！");
				return;
			} 
			if (totalTenderMoney1<100) {
				alert("投资金额不能小于100");
				return;
			} 
			$.ajax({
		        type: "get",
		        dataType : "json",
		        url: qmd.base+'/user/checkForTender.do?testTime='+testTime+'&tenderAbleMoney='+tenderAbleMoney+'&tenderContinueMoney='+tenderContinueMoney+'&id='+$("#bId").val(),
		        success: function(data){
		        	if(data=='0') {
		        		alert("请登录！");
		        		window.location.href=qmd.base+'/user/login.do';
		        		return;
		        	} else if(data=='2') {
		        		alert("非投资人不能投资！");
		        		return;
		        	} else if(data=='3'||data=='4') {
		        		alert("账户资金不足，请充值！");
		        		return;
		        	} else if(data =='5'){
		        		alert("体验金不足！");
		        		return;
		        	}
		        	/**
		        	KP.options.drag = true;
					KP.show("确定投标", 400, 430);
					$(KP.options.content)
						.load(qmd.base+"/borrow/poputInvest.do?bId="+$("#borrow_bid").val()+'&tenderAbleMoney='+tenderAbleMoney+'&tenderContinueMoney='+tenderContinueMoney+"&testTime="+testTime);
		      		**/
		        	$("#borrowRaiseInvestForm").submit();
		        
		        },
		        error:function(statusText){
		        	alert("发生错误！");
		        },
		        exception:function(){
		        	alert("发生错误！");
		        }
			});
			
			
		});


});

function alertMessage(message){
	KP.options.drag = true;
	KP.show("提示", 308, 168);
	var show_content = '<div style=" padding:5px 0 0 15px;"><div style="color:#666; font-size:14px; padding:20px 0; margin-bottom: 5px;border-bottom:1px solid #e6e6e6;">'+ message +'</div><div style="text-align:center;"><a  href="javascript:void(0)" onclick="KP.close()" style="display:inline-block; width:95px; height:30px;  line-height:30px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#7c96e8; margin-top:20px;">确定</a></div></div>';
	$(KP.options.content).html(show_content);
	
}