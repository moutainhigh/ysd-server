<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
<#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-添加银行卡</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/unbind_card.css" />
    <link rel="stylesheet" href="../css/rongbao.css" />

    <style type="text/css">
    .getcode {
        display: inline-block;
        width: 115px;
        height: 38px;
        border:0;
        border-radius: 10px;
        background: #ef3e44;
        color: #ffffff;
        text-align: center;
        line-height: 38px;
    }

    .getcode_disable {
        display: inline-block;
        width: 115px;
        height: 38px;
        border:0;
        border-radius: 10px;
        background: #ccc !important;
        color: #ffffff;
        text-align: center;
        line-height: 38px;
    }
    </style>
</head>
<body id="bg">
<!-- 头部 -->
<#include "/content/common/header.ftl">

<!--内容区域-->
<div id="main">
    <!--左边导航栏-->
<#include "/content/common/user_center_left.ftl">
    <!--右边主内容区-->
    <div class="content fr">
        <form id="bankInputForm" method="post" action="bankSignPhoneCode.do">
            <input type="hidden" name="bind_token" value="${(Session.bind_token)!}">
            <div class="input_tips">
                <img src="../img/tips.png" alt="">
                <span>短信验证码格式错误！</span>
            </div>
            <table>

                <tr>
                    <td class="left">实名验证码：</td>
                    <td width="266"><input title="checkCode"  type="text" name="checkCode"  id="checkCode"/>
                    </td>
                    <td class="button text">
                        <input type='button' id="sendCode" class='sendCode' value="获取验证码">
                    </td>
                </tr>



                <tr>
                    <td colspan="2" class="button text">
                        <input type="submit" title="submit" value="确认">
                    </td>
                </tr>
            </table>
        </form>
        <div class="tips">
            <span class="left">温馨提示：</span>
            <span class="right">
                绑卡认证将验证您的姓名、身份证、银行卡以及办理银行卡时登记的手机号码。<br>
                绑卡信息验证通过后,会发送短信验证码到你填写的手机号,请注意查收。
                </span>
            <div class="clear"></div>
        </div>
    </div>
    <div class="clear"></div>
</div>

<!--底部-->
<#include "/content/common/footer-center.ftl">

</body>
<script type="text/javascript" src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/common.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>

<script type="text/javascript">
    $().ready( function() {
        $("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
        $('#header_gywm').find('a').css('border',0);
        $(".center_left_bkrz").addClass("current");<#-- user_center_left.ftl 选中样式 -->


        var $bankInputForm = $("#bankInputForm");

        // 表单验证
        $bankInputForm.validate({
            rules: {
                "checkCode":{
                    required: true,
                    minlength:4,
                    maxlength:10
                }
            },
            messages: {
                "checkCode":{
                    required:"验证码不能为空"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parent());
            },
            submitHandler: function(form) {
                form.submit();
            }
        });
    });


    function checkCodes(){
        var code=$('input[name=checkCode]').val();
        if(code=="" || typeof(code)=='undefined'){
            //alert('请输入验证码');
            return false;
        }
    }
    var wait2=60;
    function sendAuthCode(){
        if (wait2 ==0) {
            $('.sendCode').removeAttr("disabled");
            $('.sendCode').removeClass('senderror');
            $('.sendCode').val("获取验证码");
            toggleDisabled(false);
            wait2 = 60;
        } else {
            $('.sendCode').attr("disabled", true);
            $('.sendCode').addClass('senderror');
            $('.sendCode').val("重新发送(" + wait2 + ")");
            wait2--;
            setTimeout(function(){sendAuthCode()}, 1000);
        }
    }

    var isMobile=/^[1][0-9]{10}$/;
    $('.sendCode').click(function(){

        $.ajax({
            url: '/userCenter/bankSignPhoneCodeRepeat.do',
            async:false,
            type: 'post',
            dataType:'json',
            data: {'phone':''},
            success:function(data){
                sendAuthCode();
                toggleDisabled(true);
            },
            error:function(){
                alert('发送失败，请重新发送');
                toggleDisabled(false);
            }
        });
    });

    $('.sendCode').click();

    function toggleDisabled(disabled) {
        if (disabled) {
            $(".sendCode").removeClass("getcode");
            $(".sendCode").addClass("getcode_disable");
        } else {
            $(".sendCode").removeClass("getcode_disable");
            $(".sendCode").addClass("getcode");
        }

    }


</script>
</html>
