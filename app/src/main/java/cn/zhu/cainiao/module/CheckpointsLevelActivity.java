package cn.zhu.cainiao.module;

import android.content.Intent;
import android.os.Bundle;
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

public class CheckpointsLevelActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.enter)
    Button enter;

    private StarAdapter adapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpoints_level);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

        user = AccountModel.getInstance().getAccount();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerView.setAdapter(adapter = new StarAdapter(true, this, CheckpointsActivity.class));

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getStudyLevelNum() <= user.getPassLevelNum()) {
                    Utils.SnackbarShort(enter, "请先进行“英语学习”");
                    return;
                }
                Intent intent = new Intent(CheckpointsLevelActivity.this, CheckpointsActivity.class);
                intent.putExtra(Config.POSITION, user.getPassLevelNum() + 1);
                intent.putExtra(Config.IS_UPDATE_LEVEL, true);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Utils.Log("onRestart");
        adapter.updateData();
    }

}
