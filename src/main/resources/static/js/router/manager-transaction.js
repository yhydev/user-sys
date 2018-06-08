define(["service/transaction","filter/app-dict"],function (transactionService) {
    return {
        template:`
            <router-template>
                    <h3 slot="title">交易详情</h3>
                    <div slot="content">
        
                        <div class="row">
                        <div class="col-md-6">
                            <label>姓名：</label>
                            <span>{{data.name}}</span>
                        </div>
                        
                        <div class="col-md-6">
                            <label>账号：</label>
                            <span>{{data.account}}</span>
                        </div>
                        
                        <div class="col-md-6">
                            <label>开户卡号：</label>
                            <span>{{data.memberBankCard}}</span>
                        </div>
                        
                        
                        <div class="col-md-6">
                            <label>期货内盘账号：</label>
                            <span>{{data.innerDiscAccount}}</span>
                        </div>
                    
                        </div>
                        <hr>
                    <div class="row">
                        <div class="col-md-6">
                            <label>交易类型：</label>
                            <span>{{data.type|app_dict}}</span>
                        </div>
                        
                        <div class="col-md-6">
                            <label>交易状态：</label>
                            <span>{{data.status|app_dict}}</span>
                        </div>
                        
                       
                                                <div class="col-md-6">
                            <label>交易金额(元)：</label>
                            <span>{{data.money}}</span>
                        </div>
                    </div>
                    
                    <hr>
                        <div class="row">

                     <div class="col-md-6">
                            <label>支付卡号：</label>
                            <span>{{data.payAccount}}</span>
                        </div>
                        
                        <div class="col-md-6">
                            <label>收款卡号：</label>
                            <span>{{data.receiveBankNo}}</span>
                        </div>
                        
                        <div class="col-md-6">
                            <label>收款银行：</label>
                            <span>{{data.receiveBankName}}</span>
                        </div>
                        
                        <div class="col-md-6">
                            <label>收款人(元)：</label>
                            <span>{{data.receiveUsername}}</span>
                        </div>
                        </div>
                        
		
		            </div>
                        
                    
                    
            </router-template>
        
        
        `,data:function () {
            return {
                data:{}
            }
        },mounted:function () {
            var vue = this;
            this.$route.query.token = vue.token;
            console.log(this.$route.query)
            transactionService.findList(this.$route.query).then(function (value) {
                vue.data = value.data
            }).catch(function (reason) {
                alert(reason.message)
            })
        }
    }
})