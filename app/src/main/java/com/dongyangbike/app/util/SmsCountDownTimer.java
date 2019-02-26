package com.dongyangbike.app.util;

import android.os.CountDownTimer;

public class SmsCountDownTimer extends CountDownTimer {

    public static long curMills = 0;
    public static boolean FLAG_FIRST_IN = true;

    public SmsCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long l) {

    }

    @Override
    public void onFinish() {

    }

    public void timeStart(boolean onClick) {
        if(onClick) {
            SmsCountDownTimer.curMills = System.currentTimeMillis();
        }
        SmsCountDownTimer.FLAG_FIRST_IN = false;
        this.start();
    }
}
