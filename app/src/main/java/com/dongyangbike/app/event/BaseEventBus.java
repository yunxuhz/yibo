package com.dongyangbike.app.event;

import org.greenrobot.eventbus.EventBus;

public class BaseEventBus {
    /**
    * Gets eventname.
    *
    * @return the eventname
    */
    public String getEventname() {
        return this.getClass().getName();
    }

    /**
     * 发送该事件
     */
    public void post() {
        EventBus.getDefault().post(this);
    }

}

/**
 * Revision history
 * -------------------------------------------------------------------------
 * <p/>
 * Date Author Note
 * -------------------------------------------------------------------------
 * 2016-5-16 wangyulong creat
 */
