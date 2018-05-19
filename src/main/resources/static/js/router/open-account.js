define(["vue","jquery","service/member-session","service/member","component/upload","component/router","jquery-form","validator-utils"],
    function (Vue,$,memberSessionService,memberService) {


    /*开户验证*/
    function initializeOpenAccountSubmitValidate() {

        $("#openAccount-form").validate({
            rules: {
                token: {required: true},
                name: {required: true, chineseName: true},
                idCard: {required: true, idCard: true},
                idCardFrontUrl: {required: true, url: true},
                idCardBehindUrl: {required: true, url: true},
                bankCard: {required: true, bankCard: true},
                phoneNumber: {required: true, phone: true}
            }, messages: {
                token: {required: "请登陆到您的账户"},
                name: {required: "请输入身份证姓名"},
                idCard: {required: "请输入身份证号"},
                idCardFrontUrl: {required: "请上传身份证正面",url:"请上传身份证正面"},
                idCardBehindUrl: {required: "请上传身份证背面",url:"请上传身份证背面"},
                bankCard: {required: "请输入银行卡号"},
                phoneNumber: {required: "请输入银行预留手机号"}
            }, submitHandler: function (form) {
                $(form).ajaxSubmit(function (res) {
                    if(res.success){
                        alert("您的申请已提交，我们会在1-2 个工作日为您开户，请注意您的手机电话，短信通知。")
                        location.reload();
                    }else{
                        alert(res.message)
                    }
                })
            }})

    }




        return {
            template:"#open-account-template",
                data:function () {
            return {
                memberSession:{},
                member:{}
            }
        }, beforeCreate:function(){
                var vue = this;
                memberSessionService.memberSession.then(function (res) {

                    vue.memberSession = res.data;
                })

                memberService.getFrontMember(function (res) {
                    vue.member = res.data;
                })

            },updated:function () {
                initializeOpenAccountSubmitValidate();
            }
        }



})