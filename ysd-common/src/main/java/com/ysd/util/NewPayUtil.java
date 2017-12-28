package com.ysd.util;

import com.qmd.util.SerialNumberUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xishengchun on 2017-09-20.
 */
public class NewPayUtil {
    /**
     * 投标
     * @param investUserId
     * @param borrowId
     * @return
     */
    public static String doInvestBorrow(Integer investUserId, Integer borrowId, String payUrl) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("investUserId", String.valueOf(investUserId));
        reqMap.put("borrowId", String.valueOf(borrowId));
        reqMap.put("orderNo", SerialNumberUtil.buildPaymentSn());
        String msg = HttpUtil.post(payUrl + "/borrow/invest", reqMap);
        return msg;
    }

    /**
     * 投标撤销
     * @param investUserId
     * @param borrowId
     * @param payUrl
     * @return
     */
    public static String doCancelInvestBorrow(Integer investUserId, Integer borrowId, String payUrl) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("investUserId", String.valueOf(investUserId));
        reqMap.put("borrowId", String.valueOf(borrowId));
        reqMap.put("orderNo", SerialNumberUtil.buildPaymentSn());
        String msg = HttpUtil.post(payUrl + "/borrow/invest/cancel", reqMap);
        return msg;
    }


    /**
     * 还款
     * @param borrowRepaymentDetailId
     * @param borrowId
     * @param payUrl
     * @return
     */
    public static String doRepaymentBorrow(Integer borrowRepaymentDetailId, Integer borrowId, String payUrl) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("borrowRepaymentDetailId", String.valueOf(borrowRepaymentDetailId));
        reqMap.put("borrowId", String.valueOf(borrowId));
        reqMap.put("orderNo", SerialNumberUtil.buildPaymentSn());
        String msg = HttpUtil.post(payUrl + "/borrow/repayment", reqMap);
        return msg;
    }

    /**
     * 放款
     * @param borrowId
     * @param payUrl
     * @return
     */
    public static String doReleaseBorrow(Integer borrowId, String payUrl) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("borrowId", String.valueOf(borrowId));
        reqMap.put("orderNo", SerialNumberUtil.buildPaymentSn());
        String msg = HttpUtil.post(payUrl + "/borrow/release", reqMap);
        return msg;
    }


}
