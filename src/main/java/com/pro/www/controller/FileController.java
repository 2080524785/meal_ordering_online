package com.pro.www.controller;

import com.pro.www.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@Api(tags = "文件操作")
@RequestMapping("/file")
public class FileController {
    @Value("${file.path}")
    private String path;

    @PostMapping("/upload")
    @ApiModelProperty(value = "文件上传")
    public R<String> upload(MultipartFile file){
        String fileName = UUID.randomUUID().toString();
        fileName += file.getOriginalFilename();


        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(path+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("[INFO] 文件成功上传");
        return R.success(fileName);
    }

    @GetMapping("/download")
    @ApiModelProperty(value = "文件下载")
    public void download(String name, HttpServletResponse response){
        // 读取
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path+name));
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len =0;
            byte[] bytes = new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 输出


    }
}
