define(["Promise","jquery","jquery-cookie"],function (Promise,$) {


    this.memberSession = new Promise(function (resolove,reject) {
        var token = $.cookie("token");
        var param = $.param({token:token})
        $.ajax({
            data:param,
            url:"/memberSession",
            success:function (res) {
                if(res.success){
                    resolove(res)
                }else{
                    reject(res)
                }
            },
            error:reject
        })
    });

    return this;
})





