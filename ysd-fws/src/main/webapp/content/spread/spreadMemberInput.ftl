
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-客户管理-推广渠道-<#if spreadMember.id! >编辑<#else>新增</#if>推广渠道</title>
	<#include "/content/common/meta.ftl">
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_customer.ftl">
			<#--><iframe name="MainFrame" id="mainfrm" src="table.html" style="width: 100%; height: 100%; " frameborder="0" scrolling="auto">
      	  </iframe>-->
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">客户管理</a></li>
			<li><a href="${base}/spread/spreadMemberList.do">推广渠道</a></li>
			<li><a href="#"><#if spreadMember.id! >编辑<#else>新增</#if>推广渠道</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3><#if spreadMember.id! >编辑<#else>新增</#if>推广渠道</h3>
            <form id="inputSpreadForm">
				<input type="hidden" name="id" value="${spreadMember.id}"/>
				<input type="hidden" name="spreadMember.id" value="${spreadMember.id}"/>
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
		             <tr>
		              	<td class="text_r org grayBg" width="40">*</td>
		                <td class="text_r grayBg" width="86">类型：</td>
		                <td>
		                	<label><input type="radio" name="spreadMember.sex"  value="1"  class="sex" <#if spreadMember.sex=='' || spreadMember.sex==1 >checked</#if>>个人</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<label><input type="radio" name="spreadMember.sex"  value="2"  class="sex" <#if spreadMember.sex == 2>checked</#if>>企业</label>
						</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">名称：</td>
		                <td><input type="text"  id="id_fullName" name="spreadMember.fullName" class="kaqu_w100"  value="${spreadMember.fullName}"  maxlength = "20"/></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">手机：</td>
		                <td><input type="text"  id="id_mobile" name="spreadMember.mobile" class="kaqu_w100"   value="${spreadMember.mobile}" maxlength = "11"/></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">邀请码：</td>
	                	<td><input type="text" id="id_spreadNo" name="spreadMember.spreadNo" class="kaqu_w100"  value="${spreadMember.spreadNo}" minlength = "3" maxlength = "11" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" <#if spreadMember! && spreadMember.id! >disabled</#if> />（3-11位 数字、字母）</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg">*</td>
		                <td class="text_r grayBg">备注：</td>
		                <td><textarea id = "id_remark" name="spreadMember.remark" class="kaqu_w100" style = "width: 390px; height: 110px;" maxlength = "200">${spreadMember.remark}</textarea></td>
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
		$("#li_a_tgqd").addClass("li_a_select");
			
		var $inputSpreadForm = $("#inputSpreadForm");
	
		$("#submitButton").click(function(){
			
			if($("#payPassword").val()=='' || $("#payPassword").val().length<0){
				alert('请输入安全密码!');
				return false;
			}
			
			if($("#id_fullName").val()=='' || $("#id_fullName").val().length<0){
				alert('请输入名称!');
				return false;
			}
			
			if($("#id_mobile").val()=='' || $("#id_mobile").val().length<0){
				alert('请输入手机号码!');
				return false;
			}
			
			if($("#id_spreadNo").val()=='' || $("#id_spreadNo").val().length<0){
				alert('请输入邀请码!');
				return false;
			}
			
			if($("#id_remark").val()=='' || $("#id_remark").val().length<0){
				alert('请输入备注信息!');
				return false;
			}
			
			
			$.ajax({
				url: qmd.base+"/spread/ajaxSaveSpread.do",
				data: $inputSpreadForm.serialize(),
				type: "POST",
				dataType: "json",
	            async : false, 
				success: function(data) {
					if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
		        		alert("操作失败！");
		        	} else if (data.status=="success") {
		        		alert(data.message);
		        		window.location.href = qmd.base + '/spread/spreadMemberList.do';
		        	} else {
			        	alert(data.message);
		        	}
				},
	            error : function() {  
	                alert('参数错误，请联系管理员！');
	            } 
			});
		});
	})
	</script>
</body>
	
</body>
</html>