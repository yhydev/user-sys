require(["vue"],function (Vue) {

    var dict = {
        offline_pay:"线下入金",
        offline_withdraw:"线下出金",
        failed:"交易失败",
        success:"交易成功",
        wait_check:"等待审核",

    }

    Vue.filter("app_dict",function (k) {
        return dict[k];
    })

    Vue.filter("not_apply_for",function (value) {
        return value == "not_apply_for";
    })

    Vue.filter("apply_for",function (value) {
        return value == "apply_for";
    })

    Vue.filter("open_account",function (open_account) {
        return value == "open_account";
    })

    Vue.filter("reject",function (value) {
        return value == "reject";
    })


    Vue.filter("default",function (k,def) {
        return k?k:def;
    })
})