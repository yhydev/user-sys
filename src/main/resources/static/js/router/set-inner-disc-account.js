define(["jquery","component/widget","jquery-validator","jquery-form"],function ($,widgetComponent) {

    function validate(vue) {
        $("#set-account-form").validate({
            debug:true,
            rules:{
                token:{
                    required:true
                },innerDiscAccount:{
                    required:true
                }
            },messages:{
                token:{
                    required:"请登陆账户"
                },innerDiscAccount:{
                    required:"请输入内盘期货账户"
                }
            },submitHandler:function (form) {
                console.log(form)
                $(form).ajaxSubmit(function (res) {
                    if(res.success){
                        vue.c_alert({message:"开户成功!请电话或短信通知客户。",title:"开户成功"})
                            .on("hide.bs.modal",function () {
                            vue.$router.back()
                        });
                    }else{
                        vue.c_alert({message:res.message,title:"开户失败"})
                    }
                })
            }
        })

    }





    return {
        mixins:[widgetComponent],
        template:`
          <router-template>
                <h3 slot="title">设置内盘账户</h3>
                <div  class="col-md-4 col-md-offset-4" slot="content">
                    <modal-template :title="title" :message="message"></modal-template>
                
                    <form  role="form" id="set-account-form" action="/member/setInnerDiscAccount">
                        <div class="form-group">
                             <label>内盘期货账户</label><input type="text" name="innerDiscAccount" class="form-control"  />
                             <input v-bind:value="token" name="token" type="hidden"/>
                             <input v-bind:value="$route.query.memberId" name="memberId" type="hidden">
                        </div>
                        <button type="submit" class="btn btn-default">确定</button>
                    </form>
                </div>
            </router-template>
        
        `,props:["token"],
        mounted:function () {

            validate(this)
        },updated:function () {
            validate(this)
        }
    }
})