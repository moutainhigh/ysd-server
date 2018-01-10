<!DOCTYPE html>
<html>
  <head>
   <title>${Application ["qmd.setting.name"]}-借款人-发布质押月标</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/borrow/borrowSet.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/ajaxImageUpload.js"></script>
  </head>
  
<body onload="checkimg()">
<#include "/content/common/header.ftl">

<div class="content">
  <div style="width:955px; margin:0 auto; padding-bottom:20px;">
	<#include "/content/common/user_center_header.ftl">
 <div style="width:955px; float:left; background:#fff; clear:both;">
	<#include "/content/common/user_center_left.ftl">
	
	<#-- 		右边内容块 开始 				-->
<div style="width:670px; float:right; padding:0px 15px 0px 18px; ">
      <div style="padding-top:30px;">
        <a href="" style="color:#646464;font-family:'宋体';">我的账户</a>
        <a style="color:#646464;font-family:'宋体'; padding:0px 4px;">></a>
        <a href="" style="color:#646464;font-family:'宋体';">项目管理</a>
      </div>
      <div style=" width:661px;background:#d4d4d4; height:42px; line-height:42px; padding-left:9px; margin-top:8px; float:left;">
        <a href="" class="hr hre">项目发布</a>
      </div>

	<form id="form1" name="form1" method="post" action="${base}/borrow/updateBorrow.do"  onsubmit="return check_form_promote();" enctype="multipart/form-data" >
		 <input type="hidden" name="borrow.parentFlg" id="parentFlg" value="2" />
		 <input type="hidden" name="borrow.id" value="${borrow.id}" />
		 <input type="hidden" name="borrow.parentMoney" value="${borrow.parentMoney}" />
		 <input type="hidden" name="borrow.award" id="award" value="${borrow.award}" />
		 <input type="hidden" name="borrow.isDxb" id="isDxb" value="${borrow.isDxb}" />
         <input type="hidden" name="number" id="number" value="0" />
         <input type="hidden" name="borrow.borStages" id="borStages" value="0" />
         <input type="hidden" name="borrow.type" id="borrowType" value="${borrowType}" />
         <input type="hidden" name="borrow.isday" id="borrowIsday" value="${borrowIsday}" />
         <input type="hidden" name="borrow.isVouch" value="${borrow.isVouch}"/>
         <input type="hidden" id="borrow_lowestAccount" value="${borrow.lowestAccount}"/>
         <input type="hidden" id="borrow_mostAccount" value="${borrow.mostAccount}"/>
         <input type="hidden" id="borrow_validTime" value="${borrow.validTime}"/>
         <input type="hidden" id="borrow_divides" value="${borrow.divides}"/>
         <input type="hidden" id="borrow_style" value="${borrow.style}"/>
 
    <div style="border:0px solid #c3c3c3;">
		<div class="base_frm" style="padding-top:30px;padding-bottom:30px; background:#fff;">
					<table >
						<tbody>
							<tr>
								<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">业务类型：</td>
								<td style="">
									<select id="businessType" style="width:186px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
										<@listing_childlist sign="borrow_business_type"; listingList>
										<#list listingList as listing>
										<option value="${listing.keyValue}" >${listing.name}</option>
										</#list>
									</@listing_childlist>
									</select>
								</td>
							</tr>
							<tr height="20"><td></td><td></td></tr>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">项目类型：</td>
								<td style="">
										<#if borrowType==11>天标
										<#elseif borrowType==12>月标
										<#elseif borrowType==13>流转标
										<#else>&nbsp;</#if>
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">项目标题：</td>
								<td style=""><input type="text" id="borrowname" name="borrow.name" value="${borrow.name}" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/></td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">借款人类型：</td>
								<td style=""><#if loginUser.memberType==1>企业<#else>个人</#if></td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">项目图片：</td>
								<td style="">
									<input type="hidden" id="borImageFirst" name="borImageFirst" value="${(borImageFirst)!}"/>
									<input type="file" id="borImageFirstFile" name="imageFile" value="${(borrow.borImageFirst)!}" class="kaqu_w100 kaqu_w102"/>
									<a onclick="ajaxFileUploadImageWithRtn('borImageFirstFile','${base}/file/ajaxUploadImage.do','borImageFirst',uploadFileHideBack);" id="btnUploadBig" href="javascript:void(0);" class="lsc lscr_o1">上传</a>
									<#--<a onclick="viewImage('borImageFirst')" "javascript:void(0);" style="color:#ecd689;font-family:'宋体';background:url(/static/img/a21.png) 0 0 no-repeat;width:44px;height:22px;line-height:22px;text-align:center;display:inline-block;">查看</a>-->
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">项目描述：</td>
								<td style=""><textarea name="borrow.content" cols="55" rows="6" style="border:1px solid #c6c6c6;border-radius:5px; resize:none;">${(borrow.content)!}</textarea></td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" >项目凭证：</td>
								<td>
									<input type="button" value="新增凭证"/ class="lsc lscr_o1"  onclick="addImgItem();">
									<input type="hidden" id="idSort" value="${(borrowImgList?size)+1}"/>
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td style="width:50px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" valign="top"></td>
								<td class="base_data-list" valign="top">
									<table id="table_img" style="width:100%;" cellpadding="0" cellspacing="0">
									<#list borrowImgList as tem>
										<tr id="trid_${tem_index}">
											<td>  标题：<input type="text" name="vouchersTitle" style="width:100px; height:25px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" value="${tem.name}" name="vouchersTitle"> 
												 <input type="file" style="width:100px;" id="imageItem_${tem_index}" name="imageFile">  
												 <input type="hidden"  id="voucher_${tem_index}" name="vouchers" value="${tem.url}">
											</td>
											<td>	
												<input type="button" onclick="tempback(${tem_index});ajaxFileUploadImageWithRtn('imageItem_${tem_index}','/file/ajaxUploadImage.do','voucher_${tem_index}',uploadFileHideBack);" id="btnUpload_${tem_index}" value="上传" style=" width:46px;" class="lsc lscr_o1"">	
												<input type="button" onclick="moveOut('${tem_index}')" value="删除" style=" width:46px;" class="lsc lscr_o1">	
												<input type="button" onclick="moveUp('${tem_index}')" value="上移" style=" width:46px;" class="lsc lscr_o1">
												<input type="button" onclick="moveDown('${tem_index}')" value="下移" style=" width:46px;" class="lsc lscr_o1">
											</td>
										</tr>
									</#list>
									</table>
								</td>
						</tr>
						</tbody>
					</table>
				</div>
				<div class="base_frm" style="padding-top:30px;padding-bottom:30px;background:#fff;">
					<table >
						<tbody>
							<tr>
								<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">项目金额：</td>
								<td style="">
									<input type="text" name="borrow.account" id="account"  value="${(borrow.account)!}" onkeyup="value=value.replace(/[^0-9]/g,'')" onBlur="checkPieceInput()" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" />&nbsp;元&nbsp;
									
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">投标奖励：</td>
								<td style="">
								<input type="checkbox"  name="award1" id="award1" onclick="change_award(1)" <#if borrow.award==0>checked</#if>value="1" />是否启用，
								<input type="radio" name="borrow.awardType" id="awardType0" value="0" <#if borrow.awardType==0>checked</#if> >现金奖励
								<input type="radio" name="borrow.awardType" id="awardType1" value="1" <#if borrow.awardType==1>checked</#if> >红包奖励
								<input type="text" id="funds" name="borrow.funds"  <#if borrow.award==0>value="${borrow.funds}"<#else>disabled value=""</#if> onkeyup="clearNoNum(this)" size="10" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
								</td>
							</tr><tr height="20"><td></td><td></td></tr>

							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">投资有效期：</td>
								<td style="">
									<select id="validTime" name="borrow.validTime" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
										<@listing_childlist sign="borrow_valid_time"; listingList>
											<#list listingList as listing>
												<option value="${listing.description}" >${listing.name}</a></option>
											</#list>
										</@listing_childlist>
									</select>
									&nbsp;天&nbsp;
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							
							<#if borrowIsday==0>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">年利率：</td>
								<td style=""><input type="text" name="borrow.apr" id="apr"  value="${(borrow.apr)!}" onkeyup="clearNoNum(this)" onblur="commit_year2month('apr','monthapr');loadRepaymentPlan();"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
								&nbsp;&nbsp;%，月利率为<span id="monthapr">0</span>%&nbsp;&nbsp; 
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							<#elseif borrowIsday==1>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">天利率：</td>
								<td style=""><input type="text" name="borrow.apr" id="apr" value="${(borrow.apr)!}" onKeyUp="value=value.replace(/[^0-9.]/g,'')" onblur="commit_day2year('apr','yearapr');loadRepaymentPlan();" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
								&nbsp;&nbsp;%，年利率为<span id="yearapr">0</span>%。&nbsp;&nbsp; 
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							</#if>
							
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">最小投资额：</td>
								<td style="">
									<select id="lowestAccount" name="borrow.lowestAccount" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
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
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">最大投资额：</td>
								<td style="">
									<select id="mostAccount" name="borrow.mostAccount" class="kaqu_r3"style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;">
										<option value="" selected="selected">不限</option>
											<@listing_childlist sign="borrow_most_account"; listingList>
												<#list listingList as listing>
													<option value="${listing.description}" >${listing.name}</option>
												</#list>
											</@listing_childlist>
									</select>
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">借款时长：</td>
								<td style="">
									<input type="text" name ="borrow.timeLimit" id="timeLimit" onblur="loadRepaymentPlan();" value="${borrow.timeLimit}"  onkeyup="value=value.replace(/[^0-9]/g,'')"  style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
									&nbsp;<#if borrowIsday==0>个月<#elseif borrowIsday==1>天</#if>
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							
							<#if borrowIsday==0>
								<input type="hidden" name="borrow.divides" id="borrowDivides" value="0"/>
							<#elseif borrowIsday==1>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">还款期数:</td>
								<td style="">
									<select class="kaqu_r3" name="borrow.divides" id="borrowDivides" onchange="loadRepaymentPlan();" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" >
											<option value="1">1期</option>
											<option value="2">2期</option>
											<option value="3">3期</option>
											<option value="4">4期</option>
											<option value="5">5期</option>
											<option value="6">6期</option>
									</select>
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							</#if>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">还款方式：</td>
								<td style="">
									<select class="kaqu_r3" name="borrow.style" id="borrowStyle" onchange="loadRepaymentPlan();" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;" >
											<option value="1">分期付息</option>
											<option value="2">到期付本息</option>
											<option value="3">等额本金</option>
									</select>
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							
							
							
							<tr>
								<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';"  >还款计划：</td>
								<td>
									<table width="99%" class="base_tac" bordercolor="#cfcfcf" cellpadding="0" cellspacing="0" style="border:1px solid #ccc;">
										<thead>
											<tr height="30" >
												<th style="border-bottom:1px solid #ccc;" ><a>期数</a></th>
												<th style="border-bottom:1px solid #ccc;" ><a>还款日期</a></th>
												<th style="border-bottom:1px solid #ccc;" ><a>还款总额</a></th>
												<th style="border-bottom:1px solid #ccc;" ><a>还款本金</a></th>
												<th style="border-bottom:1px solid #ccc;" ><a>还款利息</a></th>
											</tr>
										</thead>
										<tbody  id="planTable">
										</tbody>
									</table>
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">是否定向：</td>
								<td style="">
									<input type="checkbox" name="isDxb1" id="isDxb1" onclick="change_isDxb(1)" <#if borrow.isDxb==1>checked</#if> value="1"/>&nbsp;&nbsp;
									是，定向密码为：&nbsp;&nbsp;<input type="text" id="pwd" name="borrow.pwd" <#if borrow.isDxb==1><#else>disabled</#if> value="" size="10"style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
									
								</td>
							</tr>
							
             			</tbody>
                    </table>
				</div>

			<div class="base_frm" style="padding-top:10px;padding-bottom:10px;background:#fff;">
					<table >
						<tbody>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';" width="120">安全密码：</td>
								<td style=" padding-bottom:20px;">
									<input type="password" id="payPassword" name="user.payPassword" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
								</td>
							</tr><tr height="20"><td></td><td></td></tr>
							<tr>
								<td  style="width:90px; padding-right:5px; text-align:right;color:#646464;font-family:'宋体';">验证码：</td>
								<td >
									<input type="text" name="mycode" id="mycode" style="width:284px; height:30px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>
									<img id = "code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			<div class="base_frm" style="padding-top:20px;padding-left:130px;">
	             <table >
					<tr>	
						<td>
				            <input type="submit" value="确定" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#be9964; color:#fff; text-align:center; font-size:16px;border-radius:5px;" />
				          	<input type="reset" value="取消" onclick="window.history.back(); return false;" style="display:inline-block; width:145px; height:39px; line-height:39px; background:#d4d4d4; color:#000; text-align:center; font-size:16px;border-radius:5px;" />
						</td>
					</tr>
				</table>
			</div>	
		</form>
	</div>
    <div style="clear:both;"></div>
  </div>
</div><!-- content end -->
	<form id="planForm">
	<input type="hidden" name="borrowType" id="plan_borrowType" value="${borrowType}" />
	<input type="hidden" name="borrowAccount" id="plan_borrowAccount"/>
	<input type="hidden" name="borrowApr" id="plan_borrowApr"/>
	<input type="hidden" name="borrowTimeLimit" id="plan_borrowTimeLimit"/>
	<input type="hidden" name="borrowStyle" id="plan_borrowStyle"/>
	<input type="hidden" name="borrowDivides" id="plan_borrowDivides"/>
	<input type="hidden" name="borrowIsday" id="plan_borrowIsday"/>
	</form>
 <!--footer-->
<#include "/content/common/footer.ftl">
</body>
<script type="text/javascript">
$(function(){
	$("#borrower_choose").addClass("nsg nsg_a1");
	clkParentFlg();
});

<#----------------------------->
//计算月利息
function commit_year2month(str1,str2){
	var apr = $("#"+str1).val();
	var monthApr = changeTwoDecimal(parseFloat(apr)/12);
	$("#"+str2).html(monthApr);
}
function commit_day2year(str1,str2){
	var apr = $("#"+str1).val();
	var monthApr = changeTwoDecimal(parseFloat(apr)*36);
	$("#"+str2).html(monthApr);
}
//<!--控制奖励-->
function change_award(type){
  var obj=document.getElementsByName('award1');
  var ojb=document.getElementsByName('award2');  
  for(var i=0; i<obj.length; i++){
    if(obj[i].checked) {
    	jQuery("#funds").attr("disabled",false); 
    	if(ojb[i].checked){
    		document.getElementById("award").value=ojb[i].value;
    	}else{
    		document.getElementById("award").value=obj[i].value;
    	}
    }else{
    	jQuery("#funds").attr("disabled",true);
    	document.getElementById("funds").value="";
    	document.getElementById("award").value=0;
    }
    if(ojb[i].checked){
    	 if(obj[i].checked) {
    		 document.getElementById("award").value=ojb[i].value; 
    	 }else{
    		 document.getElementById("award").value=0;
    		 ojb[i].checked = false;
    		 alert("请设置启用奖励和奖励的比例！");
    	 }
    }
   }
}
//<!--设定定向标-->
function change_isDxb(type){
  var obj=document.getElementsByName('isDxb1');  
  for(var i=0; i<obj.length; i++){
    if(obj[i].checked) {
    	jQuery("#pwd").attr("disabled",false); 
    	document.getElementById("isDxb").value=obj[i].value;
    }else{
    	jQuery("#pwd").attr("disabled",true);
    	document.getElementById("isDxb").value=0;
    }
   }
}	


function loadRepaymentPlan() {
	if($("#account").val()=='') {
		return;
	}
	if($("#apr").val()=='') {
		return;
	}
	if($("#timeLimit").val()=='') {
		return;
	}
	if($("#borrowStyle").val()=='') {
		return;
	}
	if($("#borrowDivides").val()=='') {
		return;
	}
	
	$("#plan_borrowAccount").val($("#account").val());
	$("#plan_borrowApr").val($("#apr").val());
	$("#plan_borrowTimeLimit").val($("#timeLimit").val());
	$("#plan_borrowStyle").val($("#borrowStyle").val());
	$("#plan_borrowDivides").val($("#borrowDivides").val());
	$("#plan_borrowIsday").val($("#borrowIsday").val());
	
	$.ajax({
        type: "post",
        data: $('#planForm').serialize(),
        url: '${base}/borrow/ajaxShowPlan.do',
        error: function(){
			alert('操作错误,请与系统管理员联系!');
		},
		success: function(data){
			$("#planTable").html(data);
		}

	});

}

function check_form_promote(){

	var type = $("#borrowType").val();
	if ($("#borrowname").val()=='') {
			alert("标题不能为空，请重新输入!");
			return false;
		}else if ($("#account").val()=='') {
			alert("借款金额不能为空，请重新输入!");
			return false;
		}else if (checkPositiveInteger($("#account").val())==false) {
			alert("借款金额不正确，请重新输入!");
			return false;
		}else if ($("#apr").val()=='') {
			alert("借款利率不能为空，请重新输入!");
			return false;
		}else if (checkFloat($("#apr").val()==false)) {
			alert("借款利率不正确，请重新输入!");
			return false; 
		} else if (($("#award").val()==1||$("#award").val()==2) && $("#funds").val()=='') {
			alert("奖励不能为空，请重新输入!");
			return false;
		}else if (($("#award").val()==1||$("#award").val()==2) && parseFloat($("#funds").val())<=0) {
			alert("奖励不能小于0，请重新输入!");
			return false;
		} else if ($("#timeLimit").val()=='') {
			alert("借款时长不能为空，请重新输入!");
			return false;
		} else if (checkPositiveInteger($("#timeLimit").val())==false) {
			alert("借款时长不正确，请重新输入!");
			return false;
		} else if ($("#isDxb").val()==1 && $("#pwd").val()=='') {
			alert("定向密码不能为空，请重新输入!");
			return false;
		} else if ($("#editor").val()=='') {
			alert("标的详情不能为空，请重新输入!");
			return false;
		} else if ($("#mycode").val()=='') {
			alert("校验码不能为空，请重新输入!");
			return false;
		}else{
			return true;
		}	
		
	}
<#--------------------------------->

function addImgItem() {

	var x=parseInt($("#idSort").val());
	x = x + 1;
	$("#idSort").val(x);
	
	var str = '<tr id="trid_'+x+'">';
	str += '<td>';
	str += '  标题：<input type="text" name="vouchersTitle" style="width:100px; height:25px; line-height:30px; padding-left:4px; border:1px solid #c6c6c6; border-radius:5px;"/>';
	str += '  <input type="file" name="imageFile" id="imageItem_'+x+'" />';
	str += '  <input type="hidden" name="vouchers" id="voucher_'+x+'" />';
	str += '</td>';
    str += '<td>';
    str += '	<input type="button" value="上传" style=" width:46px;" class="lsc lscr_o1" id="btnUpload_'+x+'"  onclick="ajaxFileUploadImageWithRtn(\'imageItem_'+x+'\',\'${base}/file/ajaxUploadImage.do\',\'voucher_'+x+'\',uploadFileHideBack);" />';
    str += '	<input type="button" value="删除" style=" width:46px;" class="lsc lscr_o1" onclick="moveOut(\''+x+'\')" />';
    str += '	<input type="button" value="上移" style=" width:46px;" class="lsc lscr_o1" onclick="moveUp(\''+x+'\')" />';
    str += '	<input type="button" value="下移" style=" width:46px;" class="lsc lscr_o1" onclick="moveDown(\''+x+'\')" /></td>';
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

function clkParentFlg() {
	if($("#box_parent_flg").attr("checked")=='checked') {
		$("#span_pmoney").show();
	} else {
		$("#span_pmoney").hide();
		$("#pmoney").val(0);
	}
	
}
</script>
</html>
