package com.blackbread.security.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 除去空格 方法描述：
     * 
     * 作者： yingzi 完成时间： May 24, 2013 1:16:30 PM 维护人员： yingzi 维护时间： May 24, 2013
     * 1:16:30 PM 维护原因: 当前版本： v3.0
     * 
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    //字符串是否为空
    public static boolean isEmpty(String value) {
        value = replaceBlank(value);
        if ("".equals(value)) {
            return true;
        }
        return false;
    }

    /**
     * 全角字符转成半角字符 方法描述：
     * 
     * 作者： yingzi 完成时间： May 24, 2013 1:16:49 PM 维护人员： yingzi 维护时间： May 24, 2013
     * 1:16:49 PM 维护原因: 当前版本： v3.0
     * 
     * @param input
     * @return
     */
    public static String replaceDBC2SBC(String input) {
        if (input == null)
            return "";
        Pattern pattern = Pattern.compile("[\u3000\uff01-\uff5f]{1}");
        Matcher m = pattern.matcher(input);
        StringBuffer s = new StringBuffer();
        while (m.find()) {
            char c = m.group(0).charAt(0);
            char replacedChar = c == '　' ? ' ' : (char) (c - 0xfee0);
            m.appendReplacement(s, String.valueOf(replacedChar));
        }
        m.appendTail(s);
        return s.toString();
    }

    /**
     * 对于特殊标点的处理('',‘’)
     * 方法描述：
     *
     * 作者： yingzi
     * 完成时间： Jun 6, 2013 2:02:15 PM
     * @param input
     * @return
     */
    public static String replacePoint2Blank(String input) {
        String dest = "";
        if (input != null) {
            Pattern p = Pattern.compile("[']");
            Matcher m = p.matcher(input);
            dest = m.replaceAll("");
            dest = dest.replace("‘", "").replace("’", "");
        }
        return dest;
    }

    /**
     * 
     * 方法描述：格式化字符串
     *
     * 作者： yingzi
     * 完成时间： Jun 6, 2013 2:09:16 PM
     * @param input
     * @return
     */
    public static String formatStr(String input) {
        String dest = "";
        if (input != null) {
            //全角半角转换
            dest = replaceDBC2SBC(input);
            //特殊标点处理
            dest = replacePoint2Blank(dest);
            //空格,换行符处理
            dest = replaceBlank(dest);
        }
        return dest;
    }

    public static final String full2HalfChange(String QJstr) throws UnsupportedEncodingException {
        StringBuffer outStrBuf = new StringBuffer("");
        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < QJstr.length(); i++) {
            Tstr = QJstr.substring(i, i + 1);
            // 全角空格转换成半角空格  
            if (Tstr.equals("　")) {
                outStrBuf.append(" ");
                continue;
            }
            b = Tstr.getBytes("unicode");
            // 得到 unicode 字节数据  
            if (b[2] == -1) {
                b[3] = (byte) (b[3] + 32);
                b[2] = 0;
                outStrBuf.append(new String(b, "unicode"));
            } else {
                outStrBuf.append(Tstr);
            }
        }
        return outStrBuf.toString();
    }

    public static boolean isContainChinese(String str) {
        if (str == null || "".equals(str))
            return false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
