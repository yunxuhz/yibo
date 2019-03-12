/*
 * Project: KomectWalking
 * 
 * File Created at 2016-5-16
 * 
 * Copyright 2016 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
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
