define(["service/member","filter/app-dict"],function (memberService) {

    var template = `<router-template>
    <h3 slot="title">用户信息</h3>
    <div slot="content">
        
                        <div class="row">
                       
                        <div class="col-md-6">
                            <label>账号：</label>
                            <span>{{data.account}}
                             <a v-if="data.openAccountStatus == dict.openAccountStatus.applyForAccount" href="javascript:;" v-on:click="rejectOpenAccount(data.memberId)">拒绝开户</a>
                            <router-link  v-if="data.openAccountStatus == dict.openAccountStatus.applyForAccount"  :to="{path:'/set-inner-disc-account',query:{memberId:data.memberId}}">为他开户</router-link>
                            <router-link  v-if="data.type == dict.memberType.proxy"  :to="{path:'/set-inner-disc-account',query:{memberId:data.memberId}}">子用户</router-link></span>
                        </div>
                        
                        <div class="col-md-6">
                            <label>注册日期：</label>
                            <span>{{data.createDate}}</span>
                        </div>
                        
                        
                        <div class="col-md-6">
                            <label>用户类型：</label>
                            <span>{{data.type|app_dict}}</span>
                        </div>
                        
                        
                        <div class="col-md-6">
                            <label>交易状态：</label>
                            <span>{{data.openAccountStatus|app_dict}}</span>
                        </div>

                        
                        </div>
                        <div v-clock  class="row">
                           <hr>
                        <div class="col-md-6">
                            <label>姓名：</label>
                            <span>{{data.name}}</span>
                        </div>
                        
                         <div class="col-md-6">
                            <label>身份证：</label>
                            <span>{{data.idCard}}</span>
                        </div>
                  
                                 <div class="col-md-6">
                            <label>银行卡：</label>
                            <span>{{data.bankCard}}</span>
                        </div>
                  
                  
                        <div class="col-md-6">
                            <label>银行预留手机：</label>
                            <span>{{data.phoneNumber}}</span>
                        </div>
                        
                        
                        <div class="col-md-6">
                            <label>申请日期：</label>
                            <span>{{data.applyForOpenAccountDate}}</span>
                        </div>
                        
                        <div class="col-md-6">
                            <label>设置内容日期：</label>
                            <span>{{data.setInnerDiscDate}}</span>
                        </div>
                        

                        
                        
                        <div class="col-md-6">
                            <label>身份证照片：</label>
                            <span> <a target="_blank" :href="data.idCardFrontUrl">正面照</a>&nbsp;
                            <a target="_blank" :href="data.idCardBehindUrl">反面照</a> 
                            </span>
                        </div>
                                     
                        </div>
                        
                    
        </div>
</router-template>`;




        return {
            template:template,
            props:["dict"]
            ,data:function () {
                return {
                    data:{

                    }}
            },mounted:function () {
                var vue = this;
                memberService.findOne(this.$route.query).then(function (value) {
                    vue.data = value.data;
                }).catch(function (reason) {
                    alert(reason.message)
                })
            },methods:{
                rejectOpenAccount:function (memberId) {
                    var isEnter = confirm("确定要拒绝此用户的开户申请吗？")
                    var vue = this;
                    if(isEnter){
                        memberService.rejectOpenAccount({memberId:memberId}).then(function (value) {
                            vue.$router.go(0)
                        }).catch(function (reason) {
                            alert(reason.message)
                        })
                    }
                }
            }
        };



});