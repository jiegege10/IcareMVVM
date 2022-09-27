package com.icare.mvvm.widget.cardview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.qmuiteam.qmui.alpha.QMUIAlphaViewHelper;
import com.qmuiteam.qmui.alpha.QMUIAlphaViewInf;

public class ICareUIAlphaRelativeLayout extends RelativeLayout implements QMUIAlphaViewInf {

    private QMUIAlphaViewHelper mAlphaViewHelper;

    public ICareUIAlphaRelativeLayout(Context context) {
        super(context);
    }

    public ICareUIAlphaRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ICareUIAlphaRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private QMUIAlphaViewHelper getAlphaViewHelper() {
        if (mAlphaViewHelper == null) {
            mAlphaViewHelper = new QMUIAlphaViewHelper(this);
        }
        return mAlphaViewHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this, pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().onEnabledChanged(this, enabled);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    @Override
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    @Override
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaWhenDisable(changeAlphaWhenDisable);
    }

}
