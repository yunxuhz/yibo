package com.dongyangbike.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dongyangbike.app.R;


/**
 * 提示的对话框
 *
 */
public class HintDialog extends Dialog implements View.OnClickListener {


    private String hint;
    private String hint_cancel;
    private String hint_determine;
    private TextView tv_hint;
    private Button tv_determine;
    private Button tv_cancel;
    HintDialogOnclickListener listener;


    public TextView getTv_hint() {
        return tv_hint;
    }

    public TextView getTv_determine() {
        return tv_determine;
    }

    public TextView getTv_cancel() {
        return tv_cancel;
    }
    /**
     * 改变两个button文字的构造
     */
    public HintDialog(Context context, String hint, String hint_cancel, String hint_determine, HintDialogOnclickListener listener) {
        super(context, R.style.common_dialog);
        this.hint=hint;
        this.listener=listener;
        this.hint_cancel=hint_cancel;
        this.hint_determine=hint_determine;
    }

    /**
     * 只有提示的构造
     * @param context
     * @param hint
     * @param listener
     */
    public HintDialog(Context context, String hint, HintDialogOnclickListener listener) {
        super(context, R.style.common_dialog);
        this.hint=hint;
        this.listener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hint);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        tv_cancel = (Button) findViewById(R.id.dialog_leftbutton);
        tv_determine = (Button) findViewById(R.id.dialog_rightbutton);
        if (!TextUtils.isEmpty(hint_cancel)){
            tv_cancel.setText(hint_cancel);
        }

        if (!TextUtils.isEmpty(hint_determine)){
            tv_determine.setText(hint_determine);
        }
        tv_hint.setText(hint);
        tv_cancel.setOnClickListener(this);
        tv_determine.setOnClickListener(this);

    }







    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_leftbutton:
                if (listener!= null){
                    listener.onCancelClick();
                }
                break;

            case R.id.dialog_rightbutton:
                if (listener!= null){
                    listener.onDetermineClick();
                }
                break;

                default:
                    break;
        }
        //点击之后都要取消
        dismiss();
    }


    public interface HintDialogOnclickListener{
        //取消的点击
         void onCancelClick();
        //确定的点击
         void onDetermineClick();
    }
}
