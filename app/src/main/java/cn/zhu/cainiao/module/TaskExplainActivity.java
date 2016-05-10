package cn.zhu.cainiao.module;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;

public class TaskExplainActivity extends BaseActivity {

    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.author)
    TextView author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_explain);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

        content.setText("-  在进行闯关之前必须先进入`英语学习`\n" +
                "-  “英语学习”每章节有10个单词\n" +
                "-  “闯关”每个关卡有10个单词\n" +
                "-  学习过的章节数始终小于等于通过的关卡数\n" +
                "-  加油吧！孩子");

        author.setText("姓名：朱虹春\n" +
                "学校：重庆邮电大学\n\n" +
                "学号：2012211124\n" +
                "学院：经济管理学院\n" +
                "专业：信息管理与信息系统\n" +
                "希望大家喜欢！");
    }
}
