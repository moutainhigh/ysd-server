<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-借款人-发布质押月标</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/borrow/borrowSet.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/ajaxImageUpload.js"></script>
  </head>
  
<body onload="checkimg()">
<!-- header -->
<#include "/content/common/header.ftl">

<div style="width:100%; background:url(${base}/static/img/d6.png) 0 0 repeat-x; min-width:1000px; padding-bottom:30px;" class="biaoti_bg">
	<div class="content">
		<div class="biaoti">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a><a>></a>
			<a href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a href="${base}/borrow/borrowChoose.do">我要借款</a><a>></a>
			<a class="liebiao">月标借款</a>
		</div>
		<!--biaoti end-->
		<div style="width:1000px;" class="big_content">
			<#include "/content/common/user_center_left.ftl">
			<!--left_menu end-->
			
			
			<form id="form1" name="form1" method="post" action="${base}/borrow/addBorMonth.do"  onsubmit="return check_form();" enctype="multipart/form-data" >
				 <input type="hidden" name="borrow.award" id="award" value="0" />
				 <input type="hidden" name="borrow.isDxb" id="isDxb" value="0" />
		         <input type="hidden" name="number" id="number" value="0" />
		         <input type="hidden" name="borrow.borStages" id="borStages" value="0" />
		         <input type="hidden" name="borrow.type" id="borrowType" value="4" />
		         <input type="hidden" name="borrow.isday" value="0" />
		         <input type="hidden" name="borrow.isVouch" value="0"/>
			<div class="right_jiluneirong">
				<div class="tabsblk">
					<a href="javascript:void(0);"  class="checked">我要借款</a>
				</div>
				<div class="sepblk"></div>
				<div class="white">
					<div style="padding-top:10px;" class="base_frm base_frm30">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td width="150" class="base_item">借款类型：</td>
									<td>质押标</td>
								</tr>
								<tr>
									<td class="base_item">借款标题：</td>
									<td><input type="text" id="borrowname" name="borrow.name" class="base_txt"></td>
								</tr>
								<tr>
									<td class="base_item">借款人类型：</td>
									<td><#if memberType==1>企业<#else>个人</#if></td>
								</tr>
<#--
								<tr>
									<td class="base_item">上传图片：</td>
									<td>
										<input type="file" id="borImageFirstFile" name="imageFile"/>
										<input type="hidden" id="borImageFirst" name="borImageFirst"/>
										<a class="base_btn_s10"  onclick="ajaxFileUploadImageWithRtn('borImageFirstFile','${base}/file/ajaxUploadImage.do','borImageFirst',uploadFileHideBack);" id="btnUploadBig" href="javascript:void(0);">上传</a>
										<a class="base_btn_s11" href="">查看</a>
									</td>
								</tr>
-->
								<tr>
									<td class="base_item">借款金额：</td>
									<td><input type="text" class="base_txt" name="borrow.account"  id="account"  value=""  onkeyup="value=value.replace(/[^0-9]/g,'')" /> 元</td>
								</tr>
								<tr>
									<td class="base_item">年化利率：</td>
									<td><input type="text" class="base_txt" name="borrow.apr" id="apr" value="" onkeyup="clearNoNum(this)" onblur="commitMonth(this);" /> %，月利率为 <font color="#FF0000" id="monthapr">0</font>%</td>
								</tr>
								<tr>
									<td class="base_item">最小投资额：</td>
									<td>
										<select id="lowestAccount" name="borrow.lowestAccount" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
											<option value="100" selected="selected">100元</option>
											<@listing_childlist sign="borrow_lowest_account"; listingList>
												<#list listingList as listing>
													<#if listing.description=="100">
													<#else>
														<option value="${listing.description}">${listing.name}</option>
													</#if>
												</#list>
											</@listing_childlist>
										</select>
									</td>
								</tr>
								<tr>
									<td class="base_item">最大投资额：</td>
									<td>
										<select id="mostAccount" name="borrow.mostAccount" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
											<@listing_childlist sign="borrow_most_account"; listingList>
												<#list listingList as listing>
													<option value="${listing.description}" >${listing.name}</option>
												</#list>
											</@listing_childlist>
										</select>
									</td>
								</tr>
								<tr>
									<td class="base_item">投资有效期：</td>
									<td>
										<select id="validTime" name="borrow.validTime" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
											<@listing_childlist sign="borrow_valid_time"; listingList>
												<#list listingList as listing>
													<option value="${listing.description}" >${listing.name}</option>
												</#list>
											</@listing_childlist>
										</select>
									</td>
								</tr>
								<tr>
									<td class="base_item">投标奖励：</td>
									<td>
										<input type="checkbox"  name="award1" id="award1" onclick="change_j(1)" value="1"/>
										是否启用，奖励<input type="text" id="funds" name="borrow.funds" disabled value="" onkeyup="clearNoNum(this)" size="10" class="base_txt"/>%
									</td>
								</tr>
								<tr>
									<td class="base_item">发放奖励设置：</td>
									<td>
										<input type="checkbox"  name="award2" id="award2" onclick="change_j(2)" value="2"/>是否启用还款完成时发放奖励
									</td>
								</tr>
								<tr>
									<td class="base_item">借款期限：</td>
									<td>
										<input type="text" name ="borrow.timeLimit" id="timeLimit" onblur="checkMonth()" onkeyup="value=value.replace(/[^0-9]/g,'')" class="base_txt" /> 月
										<a href="javascript:setRepayment()" id="repaymentPlan" class="base_btn_s10">设置还款计划</a>
									</td>
								</tr>
								<tr>
									<td class="base_item" valign="top">回购日期：</td>
									<td  valign="top" id="test">&nbsp;</td>
								</tr>
								<tr>
									<td class="base_item">是否定向：</td>
									<td>
										<input type="checkbox" name="isDxb1" id="isDxb1" onclick="change_d(1)" value="1"/>是，定向密码为 
										<input type="password" class="base_txt" id="pwd" name="borrow.pwd" disabled value="" size="10"/>
									</td>
								</tr>
								<tr>
									<td class="base_item">借款描述：</td>
									<td>
										<textarea name="borrow.content" cols="40" rows="3" class="txt"></textarea>
									</td>
								</tr>
								<tr>
									<td class="base_item">借款凭证：</td>
									<td>
										<table id="table_img" style="width:100%;"></table><br/>
										<a class="base_btn_s11" href="javascript:void(0);" onclick="addImgItem();">新增</a>
										<input type="hidden" id="idSort" value="0"/>
									</td>
								</tr>
								<tr>
									<td class="base_item">验证码：</td>
									<td>
										<input type="text"  name="mycode" id="mycode" class="base_txt"/>
										&nbsp;<img id = "code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" />
									</td>
								</tr>
								<tr>
									<td class="base_item">交易密码：</td>
									<td><input type="password" id="payPassword" name="user.payPassword" class="base_txt"/></td>
								</tr>
								<tr>
									<td class="base_item"></td>
									<td>
										<input type="submit" class="queding" value="确定"/>
										<input type="reset" onclick="window.history.back(); return false;" class="quxiao" value="取消"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!--base_frm end-->
				</div>
				<div class="fanye"></div>
			</div>
			</form>
			<!--right_jiluneirong end-->
			<div style="clear:both;"></div>
		</div>
		<!--big_content end-->
	</div>
	<!--content end-->
</div>
<!--biaoti_bg end-->

 		

  <!--footer-->
  <#include "/content/common/footer.ftl">

 <!--帐户信息公开设置 结束-->
<!--帐户信息公开设置 开始-->
  </body>
<script type="text/javascript">
$(function(){
	$("#borrower_choose").addClass("nsg nsg_a1");
});

function addImgItem() {

	var x=parseInt($("#idSort").val());
	x = x + 1;
	$("#idSort").val(x);
	
	var str = '<tr id="trid_'+x+'">';
	str += '<td>';
	str += '  标题：<input type="text" name="vouchersTitle"/>';
	str += '  <input type="file" name="imageFile" id="imageItem_'+x+'" />';
	str += '  <input type="hidden" name="vouchers" id="voucher_'+x+'" />';
	str += '</td>';
    str += '<td>';
    str += '	<input type="button" value="上传"  id="btnUpload_'+x+'"  onclick="ajaxFileUploadImageWithRtn(\'imageItem_'+x+'\',\'${base}/file/ajaxUploadImage.do\',\'voucher_'+x+'\',uploadFileHideBack);" />';
    str += '	<input type="button" value="删除" onclick="moveOut(\''+x+'\')" />';
    str += '	<input type="button" value="上移" onclick="moveUp(\''+x+'\')" />';
    str += '	<input type="button" value="下移" onclick="moveDown(\''+x+'\')" /></td>';
    str += '</tr>';
    
    var tr_len = $("#table_img tr").length;
	if(tr_len==0) {
		$("#table_img").html(str);
	} else {
		$("#table_img").find('tr:last').after(str);
	}
}

function moveUp(x) {
	var obj = $("#trid_"+x);
	var prev = obj.prev();
	prev.before(obj);
}
function moveDown(x) {
	var obj = $("#trid_"+x);
	var next = obj.next();
	next.after(obj);
}
function moveOut(x) {
	var obj = $("#trid_"+x);
	obj.remove();
}

function uploadFileHideBack() {
	alert("上传成功！");
}
</script>
</html>
