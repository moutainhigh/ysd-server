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

	var num=1;
	$('#addHbBtn').click(function(){
		num = 0;
		var tag=$(this).parent().parent();
		$(this).parent().parent().nextAll().each(function(){
			num ++;
			tag = tag.next();
		});
		if(num < 5){
			tag.after('	<tr id="hbListTr'+num+'"><th></th><td><table  class="inputTable tabContent"><tbody><tr><th>红包金额：</th> 	<td><input type="text" class="formText" name="hbMoneyList['+num+']"  onkeyup="value=value.replace(/[^0-9\.]/g,\'\')" maxlength="4"> &nbsp;元</td> 								</tr> 								 <tr> 									<th>使用有效期：</th> 									<td> 										<input type="text" name="hbEndTimeList['+num+']" onkeyup="value=value.replace(/[^0-9]/g,\'\')" autocomplete="off" class="formText"  style="width:45px" maxlength="4" title="使用有效期"  />&nbsp;天 									</td> 								</tr> 								 <tr> 									<th>可用项目期限：</th> 									<td> 										<input type="text" name="hbLimitStartList['+num+']"  class="formText"  onkeyup="value=value.replace(/[^0-9]/g,\'\')" maxlength="4" style="width:40px" autocomplete="off" maxlength="4"/>&nbsp;&nbsp;&nbsp;天 ---&nbsp;&nbsp;&nbsp; 										<input type="text" name="hbLimitEndList['+num+']"  class="formText" onkeyup="value=value.replace(/[^0-9]/g,\'\')" maxlength="4" style="width:40px" autocomplete="off" maxlength="4"/> 天 					</td> 		</tr> <th>满多少可用：</th> 									<td> 										<input type="text" name="hbLimitMaxMoney['+num+']" onkeyup="value=value.replace(/[^0-9]/g,\'\')" autocomplete="off" class="formText"  style="width:45px" maxlength="6" title="满多少可用"  />&nbsp;元 									</td> 								</tr>				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 				 								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="删除" hidefocus class="formButton" onclick="delBtn('+num+');"/>								</td> 								 </tr> 							</tbody> 						</table> 					</td> 				</tr>');
		
		
		}else{
			$.dialog({type: "warn", content: "最多添加5个红包", ok: "确 定", cancel: "取 消"});
		}
	});	
	
	
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"activity.title": "required",
			"activity.content": "required",
			"activity.status": "required",
			"activity.orderList": "digits",
			"imgWeb":{
				imageFile:true
			},
			"imgApp":{
				imageFile:true
			}
		},
		messages: {
			"activity.title": "请填写活动标题",
			"activity.content": "请填写活动内容",
			"activity.status": "请选择活动状态",
			"activity.orderList": "排序必须为零或正整数"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
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
	
	<div class="body">
		<form id="validateForm" action="user_hongbao!addHongbaoPL.action" method="post" enctype="multipart/form-data">
			<table class="inputTable ">
				<tr>
					<th>
						用户名
					</th>
					<td>
						<input type="file" name="fileExc" id="fileExc" />*
					</td>
				</tr>
				<tr>
					<th>
						红包标题
					</th>
					<td>
						<input type="text" name="userHongbao.name" class="formText"/><label class="requireField">*</label>
					</td>
				</tr>
				
			
			
				<tr>
					<th>
						红包设置:
					</th>
					<td>
						<input type="button" value="增加红包" class="formButton" hidefocus id="addHbBtn"/>&nbsp;&nbsp;
			
					</td>
				</tr>
				
			
			
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>