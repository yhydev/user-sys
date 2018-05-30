define(["service/transaction","component/router","filter/app-dict"],function (transactionService) {
    return {
        //template:`aaa`,
        template:`
        
        <user-template>
        <h1 slot="title">交易列表</h1>
        <div slot="content">
            <table  class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>
                        用户信息
                    </th>
                    
                    <th>
                        交易信息
                    </th>
                    
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in data.results ">
                    
                    <td>
                         <div><label>账户信息：</label><span>{{item.account}}</span></div>
                    </td>
                    <td>
                    <div><label>交易类型：</label><span>{{item.type | app_dict}}</span></div>
                    <div><label>交易状态：</label><span>{{item.status | app_dict}}</span></div>
                        <div><label>交易金额(元)：</label><span>{{item.money}}</span></div>
                        <div><label>交易时间：</label><span>{{item.createDate}}</span></div>
                        <div><label>审核时间：</label><span>{{item.updateDate}}</span></div>

                        <div v-if="transactionStatus.wait_check == item.status">
                        <label>审核操作：</label><span>
                        
                        <a href="Javascript:;" v-on:click="success(item.transactionId)">通过</a>&nbsp;-&nbsp;<a href="Javascript:;" v-on:click="failed(item.transactionId)">拒绝</a>
                        
                        </span>
                        </div>
                        
                    </td>
                   
                </tr>
                </tbody>
            </table>
            <div class="router-notify" v-if="data.total == 0">暂无交易记录</div>
            <div v-if="data.total != 0" class="col-md-4 col-md-offset-8 pull-right">
                <pageable-template :total="data.total" :size="data.size" :page="data.page"></pageable-template>
            </div>
        </div>

    </user-template>
        
        `,
        props:["token","transactionStatus"],
        data:function () {
            return {
                data:{}
            }
        },methods:{
            load:function () {

                var vue = this;
                var query = JSON.parse(JSON.stringify(this.$route.query))
                transactionService.list(query).then(function (value) {
                    vue.data = value.data;
                }).catch(function (reason) {
                    alert(reason.message)
                })
            },
            success:function (transactionId) {
              this.update(transactionId,true,"确定通过此笔交易吗？");
            },failed:function (transactionId) {
              this.update(transactionId,false,"确定拒绝此交易吗");
            },
            update:function (transactionId,status,msg) {

                var isEnter = confirm(msg)

                if(isEnter){
                    var params = {transactionId:transactionId,success:status};
                    var vue = this;

                    transactionService.update(params).then(function (value) {
                        vue.$router.go(0);
                    }).catch(function (reason) {
                        alert(reason.message)
                    })
                }


            }
        },watch:{
            "$route":function () {
                this.load();
            }
        },mounted:function () {
            this.load()
        }
    };
});