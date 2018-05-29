var require = {
    baseUrl:"/js",
    paths:{
        "jquery":"https://cdn.bootcss.com/jquery/1.9.1/jquery.min",
        "jquery-validator":"https://cdn.bootcss.com/jquery-validate/1.17.0/jquery.validate.min",
        "jquery-cookie":"https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min",
        "bootstrap":"https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min",
        "jquery-form":"https://cdn.bootcss.com/jquery.form/4.2.2/jquery.form.min",
        "vue":"https://cdn.bootcss.com/vue/2.5.16/vue.min",
        "vue-router":"https://cdn.bootcss.com/vue-router/3.0.1/vue-router.min",
        "image-code":"image-code",
        "validator-utils":"validator.utils",
        "phone-auth":"phone-auth",
        "session":"session",
        "webUploader":"/plugins/webuploader-0.1.5/webuploader.min",
        "axios":"https://cdn.bootcss.com/axios/0.18.0/axios.min.js",
        "Promise":"https://cdn.bootcss.com/bluebird/3.5.1/bluebird.min",

        /*服务*/
        "service/member-session":"service/member-session",
        "service/member":"service/member",
        "service/receive-account":"service/receive-account",

        /*组件*/
        "component/upload":"component/upload",
        "component/router":"component/router",

        /*路由*/
        "router/open-account":"router/open-account",
        "router/offline-deposit":"router/offline-deposit",
        "router/offline-withdraw":"router/offline-withdraw",


        "filter":"filter",
        "modal":"modal"

    },
    shim:{
        "jquery":{
            exports:"$"
        },"jquery-form":{
            deps:["jquery"]
        },"jquery-validator":{
            deps:["jquery"]
        },"jquery-cookie":{
            deps:["jquery"]
        },"bootstrap":{
            deps:["jquery"]
        },"image-code":{
            deps:["jquery"]
        },"session":{
            deps:["jquery-cookie"]
        },"phone-auth":{
            deps:["jquery","jquery-cookie"]
        },"validator-utils":[
            "jquery",
            "jquery-validator"
        ],"axios":{
            exports:"axios"
        },"Promise":{
            exports:"Promise"
        },



        "component/upload":[
            "vue",
            "webUploader"
        ],"component/router":[
            "vue"
        ],



        "service/member-session":{
            exports:"service/member-session",
            deps:["Promise","jquery","jquery-cookie"]
        },"service/member":{
            exports:"service/member",
            deps:["Promise","jquery"]
        },"service/receive-account":{
            exports:"service/receive-account",
            deps:["jquery"]
        },"service/transaction":{
            exports:"service/transaction",
            deps:["jquery"]
        },





        "router/offline-deposit":{
            exports:"router/offline-deposit",
            deps:["vue","jquery","service/member-session","component/upload","component/router","jquery-form","validator-utils"]
        },
        "router/open-account":{
            exports:"router/open-account",
            deps:["vue","jquery","service/member-session","component/upload","component/router","jquery-form","validator-utils"]
        },"router/offline-withdraw":{
            exports:"router/offlineWithdraw"
        },"router/transaction-list":{
            //TODO deps ..
            exports:"router/transactionList"
        },"router/open-account-list":["service/member","component/router"],



        "filter/app-dict":{
            exports:"filter/app-dict",
            deps:["vue"]
        },
        "component/widget":["vue","jquery","bootstrap"]



    }

};