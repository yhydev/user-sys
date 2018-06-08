define(["jquery","service/member-session","Promise"],function ($,memberSessionService,Promise) {

    function nop() {}

    
    function update(data) {
        return new Promise(function (resole,reject) {
            memberSessionService.memberSession.then(function (value) {
                data.token = value.data.token;
                $.ajax({
                    type:"put",
                    success:function (res) {
                        if(res.success){
                            resole(res)
                        }else{
                            reject(res)
                        }
                    },
                    error:reject,
                    data:$.param(data),
                    url:"/transaction"
                })
            })
        });
    }
    
    
    function list(data) {

        return new Promise(function (resole,reject) {
            $.ajax({
                success:function (res) {
                    if(res.success){
                        resole(res)
                    }else{
                        reject(res)
                    }
                },
                error:reject,
                data:$.param(data),
                url:"/transaction/findList"
            })
        });
    }

    return {
        findList:list,
        update:update
    };
})