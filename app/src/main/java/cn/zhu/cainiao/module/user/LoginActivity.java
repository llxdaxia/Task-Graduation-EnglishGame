package cn.zhu.cainiao.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import cn.alien95.util.Utils;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;
import cn.zhu.cainiao.config.Config;
import cn.zhu.cainiao.model.AccountModel;
import cn.zhu.cainiao.model.bean.User;

/**
 * Created by linlongxin on 2016/5/8.
 */
public class LoginActivity extends BaseActivity {

    TextInputLayout userName;
    TextInputLayout password;
    Button register;
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_login);
        setToolbarIsBack(true);

        userName = (TextInputLayout) findViewById(R.id.user_name);
        password = (TextInputLayout) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);

        String name = getIntent().getStringExtra(RegisterActivity.USER_NAME);
        String pw = getIntent().getStringExtra(RegisterActivity.PASSWORD);

        if (name != null && pw != null) {
            userName.getEditText().setText(name);
            password.getEditText().setText(pw);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.closeInputMethod(LoginActivity.this);
                final String name = userName.getEditText().getText().toString();
                final String pw = password.getEditText().getText().toString();
                if (name.isEmpty() || pw.isEmpty()) {
                    Utils.SnackbarShort(userName, "用户名或密码不能为空");
                } else {
                    AccountModel.getInstance().login(name, pw, new LogInListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (user != null) {
                                Utils.writeObjectToFile(user, Config.USER_FILE);
                                Utils.Toast("登录成功");
                                finish();
                            } else {
                                Utils.Log("error-code : " + e.getErrorCode());
                                if (e.getErrorCode() == 9010 || e.getErrorCode() == 9016) {
                                    Utils.SnackbarShort(userName, "网络错误，请检查你的网络");
                                } else {
                                    Utils.SnackbarShort(userName, "用户名或密码错误");
                                }
                            }
                        }
                    });
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }
}
