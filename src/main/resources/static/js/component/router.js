require(["vue"],function (Vue) {
    Vue.component("router-template",{
        template:"#router-template"
    });
    
    
    
    Vue.component("no-open-account-template",{
        template:"#no-open-account-template"
    })
    Vue.component("user-template",{
        template:"#user-template"
    })


   /* function computePageTotal(size,total) {
        var y = (total % size > 0) ? 1 : 0;
        return y + total / size;
    }*/
    
    Vue.component("pageable-template",{
        template:"#pageable-template",
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