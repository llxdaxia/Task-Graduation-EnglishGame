package cn.zhu.cainiao.model;

import java.util.List;

import cn.alien95.util.Utils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.zhu.cainiao.config.Config;
import cn.zhu.cainiao.model.bean.User;

/**
 * Created by linlongxin on 2016/5/8.
 */
public class AccountModel {

    private static AccountModel instance;
    private User account;

    private AccountModel() {

    }

    public static AccountModel getInstance() {
        if (instance == null) {
            instance = new AccountModel();
        }
        return instance;
    }

    public void login(String name,String password, LogInListener<User> logInListener) {
        BmobUser.loginByAccount(Utils.getContext(), name, password, logInListener);
    }

    public void register(String name, String password, SaveListener saveListener) {
        User account = new User();
        account.setUsername(name);
        account.setPassword(password);
        account.setPassLevelNum(0);
        account.setStudyLevelNum(0);
        account.signUp(Utils.getContext(), saveListener);
    }

    public User getAccount() {
        if (account == null) {
            account = (User) Utils.readObjectFromFile(Config.USER_FILE);
        }
        return account;
    }

    public void clearAccount() {
        account = null;
    }

    public void updateCheckpointLevel(int level) {
        account.setPassLevelNum(level);
        User newUser = new User();
        newUser.setPassLevelNum(level);
        BmobUser bmobUser = User.getCurrentUser(Utils.getContext());
        newUser.update(Utils.getContext(), bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Utils.writeObjectToFile(account, Config.USER_FILE);
            }

            @Override
            public void onFailure(int code, String msg) {
                Utils.Log("更新闯关数失败：" + msg);
            }
        });
    }

    public void updateStudyLevel(int level) {
        account.setStudyLevelNum(level);
        User newUser = new User();
        newUser.setStudyLevelNum(level);
        BmobUser bmobUser = User.getCurrentUser(Utils.getContext());
        newUser.update(Utils.getContext(), bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Utils.writeObjectToFile(account, Config.USER_FILE);
            }

            @Override
            public void onFailure(int code, String msg) {
                Utils.Log("更新闯关数失败：" + msg);
            }
        });
    }

    public void updateAccount() {
        if (getAccount() == null) {
            Utils.Log("用户信息 == null");
            return;
        }
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", account.getUsername());
        query.setLimit(1);
        query.findObjects(Utils.getContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> data) {
                Utils.writeObjectToFile(data.get(0), Config.USER_FILE);
            }

            @Override
            public void onError(int code, String msg) {
                Utils.Log("获取用户信息失败：" + msg);
            }
        });
    }
}
