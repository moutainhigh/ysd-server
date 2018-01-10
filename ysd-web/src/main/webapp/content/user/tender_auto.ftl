<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-自动投标设置</title>
	<#include "/content/common/meta.ftl">
<script type="text/javascript">			
function checkimg(){
	var $code_img = $("#code_img");
	$code_img.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
	
	
	function verifyCode(){
		$("#vCode").html("<a id='vCodeA' title='点击更换' onclick='verifyCodeLink();'><img id = 'code_img' src='' /></a>");
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
		$("#vCode").attr("style", "");
		$("#mycode").select();
	}
	
	function verifyCodeLink(){
		$("#code_img").attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
	}
	
function btnSubmit(st) {

	var reg = /^\d+(\.\d+)?$/;
	if(!reg.test($("#autoTenderMoneyTop").val())||$("#autoTenderMoneyTop").val()<0){
		alertMessage('请输入正确的单次投标限额！');
		return false; 
	}

$("#autoTenderStatus").val(st);

$("#tenderAutoForm").submit();

}

function checkRate(v1,v2) {
	var f1 = parseFloat(v1);
	var f2 = parseFloat(v2);
	if (f1 > 100 || f2 > 100) {
		return true;
	}
	if (f1 > f2) {
		return true;
	}
	
	return false;
	
}

function clickRuleAll() {
	$("#autoTenderMoneyTop").val(0);
	$("#autoTenderMoneyLeave").val(0);
}
</script>
 </head>

 <body>
<!-- header -->
<#include "/content/common/header.ftl">


<#include "/content/common/user_center_header.ftl">

<div class="content">
  <div style="width:1200px; margin:0 auto; padding-bottom:60px; margin-top:20px;">
  
  <#include "/content/common/user_center_left.ftl">
   <!--right-->
    <div style="width:942px; float:right;">
      <div style="border:1px solid #e6e6e6; background:#fff; padding-bottom:0; float:left; margin-bottom:20px;">
       <form id="tenderAutoForm" method="post" action="${base}/user/saveTenderAuto.do"">
         <div style=" width:940px; height:64px; line-height:64px; color:#666666; font-size:16px;">
            <span style="color:#666666; font-size:16px; margin:0 40px 0 20px;">自动投资状态: </span>
            <#if user.autoTenderStatus==1>
  <!--          	     <a href="#" style="display:inline-block; width:105px; height:30px;  line-height:30px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a;">开启自动投资</a>
 		<font style="color:#fd7c1a;">开启状态</font> -->	
  				<input type="button" value="关闭自动投标" onclick="btnSubmit('0');" style="display:inline-block; width:135px; height:40px;  line-height:40px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#ccc;"/>
            <#else>
 <!--             	<font style="color:#fd7c1a;">关闭状态</font> -->
            	<input type="button" value="开启自动投标" onclick="btnSubmit('1');" style="display:inline-block; width:135px; height:40px;  line-height:40px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a;"/>
            	
            </#if>
       
        </div>
     </div>
     <input type="hidden" name="user.autoTenderRule" value="1"/>
      <input type="hidden" name="user.autoTenderStatus" id="autoTenderStatus" value="${user.autoTenderStatus}"/>
      <input type="hidden" name="strtype" id="strtype" value="1"/>
     <div style="border:1px solid #e6e6e6; background:#fff; padding-bottom:0; float:left; margin-bottom:0; width:100%;">
     <!--
        <h3 style="color:#666666; font-size:16px; padding:20px 0 20px 20px;">选择投资产品</h3>
        <ul style="width:900px; margin:0 auto;" id="win_prize">
        <@listing_childlist sign="borrow_business_type"; listingList>
			<#list listingList as listing>
			
			       <li <#if strtype><#if strtype?index_of(listing.keyValue)!=-1> data="1"<#else> data="0"</#if><#else>data="0"</#if> value="${listing.keyValue}" style="float:left; width:263px; cursor:pointer; border:1px solid #e6e6e6; color:#666666; font-size:14px; padding:15px 0 15px 20px; position:relative; margin-right:15px; margin-bottom:30px;">
			       <div style=" margin-bottom:15px;"><img src="<#if listing.keyValue==1>/img/cheyidai.png<#elseif listing.keyValue==2>/img/qirongdai.png</#if>" width="130" height="40" /></div>
			       <div style="padding-bottom:8px;">投资期限：<font style="color:#fd7c1a;">7~365</font> 天</div>
			       <div style="padding-bottom:8px;">年化收益：<font style="color:#006dc1;">10~18%</font></div>
			       <div style="padding-bottom:8px;">还款方式：到期本息还款</div>
			       <div>起投金额：100元 </div>
			       <div style="position:absolute; right:10px; bottom:10px; background:url(<#if strtype><#if strtype?index_of(listing.keyValue)!=-1>/img/a21.png<#else> /img/a20.png</#if><#else>/img/a20.png</#if> ) 0 0 no-repeat; width:42px; height:42px;" class="check_win"></div>
			       </li>
			   </#list>
		</@listing_childlist>  
        </ul>
        -->
        <div style="width:885px; margin:0 auto; background:#f5f5f5; padding:20px 0; clear:both;">
          <h3 style="color:#666666; font-size:14px; padding:0 0 15px 20px;">设置每次最高投资金额<font style=" color:#999999; font-weight:normal;">（最高10万）</font></h3>
          <div style=" padding:0 0 15px 20px;color:#666666; font-size:14px;"><input type="text" style="width:200px; height:30px; padding-left:5px; border:1px solid #e6e6e6; background:#fff;" id="autoTenderMoneyTop" name="user.autoTenderMoneyTop" value="${user.autoTenderMoneyTop}" />&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;<span>当前可用余额：<font style="color:#10b59f; font-weight:bold;">${user.ableMoney?string.currency}</font> 元</span>,<span>有效金额：<font style="color:#fd7c1a; font-weight:bold;">${(((user.ableMoney/100)?int)*100)?string.currency}</font> 元</span></div>
          <div style="color:#999999; font-size:14px;padding:0 0 40px 20px; line-height:25px;">说明：程序自动判断当前剩余可投金额，若可投金额>=你设置的金额，<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;则自动投资你设置的金额，反之则自动投资产品剩余可投金额。</div>
        </div>
       <#if user.autoTenderStatus ==1>
        <div style="text-align:right; padding:20px 35px 60px 0;">
            <input type="button" id="bcsz_detail" value="保存设置"   onclick="btnSubmit('1');" style="display:inline-block; width:135px; height:40px;  line-height:40px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#fd7c1a; margin-right:20px;"/>
            
        </div>
      <#else> 
      	 <div style="text-align:right; padding:20px 35px 60px 0;">
            <input type="button" id="bcsz_detail" value="保存设置"  disabled="disabled"  style="display:inline-block; width:135px; height:40px;  line-height:40px; text-align:center; border-radius:3px; color:#fff; font-size:14px; background:#ccc; margin-right:20px;"/>
        </div>
      </#if>
        <div style="width:885px; margin:0 auto;">
          <h3 style="color:#666666; font-size:14px; padding-bottom:10px; border-bottom:1px solid #e6e6e6;">自动投资说明</h3>
          <div style="color:#666666; font-size:14px; line-height:28px; padding:10px 0 40px 0;">
            1.每个项目，优先进行自动投资，自动投资金额不超过项目金额的50%<br />
            2.每个项目，每个用户最多只能有一笔自动投资<br />
            3.若项目设置了单笔/单人投资金额上限的，则单笔自动投资金额<=该上限值<br />
            4.重新设置后投资排序将调整至末尾，请勿重复修改自动投标设置，以免影响您的投资排序
          </div>
        </div>
     </div>
  </form>
    <div style="width:100%;clear:both; height:1px;"></div>
   </div>
 
  <div style="width:100%;clear:both; height:1px;"></div>
 </div>
</div><!-- content end -->
 
  <#include "/content/common/footer.ftl">
</body>

<script type="text/javascript">
$(function(){
	$('#win_prize li').click(function(){
		var test;
		var str = $("#strtype").val();
		if($(this).attr('data')==0){
			$(this).find('.check_win').css('background','url(/img/a21.png)');
			$(this).attr('data','1');
			if(str==""){
				test=$(this).attr('value');
			}else{
				test = str+","+$(this).attr('value')
			}
		}else{
			var arr=new Array();
			arr=str.split(',');//注split可以用字符或字符串分割
			$(this).find('.check_win').css('background','url(/img/a20.png)');
			$(this).attr('data','0');
			test="";
			for(var i=0;i<arr.length;i++)
				{
					if(arr[i]==$(this).attr('value')){
							continue;
					}
						
					if(test==""){
					 	test =arr[i]; 
					}else{
					 	test = test+","+arr[i];
					}
				}
			
		};
		$("#strtype").val(test);
	});
	$("#header_wdzh").addClass("hq");
	$("#zdtz").addClass("click_current");
	$("#sel_autoTenderLimitBegin").val(${user.autoTenderLimitBegin});
	$("#sel_autoTenderLimitEnd").val(${user.autoTenderLimitEnd});
});
</script>
</html>
