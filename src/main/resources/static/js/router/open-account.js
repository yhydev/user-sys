define(["vue","jquery","service/member-session","service/member","component/upload","component/router","jquery-form","validator-utils"],
    function (Vue,$,memberSessionService,memberService) {


    /*开户验证*/
    function initializeOpenAccountSubmitValidate() {

        $("#openAccount-form").validate({
            rules: {
                token: {required: true},
                name: {required: true, chineseName: true},
                idCard: {required: true, idCard: true},
                idCardFrontUrl: {required: true, url: true},
                idCardBehindUrl: {required: true, url: true},
                bankCard: {required: true, bankCard: true},
                phoneNumber: {required: true, phone: true}
            }, messages: {
                token: {required: "请登陆到您的账户"},
                name: {required: "请输入身份证姓名"},
                idCard: {required: "请输入身份证号"},
                idCardFrontUrl: {required: "请上传身份证正面",url:"请上传身份证正面"},
                idCardBehindUrl: {required: "请上传身份证背面",url:"请上传身份证背面"},
                bankCard: {required: "请输入银行卡号"},
                phoneNumber: {required: "请输入银行预留手机号"}
            }, submitHandler: function (form) {
                $(form).ajaxSubmit(function (res) {
                    if(res.success){
                        alert("您的申请已提交，我们会在1-2 个工作日为您开户，请注意您的手机电话，短信通知。")
                        location.reload();
                    }else{
                        alert(res.message)
                    }
                })
            }})

    }




        return {
            template:`



                 <user-template>
                <h3 slot="title">内盘期货开户</h3>
                <div slot="content">

                    <div>
                    
                    <p class="router-notify" v-if="profile.openAccountStatus == openAccountStatus.applyFor">
                                                    您的申请已提交，我们会在1-2 个工作日为您开户，请注意您的手机电话，短信通知。
                    </p>

                    <p class="router-notify" v-if="profile.openAccountStatus == openAccountStatus.openAccount">
                                                                                您的期货账号为：{{profile.innerDiscAccount}}
                    </p>
                    </div>

                    <div v-if="profile.openAccountStatus == openAccountStatus.notApplyFor ||profile.openAccountStatus == openAccountStatus.reject" class="col-md-4 col-md-offset-4">
                    
                    
                    <p v-if="profile.openAccountStatus == openAccountStatus.reject">
                                                    您的申请已提交，但是不符合开户要求,请重新提交申请。
                    </p>

                        <form   role="form" action="/member/openAccount" id="openAccount-form">

                            <div class="form-group">
                                <label >姓名</label><input type="text" name="name" class="form-control" />
                                <input type="hidden" v-model="token" name="token"/>
                            </div>

                            <div class="form-group">
                                <label >身份证</label><input type="text" name="idCard" class="form-control"  />
                            </div>
                            <div class="form-group">
                                <label >银行卡号</label><input type="text" name="bankCard" class="form-control"  />
                            </div>
                            <div class="form-group">
                                <label >银行卡预留手机号</label><input name="phoneNumber" type="text" class="form-control"  />
                            </div>
                            <div class="form-group">
                                <label >身份证正面照</label>
                                <upload-component input-name="idCardBehindUrl"></upload-component>
                            </div>
                            <div class="form-group">
                                <label >身份证背面照</label>
                                <upload-component input-name="idCardFrontUrl"></upload-component>
                            </div>


                            <br>
                            <button class="btn btn-default">提交</button>
                        </form>

                    </div>
                </div>
            </user-template>


            `,
            props:["token","profile","openAccountStatus"],
            updated:function () {
                initializeOpenAccountSubmitValidate();
            },mounted:function () {
                initializeOpenAccountSubmitValidate();
            }
        }



})