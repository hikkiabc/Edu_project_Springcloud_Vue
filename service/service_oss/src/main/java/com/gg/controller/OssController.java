package com.gg.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.gg.util.ConstantUtil;
import com.gg.utils.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/oss")
public class OssController {
    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;
    @PostMapping("/upload_avatar")
    public CommonResult uploadAvatar(MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        CommonResult commonResult = new CommonResult();
        OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);
        InputStream inputStream = file.getInputStream();
        String fileName=UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();
        String date = new DateTime().toString("yyyy/MM/dd");
        fileName=date+"/"+fileName;
        PutObjectResult putObjectResult = ossClient.putObject(bucketName,fileName, inputStream);
        commonResult.setData(putObjectResult);
        ossClient.shutdown();

        String url="https://"+bucketName+"."+endPoint+"/"+fileName;
        commonResult.setData(url);
        return commonResult;
    }
    @PostMapping("/e")
    public CommonResult excel(MultipartFile file){
        Map<String, Object> excelData = ExcelUtil.getExcelData(file, 0);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(excelData);
        return commonResult;
    }
    @PostMapping("/test/{id}")
    public Object test(@PathVariable String id){

        List<String> list = Arrays.asList("a",id);
        return list;
    }


}
