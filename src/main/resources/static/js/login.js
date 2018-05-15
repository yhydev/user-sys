

require(["jquery","jquery-form","jquery-validator","session"],function(){


    $(document).ready(function () {

        function toIndex() {
            location.href = "index.html";
        }
        
        $.autoLogin(toIndex);

        // 在键盘按下并释放及提交后验证提交表单
        $("#login-box").validate({

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
                }
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
