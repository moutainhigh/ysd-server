<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}-我的账户 -修改登录密码</title>
<#include "/content/common/meta.ftl">
  </head>
<body>
<!-- header -->
<#include "/content/common/header.ftl">

<!-- content -->
<div class="content clearfix">

	<#include "/content/common/user_center_left.ftl">

	<!-- right -->
	<div class="my-account">
		<!-- location -->
		<div class="location">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt; <a href="${base}/userCenter/index.do">我的账户</a> &gt; 设置密码保护
		</div>
		
     	<div class="tab clearfix">
			<ul>
				<li><a href="${base}/userCenter/toPassword.do"><span>登录密码</span></a></li>
				 <li><a href="${base}/userCenter/toPayPassword.do"><span>支付密码</span></a></li>
                 <li><a class="current" href="javascript:void(0);"><span>密码保护</span></a></li>
              </ul>
		</div>
		
		<div class="tab-con">	
		
			<!-- frm -->
			<div class="frm">
			<form id="setQuestionForm" method="post" onsubmit="return check_form();"  action="${base}/userCenter/questionUpdate.do" >
				<table width="90%">
					<tr>
						<td class="item" colspan="2">请选择一个新的帐号保护问题,并输入答案。帐号保护可以为您以后在忘记密码、重要设置等操作的时候,提供安全保障。</td>
					</tr>
					<tr>
						<td align=right class="item" >请选择问题：</td>
						<td>
							<select id="question" class="ddlist" name="user.question">
		                        <option value="" selected="selected">请选择</option>
		                         <@listing_childlist sign="pwd_protection"; listingList>
								<#list listingList as listing>
								<option value="${listing.description}" >${listing.name}</a>
								</option>
								</#list>
								</@listing_childlist>
								</select>
						</td>
					</tr>
					<tr >
						<td align=right class="item" >请输入答案：</td>
						<td><input type="text" id="answer" name = "user.answer" ></td>
					</tr>
					<tr >
						<td class="item"></td>
						<td><span class="btn_l s3"><input class="submit" type="submit" value="确 定"/></span></td>
					</tr>
						
				</table>
				</form>			
				</div>
			
		
			<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
			</div>
		<div class="tab-con_bottom"></div>
		
	</div>
</div>

<#include "/content/common/footer.ftl">
 </body>
    <script type="text/javascript" src="${base}/static/js/jquery/password_strength_plugin.js"></script>
		
	<script type="text/javascript">
	function check_form(){
	 	var frm = document.forms['setQuestionForm'];
	 	var answer = frm.elements['answer'].value;
	 	var question = frm.elements['question'].value;
		var errorMsg = '';
	  	if (answer.length == 0 ) {
			errorMsg += '答案不能为空！' + '\n';
	  	}else if (question.length == 0 ) {
			errorMsg += '选择的问题不能为空！' + '\n';
	  	}
	  
	  	if (errorMsg.length > 0){
			alert(errorMsg);
			return false;
	  	} else{  
			return true;
	  	}
	}
	</script>
	
<script type="text/javascript">
	$(function(){
		$("#pwd_management_left").addClass("current");
	});
</script>
</html>
