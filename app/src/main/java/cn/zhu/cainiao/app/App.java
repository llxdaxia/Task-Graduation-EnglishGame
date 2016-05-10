package cn.zhu.cainiao.app;

import android.app.Application;

import java.util.List;

import cn.alien95.resthttp.request.Http;
import cn.alien95.util.Utils;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.FindListener;
import cn.zhu.cainiao.BuildConfig;
import cn.zhu.cainiao.model.AccountModel;
import cn.zhu.cainiao.model.WordModel;
import cn.zhu.cainiao.model.bean.Word;

/**
 * Created by linlongxin on 2016/5/8.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.initialize(this);
        if (BuildConfig.DEBUG) {
            Utils.setDebug(true, "CaiNiao");
        }

        Http.initialize(this);
        Bmob.initialize(this, "7914417338839ca7521908cfecd1851c");

        //获取所有单词数据并缓存到本地
        WordModel.getInstance().getAllWordData(new FindListener<Word>() {
            @Override
            public void onSuccess(List<Word> list) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });

        AccountModel.getInstance().updateAccount();
    }
}
