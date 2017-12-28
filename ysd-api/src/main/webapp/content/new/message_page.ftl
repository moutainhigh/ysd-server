<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, minimum-scale=1, user-scalable=no">
    <title>错误提示</title>
    <link rel="stylesheet" href="${base}/static/css/new/style.css"/>
</head>
<body class="bg-gray">
<!--404-start-->
<div class="prompt">
    <img class="not-find" src="${base}/static/img/new/view_null.png" alt=""/>
    <p>${msg!}</p>
    <div>
        <button type="button" class="btn" onclick="ok()">确定</button>
    </div>
</div>
<!--404-end-->

<!--success-start-->
<#--<div class="prompt">
    <img class="success" src="images/success.png" alt=""/>
    <p>操作成功</p>
    <div>
        <button class="btn">返回</button>
    </div>
</div>-->
<!--success-end-->
</body>
<script type="text/javascript">
    function ok() {
        window.location.href = "${base}/bank/dm_success.do";
    }
</script>
</html>