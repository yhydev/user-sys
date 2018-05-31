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
        template:`
        
    <user-template>

        <div slot="title">
            <h3>线下内盘入金<small class="description">&nbsp;&nbsp;入金前，请确认您已转账</small></h3>
        </div>

            <div slot="content">

                <div v-if="profile.openAccountStatus != openAccountStatus.openAccount"  >
                    <no-open-account-template></no-open-account-template>
                </div>

                <div v-if="profile.openAccountStatus == openAccountStatus.openAccount" class="col-md-4 col-md-offset-4">
                    <modal-template :message="message">
                    </modal-template>
                    <form role="form" id="offlinePay-form" action="/transaction/offlineDeposit">
                        <div class="form-group">
                            <label>收款银行</label>

                            <label v-bind:for="'_bank_'+index" v-for="(bank,index) in banks"
                                   v-bind:class="{active:bank.choice}" class="bank-box" v-on:click="choice(index)">
                                <input style="display: none" v-bind:value="bank.receiveAccountId" type="radio" name="receiveAccountId" v-bind:id="'_bank_'+index">
                                <div>
                                    <strong>{{bank.username}}</strong>
                                    <small>{{bank.bankNo}}</small>
                                </div>
                                <div>
                                    <strong>{{bank.bankName}}</strong>
                                </div>
                            </label>

                            <label id="receiveAccountId-error" class="error" for="receiveAccountId"></label>


                        </div>

                        <div class="form-group">
                            <label>入金金额(元)</label><input name="money" type="text" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label>转账银行卡号</label><input name="payAccount" type="text" class="form-control" />
                            <input name="token" type="hidden" v-model="token">
                        </div>


                        <button type="submit" class="btn btn-default">提交</button>
                    </form>

                </div>

            </div>


        </div>


    </user-template>
        `,
        data:function () {
            return {
                banks:[],
                activeIndex:null
            }},props:["token","profile","openAccountStatus"],
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