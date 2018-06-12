define(["jquery","component/widget","jquery-form","validator-utils","jquery-validator"],function ($,weiget) {

    
    function validator(vue) {
        $("#add-member-form").validate({
            rules:{
                account:{
                    required:true,
                    phone:true

                },
                password:{
                    required:true,
                    minlength:6
                },type:{
                    required:true
                }
            },messages:{
                account:{
                    required:"请输入手机号",
                    phone:"手机号格式不正确",

                },
                password:{
                    required:"请输入密码",
                    minlength:"密码最低为 6 位字符",
                },type:{
                    required:"请选择用户类型"
                }
            },submitHandler:function (form) {
                $(form).ajaxSubmit(function (res) {
                    var msg = res.message;
                    if(res.success){
                        vue.c_alert({message:"添加成功"}).on("hide.bs.modal",function () {
                            vue.$router.go(0)
                        });
                    }else{
                        vue.c_alert({message:res.message});
                    }
                })
            }
        })
    }
    
    

    return {
        template:`
<router-template>
    <h3 slot="title">添加代理</h3>            
    <div slot="content">
    <modal-template :message="message">
                </modal-template>
        <form id="add-member-form" class="col-md-4 col-md-offset-4 row" role="form" method="post" action="/member">
            
            <div class="form-group">
                    <label >手机号(登陆账号)</label><input type="text" name="account" class="form-control" />
                                        <input type="hidden" v-model="token" name="token"/>

            </div>
            
            <div class="form-group">
                    <label >密码</label><input type="password" name="password" class="form-control" />
            </div>
            
            <div class="form-group">
                    <label >用户类型</label>
                    <select multiple name="type" class="form-control">
                      <option value="user">普通用户</option>
                      <option value="proxy">代理用户</option>
                    </select>
            </div>
            
        
        <button class="btn btn-default">提交</button>
        </form>
    </div>
            
</router-template>
        `,
        mixins:[weiget],
    props:["token"],
    updated:function () {
        validator(this)
    },mounted:function () {
        validator(this)
    }

    }



})