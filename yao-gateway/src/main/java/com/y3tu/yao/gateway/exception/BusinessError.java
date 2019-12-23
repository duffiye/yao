package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.IError;

/**
 * ClassName: BusinessError
 * Description:
 * date: 2019/12/17 11:15
 *
 * @author zht
 */
public enum BusinessError implements IError {
    /**
     * 交易处理失败
     */
    SERVER_DO_ERROR("0001", "交易处理失败"),
    /**
     * 从ftp下载文件失败
     */
    SERVER_FTP_DOWN_ERROR("0002", "从ftp下载文件失败"),
    /**
     * 上传阿里云失败
     */
    SERVER_ALIYUN_UPLOAD_ERROR("0003", "上传阿里云失败"),
    /**
     * 图片错误
     */
    SERVER_IMG_ERROR("0004", "图片错误"),
    /**
     * 数据库错误
     */
    SERVER_DB_ERROR("0005", "数据库错误"),
    /**
     * 验证码错误
     */
    VALIDATE_CODE("validate-code-error", "验证码错误"),
    /**
     * 其他异常
     */
    SERVER_OTHER_ERROR("1099", "其他异常");

    String code;
    String message;

    BusinessError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
