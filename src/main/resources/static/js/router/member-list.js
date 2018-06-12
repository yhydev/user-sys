define(["service/member","component/router"],function (memberService) {
    return {
        template:`
        <router-template>
    <h3 slot="title">用户列表</h3>
    <div slot="content">
    
                <div class="row col-md-12">
                    <div>
                       <label>用户类型：</label>
                       <a href="javascript:;" v-bind:class="{active:!$route.query.type}" v-on:click="query({'type':'all'})">全部</a>
                       <a href="javascript:;" v-bind:class="{active:$route.query.type == v }" v-for="v in type" v-on:click="query({'type':v})">{{v|app_dict}}</a>
                    </div>

                    <div>
                       <label>开户状态：</label>
                       <a href="javascript:;" v-bind:class="{active:!$route.query.openAccountStatus}" v-on:click="query({'openAccountStatus':'all'})">全部</a>
                       <a href="javascript:;" v-bind:class="{active:$route.query.openAccountStatus == v }" v-for="v in openAccountStatus" v-on:click="query({'openAccountStatus':v})">{{v|app_dict}}</a>
                    </div>
                                      
                </div>
        <table  class="table table-striped table-hover">
            <thead>
            <tr>
                <th>
                    基本信息
                </th>
                <th>
                    操作
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
                    <div>
                            <label>用户类型:&nbsp;</label><span>{{item.type|app_dict}}</span>
                    </div>
                    <div>
                            <label>开户状态:&nbsp;</label><span>{{item.openAccountStatus|app_dict}}</span>
                    </div>
                    
                    
                </td>
                
         
                <td >
                 <span><router-link  :to="{path:'/member',query:{memberId:item.memberId}}" >查看</router-link></span>
                   

         
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
                data:{},
                type:["user","proxy","manager"],
                openAccountStatus:["notApplyFor","applyFor","openAccount","reject"],
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
                var param = JSON.stringify(this.$route.query)
                param = JSON.parse(param)

                for(name in this.$route.params){
                    param[name] = this.$route.params[name];
                }
                memberService.findList(param).then(function (value) {
                    vue.data = value.data;
                }).catch(function (reason) {
                    alert(reason.message)
                })
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