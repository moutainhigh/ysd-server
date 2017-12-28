<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
<#include "/content/common/meta.ftl">
    <title>${Application ["qmd.setting.name"]}—国资控股|专业、安全、透明的互联网金融服务平台-我的账户-添加银行卡</title>
    <link rel="stylesheet" href="${base}/css/common.css" />
    <link rel="stylesheet" href="${base}/css/unbind_card.css" />
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
        <form id="bankInputForm" method="post" action="bankSignSaveHnew.do">
            <input type="hidden" name="rechargeMoney" value="${(rechargeMoney)!}">
            <input type="hidden" name="bind_token" value="${(Session.bind_token)!}">
            <input type="hidden" name="accountBank.id" value="${(accountBank.id)!}">
            <div class="input_tips">
                <img src="../img/tips.png" alt="">
                <span>手机号格式错误！</span>
            </div>
            <table>
                <tr>
                    <td class="left">真实姓名：</td>
                    <td><input title="name"  type="text" name="user.realName" data="${(loginUser.realName)! }" id="realName" value="${(loginUser.realName)! }"></td>
                </tr>
                <tr>
                    <td class="left">身份证号：</td>
                    <td><input  type="text" title="id_num" name="user.cardId" value="${loginUser.cardId!}" id="cardId"></td>
                </tr>
                <tr>
                    <td class="left">选择银行：</td>
                    <td>

                        <select id="bankid"  name="accountBank.bankId" >
						<@listing_childlist sign="account_bank"; listingList>
							<#list listingList as listing>
                                <option value="${listing.keyValue}" <#if (listing.keyValue == accountBank.bankId)!> selected</#if>>${substring(listing.name, 30, "...")}</a>
                                </option>
							</#list>
						</@listing_childlist>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="left">银行卡号：</td>
                    <td><input type="text" title="card_num" name="accountBank.account" value="${(accountBank.account)!}"  id="account" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"></td>
                </tr>
                <tr>
                    <td class="left">预留手机号：</td>
                    <td><input type="text" title="bank_name" id="phone" name="accountBank.phone"  value="${(accountBank.phone)!}" placeholder="请填写开户时预留的手机号码"></td>
                </tr>
                <tr>
                    <td class="left">交易密码：</td>
                    <td><input type="password" title="bank_name" id="safepwd" name="safepwd"  value="${(safepwd)!}" placeholder="密码由8~16位字母、数字及符号组成"></td>
                </tr>
                <tr>
                    <td colspan="2" class="button textL">
                        <input type="submit" title="submit" value="确认">
                        <input type="reset" title="reset1">
                    </td>
                </tr>
            </table>
        </form>
        <div class="tips">
            <span class="left">温馨提示：</span>
            <span class="right">
                绑卡认证将验证您的姓名、身份证、银行卡以及办理银行卡时登记的手机号码。
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
<script type="text/javascript" src="${base}/js/jquery/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/js/jquery/bankInput.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/js/jquery/jquery.validate.methods.js"></script>

<script type="text/javascript">
    $().ready( function() {
        $("#header_wdzh").addClass("current");<#-- header.ftl 选中样式 -->
        $('#header_gywm').find('a').css('border',0);
        $(".center_left_bkrz").addClass("current");<#-- user_center_left.ftl 选中样式 -->



        var $areaSelect = $("#areaSelect");

        // 地区选择菜单
        $areaSelect.lSelect({
            url: "${base}/area/ajaxArea.do"// AJAX数据获取url
        });
        var $bankInputForm = $("#bankInputForm");
        $("#account").bankInput();

        // 表单验证
        $bankInputForm.validate({
            rules: {

                "user.realName":{
                    required: true,
                    realName:true,
                    minlength:2,
                    maxlength:20
                },
                "user.cardId":{
                    required: true,
                    //isIdCardNo:true,
                    remote:"${base}/userCenter/checkCardiId.do"
                },
                "accountBank.bankId":{
                    required: true
                },
                "accountBank.account":{
                    required: true,
                    minlength: 12,
                    maxlength:26
                },
                "accountBank.phone":{
                    required: true
                },
                "safepwd":{
                    required: true,
                    minlength: 6,
                    maxlength: 16
                }
            },
            messages: {

                "user.realName":{
                    required: "真实姓名不能为空",
                    realName:"真实姓名格式错误",
                    minlength: "最少2个字",
                    maxlength: "最多20个字"
                },

                "user.cardId":{
                    required:"身份证号码不能为空",
                    isIdCardNo:"身份证号码格式不对",
                    remote:"该身份证号已认证"
                },
                "accountBank.bankId":{
                    required: "请选择开户银行",
                },
                "accountBank.account":{
                    required: "请输入银行账号",
                    minlength: "银行账号最少10位",
                    maxlength: "银行账号最多21位"
                },
                "accountBank.phone":{
                    required:"手机号不能为空"
                },
                "safepwd":{
                    required:"交易密码不能为空",
                    minlength: "最少6个字符",
                    maxlength: "最多16个字符"
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

</script>
</html>
