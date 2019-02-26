package com.dongyangbike.app.dialog;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongyangbike.app.R;
import com.dongyangbike.app.util.AppUtils;


public class BaseDialog extends Dialog {
    private static final String TAG = "BaseDialog";
    private Button singleButton;

    private Button leftButton;

    private Button rightButton;

    public BaseDialog(Context context) {
        this(context, true);

    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public BaseDialog(Context context, boolean hasAnimation) {
        super(context, R.style.Theme_dialog);
    }

    protected void setTitle(String title) {
        TextView titleTV = (TextView) findViewById(R.id.dialog_title);
        if (titleTV != null && title != null) {
            titleTV.setVisibility(View.VISIBLE);
            titleTV.setText(title);
        }
    }

    public void setMessage(String message) {
        LinearLayout messageLayout = (LinearLayout) findViewById(R.id.dialog_message_layout);
        TextView messageTv = (TextView) findViewById(R.id.dialog_message);
        if (messageTv != null && message != null && !"".equals(message)) {
            messageTv.setText(message);
            if (messageLayout != null) {
                LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) messageLayout
                        .getLayoutParams();
                if (messageTv.getLineCount() == 1) {
                    param.setMargins(0, AppUtils.dip2px(getContext(), 41), 0, 0);
                } else {
                    param.setMargins(0, AppUtils.dip2px(getContext(), 30), 0, 0);
                }
                messageLayout.setLayoutParams(param);
            }
        }
    }

    protected void setMessage(int resId) {
        setMessage(getContext().getString(resId));
    }

    /**
     * 设置对话框左边确认按钮点击事件
     *
     * @param lsn 点击事件
     */
    public void setLeftButtonListener(final View.OnClickListener lsn) {
        setLeftButtonListener(lsn, true);
    }

    /**
     * 设置对话框左边按钮点击事件，同时设置是否先关闭对话框
     *
     * @param lsn          点击事件
     * @param dismissFirst 是否先关闭对话框
     */
    public void setLeftButtonListener(final View.OnClickListener lsn, boolean dismissFirst) {
        setButtonListener(getLeftButton(), lsn, dismissFirst);
    }

    /**
     * 设置对话框右边按钮点击事件
     *
     * @param lsn 点击事件
     */
    public void setRightButtonListener(final View.OnClickListener lsn) {
        setRightButtonListener(lsn, true);
    }

    /**
     * 设置对话框右边按钮点击事件，同时设置是否先关闭对话框
     *
     * @param lsn          点击事件
     * @param dismissFirst 是否先关闭对话框
     */
    public void setRightButtonListener(final View.OnClickListener lsn, boolean dismissFirst) {
        setButtonListener(getRightButton(), lsn, dismissFirst);
    }

    private void setButtonListener(Button button, final View.OnClickListener lsn, boolean dismissFirst) {
        if (button == null) {
            return;
        }

        if (dismissFirst) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (lsn != null) {
                        lsn.onClick(v);
                    }
                }
            });
        } else {
            button.setOnClickListener(lsn);
        }
    }

//    public void setOnItemClickListener(AdapterView.OnItemClickListener lsn)
//    {
//        getListView().setOnItemClickListener(lsn);
//    }

    public void setLeftBackgroundResource(int resId) {
        getLeftButton().setBackgroundResource(resId);
    }

    public void setRightBackgroundResource(int resId) {
        getRightButton().setBackgroundResource(resId);
    }

    public void setLeftText(String text) {
        getLeftButton().setText(text);
    }

    public void setLeftText(int resId) {
        getLeftButton().setText(resId);
    }

    public void setRightText(String text) {
        getRightButton().setText(text);
    }

    public void setRightText(int resId) {
        getRightButton().setText(resId);
    }

    /**
     * 设置字体颜色:
     *
     * @param color mContext.getResources().getColor()
     */
    public void setRightTextColor(int color) {

        getRightButton().setTextColor(color);

    }
//    public void setAdapter(BaseAdapter adapter)
//    {
//        getListView().setAdapter(adapter);
//    }
//
//    public void setSelection(int position)
//    {
//        getListView().setSelection(position);
//    }

    /**
     * 消失
     */
    public void dismiss() {
        if (isShowing()) {
            try {
                super.dismiss();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 显示
     */
    public void show() {
        if (!isShowing()) {
            try {
                super.show();
            } catch (Exception e) {
                Log.d(TAG, "show: exception=" + e.getMessage());
            }
        }
    }

    public Button getLeftButton() {
        if (leftButton == null) {
            leftButton = (Button) findViewById(R.id.dialog_leftbutton);
        }
        return leftButton;

    }

    public Button getRightButton() {
        if (rightButton == null) {
            rightButton = (Button) findViewById(R.id.dialog_rightbutton);

        }
        return rightButton;
    }

//    private ListView getListView()
//    {
//        if (listView == null)
//        {
//            listView = (ListView) findViewById(R.id.dialog_listview);
//        }
//        return listView;
//    }
}
