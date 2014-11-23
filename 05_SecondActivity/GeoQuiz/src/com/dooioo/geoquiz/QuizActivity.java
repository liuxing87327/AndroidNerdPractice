package com.dooioo.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends Activity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ISCHEATER = "isCheater";

    private static Map<Integer, Boolean> INDEX_CHEATER = new HashMap<>();

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPrevButton;
    private Button cheatButton;

    private Button mNextButton;

    private ImageButton prevImgButton;
    private ImageButton nextImgButton;

    private TextView mQuestionTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
        new TrueFalse(R.string.question_text0, true),
        new TrueFalse(R.string.question_text1, true),
        new TrueFalse(R.string.question_text2, true),
        new TrueFalse(R.string.question_text3, true),
        new TrueFalse(R.string.question_text4, false),
        new TrueFalse(R.string.question_text5, false)
    };

    private int mCurrentIndex = 0;

    private boolean isCheater;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        INDEX_CHEATER.put(mCurrentIndex, isCheater);
        System.out.println(isCheater);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        isCheater = false;

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            isCheater = savedInstanceState.getBoolean(KEY_ISCHEATER, false);
        }

        updateQuestion();

        // 监听作弊按钮
        cheatButton = (Button)findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "cheatButton onClick");
                // 启动作弊的activity
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismTrueQuestion();
                // 值传递，key-value结构
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivity(i);
                startActivityForResult(i, 0);
            }
        });

        // 监听 true 点击
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });

        // 监听 false 点击
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });

        // 监听上一个的点击
        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                if(mCurrentIndex < 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                }

                initCheater();
                updateQuestion();
            }
        });

        // 监听下一个的点击
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                initCheater();
                updateQuestion();
            }
        });

        // 监听上一个的点击
        prevImgButton = (ImageButton)findViewById(R.id.prev_img);
        prevImgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                if(mCurrentIndex < 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                }

                initCheater();
                updateQuestion();
            }
        });

        // 监听下一个的点击
        nextImgButton = (ImageButton)findViewById(R.id.next_img);
        nextImgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        //监听文字的点击
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                initCheater();
                updateQuestion();
                showMessage(R.string.next_button);
            }
        });

        Log.d(TAG, "onCreate called");
    }

    @Override
    protected void onSaveInstanceState(Bundle onSaveInstanceState) {
        super.onSaveInstanceState(onSaveInstanceState);
        Log.d(TAG, "onSaveInstanceState");
        onSaveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        onSaveInstanceState.putBoolean(KEY_ISCHEATER, isCheater);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    /**
     * 更新问题
     */
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getmQuestion();
        mQuestionTextView.setText(question);
    }

    /**
     * 检查问题答案
     * @param userPressedTrue
     */
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismTrueQuestion();

        int  messageResId = 0;

        if (isCheater) {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.judgment_toast;
            } else {
                messageResId = R.string.incorrect_judgement_toast;
            }
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        showMessage(messageResId);
    }

    /**
     * 切换题目时，设置初始作弊状态
     */
    private void initCheater() {
        if (INDEX_CHEATER.get(mCurrentIndex) == null){
            INDEX_CHEATER.put(mCurrentIndex, false);
        }

        isCheater = INDEX_CHEATER.get(mCurrentIndex);
    }

    private void showMessage(int resId){
        Toast.makeText(QuizActivity.this, resId, Toast.LENGTH_SHORT).show();
    }

}
