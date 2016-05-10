package cn.zhu.cainiao.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.alien95.util.Utils;
import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;
import cn.zhu.cainiao.config.Config;
import cn.zhu.cainiao.model.AccountModel;
import cn.zhu.cainiao.model.bean.User;
import cn.zhu.cainiao.module.adaper.StarAdapter;

/**
 * Created by linlongxin on 2016/5/9.
 */
public class LevelSetActivity extends BaseActivity {


    @BindView(R.id.exit)
    Button exit;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_set);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

        user = AccountModel.getInstance().getAccount();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerView.setAdapter(new StarAdapter(true, this, CheckpointsActivity.class));

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
