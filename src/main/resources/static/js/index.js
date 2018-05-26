require(["vue","vue-router","router/open-account","router/deposit","router/offline-withdraw","router/transaction-list","service/member-session","service/member","bootstrap"],
function(Vue,VueRouter,openAccountRouter,depositRouter,offlineWithdrawRouter,transactionListRouter,memberSessionService,memberService) {

    Vue.use(VueRouter);




    var routes = [
        { path: '/openAccount', component: openAccountRouter },
        {path:"/deposit",component:depositRouter},
        {path:"/offline-withdraw",component:offlineWithdrawRouter},
        {path:"/transaction-list",component:transactionListRouter},
        {path:"/online-deposit",component:{template:"#online-deposit-template"}},
        {path:"/online-withdraw",component:{template:"#online-withdraw-template"}},
        {path: "/", redirect: "/transaction-list"}
    ]

    var router = new VueRouter({
        routes:routes
    })



    var app = new Vue({
        router:router,
        data:function () {
            return {
                profile:{},
                token:""
            }
        },methods:{
            quit:function () {
                $.cookie("token",null);
                location.reload()
            }
        },mounted:function () {


                memberSessionService.memberSession.then(function (value) {
                    app.token = value.data.token;
                })

                memberService.getFrontMember(function (res) {
                    app.profile = res.data;
                });
            }
    }).$mount("#app");


})

