<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#include "/content/common/meta.ftl">
    <title>我的资产</title>
    <link rel="stylesheet" href="../css/common.css" />
    <link rel="stylesheet" href="../css/my_assets.css">
</head>
    <body id="bg">
    <!-- 头部 -->
    <div id="header">
        <div class="headArea">
            <div class="logo">
                <img src="../img/logo.png" alt="logo" />
            </div><!--
                            --><ul class="nav">
            <li><a href="../home/index.html">首页</a></li>
            <li><a href="../center/product_list.html">项目中心</a></li>
            <li><a href="../insurance/insurance.html">安全保障</a></li>
            <li><a href="../about/about_us.html" style="border:none">关于我们</a></li>
            <li class="current"><a class="active" href="./my_assets.html" style="border:none">我的账户</a></li>
            <li class="last"><img src="../img/person_unload.png">
                <!--已登录提示-->
                <div class="already_login">
                    <div class="one">欢迎150****3654</div>
                    <a href=""><div class="two">手机客户端</div></a>
                    <a href="../about/help_center.html"><div class="three">帮助中心</div></a>
                    <div class="four">退出登录</div>
                </div>
                <!--未登录提示-->
                <div class="un_login" style="display: none">
                    <a href="../account/register.html"><div class="one">注册</div></a>
                    <a href="../account/account_login.html"><div class="two">登录</div></a>
                    <a href="../about/help_center.html"><div class="three">帮助中心</div></a>
                    <a href=""><div class="four">手机客户端</div></a>
                </div>
            </li>
        </ul>
        </div>
    </div>

    <!--内容区域-->
    <div id="main">
        <!--右边导航栏-->
        <div class="main_nav fl">
            <ul>
                <li class="current"><a href="./my_assets.html">我的资产</a></li>
                <li><a href="./fund_record.html">资金记录</a></li>
                <li><a href="./add_card.html">绑卡认证</a></li>
                <li><a href="./redbag_bonuses.html">红包奖励</a></li>
                <li><a href="./invest_friend.html">邀请好友</a></li>
                <li><a href="./account_manage.html">账户管理</a></li>
            </ul>
        </div>
        <!--右侧内容区-->
        <div class="content fr">
            <div class="assets">
                <div class="assets_top">
                    <div class="assets_top_msg">
                        <table class="personal">
                            <tr >
                                <td rowspan="2" class="center"><img src="../img/jcb_03.png"></td>
                                <td>您好，15030589645</td>
                                <td class="center"><img src="../img/account_pwd.png"></td>
                                <td class="center"><img src="../img/account_adv.png"></td>
                            </tr>
                            <tr>
                                <td>健康、财富、喜乐全部给您</td>
                                <td class="center font14"><a href="./account_manage.html">密码管理</a></td>
                                <td class="center font14"><a href="./invest_friend.html">邀请好友</a></td>
                            </tr>
                        </table>
                        <table class="person_detail">
                            <tr>
                                <td style="color:#ff9000">90000.00</td>
                                <td>10000.00</td>
                                <td>10000.00</td>
                                <td>10000.00</td>
                                <td>100.00</td>
                            </tr>
                            <tr>
                                <td>账户总额（元）</td>
                                <td>可用余额（元）</td>
                                <td>历史总收益（元）</td>
                                <td>投资中本金（元）</td>
                                <td>待到账收益（元）</td>
                            </tr>
                        </table>
                        <div class="option">
                            <div class="tixian"><a href="./ti_xian.html">提现</a></div>
                            <div class="charge current"><a href="./recharge.html">充值</a></div>
                        </div>
                    </div>
                </div>
                <div class="assets_bottom">
                    <div class="nav">
                        <div class="current">待收回款</div>
                        <div>已收回款</div>
                        <div>投资记录</div>
                    </div>
                    <!--待收回款-->
                    <table class="wait option_content">
                        <tr>
                            <th>回款时间</th>
                            <th>项目名称</th>
                            <th>期数</th>
                            <th>回款本金</th>
                            <th>到期收益</th>
                            <th>状态</th>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td>还款中</td>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td>还款中</td>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td>还款中</td>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td>还款中</td>
                        </tr>
                    </table>
                    <!--已收回款-->
                    <table class="already option_content" style="display: none">
                        <tr>
                            <th>回款时间</th>
                            <th>项目名称</th>
                            <th>期数</th>
                            <th>回款本金</th>
                            <th>到期收益</th>
                            <th>状态</th>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td>还款中</td>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td>还款中</td>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td>还款中</td>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td>还款中</td>
                        </tr>
                    </table>
                    <!--投资记录-->
                    <table class="record option_content" style="display: none">
                        <tr>
                            <th>投资时间</th>
                            <th>项目名称</th>
                            <th>期数</th>
                            <th>回款本金</th>
                            <th>到期收益</th>
                            <th>操作</th>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td class="font_yel"><a href="#">详情</a></td>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td class="font_yel"><a href="#">详情</a></td>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td class="font_yel"><a href="#">详情</a></td>
                        </tr>
                        <tr>
                            <td>2016-8-22</td>
                            <td>金兑宝第96期</td>
                            <td>1</td>
                            <td>10000.00</td>
                            <td>100.00</td>
                            <td class="font_yel"><a href="#">详情</a></td>
                        </tr>
                    </table>
                    <!--分页-->
                    <ul class="mpage">
                        <li>上一页></li><li class="current">1</li><li>2</li><li>3</li><li>...</li><li>15</li><li>下一页></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>

    <!--底部-->
    <div id="footer" style="background: transparent;">
        <div class="footerArea" style="background: url(../img/footer_bg01.png)">
            <div class="wrap_2">
                <div class="footerText">
                    <ul>
                        <li><a href="../home/new_guide.html">新手指引 &nbsp;|</a></li>
                        <li><a href="../about/about_us.html">关于我们 &nbsp;|</a></li>
                        <li><a href="../about/about_us.html#map">联系我们 &nbsp;|</a></li>
                        <li><a href="../insurance/insurance.html">安全保障</a></li>
                    </ul>
                    <p>全国服务热线<br />400-057-7820</p>
                </div>
                <div class="erweima">
                    <#--<img src="../img/erweima.png" alt="二维码" />-->
                    <p>扫描二维码关注微信</p>
                </div>
            </div>
        </div>
        <div class="copyright">
            <p>
                ICP备案：浙ICP备17036398号 <br />
                温州乐商投资有限公司
            </p>
        </div>
    </div>
</body>
<script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../js/my_assets.js"></script>
<script src="../js/common.js"></script>
</html>