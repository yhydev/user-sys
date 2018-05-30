define(["vue","jquery","component/widget","component/router","jquery-form","validator-utils"],function (Vue,$,widgetComponent) {


    function validator(vue) {
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
                            vue.c_alert(
                                {
                                    message:"出金申请提交成功，我们会在2个工作日内为您处理出金，请留意您的电话和短信。"
                                }).on("hide.bs.modal",function () {
                                vue.$router.push({path:"/transaction-list"})
                            });
                        }else{
                            vue.c_alert({message:res.message});
                        }
                    })
                }
            })
    }







    var ret = {
        props:["profile","token","openAccountStatus"],
        template:"#offline-withdraw-template",
        mixins:[widgetComponent],
        mounted:function () {
            validator(this);
        },
        updated:function () {
            validator(this)
        }
    };

    return ret;
});