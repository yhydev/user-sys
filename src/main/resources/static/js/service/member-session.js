define(["Promise","jquery","jquery-cookie"],function (Promise,$) {


    this.memberSession = new Promise(function (resolove,reject) {
        var token = $.cookie("token");
        var param = $.param({token:token})
        var defaultFailed = null;

        $.ajax({
            url:"/memberSession?"+param,
            success:function (res) {
                if(res.success){
                    resolove(res)
                }else{
                    location.href="/login.html"
                }
            },
            error:reject
        })


    });



    this.delete = function (success,error) {

    }

    return this;
})





