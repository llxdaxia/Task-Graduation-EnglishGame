package cn.zhu.cainiao.app;

import android.app.Application;

import cn.alien95.resthttp.request.RestHttp;
import cn.alien95.util.Utils;
import cn.bmob.v3.Bmob;
import cn.zhu.cainiao.BuildConfig;

/**
 * Created by linlongxin on 2016/5/8.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.initialize(this);
        RestHttp.initialize(this);
        if (BuildConfig.DEBUG) {
            Utils.setDebug(true, "CaiNiao");
            RestHttp.setDebug(true, "Cainiao_network");
        }

        //7914417338839ca7521908cfecd1851c
        Bmob.initialize(this, "7914417338839ca7521908cfecd1851c");

    }
}
