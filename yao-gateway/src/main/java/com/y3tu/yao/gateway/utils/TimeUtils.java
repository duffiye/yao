package com.y3tu.yao.gateway.utils;

import java.util.Date;

/**
 * ClassName: TimeUtil
 * Description:
 * date: 2019/11/20 14:39
 *
 * @author zht
 */
public class TimeUtils {

    public static long timeDiffSeconds (Date date,String timeStamp){
        long time = date.getTime();
        return time - Long.parseLong(timeStamp);
    }

    public static void main(String[] args) {
        timeDiffSeconds(new Date() ,"20191120144233");
    }
}
