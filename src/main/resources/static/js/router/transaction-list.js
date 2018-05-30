define(["vue","jquery","service/transaction","component/router","jquery-form","validator-utils","filter/app-dict"],function (Vue,$,transactionService) {

    return {
        /*props:["profile"],*/
        template:"#transaction-list-template",
        data:function () {
            return {
               data:{}
            }
        },watch: {
            "$route":function () {
                this.load()
            }
        },methods:{
          load:function () {
              var vue = this;
              transactionService.list(this.$route.query).then(function (value) {
                  if(value.success){
                      vue.data = value.data;
                  }
              })
          }  
        },mounted:function () {
            this.load()
        }
    }
});