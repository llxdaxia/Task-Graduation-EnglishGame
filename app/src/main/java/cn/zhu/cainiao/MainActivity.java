package cn.zhu.cainiao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.alien95.util.Utils;
import cn.zhu.cainiao.app.BaseActivity;
import cn.zhu.cainiao.config.Config;
import cn.zhu.cainiao.module.CheckpointsLevelActivity;
import cn.zhu.cainiao.module.LevelSetActivity;
import cn.zhu.cainiao.module.LoginActivity;
import cn.zhu.cainiao.module.StudyLevelActivity;
import cn.zhu.cainiao.module.TaskExplainActivity;

public class MainActivity extends BaseActivity {

    public static final int REQUEST_CODE_CAMERA = 100;
    @BindView(R.id.start_checkpoints)
    TextView startCheckpoints;
    @BindView(R.id.study)
    TextView study;
    @BindView(R.id.level_set)
    TextView levelSet;
    @BindView(R.id.task_explain)
    TextView taskExplain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbarIsBack(false);
        setTitle("菜鸟");

        startCheckpoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CheckpointsLevelActivity.class);
            }
        });

        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StudyLevelActivity.class);
            }
        });

        levelSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LevelSetActivity.class);
            }
        });

        taskExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TaskExplainActivity.class));
            }
        });

    }

    public void startActivity(Class activity) {
        if (Utils.readObjectFromFile(Config.USER_FILE) == null) {
            Utils.Toast("请登录");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, activity));
        }
    }
}
