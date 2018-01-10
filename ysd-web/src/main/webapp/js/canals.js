/**
 * Created by admin on 2016/12/15.
 */
$(document).ready(function() {
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
function my_alert() {
    $(".alert").show();
}
$(".close").click(function() {
    $(".alert").hide();
});
