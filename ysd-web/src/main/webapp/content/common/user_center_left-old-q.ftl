<div style="width:250px; float:left;">
        <h3 style="text-align:center; height:67px; line-height:67px; background:#fd7c1a; font-size:20px; color:#fff;"><a href="${base}/userCenter/borrowDetailList.do" style="display:block;color:#fff">会员中心</a></h3>
        <ul class="left_kuang" style=" border:1px solid #e6e6e6;">
        <#-- li 下 a 标签选中样式 class="click_current" -->
          <li><a id ="cztx" href="${base}/account/detail.do?type=recharge" class="center_link">充值提现</a></li>
          <li><a id ="zjjl" href="${base}/account/detail.do?type=detail"  class="center_link">资金记录</a></li>
          <li><a id ="yhkgl" href="${base}/userCenter/toBank.do"  class="center_link">绑卡认证</a></li>
     	  <li><a id ="zdtz" href="${base}/user/tenderAuto.do"  class="center_link">自动投资</a></li>
          <li><a id ="hbjjl" href="${base}/award/hongbao.do"  class="center_link">红包及奖励</a></li>
        </ul>
</div>
<script>
	//$('.center_link').click(function(){
	//	var href = $(this).attr('data-href');
	//	$.ajax({
	//		url: "${base}/user/ajaxCheckRealStatus.do",
	//		type: "POST",
	//		dataType: "json",
	//		cache: false,
	//		success: function(data) {
	//			if(data.status=="success"){
	//				window.location.href = href;
	//			}else{
	//				alertMessageCenter(data.message);
	//			}
	//		}
	//	});
	//});
	
	//function alertMessageCenter(message){
	//	KP.options.drag = true;
	//	KP.show("提示", 330, 180);
	//	var show_content = '<div style=" padding:5px 10px 0;"><div style="color:#666; font-size:14px; padding:20px 0; margin-bottom: 5px;border-bottom:1px solid #e6e6e6;">'+ message +'</div><div style="text-align:center;"><a  href="${base}userCenter/toBank.do" style="display:inline-block; width:95px; height:30px;  line-height:30px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a; margin-top:20px;">确定</a></div></div>';
	//	$(KP.options.content).html(show_content);
	//}
</script>
 
 