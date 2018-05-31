define(["service/transaction","component/router","filter/app-dict"],
    function (transactionService) {

 /*       Vue.use(VueRouter)
    var routers = [

    ];

    var vueRouter = new VueRouter({
        routes:routers
    });*/


    return {
   //     router:vueRouter,
        template:`
        
        <user-template>
        <h1 slot="title">交易列表</h1>
        <div slot="content">
                <div class="row col-md-12" role="form">
                    <div>
                       <label>交易类型：</label>
                       <a href="javascript:;" v-bind:class="{active:!$route.query.type}" v-on:click="query({'type':'all'})">全部</a>
                       <a href="javascript:;" v-bind:class="{active:$route.query.type == v }" v-for="v in type" v-on:click="query({'type':v})">{{v|app_dict}}</a>
                    </div>
                    
                    <div>
                       <label>交易状态：</label>
                       <a href="javascript:;" v-bind:class="{active:!$route.query.status}" v-on:click="query({'status':'all'})">全部</a>
                                              <a href="javascript:;" v-bind:class="{active:$route.query.status == v }" v-for="v in status" v-on:click="query({'status':v})">{{v|app_dict}}</a>
                    </div>
                  
                </div>
            <table  class="table table-striped table-hover">
                <thead>
                <tr><!--
                    <th>
                        用户信息
                    </th>
                    -->
                    <th>
                        交易信息
                    </th>
                    <th>
                        时间信息
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in data.results ">
                    <td>
                    <div><label>交易类型：</label><span>{{item.type | app_dict}}</span></div>
                    <div><label>交易状态：</label><span>{{item.status | app_dict}}</span></div>
                        <div><label>交易金额(元)：</label><span>{{item.money}}</span></div>
                        <div><label>交易详情：</label><span>
                        <router-link v-bind:to="'/manager-transaction?transactionId='+item.transactionId">交易详情</router-link>
</span></div>
                        <div v-if="transactionStatus.wait_check == item.status">
                        <label>审核操作：</label><span>
                        
                        <a href="Javascript:;" v-on:click="success(item.transactionId)">通过</a>&nbsp;-&nbsp;<a href="Javascript:;" v-on:click="failed(item.transactionId)">拒绝</a>
                        
                        </span>
                        </div>
                        
                    </td>
                    <td>
                    <div><label>交易时间：</label><span>{{item.createDate}}</span></div>
                        <div><label>审核时间：</label><span>{{item.updateDate}}</span></div>
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
                data:{},
                status:["wait_check","success","failed"],
                type:["offline_deposit","offline_withdraw"]
            }
        },methods:{
            query:function (data) {
                var query = this.$route.query;
                for(name in query){
                    if(!data[name] ){
                        data[name] = query[name];
                    }

                    if(data[name]=='all'){
                        data[name]=null;
                    }
                }
                var query = JSON.parse(JSON.stringify(data))


                this.$router.push({path:this.$route.path,query:query});

            },
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
                console.log(111)
                this.load();
            }
        },mounted:function () {
            this.load()
        }
    };
});