package cn.zhu.cainiao.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
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


    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.pass_level_num)
    TextView passLevelNum;
    @BindView(R.id.study_level_num)
    TextView studyLevelNum;
    @BindView(R.id.exit)
    TextView exit;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

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
