/**
 * Created by admin on 2016/11/2.
 */
$(function(){
	$('#header_xmzx').addClass('current');
    $("#header_sy").removeClass('current');
	$('#header_sy a').css('border',0);
});
$(".GoLogin .close").click(function() {
    $(".GoLogin").hide();
});
function goLogin() {
	$("#password").val('');
	$("#username").val('');
	$("#mycode").val('');
	$('.input_tips').css('visibility','hidden');
	verifyCodeLink();
    $(".GoLogin").show();
};
$(".payment_rule").mouseover(function() {
    $(".rule_detail").show();
});
$(".payment_rule").mouseout(function() {
    $(".rule_detail").hide();
});
function checkTenderAbleMoney(){
	var valu = $("#tenderAbleMoney").val();//投资金额
	var now = $("#borrow_balance_").attr("data-balance");//可投金额
	now = parseFloat(now);
	if(valu==""||valu==" "){
		$(".product_name .right .tips").css("display","block");
		$(".product_name .right .tips").text("请输入投资金额！");
		return false;
	}else if((valu < 100) || (valu%100 != 0)) {
		$(".product_name .right .tips").css("display","block");
		$(".product_name .right .tips").text("起投金额必须是100的整数倍！");
		return false;
	}else if (valu > now) {
		$(".product_name .right .tips").css("display","block");
		$(".product_name .right .tips").text("投资金额超出项目可投金额！");
		return false;
	}
	return true;
}
$("#tenderAbleMoney").blur(function() {
	checkTenderAbleMoney();
});

$("#tenderAbleMoney").focus(function() {
	$(".product_name .right .tips").css("display","none");
})

$("#borrowRaiseInvestForm").submit(function() {
	if (!checkTenderAbleMoney()) {
		return false;
	}
})
//轮播图自动跳转
    $(document).ready(function() {
        /*
         *  Simple image gallery. Uses default settings
         简单的图像库。 使用默认设置
         */

        $('.fancybox').fancybox();

        /*
         *  Different effects
         */

        // Change title type, overlay closing speed
        $(".fancybox-effects-a").fancybox({
            helpers: {
                title : {
                    type : 'outside'
                },
                overlay : {
                    speedOut : 0
                }
            }
        });

        // Disable opening and closing animations(动画), change title type
        $(".fancybox-effects-b").fancybox({
            openEffect  : 'none',
            closeEffect	: 'none',

            helpers : {
                title : {
                    type : 'over'
                }
            }
        });

        // Set custom style, close if clicked, change title type and overlay color
        $(".fancybox-effects-c").fancybox({
            wrapCSS    : 'fancybox-custom',
            closeClick : true,

            openEffect : 'none',

            helpers : {
                title : {
                    type : 'inside'
                },
                overlay : {
                    css : {
                        'background' : 'rgba(238,238,238,0.85)'
                    }
                }
            }
        });

        // Remove padding, set opening and closing animations, close if clicked and disable overlay
        $(".fancybox-effects-d").fancybox({
            padding: 0,

            openEffect : 'elastic',
            openSpeed  : 150,

            closeEffect : 'elastic',
            closeSpeed  : 150,

            closeClick : true,

            helpers : {
                overlay : null
            }
        });

        /*
         *  Button helper. Disable animations, hide close button, change title type and content
         */

        $('.fancybox-buttons').fancybox({
            openEffect  : 'none',
            closeEffect : 'none',

            prevEffect : 'none',
            nextEffect : 'none',

            closeBtn  : false,

            helpers : {
                title : {
                    type : 'inside'
                },
                buttons	: {}
            },

            afterLoad : function() {
                this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
            }
        });


        /*
         *  Thumbnail helper. Disable animations, hide close button, arrows and slide to next gallery item if clicked
         */

        $('.fancybox-thumbs').fancybox({
            prevEffect : 'none',
            nextEffect : 'none',

            closeBtn  : false,
            arrows    : false,
            nextClick : true,

            helpers : {
                thumbs : {
                    width  : 50,
                    height : 50
                }
            }
        });

        /*
         *  Media helper. Group items, disable animations, hide arrows, enable media and button helpers.
         */
        $('.fancybox-media')
            .attr('rel', 'media-gallery')
            .fancybox({
                openEffect : 'none',
                closeEffect : 'none',
                prevEffect : 'none',
                nextEffect : 'none',

                arrows : false,
                helpers : {
                    media : {},
                    buttons : {}
                }
            });

        /*
         *  Open manually
         */

        $("#fancybox-manual-a").click(function() {
            $.fancybox.open('1_b.jpg');
        });

        $("#fancybox-manual-b").click(function() {
            $.fancybox.open({
                href : 'iframe.html',
                type : 'iframe',
                padding : 5
            });
        });

        $("#fancybox-manual-c").click(function() {
            $.fancybox.open([
                {
                    href : '1_b.jpg',
                    title : 'My title'
                }, {
                    href : '2_b.jpg',
                    title : '2nd title'
                }, {
                    href : '3_b.jpg'
                }
            ], {
                helpers : {
                    thumbs : {
                        width: 75,
                        height: 50
                    }
                }
            });
        });
        //点击原点图标变红色，并跳转到下一个版面
        var dnum = 1;
        var dnum_sum = $(".data_nav span").length;

        $(".data_nav span").click(function() {
            dnum = $(this).index();
            var move = (dnum-1)*(-1080);
            $(this).addClass("current").siblings().removeClass("current");
            $(".data_public .data p").css("left", move+"px");
        });
        //点击右导航按钮，红点跳向下一个，图片进入下一版面
        $(".data_nav i.right").click(function() {
            dnum++;
            if(dnum > dnum_sum) {
                dnum = 1;
            }
            $(".data_nav span").eq(dnum-1).addClass("current").siblings().removeClass("current");
            var move = (dnum-1)*(-1080);
            $(".data_public .data p").css("left", move+"px");
        });
        //点击左导航按钮，红点跳向下一个，图片进入下一版面
        $(".data_nav i.left").click(function() {
            dnum--;
            if(dnum < 1) {
                dnum = dnum_sum;
            }
            $(".data_nav span").eq(dnum-1).addClass("current").siblings().removeClass("current");
            var move = (dnum-1)*(-1080);
            $(".data_public .data p").css("left", move+"px");
        });


    });
