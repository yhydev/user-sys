require(["jquery","service/member","jquery-validator","jquery-form","jquery-cookie"],function ($,memberService) {

    memberService.getFrontMember.then(function (res) {
        if(res.data.type == "manager"){
            location.href = "/manager_index.html"
        }
    }).catch(function (reason) {
    })




    // 在键盘按下并释放及提交后验证提交表单
    $("#login-form").validate({
        debug:true,
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
                        $.cookie("token",res.data.token,{expired:7})
                        location.href = "/manager_index.html";
                    }else{
                        alert(res.message)
                    }
                },error:function(){

                }
            });
        }
    })

})