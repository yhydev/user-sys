package com.gushushu.yanao.usersys.controller;

import com.gushushu.yanao.usersys.common.ResponseEntityBuilder;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionController {

    /**
     * 参数验证失败执行
     * @param e
     * @return
     */
    @ExceptionHandler({ BindException.class })
    @ResponseBody
    public ResponseEntity<com.gushushu.yanao.usersys.common.ResponseBody<Object>> validateException(BindException e) {
        return ResponseEntityBuilder.failed(e.getAllErrors().get(0).getDefaultMessage());
    }


    /**
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({ IllegalStateException.class })
    @ResponseBody
    public ResponseEntity<com.gushushu.yanao.usersys.common.ResponseBody<Object>> uploadSizeException(IllegalStateException exception){

        //exception.getCause().getCause()

        String msg = null;

        if(exception.getCause() instanceof FileUploadBase.FileSizeLimitExceededException){
            FileUploadBase.FileSizeLimitExceededException exceededException = (FileUploadBase.FileSizeLimitExceededException) exception.getCause();

            msg = String.format("文件大小出错，您上传的文件大小为 %s bytes,请选择为 %s bytes以下的文件",exceededException.getActualSize(),exceededException.getPermittedSize());
        }else{
            msg = String.format("未知错误，%s",exception.getMessage());
        }

        return ResponseEntityBuilder.failed(msg);
    }





}