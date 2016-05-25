package cn.zhu.cainiao.module.user;

import android.os.Bundle;
import android.widget.TextView;

import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;

public class ExplainActivity extends BaseActivity {

    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_explain);
        setToolbarIsBack(true);

        content = (TextView) findViewById(R.id.content);

        content.setText(
                "-  进行闯关之前请先`英语学习`。\n" +
                        "-  “英语学习”每类别有10个单词。\n" +
                        "-  “闯关”每个关卡有10个单词。\n" +
                        "-  学习章节数大于等于通过的关卡数。\n" +
                        "-  根据通过关卡数封称呼。\n" +
                        "-  加油吧，这就是你的学神之路。");

    }
}
