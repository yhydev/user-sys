define(["vue","vue-router","router/manager-transaction","router/member-list","router/set-inner-disc-account","service/member-session","dict","router/manager-transactions","bootstrap"],
    function (Vue,VueRouter,managerTransactionRouter,memberListRouter,setInnerDiscAccountRouter,memberSessionService,dict,managerTransactionsRouter) {
    Vue.use(VueRouter)

    var routers = [
        {
            path:"/member-list",component:memberListRouter
        },
        {
            path:"/setInnerDiscAccount",component:setInnerDiscAccountRouter
        },{
            path:"/manager-transactions",component:managerTransactionsRouter
        }, {
            path:"/manager-transaction",component:managerTransactionRouter
        }
        ];

    var vueRouter = new VueRouter({
        routes:routers
    });


    new Vue({
        router:vueRouter,
        data:{
            token:"",
            profile:{},
            dict:dict
        },mounted:function () {

            var vue = this;
            memberSessionService.memberSession.then(function (value) {
                vue.token = value.data.token;
                vue.profile = value.data;
            }).catch(function (reason) {
                location.href = "/manager_login.html";
            })
        },methods:{
            quit:function () {
                $.cookie("token",null);
                location.reload();
            }

        }
    }).$mount("#app")



})