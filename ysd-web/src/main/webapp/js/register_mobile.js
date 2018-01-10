$(function () {
    // 服务协议勾选功能
    var $protocol = $('input[name="protocol"]');
    $('#checkBtn').tap(function () {
        var $this = $(this);
        if ($this.hasClass('checked')) {
            $this.removeClass('checked');
            $protocol.val(0);
        } else {
            $this.addClass('checked');
            $protocol.val(1);
        }
    });

    // 错误提示
    var errHint = {
        phone: {
            str1: '手机号码不能为空',
            str2: '手机号码格式不正确'
        },
        code: {
            str1: '短信验证码不能为空',
            str2: '短信验证码不正确'
        },
        password: {
            str1: '密码不能为空',
            str2: '密码格式不正确,长度8~16位，必须包含字母'
        },
        repassword: {
            str: '两个密码不一致'
        },
        myCode: {
            str: '验证码不能为空'
        }
    };

    // 注册表单提交
    var $phone = $('input[name="user.phone"]');
    var $code = $('input[name="codeReg"]');
    var $password = $('input[name="user.password"]');
    var $repassword = $('input[name="password2"]');
    var $protocol = $('input[name="protocol"]');
    var isMobile = /^(?:13\d|15\d|18\d|177|14\d)\d{5}(\d{3}|\*{3})$/;
    var isPassword = /.*[a-zA-Z]+.*$/;

    $('#registerForm').on('submit', function () {
        if (!$phone.val()) {
            alert(errHint.phone.str1);
            return false;
        }
        if (!isMobile.test($phone.val())) {
            alert(errHint.phone.str2);
            return false;
        }
        if (!$code.val()) {
            alert(errHint.code.str1);
            return false;
        }
        if (!$password.val()) {
            alert(errHint.password.str1);
            return false;
        }
        if (!isPassword.test($password.val()) || $password.val().length < 8 || $password.val().length > 16) {
            alert(errHint.password.str2);
            return false;
        }
        if ($repassword.val() !== $password.val()) {
            alert(errHint.repassword.str);
            return false;
        }

        if ($protocol.val() !== '1') {
            alert('请勾选我已阅读并同意服务协议选择框');
            return false;
        }
        $(this).submit();
    });

    // 变换验证码
    var $codeImg = $('#codeImg');
    var $myCode = $('#myCode');
    $codeImg.tap(function () {
        verifyCodeLink();
    });

    // 验证码
    function verifyCodeLink(){
        $codeImg.attr("src", "${base}/rand.do?timestamp" + (new Date()).valueOf());
    }

    // 获取短信验证码
    var $getCode = $('#getCode');
    $getCode.tap(function () {
        if (!$phone.val()) {
            alert(errHint.phone.str1);
            return false;
        }
        if (!isMobile.test($phone.val())) {
            alert(errHint.phone.str2);
            return false;
        }

        if (!$myCode.val()) {
            alert(errHint.myCode.str);
            return false;
        }

        // 获取验证码逻辑
        sendAuthCode(this);
    });

    //发送验证码
    var wait2=60;
    function sendAuthCode(o){
        if (wait2 ==0) {
            o.removeAttribute("disabled");
            o.innerHTML="获取验证码";
            $getCode.removeClass("getcode_disable");
            wait2 = 60;
        } else {
            if(wait2==60){
                var phoneReg = $phone.val();
                var mycode = $myCode.val();
                $.ajax({
                    url: '${base}/sendPhoneCode.do',
                    async:false,
                    type: 'post',
                    dataType:'json',
                    data: {'phoneReg':phoneReg, 'mycode': mycode},
                    beforeSend: function() {
                        o.innerHTML="短信发送中...";
                        o.setAttribute("disabled", true);
                        $getCode.addClass("getcode_disable");
                    },
                    success: function (data) {
                        if(data.result==0){
                            o.innerHTML="重新发送(" + wait2 + ")";
                            wait2--;
                            setTimeout(function(){sendAuthCode(o)}, 1000);
                        }
                        else if(data.result==1){
                            alert("短信发送失败,原因是:"+data.reason);
                            o.removeAttribute("disabled");
                            o.innerHTML="获取验证码";
                            $getCode.removeClass("getcode_disable");
                            verifyCodeLink();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert(errorThrown);
                        verifyCodeLink();
                    }
                });
            }else{
                o.innerHTML="重新发送(" + wait2 + ")";
                wait2--;
                setTimeout(function(){sendAuthCode(o)}, 1000);
            }
        }
    }

    function getPopHtml($obj) {
        var html = $obj.html();
        $obj.remove();
        return html;
    }
    // 协议
    // 获取服务协议html
    var fwProtocoHtml = getPopHtml($('#fwProtocolBox'));
    var tzProtocoHtml = getPopHtml($('#tzProtocolBox'));
    $('#fwProtocolBtn').tap(function () {
        layer.open({
            type: 1,
            content: fwProtocoHtml,
            style: 'width: 85%; height: '+ Math.floor($(window).height() / 1.5) +'px; overflow-y: auto; border-radius: 0.25rem;'
        });
    });

    $('#tzProtocolBtn').tap(function () {
        layer.open({
            type: 1,
            content: tzProtocoHtml,
            style: 'width: 85%; height: '+ Math.floor($(window).height() / 1.5) +'px; overflow-y: auto; border-radius: 0.25rem;'
        });
    });





});