/*
require.config()
*/



require(["jquery","jquery-form","jquery-validator","image-code","phone-auth","session"],function($){

    $(document).ready(function () {

        function toIndex() {
            location.href = "index.html";
        }

        $.autoLogin(toIndex);


        $("#phoneAuthSent").phoneAuth(function(success,fail){
            var phone = $("[name=account]").val();
            var imageCode = $("[name=imageCode_]").val();
            var data = {phone:phone,imageCode:imageCode,type:"register"}
            $.ajax({
                url:"/identifyingCode/send?"+$.param(data),
                success:function (res) {
                    if(res.success){
                        success()
                    }else{
                        fail()
                        alert(res.message)
                    }
                }
            })
        });




        // 在键盘按下并释放及提交后验证提交表单
        $("#register").validate({
            rules: {
                account:{
                    required:true,
                    minlength:11,
                    maxlength:11,
                    digits:true
                },
                password: {
                    required: true,
                    minlength: 5
                },
                imageCode_: {
                    required: true
                },
                phoneCode: {
                    required: true,
                }
            },
            messages: {
                account:{
                    required:"请输入手机号",
                    minlength:"手机号格式不正确",
                    maxlength:"手机号格式不正确",
                    digits:"手机号格式不正确"
                },
                password: {
                    required: "请输入密码",
                    minlength: "密码长度不能小于 5 个字母"
                },
                imageCode_: {
                    required: "请输入图片验证码"
                },
                phoneCode: {required:"请输入手机验证码"}
            },
            submitHandler:function (form) {
                $(form).ajaxSubmit({
                    success:function(res){
                        if(res.success){
                            $.tokenLogin(res.data.token,toIndex);
                        }else{
                            alert(res.message);
                        }
                    },error:function(){

                    }
                });
            }
        })


    })


})
