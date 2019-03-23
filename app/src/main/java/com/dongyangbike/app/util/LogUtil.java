package com.dongyangbike.app.util;

import android.util.Log;

import java.util.Locale;

/**
 * Created by xuyun on 2016/11/11.
 */
public class LogUtil {

    private static boolean LOGV = true;
    private static boolean LOGD = true;
    private static boolean LOGI = true;
    private static boolean LOGW = true;
    private static boolean LOGE = true;

    /**
     * V.
     *
     * @param mess the mess
     */
    public static void v(String mess) {
        if (LOGV) {
            Log.e(getTag(), buildMessage(mess));
        }
    }

    /**
     * D.
     *
     * @param mess the mess
     */
    public static void d(String mess) {
        if (LOGD) {
            Log.i(getTag(), buildMessage(mess));
        }
    }

    /**
     * .
     *
     * @param mess the mess
     */
    public static void i(String mess) {
        if (LOGI) {
            Log.i(getTag(), buildMessage(mess));
        }
    }

    /**
     * W.
     *
     * @param mess the mess
     */
    public static void w(String mess) {
        if (LOGW) {
            Log.w(getTag(), buildMessage(mess));
        }
    }

    /**
     * E.
     *
     * @param mess the mess
     */
    public static void e(String mess) {
        if (LOGE) {
            Log.e(getTag(), buildMessage(mess));
        }
    }

    /**
     *
     * @param obj
     * @param objs
     */
    public static void printList(Object obj, Object[] objs) {
        if (LOGD) {
            if (objs != null) {
                int len = objs.length;
                String mesg = "";

                for (int i = 0; i < len; ++i) {
                    int j = i + 1;
                    mesg = mesg + "[" + j + "." + objs[i].getClass().getSimpleName() + "] ";
                }

                if (obj instanceof String) {
                    Log.i((String) obj, buildMessage(mesg));
                } else {
                    Log.d(obj.getClass().getSimpleName(), mesg);
                }
            }
        }
    }

    public static String getTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                break;
            }
        }
        return callingClass;
    }

    /**
     * buildMessage
     */
    public static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                caller = trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }
}
