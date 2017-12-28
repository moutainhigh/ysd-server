
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-客户管理-门店充值</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_customer.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">客户管理</a></li>
			<li><a href="${base}/payment/rechargeToAgency.do">门店充值</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>门店充值</h3>
            <form id="rechargeForm">
				<input type="hidden" name="id" value="${loginUser.id}">
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td class="text_r org grayBg" width="40">*</td>
		                <td class="text_r grayBg" width="86">充值用户名：</td>
		                <td><input class="input2 " name="userAccountRecharge.username" id="usernameId" maxlength = "20"/></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40">*</td>
		                <td class="text_r grayBg" width="86">充值金额：</td>
		                <td><input class="input2 " name="userAccountRecharge.money" type="text"  id="money" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'');" maxlength="12"/> 元</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">充值方式：</td>
		                <td>
		                	<#list rechargeConfigListAgency as rechargeConfig >
		                       		<label><input type="radio" name="payment" id="rechargeInterface" value="${rechargeConfig.id}"<#if rechargeConfig_index =0> checked </#if>/>
		                       		<#if rechargeConfig.logoPath!>
		                       			<img src = "<@imageUrlDecode imgurl="${rechargeConfig.logoPath}"; imgurl>${imgurl}</@imageUrlDecode>" class="vam" alt="${rechargeConfig.name}"/>
		                       		<#else>
		                       			${rechargeConfig.name}
		                       		</#if>&nbsp;&nbsp;&nbsp;&nbsp;</label>
		                        </#list>
		                </td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86"><#if firstRechargeConfig.paymentFeeType==2||firstRechargeConfig.paymentFeeType==3>充值奖励：<#else>充值手续费：</#if></td>
		                <td><div id = "paymentFee_div">0.00元</div></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg"></td>
		                <td class="text_r grayBg">实际到账：</td>
		                <td><em class="c1" id = "actualToAccount_div">0.00元</em></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">安全密码：</td>
		                <td>
                 			<input type="password" name="user.payPassword" id="payPassword" class = "input2 "  maxlength="30"/>
							 <label id="payPassword_label" for="user.payPassword"></label>
						</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">验证码：</td>
		                <td><input class="input2 " name="mycode" id="mycode" type="text"  id="money"  maxlength="4"/>
		                	<a class="kaqu_yiyi">
		                	<img id = "code_img" src="${base}/rand.do" title="点击图片重新获取验证码" />
		                	</a></td>
		              </tr>                                                                             
		              <tr>
		                <td class="text_r grayBg">&nbsp;</td>
		                <td class="text_r grayBg">&nbsp;</td>
		            	<td height="40">
		                	<input class="btn1 white" type="button" id = "submitButton" value="提交">
		       	   			<input class="btn2 ml_15" type="reset" value="重置">
		                </td>
		              </tr>
		        	</table> 
            </form>       
        </div>                
    </div>
    
</div>

	<#include "/content/common/footer.ftl">
<script type="text/javascript">
	$().ready(function() {
			
		$(".tjxx").attr("id","tjxx");
		$("#li_a_mdcz").addClass("li_a_select");
			
		var $rechargeForm = $("#rechargeForm");
		var $money = $("#money");
		var $radio_c=$(":input[name='payment']");
		
		$("#submitButton").click(function(){
			if($money.val()=='' || $money.val().length<0 ){
				alert('请输入正确的充值金额!');
				return false;
			}
			
			if($("#usernameId").val()=='' || $("#usernameId").val().length<0){
				alert('请输入充值用户!');
				return false;
			}
			
			if($("#payPassword").val()=='' || $("#payPassword").val().length<0){
				alert('请输入安全密码!');
				return false;
			}
				$.ajax({
					url: qmd.base+"/payment/ajaxSubmitAgency.do",
					data: $rechargeForm.serialize(),
					type: "POST",
					dataType: "json",
		            async : false, 
					success: function(data) {
						if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
			        		alert("还款充值失败！");
			        	} else if (data.status=="success") {
			        		alert(data.message);
			        		window.location.href = qmd.base + '/account/rechargeAgencyList.do';
			        	} else {
				        	alert(data.message);
			        	}
					},
		            error : function() {  
		                alert('参数错误，请联系管理员！');
		            } 
				});
		});
		$money.blur(function(){
			reality();
		})
		
		$radio_c.change(function(){
			//判断是否为线下充值
			$this = $(this);
			if($this.val()==0){
				$(".ofg_c").show();
				$("#jl_sxf_div").html("充值奖励：");
			}else{
				$("#jl_sxf_div").html("充值手续费：");
				$(".ofg_c").hide();
			}
		
			reality();
		});
		
		//计算实际到账金额
		function reality(){
			var rad = $(':input:radio:checked').val();
			if($money.val()!=''){
				$.ajax({
					url: qmd.base + "/payment/ajaxPaymentFee.do",
					data: {"payment": rad,"userAccountRecharge.money": $money.val()},
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						if(data.status == "success"){
							var $paymentFeeDiv = $("#paymentFee_div");
							var $actualToAccountDiv = $("#actualToAccount_div");
							$paymentFeeDiv.html(data.paymentFee+'元');
							$actualToAccountDiv.html(data.actualToAccount+'元');
						}else if(data.status == "warn"){
							$money.val('');
							alert(data.message);
						}
					}
				});
			}
		}
		
	});
	</script>
</body>
	
</body>
</html>