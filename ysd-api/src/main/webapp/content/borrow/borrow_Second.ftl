<!DOCTYPE html>
<html>
  <head>
  <title>${Application ["qmd.setting.name"]}-借款人-借款标信息修改填写页</title>

  	<#include "/content/common/meta.ftl">
	<script src="${base}/static/js/borrow/borrowUpdate.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
<SCRIPT language=javascript> 
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
	checkimg();
 }
</script>
  </head>
  
<body onload="checkAward()">
<!-- header -->
<#include "/content/common/header.ftl">
<!-- content -->
<div class="admin clearfix">

	<#include "/content/common/user_center_left.ftl">

	<!-- admin content -->
	<div class="admin-con">
		<!-- breadcrumb -->
		<div class="breadcrumb">
			<a href="#">${Application ["qmd.setting.name"]}</a>&gt;<a href="${base}/borrow/userBorrowMgmt.do">借款管理</a> &gt; 秒标借款
	</div>
	<div class="tab clearfix">
		<ul>
			<li><a href="" class="current"><span>秒标修改</span></a></li>
		</ul>		
	</div>
	<div class="tab-con">
	    <form name="form1" method="post" action="updateSecondBorrow.do"  onsubmit="return check_form();" enctype="multipart/form-data" >
	    	<input type="hidden" name="borrow.id" value="${borrow.id}" />
	    	<input type="hidden" name="borrow.award" id="award" value="${borrow.award}" />
	    	<input type="hidden" name="borrow.isDxb" id="isDxb" value="${borrow.isDxb}" />
	    	<input type="hidden" name="borrow.validTime" value="${borrow.validTime}" />
	    	 <input type="hidden" name="borrow.type" id="borrowType"  value="0" />
	    	 <input type="hidden" name="borrow.timeLimit" id="timeLimit" value="1" />
	        <div class="frm">
				<table width="90%">
					<tr>
						<td class="item" width="150">借款类型：</td>
						<td>秒标	</td>
					</tr>
					<tr>
						<td class="item">借款标题：</td>
						<td><input type="text" id="borrowname" name="borrow.name" value="${borrow.name}" size="50" /></td>
                    </tr>
                   	<tr>
                        <td class="item">借款人类型：</td>
                        <td>
                          <#if memberType==1>企业<#else>个人</#if>
                        </td>
					</tr>
                    <tr id="testtest0">
						 <td class="item">上传图片：</td>
						 <td><input type="file"  id="fileImage" name="borImagesFile" value=""><!--&nbsp;<input type="button" id="btn_0" value="新增" onclick="addImage('0');"/>--></td>
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
						  <td  class="item" >最小投资额：</td>
						  <td>
							<select id="lowestAccount" class="ddlist" name="borrow.lowestAccount">
			                	<@listing_childlist sign="borrow_lowest_account"; listingList>
									<#list listingList as listing>
											<option value="${listing.description}" <#if listing.description==borrow.lowestAccount>selected="selected"</#if> >${listing.name}</option>
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
										<option value="${listing.description}" <#if listing.description==borrow.mostAccount>selected="selected"</#if> >${listing.name}</option>
									</#list>
								</@listing_childlist>
							 </select>
						   </td>
                       </tr>
						  <tr > 
						   <td  class="item" >是否为定向标：</td>
						   <td> <input type="checkbox"  name="isDxb1" id="isDxb1" onclick="change_d(1)" value="1" <#if borrow.isDxb == "1">checked</#if> class="autoch"> 是，定向密码为<input type="password" id="pwd" name="borrow.pwd" value="<#if borrow.isDxb == "1">${borrow.pwd}</#if>" <#if borrow.isDxb != "1">disabled</#if> size="10" /> </td>
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
								<input type="text" name="mycode" id="mycode"> &nbsp;<img id="code_img" src="${base}/rand.do" onclick="changeValidateCode(this)" title="点击图片重新获取验证码" /></br>
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
			</div>
		
		</div>
		</form>
	</div>

  <!--footer-->
  <#include "/content/common/footer.ftl">

 <!--帐户信息公开设置 结束-->
<!--帐户信息公开设置 开始-->
  </body>
</html>
