define(["Promise","jquery"],function (Promise,$) {

    var rejectOpenAccount = function (data) {
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


    var findList = function (data) {
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

    var findOne = function (data) {
        return new Promise(function (resolove,reject) {
            $.ajax({
                data:data,
                url:"/member/findOne",
                success:function (res) {
                    if(res.success){
                        resolove(res)
                    }else{
                        reject(res)
                    }
                },
                error:reject
            })
        })
    };

    return {
        findList:findList,
        rejectOpenAccount:rejectOpenAccount,
        findOne:findOne
    };
})