package cn.zhu.cainiao.module;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.alien95.resthttp.view.HttpImageView;
import cn.alien95.util.Utils;
import cn.bmob.v3.listener.FindListener;
import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;
import cn.zhu.cainiao.config.Config;
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
    private List<Word> wordData;
    private List<Integer> randomNum = new ArrayList<>();
    private int[] backgroundRes;
    private boolean isSelectCorrect;
    private int currentLevelNum;
    private boolean isUpdateLevel;
    private int allWordNum;
    private int maxLevelNum;
    private int currentSurplusNum;   //当前单词剩余数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpoints);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

        currentLevelNum = getIntent().getIntExtra(Config.POSITION, -1);
        isUpdateLevel = getIntent().getBooleanExtra(Config.IS_UPDATE_LEVEL, false);
        backgroundRes = new int[]{R.drawable.random_one, R.drawable.random_two, R.drawable.random_three, R.drawable.random_four};
        setOptionBackground();  //设置选项的背景
        setTitle("第" + currentLevelNum + "关");

        WordModel.getInstance().getAllWordData(new FindListener<Word>() {
            @Override
            public void onSuccess(List<Word> list) {
                wordData = list;
                allWordNum = list.size();
                if (allWordNum % 10 == 0) {
                    maxLevelNum = allWordNum / 10;
                } else {
                    maxLevelNum = allWordNum / 10 + 1;
                }

                if (currentLevelNum > maxLevelNum) {  //所有都学完了
                    showDialog("所有关卡已被你闯完");
                    return;
                } else {

                    if (maxLevelNum - currentLevelNum == 0) {  //最后一关
                        currentSurplusNum = allWordNum % 10;
                    } else {  //不是最后一关
                        currentSurplusNum = 10;
                    }

                    setRandom(currentSurplusNum);   //产生一堆不重复随机数

                    Word word = wordData.get((currentLevelNum - 1) * 10 + randomNum.get(currentSurplusNum - 1));
                    currentSurplusNum--;
                    List<String> data = word.getOptions();
                    if (word.getImgUrl() != null && !word.getImgUrl().isEmpty()) {
                        wordImage.setImageUrl(word.getImgUrl());
                    }
                    Utils.Log("word.getImgUrl() : " + word.getImgUrl());
                    A.setText(data.get(0));
                    B.setText(data.get(1));
                    C.setText(data.get(2));
                    D.setText(data.get(3));

                    setOptionListener(word);
                }

            }

            @Override
            public void onError(int i, String s) {
                if (i != 9009) {
                    Utils.Toast("获取数据失败");
                }
            }
        });


//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!isSelectCorrect) {
//                    showDialog("请选择正确答案");
//                    return;
//                }
//
//                if (currentSurplusNum == 0) {  //本章节已经学习完了
//                    Utils.Toast("恭喜你，完成了这个关卡");
//                    if (isUpdateLevel) {
//                        AccountModel.getInstance().updateCheckpointLevel(currentLevelNum);
//                    }
//                    finish();
//                } else {
//                    if (currentSurplusNum == 1) {
//                        next.setText("完成");
//                    }
//                    Word word = wordData.get((currentLevelNum - 1) * 10 + randomNum.get(currentSurplusNum - 1));
//                    currentSurplusNum--;
//
//                    List<String> data = word.getOptions();
//                    if (word.getImgUrl() != null && !word.getImgUrl().isEmpty()) {
//                        wordImage.setImageUrl(word.getImgUrl());
//                    }
//                    A.setText(data.get(0));
//                    B.setText(data.get(1));
//                    C.setText(data.get(2));
//                    D.setText(data.get(3));
//
//                    setOptionBackground();
//                    setOptionListener(word);
//                }
//
//            }
//        });

    }


    public void setOptionListener(final Word word) {
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOption(word, 0, A);
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOption(word, 1, B);
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOption(word, 2, C);
            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOption(word, 3, D);
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

    public void setOption(Word word, int position, TextView option) {
        if (word.getCorrectOption() == position) {
            isSelectCorrect = true;
            option.setPressed(true);
            Utils.Toast("恭喜你，回答正确");
            if (currentSurplusNum > 0) {
                Word newWord = wordData.get((currentLevelNum - 1) * 10 + randomNum.get(currentSurplusNum - 1));
                currentSurplusNum--;
                List<String> data = newWord.getOptions();
                if (newWord.getImgUrl() != null) {
                    wordImage.setImageUrl(newWord.getImgUrl());
                }
                A.setText(data.get(0));
                B.setText(data.get(1));
                C.setText(data.get(2));
                D.setText(data.get(3));

                setOptionBackground();
                setOptionListener(newWord);
            } else {
                finish();
            }

        } else {
            Utils.SnackbarShort(option, "答案错误");
            isSelectCorrect = false;
        }
    }

    public void setRandom(int endNum) {
        Random rand = new Random(System.currentTimeMillis());
        boolean[] bool = new boolean[endNum];
        int randInt;
        for (int i = 0; i < endNum; i++) {
            do {
                randInt = rand.nextInt(endNum);
            } while (bool[randInt]);
            bool[randInt] = true;
            randomNum.add(randInt);
        }
    }
}
