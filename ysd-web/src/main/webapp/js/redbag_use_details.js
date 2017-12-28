/**
 * Created by admin on 2016/10/31.
 */
$(".content .options").find("div").click(function() {
    var index = $(this).index();
    $(this).addClass("current").siblings().removeClass("current");
    if (index == 0) {
        $(".content .redbag").show();
        $(".content .bonuses").hide();
    }
    if (index == 1) {
        $(".content .redbag").hide();
        $(".content .bonuses").show();
        $(".content .bonuses table.all").show();
        $(".content .bonuses table.friend_invest").hide();
        $(".content .bonuses table.invest_bonuses").hide();
        $(".content .bonuses a").first().addClass("current").siblings().removeClass("current");
    }
})
$(".content .bonuses .bonuses_options").find("a").click(function() {
    var index = $(this).index();
    $(this).addClass("current").siblings().removeClass("current");
    if (index == 0) {
        $(".content .bonuses table.all").show();
        $(".content .bonuses table.friend_invest").hide();
        $(".content .bonuses table.invest_bonuses").hide();
    }
    if (index == 1) {
        $(".content .bonuses table.all").hide();
        $(".content .bonuses table.friend_invest").show();
        $(".content .bonuses table.invest_bonuses").hide();
    }
    if (index == 2) {
        $(".content .bonuses table.all").hide();
        $(".content .bonuses table.friend_invest").hide();
        $(".content .bonuses table.invest_bonuses").show();
    }
})
