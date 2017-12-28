<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${str}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">

$().ready( function() {

	var num=${hbList?size};
	$('#addHbBtn').click(function(){
		num = 0;
		var tag=$(this).parent().parent();
		$(this).parent().parent().nextAll().each(function(){
			num ++;
			tag = tag.next();
		});
		if(num < 10){
			tag.after('	<tr id="hbListTr'+num+'"><th></th><td><table  class="inputTable tabContent"><tbody><tr><th>红包金额：</th> 									<td><input type="text" class="formText" name="hbMoney['+num+']"  onkeyup="value=value.replace(/[^0-9\.]/g,\'\')" maxlength="4"> &nbsp;元</td> 								</tr> 								 <tr> 									<th>使用有效期：</th> 									<td> 										<input type="text" name="hbEndTime['+num+']" onkeyup="value=value.replace(/[^0-9]/g,\'\')" autocomplete="off" class="formText"  style="width:45px" maxlength="4" title="使用有效期"  />&nbsp;天 									</td> 								</tr> 								 <tr> 									<th>可用项目期限：</th> 									<td> 										<input type="text" name="hbLimitStart['+num+']"  class="formText"  onkeyup="value=value.replace(/[^0-9]/g,\'\')" maxlength="4" style="width:40px" autocomplete="off" maxlength="4"/>&nbsp;&nbsp;&nbsp;天 ---&nbsp;&nbsp;&nbsp; 										<input type="text" name="hbLimitEnd['+num+']"  class="formText" onkeyup="value=value.replace(/[^0-9]/g,\'\')" maxlength="4" style="width:40px" autocomplete="off" maxlength="4"/> 天 									</td> 								</tr> 								<tr> 									<th>可用终端：</th> 									 <td> 										<label><input type="checkbox" name="hbIsPc['+num+']"/>PC</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 										<label><input type="checkbox" name="hbIsApp['+num+']"  />移动</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 										<label><input type="checkbox" name="hbIsHfive['+num+']" />H5</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="删除" hidefocus class="formButton" onclick="delBtn('+num+');"/>								</td> 								 </tr> 							</tbody> 						</table> 					</td> 				</tr>');
		}else{
			$.dialog({type: "warn", content: "最多添加10个红包", ok: "确 定", cancel: "取 消"});
		}
	});	
	

});

function changeMoney(){
	var money=0;
	if(document.getElementById("check1").checked && $("#num1").val() > 0 ){
		money=10*$("#num1").val();
	}else{
		$("#num1").val(0);
	}
	if(document.getElementById("check2").checked && $("#num2").val() > 0 ){
		money=money+(20*$("#num2").val());
	}else{
		$("#num2").val(0);
	}
	
	if(document.getElementById("check3").checked && $("#num3").val() > 0 ){
		money=money+(50*$("#num3").val());
	}else{
		$("#num3").val(0);
	}
	
	$("#totalMoney").html(money);
}

	function delBtn(numid){
	
		$.dialog({type: "warn", content: "确定要删除此红包设置吗？", ok: "确 定", cancel: "取 消", modal: true, okCallback:right});
			function right(){
				$("#hbListTr"+numid).remove();
			}
	}

</script>
</head>
<body class="input admin">
	<div class="bar">
		编辑${hongbao.typeString}
	</div>
	<div class="body">
		<form id="validateForm" action="hongbao!update.action" method="post">
			<input type="hidden" name="id" value="${hongbao.id}"/>
			<input type="hidden" name="hongbao.type" value="${hongbao.type}"/>
			<table class="inputTable tabContent">
				<tr>
					<th>
						红包类型: 
					</th>
					<td>
						${hongbao.typeString}
					</td>
				</tr>
				<tr>
					<th>
						是否启用: 
					</th>
					<td>
						<input type="radio" name="hongbao.isEnabled" value="1" <#if hongbao.isEnabled ==1>checked </#if>/>开启
						<input type="radio" name="hongbao.isEnabled" value="0" <#if hongbao.isEnabled ==0>checked </#if>/>关闭
					</td>
				</tr>
				<tr>
					<th>
						红包设置:
					</th>
					<td>
						<input type="button" value="增加红包" class="formButton" hidefocus id="addHbBtn"/>&nbsp;&nbsp;
						总额：<label id="totalMoney">${hongbao.total}</label>
					</td>
				</tr>
				<#list hbList as hb>				
				<tr id="hbListTr${hb_index}">
					<th></th>
					<td>
						<table class="inputTable tabContent">
							<tbody>
								<tr>
									<th>红包金额：</th>
									<td><input type="text" name="hbMoney[${hb_index}]" value="${hb.money}" class="formText" onkeyup="value=value.replace(/[^0-9\.]/g,'')" maxlength="4">&nbsp;元</td>
								</tr>
								<tr>
									<th>使用有效期：</th>
									<td>
										<input type="text" name="hbEndTime[${hb_index}]" value="${hb.expDate}" onkeyup="value=value.replace(/[^0-9]/g,'')" autocomplete="off" class="formText"  style="width:45px" title="使用有效期"  maxlength="4"/>&nbsp;天
									</td>
								</tr>
								<tr>
									<th>可用项目期限：</th>
									<td>
										<input type="text" name="hbLimitStart[${hb_index}]" value="${hb.limitStart}"  onkeyup="value=value.replace(/[^0-9]/g,'')" maxlength="4" class="formText" style="width:40px" autocomplete="off" maxlength="4"/>&nbsp;&nbsp;&nbsp;天 ---&nbsp;&nbsp;&nbsp;
										<input type="text" name="hbLimitEnd[${hb_index}]" value="${hb.limitEnd}"  onkeyup="value=value.replace(/[^0-9]/g,'')" maxlength="4" class="formText" style="width:40px" autocomplete="off" maxlength="4"/> 天
									</td>
								</tr>
								<tr>
									<th>可用终端：</th>
									<td>
										<label><input type="checkbox" name="hbIsPc[${hb_index}]" <#if hb.isPc ==1 >  checked="checked" </#if> />PC</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label><input type="checkbox" name="hbIsApp[${hb_index}]" <#if hb.isApp ==1 >  checked="checked" </#if> />移动</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label><input type="checkbox" name="hbIsHfive[${hb_index}]" <#if hb.isHfive ==1 >  checked="checked" </#if> />H5</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									
									<input type="button" value="删除" hidefocus class="formButton" onclick="delBtn(${hb_index});"/>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				</#list>
			
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>