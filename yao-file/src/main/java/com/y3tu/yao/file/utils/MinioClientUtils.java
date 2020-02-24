package com.y3tu.yao.file.utils;

import com.y3tu.yao.file.entity.MinioEntity;
import io.micrometer.core.instrument.util.StringUtils;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MinioClientUtils
 * Description:
 * date: 2020/1/14 15:23
 *
 * @author zht
 */
public class MinioClientUtils {
    @Autowired
    private MinioClient minioClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioClientUtils.class);

    private static int RETRY_NUM = 3;

    @Value("minio.url")
    private String url;
    @Value("minio.username")
    private String username;
    @Value("minio.password")
    private String password;
    @Value("minio.region")
    private String region;


    public boolean createBucketPublic(String bucketName) {
        boolean isCreated;
        try {
            minioClient.makeBucket(bucketName);
            //minioClient.setBucketPolicy(bucketName, bucketPublicPolicy);
            isCreated = true;
        } catch (Exception e) {
            isCreated = false;
            LOGGER.error("createBucketPublic", e);
            e.printStackTrace();
        }
        return isCreated;
    }

    public String uploadJpegFile(String bucketName, String minioPath, String jpgFilePath) {
        return uploadFile(bucketName, minioPath, jpgFilePath, MediaType.IMAGE_JPEG_VALUE);
    }

    public String uploadJpegStream(String bucketName, String minioPath, InputStream inputStream) {
        return uploadStream(bucketName, minioPath, inputStream, MediaType.IMAGE_JPEG_VALUE);
    }

    public String uploadStream(String bucketName, String minioFilePath, InputStream inputStream, String mediaType) {
        LOGGER.info("uploadStream for bucketName={} minioFilePath={} inputStream.getclass={}, mediaType={}", bucketName,
                minioFilePath, inputStream.getClass(), mediaType);
        if (StringUtils.isBlank(mediaType)) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        try {
            putObjectWithRetry(bucketName, minioFilePath, inputStream, mediaType);
            //return cleanUrlByRemoveIp(minioClient.getObjectUrl(bucketName, minioFilePath));
            return minioClient.getObjectUrl(bucketName, minioFilePath);
        } catch (Exception e) {
            LOGGER.error("uploadStream occur error:", e);
            throw new RuntimeException(e);
        }
    }

    public String uploadFile(String bucketName, String minioFilePath, String localFile, String mediaType) {
        LOGGER.info("uploadFile for bucketName={} minioFilePath={} localFile={}, mediaType={}", bucketName,
                minioFilePath, localFile, mediaType);
        if (StringUtils.isBlank(mediaType)) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        try {
            putObjectWithRetry(bucketName, minioFilePath, localFile, mediaType);
            //return cleanUrlByRemoveIp(minioClient.getObjectUrl(bucketName, minioFilePath));
            return minioClient.getObjectUrl(bucketName, minioFilePath);
        } catch (Exception e) {
            LOGGER.error("uploadFile occur error:", e);
            throw new RuntimeException(e);
        }
    }

    public List<MinioEntity> listFilesSwap(String bucketName, String prefix, boolean recursive) {
        LOGGER.info("list files for bucketName={} prefix={} recursive={}", bucketName, prefix, recursive);
        return swapResultToEntityList(minioClient.listObjects(bucketName, prefix, recursive));
    }

    public Iterable<Result<Item>> listFiles(String bucketName, String prefix, boolean recursive) {
        LOGGER.info("list files for bucketName={} prefix={} recursive={}", bucketName, prefix, recursive);
        return minioClient.listObjects(bucketName, prefix, recursive);
    }


    public List<MinioEntity> listFilesByBucketNameSwap(String bucketName) {
        LOGGER.info("listFilesByBucketName for bucketName={}", bucketName);
        return swapResultToEntityList(minioClient.listObjects(bucketName, null, true));
    }

    public Iterable<Result<Item>> listFilesByBucketName(String bucketName) {
        LOGGER.info("listFilesByBucketName for bucketName={}", bucketName);
        return minioClient.listObjects(bucketName, null, true);
    }

    public Iterable<Result<Item>> listFilesByBucketAndPrefix(String bucketName, String prefix) {
        LOGGER.info("listFilesByBucketAndPrefix for bucketName={} and prefix={}", bucketName, prefix);
        return minioClient.listObjects(bucketName, prefix, true);
    }

    public List<MinioEntity> listFilesByBucketAndPrefixSwap(String bucketName, String prefix) {
        LOGGER.info("listFilesByBucketAndPrefix for bucketName={} and prefix={}", bucketName, prefix);
        return swapResultToEntityList(minioClient.listObjects(bucketName, prefix, true));
    }


    private MinioEntity swapResultToEntity(Result<Item> result) {
        MinioEntity minioEntity = new MinioEntity();
        try {
            if (result.get() != null) {
                Item item = result.get();
                //minioEntity.setObjectName(cleanUrlByRemoveIp(item.objectName()));
                minioEntity.setObjectName(item.objectName());
                minioEntity.setIsDir(item.isDir());
                minioEntity.setETag(item.etag());
                minioEntity.setLastModified(item.lastModified());
                minioEntity.setSize(item.size());
                minioEntity.setStorageClass(item.storageClass());
            }
        } catch (Exception e) {
            LOGGER.error("UrlUtils error, e={}", e.getMessage());
        }
        return minioEntity;
    }

    private List<MinioEntity> swapResultToEntityList(Iterable<Result<Item>> results) {
        List<MinioEntity> minioEntities = new ArrayList<>();
        for (Result<Item> result : results) {
            minioEntities.add(swapResultToEntity(result));
        }
        return minioEntities;
    }

    public void putObjectWithRetry(String bucketName, String objectName, InputStream stream, String contentType) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, NoResponseException, InvalidBucketNameException, XmlPullParserException, InternalException {
        int current = 0;
        boolean isSuccess = false;
        while (!isSuccess && current < RETRY_NUM) {
            try {
                minioClient.putObject(bucketName, objectName, stream, contentType);
                isSuccess = true;
            } catch (ErrorResponseException e) {
                LOGGER.warn("[minio] putObject stream, ErrorResponseException occur for time =" + current, e);
                current++;
            }
        }
        if (current == RETRY_NUM) {
            LOGGER.error("[minio] putObject, backetName={}, objectName={}, failed finally!");
        }
    }

    public void putObjectWithRetry(String bucketName, String objectName, String fileName, String contentType) throws InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, NoResponseException, XmlPullParserException, ErrorResponseException, InternalException, InvalidArgumentException, InsufficientDataException {
        int current = 0;
        boolean isSuccess = false;
        while (!isSuccess && current < RETRY_NUM) {
            try {
                minioClient.putObject(bucketName, objectName, fileName, contentType);
                isSuccess = true;
            } catch (ErrorResponseException e) {
                current++;
                LOGGER.debug("[minio] putObject file, ErrorResponseException occur!");
            }
        }
        if (current == RETRY_NUM) {
            LOGGER.error("[minio] putObject, backetName={}, objectName={}, failed finally!");
        }
    }

}
