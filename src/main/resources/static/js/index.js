require(["jquery","vue","vue-router","router/open-account","router/deposit","router/offline-withdraw","service/member-session","service/member","component/router","bootstrap"],
function($,Vue,VueRouter,openAccountRouter,depositRouter,offlineWithdrawRouter,memberSessionService,memberService) {

    Vue.use(VueRouter);


    var routes = [
        { path: '/openAccount', component: openAccountRouter },
        {path:"/deposit",component:depositRouter},
        {path:"/offline-withdraw",component:offlineWithdrawRouter}
    ]

    var router = new VueRouter({
        routes:routes
    })



    var app = new Vue({
        router:router,
        data:function () {
            return {
                profile:{},
            }
        },mounted:function () {

                memberService.getFrontMember(function (res) {
                    if(!res.success){
                        location.href = "/login.html"
                    }else{
                        app.profile = res.data;
                    }
                });





        }

    }).$mount("#app");


})

