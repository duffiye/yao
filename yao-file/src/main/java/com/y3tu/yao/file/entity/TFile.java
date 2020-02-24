package com.y3tu.yao.file.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * t_file
 * @author 
 */
@Data
public class TFile implements Serializable {
    private Integer id;

    private String fid;

    private String filename;

    private static final long serialVersionUID = 1L;

}