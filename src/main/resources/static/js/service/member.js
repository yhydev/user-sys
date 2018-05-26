





define(["Promise","jquery","service/member-session"],function (Promise,$,memberSessionService) {

    function nop() {
        
    }
    this.getFrontMember = function (success,error) {
        memberSessionService.memberSession.then(function (memberSession) {

            success =success?success:nop
            error =error?error:nop;

            if(memberSession.success){
                $.ajax({
                    url:"/member/getFrontMember?"+$.param(memberSession.data),
                    success:success,
                    error:error
                })
            }else{
                success(memberSession)
            }
        })
        

    }


    return this;
})