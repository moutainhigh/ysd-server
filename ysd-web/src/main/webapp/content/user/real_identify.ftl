<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户 -个人账户实名认证</title>
<#include "/content/common/meta.ftl">
<script type="text/javascript" src="${base}/static/js/jquery/jquery.card.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/static/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/static/js/jquery/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/static/js/common/ajaxfileupload.js"></script>
<script type="text/javascript" src="${base}/static/js/common/ajaxImageUpload.js"></script>

<script type="text/javascript">
$().ready( function() {
	$("#standard_certification").addClass("current");
	var $realnameForm = $("#realnameForm");
	var $areaSelect = $("#areaSelect");
	
	 // 表单验证
	$realnameForm.validate({
		ignore: "",
		rules: {
			"user.realName":{
				required: true,
				realName:true,
				minlength:2,
				maxlength:20
			},
			"user.cardId":{
				required: true,
				//isIdCardNo:true,
				//remote:"${base}/userCenter/checkCardiId.do"
			},
			"user.payPassword":{
				required: true,
				remote:"${base}/userCenter/ajaxPayPassword.do"
			}
		},
		messages: {
			"user.realName":{
				required: "真实姓名不能为空",
				realName:"真实姓名格式错误",
				minlength: "最少2个字",
				maxlength: "最多20个字"
			},
			"user.cardId":{
				required:"身份证号码不能为空",
				//isIdCardNo:"身份证号码格式不对",
				//remote:"该身份证号已认证"
			},
			"user.payPassword":{
				required: "请输入交易密码",
				remote:"交易密码输入错误"
			}
		},
		errorPlacement: function(error, element) {
		  error.appendTo(element.parent());
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	
});


$(function(){
	$("#member_realname").addClass("nsg nsg_a1");
});
</script>
</head>
<body>
 <!-- header -->
<#include "/content/common/header.ftl">



<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
    <#include "/content/common/user_center_header.ftl">
    
    <div style="width:955px; float:left; background:#fff; clear:both;">
        <#include "/content/common/user_center_left.ftl">
        
        <div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
          <div style="padding-top:30px;">
			<a style="color:#646464;font-family:'宋体';">${Application ["qmd.setting.name"]}</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a style="color:#646464;font-family:'宋体';" >个人账户认证</a>
          </div>
          <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
            		
					<a href="javascript:void(0);" class="hr hre">个人账户认证</a>         
          </div>

           <div style="height:23px;line-height:23px;text-align:center;color:#595757;font-family:'宋体';width：100%；margin-top:0; margin-bottom:30px; clear:both;"></div>  

   <form id="realnameForm" method="post" action="identity.do" enctype="multipart/form-data">
    <table width="100%" cellpadding="0" cellspacing="0">
     <tbody>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">用户名：</td>
         <td style="color:#646464;font-family:'宋体';">${loginUser.username}</td>
       </tr>
       <tr height="20"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">真实姓名：</td>
         <td style="color:#646464;font-family:'宋体';"><input name="user.realName" id="realName" value = "${loginUser.realName!}" style="width:170px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/></td>
       </tr>
       <tr height="20"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">身份证号码：</td>
         <td style="color:#646464;font-family:'宋体';"><input name="user.cardId" value="${loginUser.cardId!}" id="cardId"  type="text" style="width:170px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/></td>
       </tr>
     <tr height="20"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">交易密码：</td>
         <td style="color:#646464;font-family:'宋体';">
         	<#if loginUser.payPassword!>
         		<input name="user.payPassword" id="payPassword" type="password" style="width:170px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px; margin-left:10px;"/>
         	<#else>
          		<a href="${base}/userCenter/toPayPassword.do" >请先设置一个交易密码</a>
          		<input type="hidden"  name="user.payPassword">
          	</#if>
        	 
         </td>
       </tr>
       <tr height="32"></tr>
       <tr>
         <td style="width:100px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"></td>
         <td style="color:#646464;font-family:'宋体';">
          <input type="submit" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" value="确定"/>
          <input type="submit" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#d4d4d4; color:#000; text-align:center; font-size:16px;border-radius:5px;" value="取消"/>
         </td>
       </tr>
     </tbody>
    </table>
    </form>      
 </div>
    </div>
    
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->
<#include "/content/common/footer.ftl">
</body>

</html>
