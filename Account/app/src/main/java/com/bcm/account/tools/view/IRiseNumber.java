package com.bcm.account.tools.view;

/**
 * 控制数字变动接口
 */
public interface IRiseNumber {
    /**
     * 开始
     */
    void start();

    /**
     * 设置浮点型数字
     *
     * @param number
     * @return
     */
    void withNumber(float number);

    /**
     * 设置整型数字
     *
     * @param number
     * @return
     */
    void withNumber(int number);

    /**
     * 设置整型数字范围
     *
     * @param fromNumber
     * @param endNumber
     */
    void setFromAndEndNumber(int fromNumber, int endNumber);

    /**
     * 设置浮点型数字范围
     *
     * @param fromNumber
     * @param endNumber
     */
    void setFromAndEndNumber(float fromNumber, float endNumber);

    /**
     * 设置动画时长
     *
     * @param duration
     * @return
     */
    void setDuration(long duration);

    /**
     * 设置动画结束监听
     *
     * @param callback
     */
    void setOnEndListener(NumberScrollTextView.EndListener callback);
}
