define(["jquery","service/member-session","Promise"],function ($,memberSessionService,Promise) {

    function nop() {}

    function list(data) {

        var promise = new Promise(function (resole,reject) {
            memberSessionService.memberSession.then(function (value) {
                data.token = value.data.token;
                $.ajax({
                    success:resole,
                    error:reject,
                    data:$.param(data),
                    url:"/transaction/list"
                })
            })
        });



        return promise;

    }

    return {
        list:list
    };
})