package cn.zhu.cainiao.config;

import java.io.File;

import cn.alien95.util.Utils;

/**
 * Created by linlongxin on 2016/5/8.
 */
public class Config {

    private static final String USER_INFO = "user_info";
    public static File CACHE_DIR = Utils.getCacheDir();
    public static File USER_FILE = new File(CACHE_DIR, USER_INFO);


    //一些键值对的key，或者Request Code或Result Code
    public static final String POSITION = "POSITION";
    public static final String IS_UPDATE_LEVEL = "IS_UPDATE_LEVEL";
}
