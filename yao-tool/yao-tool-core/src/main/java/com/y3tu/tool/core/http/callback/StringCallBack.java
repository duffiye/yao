package com.y3tu.tool.core.http.callback;

import com.y3tu.tool.core.http.HttpException;
import okhttp3.Call;
import okhttp3.Response;

import java.io.IOException;

/**
 *功能描述 :处理字符串回调
 * @author zht
 * @date 2019/12/23
 */
public class StringCallBack extends AbstractCallBack<String> {


    @Override
    public void onFailure(Call call, IOException e) {
        throw new HttpException(e);
    }

    @Override
    public void onResponse(Call call, Response response) {
        onParseResponse(call, response);
    }

    @Override
    public String onParseResponse(Call call, Response response) {
        try {
            return response.body().string();
        } catch (Exception e) {
            throw new RuntimeException("failure");
        }
    }
}
