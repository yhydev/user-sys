define(["Promise","jquery"],function (Promise,$) {


    this.findOne = function (data) {
        return new Promise(function (resolove,reject) {
            $.ajax({
                data:data,
                url:"/memberSession/findOne",
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

    return this;
})





