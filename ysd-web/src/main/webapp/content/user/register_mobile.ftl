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
    <title>注册页面</title>
    <link rel="stylesheet" href="../../css/mobile_common.css">
    <link rel="stylesheet" href="../../css/layer_mobile/layer.css">
    <script>
        setSize();
        window.addEventListener("resize",setSize,false);
        window.addEventListener("orientationchange",setSize,false);
        function setSize() {
            var html = document.getElementsByTagName("html")[0];
            var width = html.getBoundingClientRect().width;
            html.style.fontSize = width/16 + 'px';
        }

        document.addEventListener('touchstart',function(ev){
            ev.preventDefault();
        });
    </script>
</head>
<body>
    <header id="header">
        <div class="top"><img src="../img/move/mine_top_bg_image.png" /></div>
    </header>
    <article class="content">
        <section class="register-list">
            <form action="${base}/user/register.do" method="post" id="registerForm" AUTOCOMPLETE="off">
                <div class="line">
                    <span class="icon icon-phone"></span>
                    <input type="number" class="input-text" name="user.phone" placeholder="请输入手机号" />
                </div>
                <div class="line">
                    <span class="icon icon-verification"></span>
                    <input type="text" class="input-text input-text-code" name="mycode" id="myCode" placeholder="请输入验证码" />
                    <a title='点击更换'><img src="${base}/rand.do" class="identify_img" id="codeImg"></a>
                </div>
                <div class="line">
                    <span class="icon icon-verification"></span>
                    <input type="text" class="input-text input-text-code" name="codeReg" placeholder="请输入短信验证码" />
                    <a href="javascript:;" class="btn btn-red btn-getCode" id="getCode">获取验证码</a>
                </div>
                <div class="line">
                    <span class="icon icon-password"></span>
                    <input type="password" class="input-text" name="user.password" placeholder="请输入密码，长度8~16位，必须包含字母" />
                </div>
                <div class="line">
                    <span class="icon icon-password"></span>
                    <input type="password" class="input-text" name="password2" placeholder="再次输入密码" />
                </div>
                <div class="line">
                    <input type="submit" class="btn btn-red btn-submit" value="注册并下载APP" />
                </div>
                <div class="other-line">
                    <span class="icon-check checked" id="checkBtn"></span>我已阅读并同意<a href="javascript:;" class="a-link a-red" id="fwProtocolBtn">《服务协议》</a><a href="javascript:;" class="a-link a-red" id="tzProtocolBtn">《投资服务协议》</a>
                    <input type="hidden" name="protocol" value="1">
                </div>
            </form>
        </section>
    </article>
    <#include "/content/user/fw_protocol_mobile.ftl">
    <#include "/content/user/tz_protocol_mobile.ftl">
    <script src="../../js/zepto.min.js"></script>
    <script src="../../js/layer_mobile/layer.js"></script>
    <script src="../../js/register_mobile.js"></script>
</body>
</html>