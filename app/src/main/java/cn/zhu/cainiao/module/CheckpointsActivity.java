package cn.zhu.cainiao.module;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.alien95.resthttp.view.HttpImageView;
import cn.alien95.util.Utils;
import cn.bmob.v3.listener.FindListener;
import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;
import cn.zhu.cainiao.model.AccountModel;
import cn.zhu.cainiao.model.WordModel;
import cn.zhu.cainiao.model.bean.Word;

/**
 * Created by linlongxin on 2016/5/8.
 */
public class CheckpointsActivity extends BaseActivity {

    @BindView(R.id.word_image)
    HttpImageView wordImage;
    @BindView(R.id.A)
    TextView A;
    @BindView(R.id.B)
    TextView B;
    @BindView(R.id.C)
    TextView C;
    @BindView(R.id.D)
    TextView D;
    @BindView(R.id.next)
    Button next;

    private int checkpointNum;
    private List<Word> wordData;
    private int[] backgroundRes;
    private boolean isSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpoints);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

        checkpointNum = AccountModel.getInstance().getAccount().getPassLevelNum() + 1;

        setTitle("第" + checkpointNum + "关");

        backgroundRes = new int[]{R.drawable.random_one, R.drawable.random_two, R.drawable.random_three, R.drawable.random_four};

        setOptionBackground();

        showProgressBar("加载中...");
        WordModel.getInstance().getAllWordData(new FindListener<Word>() {
            @Override
            public void onSuccess(List<Word> list) {
                wordData = list;
                if (list.size() < checkpointNum) {
                    showDialog("没有关卡了");
                    dismissProgressBar();
                    next.setEnabled(false);
                    return;
                }
                final Word word = list.get(checkpointNum - 1);
                if (word == null) {
                    dismissProgressBar();
                    return;
                }
                List<String> data = word.getOptions();
                wordImage.setImageUrl(word.getImgUrl());
                A.setText(data.get(0));
                B.setText(data.get(1));
                C.setText(data.get(2));
                D.setText(data.get(3));
                dismissProgressBar();

                setLisenner(word);
            }

            @Override
            public void onError(int i, String s) {
                Utils.Log("code : " + i + "error : " + s);
                if (i != 9009) {
                    Utils.SnackbarShort(A, "加载失败");
                }
                dismissProgressBar();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isSuccess) {
                    showDialog("请选择答案");
                    return;
                }

                showProgressBar("正在加载...");
                checkpointNum++;
                if (checkpointNum > wordData.size()) {
                    showDialog("你好厉害，所有关卡已被你闯完！");
                    dismissProgressBar();
                    return;
                }
                Word word = wordData.get(checkpointNum - 1);
                if (word == null) {
                    dismissProgressBar();
                    return;
                }
                setTitle("第" + checkpointNum + "关");
                List<String> data = word.getOptions();
                wordImage.setImageUrl(word.getImgUrl());
                A.setText(data.get(0));
                B.setText(data.get(1));
                C.setText(data.get(2));
                D.setText(data.get(3));
                setOptionBackground();
                dismissProgressBar();
                setLisenner(word);
            }
        });

    }


    public void setLisenner(final Word word) {
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOption(word, 0);
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOption(word, 1);
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOption(word, 2);
            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOption(word, 3);
            }
        });
    }


    public void setOptionBackground() {
        int[] data = new int[4];
        Random rand = new Random(System.currentTimeMillis());
        boolean[] bool = new boolean[4];
        int randInt;
        for (int i = 0; i < 4; i++) {
            do {
                randInt = rand.nextInt(4);
            } while (bool[randInt]);
            bool[randInt] = true;
            data[i] = randInt;
        }

        A.setBackgroundResource(backgroundRes[data[0]]);
        B.setBackgroundResource(backgroundRes[data[1]]);
        C.setBackgroundResource(backgroundRes[data[2]]);
        D.setBackgroundResource(backgroundRes[data[3]]);
    }

    public void setOption(Word word, int postion) {
        if (word.getCorrectOption() == postion) {
            showDialog("恭喜你，回答正确");
            isSuccess = true;
            AccountModel.getInstance().updateCheckpointLevel(checkpointNum);
        } else {
            showDialog("答案错误！");
            isSuccess = false;
        }
    }
}
