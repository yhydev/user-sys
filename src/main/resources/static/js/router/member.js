define(function () {
    return {
        template:`
<router-template>
    <h3 slot="title">用户信息</h3>
    <div slot="content">
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
                    
</div>
</router-template>        
`
    };
});