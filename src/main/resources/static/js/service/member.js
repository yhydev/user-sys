





define(["Promise","jquery","service/member-session"],function (Promise,$,memberSessionService) {

    function nop() {
        
    }
    
    this.getFrontMember = function (success,error) {

        memberSessionService.memberSession.then(function (memberSession) {
            $.ajax({
                url:"/member/getFrontMember?"+$.param(memberSession.data),
                success:success?success:nop,
                error:error?error:nop
            })
        })
        

    }


    return this;
})