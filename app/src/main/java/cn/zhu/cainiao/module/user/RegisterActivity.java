package cn.zhu.cainiao.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import cn.alien95.util.Utils;
import cn.bmob.v3.listener.SaveListener;
import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;
import cn.zhu.cainiao.model.AccountModel;

/**
 * Created by linlongxin on 2016/5/8.
 */
public class RegisterActivity extends BaseActivity {

    TextInputLayout userName;
    TextInputLayout password;
    Button register;

    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_register);
        setToolbarIsBack(true);

        userName = (TextInputLayout) findViewById(R.id.user_name);
        password = (TextInputLayout) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.closeInputMethod(RegisterActivity.this);
                final String name = userName.getEditText().getText().toString();
                final String pw = password.getEditText().getText().toString();
                if (name.isEmpty() || pw.isEmpty()) {
                    Utils.SnackbarShort(userName, "用户名或密码不能为空");
                } else {
                    AccountModel.getInstance().register(name, pw, new SaveListener() {

                        @Override
                        public void onSuccess() {
                            Utils.Toast("注册成功");
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra(USER_NAME, name);
                            intent.putExtra(PASSWORD, pw);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(int code, String arg0) {
                            Utils.Log("code : " + code + " arg : " + arg0);
                            if (code == 9010 || code == 9016) {
                                Utils.SnackbarShort(register, "网络错误，请检查你的网络");
                            } else
                                Utils.SnackbarShort(register, "用户名重复");
                        }
                    });
                }
            }
        });


    }
}
