(function(){



    $.appToken = "123";

    $.autoLogin = function(has,nohas){
        var token = $.cookie("token")
        if(token){
            $.tokenLogin(token,has,nohas)
        }
    }


    $.tokenLogin = function(token,has,nohas){

        var data = {token:token};
        var param = $.param(data);


        $.get("/memberSession?"+param,function (res) {
            if(res.success){
                $.cookie("token",res.data.token,{expried: 7 })
                $.userSessioon = res.data;
                if(has){has()}
            }else{
                if(nohas){nohas()}
            }
        })
    }



})()