package cn.zhu.cainiao.module.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
public class StudyLevelActivity extends BaseActivity {


    RecyclerView recyclerView;
    TextView enter;
    private StarAdapter adapter;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_activity_level);
        setToolbarIsBack(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        enter = (TextView) findViewById(R.id.enter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        user = AccountModel.getInstance().getAccount();
        recyclerView.setAdapter(adapter = new StarAdapter(false, this, StudyActivity.class));

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyLevelActivity.this, StudyActivity.class);
                intent.putExtra(Config.POSITION, user.getStudyLevelNum() + 1);
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
