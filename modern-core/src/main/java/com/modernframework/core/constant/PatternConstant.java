package com.modernframework.core.constant;

import com.modernframework.core.map.WeakConcurrentMap;

import java.util.regex.Pattern;

/**
 * 常用正则表达式集合，更多正则见:<br>
 * <a href="https://any86.github.io/any-rule/">https://any86.github.io/any-rule/</a>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @since 1.0.0
 */
public class PatternConstant {

    /**
     * 英文字母 、数字和下划线
     */
    public final static Pattern GENERAL = Pattern.compile(RegexConstant.GENERAL);
    /**
     * 数字
     */
    public final static Pattern NUMBERS = Pattern.compile(RegexConstant.NUMBERS);
    /**
     * 字母
     */
    public final static Pattern WORD = Pattern.compile(RegexConstant.WORD);
    /**
     * 单个中文汉字
     */
    public final static Pattern CHINESE = Pattern.compile(RegexConstant.CHINESE);
    /**
     * 中文汉字
     */
    public final static Pattern CHINESES = Pattern.compile(RegexConstant.CHINESES);
    /**
     * 分组
     */
    public final static Pattern GROUP_VAR = Pattern.compile(RegexConstant.GROUP_VAR);
    /**
     * IP v4
     */
    public final static Pattern IPV4 = Pattern.compile(RegexConstant.IPV4);
    /**
     * IP v6
     */
    public final static Pattern IPV6 = Pattern.compile(RegexConstant.IPV6);
    /**
     * 货币
     */
    public final static Pattern MONEY = Pattern.compile(RegexConstant.MONEY);
    /**
     * 邮件，符合RFC 5322规范，正则来自：<a href="http://emailregex.com/">http://emailregex.com/</a><br>
     * <a href="https://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address/44317754">https://stackoverflow.com/questions/386294/what-is-the-maximum-length-of-a-valid-email-address/44317754</a>
     * 注意email 要宽松一点。比如 jetz.chong@hutool.cn、jetz-chong@ hutool.cn、jetz_chong@hutool.cn、dazhi.duan@hutool.cn 宽松一点把，都算是正常的邮箱
     */
    public final static Pattern EMAIL = Pattern.compile(RegexConstant.EMAIL, Pattern.CASE_INSENSITIVE);
    /**
     * 移动电话
     */
    public final static Pattern MOBILE = Pattern.compile(RegexConstant.MOBILE);
    /**
     * 中国香港移动电话
     * eg: 中国香港： +852 5100 4810， 三位区域码+10位数字, 中国香港手机号码8位数
     * eg: 中国大陆： +86  180 4953 1399，2位区域码标示+13位数字
     * 中国大陆 +86 Mainland China
     * 中国香港 +852 Hong Kong
     * 中国澳门 +853 Macao
     * 中国台湾 +886 Taiwan
     */
    public final static Pattern MOBILE_HK = Pattern.compile(RegexConstant.MOBILE_HK);
    /**
     * 中国台湾移动电话
     * eg: 中国台湾： +886 09 60 000000， 三位区域码+号码以数字09开头 + 8位数字, 中国台湾手机号码10位数
     * 中国台湾 +886 Taiwan 国际域名缩写：TW
     */
    public final static Pattern MOBILE_TW = Pattern.compile(RegexConstant.MOBILE_TW);
    /**
     * 中国澳门移动电话
     * eg: 中国台湾： +853 68 00000， 三位区域码 +号码以数字6开头 + 7位数字, 中国台湾手机号码8位数
     * 中国澳门 +853 Macao 国际域名缩写：MO
     */
    public final static Pattern MOBILE_MO = Pattern.compile(RegexConstant.MOBILE_MO);
    /**
     * 座机号码
     */
    public final static Pattern TEL = Pattern.compile(RegexConstant.TEL);
    /**
     * 座机号码+400+800电话
     *
     * @see <a href="https://baike.baidu.com/item/800">800</a>
     */
    public final static Pattern TEL_400_800 = Pattern.compile(RegexConstant.TEL_400_800);
    /**
     * 18位身份证号码
     */
    public final static Pattern CITIZEN_ID = Pattern.compile(RegexConstant.CITIZEN_ID);
    /**
     * 邮编，兼容港澳台
     */
    public final static Pattern ZIP_CODE = Pattern.compile(RegexConstant.ZIP_CODE);
    /**
     * 生日
     */
    public final static Pattern BIRTHDAY = Pattern.compile(RegexConstant.BIRTHDAY);
    /**
     * URL
     */
    public final static Pattern URL = Pattern.compile(RegexConstant.URL);
    /**
     * Http URL
     */
    public final static Pattern URL_HTTP = Pattern.compile(RegexConstant.URL_HTTP, Pattern.CASE_INSENSITIVE);
    /**
     * 中文字、英文字母、数字和下划线
     */
    public final static Pattern GENERAL_WITH_CHINESE = Pattern.compile(RegexConstant.GENERAL_WITH_CHINESE);
    /**
     * UUID
     */
    public final static Pattern UUID = Pattern.compile(RegexConstant.UUID, Pattern.CASE_INSENSITIVE);
    /**
     * 不带横线的UUID
     */
    public final static Pattern UUID_SIMPLE = Pattern.compile(RegexConstant.UUID_SIMPLE);
    /**
     * MAC地址正则
     */
    public static final Pattern MAC_ADDRESS = Pattern.compile(RegexConstant.MAC_ADDRESS, Pattern.CASE_INSENSITIVE);
    /**
     * 16进制字符串
     */
    public static final Pattern HEX = Pattern.compile(RegexConstant.HEX);
    /**
     * 时间正则
     */
    public static final Pattern TIME = Pattern.compile("\\d{1,2}:\\d{1,2}(:\\d{1,2})?");
    /**
     * 中国车牌号码（兼容新能源车牌）
     */
    public final static Pattern PLATE_NUMBER = Pattern.compile(RegexConstant.PLATE_NUMBER);

    /**
     * 社会统一信用代码
     * <pre>
     * 第一部分：登记管理部门代码1位 (数字或大写英文字母)
     * 第二部分：机构类别代码1位 (数字或大写英文字母)
     * 第三部分：登记管理机关行政区划码6位 (数字)
     * 第四部分：主体标识码（组织机构代码）9位 (数字或大写英文字母)
     * 第五部分：校验码1位 (数字或大写英文字母)
     * </pre>
     */
    public static final Pattern CREDIT_CODE = Pattern.compile(RegexConstant.CREDIT_CODE);
    /**
     * 车架号
     * 别名：车辆识别代号 车辆识别码
     * eg:LDC613P23A1305189
     * eg:LSJA24U62JG269225
     * 十七位码、车架号
     * 车辆的唯一标示
     */
    public static final Pattern CAR_VIN = Pattern.compile(RegexConstant.CAR_VIN);
    /**
     * 驾驶证  别名：驾驶证档案编号、行驶证编号
     * eg:430101758218
     * 12位数字字符串
     * 仅限：中国驾驶证档案编号
     */
    public static final Pattern CAR_DRIVING_LICENCE = Pattern.compile(RegexConstant.CAR_DRIVING_LICENCE);
    /**
     * 中文姓名
     * 总结中国人姓名：2-60位，只能是中文和 ·
     */
    public static final Pattern CHINESE_NAME = Pattern.compile(RegexConstant.CHINESE_NAME);

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Pattern池
     */
    private static final WeakConcurrentMap<RegexWithFlag, Pattern> POOL = new WeakConcurrentMap<>();

    /**
     * 先从Pattern池中查找正则对应的{@link Pattern}，找不到则编译正则表达式并入池。
     *
     * @param regex 正则表达式
     * @return {@link Pattern}
     */
    public static Pattern get(String regex) {
        return get(regex, 0);
    }

    /**
     * 先从Pattern池中查找正则对应的{@link Pattern}，找不到则编译正则表达式并入池。
     *
     * @param regex 正则表达式
     * @param flags 正则标识位集合 {@link Pattern}
     * @return {@link Pattern}
     */
    public static Pattern get(String regex, int flags) {
        final RegexWithFlag regexWithFlag = new RegexWithFlag(regex, flags);
        return POOL.computeIfAbsent(regexWithFlag, (key) -> Pattern.compile(regex, flags));
    }

    /**
     * 移除缓存
     *
     * @param regex 正则
     * @param flags 标识
     * @return 移除的{@link Pattern}，可能为{@code null}
     */
    public static Pattern remove(String regex, int flags) {
        return POOL.remove(new RegexWithFlag(regex, flags));
    }

    /**
     * 清空缓存池
     */
    public static void clear() {
        POOL.clear();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------

    /**
     * 正则表达式和正则标识位的包装
     *
     * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
     */
    private static class RegexWithFlag {
        private final String regex;
        private final int flag;

        /**
         * 构造
         *
         * @param regex 正则
         * @param flag  标识
         */
        public RegexWithFlag(String regex, int flag) {
            this.regex = regex;
            this.flag = flag;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + flag;
            result = prime * result + ((regex == null) ? 0 : regex.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            RegexWithFlag other = (RegexWithFlag) obj;
            if (flag != other.flag) {
                return false;
            }
            if (regex == null) {
                return other.regex == null;
            } else {
                return regex.equals(other.regex);
            }
        }

    }
}