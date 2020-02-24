package com.y3tu.yao.file.vo;

import lombok.Data;

@Data
public class FileVO {
    public FileVO() {
    }

    public FileVO(String url) {
        this.url = url;
    }

    private String url;

    private String fid;

    private Long expire;
}
