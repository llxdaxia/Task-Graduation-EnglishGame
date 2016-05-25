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

    public static final String[] SUBHEAD_TITLE = new String[]{
            "水果", "人体器官", "色彩", "称呼", "学习用品", "衣物", "天气", "食品", "运动", "动物"
    };

    public static final String[] STUDY_LEVEL = new String[]{
            "学渣", "学屌", "学霸", "学神", "小神童"
    };
}
