define(["vue","jquery","service/member-session","service/member","component/upload","component/router",
    "jquery-form","validator-utils"],function (Vue,$,memberSessionService,memberService) {

    return {
        template:"#deposit-template",
        data:function () {
            return {
                memberSession:{},
                member:{}
            }},
        mounted:function () {
            var vue = this;
            memberSessionService.memberSession.then(function (res) {

                vue.memberSession = res.data;
            })

            memberService.getFrontMember(function (res) {
                vue.member = res.data;
            })
        }
    }



    
});