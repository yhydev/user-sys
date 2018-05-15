require(["jquery","vue","vue-router","router/open-account","router/deposit","service/member-session","component/router","bootstrap"],
function($,Vue,VueRouter,openAccountRouter,depositRouter,memberSessionService) {

    Vue.use(VueRouter);


    var routes = [
        { path: '/openAccount', component: openAccountRouter },
        {path:"/deposit",component:depositRouter}
    ]

    var router = new VueRouter({
        routes:routes
    })



    var app = new Vue({
        router:router,
        data:function () {
            return {
                profile:{}
            }
        },mounted:function () {

                memberSessionService.memberSession.then(function (res) {
                    if(!res.success){
                        location.href = "/login.html"
                    }else{
                        app.profile = res.data;
                    }
                })

        }

    }).$mount("#app");


})

