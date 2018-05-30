define(["service/member","component/router"],function (memberService) {
    return {
        template:`
        <router-template>
    <h3 slot="title">用户列表</h3>
    <div slot="content">
        <table  class="table table-striped table-hover">
            <thead>
            <tr>
                <th>
                    账户信息
                </th>
                <th>
                    开户信息
                </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in data.results">
            
                <td>
                    <div>
                        <label>账户:&nbsp;</label><span>{{item.account}}</span>
                    </div>

                    <div>
                        <label>注册日期:&nbsp;</label><span>{{item.createDate}}</span>
                    </div>
                    
                    <div v-if="item.openAccountStatus == openAccountStatus.applyFor">
                    <label>开户操作:&nbsp;</label>
                    <span>
                    <router-link v-bind:to="'/setInnerDiscAccount?memberId='+item.memberId" >开户</router-link>&nbsp;-&nbsp;<a href="javascript:void()" v-on:click="rejectOpenAccount(item.memberId)">拒绝</a></span>
                    
                    </div>  
                    
                    <div v-if="item.openAccountStatus == openAccountStatus.openAccount">
                        <label>内盘期货账户:&nbsp;</label><span>{{item.innerDiscAccount}}</span>  
                    </div>  

                </td>
                
                <td >
                    <div v-if="item.openAccountStatus == openAccountStatus.notApplyFor">
                        未申请开户
                    </div>
                    <div v-if="item.openAccountStatus != openAccountStatus.notApplyFor">
                    
                    <div>
                            <label>姓名:&nbsp;</label><span>{{item.name}}</span>
                        </div>
                    <div>
                        <label>身份证:&nbsp;</label><span>{{item.idCard}}</span>
                    </div>
                    <div>
                        <label>银行卡:&nbsp;</label><span>{{item.bankCard}}</span>
                    </div>
                    <div>
                        <label>预留手机号:&nbsp;</label><span>{{item.phoneNumber}}</span>
                    </div>
                    <div>
                        <label>申请日期:&nbsp;</label><span>{{item.applyForOpenAccountDate}}</span>
                    </div>
                     <div>
                        <label>身份证正面照:&nbsp;</label><span><a target="_blank" v-bind:href="item.idCardFrontUrl">点击查看</a></span>
                    </div>
                    <div>
                        <label>身份证反面照:&nbsp;</label><span><a target="_blank" v-bind:href="item.idCardBehindUrl">点击查看</a></span>
                    </div>     
                    <div>
                    </div>
                </td>
                
               
            </tr>
            </tbody>
        </table>
        <div class="router-notify" v-if="data.total == 0">暂无记录</div>
        <div v-if="data.total != 0" class="col-md-4 col-md-offset-8 pull-right">
            <pageable-template :total="data.total" :size="data.size" :page="data.page"></pageable-template>
        </div>
    </div>



</router-template>

        
        `,
        props:["token","openAccountStatus"],
        data:function () {
            return {
                data:{}
            }
        },methods:{
            load:function () {
                var vue = this;
                var param = JSON.stringify(this.$route.query)
                param = JSON.parse(param)
                param.token = this.token;

                for(name in this.$route.params){
                    param[name] = this.$route.params[name];
                }

                memberService.getMemberList(param).then(function (value) {
                    vue.data = value.data;
                }).catch(function (reason) {
                    alert(reason.message)
                })
            },rejectOpenAccount:function (memberId) {
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
        },watch:{

            "$route":function () {
                this.load()
            }
        },
        mounted:function () {
            this.load();
        }
    }
})