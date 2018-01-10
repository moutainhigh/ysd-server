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
$(function(){
	partdata();
	$('#header_xmzx').addClass('current');
	$('#header_sy a').css('border',0);
    // 无缝滚动
    $('.investScroll').myScroll({
        speed: 40, //数值越大，速度越慢
        rowHeight: 58 //li的高度
    });
    $('.questionScroll').myScroll({
        speed: 40, //数值越大，速度越慢
        rowHeight: 58 //li的高度
    });
    $(".navList ul > li").click(function() {
    	$(this).addClass("active").siblings().removeClass("active");
    });
});
function queryBorrow(type,selected){
	if(type === 1){//项目类型
		$("#qbtype_input").val(selected);
	}else if(type === 2){//项目状态
		$("#qbstatus_input").val(selected);
	}else if(type === 3){//项目期限
		$("#qblimit_input").val(selected);
	}
	$("#pageNumber").val(1);
	$("#listForm").submit();
}

