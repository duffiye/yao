package com.y3tu.yao.file.service;

import com.y3tu.yao.file.entity.UpdateUrl;
import com.y3tu.yao.file.mapper.UrlMapper;
import com.y3tu.yao.file.param.FileUpdateParam;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * ClassName: FileService
 * Description:
 * date: 2020/2/18 10:45
 *
 * @author zht
 */
@Service
public class FileService {

    @Autowired
    UrlMapper urlMapper;

    @Autowired
    public MinioClient minioClient;

    private  final int queryNum = 10;


    public void batchUpload(FileUpdateParam param) throws Exception {
        int maxId = urlMapper.getMaxId(param.getTableName());

        int count = 0;
        int queryId;
        do{
            // 分批次
            List<UpdateUrl> urlList = urlMapper.selectUrl(param.getSelectColumnName(), param.getTableName(),count,queryNum);
            count=count+queryNum;
            int queryStartId =urlList.get(0).getId();
            queryId=urlList.get(urlList.size()-1).getId();
            // 上传文件
            uploadFile(urlList,param);
            System.out.println(param.getTableName()+"表,第"+queryStartId+"到"+queryId+"条数据更新完成");
        }while (queryId<maxId);

    }


    private void uploadFile(List<UpdateUrl> urlList,FileUpdateParam param) throws Exception {
        HttpURLConnection conn;
        InputStream inputStream = null;
        BufferedInputStream bis = null;

        trustAllHttpsCertificates();
        for (UpdateUrl updateUrl : urlList) {
            try {
                // 建立链接
                if (Objects.isNull(updateUrl.getUrl())){
                    continue;
                }
                URL httpUrl = new URL(updateUrl.getUrl());
                conn = (HttpURLConnection) httpUrl.openConnection();
                //以Post方式提交表单，默认get方式
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                // post方式不能使用缓存
                conn.setUseCaches(false);
                //连接指定的资源
                conn.connect();
                //获取网络输入流
                inputStream = conn.getInputStream();
                String bucket = "x-link";
                String objectName = getFid();
                if (!minioClient.bucketExists(bucket)) {
                    minioClient.makeBucket(bucket);
                }
                minioClient.putObject(bucket, "img/"+objectName, inputStream, "image/jpg");
                bis = new BufferedInputStream(inputStream);
                System.out.println(objectName + ":上传完成...");
                updateUrl.setNewUrl(bucket + "/" + objectName);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        urlMapper.updateUrl(urlList, param.getUpdateColumnName(), param.getTableName());
    }

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    private String getFid() {
        return UUID.randomUUID().toString() + ".jpg";
    }

}
