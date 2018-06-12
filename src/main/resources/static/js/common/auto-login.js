define(["jquery","service/member-session"],function ($,sessionService) {
    var pages ={
        manager:"/manager_index.html",
        user:"/index.html"
    }
console.log(sessionService)
    sessionService.findOne({token:$.cookie("token")}).then(function (value) {
        var page = pages[value.data.type];
        if(page){
            location.href = page;
        }
    }).catch(function (reason) {  })
})