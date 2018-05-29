





define(["Promise","jquery","service/member-session"],function (Promise,$,memberSessionService) {

    /**
     * get current login member info
     */
    var getFrontMember = new Promise(function (resolve,reject) {
        //TODO 不使用缓存
            memberSessionService.memberSession.then(function (memberSession) {
                if(memberSession.success){
                    $.ajax({
                        data:$.param(memberSession.data),
                        url:"/member/getFrontMember",
                        success:resolve,
                        error:reject
                    })
                }else{
                    reject(memberSession)
                }
            }).catch(function (reason) {
                reject(reason)
            })
    });

    var getMemberList = function (data) {
        return new Promise(function (resolve,reject) {
            $.ajax({
                data:data,
                url:"/member/getMemberList",
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
        getFrontMember:getFrontMember,
        getMemberList:getMemberList
    };
})