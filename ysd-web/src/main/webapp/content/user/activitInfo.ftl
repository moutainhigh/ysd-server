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
    <title>活动页面</title>
    <link rel="stylesheet" href="../css/activit1.css">
    <script>
        setSize();
        window.addEventListener("resize",setSize,false);
        window.addEventListener("orientationchange",setSize,false);
        function setSize() {
            var html = document.getElementsByTagName("html")[0];
            var width = html.getBoundingClientRect().width;
            html.style.fontSize = width/16 + 'px';
        }
//        document.addEventListener('touchstart',function(ev){
//            ev.preventDefault();
//        });
    </script>
</head>
<body>
    <div class="main">
        <div class="pic-box">
            <img src="../img/activity_head.jpg" alt="">
        </div>
        <div class="pic-box">
            <img src="../img/activity_text.jpg" alt="">
        </div>
        <div class="pic-box">
            <img src="../img/activity_jp.jpg" alt="">
        </div>
        <div class="activity_info">
            <div class="text">
                <p><strong>参与资格：</strong>购买乐商贷平台【企业宝】的用户。</p>
                <p><strong>斩标规则：</strong>[企业宝] 标的满标后，单用户对该标的购买总额由高到低进行排名。受邀用户投资成功即获一次抽奖（100%中奖）。（仅限杭州，乐清地区，若因地域问题无法参与，可兑换相对应礼品）。名次若出现并列情况，依照交易完成时间先后顺序为准。</p>
                <p><strong>斩标公布：</strong>满标后5个工作日内， 获奖信息以手机短信和其他方式进行通知。</p>
                <p><strong>奖品兑换：</strong>奖品将采取邮寄派送，杭州地区可送货上门。</p>
            </div>
        </div>
        <div class="activity_about">
            <ul>
                <li><span>年化收益率：</span><p>10.60%</p></li>
                <li><span>锁&nbsp;&nbsp;&nbsp;定&nbsp;&nbsp;&nbsp;期：</span><p>6个月（183天）</p></li>
                <li><span>收&nbsp;益&nbsp;处&nbsp;理：</span><p>锁定期结束后返还到投资账户</p></li>
                <li><span>加&nbsp;入&nbsp;条&nbsp;件：</span><p>100元起投，且以1元的整数倍递增</p></li>
                <li><span>用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;途：</span><p>经乐商贷平台审核后，符合资质的中大型企业用于资金周转。</p></li>
            </ul>
        </div>
        <div class="activity_guide">
            <div class="text">
                <p>企业宝是乐商贷平台推出的稳定收益标的。经由出借人授权，并由系统为出借人实现分散投标，节省投资时间、提高投资效率的投标产品。平台通过多层风控把关，严格的标的筛选的过程，再以分散投标，降低出借人投资风险。</p>
                <p>购买企业宝产品之后，资金无闲置立即开始投标，标的投满审核通过后，即进入投资锁定期，投资锁定期结束后， 本金收益一并返还到投资账户中。</p>
            </div>
        </div>
        <div class="activity_footer">
            <span class="activity_footer_bg1"></span>
            <p>※本活动最终解释权归活动方所有</p>
            <span class="activity_footer_bg2"></span>
        </div>
        <!--<div class="link-bottom"><a href="#" class="btn">进入企业宝</a></div>-->
    </div>
</body>
</html>