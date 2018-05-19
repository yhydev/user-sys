define(["vue","jquery","service/member-session","service/member","service/receive-account","component/upload","component/router",
    "jquery-form","validator-utils"],function (Vue,$,memberSessionService,memberService,receiveAccountService) {

    function validator() {
        $("#offlinePay-form").validate({
            rules:{
                money:{
                    required:true,
                    digits:true,
                },payAccount:{
                    bankCard:true
                },receiveAccountId:{
                    required:true
                }, token:{
                    required:true
                }},messages:{
                money:{
                    required:"请输入转账的金额",
                    digits:"请输入合法金额",
                },payAccount:{
                    bankCard:"请填写正确的转账卡号"
                },receiveAccountId:{
                    required:"请选择收款的账户"
                }, token:{
                    required:"请登陆"
                }
            },submitHandler:function (form) {
                $(form).ajaxSubmit(function (res) {
                    if(res.success){
                        alert("提交成功，请注意您的手机短信及电话，工作人员会在一个工作日内为您完成入金。")
                    }else{
                        alert(res.message)
                    }
                })
            }
        })
    }

    return {
        props:["profile"],
        template:"#offline-withdraw-template"
    }
   /* return {
        template:"#offline-withdraw-template",
        data:function () {
            return {
                memberSession: {}
            }},
        mounted:function () {

            var vue = this;

            receiveAccountService.getAll(function (res) {
                if(res.success){
                    for(var i = 0;i<res.data.length;i++){
                        res.data[i].choice = false;
                    }
                    vue.banks = res.data;
                }else{
                    alert(res.message)
                }
            })



            memberSessionService.memberSession.then(function (res) {
                vue.memberSession = res.data;
            })

            memberService.getFrontMember(function (res) {
                vue.member = res.data;
            })
        },updated:function () {
            validator();

        }
    }

*/


});