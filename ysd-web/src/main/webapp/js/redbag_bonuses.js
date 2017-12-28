/**
 * Created by admin on 2016/10/31.
 */
$(".content .options").find("div").click(function() {
    var index = $(this).index();
    $(this).addClass("current").siblings().removeClass("current");
    if (index == 0) {
        //$(".content .red_bag").show();
        //$(".content .content_bonuses").hide();
        $(".red_bag .redbag_options span").first().addClass("current").siblings().removeClass("current");
        $(".red_bag .un").show();
    }
    if (index == 1) {
        //$(".content .red_bag").hide();
        //$(".content .content_bonuses").show();
        $(".content_bonuses .redbag_options span").first().addClass("current").siblings().removeClass("current");
        $(".content .content_bonuses table.all").show();
        $(".content .content_bonuses table.friend_invest").hide();
        $(".content .content_bonuses table.invest_bonuses").hide();
    }
})
$(".content .red_bag .redbag_options").find("span").click(function() {
    var index = $(this).index();
    $(this).addClass("current").siblings().removeClass("current");
    if (index == 0) {
        $(".content .red_bag > .un").show();
        $(".content .red_bag > .used").hide();
        $(".content .red_bag > .overdue").hide();
    }
    if (index == 1) {
        $(".content .red_bag > .un").hide();
        $(".content .red_bag > .used").show();
        $(".content .red_bag > .overdue").hide();
    }
    if (index == 2) {
        $(".content .red_bag > .un").hide();
        $(".content .red_bag > .used").hide();
        $(".content .red_bag > .overdue").show();
    }
})
$(".content .content_bonuses .redbag_options").find("span").click(function() {
    var index = $(this).index();
    $(this).addClass("current").siblings().removeClass("current");
    if (index == 0) {
        $(".content .content_bonuses > .all").show();
        $(".content .content_bonuses > .friend_invest").hide();
        $(".content .content_bonuses > .invest_bonuses").hide();
    }
    if (index == 1) {
        $(".content .content_bonuses > .all").hide();
        $(".content .content_bonuses > .friend_invest").show();
        $(".content .content_bonuses > .invest_bonuses").hide();
    }
    if (index == 2) {
        $(".content .content_bonuses > .all").hide();
        $(".content .content_bonuses > .friend_invest").hide();
        $(".content .content_bonuses > .invest_bonuses").show();
    }
})