$(document).on('change', '#card_type', function () {
    var val = $(this).val();
    var card_type_extend = $('.card_type_extend');
    if (val == 'GROUPON') {
        card_type_extend.html('<div class="form-group">\n' +
            '            <label for="deal_detail" class="col-sm-2 control-label">订单详情</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="deal_detail" id="deal_detail" placeholder="填写团购套餐的详情"/>\n' +
            '            </div>\n' +
            '        </div>')
    }
    if (val == 'CASH') {
        card_type_extend.html('<div class="form-group">\n' +
            '            <label for="least_cost" class="col-sm-2 control-label">现金券最低消费门槛</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="least_cost" id="least_cost" placeholder="最低消费门槛。"/>\n' +
            '            </div>\n' +
            '        </div>' +
            '<div class="form-group">\n' +
            '            <label for="reduce_cost" class="col-sm-2 control-label">抵扣金额</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="reduce_cost" id="reduce_cost" placeholder="抵扣金额。"/>\n' +
            '            </div>\n' +
            '        </div>')
    }
    if (val == 'DISCOUNT') {
        card_type_extend.html('<div class="form-group">\n' +
            '            <label for="discount" class="col-sm-2 control-label">折扣</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="discount" id="discount" placeholder="折扣 例如：30 表示：3折"/>\n' +
            '            </div>\n' +
            '        </div>')
    }
    if (val == 'GIFT') {
        card_type_extend.html('<div class="form-group">\n' +
            '            <label for="gift" class="col-sm-2 control-label">礼品详情</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="gift" id="gift" placeholder="商户名字,字数上限为12个汉字。"/>\n' +
            '            </div>\n' +
            '        </div>')
    }
    if (val == 'GENERAL_COUPON') {
        card_type_extend.html('<div class="form-group">\n' +
            '            <label for="default_detail" class="col-sm-2 control-label">优惠详情</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="default_detail" id="default_detail" placeholder="商户名字,字数上限为12个汉字。"/>\n' +
            '            </div>\n' +
            '        </div>')
    }
});
$('#card-add-form').submit(function () {
    var params = $("#card-add-form").serializeArray();

    var card = {};
    var sub_card = {};
    var base_info = {};
    var sku = {};
    var date_info = {};
    var location_id_list = [];
    for (var item in params) {
        var name = params[item].name;
        var value = params[item].value;
        if (value != '') {
            if (name == 'card_type') {
                card[name] = value;
                card[value.toLowerCase()] = sub_card
            } else if (name == 'quantity') {
                sku[name] = value;
            } else if (name == 'fixed_begin_term' || name == 'end_timestamp'
                || name == 'begin_timestamp' || name == 'fixed_term'||name == 'type') {
                date_info[name] = value;
            } else if (name == 'location_id_list') {
                location_id_list = value.split(',');
                base_info['location_id_list'] = location_id_list;
            } else if (name == 'deal_detail' || name == 'gift' || name == 'default_detail'
                || name == 'least_cost' || name == 'reduce_cost' || name == 'discount') {
                sub_card[name] = value;
            } else if (name == 'use_all_locations') {
                if(value!='false'){
                    base_info[name] = value;
                }

            }else if(name == 'deal_detail' || name == 'gift'){
                base_info[name] = true
            } else {
                base_info[name] = value;
            }

        }
    }
    base_info['sku'] = sku;
    base_info['date_info'] = date_info;


    sub_card['base_info'] = base_info;
    var values = {};
    values['card'] = card;
    alert(JSON.stringify(values, null, 2));
//                var options = {
//                    // 规定把请求发送到那个URL
////                url: "https://api.weixin.qq.com/cgi-bin/media/uploadimg",
//                    url: "material/material-add",
//                    // 请求方式
//                    type: "post", //必须用post
//                    // 请求成功时执行的回调函数
//                    success: function(data) {
//                        // 图片显示地址
//                        alert(JSON.stringify(data));
//                    }
//                };
//                $("#card-add-form").ajaxSubmit(options);
//                $("#card-add-form").resetForm();
    return false
});
$(document).on('change', '.dataType', function () {
    var val = $(this).val();
    var dataType_sub = $(".dataType-sub");
    if (val == 'DATE_TYPE_FIX_TIME_RANGE') {
        dataType_sub.html('<!--卡券起用时间-->\n' +
            '        <div class="form-group">\n' +
            '            <label for="begin_timestamp" class="col-sm-2 control-label">卡券起用时间</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="begin_timestamp" id="begin_timestamp" placeholder="从1970年1月1日00:00:00至起用时间的秒数，最终需转换为字符串形态传入。例如：14300000"/>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '        <!--卡券结束时间-->\n' +
            '        <div class="form-group">\n' +
            '            <label for="end_timestamp" class="col-sm-2 control-label">结束时间</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="end_timestamp" id="end_timestamp" placeholder="建议设置为截止日期的23:59:59过期。例如：15300000"/>\n' +
            '            </div>\n' +
            '        </div>')
    }
    if (val == 'DATE_TYPE_FIX_TERM') {
        dataType_sub.html('<!--卡券生效时间-->\n' +
            '        <div class="form-group">\n' +
            '            <label for="fixed_begin_term" class="col-sm-2 control-label">卡券生效时间</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="fixed_begin_term" id="fixed_begin_term" placeholder="自领取后多少天开始生效，领取后当天生效填写0。（单位为天）"/>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '        <!--卡券有效期-->\n' +
            '        <div class="form-group">\n' +
            '            <label for="fixed_term" class="col-sm-2 control-label">卡券有效期(天)</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="fixed_term" id="fixed_term" placeholder="自领取后多少天内有效，不支持填写0。"/>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '        <!--卡券卡券统一过期时间-->\n' +
            '        <div class="form-group">\n' +
            '            <label for="end_timestamp" class="col-sm-2 control-label">卡券统一过期时间</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="end_timestamp" id="end_timestamp" placeholder="卡券统一过期时间，建议设置为截止日期的23:59:59过期，设置了fixed_term卡券，当时间达到end_timestamp时卡券统一过期"/>\n' +
            '            </div>\n' +
            '        </div>')
    }
});
$(document).on('change', '.all-locations', function () {
    var val = $(this).val();
    if (val == 'false') {
        $('.location_id_list').html('<!--门店位置-->\n' +
            '        <div class="form-group">\n' +
            '            <label for="location_id_list" class="col-sm-2 control-label">门店位置</label>\n' +
            '            <div class="col-sm-10">\n' +
            '                <input type="text" class="form-control" name="location_id_list" id="location_id_list" placeholder="具备线下门店的商户为必填。多个门店用,隔开例如：123,5623"/>\n' +
            '            </div>\n' +
            '        </div>');
    }
});
