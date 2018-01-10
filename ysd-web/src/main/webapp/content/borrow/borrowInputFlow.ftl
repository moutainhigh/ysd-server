<!DOCTYPE html>
<html>
  <head>
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-借款人-发布流转标</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/borrow/borrowSet.js"></script>
		<script type="text/javascript" src="${base}/static/js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${base}/static/js/common/ajaxImageUpload.js"></script>

<script type="text/javascript">
function setWanderStages() {
	$("#redeem_plan_table").html("");
	$("#wanderStages").val("");
	
	var varTimeLimit = $("#timeLimit").val();
	var timeLimit = parseInt(varTimeLimit);
	
	var varRedeemTimes=$('input:radio[name="borrow.wanderRedeemTimes"]:checked').val();
	var redeemTimes = parseInt(varRedeemTimes);
	
	if(timeLimit%redeemTimes!=0) {
		alert("输入借款天数和还款期数不正确！");
		return;
	}
	
	var nmb = timeLimit/redeemTimes;
	
	var str = "";
	var stg = "";
	for (var i = 1;i<=redeemTimes;i++) {
		var dt = addDate(nmb*i); 
		str +="<tr>";
		str +="  <td>"+i+"</td>";
		str +="  <td>"+dt+"</td>";
		str +="</tr>"
		stg +=""+i+","+dt;
		if (i<redeemTimes) {
			stg+=":";
		}
	}
	$("#redeem_plan_table").html(str);
	$("#wanderStages").val(stg);
}

function btnFlowSave() {
	if ($("#idborrowname").val()=='') {
		alert("标题不能为空，请重新输入!");
		return false;
	} else if ($("#idFileImage").val()=='') {
		alert("图片不能为空，请重新输入!");
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
	} else if (checkFloat($("#apr").val()==false)) {
		alert("借款利率不正确，请重新输入!");
		return false;
	} else if ($("#wanderPieceSize").val()=='') {
		alert("总份数不能为空，请重新输入!");
		return false;
	} else if (checkPositiveInteger($("#wanderPieceSize").val())==false) {
		alert("总份数不正确，请重新输入!");
		return false;
	} else if (parseInt($("#lowestAccount").val()) > parseInt($("#wanderPieceSize").val()) ) {
		alert("最小认购份数大于总份数，请重新输入!");
		return false;
	} else if (checkMod($("#account").val(),$("#wanderPieceSize").val())==false ) {
		alert("借款金额不能被总份数整除，请重新输入!");
		return false;
	} else if ($("#award1").attr("checked")==true && $("#funds").val()=='') {
		alert("奖励不能为空，请重新输入!");
		return false;
	} else if ($("#timeLimit").val()=='') {
		alert("借款时长不能为空，请重新输入!");
		return false;
	} else if (checkPositiveInteger($("#timeLimit").val())==false) {
		alert("借款时长不正确，请重新输入!");
		return false;
	} else if ($("#borrow_style").val() ==2 && checkMod($("#timeLimit").val(),$("#repaymentPeriod").val())==false ) {
		alert("借款时长不能整除还款周期，请重新输入!");
		return false;
	
<#--
	} else if (checkMod($("#timeLimit").val(),$("input[name='borrow.wanderRedeemTimes']:checked").val())==false ) {
		alert("借款时长不能被回购期数整除，请重新输入!");
		return false;
	} else if ($("#wanderStages").val()==''||$("#wanderStages").val()=='0') {
		alert("请设置还款计划!");
		return false;
-->
	} else if ($("#isDxb1").attr("checked")==true && $("#pwd").val()=='') {
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
	
	//$("#wanderForm").submit();
	
}

function checkMod(str,dov) {
	var a = parseInt(str);
	var b = parseInt(dov);
	if (a%b==0){
		return true;
	}
	return false;
}

function checkFloat(str)//正浮点数
{
    var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
    if(!reg.test(str))
    {
        return false;
    }
    return true;
}

function checkPositiveInteger(str) // 正整数
{
    var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
    if(!reg.test(str))
    {
        return false;
    }
    return true;
}

function getFormatDate(day)
{
	var Year = 0;
	var Month = 0;
	var Day = 0;
	var CurrentDate = "";
	Year= day.getFullYear();
	Month= day.getMonth()+1;
	Day = day.getDate();
	CurrentDate += Year + "-";
	if (Month >= 10 ) {
		CurrentDate += Month + "-";
	} else {
		CurrentDate += "0" + Month + "-";
	}
	if (Day >= 10 ) {
		CurrentDate += Day ;
	} else {
		CurrentDate += "0" + Day ;
	}
	return CurrentDate;
} 

function addDate(add) {
	var dy = new Date();
	dy.setTime(dy.getTime()+24*3600*1000*add);
	return getFormatDate(dy);
}

function check_repPeriod(){
	if ($("#borrow_style").val() ==2 && checkMod($("#timeLimit").val(),$("#repaymentPeriod").val())==false ) {
		alert("借款时长不能整除还款周期，请重新输入!");
		return false;
	}
}

function checkPieceInput() {
    if (parseInt($("#lowestAccount").val()) > parseInt($("#wanderPieceSize").val()) ) {
		alert("最小认购份数大于总份数，请重新输入!");
		return false;
	}
	
	if ($("#account").val() != ''&& $("#wanderPieceSize").val()!=''&&checkMod($("#account").val(),$("#wanderPieceSize").val())==false ) {
		alert("借款金额不能被总份数整除，请重新输入!");
		return false;
	}
	
}

 function checkAward(){
 	var award =  document.getElementById("award").value;
 	var obj=document.getElementsByName('award1');
 	var apr = document.getElementById("apr").value;
  	var ojb=document.getElementsByName('award2'); 
  	 for(var i=0; i<obj.length; i++){ 
	 	if(award==1){
	 		obj[i].checked = true;
	 		jQuery("#funds").attr("disabled",false); 
	  	}else if(award==2){
	  		obj[i].checked = true;
	  		ojb[i].checked = true;
	 		jQuery("#funds").attr("disabled",false); 
	  	}
	}
	if(parseFloat(apr)>0){
		var yearApr = changeTwoDecimal(parseFloat(apr*36.5));
		document.getElementById("yearapr").value = yearApr;
		document.getElementById("yearapr").innerHTML=yearApr;
	}
	
 }
 
 function change_award(){
   	if ($("#award1").is(':checked')) {
   		$("#funds").attr("disabled",false);
   		$("#award").val('1');
   	} else {
   		$("#funds").val('');
   		$("#funds").attr("disabled",true);
   		$("#award").val('0');
   	}
}

function changeStyle() {

	if ($("#borrow_style").val()==2) {
		$("#repaymentPeriodSpan").show();
	} else {
		$("#repaymentPeriodSpan").hide();
	}
	
}
</script>
  </head>
  
<body onload="checkimg()">
<!-- header -->
<#include "/content/common/header.ftl">
<!-- content -->

<div style="width:100%; background:url(${base}/static/img/d6.png) 0 0 repeat-x; min-width:1000px; padding-bottom:30px;" class="biaoti_bg">
	<div class="content">
		<div class="biaoti">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a><a>></a>
			<a href="${base}/userCenter/index.do">我的账户</a><a>></a>
			<a href="${base}/borrow/borrowChoose.do">我要借款</a><a>></a>
			<a class="liebiao">新增流转标</a>
		</div>
		<!--biaoti end-->
		<div style="width:1000px;" class="big_content">
			<#include "/content/common/user_center_left.ftl">
			<!--left_menu end-->
			
	    <form id="flowForm" name="form1" method="post" action="${base}/borrow/addBorFlow.do" onsubmit="return btnFlowSave();" enctype="multipart/form-data" >
			  <input type="hidden" name="borrow.award" id="award" value="0" />
			 <input type="hidden" name="borrow.isDxb" id="isDxb" value="0" />
			 <input type="hidden" name="borrow.id" id="id" value="${borrow.id}" />
	         <input type="hidden" name="number" id="number" value="0" />
	         <input type="hidden" name="borrow.borStages" id="borStages" value="0" />
	         <input type="hidden" name="borrow.wanderStages" id="wanderStages" value="0" />
	         <input type="hidden" name="borrow.isday" value="1" />
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
						<td>流转标</td>
					</tr>
					<tr>
						<td class="base_item">借款标题：</td>
						<td><input type="text" id="idborrowname" name="borrow.name" value="${borrow.name}" size="50" class="base_txt" /></td>
                    </tr>
                   	<tr>
                        <td class="base_item">借款人类型：</td>
                        <td>
                          <#if memberType==1>企业<#else>个人</#if>
                        </td>
					</tr>
					<tr>
                        <td class="base_item">担保抵押：</td>
                        <td>
                          <select id="isVouch" class="ddlist" name="borrow.isVouch">
                          	<option value="0">无担保，无抵押</option>
                          	<option value="1">有担保，无抵押</option>
                          	<option value="2">无担保，有抵押</option>
                          	<option value="3">有担保，有抵押</option>
                          </select>
                        </td>
					</tr>
       <#--             <tr id="testtest0">-->
<#--
						 <td class="item">上传图片：</td>
						 <td><input type="file" id="idFileImage" name="borImagesFile" value="${borrow.borImageFirst}"></td>
-->
<#--						 <td class="base_item">上传图片：</td>
						 <td>
						 	<input type="file" id="borImageFirstFile" name="imageFile"/>
							<input type="hidden" id="borImageFirst" name="borImageFirst"/>
							<input type="button" onclick="ajaxFileUploadImageWithRtn('borImageFirstFile','${base}/file/ajaxUploadImage.do','borImageFirst',uploadFileHideBack);" id="btnUploadBig" value="上传"/>
						 </td>
                     </tr>  
 -->  
                    <tr>
						  <td class="base_item">借款金额：</td>
						  <td><input type="text" name="borrow.account"  id="account"  value="${borrow.account}"  onkeyup="value=value.replace(/[^0-9]/g,'')" onBlur="checkPieceInput()" class="base_txt"/>  元</td>
                     </tr>  
					 <tr>
						  <td  class="base_item" >天利率：</td>
						  <td><input type="text"  name="borrow.apr" id="apr"  class="base_txt" value="${borrow.apr}" onkeyup="clearNoNum(this)" onblur="commit(this);" > ‰，年利率为 <font color="#FF0000" id="yearapr">0</font>%</td>
                      </tr>
                     <tr>
						  <td  class="base_item" >认购总份数：</td>
						  <td><input type="text" class="base_txt" name="borrow.wanderPieceSize" id="wanderPieceSize" value="${borrow.wanderPieceSize}" onKeyUp="value=value.replace(/[^0-9.]/g,'')"  onBlur="checkPieceInput()" /> 份</td>
                      </tr>
					 <tr>
						  <td  class="base_item" >最小认购额：</td>
						  <td>
							<select id="lowestAccount" class="ddlist" name="borrow.lowestAccount">
			                   <@listing_childlist sign="borrow_lowest_wander"; listingList>
								<#list listingList as listing>
									<option value="${listing.keyValue}" >${listing.name}</a>
									</option>
								</#list>
								</@listing_childlist>
							 </select>
							</td>
                      </tr>
					  <tr>
						  <td  class="base_item" >最大认购额：</td><td>
							<select id="mostAccount" class="ddlist" name="borrow.mostAccount">
		                        <option value="" selected="selected">请选择</option>
			                     <@listing_childlist sign="borrow_most_wander"; listingList>
										<#list listingList as listing>
											<option value="${listing.keyValue}" >${listing.name}</a>
											</option>
										</#list>
								</@listing_childlist>
							</select>
						   </td>
                       </tr>
                       <!--
					   <tr>
						  <td  class="item" >投资有效期：</td>
						  <td>
						   <select id="validTime" class="ddlist" name="borrow.validTime">
			                    <@listing_childlist sign="borrow_valid_time"; listingList>
									<#list listingList as listing>
										<option value="${listing.description}" >${listing.name}</a>
										</option>
									</#list>
								</@listing_childlist>
							</select>
						</td>
                      </tr>
                      -->
                       <!--
                      <tr>
						  <td  class="item" >每人认购次数：</td>
						  <td>
						   <select id="validTime" class="ddlist" name="borrow.wanderTenderTimes">
						   		<option value="1">1</option>
						   		<option value="2">2</option>
						  
			                    <@listing_childlist sign="borrow_tender_time_limit"; listingList>
									<#list listingList as listing>
										<option value="${listing.description}" >${listing.name}</a>
										</option>
									</#list>
								</@listing_childlist>
							
							</select>
						</td>
                      </tr>
                          -->
                      <tr> 
						 <td  class="base_item" >投标奖励：</td>
						 <td> 
						 	<input type="checkbox"  name="award1" id="award1" onclick="change_award();" value="1" class="autoch"> 是否启用奖励
				 		 </td>
                      </tr>
                      <tr> 
						 <td  class="base_item" ></td>
						 <td>
					 		<input type="radio" name="borrow.awardType" id="awardType0" value="0" checked="">现金奖励
							<input type="radio" name="borrow.awardType" id="awardType1" value="1" >红包奖励
					 		<input type="text" id="funds" name="borrow.funds" class="base_txt" disabled value="" onkeyup="clearNoNum(this)" size="10" />% 
					 		注：此处的奖励是每天的奖励
					 	</td>
                      </tr>
                      <tr>
						   <td  class="base_item" >借款时长：</td>
						   <td><input type="text" value="${borrow.timeLimit}" name ="borrow.timeLimit" id="timeLimit"  onkeyup="value=value.replace(/[^0-9]/g,'')" class="base_txt">天</td>
                       </tr>
						  	<td  class="base_item" >投资有效期：</td>
						  	<td>
				   					<select id="validTime" class="ddlist" name="borrow.validTime">
	                    				<@listing_childlist sign="borrow_valid_time"; listingList>
											<#list listingList as listing>
												<option value="${listing.description}" >${listing.name}</a></option>
											</#list>
										</@listing_childlist>
									</select>
							</td>
                     </tr>
                     <tr height = "40px">
                    	<td>还款方式：</td>
                    	<td>
                    		<select name="borrow.style" id="borrow_style" onchange="changeStyle();" style="width:180px; height:24px; padding:2px; border:1px solid #7d7d7d;">
                    			<option value="1">到期还本息</option>
                    			<option value="2">分期付息</option>
                    		</select>
                    		<span style="display:none" id="repaymentPeriodSpan">还款周期<input type="text" name="borrow.repaymentPeriod" id="repaymentPeriod" class="base_txt"/></span>
                    </tr>
                       <tr> 
						   <td  class="base_item" >是否为定向标：</td>
						   <td> <input type="checkbox"  name="isDxb1" id="isDxb1" onclick="change_d(1)" value="1" class="autoch"> 是，定向密码为<input type="password" id="pwd" name="borrow.pwd" disabled value="" size="10" class="base_txt" /> </td>
                        </tr>
                        <tr>
							<td class="base_item"> 借款描述： </td>
                            <td >
                              	<textarea name="borrow.content" rows="3" cols="50" ></textarea>
                             </td>
                        </tr>
                        <tr>
							<td class="base_item" style=" padding-top:10px;"> 借款凭证： </td>
                            <td >
                            	<br>
                              	<input type="button" value="新增" onclick="addImgItem();" class="base_btn_s10">
                              	<input type="hidden" id="idSort" value="0"/>
                            	<table id="table_img" style="width:100%;" cellpadding="0" cellspacing="0"></table>
                             </td>
                        </tr>
						<tr>
                       		<td class="base_item" >验证码：</td>
                       		<td>
 								<input type="hidden" value="" name="id" />
								<input type="text" name="mycode" id="mycode" class="base_txt"> &nbsp;<img id = "code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" /></br>
							</td>
						</tr>
						<tr>
							<td class="base_item" valign="top"></td>
							<td>
								<input type="submit" value="确 定" class="queding" />
								<input type="reset"  onclick="window.history.back(); return false;" value="取 消" class="quxiao" />
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
		<div style="clear:both;"></div>
		</div>
		<!--big_content end-->
	</div>
	<!--content end-->
</div>
 		

  <!--footer-->
  <#include "/content/common/footer.ftl">

  </body>
<script type="text/javascript">
$(function(){
	$("#borrow_want").addClass("current");
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
