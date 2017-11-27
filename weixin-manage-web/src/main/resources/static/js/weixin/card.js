$(function () {
    showCards();
});
//为素材table添加数据
function showCards() {
    $.ajax({
        url:'/card/card-list',
        data:{
            count:10,
            offset:0
        },
        success:function (data) {
            var materials = data.data;
            $('#card-list').html(cardHtml(materials))
        }
    })
}
//将后台传入的数据转换成html
function cardHtml(data) {
    if(data==null || data==""){
        return '<tr><td colspan="8">暂无数据！</td></tr>';
    }
    var htmlData= '';
    $.each(data,function (k, v) {
        htmlData+='<tr>'
            +'<td>'+v.id+'</td>'
            +'<td>'+v.cardId+'</td>'
            +'<td>'+v.ticket+'</td>'
            +'<td><a target="_blank" href="'+v.url+'">'+v.url+'</a></td>'
            +'<td><a target="_blank" href="'+v.showQrcodeUrl+'">'+v.showQrcodeUrl+'</a></td>'
            +'<td>'+v.createdTime+'</td>'
            +'<td>'+v.updatedTime+'</td>'
            +'<td><a href="javascript:void(0)" class="card-showQrcode block-title">扫码生成</a>&emsp;<a href="javascript:void(0)" class="card-del block-title">删除</a></td>';
        htmlData+='</tr>'
    });
    return htmlData;
}
