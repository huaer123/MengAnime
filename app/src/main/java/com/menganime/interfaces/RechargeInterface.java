package com.menganime.interfaces;

/**
 * Created by Administrator on 2017/5/19.
 */

public interface RechargeInterface {
    /**
     * 开通vip-forSDK计费
     * @param json
     */
    void openVip(String json);

    /**
     * 支付宝开通vip
     * @param json
     */
    void alipay(String json);

    /**
     * 提交支付状态
     * @param json
     */
    void alipayStatus(String json);

}
