require(["vue"],function (Vue) {

    var dict = {
        offline_pay:"线下入金",
        offline_withdraw:"线下出金",
        failed:"交易失败",
        success:"交易成功",
        wait_check:"等待审核"
    }

    Vue.filter("app_dict",function (k) {
        return dict[k];
    })
})