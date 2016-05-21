package cn.zhu.cainiao.module.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.alien95.util.Utils;
import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;
import cn.zhu.cainiao.config.Config;
import cn.zhu.cainiao.model.AccountModel;
import cn.zhu.cainiao.model.bean.User;

/**
 * Created by linlongxin on 2016/5/9.
 */
public class SetActivity extends BaseActivity {


    TextView userName;
    TextView passLevelNum;
    TextView studyLevelNum;
    TextView exit;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_set);
        setToolbarIsBack(true);

        userName = (TextView) findViewById(R.id.user_name);
        passLevelNum = (TextView) findViewById(R.id.pass_level_num);
        studyLevelNum = (TextView) findViewById(R.id.study_level_num);
        exit = (TextView) findViewById(R.id.exit);

        user = AccountModel.getInstance().getAccount();

        userName.setText("用户名：" + user.getUsername());
        passLevelNum.setText("通过关卡数：" + user.getPassLevelNum());
        studyLevelNum.setText("学习章节数：" + user.getStudyLevelNum());

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.USER_FILE.delete()) {
                    Utils.Toast("已退出");
                    AccountModel.getInstance().clearAccount();
                    finish();
                } else {
                    Utils.SnackbarShort(exit, "退出失败");
                }
            }
        });


    }


}
