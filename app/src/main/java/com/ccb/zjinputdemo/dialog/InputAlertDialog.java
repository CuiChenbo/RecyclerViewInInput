package com.ccb.zjinputdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccb.zjinputdemo.R;
import com.ccb.zjinputdemo.utils.ViewUtils;


/**
 * 蓝牙的的提示Dialog -- 支持全部的蓝牙弹窗样式
 */
public class InputAlertDialog {

    private Context mC;
    private TextView tvTitle;
    private EditText et;
    private Button btn;
    private Dialog dialog;
    public InputAlertDialog(Context context){
        mC = context;
    }

    public InputAlertDialog bunlid(){
        View view = LayoutInflater.from(mC).inflate(
                R.layout.view_input_alert_dialog, null);
        FrameLayout cardView = view.findViewById(R.id.rootView);
        tvTitle = view.findViewById(R.id.tvTitle);
        et = view.findViewById(R.id.et);
        btn = view.findViewById(R.id.btn);
        dialog = new Dialog(mC, R.style.DialogorKeyboard);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false); //点击外部不让消失
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attr = window.getAttributes();
        attr.width = (ViewUtils.getScreenWidth(mC));
        attr.height = dp2px(mC,100);
        attr.gravity = Gravity.BOTTOM;
        window.setAttributes(attr);
        return this;
    }

    public InputAlertDialog setTitleText(CharSequence text){
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(text);
        return this;
    }

    public InputAlertDialog setSubmitClickList(View.OnClickListener clickList){
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(clickList);
        return this;
    }

    public InputAlertDialog setInputText(String text){
        et.setText(text);
        et.setSelection(et.getText().length()); //光标移动到最后
        return this;
    }

    public String getInputText(){
        return et.getText().toString().trim();
    }



    public void show() {
        if (et != null &&  dialog!= null){
        et.requestFocus();
        dialog.show();}
    }

    public void  dissmiss(){
        if(dialog!=null&&dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    /**
     * dip转换px
     */
    public int dp2px(Context content, int dip) {
        final float scale = content.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */

    public int px2dp(Context content, int px) {
        final float scale = content.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
