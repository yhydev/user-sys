(function(){
    $.fn.phoneAuth = function(sendfunc){

        var btn = $(this)
        var txt = btn.text()
        var second = 60;

        function sendFailedCall(){
            btn.text(txt)
        }

        function sendSuccessCall(){

            var daojishi = function(){
                setTimeout(function(){
                    btn.text(second + " s 秒后可再次发送")
                    second -= 1;
                    if(second > 0){
                        daojishi()
                    }else{
                        btn.text(txt)
                        second = 60;
                    }
                },1000)
            }
            daojishi()


        }



        var clickEvent = function(){
            if(second == 60){
                btn.text("发送中..")
                sendfunc(sendSuccessCall,sendFailedCall);
            }

        }

        btn.click(clickEvent);




    }

})()