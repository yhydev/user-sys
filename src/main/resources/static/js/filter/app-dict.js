require(["vue"],function (Vue) {

    var dict = {
        offline_deposit:"线下入金",
        offline_withdraw:"线下出金",
        failed:"交易失败",
        success:"交易成功",
        wait_check:"等待审核",
        all:"全部",
        manager:"管理员",
        user:"普通用户",
        proxy:"代理用户",
        not_apply_for:"未申请",
        apply_for:"已申请",
        open_account:"已开户",
        reject:"未开户"

    }

    Vue.filter("app_dict",function (k) {
        return dict[k]?dict[k]:k;
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