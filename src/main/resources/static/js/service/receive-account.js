define(["jquery"],function ($) {

    function nop() {}

    function getAll(success,failed) {
        $.ajax({
            success:success?success:nop,
            error:failed?success:nop,
            url:"/receiveAccount"
        })
    }

    return {
        getAll:getAll
    };
})