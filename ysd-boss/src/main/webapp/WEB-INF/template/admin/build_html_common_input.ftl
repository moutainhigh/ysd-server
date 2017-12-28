<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>前台页面更新</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	$("#inputForm").submit(function() {
		$("#id").val($("#idSelect").val());
		$("#maxResults").val($("#maxResultsInput").val());
	});

	$("#inputForm").ajaxForm({
		dataType: "json",
		beforeSubmit: function(data) {
			$("#status").text("正在进行更新操作,请稍后...");	
		},
		success: function(data) {
			$.dialog({type: "success", content: "页面更新成功! ", width: 280, modal: true, autoCloseTime: 3000});
		}
	});

})
</script>
</head>
<body class="input">
	<div class="bar">
		前台页面更新
	</div>
	<div class="body">
		<form id="inputForm" action="${Application["qmd.setting.url"]}/buildHtml/ajaxAll.do" method="post">
			<input type="hidden" id="id" name="id" value="" />
			<input type="hidden" id="maxResults" name="maxResults" value="" />
			<input type="hidden" id="firstResult" name="firstResult" value="0" />
			<table class="inputTable">
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						更新系统设置及头部、底部页面
					</td>
				</tr>
				<tr id="statusTr" class="hidden">
					<th>
						&nbsp;
					</th>
					<td>
						<span class="loadingBar">&nbsp;</span>
						<p id="status"></p>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" id="submitButton" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>