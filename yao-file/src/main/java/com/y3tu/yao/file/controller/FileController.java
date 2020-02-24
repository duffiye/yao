package com.y3tu.yao.file.controller;

import com.y3tu.tool.core.exception.BusinessException;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.yao.file.entity.TFile;
import com.y3tu.yao.file.mapper.TFileMapper;
import com.y3tu.yao.file.param.FileParam;
import com.y3tu.yao.file.param.FileUpdateParam;
import com.y3tu.yao.file.service.FileService;
import com.y3tu.yao.file.vo.FileVO;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.UUID;

/**
 * ClassName: FileController
 * Description:文件控制器
 * date: 2020/1/14 15:48
 *
 * @author zht
 */
@Slf4j
@RestController
public class FileController {

    @Autowired
    public MinioClient minioClient;

    @Autowired
    private TFileMapper fileMapper;

    @Autowired
    private FileService fileService;

    @PostMapping("/upload/image/{bucket}")
    public R uploadImage(@RequestParam MultipartFile file, @PathVariable("bucket") String bucket) {
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new BusinessException("文件不能为空");
        }
        String objectName = getFid(file.getOriginalFilename(), bucket);
        bucket = getBucket(bucket);
        String fid = bucket + "/" + bucket;
        try {
            if (!minioClient.bucketExists(bucket)) {
                minioClient.makeBucket(bucket);
            }
            minioClient.putObject(bucket, "/img/"+objectName, file.getInputStream(), file.getContentType());
        } catch (Exception e) {
            log.info("文件上传失败，{}", e.getMessage());
            return R.error("文件上传失败");
        }
        return R.success(fid);
    }

    @PostMapping("/upload/{bucket}")
    public R upload(@RequestParam MultipartFile file, @PathVariable("bucket") String bucket) {
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new BusinessException("文件不能为空");
        }
        String fid = getFid(file.getOriginalFilename(), bucket);
        bucket = getBucket(bucket);
        try {
            if (!minioClient.bucketExists(bucket)) {
                minioClient.makeBucket(bucket);
            }
            minioClient.putObject(bucket, fid, file.getInputStream(), "application/octet-stream");
            //保存文件名称
            TFile tFile = new TFile();
            tFile.setFid(bucket + "/" + fid);
            tFile.setFilename(file.getOriginalFilename());
            fileMapper.insert(tFile);
        } catch (Exception e) {
            log.info("文件上传失败，{}", e.getMessage());
            System.out.println(e.getMessage());
            return R.error("文件上传失败");
        }
        return R.success(bucket + "/" + fid);
    }

    @PostMapping("/download")
    public R file(@RequestBody FileParam param) {
        try {
            String bucket = param.getBucket();
            if (StringUtils.isNotEmpty(param.getBucket()) && param.getBucket().startsWith("/")) {
                bucket = param.getBucket().substring(1);
            }
            FileVO fileVo = new FileVO();
            String url = minioClient.presignedGetObject(bucket, param.getFid(), param.getExpire());
            Calendar c = Calendar.getInstance();
            c.add(Calendar.SECOND, param.getExpire());
            long expire = c.getTime().getTime();
            fileVo.setUrl(url);
            fileVo.setExpire(expire);
            fileVo.setFid(param.getBucket() + "/" + param.getFid());
            return R.success(fileVo);
        } catch (Exception e) {
            log.info("获取文件异常, 请求参数：{}, 异常：{}", param, e.getMessage());
            return R.error("获取文件失败");
        }
    }

    @GetMapping("/download/file")
    public void fileInputStream(@RequestParam String fid, HttpServletResponse response) {
        try {
            String bucket = null;
            String objectName = null;
            if (StringUtils.isNotEmpty(fid)) {
                if (fid.startsWith("/")) {
                    bucket = fid.substring(1);
                }
                String[] split = fid.split("/");
                if (split.length > 1) {
                    bucket = split[0];
                    objectName = fid.substring(fid.indexOf("/") + 1);
                }
            }
            OutputStream out = response.getOutputStream();
            if (StringUtils.isNotEmpty(bucket) && StringUtils.isNotEmpty(objectName)) {
                //查询文件原名
                TFile file = fileMapper.findByFid(fid);
                InputStream is = minioClient.getObject(bucket, objectName);
                response.setContentType("application/octet-stream; charset=utf-8");
                response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(file.getFilename(), "UTF-8") + ".xls;filename*=UTF-8''" + URLEncoder.encode(file.getFilename(), "UTF-8"));
                StreamUtils.copy(is, out);
                is.close();
            } else {
                out.write("{\"code\":503,\"data\":null,\"message\":\"文件地址有误\"}".getBytes(StandardCharsets.UTF_8));
            }
            out.flush();
        } catch (Exception e) {
            log.info("获取文件异常, 请求参数：{}, 异常：{}", fid, e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public R deleteFile(@RequestParam String fid) {
        try {
            String bucket = null;
            String objectName = fid.substring(fid.indexOf("/") + 1);
            getBucket(fid);
            minioClient.removeObject(bucket, objectName);
            fileMapper.deleteByFid(fid);
        } catch (Exception e) {
            log.info("删除文件异常, 请求参数：{}, 异常：{}", fid, e.getMessage());
            return R.success(false);
        }
        return R.success(true);
    }

    @DeleteMapping("/remove/{bucketName}")
    public R removeFileByBucket(@PathVariable("bucketName") String bucketName) throws XmlPullParserException {
        Iterable<Result<Item>> results = minioClient.listObjects(bucketName);
        results.forEach(itemResult -> {
            try {
                minioClient.removeObject(bucketName,itemResult.get().objectName());
                System.out.println(itemResult.get().objectName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return R.success();
    }

    private String getBucket(String bucket) {
        if (StringUtils.isNotEmpty(bucket)) {
            if (bucket.startsWith("/")) {
                bucket = bucket.substring(1);
            }
            String[] split = bucket.split("/");
            if (split.length > 1) {
                bucket = split[0];
            }
        }
        return bucket;
    }

    private String getFid(String fileName, String bucket) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String fid = UUID.randomUUID().toString() + suffix;
        if (StringUtils.isNotEmpty(bucket)) {
            if (bucket.startsWith("/")) {
                bucket = bucket.substring(1);
            }
            String[] split = bucket.split("/");
            if (split.length > 1) {
                StringBuilder tmp = new StringBuilder();
                for (int i = 1; i < split.length; i++) {
                    tmp.append(split[i]).append("/");
                }
                fid = tmp.toString() + fid;
            }
        }
        return fid;
    }

    @RequestMapping("/batch-upload")
    public R batchUpload(@RequestBody FileUpdateParam param) throws Exception {
        fileService.batchUpload(param);
        return R.success();
    }

}
