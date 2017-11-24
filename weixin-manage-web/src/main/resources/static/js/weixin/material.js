$(function () {
    $.ajax({
        url:'/material/material-list',
        data:{
            access_token:'3EHqitbmzCoxn9_0MMrpyxnVO99t-wqr02dkGeDEzNyViESA99HT2ouHoNJ9jGcOyAyK7_YWpFpiExoJYxu9vPRmR7lacFrqMJ23o0CMdun3oV57p8-u2O2NP0xleSFMKDUcABAZSJ',
            count:10,
            offset:0,
            type:"image"
        },
        success:function (data) {
            alert(JSON.stringify(data))
        }
    })

});