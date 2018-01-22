$(function () {

    // 判断用户
    $.ajax({
        type: 'GET',
        url: 'http://192.168.2.180:8080/rest/checkAwardPeople/15',
        dataType: 'JSONP',
        success: function (result) {
            console.log(result);
        }
    });


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


    var rotateFn = function (awards, angles, txt) {
        bRotate = !bRotate;
        $rotate.stopRotate();
        $rotate.rotate({
            angle: 0,
            animateTo: angles + 1800,
            duration: 8000,
            callback: function () {
                alert(txt);
                bRotate = !bRotate;
            }
        });
    };


    $('#pointer').click(function () {
        if (bRotate) return;
        var item = rnd(0, 7);
        switch (item) {
            case 0:
                rotateFn(0, 315, '0');
                break;
            case 1:
                rotateFn(1, 45, '1');
                break;
            case 2:
                rotateFn(2, 90, '2');
                break;
            case 3:
                rotateFn(3, 135, '3');
                break;
            case 4:
                rotateFn(4, 180, '4');
                break;
            case 5:
                rotateFn(5, 225, '5');
                break;
            case 6:
                rotateFn(6, 270, '6');
                break;
            case 7:
                rotateFn(7, 315, '7');
                break;
        }

        console.log(item);
    });

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
        templateAfter: function() {
            var html = '</div>';
            html += '<a href="javascript:;" class="layer-close-btn close-btn"></a>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            return html;
        },
        closeEvent: function () {
            $(document).on('click', '.layer .close-btn', function () {
                $(this).closest('.layer').remove();
            });
        }
    };

    // 中奖提示
    function createWinCj() {
        var winPopHtml = Layer.templateBefore();
        winPopHtml += '<div class="pop-win">';
        winPopHtml += '<div class="prize-pic"><img src="img/my/8.png" alt=""></div>';
        winPopHtml += '<div class="prize-text">进口有机大米一袋</div>';
        winPopHtml += '<a href="#" class="pop-win-btn"></a>';
        winPopHtml += '</div>';
        winPopHtml += Layer.templateAfter();
        $('body').append(winPopHtml);
        Layer.closeEvent();
    }

    // 无抽奖资格提示
    var noWinHint = false;
    if (noWinHint) {
        createNoCj();
        Layer.closeEvent();
    }

    function createNoCj() {
        var noWinPopHtml = Layer.templateBefore();
        noWinPopHtml += '<div class="pop-noWin"><a href="#" class="pop-noWin-btn"></a></div>';
        noWinPopHtml += Layer.templateAfter();
        $('body').append(noWinPopHtml);
    }

    // 已中奖提示
    var winedHint = false;
    if (winedHint) {
        createWinedCj();
        Layer.closeEvent();
    }
    function createWinedCj() {
        var winedPopHtml = Layer.templateBefore();
        winedPopHtml += '<div class="pop-wined"><a href="#" class="pop-wined-btn"></a></div>';
        winedPopHtml += Layer.templateAfter();
        $('body').append(winedPopHtml);
    }



});

function rnd(n, m) {
    return Math.floor(Math.random() * (m - n + 1) + n)
}