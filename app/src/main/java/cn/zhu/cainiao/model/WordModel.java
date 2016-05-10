package cn.zhu.cainiao.model;

import cn.alien95.util.Utils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.zhu.cainiao.model.bean.Word;

/**
 * Created by linlongxin on 2016/5/9.
 */
public class WordModel {

    private static WordModel instance;

    private WordModel(){

    }

    public static WordModel getInstance(){
        if(instance == null){
            instance = new WordModel();
        }
        return instance;
    }

    public void getAllWordData(FindListener<Word> findListener){
        BmobQuery<Word> wordQuery = new BmobQuery<>();
        wordQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 先从缓存获取数据，如果没有，再从网络获取。
        wordQuery.findObjects(Utils.getContext(), findListener);
    }
}
