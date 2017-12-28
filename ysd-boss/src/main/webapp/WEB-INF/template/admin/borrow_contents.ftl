<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>修改标页面 </title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">

function checkAward(){
 	var award =  document.getElementById("award").value;
 	var isDxb =  document.getElementById("isDxb").value;
 	var obj=document.getElementsByName('award1');
  	var ojb=document.getElementsByName('award2'); 
  	 var dxb=document.getElementsByName('isDxb1');  
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
	for(var i=0; i<dxb.length; i++){ 
	 	if(isDxb==1){
	 		dxb[i].checked = true;
	 		jQuery("#pwd").attr("disabled",false); 
	  	}
	}
 }
 
//<!--控制奖励-->
function change_j(type){
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
function change_d(type){
  var obj=document.getElementsByName('isDxb1');  
  for(var i=0; i<obj.length; i++){
    if(obj[i].checked) {
    	jQuery("#pwd").attr("disabled",false); 
    	document.getElementById("isDxb").value=obj[i].value;
    }else{
    	jQuery("#pwd").attr("disabled",true);
    	document.getElementById("isDxb").value=0;
    	document.getElementById("pwd").value="";
    }
   }
}	

function check_form(){
	 var frm = document.forms['validateForm'];
	 var isDxb = frm.elements['isDxb'].value;
	 var pwd = frm.elements['pwd'].value;
	 var award = frm.elements['award'].value;
	 var funds = frm.elements['funds'].value;
	 var errorMsg = '';
	  if ((isDxb == 1 && pwd.length<0)||(isDxb == 1 && pwd =="")) {
		errorMsg += '请输入定向密码' + '\n';
	  }
	  
	  if(award != 0 && !funds>0 ){
	  	errorMsg += '请输入奖励值' + '\n';
	  }
	  if (errorMsg.length > 0){
		alert(errorMsg); return false;
	  } else{  
		return true;
	  }
}
</script>
</head>
<body class="input" onload="checkAward()">
	<div class="body">
		<form id="validateForm" action="borrow!updateMess.action" onsubmit="return check_form();"  method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="borrow.user.id" value="${borrow.user.id}" />
			<!--<input type="hidden" name="borrow.name" value="${borrow.name}" />-->
			<input type="hidden" name="borrow.award" id="award" value="${borrow.award}" />
			<input type="hidden" name="borrow.isDxb" id="isDxb" value="${borrow.isDxb}" />
	<div class="bar">
		贷款管理 / 修改标  
	</div>
			<table class="inputTable">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
							${borrow.user.username} 
					</td>
				</tr>
				<tr>
					<th>
						借款标题: 
					</th>
					<td>
						<input type="text" name="borrow.name"  value="${borrow.name}" />
					</td>
				</tr>
				<tr>
					<th>
						借款期限: 
					</th>
					<td>
					<#if borrow.type==15>
						${borrow.timeLimit}月
					<#else>
						${borrow.timeLimit}天
					</#if>
					</td>
				</tr>
				<tr>
					<th>
						借贷总金额: 
					</th>
					<td>
						${borrow.account}
					</td>
				</tr>
				<#if borrow.type!=2>
				<tr>
					<#if borrow.type==4>
						<th>年利率:</th>
						<td>${borrow.apr}%</td>
					<#else>
						<th>
							天利率:
						</th>
						<td>
							${borrow.apr}‰
						</td>
					</#if>
				</tr>
				<tr>
					<th>
						最低投标金额: 
					</th>
					<td>
						${borrow.lowestAccount}
					</td>
				</tr>
				<#if type==1>
				<tr>
					<th>
						最多投标总额:
					</th>
					<td>
						<#if !borrow.mostAccount&&borrow.mostAccount!=''>
								${borrow.mostAccount}份
							<#else>
								没有限制
						</#if>
					</td>
				</tr>
				<#else>
				<tr>
					<th>
						最多投标总额:
					</th>
					<td>
					<select name="borrow.mostAccount">
						<#if  borrow.mostAccount && borrow.mostAccount !=0>
							<option value="${borrow.mostAccount}" selected="selected">${borrow.mostAccount}元</option>
						<#elseif borrow.mostAccount==0>
							<option value="${borrow.mostAccount}" selected="selected">没有限制</option>
						</#if>
	                    <@listing_childlist sign="borrow_most_account"; listingList>
							<#list listingList as listing>
								<#if listing.description !=borrow.mostAccount >
									<option value="${listing.description}" >${listing.name}</a></option>
								</#if>
							</#list>
					</@listing_childlist>
					</select>
			
					</td>
				</tr>
				<tr>
					<th>
						有效时间: 
					</th>
					<td>
						<input type="text" name="borrow.validTime" maxlength ="5" size="3" value="${borrow.validTime}" />天
					</td>
				</tr>
			</#if>
				<tr>
					<th>
						参考有效时间: 
					</th>
					<td>
						${borrow.validTime}天
					</td>
				</tr>
				<#else>
				<tr>
					<th>借贷总份数: </th>
					<td>${borrow.wanderPieceSize} 份</td>
				</tr>
				<tr>
					<th>单份金额: </th>
					<td>${borrow.wanderPieceMoney} 元/份</td>
				</tr>
				<tr>
					<th>
						天利率:
					</th>
					<td>
						${borrow.apr}‰
					</td>
				</tr>
				<tr>
					<th>最低投标金额: </th>
					<td>${borrow.lowestAccount}份</td>
				</tr>
				<tr>
					<th>最多投标总额: </th>
					<td>
						<select name="borrow.mostAccount">
							<#if !borrow.mostAccount>
								<option value="">没有限制</option>
							<#else>
								<option value="" selected="selected">没有限制</option>
							</#if>	
						 	<@listing_childlist sign="borrow_most_account"; listingList>
								<#list listingList as listing>
									<#if listing.description !=borrow.mostAccount >
										<option value="${listing.description}" >${listing.name}</a></option>
									<#else>
										<option value="${listing.description}" selected="selected">${listing.name}</a></option>
									</#if>
								</#list>
							</@listing_childlist>
						</select>
					</td>
				</tr>
				
				</#if>
				<#if type==1> 
					
				<#else>
					 <tr > 
							 <th>投标奖励：</th>
							 <td> <input type="checkbox"  name="award1" id="award1" onclick="change_j(1)" value="1" class="autoch"> 是否启用，奖励<input type="text" id="funds" name="borrow.funds" disabled value="${borrow.funds}" onKeyUp="value=value.replace(/[^0-9.]/g,'')" size="10" />% </td>
	                      </tr>
	                      <tr > 
							 <th>发放奖励设置：</th>
							 <td> <input type="checkbox"  name="award2" id="award2" onclick="change_j(2)" value="2" class="autoch"> 是否启用还款完成时发放奖励</td>
	                      </tr>
					<tr>
						<th>
							是否为定向标: 
						</th>
						   <td> <input type="checkbox"  name="isDxb1" id="isDxb1" onclick="change_d(1)" value="1" class="autoch"> 是，定向密码为<input type="password" id="pwd" name="borrow.pwd" disabled value="${borrow.pwd}" size="10" /> </td>
					</tr>
					<tr>
				</#if>	
		    <th>借款内容： </th>
		   <td>
                     <div class="showtab1">
						${borrow.content}
					</div>
           </td>
          </tr>   
          	<tr>	
		    	<th>置顶： </th>
		   		<td>
                     <select name="borrow.showTop">
                     	<option value="0" <#if borrow.showTop==0>selected="selected"</#if> >不置顶</option>
                     	<option value="1" <#if borrow.showTop==1>selected="selected"</#if> >置顶</option>
                     </select>
           		</td>
          	</tr>
          	<tr>
					<th>pc首页展示: </th>
					<td>
						<select id="rong_xun_flg" name="borrow.rongXunFlg" >
							<option value="0"<#if borrow.rongXunFlg==0>selected="selected"</#if>>普通</option>
							<option value="1" <#if borrow.rongXunFlg==1>selected="selected"</#if>>热门推荐</option>
							<option value="2" <#if borrow.rongXunFlg==2>selected="selected"</#if>>新手福利</option>
							<option value="3" <#if borrow.rongXunFlg==3>selected="selected"</#if>>精品理财</option>
							<option value="4" <#if borrow.rongXunFlg==4>selected="selected"</#if>>定期优选</option>
						</select>
					</td>
			</tr>
          	<tr>	
		    	<th>排序： </th>
		   		<td>
		   			<input type="text" name="borrow.showSort" maxlength ="10" size="8" value="${borrow.showSort}" onKeyUp="value=value.replace(/[^0-9.]/g,'')" />
           		</td>
          	</tr>
          	   
			</table>
		<!--天标和月标 增加还款计划-->
			<#if borrow.type==1|| borrow.type==4>
			<div class="bar">还款计划</div>
			<table class="inputTable tabContent" >
			 <#if borrow.type==4>
			 	<tr><td>&nbsp;</td><td>期数</td><td>日期</td><td>还款本金</td><td>还款利息</td></tr>
			 <#else>
				<tr><td>&nbsp;</td><td>期数</td><td>天数</td><td>还款本金</td><td>还款利息</td></tr>
			</#if>
				<#list retBorrow.paymentDetail as plan>
				<tr>
					<td>&nbsp;</td>
					<td>${plan_index+1}</td>
					<td>${plan.dateNum}</td>
					<td>${plan.benjinM}</td>
					<td>${plan.lixiM}</td>
				</tr>
				</#list>
			</table>
			</#if>
			
			<!--流转标 增加赎回计划-->
			<#if borrow.type==2>
			<div class="bar">赎回计划</div>
			<table class="inputTable tabContent" width="80%" align="right">
				<tr><td>&nbsp;</td><td>期数</td><td>回购日期</td></tr>
				<#list wanderRepayPlanDetail.wanderRepayPlanEach as plan>
				<tr>
					<td>&nbsp;</td>
					<td>${plan.issue}</td>
					<td>${plan.repayDateStr}</td>
				</tr>
				</#list>
			</table>
			</#if>
			
			<div class="buttonArea">
				<input type="submit" class="formButton" value="修改" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>