package cn.zhu.cainiao.module.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import cn.alien95.util.Utils;
import cn.bmob.v3.listener.FindListener;
import cn.zhu.cainiao.model.AccountModel;
import cn.zhu.cainiao.model.WordModel;
import cn.zhu.cainiao.model.bean.Word;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccountModel.getInstance().updateAccount();
        //获取所有单词数据并缓存到本地
        WordModel.getInstance().getAllWordData(new FindListener<Word>() {
            @Override
            public void onSuccess(List<Word> list) {
                Utils.Log("list.size : " + list.size());
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(int code, String s) {
                Utils.Log("code : " + code);
                if (code != 9009) {
                    Utils.Toast("获取数据失败");
                    startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
}
