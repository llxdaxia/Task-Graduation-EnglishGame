package cn.zhu.cainiao.module;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.alien95.resthttp.view.HttpImageView;
import cn.alien95.util.Utils;
import cn.bmob.v3.listener.FindListener;
import cn.zhu.cainiao.R;
import cn.zhu.cainiao.app.BaseActivity;
import cn.zhu.cainiao.config.Config;
import cn.zhu.cainiao.model.AccountModel;
import cn.zhu.cainiao.model.WordModel;
import cn.zhu.cainiao.model.bean.Word;

/**
 * Created by linlongxin on 2016/5/9.
 */
public class StudyActivity extends BaseActivity {

    @BindView(R.id.chinese)
    TextView chinese;
    @BindView(R.id.english)
    TextView english;
    @BindView(R.id.pronunciation)
    ImageView pronunciation;
    @BindView(R.id.phoneticSymbol)
    TextView phoneticSymbol;
    @BindView(R.id.image)
    HttpImageView image;
    @BindView(R.id.next)
    TextView next;

    private List<Word> wordData;
    private List<Integer> randomNum = new ArrayList<>();
    private int allWordNum;
    private int currentStudyLevelNum;
    private int maxLevelNum;
    private int currentSurplusNum;   //当前单词剩余数
    private boolean isUpdateLevel;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);
        setToolbarIsBack(true);

        currentStudyLevelNum = getIntent().getIntExtra(Config.POSITION, -1);
        isUpdateLevel = getIntent().getBooleanExtra(Config.IS_UPDATE_LEVEL, false);

        setTitle("第" + currentStudyLevelNum + "章节");

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int supported = textToSpeech.setLanguage(Locale.US);
                    if ((supported != TextToSpeech.LANG_AVAILABLE) && (supported != TextToSpeech.LANG_COUNTRY_AVAILABLE)) {
                        Utils.SnackbarShort(next, "不支持当前语言！");
                    }
                }
            }
        });

        pronunciation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(english.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        WordModel.getInstance().getAllWordData(new FindListener<Word>() {
            @Override
            public void onSuccess(List<Word> list) {
                wordData = list;
                allWordNum = list.size();

                maxLevelNum = allWordNum % 10;
                if (maxLevelNum == 0) {
                    maxLevelNum = allWordNum / 10;
                } else {
                    maxLevelNum = allWordNum / 10 + 1;
                }

                if (currentStudyLevelNum > maxLevelNum) {
                    showDialog("你所有章节已学完");
                    next.setEnabled(false);
                    return;
                } else {
                    //不是最后章节
                    if (maxLevelNum - currentStudyLevelNum > 0) {
                        currentSurplusNum = 10;
                    } else {
                        //最后一个章节
                        currentSurplusNum = allWordNum % 10;
                    }

                    if (currentSurplusNum == 1) {
                        next.setText("完成");
                    }

                    setRandom(currentSurplusNum);  //生成一堆不重复随机数
                    Word word = wordData.get((currentStudyLevelNum - 1) * 10 + randomNum.get(currentSurplusNum - 1));
                    currentSurplusNum--;
                    chinese.setText(word.getChinese());
                    english.setText(word.getTranslate());
                    phoneticSymbol.setText(word.getPhoneticSymbol());
                    image.setImageUrl(word.getImgUrl());
                }
            }

            @Override
            public void onError(int i, String s) {
                if (i != 9009) {
                    Utils.Toast("获取数据失败");
                }
            }
        });

        //下一个按钮监听
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSurplusNum == 0) {  //本章节已经学习完了
                    Utils.Toast("本章节已学完");
                    if (isUpdateLevel) {
                        AccountModel.getInstance().updateStudyLevel(currentStudyLevelNum);
                    }
                    finish();
                } else {
                    if (currentSurplusNum == 1) {
                        next.setText("完成");
                    }
                    Word word = wordData.get((currentStudyLevelNum - 1) * 10 + randomNum.get(currentSurplusNum - 1));
                    currentSurplusNum--;
                    chinese.setText(word.getChinese());
                    english.setText(word.getTranslate());
                    phoneticSymbol.setText(word.getPhoneticSymbol());
                    image.setImageUrl(word.getImgUrl());
                }

            }
        });

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
