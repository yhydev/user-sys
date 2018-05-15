





define(["Promise","jquery","jquery-cookie"],function (Promise,$) {


    this.memberSession = new Promise(function (resolove,reject) {
        var token = $.cookie("token");
        var param = $.param({token:token})

        $.ajax({
            url:"/memberSession?"+param,
            success:resolove,
            error:reject
        })
    });

    
    
    this.delete = function (success,error) {
        
    }

    return this;
})