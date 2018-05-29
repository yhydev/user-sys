define(["vue","jquery","component/widget","service/receive-account","component/upload","component/router",
    "jquery-form","validator-utils"],function (Vue,$,widgetComponent,receiveAccountService) {

    function validator(vue) {
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
                        vue.c_alert(
                            {
                                message:"入金申请提交成功，我们会在2个工作日内为您处理出金，请留意您的电话和短信。"
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
    
    
    
    return {
        mixins:[widgetComponent],
        template:"#offline-deposit-template",
        data:function () {
            return {
                banks:[],
                activeIndex:null
            }},props:["token","profile"],
        methods:{
            choice:function (index) {
                if(this.activeIndex != null){
                    this.banks[this.activeIndex].choice = false;
                }
                this.activeIndex = index;
                this.banks[index].choice = true;
            }
        },
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


        },updated:function () {
            validator(this);
        }
    }



    
});