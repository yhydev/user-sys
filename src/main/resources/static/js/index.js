require(["vue","vue-router","router/open-account","router/offline-deposit"
        ,"router/offline-withdraw","router/transaction-list","service/member-session"
        ,"dict","bootstrap"],
function(Vue,VueRouter,openAccountRouter,offlineDepositRouter
         ,offlineWithdrawRouter,transactionListRouter,memberSessionService,dict) {

    Vue.use(VueRouter);




    var routes = [
        { path: '/openAccount', component: openAccountRouter },
        {path:"/offline-deposit",component:offlineDepositRouter},
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
                token:"",
                openAccountStatus:dict.openAccountStatus
            }
        },methods:{
            quit:function () {
                $.cookie("token",null);
                location.reload();
            }
        },mounted:function () {
                memberSessionService.findOne({token:$.cookie("token")}).then(function (value) {
                    app.token = value.data.token;
                    app.profile = value.data;
                }).catch(function (reason) {
                    location.href = "/login.html";
                });
            }
    }).$mount("#app");


})

