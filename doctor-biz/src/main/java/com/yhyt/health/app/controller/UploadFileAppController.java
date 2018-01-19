package com.yhyt.health.app.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.result.AppResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月24日 下午2:40:12
 * 类说明
 */
@Api(value = "", description = "文件上传操作")
@RestController
public class UploadFileAppController {

    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private FastFileStorageClient storageClient;

    private static final String[] SUPPORT_IMAGE_TYPE = { "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP" };
    private static final List<String> SUPPORT_IMAGE_LIST = Arrays.asList(SUPPORT_IMAGE_TYPE);

    private final Logger logger = LoggerFactory.getLogger(UploadFileAppController.class);

    @ApiOperation(value = "单个文件上传", notes = "单个文件上传")
    @PostMapping("/uploadImage")
    public AppResult uploadImg(@RequestParam("file") MultipartFile importFile) throws IOException {
        AppResult appResult = new AppResult();
        InputStream inputStream = importFile.getInputStream();
        String[] fileExtNames=importFile.getOriginalFilename().split("\\.");
        String fileExtName = fileExtNames[fileExtNames.length - 1];
//        String fileExtName = importFile.getOriginalFilename().split("\\.")[1];
        //如果是图片
        StorePath storePath=null;
        if (SUPPORT_IMAGE_LIST.contains(fileExtName.toUpperCase())) {
            storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, importFile.getSize(), fileExtName, null);
        } else {
            storePath =storageClient.uploadFile(inputStream, importFile.getSize(), fileExtName, null);
        }
        storePath.getGroup();
        storePath.getPath();
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("url", doctorConstant.getFdfs().get("url") + storePath.getFullPath());
        //storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        return appResult;
    }


    @ApiOperation(value = "多个文件上传", notes = "多个文件上传")
    @PostMapping("/uploadImages")
    public List<String> uploadImgs(HttpServletRequest request) throws IOException {
        List<String> list = new ArrayList<String>();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("importFile");
        for (MultipartFile file : files) {
            InputStream inputStream = file.getInputStream();
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, file.getSize(), file.getOriginalFilename().split("\\.")[1], null);
            list.add(storePath.getFullPath());
        }
        return list;
    }

    @ApiOperation(value = "单个文件删除", notes = "单个文件删除")
    @DeleteMapping("/uploadImage")
    public String deleteImg(@RequestParam("importFile") MultipartFile importFile) throws IOException {

        InputStream inputStream = importFile.getInputStream();
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, importFile.getSize(), importFile.getOriginalFilename().split("\\.")[1], null);
        logger.info(storePath.getFullPath());
        //storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        return storePath.getFullPath();
    }
}
