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
    <title>抽奖活动页面</title>
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
<body>
    <input type="hidden" value="${awarInfo.isAward}" id="isAward">
    <input type="hidden" value="${awarInfo.myAwar}" id="myAwar">
    <article class="wrap">
        <section class="dial-wrap">
            <div class="dial-lamp"></div>
            <div class="dial-disk" id="rotate"></div>
            <div class="dial-get">
                <a href="javascript:;" class="btn btned" id="pointer"></a>
            </div>
        </section>
        <section class="help-btns">
            <a href="cj_rule.ftl"></a><a href="javascript:;"></a>
        </section>
        <#if awarInfo.myAwar == null>
        <section class="win-list" id="winList">
            <ul class="list">
                <#list awardInfoList as ap>
                    <li>
                        <span class="name">${ap.name}</span>
                        <span >${ap.awardName}</span>
                        <span class="time">${ap.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                    </li>
                </#list>
            </ul>
        </section>
        <#else>
        <section class="win-my " id="myWin">
            <div class="pic">
                <img src="../../assets/h5cj/img/my/${awarInfo.awardCode}.png" alt="">
            </div>
            <h3 class="title">${awarInfo.myAwar}</h3>
            <div class="hint"><span>温馨提示：</span><p>请您在中奖后3个工作日内保持手机畅通，以便客服专员通过电话与您确认中奖信息。</p></div>
        </section>
        </#if>
    </article>
    <script src="../../assets/h5cj/js/jquery.min.js"></script>
    <script src="../../assets/h5cj/js/jQueryRotate.js"></script>
    <script src="../../assets/h5cj/js/layer_mobile/layer.js"></script>
    <script src="../../assets/h5cj/js/cj.js"></script>
</body>
</html>