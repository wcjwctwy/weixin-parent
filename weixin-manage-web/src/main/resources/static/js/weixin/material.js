$(function () {
    showMaterials();
});
//为素材table添加数据
function showMaterials() {
    $.ajax({
        url:'/material/material-list',
        data:{
            count:10,
            offset:0
        },
        success:function (data) {
            var materials = data.data;
            $('#material-list').html(materialHtml(materials))
        }
    })
}
//将后台传入的数据转换成html
function materialHtml(data) {
    if(data==null || data==""){
        return '<tr><td colspan="9">暂无数据！</td></tr>';
    }
    var htmlData= '';
    $.each(data,function (k, v) {
        htmlData+='<tr>'
        +'<td>'+v.id+'</td>'
        +'<td>'+v.mediaId+'</td>'
        +'<td><a target="_blank" href="'+v.url+'">'+v.url+'</a></td>'
        +'<td>'+v.classify+'</td>'
        +'<td>'+v.type+'</td>'
        +'<td>'+v.desc+'</td>'
        +'<td>'+v.createdTime+'</td>'
        +'<td>'+v.updatedTime+'</td>'
        +'<td><a href="javascript:void(0)" class="material-edit block-title">编辑</a>&emsp;<a href="javascript:void(0)" class="material-del block-title">删除</a></td>';
        htmlData+='</tr>'
    });
    return htmlData;
}
