define(["vue","vue-router","router/add-member","router/manager-transaction","router/member-list"
        ,"router/set-inner-disc-account","service/member-session","router/member","dict","router/manager-transactions","bootstrap"],
    function (Vue,VueRouter,addMemberRouter,managerTransactionRouter,memberListRouter,
              setInnerDiscAccountRouter,memberSessionService,memberRouter,
              dict,managerTransactionsRouter) {
    Vue.use(VueRouter)

    var routers = [
        {
            path:"/member-list",component:memberListRouter
        },
        {
            path:"/set-inner-disc-account",component:setInnerDiscAccountRouter
        },{
            path:"/manager-transactions",component:managerTransactionsRouter
        }, {
            path:"/manager-transaction",component:managerTransactionRouter
        },{
            path:"/add-member",component:addMemberRouter
        },{
            path:"/member",component:memberRouter

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
            memberSessionService.findOne().then(function (value) {
                vue.token = value.data.token;
                vue.profile = value.data;
            }).catch(function (reason) {
                location.href = "/login.html";
            })
        },methods:{
            quit:function () {
                $.cookie("token",null);
                location.reload();
            }

        }
    }).$mount("#app")



})