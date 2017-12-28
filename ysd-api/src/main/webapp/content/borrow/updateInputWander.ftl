<!DOCTYPE html>
<html>
  <head>
  <title>${Application ["qmd.setting.name"]}-借款人-修改借款标信息填写页</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/borrow/borrowUpdate.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>

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

function btnWanderSave() {
	
	if ($("#idborrowname").val()=='') {
		alert("标题不能为空，请重新输入!");
		return false;
	} else if ($("#account").val()=='') {
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
	} else if (checkMod($("#timeLimit").val(),$("input[name='borrow.wanderRedeemTimes']:checked").val())==false ) {
		alert("借款时长不能被回购期数整除，请重新输入!");
		return false;
	} else if ($("#wanderStages").val()==''||$("#wanderStages").val()=='0') {
		alert("请设置还款计划!");
		return false;
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


</script>
  </head>
  
<body>
<!-- header -->
<#include "/content/common/header.ftl">
<!-- content -->
<div class="admin clearfix">

	<#include "/content/common/user_center_left.ftl">

<!-- right -->
	<div class="admin-con">
		<!-- breadcrumb -->
		<div class="breadcrumb">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt;<a href="${base}/userCenter/index.do">我的账户</a>&gt;<a href="${base}/borrow/userBorrowMgmt.do">借款管理</a>&gt; 修改流转标借款
		</div>
		<div class="tab clearfix">
			<ul>
				<li><a href="" class="current"><span>我要借款</span></a></li>
			</ul>		
		</div>
		<div class="tab-con">
	    <form id="wanderForm" name="form1" method="post" action="${base}/borrow/updateBorWander.do" onsubmit="return btnWanderSave();"  enctype="multipart/form-data" >
	    	 <input type="hidden" name="borrow.id" value="${borrow.id}" id="borrowId"/>
			 <input type="hidden" name="borrow.award" id="award" value="${borrow.award}" />
			 <input type="hidden" name="borrow.isDxb" id="isDxb" value="${borrow.isDxb}" />
	         <input type="hidden" name="number" id="number" value="0" />
	         <input type="hidden" name="borrow.borStages" id="borStages" value="0" />
	         <input type="hidden" name="borrow.wanderStages" id="wanderStages" value="${borrow.wanderStages}" />
	         <div class="frm">
				<table width="90%">
					<tr>
						<td class="item" width="150">借款类型：</td>
						<td>流转标</td>
					</tr>
					<tr>
						<td class="item">借款标题：</td>
						<td><input type="text" id="idborrowname" name="borrow.name" value="${borrow.name}" size="50" /></td>
                    </tr>
                   	<tr>
                        <td class="item">借款人类型：</td>
                        <td>
                          <#if memberType==1>企业<#else>个人</#if>
                        </td>
					</tr>
					
					<tr>
                        <td class="item">担保抵押：</td>
                        <td>
                          <select id="isVouch" class="ddlist" name="borrow.isVouch">
                          	<option value="0" <#if borrow.isVouch==0>selected</#if> >无担保，无抵押</option>
                          	<option value="1" <#if borrow.isVouch==1>selected</#if> >有担保，无抵押</option>
                          	<option value="2" <#if borrow.isVouch==2>selected</#if> >无担保，有抵押</option>
                          	<option value="3" <#if borrow.isVouch==3>selected</#if> >有担保，有抵押</option>
                          </select>
                        </td>
					</tr>
					
                    <tr id="testtest0">
						 <td class="item">上传图片：</td>
						 <td><input type="file" id="idFileImage" name="borImagesFile"><!--&nbsp;<input type="button" id="btn_0" value="新增" onclick="addImage('0');"/>--></td>
                     </tr>    
                    <tr>
						  <td class="item">借款金额：</td>
						  <td><input type="text" name="borrow.account"  id="account"  value="${borrow.account}"  onkeyup="value=value.replace(/[^0-9]/g,'')" />  元</td>
                     </tr>  
					 <tr>
						  <td  class="item" >天利率：</td>
						  <td><input type="text"  name="borrow.apr" id="apr" value="<#if borrow.id!=null>${borrow.apr}</#if>" onkeyup="clearNoNum(this)" onblur="commit(this);" > ‰，年利率为 <font color="#FF0000" id="yearapr">0</font>%</td>
                      </tr>
                     <tr>
						  <td  class="item" >认购总份数：</td>
						  <td><input type="text"  name="borrow.wanderPieceSize" id="wanderPieceSize" value="${borrow.wanderPieceSize}" onKeyUp="value=value.replace(/[^0-9.]/g,'')" /> 份</td>
                      </tr>
					 <tr>
						  <td  class="item" >最小认购额：</td>
						  <td>
							<select id="lowestAccount" class="ddlist" name="borrow.lowestAccount">
			                   <@listing_childlist sign="borrow_lowest_wander"; listingList>
								<#list listingList as listing>
									<option value="${listing.keyValue}" <#if listing.keyValue==borrow.lowestAccount>selected</#if> >${listing.name}</a>
									</option>
								</#list>
								</@listing_childlist>
							 </select>
							</td>
                      </tr>
					  <tr>
						  <td  class="item" >最大认购额：</td><td>
							<select id="mostAccount" class="ddlist" name="borrow.mostAccount">
		                        <option value="" selected="selected">请选择</option>
			                     <@listing_childlist sign="borrow_most_wander"; listingList>
										<#list listingList as listing>
											<option value="${listing.keyValue}" <#if listing.keyValue==borrow.mostAccount>selected</#if>>${listing.name}</a>
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
						 <td  class="item" >投标奖励：</td>
						 <td> <input type="checkbox"  name="award1" id="award1" <#if borrow.award==1||borrow.award==2>checked</#if> onclick="change_j(1)" value="1" class="autoch"> 
						 是否启用，奖励<input type="text" id="funds" name="borrow.funds" <#if borrow.award==1||borrow.award==2><#else>disabled</#if> value="${borrow.funds}" onkeyup="clearNoNum(this)" size="10" />% 
						 注：此处的奖励是每天的奖励</td>
                      </tr>
                      <tr > 
						 <td  class="item" >发放奖励设置：</td>
						 <td> <input type="checkbox"  name="award2" id="award2" <#if borrow.award==2>checked</#if> onclick="change_j(2)" value="2" class="autoch"> 是否启用还款完成时发放奖励</td>
                      </tr>
                      <tr>
						   <td  class="item" >借款时长：</td>
						   <td><input type="text" value="${borrow.timeLimit}" name ="borrow.timeLimit" id="timeLimit"  onkeyup="value=value.replace(/[^0-9]/g,'')" >天</td>
                       </tr>
                      <tr>
						  <td  class="item" >回购期数：</td>
						  <td>
						  	<input type="radio" name="borrow.wanderRedeemTimes" id="redeemTimes1" value="1" <#if borrow.wanderRedeemTimes==null||borrow.wanderRedeemTimes==''||borrow.wanderRedeemTimes==1> checked="checked"</#if> />1次&nbsp;
						  	<input type="radio" name="borrow.wanderRedeemTimes" id="redeemTimes2" value="2" <#if borrow.wanderRedeemTimes==2> checked="checked"</#if>/>2次&nbsp;
						  	<input type="radio" name="borrow.wanderRedeemTimes" id="redeemTimes3" value="3" <#if borrow.wanderRedeemTimes==3> checked="checked"</#if>/>3次&nbsp;
						  	<input type="radio" name="borrow.wanderRedeemTimes" id="redeemTimes4" value="4" <#if borrow.wanderRedeemTimes==4> checked="checked"</#if>/>4次&nbsp;
						  	<input type="radio" name="borrow.wanderRedeemTimes" id="redeemTimes5" value="5" <#if borrow.wanderRedeemTimes==5> checked="checked"</#if>/>5次
						  	&nbsp;&nbsp;&nbsp;&nbsp;
						  	<a href="javascript:setWanderStages();" id="repaymentPlan" class="btn s1"><span>设置还款计划</span></a>
						</td>
                      </tr>
                       <tr>
                           	<td  class="item" >回购日期：</td>
                           	<td  class="data-list" valign="top">
								<div>
                           			<table class="tac">
										<thead>
											<tr>
												<th width="40%">期数</th>
												<th>回购日期</th>
											</tr>
										</thead>
										<tbody id="redeem_plan_table">
											
										</tbody>
									</table>
								</div>
                           </td>
                       </tr>
                       <tr> 
						   <td  class="item" >是否为定向标：</td>
						   <td> <input type="checkbox"  name="isDxb1" id="isDxb1" <#if borrow.isDxb==1>checked</#if> onclick="change_d(1)" value="1" class="autoch"> 是，定向密码为<input type="password" id="pwd" name="borrow.pwd"  <#if borrow.isDxb==1>value="${borrow.pwd}"<#else>disabled</#if> size="10" /> </td>
                        </tr>
                        <tr>
							<td class="item"> 借款描述： </td>
                            <td >
                              	<textarea id="editor" name="borrow.content" class="editor"  >${borrow.content}</textarea>
                             </td>
                        </tr>
						<tr>
                       		<td class="item" >验证码：</td>
                       		<td>
 								<input type="hidden" value="" name="id" />
								<input type="text" name="mycode" id="mycode"> &nbsp;<img src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" /></br>
							</td>
						</tr>
						<tr>
							<td class="item" valign="top"></td>
							<td>
								<input type="submit" value="确 定" class="btn s3" />
								<input type="reset"  onclick="window.history.back(); return false;" value="取 消" class="btn s9" />
								
							</td>
						</tr>
				</table>
			</div>
			<br /><br /><br /><br /><br /><br /><br />
		</form>
		</div>
		<div class="tab-con_bottom"></div>
		
	</div>
</div>
 		

  <!--footer-->
  <#include "/content/common/footer.ftl">

  </body>
<script type="text/javascript">
$(function(){
	
	
	// 设定年利率
	var apr = document.getElementById("apr").value;
	var yearApr = changeTwoDecimal(parseFloat(apr*365)/10);
	document.getElementById("yearapr").innerHTML=yearApr;
	
	//修改时
	if ($("#borrowId").val() !='') {
		
		$("#borrow_manage").addClass("current");
	
		//设置还款计划
		setWanderStages();
		
		if($("#award").val()==1){
			change_j(1);
			
		} else if($("#award").val()==1){
			change_j(2);
		}
		
		
		//if ($("#isDxb1").val() ==1||$("#isDxb1").val() ==2) {
			
			//$("#isDxb1").attr("disabled",false);
			//$("#pwd").val(${borrow.pwd});
		//}
		
		
		
	} else {
		$("#borrow_want").addClass("current");
	}
	
});
</script>
</html>

