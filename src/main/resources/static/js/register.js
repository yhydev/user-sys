/*
require.config()
*/



require(["jquery","common/auto-login","jquery-form","jquery-validator","jquery-cookie","phone-auth"],function($){

    $(document).ready(function () {
        var imgCodeEle =  $(".image-code-box .code-img");
        imgCodeEle.click(function(){
            this.src = this.src;
        })


        function getUrlParam(name)
        {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r!=null) return unescape(r[2]); return null; //返回参数值
        }


        $("[name=proxyId]").val(getUrlParam("proxyId"))



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
                        imgCodeEle.click();
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
                            $.cookie("token",res.data.token,{expired:7})
                            location.reload()
                        }else{
                            imgCodeEle.click();
                            alert(res.message);
                        }
                    },error:function(){
                        imgCodeEle.click();
                        alert("未知错误，请联系管理员")
                    }
                });
            }
        })


    })


})
