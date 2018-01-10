<!DOCTYPE html>
<html>
  <head>
  <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-借款人-发布天标</title>
	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/borrow/borrowSet.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
  </head>
  
<body onload="checkimg()">
<!-- header -->
<#include "/content/common/header.ftl">
<!-- content -->
<div class="admin clearfix">

	<#include "/content/common/user_center_left.ftl">

<!-- admin content -->
	<div class="admin-con">
		<!-- breadcrumb -->
		<div class="breadcrumb">
			<a href="${base}/">${Application ["qmd.setting.name"]}</a> &gt;<a href="${base}/userCenter/index.do">我的账户</a>&gt;<a href="${base}/borrow/borrowChoose.do">我要借款</a>&gt; 天标借款
		</div>
		<div class="tab clearfix">
			<ul>
				<li><a href="#" class="current"><span>我要借款</span></a></li>
			</ul>		
		</div>
		<div class="tab-con">
	    <form id="form1" name="form1" method="post" action="${base}/borrow/addBor.do"  onsubmit="return check_form();" enctype="multipart/form-data" >
			 <input type="hidden" name="borrow.award" id="award" value="0" />
			 <input type="hidden" name="borrow.isDxb" id="isDxb" value="0" />
	         <input type="hidden" name="number" id="number" value="0" />
	         <input type="hidden" name="borrow.borStages" id="borStages" value="0" />
	         <input type="hidden" name="borrow.type" id="borrowType" value="1" />
	         <input type="hidden" name="borrow.isday" value="1" />
	         <div class="frm">
				<table width="90%">
					<tr>
						<td class="item" width="150">借款类型：</td>
						<td>天标</td>
					</tr>
					<tr>
						<td class="item">借款标题：</td>
						<td><input type="text" id="borrowname" name="borrow.name" value="" size="50" /></td>
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
                          	<option value="0">无担保，无抵押</option>
                          	<option value="1">有担保，无抵押</option>
                          	<option value="2">无担保，有抵押</option>
                          	<option value="3">有担保，有抵押</option>
                          </select>
                        </td>
					</tr>
                    <tr id="testtest0">
						 <td class="item">上传图片：</td>
						 <td><input type="file" id="fileImage" name="borImagesFile"><!--&nbsp;<input type="button" id="btn_0" value="新增" onclick="addImage('0');"/>--></td>
                     </tr>    
                    <tr >
						  <td class="item">借款金额：</td>
						  <td><input type="text" name="borrow.account"  id="account"  value=""  onkeyup="value=value.replace(/[^0-9]/g,'')" />  元</td>
                     </tr>  
					 <tr >
						  <td  class="item" >天利率：</td>
						  <td><input type="text"  name="borrow.apr" id="apr" value="" onKeyUp="value=value.replace(/[^0-9.]/g,'')" onblur="commit(this);" > ‰，年利率为 <font color="#FF0000" id="yearapr">0</font>%</td>
                      </tr>
					 <tr >
						  <td  class="item" >最小投资额：</td>
						  <td>
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
							</td>
                      </tr>
					  <tr >
						  <td  class="item" >最大投资额：</td><td>
							<select id="mostAccount" class="ddlist" name="borrow.mostAccount">
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
                          
                      <tr > 
						 <td  class="item" >投标奖励：</td>
						 <td> <input type="checkbox"  name="award1" id="award1" onclick="change_j(1)" value="1" class="autoch"> 是否启用，奖励<input type="text" id="funds" name="borrow.funds" disabled value="" onKeyUp="value=value.replace(/[^0-9.]/g,'')" size="10" />% </td>
                      </tr>
                      <tr > 
						 <td  class="item" >发放奖励设置：</td>
						 <td> <input type="checkbox"  name="award2" id="award2" onclick="change_j(2)" value="2" class="autoch"> 是否启用还款完成时发放奖励</td>
                      </tr>
                      <tr >
						   <td  class="item" >借款期限：</td>
						   <td><input type="text" value="" name ="borrow.timeLimit" id="timeLimit" onblur="checkNumber()" onkeyup="value=value.replace(/[^0-9]/g,'')" >天 <a href="javascript:setRepayment()" id="repaymentPlan" class="btn s1"><span>设置还款计划</span></a></td>
                       </tr>
                       <tr>
                           <td class="item" valign="top">回购日期：</td>
                           <td  class="data-list" valign="top" id="test"></td>
                       </tr>
                       <tr > 
						   <td  class="item" >是否为定向标：</td>
						   <td> <input type="checkbox"  name="isDxb1" id="isDxb1" onclick="change_d(1)" value="1" class="autoch"> 是，定向密码为<input type="password" id="pwd" name="borrow.pwd" disabled value="" size="10" /> </td>
                        </tr>
                        <tr>
							<td class="item"> 借款描述： </td>
                            <td >
                              	<textarea id="editor" name="borrow.content" class="editor"  ></textarea>
                             </td>
                        </tr>
						<tr>
                       		<td class="item" >验证码：</td>
                       		<td>
 								<input type="hidden" value="" name="id" />
								<input type="text" name="mycode" id="mycode"> &nbsp;<img id = "code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" /></br>
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

 <!--帐户信息公开设置 结束-->
<!--帐户信息公开设置 开始-->
  </body>
<script type="text/javascript">
$(function(){
	$("#borrow_want").addClass("current");
});
</script>
</html>
