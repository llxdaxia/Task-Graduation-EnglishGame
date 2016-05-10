package cn.zhu.cainiao.model.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by linlongxin on 2016/5/8.
 */
public class User extends BmobUser{

    private Integer passLevelNum;
    private Integer studyLevelNum;

    public Integer getPassLevelNum() {
        return passLevelNum;
    }

    public void setPassLevelNum(Integer passLevelNum) {
        this.passLevelNum = passLevelNum;
    }

    public Integer getStudyLevelNum() {
        return studyLevelNum;
    }

    public void setStudyLevelNum(Integer studyLevelNum) {
        this.studyLevelNum = studyLevelNum;
    }
}
