package com.y3tu.yao.file.mapper;

import com.y3tu.yao.file.entity.TFile;
import org.springframework.stereotype.Repository;

public interface TFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TFile record);

    int insertSelective(TFile record);

    TFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TFile record);

    int updateByPrimaryKey(TFile record);

    TFile findByFid(String fid);

    int deleteByFid(String fid);
}