
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${Application ["qmd.setting.name"]}-账户管理-子页面管理</title>
	<#include "/content/common/meta.ftl">
	<script type="text/javascript" src="${base}/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/js/common/ajaxImageUpload.js"></script>
 <!-- header -->
</head>
<body>
	<#include "/content/common/user_center_header.ftl">
	<#include "/content/common/menu_user.ftl">
<div class="mainBox">
	<!--面包屑-->
	<div class="breadCrumbs shadowBread">
		<ul>
			<li><a href="#">账户管理</a></li>
			<li><a href="#">店铺管理</a></li>
		</ul>
	</div>
    
    <div class="mainWrap">
        <div class="broderShadow">
        	<h3>基本信息</h3>
				<form id="agencyForm" method="post" enctype="multipart/form-data">
					<table class="tableSet" width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">公司LOGO：</td>
		                <td>
		                	<input type="hidden" id="vouchers" name="vouchers" value="${(agency.logo)!}"/>
							<input type="file" id="imageFile" name="imageFile" class="kaqu_w100 kaqu_w102">

							<a onclick="ajaxFileUploadImageWithRtn('4','imageFile','${base}/file/ajaxUploadImage.do','vouchers',uploadFileHideBack);" id="btnLogo1" href="javascript:void(0);" class="kaqu_e1">上传</a>
							<a onclick="viewImage('vouchers')" class="kaqu_e2" href="javascript:void(0);">查看</a>
		                </td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">顶部广告图：</td>
		                <td>
		                	<input type="hidden" id="vouchers1" name="vouchers"  value="${(agency.logo1)!}"/>
							<input type="file" id="imageFile1" name="imageFile" class="kaqu_w100 kaqu_w102">

							<a onclick="ajaxFileUploadImageWithRtn('4','imageFile1','${base}/file/ajaxUploadImage.do','vouchers1',uploadFileHideBack);" id="btnUploadBig" href="javascript:void(0);" class="kaqu_e1">上传</a>
							<a onclick="viewImage('vouchers1')" class="kaqu_e2" href="javascript:void(0);">查看</a>
		                </td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">地图位置：</td>
		                <td>
		                	<input type="hidden" id="vouchers2" name="vouchers"  value="${(agency.logo2)!}"/>
							<input type="file" id="imageFile2" name="imageFile" class="kaqu_w100 kaqu_w102">

							<a onclick="ajaxFileUploadImageWithRtn('4','imageFile2','${base}/file/ajaxUploadImage.do','vouchers2',uploadFileHideBack);" id="btnUploadBig" href="javascript:void(0);" class="kaqu_e1">上传</a>
							<a onclick="viewImage('vouchers2')" class="kaqu_e2"  href="javascript:void(0);">查看</a>
		                </td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">公司介绍：</td>
	               		<td><textarea  class="kaqu_w100 " name="agency.introduction" id="introduction" style = "width: 390px; height: 110px;"/>${(agency.introduction)!}</textarea></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">联系电话：</td>
		                <td>
		                	<input type="text" class="kaqu_w100 " name="agency.subpagePhone" id="subpagePhone" maxlength = "50"  value = "${(agency.subpagePhone)!}"/>
							<label id = "subpagePhone_label" for="agency.subpagePhone"></label>
		                </td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">联系地址：</td>
		                <td><input type="text" class="kaqu_w100 " name="agency.subpageAddress" id="subpageAddress" value = "${(agency.subpageAddress)!}" maxlength = "200"/></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">联系人手机：</td>
		                <td><input type="text" class="kaqu_w100 " name="agency.linkmanMobile" id="" value = "${(agency.linkmanMobile)!}" maxlength = "30"/></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">其它宣传图：</td>
		                <td>
		                	<input type="button" value="新增宣传图" onclick="addImgItem();" class="kaqu_e4"/>
							<input type="hidden" id="idSort" value="${(voucherImgList?size)-1}"/>
							<img src="${base}/static/img/qs.png" width="20" height="20" title="点击新增宣传图"/ class="kaqu_e5">
						</td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86"></td>
		                <td>
		              		<table class = "tableShadow" width="50%" cellpadding="0" cellspacing="0">
		              			<thead>
									<tr>
										<td>标题</td>
										<td>图片</td>
										<td class="kaqu_e7">操作</td>
										<td class="kaqu_e8">编辑</td>
									</tr>
								</thead>
									<tbody id="table_img">
										<#list voucherImgList as vil>
											<#assign v_img = ''>
											<@imageUrlDecode imgurl="${vil.url}"; imgurl>
												<#assign v_img = imgurl>
											</@imageUrlDecode>
											
											<tr id="trid_${vil_index}" class = "whiteBg">
												<td class="kaqu_sd">
													<input type="text" class = "kaqu_e10" maxlength="20" name="vouchersTitle" value="${vil.name}"></td>
												<td class="kaqu_sd">
													<input type="file" id="imageItem_${vil_index}" name="imageFile" style="height:34px;margin-bottom:10px;">
													<input type="hidden" id="voucher_${vil_index}" name="vouchers" value="<#if v_img! && (v_img?split("="))?size gt 1 > ${v_img?split("=")[1]}</#if>">
												</td>
												<td class="kaqu_sd"><a href="${v_img}" target = "_blank" >查看</a></td>
												<td class="kaqu_e14">
													<a class="kaqu_top" href="javascript:void(0);" onclick="moveUp('${vil_index}')">上移</a>
													<a class="kaqu_down" href="javascript:void(0);" onclick="moveDown('${vil_index}')">下移</a>
													<a class="kaqu_close" href="javascript:void(0);" onclick="moveOut('${vil_index}')">删除</a>
												</td>
											</tr>
										</#list>
									</tbody>
								</table></td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86">公司实景：</td>
		                <td>
		                	<input type="button" value="新增实景图" onclick="addImgItemSj();" class="kaqu_e4"/>
							<input type="hidden" id="idSortSj" value="${(voucherImgListSj?size)!'0'}"/>
							<img src="${base}/static/img/qs.png" width="20" height="20" title="点击新增实景图"/ class="kaqu_e5">
		                </td>
		              </tr>
		              <tr>
		                <td class="text_r org grayBg" width="40"></td>
		                <td class="text_r grayBg" width="86"></td>
		                <td>
		                	<table class = "tableShadow" width="50%" cellpadding="0" cellspacing="0">
		              			<thead>
		              				<tr>
										<td class="kaqu_e6">标题</td>
										<td class="kaqu_e6">图片</td>
										<td class="kaqu_e7">操作</td>
										<td class="kaqu_e8">编辑</td>
									</tr>
								</thead>
									<tbody id="table_img_sj">
										<#list voucherImgListSj as vil>
											<#assign v_img = ''>
											<@imageUrlDecode imgurl="${vil.url}"; imgurl>
												<#assign v_img = imgurl>
											</@imageUrlDecode>
											<tr id="trid_sj_${vil_index}" class="whiteBg">
												<td class="kaqu_sd">
													<input type="text" class = "kaqu_e10" maxlength="20" name="vouchersTitleSj" value="${vil.name}"></td>
												<td class="kaqu_sd">
													<input type="file" id="imageItem_sj_${vil_index}" name="imageFile" style="height:34px;margin-bottom:10px;">
													<input type="hidden" id="voucher_sj_${vil_index}" name="vouchersSj" value="${vil.url}">
												</td>
												<td class="kaqu_sd"><a href="${v_img}" target = "_blank" >查看</a></td>
												<td class="kaqu_e14">
													<a class="kaqu_top" href="javascript:void(0);" onclick="moveUpSj('${vil_index}')">上移</a>
													<a class="kaqu_down" href="javascript:void(0);" onclick="moveDownSj('${vil_index}')">下移</a>
													<a class="kaqu_close" href="javascript:void(0);" onclick="moveOutSj('${vil_index}')">删除</a>
												</td>
											</tr>
										</#list>
									</tbody>
								</table>	
		                </td>
		              </tr>
		              
		              <tr>
		                <td class="text_r grayBg">&nbsp;</td>
		                <td class="text_r grayBg">&nbsp;</td>
		            	<td height="40">
<!--		                	<input type="button" class="btn1 white" value="预 览" id = "yl_pt_button">-->
				            <input type="button" class="btn1 white" value="确 定" id = "ok_pt_button"/>
				            <input type="reset" class="btn2 ml_15"  onclick="window.history.back(); return false;" value="取 消" />
            
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
		$(".gjxx").attr("id","gjxx");
		$("#li_a_zymgl").addClass("li_a_select");
		
		
	var $agencyForm = $("#agencyForm");
	 // 表单验证
	$agencyForm.validate({
		rules: {
			"agency.subpagePhone":{
				tel:true
			}
		},
		errorPlacement: function(error, element) {
		  error.appendTo(element.parent());
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			//form.submit();
		}
	});
	
	var $yl_pt_button = $("#yl_pt_button");
	var $ok_pt_button = $("#ok_pt_button");
	
	$yl_pt_button.click(function(){
		$agencyForm.attr("target", "_blank");
		$agencyForm.attr("action", "${base}/preivew.do");
		$agencyForm.submit();
	});
	
	$ok_pt_button.click(function(){
		$.ajax({
				url: qmd.base+"/agency_pt/ajaxUpdateSubpageDetail.do",
				data: $agencyForm.serialize(),
				type: "POST",
				dataType: "json",
           		async : false, 
				success: function(data) {
					if(typeof(data.status) == "undefined" || typeof(data.status) == "null" || data.status == null || data.status == ""){
		        		alert("操作失败！");
		        	} else if (data.status=="success") {
		        		alert(data.message);
		        		window.location.href = qmd.base + '/agency_pt/subpageDetail.do';
		        	} else {
			        	alert(data.message);
		        	}
				},
	            error : function() {  
	                alert('参数错误，请联系管理员！');
	            } 
			});
	});
	
	if(typeof(KindEditor) != "undefined") {
			KindEditor.ready(function(K) {
				editor = K.create("#editor", {
				 	width:"500px" ,
					height: "350px",
					items: ["source", "|", "undo", "redo", "|", "preview", "|","justifyleft", "justifycenter", "justifyright", "justifyfull", "|", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "/", "image", "flash", "media", "insertfile", "emoticons", "map"],
					syncType: "form",
					allowFileManager: true,
					uploadJson: qmd.base+"/file/ajaxUpload.do",
					fileManagerJson: qmd.base+"/file/ajaxBrowser.do",
					afterChange: function() {
						this.sync();
					}
				});
			});
		}
		
	})
	
	
<#--上传图片后回调-->
function uploadFileHideBack () {
	alert('上传成功！！');
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
	str += '<tr id="trid_'+x+'" class = "whiteBg">';
	str += '		<td class="kaqu_sa"><input type="text" name="vouchersTitle" maxlength="20" class = "kaqu_e10"></td>';
	str += '		<td  class="kaqu_sa"><input type="file" name="imageFile" id="imageItem_'+x+'" style="height:34px;margin-bottom:10px;" />';
	str += '			<input type="hidden" name="vouchers" id="voucher_'+x+'" /></td>';
	str += '		<td  class="kaqu_sa"><a id="btnUpload_'+x+'" onclick="ajaxFileUploadImageWithRtn(\'4\',\'imageItem_'+x+'\',\'${base}/file/ajaxUploadImage.do\',\'voucher_'+x+'\',uploadFileHideBackItem(\'btnUpload_'+x+'\'));" href="javascript:void(0);" class="kaqu_se">上传</a></td>';
	str += '		<td class="kaqu_sa">';
	str += '			<a onclick="moveUp(\''+x+'\')" href="javascript:void(0);" class="kaqu_top">上移</a>';
    str += '	    	<a onclick="moveDown(\''+x+'\')" href="javascript:void(0);" class="kaqu_down">下移</a>';
    str += '	    	<a onclick="moveOut(\''+x+'\')" href="javascript:void(0);" class="kaqu_close">删除</a>';
    str += '	    </td>';
	str += '</tr>';
	
    var tr_len = $("#table_img tr").length;
	if(tr_len==0) {
		$("#table_img").append(str);
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





<#--实景图片操作-->

function addImgItemSj() {
	var x=parseInt($("#idSortSj").val());
	x = x + 1;
	$("#idSortSj").val(x);
	var str = '';
	str += '<tr id="trid_sj_'+x+'" class = "whiteBg">';
	str += '		<td class="kaqu_sa"><input type="text" name="vouchersTitleSj" maxlength="20" class = "kaqu_e10"></td>';
	str += '		<td  class="kaqu_sa"><input type="file" name="imageFile" id="imageItem_sj_'+x+'" style="height:34px;margin-bottom:10px;" />';
	str += '			<input type="hidden" name="vouchersSj" id="voucher_sj_'+x+'" /></td>';
	str += '		<td  class="kaqu_sa"><a id="btnUpload_sj_'+x+'" onclick="ajaxFileUploadImageWithRtn(\'4\',\'imageItem_sj_'+x+'\',\'${base}/file/ajaxUploadImage.do\',\'voucher_sj_'+x+'\',uploadFileHideBackItem(\'btnUpload_sj_'+x+'\'));" href="javascript:void(0);" class="kaqu_se">上传</a></td>';
	str += '		<td class="kaqu_sa">';
	str += '			<a onclick="moveUpSj(\''+x+'\')" href="javascript:void(0);" class="kaqu_top">上移</a>';
    str += '	    	<a onclick="moveDownSj(\''+x+'\')" href="javascript:void(0);" class="kaqu_down">下移</a>';
    str += '	    	<a onclick="moveOutSj(\''+x+'\')" href="javascript:void(0);" class="kaqu_close">删除</a>';
    str += '	    </td>';
	str += '</tr>';
	
    var tr_len = $("#table_img_sj tr").length;
	if(tr_len==0) {
		$("#table_img_sj").append(str);
	} else {
		$("#table_img_sj").find('tr:last').after(str);
	}
}

function moveUpSj(x) {
	var obj = $("#trid_sj_"+x);
	var prev = obj.prev();
	prev.before(obj);
}

function moveDownSj(x) {
	var obj = $("#trid_sj_"+x);
	var next = obj.next();
	next.after(obj);
}
function moveOutSj(x) {
	var obj = $("#trid_sj_"+x);
	obj.remove();
}
	</script>
</body>
	
</body>
</html>