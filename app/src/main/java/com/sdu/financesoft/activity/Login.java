package com.sdu.financesoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sdu.financesoft.view.LocusPassWordView;
import com.sdu.financesoft.view.LocusPassWordView.OnCompleteListener;

public class Login extends Activity {
    private LocusPassWordView lpwv;
    private Toast toast;
    private static final String TAG = "Login";

    private void showToast(CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Log.i(TAG, "onCreate start!!!!");
        lpwv = (LocusPassWordView) this.findViewById(R.id.mLocusPassWordView);
        lpwv.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(String mPassword) {
                // 如果密码正确,则进入主页面。
                if (lpwv.verifyPassword(mPassword)) {
                    showToast("密码输入正确，即将登录主界面！");
                    Intent intent = new Intent(Login.this,
                            MainActivity.class);
                    // 打开新的Activity
                    startActivity(intent);
                    finish();
                } else {
                    showToast("密码错误，请重新输入！");
                    lpwv.markError();
                    lpwv.clearPassword();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart start!!!!");
        // 如果密码为空,则进入设置密码的界面
        Button noSetPassword = (Button) this.findViewById(R.id.tvNoSetPassword);
        TextView toastTv = (TextView) findViewById(R.id.login_toast);
        if (lpwv.isPasswordEmpty()) {
            lpwv.setVisibility(View.GONE);
            noSetPassword.setVisibility(View.VISIBLE);
            toastTv.setText(R.string.sethandpassword);
            noSetPassword.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this,
                            SetPasswordActivity.class);
                    // 打开新的Activity
                    startActivity(intent);
                    finish();
                }

            });
        } else {
            toastTv.setText(R.string.inputpassword);
            lpwv.setVisibility(View.VISIBLE);
            noSetPassword.setVisibility(View.GONE);
        }
    }

}
