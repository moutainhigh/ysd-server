
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${Application ["qmd.setting.name"]}-客户管理-推广人员-添加推广人员</title>
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
			<li><a href="#">推广人员</a></li>
			<li><a href="#">添加推广人员</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>添加推广人员</h3>
            <form id="inputSpreadForm">
				<input type = "hidden" id = "spread_userid" name = "spreadMember.userId" >
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td class="text_r org grayBg" width="40">*</td>
		                <td class="text_r grayBg" width="96">推广人员用户名：</td>
		                <td><input class="input2 " id="spread_username" name="spreadMember.uusername"  type="text" autocomplete="off"  onblur="findSpreadName(this);" maxlength="20"/> 
		                	（从现有注册会员中查询）<div id = "div_message"></div></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg"></td>
		                <td class="text_r grayBg">推广人员姓名：</td>
		                <td>
		                	<input type="text" class="input1" disabled id="spread_realname" name="spreadMember.fullName"  class="input2 "  maxlength = "11"/>
		                </td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg"></td>
		                <td class="text_r grayBg">身份证号码：</td>
		                <td><input type="text"  class="input1" disabled id="spread_card_id" name=""  class="input2 "  minlength = "3" maxlength = "11" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" <#if spreadMember! && spreadMember.id! >disabled</#if></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg"></td>
		                <td class="text_r grayBg">手机号码：</td>
		                <td><input type="text"  class="input1" disabled id="spread_phone" name="spreadMember.mobile"  class="input2 "  minlength = "3" maxlength = "11" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" <#if spreadMember! && spreadMember.id! >disabled</#if> </td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">安全密码：</td>
		                <td>
                 			<input type="password" name="user.payPassword" id="payPassword" class="input2"  maxlength="30"/>
							 <label id="payPassword_label" for="user.payPassword"></label>
						</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">验证码：</td>
		                <td><input class="input2" name="mycode" id="mycode" type="text"  id="money"  maxlength="4"/>
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
<script>
	$().ready( function() { 
		$(".tjxx").attr("id","tjxx");
		$("#li_a_tgry").addClass("li_a_select");
		
		var $inputSpreadForm = $("#inputSpreadForm");
		
		$("#submitButton").click(function(){
			
			if($("#spread_username").val()=='' || $("#spread_username").val().length<0){
				alert('请输入推广人员用户名!');
				return false;
			}
			
			if($("#spread_realname").val()=='' || $("#spread_realname").val().length<0){
				alert('请输入推广人员用户名!');
				return false;
			}
			if($("#payPassword").val()=='' || $("#payPassword").val().length<0){
				alert('请输入安全密码!');
				return false;
			}
			$.ajax({
				url: qmd.base+"/spread/ajaxSaveSpreadTg.do",
				data: $inputSpreadForm.serialize(),
				type: "POST",
				dataType: "json",
	            async : false, 
				success: function(data) {
					if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
		        		alert("操作失败！");
		        	} else if (data.status=="success") {
		        		alert(data.message);
		        		window.location.href = qmd.base + '/spread/spreadTg.do';
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
	
	function findSpreadName(obj){
		
		$realname = $("#spread_realname");
		$cardId = $("#spread_card_id");
		$phone = $("#spread_phone");
		$userid = $("#spread_userid");
		
		if(obj.value == ''){
			return false;
		}
		
		$.ajax({
				url: qmd.base + "/user/ajaxUserMessage.do",
				data: {"user.username":obj.value},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					if(data.status=="success"){
						$("#div_message").html('');
						$realname.val(data.user.realName);
						$cardId.val(data.user.cardId);
						$phone.val(data.user.phone);
						$userid.val(data.user.id);
					}else{
						$("#div_message").html('没有此用户！');
						$userid.val('');
						$realname.val('');
						$cardId.val('');
						$phone.val('');
					}
				}
		});
	}
</script>
</body>
</html>