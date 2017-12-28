
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-资金管理-还款充值</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_account.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">资金管理</a></li>
			<li><a href="${base}/payment/rechargeTo.do">还款充值</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>还款充值</h3>
            <form id="rechargeForm"><#-- action="${base}/payment/repaymentSubmit.do" -->
				<input type="hidden" name="id" value="${loginUser.id}">
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td class="text_r org grayBg" width="40">*</td>
		                <td class="text_r grayBg" width="86">充值金额：</td>
		                <td><input class="input2 " name="userAccountRecharge.money" type="text"  id="money" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,'');" maxlength="12"/> 元</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">充值方式：</td>
		                <td>
		                	<#-- 还款充值-->
								<#list rechargeConfigList as rechargeConfig >
									<#if rechargeConfig.id = 4>
			                       			<label>
				                       			<input type="radio" name="payment" id="rechargeInterface" value="${rechargeConfig.id}" checked/>
				                       			<span style=" display:inline-block; padding-left:10px;">${rechargeConfig.name}</span>
				                       		</label>
			                       		&nbsp;&nbsp;&nbsp;&nbsp;
		                       		</#if>
		                        </#list>
		                
		                </td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">还款充值说明：</td>
		                <td><textarea class="textarea" style="width:252px" name="userAccountRecharge.remark" id="remark" maxlength="300"></textarea></td>
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
			
		$(".lssj").attr("id","lssj");
		$("#li_a_hkcz").addClass("li_a_select");
		
		var $rechargeForm = $("#rechargeForm");
		var $money = $("#money");
		var $miniMoney = ${Application ["qmd.setting.miniMoney"]};
		
		$("#submitButton").click(function(){
			var pay_id = $('input[name="payment"]:checked').val();
			if($money.val()=='' || $money.val().length<0 ){
				alert('请输入正确的充值金额!');
				return false;
			}
			if($("input[name='payment']:checked").val() == 0 && parseInt($miniMoney) > parseInt($money.val())){
				alert("线下充值金额至少为"+$miniMoney+"元");
				return false;
			}
			if($("#payPassword").val()=='' || $("#payPassword").val().length<0){
				alert('请输入安全密码!');
				return false;
			}
			if(pay_id != '0'){
				$.ajax({
					url: qmd.base+"/payment/ajaxRepaymentSubmit.do",
					data: $rechargeForm.serialize(),
					type: "POST",
					dataType: "json",
		            async : false, 
					success: function(data) {
						if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
			        		alert("还款充值失败！");
			        	} else if (data.status=="success") {
			        		alert(data.message);
			        		window.location.href = qmd.base + '/account/detail.do?type=recharge';
			        	} else {
				        	alert(data.message);
			        	}
					},
		            error : function() {  
		                alert('参数错误，请联系管理员！');
		            } 
				});
			}
		});
	})
	</script>
</body>
	
</body>
</html>