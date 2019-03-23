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

import com.dongyangbike.app.http.ack.NearStationsAck;

public class SwitchParkEvent extends BaseEventBus{

    public NearStationsAck.Data.Row getData() {
        return data;
    }

    public void setData(NearStationsAck.Data.Row data) {
        this.data = data;
    }

    private NearStationsAck.Data.Row data;

    public SwitchParkEvent(NearStationsAck.Data.Row data) {
        this.data = data;
    }



}

