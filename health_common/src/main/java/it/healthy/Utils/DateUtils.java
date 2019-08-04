package it.healthy.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * 日期转换-  String -> Date
     *
     * @param orderDate 字符串时间
     * @return Date类型信息
     * @throws Exception 抛出异常
     */
    public static Date parseString2Date(String orderDate) throws ParseException {
        if(orderDate==null) return null;
        return parseString2Date(orderDate, "yyyy-MM-dd");
    }

    public static Date parseString2Date(String orderDate,String pattern) throws ParseException {
        if (orderDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(orderDate);
        return date;
    }

    /**
     * 日期转换 Date -> String
     *
     * @param date Date类型信息
     * @return 字符串时间
     * @throws Exception 抛出异常
     */
    public static String parseDate2String(Date date) throws Exception {
        if (date == null) {
            return null;
        }
        return parseDate2String(date, "yyyy-MM-dd");
    }

    /**
     * 日期转换 Date -> String
     * @param date
     * @param pattern
     * @return
     */
    private static String parseDate2String(Date date, String pattern) {
        if(date==null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
