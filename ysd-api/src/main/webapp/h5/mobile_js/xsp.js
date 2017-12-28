/**
 * Created by Administrator on 2015/7/2.
 */
var XSP=XSP||{};
;(function($,WIN){

    XSP.baseUrl="http://www.web.com/";
    XSP.apiUrl="http://www.web.com/rest/";

    //全局的gcs函数
    XSP.gcs = function(url, callback){
        callback = callback || function(){};
        // 用CACHE_TIME来控制JS文件的缓存
        $.ajax({url:url+'?'+CACHE_TIME,dataType:"script",cache:true, success : callback});

        //重写全局的getScript函数
        jQuery.getScript =XSP.gcs;
        $.getScript = XSP.gcs;
    }

    //全局的back 函数
    XSP.back = function(){
        history.back();
    }

    //全局的弹窗组件 toast
    XSP.dialog = XSP.dialog || {};
    XSP.dialog = $.extend({
        show_loading: function() {
            var target = $("#xsp_loading");
            if (target.length===0) {//如果不存在，就往页面中添加
                var newhtml = '<div class="am-modal am-xsp-loading" tabindex="-1" id="xsp_loading"><div class="am-modal-dialog am-xsp-loading-dialog"><i class="am-icon-spinner am-icon-spin am-icon-lg"></i></div></div>';
                $("body").append($(newhtml));
                target = $("#xsp_loading");
            }

            //显示弹窗
            target.modal({
                closeViaDimmer: false
            });
        },

        hide_loading: function() {
            var target = $("#xsp_loading");
            if (target.length===0) return;
            target.modal('close');
            // 如果还有窗口未关闭，则不关闭遮罩层
            if ($('.am-modal:visible').size() > 1) {
                $('.am-dimmer').addClass('am-active').show();
            }
        },

        //类似原生的 toast 提示
        toast:function(msg, seconds, func){
            if (!seconds) seconds = 3000;
            if (!func || typeof func != 'function') func = function() {};

            var $body=$("body");
            // $body.addClass("am-dimmer-active");

            var $toast=$body.find("#toast");
            if($toast.length===0){//如果不存在，就往页面中添加一个 toast 元素
                var $html=$('<div class="am-modal-dialog am-xsp-toast" id="toast" >'+msg+'</div>');
                $body.append($html);
            }else{
                $toast.html(msg);
            }

            //重新获取 toast
            $toast=$body.find("#toast").on('click', function() {
                clearTimeout(toast_timer);
                $toast.slideUp(200, function() {
                    $toast.hide();
                });
            });
            //重新计算 left位置
            //var toastWidth=$toast.css('width').replace("px","");
            //console.log(toastWidth);
            //$toast.css("left",($body.width()-toastWidth)/2+"px");
/*            console.log($body.width());
            console.log(toastWidth);*/

            //显示提示文字
            $toast.slideDown(200);
            var toast_timer = setTimeout(function(){
                $toast.slideUp(200, function() {
                    $toast.hide();
                    func();
                });
            }, seconds);

        },
        alert:function(msg, func){
            if (!func || typeof func != 'function') func = function() {};
            var $html=$("#mytoast");
            if($html.length===0){//如果不存在，就往页面中添加
                var newhtml='<div class="am-modal am-modal-alert am-xsp-alert" tabindex="-1" id="mytoast"><div class="am-modal-dialog"><div class="am-modal-hd">提示</div><div id="msg" class="am-modal-bd">'+msg+'</div><div class="am-modal-footer"><span class="am-modal-btn">确定</span></div></div></div>';
                $("body").append($(newhtml));
            }else{
                $html.find("#msg").html(msg);
            }

            $html=$("#mytoast");

            //显示弹窗
            $html.modal({
                closeViaDimmer: false
            });
            $html.find('.am-modal-btn').off('click.cancel.modal.amui').on('click', func);
        },
        confirm:function(msg, func_yes, func_no){
            if (!func_yes || typeof func_yes != 'function') func_yes = function() {};
            if (!func_no || typeof func_no != 'function') func_no = function() {};
            var $html=$("#myconfirm");
            if($html.length===0){//如果不存在，就往页面中添加
                var newhtml='<div class="am-modal am-modal-confirm am-xsp-confirm" tabindex="-1" id="myconfirm"><div class="am-modal-dialog"><div class="am-modal-hd">提示</div><div id="msg" class="am-modal-bd">'+msg+'</div><div class="am-modal-footer"><span class="am-modal-btn" data-am-modal-confirm>确定</span><span class="am-modal-btn" data-am-modal-cancel>取消</span></div></div></div>';
                $("body").append($(newhtml));
            }else{
                $html.find("#msg").html(msg);
            }

            $html=$("#myconfirm");

            //显示弹窗
            $html.modal({
                closeViaDimmer: false,
                onConfirm: func_yes,
                onCancel: func_no
            });
        },
        custom: function(id, title, content, func){
            if (!func || typeof func != 'function') func = function() {};
            var select = "#"+id;
            var target = $("#"+id);
            if(target.length===0){//如果不存在，就往页面中添加
                var newhtml = '<div class="am-modal am-xsp-custom" tabindex="-1" id="'+id+'"><div class="am-modal-dialog"><div class="am-xsp-custom-titlebar"><a href="javascript:;" class="am-close" data-am-modal-close>&times;</a><span class="am-xsp-custom-title">'+title+'</span></div><div class="am-xsp-custom-content">'+content+'</div></div></div>';
                $("body").append($(newhtml));
                target = $("#"+id);
            } else {
                target.find(".am-xsp-custom-title").html(title);
                target.find(".am-xsp-custom-content").html(content);
            }

            // 隐藏前一个窗口
            target.siblings('.am-xsp-custom.am-modal-active').last().modal('close');

            //显示弹窗
            target.modal({
                closeViaDimmer: false
            });
            target.on('closed.modal.amui', function() {
                var current_modal = $(this);
                var preview_modal = current_modal.siblings('.am-xsp-custom').last();
                func(preview_modal, current_modal);
            });
            return target;
        }
    }, XSP.dialog);

    //全局的发送验证码功能
    XSP.send=XSP.send||{};
    XSP.send = $.extend({
        //发送短信接口
        /**
         * self ：当前对应的按钮
         * phone: 接收短信的手机号码
         * sms_type: 短信的类型
         * time_out: 禁用按钮多少秒
         * */
        send_msg: function(self, phone, sms_type) {

            if (typeof WIN._cs == undefined) WIN._cs = '';
            var hand;
            self.attr("disabled", true);
            $.ajax('/User/send_msg', {
                type: 'post',
                cache: false,
                dataType: 'json',
                data: {
                    phone: phone,
                    sms_type: sms_type,
                    cs: WIN._cs
                },
                success: function(res) {
                    if (res.code == 10000)
                    {
                        res = res.data;
                        var tt = res.time;
                        if (hand)clearInterval(hand);
                        var _interval = function () {
                            tt = tt - 1;
                            if (tt > 0){
                                self.html( tt+'秒后重新发送');
                            }
                            else
                            {
                                if (hand){
                                    self.removeAttr("disabled");//取消按钮的禁用状态
                                    clearInterval(hand);
                                };
                                self.html('发送验证码');
                                tt = 120;
                            }
                        };
                        _interval();
                        hand = setInterval(_interval, 1000);

                        XSP.dialog.toast("验证码已发送");
                    } else {
                        self.removeAttr("disabled");
                        XSP.dialog.toast("验证码发送失败：" + res.msg);
                    }
                }
            });
        }
    }, XSP.send);

    //全局的工具类功能
    XSP.tool = XSP.tool||{};
    XSP.tool = $.extend({

        /**
         * 将数值格式化成金额形式
         * @param num 数值(Number或者String)
         * @return 金额格式的字符串,如'1,234,567.45'
         * @type String
         */
        format_currency: function(num) {
            num = num.toString().replace(/\$|\,/g,'');
            if (isNaN(num)) num = "0";
            var sign = (num == (num = Math.abs(num)));
            num = num.toString();
            var tmp = num.split('.');
            var cents = '';
            if (tmp.length >= 2) {
                num = tmp[0];
                cents = '.'+tmp[1];
            }
            for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
                num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
            return (((sign)?'':'-') + num + cents);
        },

        /**
         * 将数值格式化成已万为单位的形式
         * @param num 数值(Number或者String)
         * @return 金额格式的字符串,如：12万，1000
         * @type String
         */
        format_currency_wan: function(money) {
            var result = money / 10000
            if (result >= 1) {
                return result + '万';
            } else {
                return money;
            }
        },

        // 判断是否微信浏览器
        is_weixin: function() {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        },

    }, XSP.tool);

})(jQuery,window);