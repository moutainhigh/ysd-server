$(function(){
	$("#borrowInvest").click(function() {
			$.ajax({
		        type: "post",
		        dataType : "json",
		        data: $('#bInvest').serialize(),
		        url: 'investDo.do',
		        success: function(data, textStatus){
		        	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
		        		//alert("未获到错误信息");
		        		alert("投标成功");
		        		location.href = "detail.do?bId=" + $('#borrowId').val();
		        		//location.reload();
		        	}else{
			        	alert(data.errorMsg);
		        		//location.href = "detail.do?bId=" + $('#borrowId').val();
		        	}
		        },
		        error:function(statusText){
		        	alert(statusText);
		        },
		        exception:function(){
		        	alert(statusText);
		        }
		     });
		});
 });
function lxfEndtime(){
    $(".lxftime").each(function(){
          var lxfday=$(this).attr("lxfday");
          var balance=$(this).attr("balance");
          var endtime = new Date($(this).attr("endtime")).getTime();
          var nowtime = new Date().getTime();
          var youtime = endtime-nowtime;
          var seconds = youtime/1000;
          var minutes = Math.floor(seconds/60);
          var hours = Math.floor(minutes/60);
          var days = Math.floor(hours/24);
          var CDay= days ;
          var CHour= hours % 24;
          var CMinute= minutes % 60;
          var CSecond= Math.floor(seconds%60);
          if(balance == "0"){
        	  $(this).html("此标已满");
        	  $("#borrowInvest").attr({"disabled":"disabled"});
        	  $("#borrowInvests").attr({"disabled":"disabled"});
        	  return;
          }
          if(endtime<=nowtime){
                  $(this).html("招标期已过");
            	  $("#borrowInvest").attr({"disabled":"disabled"});
              	  $("#borrowInvests").attr({"disabled":"disabled"});
                  }else{
                	  //$("#borrowInvest").removeAttr("disabled");
                          if($(this).attr("lxfday")=="no"){
                                  $(this).html("<i></i><span>"+CHour+"</span>时<span>"+CMinute+"</span>分<span>"+CSecond+"</span>秒");
                                  }else{
                  $(this).html("<i></i><span>"+days+"</span><em>天</em><span>"+CHour+"</span><em>时</em><span>"+CMinute+"</span><em>分</em><span>"+CSecond+"</span><em>秒</em>");
                          }
                          setTimeout("lxfEndtime()",1000);
                  }
    });
};
$(function(){
	lxfEndtime();
});
function changeValidateCode(obj) {
	var timenow = new Date().getTime();
		//每次请求需要一个不同的参数，否则可能会返回同样的验证码
	//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
	obj.src= qmd.base+"/rand.do?d="+timenow;
}
