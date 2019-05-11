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

