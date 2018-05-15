

require(["vue","webUploader"],function (Vue,WebUploader) {

    function randomVariable() {
        return "id_" + Math.ceil(Math.random()*1000);

    }

    var template = " <div class=\"upload-box\">\n" +
        "\n" +
        "            <div v-bind:id=\"idName\" :class=\"{'select-file':url,'unselect-file':!url}\"   class=\"select-upload-btn\">选择文件</div>\n" +
        "            <input  v-model=\"url\" v-bind:name=\"inputName\" type=\"hidden\">\n" +
        "        </div>"


    Vue.component("upload-component",{
        template:template,
        computed:{
            idName:randomVariable,
          inputName:function () {
              return this.$attrs["input-name"];
          }
        },data:function () {
            return {
                url:null
            }
        },//v-bind:class="{'select-file':'select-file',unselect-file'':'unselect-file'}"
        mounted:function () {
            var t = this;

            var uploader = WebUploader.create({

                // 选完文件后，是否自动上传。
                auto: true,

                // swf文件路径
                swf: '/plugins/webuploader-0.1.5/Uploader.swf',

                // 文件接收服务端。
                server: '/upload',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#'+t.idName,

                // 只允许选择图片文件。
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*'
                }
            });

            uploader.on("uploadSuccess",function (f,resp) {

                uploader.removeFile(f);

                if(resp.success){

                    //t.url = resp.data;
                    $("[name="+t.inputName+"]").val(resp.data)


                    $("#"+t.idName + " .webuploader-pick").html("重新选择");

                    var error = $("#"+t.inputName+"-error");

                    if(error.length != 0){
                        error.css("display","none");
                    }
                }else {
                   alert(resp.message);
                }



            })

        }
    })

})