package com.y3tu.yao.file.entity;

import lombok.Data;

import java.util.Date;

/**
 * ClassName: MinioEntity
 * Description:
 * date: 2020/1/14 15:48
 *
 * @author zht
 */
@Data
public class MinioEntity {
    private String objectName;


    private Boolean isDir;


    private String eTag;


    private Date lastModified;


    private Integer size;


    private String storageClass;





}
