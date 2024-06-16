package com.modernframework.core.utils;

import com.modernframework.core.utils.tuple.Tuple2;
import com.modernframework.core.utils.tuple.Tuples;
import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * 时间工具
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
@Slf4j
public class DateUtils {

    public static final String FORMATSTR_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FORMATSTR_YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.S";
    public static final String FORMATSTR_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATSTR_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String FORMATSTR_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String FORMATSTR_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMATSTR_YYYY = "yyyy";
    public static final String FORMATSTR_YYYYMMDD = "yyyyMMdd";
    public static final String FORMATSTR_HH_mm_ss = "HH:mm:ss";
    public static final String FORMATSTR_YYYYMMDDDELIMITER = "-";

    public static final DateTimeFormatter dateFormatter_yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter dateFormatter_yyyy_MM_dd_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final int ONE_DAY_DATE_TIME_STAMP = 1000 * 60 * 60 * 24;
    public static final int ONE_HOUR_DATE_TIME_STAMP = 1000 * 60 * 60;
    public static final int ONE_SECOND_DATE_TIME_STAMP = 1000;

    @SuppressWarnings("unchecked")
    private static final Tuple2<Pattern, String>[] SIMPLE_DATE_FORMATS = new Tuple2[]{
            Tuples.of(Pattern.compile("^\\d{4} \\d{2} \\d{2} \\d{2} \\d{2} \\d{2}$"), "yyyy MM dd HH mm ss"),
            Tuples.of(Pattern.compile("^\\d{4} \\d{2} \\d{2}$"), "yyyy MM dd"),
            Tuples.of(Pattern.compile("^\\d{2} \\d{2} \\d{4} \\d{2} \\d{2} \\d{2}$"), "MM dd yyyy HH mm ss"),
            Tuples.of(Pattern.compile("^\\d{2} \\d{2} \\d{4}$"), "MM dd yyyy"),
    };

    @SuppressWarnings("unchecked")
    private static final Tuple2<Pattern, String>[] LOCALE_DATE_FORMATS = new Tuple2[]{
            Tuples.of(Pattern.compile(".*\\d{2} \\d{4} \\d{2} \\d{2} \\d{2}$"), "MMMM dd yyyy HH mm ss"),
            Tuples.of(Pattern.compile(".*\\d{2} \\d{4} \\d{2} \\d{2} \\d{2}$"), "MMM dd yyyy HH mm ss"),
            Tuples.of(Pattern.compile(".*\\d{2} \\d{4}$"), "MMMM dd yyyy"),
            Tuples.of(Pattern.compile(".*\\d{2} \\d{4}$"), "MMM dd yyyy"),
    };

    /*一周星期枚举*/
    public static enum DAY_IN_WEEK_ENUM {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY}

    ;

    /**
     * 一天的时间戳
     */
    public final Long ONE_DAY_DURATION = 1000 * 60 * 60 * 24L;

    /**
     * 尽可能将参数转换成日期
     */
    public static Date parse(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return parse((String) obj);
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Number) {
            Number n = (Number) obj;
            return new Date(n.longValue());
        }
        if (obj instanceof LocalDateTime) {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = ((LocalDateTime) obj).atZone(zone).toInstant();
            return Date.from(instant);
        }
        return null;
    }

    public static LocalDateTime parse2LocalDateTime(String dateStr, DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.BASIC_ISO_DATE;
        }
        return LocalDateTime.parse(dateStr, dateTimeFormatter);
    }

    public static LocalDateTime parse2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate parse2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String getYYYYMMDDHHMMSS(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    /**
     * 尽可能将参数转换成日期
     */
    public static Date parse(String str) {
        if (str == null) {
            return null;
        }
        str = str.replaceAll("[:\\- ,\\.]+", " ").trim();
        if (str.isEmpty()) {
            return null;
        }
        ParsePosition pos = new ParsePosition(0);
        for (Tuple2<Pattern, String> fmt : SIMPLE_DATE_FORMATS) {
            if (str.length() != fmt._1.length()) {
                continue;
            }
            if (!fmt._0.matcher(str).matches()) {
                continue;
            }
            Date d = new SimpleDateFormat(fmt._1).parse(str, pos);
            if (d != null) {
                return d;
            }
            pos.setIndex(0);
        }
        Locale[] locales = Locale.getAvailableLocales();
        for (Tuple2<Pattern, String> fmt : LOCALE_DATE_FORMATS) {
            if (!fmt._0.matcher(str).matches()) {
                continue;
            }
            SimpleDateFormat parser = new SimpleDateFormat(fmt._1);
            for (Locale locale : locales) {
                parser.setDateFormatSymbols(DateFormatSymbols.getInstance(locale));
                Date d = parser.parse(str, pos);
                if (d != null) {
                    return d;
                }
                pos.setIndex(0);
            }
        }
        return null;
    }

    /**
     * 按照DATETIME格式生成时间字符串
     */
    public static String format(Object date) {
        return format(date, FORMATSTR_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 按照指定格式生成时间字符串
     */
    public static String format(Object date, String pattern) {
        return format(date, pattern, Locale.getDefault());
    }

    /**
     * 按照指定格式生成时间字符串
     */
    public static String format(Object date, String pattern, Locale local) {
        Date d = parse(date);
        if (d == null) {
            return "";
        }
        return new SimpleDateFormat(pattern, local).format(d);
    }

    /**
     * 计算两个日期间的间隔天数
     */
    private static long daysBetween(Date one, Date two) {
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference);
    }

    /**
     * 取相对于当前时间‘前’或‘后’‘N’天的日期字符串
     *
     * @param offset  正数为n天后，负数为n天前
     * @param pattern 输出格式，默认为DATETIME（即 yyyy-MM-dd HH:mm:ss）
     */
    public static String getPrevOrNextNDay(int offset, String pattern) {
        LocalDateTime now = LocalDateTime.now();
        now.plusDays(offset);
        if (StringUtils.isBlank(pattern)) {
            pattern = FORMATSTR_YYYY_MM_DD_HH_MM_SS;
        }
        return now.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 取相对于当前时间‘前’或‘后’‘N’天的日期对象｛结果日期舍去“时”、“分”、“秒”为“0时:0分:0秒”｝
     */
    public static Date getPrevOrNextNDayDate(int offset) {
        try {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DAY_OF_MONTH, offset);

            now.set(Calendar.HOUR_OF_DAY, 0);
            now.set(Calendar.MINUTE, 0);
            now.set(Calendar.SECOND, 0);

            return now.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return 当前时间 yyyy-MM-dd HH:mm:ss
     */
    public static String now() {
        return format(new Date(), FORMATSTR_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @return 当前日期 yyyy-MM-dd
     */
    public static String today() {
        return format(new Date(), FORMATSTR_YYYY_MM_DD);
    }

    public static String thisYear() {
        return format(new Date(), FORMATSTR_YYYY);
    }

    /**
     * 获取当前时间所在周的星期几
     *
     * @return
     */
    public static String getDayInWeekName() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//获取周几而非一周的第几天
        if (calendar.getFirstDayOfWeek() == Calendar.SUNDAY) {//一周首日是否是周日
            dayOfWeek = dayOfWeek - 1;
            if (dayOfWeek == 0) {
                dayOfWeek = 7;
            }
        }
        return StringUtils.toUpperCaseFirstOne(DAY_IN_WEEK_ENUM.values()[dayOfWeek - 1].name().toLowerCase());
    }

    /**
     * 取得所在时区的当前时间
     *
     * @param zone 所在的时区
     *             东区为正： 例 东8区：1
     *             西区为负： 例 本8区：-8
     * @return 返回设定时区的时间
     */
    public static Date getZoneTime(int zone) {
        /* 取得所在的时区 */
        TimeZone /*time*/defaultTimeZone;
        if (zone > 0) {
            /* 东区的时间加 */
            defaultTimeZone = TimeZone.getTimeZone("GMT+" + zone); //设置所在的时区
        } else {
            /* 西区时间差 */
            defaultTimeZone = TimeZone.getTimeZone("GMT-" + Math.abs(zone));
        }
        /* 设置时区 */
        TimeZone.setDefault(defaultTimeZone);
        /* 获取实例 */
        Calendar calendar = Calendar.getInstance();
        /* 获取Date对象 */
        return calendar.getTime();
    }

    /**
     * 取得所在时区的当前时间
     *
     * @param zone             所在的时区
     *                         东区为正： 例 东8区：1
     *                         西区为负： 例 西8区：-8
     * @param simpleDateFormat 例: yyyy-MM-dd
     *                         默认为: yyyy-MM-dd HH:mm:ss
     * @return 返回设定时区的时间字符
     */
    public static String localTime(int zone, String simpleDateFormat) {
        DateFormat format;
        if (simpleDateFormat == null || "".equals(simpleDateFormat)) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            format = new SimpleDateFormat(simpleDateFormat);
        }
        /* 获取Date对象 */
        Date date = getZoneTime(zone);
        String dateStr = new String();
        /* 对象进行格式化，获取字符串格式的输出 */
        try {
            dateStr = format.format(date);
        } catch (Exception e) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateStr = format.format(date);
        }
        return dateStr;
    }

    /**
     * Date->XMLDate格式转换
     *
     * @param date
     * @return XMLGregorianCalendar
     * @throw
     */
    public static XMLGregorianCalendar toXmlDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        DatatypeFactory dtFactory = null;
        try {
            dtFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            return null;
        }

        XMLGregorianCalendar xmlDate = dtFactory.newXMLGregorianCalendar();
        xmlDate.setYear(cal.get(Calendar.YEAR));
        //由于Calendar.MONTH取值范围为0~11,需要加1
        xmlDate.setMonth(cal.get(Calendar.MONTH) + 1);
        xmlDate.setDay(cal.get(Calendar.DAY_OF_MONTH));
        xmlDate.setHour(cal.get(Calendar.HOUR_OF_DAY));
        xmlDate.setMinute(cal.get(Calendar.MINUTE));
        xmlDate.setSecond(cal.get(Calendar.SECOND));

        return xmlDate;
    }

    /**
     * Date->Timestamp格式转换
     *
     * @param date
     * @return Timestamp
     * @throws
     */
    public static Timestamp toTimestampDate(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * Timestamp->Date
     *
     * @param ts
     * @return Date
     * @throws
     */
    public static Date toDate(Timestamp ts) {
        if (ts == null) {
            return null;
        }
        Date date = ts;
        return date;
    }

    /**
     * 日期比较
     *
     * @param date1
     * @param date2
     * @return date1 - date2
     */
    public static long compileDate(Object date1, Object date2) {
        Date d1 = parse(format(parse(date1), FORMATSTR_YYYY_MM_DD));
        Date d2 = parse(format(parse(date2), FORMATSTR_YYYY_MM_DD));
        return d1.getTime() - d2.getTime();
    }

    /**
     * 时间比较
     *
     * @param date1
     * @param date2
     * @return date1 - date2
     */
    public static long compileDateTime(Object date1, Object date2) {
        Date d1 = parse(format(parse(date1), FORMATSTR_YYYY_MM_DD_HH_MM_SS));
        Date d2 = parse(format(parse(date2), FORMATSTR_YYYY_MM_DD_HH_MM_SS));
        return d1.getTime() - d2.getTime();
    }

    /**
     * 时间比较
     *
     * @param date_1
     * @param date_2
     * @return Integer
     * @throws
     * @desc -----------------
     * 1（date1 > date2）
     * 0（date1 = date2）
     * -1（date1 < date2）
     * 9（时间格式非法）
     * -----------------
     */
    public static Integer compare(String date_1, String date_2) {
        DateFormat df = new SimpleDateFormat(FORMATSTR_YYYY_MM_DD_HH_MM_SS);
        try {
            Date d1 = df.parse(date_1);
            Date d2 = df.parse(date_2);
            long delta = d1.getTime() - d2.getTime();
            if (delta > 0) {
                return 1; //d1>d2
            } else if (delta == 0) {
                return 0; //d1=d2
            } else {
                return -1; //d1<d2
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 9; //时间格式非法
        }
    }

    /**
     * 获取当天零点时间
     */
    public static String getZeroTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (DateUtils.format(cal.getTime(), DateUtils.FORMATSTR_YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 获取当天结束时间
     */
    public static String getLastTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return (DateUtils.format(cal.getTime(), DateUtils.FORMATSTR_YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 日期是否违法，格式 "yyyy-MM-dd HH:mm:ss"
     * 年 月 日 不能为空
     * 时间可为空，为空时则为 "00:00:00"
     */
    public static boolean isIllegalDateTime(String year, Integer month, Integer day, String time) {
        if (year == null || month == null || day == null) {
            return true;
        }
        String _triggerMonth = month < 10 ? "0" + month : month + "";
        String _triggerDay = day < 10 ? "0" + day : day + "";
        String _time = StringUtils.isBlank(time) ? "00:00:00" : time;
        String dateStr = year + "-" + _triggerMonth + "-" + _triggerDay + " " + _time;
        String dateFormatStr = DateUtils.format(DateUtils.parse(dateStr));
        if (dateFormatStr == null) {
            return true;
        }
        return !dateStr.equals(dateFormatStr);
    }

    /**
     * 时间是否违法，格式 "HH:mm:ss"
     */
    public static boolean isIllegalTime(String time) {
        if (StringUtils.isBlank(time)) {
            return true;
        }
        String todayTime = today() + " " + time;
        String dateFormatStr = DateUtils.format(DateUtils.parse(todayTime));
        if (dateFormatStr == null) {
            return true;
        }
        return !todayTime.equals(dateFormatStr);
    }

    /**
     * 根据月份数字，转换格式
     * 1 ==> 01
     * 10 ==> 10
     */
    public static String getFormatMonth(String month) {
        if (StringUtils.isEmpty(month)) {
            return "null";
        }
        String m = month + "";
        int mSize = m.length();
        if (mSize > 1) {
            return m;
        } else {
            m = "0" + m;
            return m;
        }
    }

    /**
     * 根据日期数字，转换格式
     * 1 ==> 01
     * 10 ==> 10
     */
    public static String getFormatDay(String day) {
        if (StringUtils.isEmpty(day)) {
            return "null";
        }
        String d = day + "";
        int dSize = d.length();
        if (dSize > 1) {
            return d;
        } else {
            d = "0" + d;
            return d;
        }
    }

    /**
     * 根据年与月返回月中最大的天数
     * 年不能小于0
     * 月的有效参数为 0~11
     * 错误参数返回 -1
     *
     * @param year
     * @param month [0-11]
     * @return
     */
    public static int getActualMaxDayInMonth(int year, int month) {
        if (year <= 0 || month < 0 || month > 11) {
            return -1;
        }
        Calendar minimumCalendar = Calendar.getInstance();
        minimumCalendar.set(Calendar.YEAR, year);
        minimumCalendar.set(Calendar.MONTH, month);
        System.out.println(DateUtils.format(minimumCalendar.getTime()));
        int max = minimumCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 本月中最小的一天
        return max;
    }

    /**
     * 判断指定day是否存在
     *
     * @param year
     * @param month [0-11]
     * @param day   [0-31]
     * @return boolean
     * @throws
     */
    public static boolean isDayExist(int year, int month, int day) {
        if (year <= 0) {
            /* 非法年 */
            return false;
        }
        if (month < 1 || month > 12) {
            /* 非法月 */
            return false;
        }
        if (day < 1 || day > 31) {
            /* 非法日 */
            return false;
        }

        int maxDays = getActualMaxDayInMonth(year, month - 1);
        return (day <= maxDays) ? true : false;
    }

    /**
     * 判断 指定day 在 指定year、指定 month 内是否存在，存在返回true, 不存在返回 false
     *
     * @param year  [1-9999]
     * @param month [1-12]
     * @param day   [1-31]
     * @return
     */
    public static boolean isDayValid(int year, int month, int day) {
        if (year < 1 || year > 9999
                || month < 1 || month > 12
                || day < 1 || day > 31) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        if ((year + "").length() < 4) {
            for (int i = 0; i < (4 - (year + "").length()); i++) {
                sb.append("0");
            }
        }
        sb.append(year).append("-");
        if ((month + "").length() < 2) {
            sb.append("0");
        }
        sb.append(month).append("-");
        if ((day + "").length() < 2) {
            sb.append("0");
        }
        sb.append(day);
        return format(sb.toString()).startsWith(sb.toString()) ? true : false;
    }

    /**
     * 根据月份，日期，转换格式
     * 1,1 ==》 01-01
     * 10，20 ==》 10-20
     */
    public static String getFormatMonthDay(String month, String day) {
        if (StringUtils.isEmpty(month) || StringUtils.isEmpty(day)) {
            return "null";
        }
        String M = getFormatMonth(month);
        String D = getFormatMonth(day);
        return M + "-" + D;
    }


    /**
     * 获取传入时间所处周的周一凌晨时间
     */
    public static Date getMondayOfWeek(Date date) {
        LocalDateTime localDateTime = parse2LocalDateTime(date);
        LocalDateTime monday = localDateTime.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1).withHour(0).withMinute(0).withSecond(0);
        return parse(monday);
    }

    /**
     * 获取传入时间所处周的周一凌晨时间字符串(yyyy-MM-dd HH:mm:ss)
     */
    public static String getMondayOfWeekStr(Date date) {
        return format(getMondayOfWeek(date));
    }

    /**
     * 获取传入时间所处月的第一天的凌晨时间
     */
    public static Date getFirstDayOfMonth(Date date) {
        LocalDateTime localDateTime = parse2LocalDateTime(date);
        LocalDateTime monday = localDateTime.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0);
        return parse(monday);
    }

    /**
     * 获取传入时间所处月的第一天的凌晨时间(yyyy-MM-dd HH:mm:ss)
     */
    public static String getFirstDayOfMonthStr(Date date) {
        return format(getFirstDayOfMonth(date));
    }

    /**
     * 获取传入时间所处年的第一天的凌晨时间
     */
    public static Date getFirstDayOfYear(Date date) {
        LocalDateTime localDateTime = parse2LocalDateTime(date);
        LocalDateTime monday = localDateTime.with(TemporalAdjusters.firstDayOfYear()).withHour(0).withMinute(0).withSecond(0);
        return parse(monday);
    }

    /**
     * 获取传入时间所处年的第一天的凌晨时间(yyyy-MM-dd HH:mm:ss)
     */
    public static String getFirstDayOfYearStr(Date date) {
        return format(getFirstDayOfYear(date));
    }

    /**
     * 获得所传时间的凌晨时间
     */
    public static Date getEarlyMorningFromDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.of(parse2LocalDate(date), LocalTime.MIN);
        return parse(localDateTime);
    }

    /**
     * 获得所传时间的凌晨时间戳
     */
    public static Long getEarlyMorningFromDateStamp(Date date) {
        return LocalDateTime.of(parse2LocalDate(date), LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 获得所传时间的凌晨时间(yyyy-MM-dd HH:mm:ss)
     */
    public static String getEarlyMorningFromDateStr(Date date) {
        return format(getEarlyMorningFromDate(date));
    }

    /**
     * 获得当日凌晨时间
     */
    public static Date getTodayEarlyMorning() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return parse(localDateTime);
    }

    /**
     * 获得当日凌晨时间(yyyy-MM-dd HH:mm:ss)
     */
    public static String getTodayEarlyMorningStr() {
        return format(getTodayEarlyMorning());
    }


//	/**
//	 * 根据时间转换对应的cron时间格式：
//	 * 01:30 ==> 0 30 1 * * ?
//	 */
//	public static String getCronDate(String t){
//		if(StringUtils.isEmpty(t)){
//			return "null";
//		}
//		String[] strs = t.trim().split(":");
//		String tmp = "0";
//		for(String s:strs){
//			if(Integer.parseInt(s.substring(0,1))>0){
//				tmp += " ";
//				tmp += s;
//			}else{
//				tmp += " ";
//				tmp += s.substring(1,2);
//			}
//		}
//		tmp += " * * ?";
//		return tmp;
//	}

    /**
     * 判断某时间[targetTime]是否在一段时间区间内[date1,date2]
     */
    public static boolean containTime(Object date1, Object date2, Object targetTime) {
        boolean t1 = comTime(date1, targetTime);
        boolean t2 = comTime(date2, targetTime);

        if (!t1 && t2) {
            return true;
        }
        return false;
    }

    /**
     * 时间比较
     *
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean comTime(Object date1, Object date2) {
        Date d1 = parse(date1);
        Date d2 = parse(date2);
        long t = d1.getTime() - d2.getTime();
        if (t >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算两个时间戳的差额，用 [00:00:00] 格式表示结果
     *
     * @param startStamp
     * @param endStamp
     * @return
     */
    public static String timeDifference(long startStamp, long endStamp) {
        return formatTimeDifference(endStamp - startStamp);
    }

    public static String formatTimeDifference(long diff) {
        if (diff < 0) {
            return "00:00:00";
        }
        StringBuilder sb = new StringBuilder();
        long hour = diff / (1000 * 60 * 60);

        if (hour <= 0) {
            sb.append("00");
        } else if (hour < 10) {
            sb.append("0").append(hour);
        } else {
            sb.append(hour);
        }
        sb.append(":");
        long remaining = diff - hour * 1000 * 60 * 60;
        if (remaining > 0) {
            long minute = remaining / (1000 * 60);
            if (minute <= 0) {
                sb.append("00");
            } else if (minute < 10) {
                sb.append("0").append(minute);
            } else {
                sb.append(minute);
            }
            sb.append(":");
            remaining = remaining - minute * 1000 * 60;
            if (remaining > 0) {
                long sec = remaining / 1000;
                if (sec <= 0) {
                    sb.append("00");
                } else if (sec < 10) {
                    sb.append("0").append(sec);
                } else {
                    sb.append(sec);
                }
            } else {
                sb.append("00");
            }
        } else {
            sb.append("00:00");
        }
        return sb.toString();
    }

    /**
     * 以秒来格式化时间展示
     *
     * @param diffSec
     * @return
     */
    public static String formatTimeSecDifference(long diffSec) {
        if (diffSec < 0) {
            return "00:00:00";
        }
        StringBuilder sb = new StringBuilder();
        long hour = diffSec / (60 * 60);

        if (hour <= 0) {
            sb.append("00");
        } else if (hour < 10) {
            sb.append("0").append(hour);
        } else {
            sb.append(hour);
        }
        sb.append(":");
        long remaining = diffSec - hour * 60 * 60;
        if (remaining > 0) {
            long minute = remaining / (60);
            if (minute <= 0) {
                sb.append("00");
            } else if (minute < 10) {
                sb.append("0").append(minute);
            } else {
                sb.append(minute);
            }
            sb.append(":");
            remaining = remaining - minute * 60;
            if (remaining > 0) {
                long sec = remaining;
                if (sec <= 0) {
                    sb.append("00");
                } else if (sec < 10) {
                    sb.append("0").append(sec);
                } else {
                    sb.append(sec);
                }
            } else {
                sb.append("00");
            }
        } else {
            sb.append("00:00");
        }
        return sb.toString();
    }

    /**
     * 根据时间戳获取所在小时内的第几秒
     */
    public static long getSecondOfHour(Long timeStamp) {
        return (timeStamp % ONE_DAY_DATE_TIME_STAMP) % ONE_HOUR_DATE_TIME_STAMP / ONE_SECOND_DATE_TIME_STAMP;
    }

}
