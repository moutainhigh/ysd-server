<!DOCTYPE html>
<html>
  <head>
  <meta charset="gb2312" />
  <title>${Application ["qmd.setting.name"]}-—国资控股|专业、安全、透明的互联网金融服务平台-借款人-发布天标</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/borrow/borrowUpdate.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
    
 
<SCRIPT language=javascript> 
 function checkAward(){
 	var award =  document.getElementById("award").value;
 	var isDxb =  document.getElementById("isDxb").value;
 	var obj=document.getElementsByName('award1');
 	var Dxb=document.getElementsByName('isDxb1');
 	var strMess = document.getElementById("strMess").value;
 	var apr = document.getElementById("apr").value;
 	var strNum="";
 	var repayMess="";
 	var str="";
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
	
	 for(var i=0; i<Dxb.length; i++){ 
	 	if(isDxb==1){
	 		Dxb[i].checked = true;
	 		jQuery("#pwd").attr("disabled",false); 
	 	}
	 }
	if(parseFloat(apr)>0){
		var yearApr = changeTwoDecimal(parseFloat(apr*36.5));
		document.getElementById("yearapr").value = yearApr;
		document.getElementById("yearapr").innerHTML=yearApr;
	}
	strNum = strMess.split(";");
	for(var i=0; i<strNum.length-1;i++ ){
		repayMess= strNum[i].split(",");
		var j = i+1;
		str += "<tr id='repayList"+i+"'><td>"+j+"</td><td>第"+repayMess[0]+"天</td><td>"+repayMess[1]+"</td><td>"+repayMess[2]+"</td><td>"+repayMess[3]+"</td></tr>";
	}
		str = "<div><table class=\"tac\"><thead><tr><th>期数</th><th>还款日期</th><th>还款总额</th><th>本金</th><th>利息</th></tr></thead>"+str+"</table>";
    	$("#test").html(str);
	checkimg();
 }
</script>
  </head>
  
  <body  onload="checkAward();">
<#include "/content/common/header.ftl">
<!-- content -->
<div class="admin clearfix">

	<#include "/content/common/user_center_left.ftl">

	<!-- admin content -->
	<div class="admin-con">
		<!-- breadcrumb -->
		<div class="breadcrumb">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt;<a href="${base}/borrow/userBorrowMgmt.do">借款管理</a>&gt; 天标借款
		</div>
		<div class="tab clearfix">
			<ul>
				<li><a href="#" class="current"><span>修改借款</span></a></li>
			</ul>		
		</div>
		<div class="tab-con">
	         <form name="form1" method="post" action="updateBorrow.do" onsubmit="return check_form();" enctype="multipart/form-data" >
			 <input type="hidden" name="borrow.award" id="award" value="${borrow.award}" />
			 <input type="hidden" name="borrow.isDxb" id="isDxb" value="${borrow.isDxb}" />
			 <input type="hidden" name="strMess" id="strMess" value="${strMess}" />
			 <input type="hidden" name="borrow.id" id="id" value="${borrow.id}" />
	         <input type="hidden" name="number" id="number" value="0" />
	         <input type="hidden" name="borrow.borStages" id="borStages" value="${borrow.borStages}" />
	          <input type="hidden" name="borrow.type" id="borrowType"  value="1" />
	          <div class="frm">
				<table width="90%">
				<tr>
						<td class="item" width="150">借款类型：</td>
						<td>天标</td>
					</tr>
					<tr >
						<td  class="item" >借款标题：</td><td><input type="text" id="borrowname" name="borrow.name" value="${borrow.name}" size="50" /></td>
                    </tr>
								  
					<tr>
                          <td class="item"> 发标者： </td>
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
						 <td><input type="file"  id="fileImage" name="borImagesFile" value="${borrow.borImageFirst}"><!--&nbsp;<input type="button" id="btn_0" value="新增" onclick="addImage('0');"/>--></td>
                     </tr>     
                    <tr >
						  <td class="item">借款金额：</td>
						  <td><input type="text" name="borrow.account"  id="account"  value="${borrow.account}"  onkeyup="value=value.replace(/[^0-9]/g,'')" />  元</td>
                     </tr>  
					 <tr >
						  <td  class="item" >天利率：</td>
						  <td><input type="text"  name="borrow.apr" id="apr" value="${borrow.apr}" onkeyup="clearNoNum(this)" onblur="commit(this);" > ‰，年利率为 <font color="#FF0000" id="yearapr">0</font>%</td>
                      </tr>
							  <tr >
						      <td  class="item" >最小投资额：</td><td>
						        <select id="lowestAccount" class="ddlist" name="borrow.lowestAccount">
	                                   <option value="100" selected="selected">100元</option>
			                   				<@listing_childlist sign="borrow_lowest_account"; listingList>
												<#list listingList as listing>
												<#if listing.description == "100">
												<#else>
													<option value="${listing.description}" >${listing.name}</a>
													</option>
												</#if>
									</#list>
										</@listing_childlist>
								</select>
                              </tr>
							  <tr >
						      <td  class="item" >最大投资额：</td><td>
						       <select id="mostAccount" class="ddlist" name="borrow.mostAccount">
                                   		<option value="" selected="selected">请选择</option>
	                                    <@listing_childlist sign="borrow_most_account"; listingList>
											<#list listingList as listing>
												<option value="${listing.description}" >${listing.name}</a>
												</option>
											</#list>
										</@listing_childlist>
								</select>
						   </td>
                              </tr>
							  <tr >
						      <td  class="item" >投标截止日期：</td><td><div>
						      <select id="validTime" class="ddlist" name="borrow.validTime">
						      	<option value="${borrow.validTime}" selected="selected">${effectiveTime}</option>
	                                    <@listing_childlist sign="borrow_valid_time"; listingList>
											<#list listingList as listing>
											<#if listing.description == borrow.validTime>
											<#else>
												<option value="${listing.description}" >${listing.name}</a>
												</option>
											</#if>
											</#list>
										</@listing_childlist>
								</select>
							</div>
                              </tr>
                              <tr > 
						 <td  class="item" >投标奖励：</td>
						 <td> <input type="checkbox"  name="award1" id="award1" onclick="change_j(1)" value="1" class="autoch"> 是否启用，奖励<input type="text" id="funds" name="borrow.funds" disabled value="${borrow.funds}" onkeyup="clearNoNum(this)" size="10" />% </td>
                      </tr>
                      <tr > 
						 <td  class="item" >发放奖励设置：</td>
						 <td> <input type="checkbox"  name="award2" id="award2" onclick="change_j(2)" value="2" class="autoch"> 是否启用还款完成时发放奖励</td>
                      </tr>
                      <tr >
						   <td  class="item" >借款期限：</td>
						   <td><input type="text" value="${borrow.timeLimit}" name ="borrow.timeLimit" id="timeLimit"  onkeyup="value=value.replace(/[^0-9]/g,'')" >天 <a href="javascript:setRepayment()" id="repaymentPlan" class="btn s1"><span>设置还款计划</span></a></td>
                       </tr>
                         <tr>
                           <td  class="item" >回购日期：</td>
                           <td  class="data-list" valign="top" id="test"></td>
                       </tr>
                       <tr>
						   <td  class="item" >是否为定向标：</td>
						   <td> <input type="checkbox"  name="isDxb1" id="isDxb1" onclick="change_d(1)" value="1" class="autoch"> 是，定向密码为<input type="password" id="pwd" name="borrow.pwd" disabled value="" size="10" /> </td>
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
								<input type="text" name="mycode" id="mycode"> &nbsp;<img  id="code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" /></br>
							</td>
						</tr>
						<tr>
							<td class="item" valign="top"></td>
							<td><input type="submit" value="确 定" class="btn s3" />
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

 <!--帐户信息公开设置 结束-->
<!--帐户信息公开设置 开始-->
  </body>
</html>
