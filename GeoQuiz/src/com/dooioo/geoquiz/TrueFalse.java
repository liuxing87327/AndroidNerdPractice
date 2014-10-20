package com.dooioo.geoquiz;

/**
 * 功能说明：TrueFalse
 * 作者：liuxing(2014-10-12 16:04)
 */
public class TrueFalse {

    private int mQuestion;
    private boolean mTrueQuestion;

    public TrueFalse(int mQuestion, boolean mTrueQuestion){
        this.mQuestion = mQuestion;
        this.mTrueQuestion = mTrueQuestion;
    }

    public int getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(int mQuestion) {
        this.mQuestion = mQuestion;
    }

    public boolean ismTrueQuestion() {
        return mTrueQuestion;
    }

    public void setmTrueQuestion(boolean mTrueQuestion) {
        this.mTrueQuestion = mTrueQuestion;
    }
}
