package com.dongyangbike.app.dialog;

/**
 * Created by Administrator on 2017/12/1.
 */

public interface DialogClickListener {

    /**
     * leftClick
     */
    void onLeftClick();

    /**
     * right or sigle Click
     */
    void onRightClick();

    /**
     * dismiss callback
     */
    void onDismiss();
}
