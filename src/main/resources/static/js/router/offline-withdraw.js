define(["vue","jquery","component/router","jquery-form","validator-utils"],function (Vue,$) {

    function validator() {
        console.log($("#offlineWithdraw-form"))
        $("#offlineWithdraw-form").validate({
            rules:{
                money:{
                    required:true,
                    range:[1,99999999]
                },token:{
                    required:true
                }
            },messages:{
                money:{
                    required:"请输入金额",
                    range:"请输入1 - 99999999的金额",
                },token:{
                    required:"请登陆"
                }
            },submitHandler:function (form) {
                $(form).ajaxSubmit(function (res) {
                    if(res.success){
                        alert("提交成功，请注意您的手机短信及电话，工作人员会在一个工作日内为您完成出金。")
                    }else{
                        alert(res.message)
                    }
                })
            }
        })
    }
    return {
        props:["profile","token"],
        template:"#offline-withdraw-template",
        mounted:function () {
            validator();
        },
        updated:function () {
            validator();
        }
    }
});