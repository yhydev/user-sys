define(["vue","vue-router","router/member-list","router/set-inner-disc-account","service/member-session","bootstrap"],
    function (Vue,VueRouter,memberListRouter,setInnerDiscAccountRouter,memberSessionService) {
    Vue.use(VueRouter)

    var routers = [
        {
            path:"/member-list",component:memberListRouter
        },{
            path:"/setInnerDiscAccount",component:setInnerDiscAccountRouter
        }
        ];

    var vueRouter = new VueRouter({
        routes:routers
    });


    new Vue({
        router:vueRouter,
        data:{
            token:"",
            profile:{}
        },mounted:function () {

            var vue = this;

            memberSessionService.memberSession.then(function (value) {
                vue.token = value.data.token;
            }).catch(function (reason) {
                location.href = "/manager_login.html";
            })
        }
    }).$mount("#app")



})