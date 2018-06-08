define(["service/transaction",
    "component/router","filter/app-dict"],function (transactionService) {

    return {
        /*props:["profile"],*/
        template:"#transaction-list-template",
     //   template:`<div>{{token}}</div>`,
        props:["token"],
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

              transactionService.findList(this.$route.query).then(function (value) {
                  if(value.success){
                      vue.data = value.data;
                  }
              }).catch(function (reason) {
                  alert(reason.message)
              })
          }  
        },mounted:function () {
            this.load()
        }
    }
});