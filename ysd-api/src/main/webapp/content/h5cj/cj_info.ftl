<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,user-scalable=no">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="x5-orientation" content="portrait">
    <meta name="x5-fullscreen" content="true">
    <meta name="screen-orientation" content="portrait">
    <meta name="full-screen" content="yes">
    <title>抽奖活动-活动规则</title>
    <link rel="stylesheet" href="../../assets/h5cj/css/cj.css">
    <script>
        setSize();
        window.addEventListener("resize",setSize,false);
        window.addEventListener("orientationchange",setSize,false);
        function setSize() {
            var html = document.getElementsByTagName("html")[0];
            var width = html.getBoundingClientRect().width;
            html.style.fontSize = width/16 + 'px';
        }
    </script>
</head>
<body class="cj-info-body">
    <article class="cj-info-main">
        <h1>填写中奖信息</h1>
        <form>
            <div class="form-list">
                <div class="line">
                    <label class="tit">姓名</label>
                    <input type="text" name="name" class="input-text" placeholder="请输入姓名" />
                </div>
                <div class="line">
                    <label class="tit">联系电话</label>
                    <input type="number" name="iphone" class="input-text" placeholder="请输入联系电话" />
                </div>
                <div class="line">
                    <label class="tit">收件地址</label>
                    <textarea name="address" class="textarea-text" placeholder="请输入收件地址"></textarea>
                </div>
                <div class="btn-box">
                    <a href="javascript:;" class="btn" id="subBtn"></a>
                </div>
            </div>
        </form>
    </article>
</body>
</html>