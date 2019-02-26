package com.dongyangbike.app.dialog;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.dongyangbike.app.R;
import com.dongyangbike.app.util.AppUtils;


public class ConfirmTitleDialog extends BaseDialog {

    /**
     * 旧布局构造方法集
     */
    public ConfirmTitleDialog(Context context, int titleResId, int messageResId) {
        super(context);
        setContentView(R.layout.layout_public_base_dialog);
        setTitle(context.getString(titleResId));
        setMessage(messageResId);
        setCanceledOnTouchOutside(false);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    public ConfirmTitleDialog(Context context, int titleResId, String message) {
        super(context);
        setContentView(R.layout.layout_public_base_dialog);
        setTitle(context.getString(titleResId));
        setMessage(message);
        setCanceledOnTouchOutside(false);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    public ConfirmTitleDialog(Context context, int titleResId, String message, int rightBtnText) {
        super(context);
        setContentView(R.layout.layout_public_base_dialog);
        setTitle(context.getString(titleResId));
        ViewGroup.LayoutParams param = getRightButton().getLayoutParams();
        param.width = AppUtils.dip2px(context, 190);
        getRightButton().setLayoutParams(param);
        setMessage(message);
        setCanceledOnTouchOutside(false);
        getLeftButton().setVisibility(View.GONE);
        setRightText(rightBtnText);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }
    public ConfirmTitleDialog(Context context, int titleResId, String message, int leftBtnText, int rightBtnText) {
        super(context);
        setContentView(R.layout.layout_public_base_dialog);
        setTitle(context.getString(titleResId));
        setMessage(message);
        setCanceledOnTouchOutside(false);
        setLeftText(leftBtnText);
        setRightText(rightBtnText);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    /**
     * 新布局构造方法集
     */
    public ConfirmTitleDialog(Context context, int messageResId) {
        super(context);
        setContentView(R.layout.layout_public_base_dialog);
        setMessage(messageResId);
        setCanceledOnTouchOutside(false);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    public ConfirmTitleDialog(Context context, String message) {
        super(context);
        setContentView(R.layout.layout_public_base_dialog);
        setMessage(message);
        setCanceledOnTouchOutside(false);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    public ConfirmTitleDialog(Context context, String message, int rightBtnText) {
        super(context);
        setContentView(R.layout.layout_public_base_dialog);
        ViewGroup.LayoutParams param = getRightButton().getLayoutParams();
        param.width = AppUtils.dip2px(context, 182);
        getRightButton().setLayoutParams(param);
        setMessage(message);
        setCanceledOnTouchOutside(false);
        getLeftButton().setVisibility(View.GONE);
        setRightText(rightBtnText);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    public ConfirmTitleDialog(Context context, String message, int leftBtnText, int rightBtnText) {
        super(context);
        setContentView(R.layout.layout_public_base_dialog);
        setMessage(message);
        setCanceledOnTouchOutside(false);
        setLeftText(leftBtnText);
        setRightText(rightBtnText);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    public ConfirmTitleDialog(Context context, int message, int leftBtnText, int rightBtnText) {
        super(context);
        setContentView(R.layout.layout_public_base_dialog);
        setMessage(context.getString(message));
        setCanceledOnTouchOutside(false);
        setLeftText(leftBtnText);
        setRightText(rightBtnText);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    public ConfirmTitleDialog(Context context, int layoutResId, String title, String message, int leftBtnText, int rightBtnText) {
        super(context);
        setContentView(layoutResId);
        setCancelable(false);
        setMessage(message);
        setTitle(title);
        setLeftText(leftBtnText);
        setRightText(rightBtnText);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }
}
