package com.y3tu.yao.common.util;


import io.micrometer.core.instrument.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.y3tu.yao.common.constants.DateConstant.PURE_DATETIME_MS_PATTERN;

/**
 * ClassName: TimeUtil
 * Description:
 * date: 2019/11/20 14:39
 *
 * @author zht
 */
public class TimeUtils {

    /**
     * 两个时间之差
     *
     * @param startDate
     * @param endDate
     * @return 分钟
     */
    public static Integer getBetweenMinutes(Date startDate, Date endDate) {
        long betweenMilliSeconds = getBetweenMilliSeconds(startDate, endDate);
        return (int) (betweenMilliSeconds / (60 * 1000));
    }

    /**
     * 两个时间之差
     *
     * @param startDateStr
     * @param endDateStr
     * @return 分钟
     */
    public static Integer getBetweenMinutes(String startDateStr, String endDateStr) {
        long betweenMilliSeconds = getBetweenMilliSeconds(startDateStr, endDateStr);
        return (int) (betweenMilliSeconds / (60 * 1000));
    }

    /**
     * 两个时间之差
     *
     * @param startDate
     * @param endDate
     * @return 秒数
     */
    public static Integer getBetweenSecond(Date startDate, Date endDate) {
        long betweenMilliSeconds = getBetweenMilliSeconds(startDate, endDate);
        return (int) (betweenMilliSeconds / (1000));
    }

    /**
     * 两个时间之差
     *
     * @param startDateStr
     * @param endDateStr
     * @return 秒数
     */
    public static Integer getBetweenSecond(String startDateStr, String endDateStr) {
        long betweenMilliSeconds = getBetweenMilliSeconds(startDateStr, endDateStr);
        return (int) (betweenMilliSeconds / (1000));
    }

    /**
     * 两个时间之差
     *
     * @param startDateStr
     * @param endDateStr
     * @return 秒数
     */
    public static long getBetweenMilliSeconds(String startDateStr, String endDateStr) {
        SimpleDateFormat format = new SimpleDateFormat(PURE_DATETIME_MS_PATTERN);
        Date startDate;
        Date endDate;
        try {
            startDate = format.parse(startDateStr);
            endDate = format.parse(endDateStr);
            try {
                long ss = 0;
                if (startDate.before(endDate)) {
                    ss = endDate.getTime() - startDate.getTime();
                } else {
                    ss = startDate.getTime() - endDate.getTime();
                }
                return ss;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
            return 0;
        }
    }


    /**
     * 两个时间之差
     *
     * @param startDate
     * @param endDate
     * @return 秒数
     */
    public static long getBetweenMilliSeconds(Date startDate, Date endDate) {
        try {
            if (startDate != null && endDate != null) {
                long ss;
                if (startDate.before(endDate)) {
                    ss = endDate.getTime() - startDate.getTime();
                } else {
                    ss = startDate.getTime() - endDate.getTime();
                }
                return ss;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDatFormat(Date date, String dateFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
            return format.format(new Date());
        }
    }


    /**
     * 拆分时间间隔
     * Title: getDateArray
     * Description: '2018-04-04 - 2018-05-22'
     *
     * @author guojx
     * @date 2018年4月13日
     */
    public static String[] getDateArray(String date) {
        String[] defalut = {"", ""};
        try {
            if (StringUtils.isNotEmpty(date)) {
                String startDate = date.substring(0, 10);
                String endDate = date.substring(date.length() - 10, date.length());
                String[] dates = {startDate, endDate};
                return dates;
            } else {
                return defalut;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return defalut;
        }
    }

    public static Date getDateBefore(Date d, int day, Integer type) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(type, now.get(type) - day);
        return now.getTime();
    }

    /**
     * 获取给定日期之前或之后的日期
     *
     * @param date
     * @param type
     * @param num
     * @return
     */
    public static String getPreviouslyDate(Date date, String type, Integer num) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String resultDate = "";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            switch (type) {
                case "day":
                    c.add(Calendar.DATE, num);
                    break;
                case "month":
                    c.add(Calendar.MONTH, num);
                    break;
                case "year":
                    c.add(Calendar.YEAR, num);
                    break;
                default:
                    c.add(Calendar.DATE, 0);
                    break;
            }
            Date d = c.getTime();
            resultDate = format.format(d);
            return resultDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ******************************************
     * 方法名称:判断日期是否是某一天
     * 创建人：guojx
     * 创建时间：2018年4月19日 下午9:44:57
     *
     * @param date
     * @param num
     * @return
     * @return:boolean ******************************************
     */
    public static boolean isSameday(Date date, Integer num) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = fmt.format(date).toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, num);
        String oneDateStr = fmt.format(calendar.getTime()).toString();
        if (dateStr.equals(oneDateStr)) {//格式化为相同格式
            return true;
        } else {
            return false;
        }
    }
}
