$(function () {
    /**
     * 获取url查询参数
     * @param string 要查询的url地址
     * @return String
     * */
    function getQueryString(str) {
        var strVal = location.search.match(new RegExp("[\?\&]"+str+"=([^\&]*)(\&?)","i"));
        return strVal ? strVal[1] : strVal;
    }

    /**
     * 对日期进行格式化，
     * @param date 要格式化的日期
     * @param format 进行格式化的模式字符串
     *     支持的模式字母有：
     *     y:年,
     *     M:年中的月份(1-12),
     *     d:月份中的天(1-31),
     *     h:小时(0-23),
     *     m:分(0-59),
     *     s:秒(0-59),
     *     S:毫秒(0-999),
     *     q:季度(1-4)
     * @return String
     */
    function dateFormat(date, format){
        date = new Date(date);
        format = format || 'yyyy-MM-dd hh:mm:ss';
        var map = {
            "M": date.getMonth() + 1, //月份
            "d": date.getDate(), //日
            "h": date.getHours(), //小时
            "m": date.getMinutes(), //分
            "s": date.getSeconds(), //秒
            "q": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
        };
        format = format.replace(/([yMdhmsqS])+/g, function(all, t){
            var v = map[t];
            if (v !== undefined) {
                if (all.length > 1) {
                    v = '0' + v;
                    v = v.substr(v.length - 2);
                }
                return v;
            }
            else if (t === 'y') {
                return (date.getFullYear() + '').substr(4 - all.length);
            }
            return all;
        });
        return format;
    }

    var id = $('#userId').val();

    // 我的奖品
    var myWin = [
        { imgSrc: '1.png', text: '精美瓷器碗碟一套' },
        { imgSrc: '2.png', text: '多功能榨汁机一台' },
        { imgSrc: '3.png', text: '3D耳机一副' },
        { imgSrc: '4.png', text: '吉祥春节对联一套' },
        { imgSrc: '5.png', text: '便携式体重秤一台' },
        { imgSrc: '6.png', text: '大毫安充电宝一个' },
        { imgSrc: '7.png', text: '舒肤床上四件套一份' },
        { imgSrc: '8.png', text: '进口有机大米一袋' }
    ];

    // 转盘
    var $rotate = $('#rotate');
    var bRotate = false;
    var rotateTimeOut = function () {
        $rotate.rotate({
            angle: 0,
            animateTo: 2160,
            duration: 8000,
            callback: function () {
                alert('网络超时，请检查您的网络设置！');
            }
        });
    };


    var rotateFn = function (awards, angles, code) {
        bRotate = !bRotate;
        $rotate.stopRotate();
        $rotate.rotate({
            angle: 0,
            animateTo: angles + 1800,
            duration: 8000,
            callback: function () {
                createWinCj(myWin[code-1].imgSrc, myWin[code-1].text);  // 显示奖品
                bRotate = !bRotate;
            }
        });
    };

    var cjCode = '';
    var isAward = $('#isAward').val();
    var myAwar = $('#myAwar').val();


    $('#pointer').click(function () {
        if (bRotate) return;
        if(isAward == 0){
            createNoCj();
            return false;
        }
        if(!!myAwar) {
            createWinedCj();
            return false;
        }
        // 输入抽奖码
        importCjCode(function ($text, $btn) {
            $btn.click(function () {
                var text = $text.val();
                if(!text) {
                    layer.open({
                        content: '抽奖码不能为空！'
                        ,skin: 'msg'
                        ,time: 2
                    });
                    $text.focus();
                    return false;
                }
                if(!/^[0-9a-zA-Z]+$/.test(text)) {
                    layer.open({
                        content: '抽奖码格式不正确！'
                        ,skin: 'msg'
                        ,time: 2
                    });
                    $text.focus();
                    return false;
                }
                $.ajax({
                    type: 'POST',
                    url: '/rest/checkAwardCode/'+ text,
                    dataType: 'json',
                    success: function (result) {
                        if(!result || result == 0){
                            layer.open({
                                content: '抽奖码无效！'
                                ,skin: 'msg'
                                ,time: 2
                            });
                            return false;
                        }
                        // 抽奖码有效
                        removeCjCode(); // 移除抽奖码弹框
                        cjCode = text;
                        if(result.awardNameCode) {
                            rotateCj(result);
                        }
                    }
                });
            });
        });
        return false;
    });

    // 转动抽奖
    function rotateCj(params) {
        var code = params.awardNameCode;
        switch (parseInt(code)) {
            case 0:
                rotateFn(0, 315, code);
                break;
            case 1:
                rotateFn(1, 45, code);
                break;
            case 2:
                rotateFn(2, 90, code);
                break;
            case 3:
                rotateFn(3, 135, code);
                break;
            case 4:
                rotateFn(4, 180, code);
                break;
            case 5:
                rotateFn(5, 225, code);
                break;
            case 6:
                rotateFn(6, 270, code);
                break;
            case 7:
                rotateFn(7, 315, code);
                break;
        }
    }

    // 弹框
    var Layer = {
        templateBefore: function () {
            var html = '<div class="layer">';
            html += '<div class="shade"></div>';
            html += '<div class="layer-main">';
            html += '<div class="layer-section">';
            html += '<div class="layer-wrap">';
            html += '<div class="layer-content">';
            return html;
        },
        templateAfter: function(params) {
            var html = '</div>';
            if(params && params.btn) {
                html += Layer.btn();
            }
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            return html;
        },
        btn: function () {
            return  '<a href="javascript:;" class="layer-close-btn close-btn"></a>';
        },
        closeEvent: function () {
            $(document).on('click', '.layer .close-btn', function () {
                $(this).closest('.layer').remove();
            });
        },
        closeAll: function () {
            $('.layer').remove();
        }
    };

    // 中奖提示
    function createWinCj(img, text) {
        var winPopHtml = Layer.templateBefore();
        winPopHtml += '<div class="pop-win">';
        winPopHtml += '<div class="prize-pic"><img src="../../assets/h5cj/img/my/'+ img +'" alt=""></div>';
        winPopHtml += '<div class="prize-text">'+ text +'</div>';
        winPopHtml += '<a href="/rest/infolist?id='+ id +'&cjCode='+ cjCode +'" class="pop-win-btn"></a>';
        winPopHtml += '</div>';
        winPopHtml += Layer.templateAfter({ btn: true });
        $('body').append(winPopHtml);
        Layer.closeEvent();
    }


    // 无抽奖资格提示
    function createNoCj() {
        var noWinPopHtml = Layer.templateBefore();
        noWinPopHtml += '<div class="pop-noWin"><a href="#" class="pop-noWin-btn"></a></div>';
        noWinPopHtml += Layer.templateAfter({ btn: true });
        $('body').append(noWinPopHtml);
        Layer.closeEvent();
    }

    // 已中奖提示
    function createWinedCj() {
        var winedPopHtml = Layer.templateBefore();
        winedPopHtml += '<div class="pop-wined"><a href="javascript:;" class="pop-wined-btn"></a></div>';
        winedPopHtml += Layer.templateAfter({ btn: true });
        $('body').append(winedPopHtml);
        Layer.closeEvent();
    }

    // 输入抽奖码
    function importCjCode(backFn) {
        var codePopHtml = Layer.templateBefore();
        codePopHtml += '<div class="pop-exchange">';
        codePopHtml += '<div class="input-box">';
        codePopHtml += '<input type="text" id="exchangeText" placeholder="请输入抽奖码"/>';
        codePopHtml += '</div>';
        codePopHtml += '<div class="btn-box"><a href="javascript:;" class="btn" id="exchangeBtn"></a></div>';
        codePopHtml += '</div>';
        codePopHtml += Layer.templateAfter();
        $('body').append(codePopHtml);
        backFn && backFn($('#exchangeText'), $('#exchangeBtn'));
    }

    // 移除抽奖码弹框
    function removeCjCode() {
        Layer.closeAll();
    }

});