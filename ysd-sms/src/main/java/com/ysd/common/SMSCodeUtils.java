package com.ysd.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xishengchun on 15/9/1.
 */
public class SMSCodeUtils {

    private static final String RANGE = "1234567890";

    private static final int RANGE_LENGTH = RANGE.length();

    private static final int SMS_CODE_LENGTH = 4;

    public static String genSMSCode() {
        return genSMSCode(SMS_CODE_LENGTH);
    }

    private static String genSMSCode(int codeLength) {
        String retStr = "";
        boolean bDone = true;
        if (codeLength < 0) {
            codeLength = SMS_CODE_LENGTH;
        }
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < codeLength; i++) {
                double dblR = Math.random() * RANGE_LENGTH;
                int intR = (int) Math.floor(dblR);
                char c = RANGE.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += RANGE.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    public static String genSMSCode6() {
        return genSMSCode(6);
    }

    /**
     * 验证手机号码
     * @param mobile
     * @return
     */
    public static boolean validMobile(String mobile) {
        Pattern pattern = Pattern.compile("^[1-9]{1}[0-9]{10}$");
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

}
