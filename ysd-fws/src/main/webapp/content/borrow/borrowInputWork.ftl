<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<title>${Application ["qmd.setting.name"]}-发布项目</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/js/borrow/borrowSet.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxImageUpload.js"></script>
	<script type="text/javascript" src="${base}/js/jquery/jquery.lSelect.js"></script>
	
<script type="text/javascript">
<#--校验并提交-->
function btnBorrowSave() {
	
	if ($("#idborrowname").val()=='') {
		alert("标题不能为空，请重新输入!");
		return false;
	} else if ($("#mycode").val()=='') {
		alert("校验码不能为空，请重新输入!");
		return false;
	}else{
		return true;
	}	
	
}


<#--上传 借款凭证后回调-->
function uploadFileHideBackItem(aid) {
	alert('上传成功！');
	var obj = $("#"+aid);
	obj.after('<a style=" color:#c43b3b;" href="javascript:void(0);">上传成功</a>');
	obj.remove();
}

<#--新增借款凭证-->
function addImgItem() {

	var x=parseInt($("#idSort").val());
	x = x + 1;
	$("#idSort").val(x);
	
	var str = '';
	
	str += '	<tr  id="trid_'+x+'">';
	str += '		<td class="kaqu_sa">';
	str += '			<input type="text" name="vouchersTitle" class="kaqu_e10"/>';
	str += '			<input type="file" name="imageFile" id="imageItem_'+x+'" style="height:34px;margin-bottom:10px;"/>';
	str += '			<input type="hidden" name="vouchers" id="voucher_'+x+'" />';
	str += '		</td>';
	str += '		<td class="kaqu_sd">';
	str += '			<a id="btnUpload_'+x+'" onclick="ajaxFileUploadImageWithRtn(\'3\',\'imageItem_'+x+'\',\'${base}/file/ajaxUploadImage.do\',\'voucher_'+x+'\',uploadFileHideBackItem(\'btnUpload_'+x+'\'));" href="javascript:void(0);" class="kaqu_se">上传</a>';
	str += '		</td>';
	str += '		<td class="kaqu_e14">';
	str += '			<a onclick="moveUp(\''+x+'\')" href="javascript:void(0);" class="kaqu_top">上移</a>';
	str += '			<a onclick="moveDown(\''+x+'\')" href="javascript:void(0);" class="kaqu_down">下移</a>';
	str += '			<a onclick="moveOut(\''+x+'\')" href="javascript:void(0);"class="kaqu_close">删除</a>';
	str += '		</td>';
	str += '	</tr>';
	
    var tr_len = $("#table_img tr").length;
	if(tr_len==0) {
		$("#table_img").html(str);
	} else {
		$("#table_img").find('tr:last').after(str);
	}
}
<#--上移借款凭证-->
function moveUp(x) {
	var obj = $("#trid_"+x);
	var prev = obj.prev();
	prev.before(obj);
}
<#--下移借款凭证-->
function moveDown(x) {
	var obj = $("#trid_"+x);
	var next = obj.next();
	next.after(obj);
}
<#--删除借款凭证-->
function moveOut(x) {
	var obj = $("#trid_"+x);
	obj.remove();
}
</script>
  </head>
  
<body class="body_bg">
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_agency.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="${base}/userCenter/index.do">账户中心</a></li>
			<li><a href="${base}/borrow/borrowChoose.do">借款管理</a></li>
            <li>编辑项目</li>
			
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>项目基本信息</h3>
            <form id="flowForm" name="form1">
				<input type="hidden" name="borrow.id" id="id" value="${borrow.id}" />
				<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td class="text_r org grayBg" width="40"></td>
	                <td class="text_r grayBg" width="86">项目类型：</td>
	                <td><input class="input1" disabled="disabled" type="text" value="${borrow.btype}" size="45"/></td>
	              </tr>
	              <tr>
	                <td class="text_r org grayBg"></td>
	                <td class="text_r grayBg">项目编号：</td>
	                <td><input class="input1" disabled="disabled" type="text" value="${borrow.businessCode}" size="35"/></td>
	              </tr>
	              <tr>
	                <td class="text_r org grayBg">*</td>
	                <td class="text_r grayBg">项目描述：</td>
	                <td><textarea class="textarea" name="borrow.content" style="width:252px">${borrow.content}</textarea></td>
	              </tr>
	              <tr>
	                <td class="text_r org grayBg">*</td>
	                <td class="text_r grayBg">项目标题：</td>
	                <td>
	                  <input type="text" id="idborrowname" name="borrow.name" value="${borrow.name}" class="input2 w_252"/>
					</td>
	              </tr> 
	              <tr>
	                <td class="text_r org grayBg"></td>
	                <td class="text_r grayBg">项目凭证：</td>
	                <td>
						<input type="button" value="新增凭证" onclick="addImgItem();" class = "btn3 white">
						<input type="hidden" id="idSort" value="<#if voucherBeans?if_exists>${voucherBeans?size}<#else>0</#if>"/>
						<img src="${base}/static/img/qs.png" width="20" height="20" title="新增凭证"/ class="kaqu_e5">
					</td>
	              </tr>
	              <tr>
	                <td class="text_r org grayBg"></td>
	                <td class="text_r grayBg"></td>
	                <td>
	                <#-- 凭证  -->
					<table class="tableShadow" style="width:50%;" border="0" cellspacing="1" cellpadding="0">
						<thead>
							<tr>
								<td>凭证标题</td>
								<td>操作</td>
								<td>编辑</td>
							</tr>
						</thead>
						<tbody id="table_img">
							<#list voucherBeans as vb>
								<tr  id="trid_${vb_index}">
									<td class="kaqu_sa">
										<input type="text" name="vouchersTitle" class="kaqu_e10" value="${vb.name}"/>
										<input type="file" name="imageFile" id="imageItem_${vb_index}" style="height:34px;margin-bottom:10px;"/>
										<input type="hidden" name="vouchers" id="voucher_${vb_index}" value="${vb.url}"/>
									</td>
									<td class="kaqu_sd">
										<a id="btnUpload_${vb_index}" onclick="ajaxFileUploadImageWithRtn('3','imageItem_${vb_index}','${base}/file/ajaxUploadImage.do','voucher_${vb_index}',uploadFileHideBackItem('btnUpload_${vb_index}'));" href="javascript:void(0);" class="kaqu_se">上传</a>
									</td>
									<td class="kaqu_e14">
										<a onclick="moveUp('${vb_index}')" href="javascript:void(0);" class="kaqu_top">上移</a>
										<a onclick="moveDown('${vb_index}')" href="javascript:void(0);" class="kaqu_down">下移</a>
										<a onclick="moveOut('${vb_index}')" href="javascript:void(0);"class="kaqu_close">删除</a>
									</td>
								</tr>
							</#list>
						</tbody>
					</table>
					<#-- end -->
					</td>
	              </tr>
	            </table>
	             <h3>安全信息</h3>
	             <table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td class="text_r org grayBg" width="40">*</td>
	                <td class="text_r grayBg" width="86">安全密码：</td>
	                <td><input type="password" name="safepwd" class="input2"/></td>
	              </tr>
	              <tr>
	                <td class="text_r org grayBg" width="40">*</td>
	                <td class="text_r grayBg" width="86">验证码：</td>
	                <td><input type="text" name="mycode" id="mycode" class="input2" style="width:100px;" size="20"/>
	                	<img id = "code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" />
	                </td>
	              </tr>
	              <tr>
	                <td class="text_r grayBg">&nbsp;</td>
	                <td class="text_r grayBg">&nbsp;</td>
	            	<td height="40">
	                	<input id="submitButton" class="btn1 white" type="button" value="提交">
	       	   			<input class="btn2 ml_15" type="button" value="取消" onclick="window.history.back(); return false;" >
	                </td>
	              </tr>
	            </table>
            </form>       
        </div>                
    </div>
    
</div>
	<#include "/content/common/footer.ftl">
</body>

<script type="text/javascript">
$(function(){
	$().ready( function() { 
		$(".sssj").attr("id","sssj");
		$("#li_a_jkgl").addClass("li_a_select");
	
		$raiseForm = $("#flowForm");
		
		$("#submitButton").click(function(){
			if(!btnBorrowSave()){
				return ;
			};
			
			$.ajax({
				data: $raiseForm.serialize(),
				url: qmd.base+"/borrow/ajaxSaveBorrowWork.do",
				type: "POST",
				dataType: "json",
	            async : false, 
				success: function(data) {
					if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
		        		alert("操作成功");	
		        	} else if (data.status=="success") {
		        		alert(data.message);
		        		window.location.href = qmd.base + '/borrow/userBorrowMgmt.do';
		        	} else {
			        	alert(data.message);
		        	}
				},
	            error : function() {  
	                alert('操作失败，请联系管理员。');
	            } 
			});
		});
		
	});
});
</script>
</html>
