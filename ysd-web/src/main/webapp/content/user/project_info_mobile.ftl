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
    <title>项目介绍</title>
    <link rel="stylesheet" href="${base}/css/mobile_common.css">
    <script>
        setSize();
        window.addEventListener("resize", setSize, false);
        window.addEventListener("orientationchange", setSize, false);

        function setSize() {
            var html = document.getElementsByTagName("html")[0];
            var width = html.getBoundingClientRect().width;
            html.style.fontSize = width / 16 + 'px';
        }

        /*document.addEventListener('touchstart', function (ev) {
            ev.preventDefault();
        });*/
    </script>
</head>
<body>
    <header id="header">
        <div class="top"><img src="../img/move/mine_top_bg_image.png"/></div>
    </header>
    <article class="content">
        <section class="project-info">
            <h1>项目介绍</h1>
            <p>乐商宝是乐商贷平台推出的稳定收益标的。经由出借人授权，并由系统为出借人实现分散投标，节省投资时间、提高投资效率的投标产品。平台通过多层风控把关，严格的标的筛选的过程，再以分散投标，降低出借人投资风险。</p>
            <p>购买乐商宝产品之后，资金无闲置立即开始投标，标的投满审核通过后，即进入投资锁定期，投资锁定期结束后， 本金收益一并返还到投资账户中。</p>
            <p class="text-center"><strong>经乐商贷多层风控把关、严格筛选的优质资产标的。</strong></p>
            <div class="reason-w">
                <ul class="list">
                    <li><div class="pic"><img src="../img/move/spread_icon_01.png" alt=""></div><strong class="tit">合规</strong></li>
                    <li><div class="pic"><img src="../img/move/spread_icon_02.png" alt=""></div><strong class="tit">安全</strong></li>
                    <li><div class="pic"><img src="../img/move/spread_icon_03.png" alt=""></div><strong class="tit">权威</strong></li>
                    <li><div class="pic"><img src="../img/move/spread_icon_04.png" alt=""></div><strong class="tit">实力</strong></li>
                </ul>
            </div>
            <h2>乐商宝项目介绍</h2>
            <ul class="ul-infos">
                <li>预期年化收益率：6.40%</li>
                <li>锁定期： 1 个月（31天）</li>
                <li>收益处理方式：锁定期结束后返还到投资账户</li>
                <li>加入条件：100元起投，且以1元的整数倍递增</li>
            </ul>
            <div class="help">
                <h2>常见问题</h2>
                <dl class="dl-list">
                    <dt>1. 乐商宝安全吗？</dt>
                    <dd>乐商贷以严谨负责的态度对每笔借款进行严格筛选。同时，乐商宝所对应借款100%适用乐商贷本金保障计划。</dd>
                    <dt>2. 乐商宝发布时间？</dt>
                    <dd>自2017年10月起，乐商宝发布会根据每天现有标的的情况，不定时更新</dd>
                    <dt>3. 乐商宝收益处理方式是什么？</dt>
                    <dd>乐商宝所投标的，严格按照日期，按时返还本金和利息到投资人账户。中途不会做任何再投资。</dd>
                    <dt>4. 乐商宝投得标的是否可以转让？</dt>
                    <dd>乐商宝所投的每一个标的，均不可以转让债权。</dd>
                    <dt>5. 乐商宝获得的收益是否有管理费？</dt>
                    <dd>乐商宝所投标的均和乐商贷其他标的一样，不进行收取管理费</dd>
                </dl>
            </div>
            <div class="hint-w">
                <h2>风险提示</h2>
                <p class="text">市场有风险，投资需谨慎。请您在投资前，仔细阅读<a href="#">《投资风险说明和禁止性行为》</a>。</p>
            </div>
        </section>
    </article>
</body>
</html>