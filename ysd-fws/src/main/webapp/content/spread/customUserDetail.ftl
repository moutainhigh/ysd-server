
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-客户管理-客户详情</title>
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
			<#--><li><a href="${base}/spread/spreadMemberList.do">推广渠道</a></li>
			<li><a href="${base}/spread/customUserList.do?user.spreadMemberId=${(user.spreadMemberId)!}">客户列表</a></li>
			-->
			<li><a href="#">客户详情</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>客户详情</h3>
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">用户名：</td>
		                <td>${customer.username}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg"></td>
		                <td class="text_r grayBg">身份证号码：</td>
		                <td>${customer.cardId}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg"></td>
		                <td class="text_r grayBg">真实姓名：</td>
		                <td>${customer.realName}</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg"></td>
		                <td class="text_r grayBg">实名认证：</td>
		                <td>
                 			<#if customer.realStatus==1>通过<#elseif customer.realStatus==2>待审核&nbsp;&nbsp;<a href="javascript:void(0);" onclick="ajaxCustomerRealname('${customer.id}');">[审核]</a><#else>未认证</#if>
						</td>
		              </tr>                                                                           
		              <tr>
		                <td class="text_r grayBg">&nbsp;</td>
		                <td class="text_r grayBg">&nbsp;</td>
		            	<td height="40">
		       	   			<input class="btn2 ml_15" type="button" onClick="location='${base}/spread/customUserList.do?user.spreadMemberId=${(user.spreadMemberId)!}'" value="返回">
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
		$("#li_a_khgl").addClass("li_a_select");
	});
	
	
function ajaxCustomerRealname(id) {

	if (confirm("确认要审核通过此客户信息吗？")) {
		var testTime = new Date().getTime();
		$.ajax({
		        type: "post",
		        dataType : "json",
		        url: 'ajaxCustomerRealname.do?id='+id+'&testTime='+testTime+'&',
		        success: function(data, textStatus){
		        	if(typeof(data.errorMsg) == "undefined" || typeof(data.errorMsg) == "null" || data.errorMsg == null || data.errorMsg == ""){
		        		alert("审核失败");
						window.location.reload();
		        	} else if (data.errorMsg=="OK") {
		        		alert("审核成功");
		        		window.location.reload();
		        	} else {
			        	alert(data.errorMsg);
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
}
	
</script>
</body>
	
</body>
</html>