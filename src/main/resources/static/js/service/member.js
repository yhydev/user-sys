define(["Promise","jquery","service/member-session"],function (Promise,$,memberSessionService) {

    this.rejectOpenAccount = function (data) {
        return new Promise(function (resolve,reject) {
                    $.ajax({
                        data:data,
                        url:"/member/rejectOpenAccount",
                        success:function (res) {
                            if(res.success){
                                resolve(res)
                            }else{
                                reject(res)
                            }
                        },
                        error:reject
                    })
        })
    }


    this.findList = function (data) {
        return new Promise(function (resolve,reject) {
                    $.ajax({
                    data:data,
                    url:"/member/findList",
                    success:function (res) {
                        if(res.success){
                            resolve(res)
                        }else{
                            reject(res)
                        }
                    },
                    error:reject
                })

            })
    }

    return {
        findList:findList,
        rejectOpenAccount:rejectOpenAccount
    };
})