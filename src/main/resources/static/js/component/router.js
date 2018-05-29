require(["vue"],function (Vue) {
    Vue.component("router-template",{
        template:`
            <div v-clock  class="col-md-8 col-md-offset-2 column">

        <div class="row">
            <slot name="title"></slot>
        </div>
        <hr>
        <div class="row">
            <slot name="content"></slot>
        </div>
        <hr>
        <div class="row">
            <slot name="foot">
            </slot>
        </div>
    </div>

        `
    });

    Vue.component("no-open-account-template",{
        template:"#no-open-account-template"
    })
    Vue.component("user-template",{
        template:`<router-template>
        <slot slot="title" name="title">
        </slot>
        <slot slot="content" name="content">
        </slot>

        <p slot="foot">如果您在操作过程中遇到问题，请致电 7 * 24 小时电话：037993483728</p>
    </router-template>
`
    })


   /* function computePageTotal(size,total) {
        var y = (total % size > 0) ? 1 : 0;
        return y + total / size;
    }*/
    
    Vue.component("pageable-template",{
        template:`
        <ul class="pagination pull-right">

        <li >
            <a href="javascript:void(0)" v-on:click="go(page-1)">上一页</a>
        </li>
        <li>
            <a href="javascript:void(0)">{{page+1}}&nbsp;/&nbsp;{{pageTotal+1}}</a>
        </li>
        <li>
            <a href="javascript:void(0)" v-on:click="go(page+1)">下一页</a>
        </li>
    </ul>
        `,
        props:["page","size","total"],
        data:function () {
            return {pageTotal:0}
        },
        watch:{
            size:function () {
                this.pageTotal = Math.ceil(this.total / this.size) - 1;
                if(this.pageTotal < 0){
                    this.pageTotal = 0;
                }
            },total:function () {
                this.pageTotal = Math.ceil(this.total / this.size) - 1;
                if(this.pageTotal < 0){
                    this.pageTotal = 0;
                }
            }
        },
        methods:{
            go:function (index) {

                if(index < 0){
                    index = 0;
                }else if(this.pageTotal < index ){
                    index = this.pageTotal;
                }

                var query = JSON.parse(JSON.stringify(this.$route.query))
                query["page"] = index;
                this.$router.push({path:this.$route.path,query:query})
            }
        }
    })
    
    
    
})