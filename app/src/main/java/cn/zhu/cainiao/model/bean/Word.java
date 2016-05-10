package cn.zhu.cainiao.model.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by linlongxin on 2016/5/8.
 */
public class Word extends BmobObject{

    private String chinese;
    private String translate;  //单词英文翻译
    private String phoneticSymbol;  //音标
    private String imgUrl;  //图片
    private List<String> options;  //选项
    private Integer correctOption;  //正确选项ID

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    public void setPhoneticSymbol(String phoneticSymbol) {
        this.phoneticSymbol = phoneticSymbol;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Integer getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(Integer correctOption) {
        this.correctOption = correctOption;
    }
}
