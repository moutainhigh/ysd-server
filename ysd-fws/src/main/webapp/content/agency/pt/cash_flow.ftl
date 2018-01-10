
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-资金管理-放款管理-债权转让放款</title>
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
			<li><a href="#">放款管理</a></li>
            <li><a href="#">债权流转放款</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>债权流转放款</h3>
            <form id="getMoneyForm" >
				<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="text_r org grayBg" width="40">*</td>
                <td class="text_r grayBg" width="96">项目编号：</td>
                <td>
                	<select id="xmbh_id" class="kaqu_r3" name="">
               			<option value="" selected="selected">请选择</option>
               			<#list borrowList as borrow>
							<option value="${borrow.id}" >${borrow.businessCode}</option>
               			</#list>
					</select>
                </td>
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">项目标题：</td>
                <td><div id = "ZQZR_title"></div></td>
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">项目类型：</td>
                <td><div id = "ZQZR_businessType"></div></td>
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">项目总金额：</td>
                <td><div id = "ZQZR_money"></div></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">已完成总金额：</td>
                <td><div id = "ZQZR_money_yes"></div></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">年利率：</td>
                <td><div id = "ZQZR_rate"></div></td>
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">发布时间：</td>
                <td><div id = "ZQZR_date"></div></td>
              </tr>
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">借款人用户名：</td>
				<td><div id = "ZQZR_username"></div></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">借款人姓名：</td>
                <td><div id = "ZQZR_realName"></div></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">已放款金额：</td>
                <td><div id = "ZQZR_fk_money_yes"></div></td>
              </tr> 
          	  <tr>
                <td class="text_r org grayBg"></td>
                <td class="text_r grayBg">可放款金额：</td>
                <td><div id = "ZQZR_fk_money"></div></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">借款人提现账户：</td>
                <td><div id = "ZQZR_cash_account"></div></td>
              </tr> 
              <tr>
                <td class="text_r org grayBg">*</td>
                <td class="text_r grayBg">安全密码：</td>
                <td><input class="input1 " type="password" name="user.payPassword" id="payPassword" maxlength = "30"/></td>
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
$().ready( function() {
	$(".lssj").attr("id","lssj");
		$("#li_a_zqzrfk").addClass("li_a_select");
	$getMoneyForm = $("#getMoneyForm");
	
	$('#xmbh_id').change(function(){
		var borrowId = $(this).val();
		var testTime = new Date().getTime();
		if(borrowId != null && typeof(borrowId) != "undefined" ){ 
			$.ajax({
			        type: "post",
			        dataType : "json",
			        url: qmd.base+'/ajaxBorrowInfo.do?borrowId='+borrowId+'&testTime='+testTime,
			        success: function(data, textStatus){
			        	
			        	if(data.status == "success"){
			        		$("#ZQZR_title").html(data.borrow.name);<#--项目标题-->
			        		$("#ZQZR_businessType").html(data.borrow.showBusinessType);<#--项目类型-->
			        		$("#ZQZR_money").html(data.borrow.account+"元");<#--项目总金额-->
			        		$("#ZQZR_money_yes").html(data.tenderSumMoney+"元");<#--已完成金额【截止昨天24：00】-->
			        		$("#ZQZR_rate").html(data.borrow.rateYearLow+"%");<#--年利率-->
			        		$("#ZQZR_date").html(data.verifyTime);<#--审核时间-->
			        		$("#ZQZR_username").html(data.user.username);<#--借款人用户名-->
			        		$("#ZQZR_realName").html(data.user.realName);<#--借款人姓名-->
			        		$("#ZQZR_fk_money_yes").html(data.cashSumMoney+"元");<#--已放款金额-->
		        			$("#ZQZR_fk_money").html(data.fkMoney+"元");<#--可放款金额-->
			        		if(typeof(data.bankList) == "undefined" || typeof(data.bankList) == "null" || data.bankList == null || data.bankList == ""){
			        			$("#ZQZR_cash_account").html('<input type = "hidden" name = "accountBank.id" value = ""/><a href = "${base}/agency_pt/toBankInput.do?buserid='+data.borrow.borrowerId+'">设置借款人提现银行账户</a>');
			        		}else{
			        		var selHTML = '';
			        			<@compress single_line = true>
		        				 selHTML +='<input type = "hidden" name = "user.id" value = "'+data.borrow.borrowerId+'">
			        						<input type = "hidden" name = "borrowId" value = "'+borrowId+'">
			        						<select id="ZQZR_cash_account_select" class="kaqu_r3" name="accountBank.id">
			                           			<option value="" selected="selected">请选择</option>';
			                	 </@compress>  
			                 	$.each(data.bankList, function(i) {
			                         	 selHTML +='<option value="'+data.bankList[i].id+'" >'+data.bankList[i].name+'（'+data.bankList[i].account+'）</option>';
			                         });
			                   $("#ZQZR_cash_account").html(selHTML+'</select>');
		        			}
			        		
			        	} else {
			        		alert(data.message);
							window.location.reload();
			        	}
			        },
			        error:function(statusText){
			        	alert(statusText);
			        },
			        exception:function(){
			        	alert(statusText);
			        }
				});
			}
	});
	
	$("#submitButton").click(function(){
		if($("#ZQZR_cash_account_select").val() ==''){
			alert('请选择 借款人提现账户');
			return false;
		}
		if($("#payPassword").val()=='' || $("#payPassword").val().length<0){
			alert('请输入安全密码!');
			return false;
		}
		$.ajax({
			url: qmd.base+"/agency_pt/ajaxCashFlowSave.do",
			data: $getMoneyForm.serialize(),
			type: "POST",
			dataType: "json",
            async : false, 
			success: function(data) {
				if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
	        		alert("债权转让放款失败！");
	        	} else if (data.status=="success") {
	        		alert(data.message);
        		window.location.href = qmd.base + '/agency_pt/cashList.do';
	        	} else {
		        	alert(data.message);
	        	}
			},
            error : function() {  
                alert('参数错误，请联系管理员！');
            } 
		});
		
	});
});
</script>
</body>
</html>