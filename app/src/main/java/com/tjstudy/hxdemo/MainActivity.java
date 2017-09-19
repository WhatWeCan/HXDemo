package com.tjstudy.hxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hdl.elog.ELog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class MainActivity extends AppCompatActivity {

    private EditText mEtName;
    private EditText mEtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
    }

    public void onReg(View view) {
        String name = mEtName.getText().toString();
        String pwd = mEtPwd.getText().toString();

        try {
            //注册失败会抛出HyphenateException
            EMClient.getInstance().createAccount(name, pwd);//同步方法
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        } catch (HyphenateException e) {
            e.printStackTrace();
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLog(View view) {
        String name = mEtName.getText().toString();
        String pwd = mEtPwd.getText().toString();
        EMClient.getInstance().login(name, pwd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                ELog.e("main", "登录聊天服务器成功！");
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                ELog.e("main", "登录聊天服务器失败！");
                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onChat(View view) {

    }
}
