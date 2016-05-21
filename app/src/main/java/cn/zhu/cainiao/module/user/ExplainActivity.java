package cn.zhu.cainiao.module.user;

import android.os.Bundle;
import android.widget.TextView;

import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;

public class ExplainActivity extends BaseActivity {

    TextView content;
    TextView author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_explain);
        setToolbarIsBack(true);

        content = (TextView) findViewById(R.id.content);
        author = (TextView) findViewById(R.id.author);

        content.setText("-  在进行闯关之前必须先进入`英语学习`\n" +
                "-  “英语学习”每章节有10个单词\n" +
                "-  “闯关”每个关卡有10个单词\n" +
                "-  学习过的章节数始终小于等于通过的关卡数\n" +
                "-  加油吧！孩子");

        author.setText("姓名：林龙鑫，周建国，杨程\n" +
                "学校：重庆邮电大学\n" +
                "学号：2013211466\n" +

                "学院：计算机科学与技术学院\n" +
                "专业：计算机科学与技术专业\n");
    }
}
