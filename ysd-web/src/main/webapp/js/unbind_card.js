/**
 * Created by admin on 2016/10/31.
 */

$(document).ready(function() {
    var tips_value = $(".input_tips > span").text();
    var name = $("input[title='name']").val();
    var id_num = $("input[title='id_num']").val();
    var bank = $("input[title='bank']").val();
    var card_num = $("input[title='card_num']").val();
    var bank_name = $("input[title='bank_name']").val();
    $("[type='submit']").click(function() {
        if(name == '') {
            tips_value = "请输入用户名！";
        }
        if(id_num == '') {
            tips_value = "请输入身份证号码！";
        }
        if(bank == '') {
            tips_value = "请选择银行！";
        }
        if (card_num == '') {
            tips_value = "请输入卡号！";
        }
        if(bank_name) {
            tips_value = "请输入开户行名称！";
        }
        $(".input_tips > span").text(tips_value);
    });
})