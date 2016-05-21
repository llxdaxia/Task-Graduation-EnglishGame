package cn.zhu.cainiao.module.launcher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import cn.alien95.util.Utils;
import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;
import cn.zhu.cainiao.config.Config;
import cn.zhu.cainiao.module.points.CheckpointsLevelActivity;
import cn.zhu.cainiao.module.user.LoginActivity;
import cn.zhu.cainiao.module.user.SetActivity;
import cn.zhu.cainiao.module.study.StudyLevelActivity;
import cn.zhu.cainiao.module.user.TaskExplainActivity;

public class MainActivity extends BaseActivity {

    TextView startCheckpoints;
    TextView study;
    TextView levelSet;
    TextView taskExplain;

    private final int PERMISSION_REQUEST_CODE = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity_main);
        ButterKnife.bind(this);
        setToolbarIsBack(false);

        startCheckpoints = (TextView) findViewById(R.id.start_checkpoints);
        study = (TextView) findViewById(R.id.study);
        levelSet = (TextView) findViewById(R.id.level_set);
        taskExplain = (TextView) findViewById(R.id.task_explain);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }

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
                startActivity(SetActivity.class);
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
