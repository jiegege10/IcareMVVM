package com.icare.mvvm.widget.supper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.icare.mvvm.R;
import com.icare.mvvm.widget.supper.helper.EditTextHelper;
import com.icare.mvvm.widget.supper.helper.ShapeBuilder;
import com.icare.mvvm.widget.supper.helper.ShapeType;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;

/**
* @date: 2021/8/10 2:14 下午
* @author: Mr.He
* @param
* @return 打造万能的布局满足市面常见的样式
*/
public class ICareSuperTextView extends RelativeLayout {

    private Context mContext;

    private BaseTextView leftView, centerView, rightView;
    private LayoutParams leftBaseViewParams, centerBaseViewParams, rightBaseViewParams;

    private CircleImageView leftIconIV, rightIconIV;
    private LayoutParams leftImgParams, rightImgParams;
    private int leftIconWidth;//左边图标的宽
    private int leftIconHeight;//左边图标的高

    private int rightIconWidth;//右边图标的宽
    private int rightIconHeight;//右边图标的高

    private int leftIconMarginLeft;//左边图标的左边距
    private int rightIconMarginRight;//右边图标的右边距

    private Drawable leftIconRes;//左边图标资源
    private Drawable rightIconRes;//右边图标资源


    private int defaultColor = 0xFF373737;//文字默认颜色
    private int defaultSize = 15;//默认字体大小
    private int defaultMaxEms = 0;
    private int defaultMaxLines = 0;


    private String mLeftTextString;
    private String mLeftTopTextString;
    private String mLeftBottomTextString;

    private String mRightTextString;
    private String mRightTopTextString;
    private String mRightBottomTextString;

    private String mCenterTextString;
    private String mCenterTopTextString;
    private String mCenterBottomTextString;


    private ColorStateList mLeftTextColor;
    private ColorStateList mLeftTopTextColor;
    private ColorStateList mLeftBottomTextColor;

    private ColorStateList mCenterTextColor;
    private ColorStateList mCenterTopTextColor;
    private ColorStateList mCenterBottomTextColor;

    private ColorStateList mRightTextColor;
    private ColorStateList mRightTopTextColor;
    private ColorStateList mRightBottomTextColor;

    private ColorStateList mRightEditHintTextColor;
    private ColorStateList mRightEditTextColor;


    private int mLeftTextSize;
    private int mLeftTopTextSize;
    private int mLeftBottomTextSize;

    private int mRightTextSize;
    private int mRightTopTextSize;
    private int mRightBottomTextSize;

    private int mCenterTextSize;
    private int mCenterTopTextSize;
    private int mCenterBottomTextSize;

    private int mLeftTopLines;
    private int mLeftLines;
    private int mLeftBottomLines;

    private int mCenterTopLines;
    private int mCenterLines;
    private int mCenterBottomLines;

    private int mRightTopLines;
    private int mRightLines;
    private int mRightBottomLines;

    private int mLeftTopMaxEms;
    private int mLeftMaxEms;
    private int mLeftBottomMaxEms;

    private int mCenterTopMaxEms;
    private int mCenterMaxEms;
    private int mCenterBottomMaxEms;

    private int mRightTopMaxEms;
    private int mRightMaxEms;
    private int mRightBottomMaxEms;

    private boolean mLeftTopTextBold;
    private boolean mLeftTextBold;
    private boolean mLeftBottomTextBold;

    private boolean mCenterTopTextBold;
    private boolean mCenterTextBold;
    private boolean mCenterBottomTextBold;

    private boolean mRightTopTextBold;
    private boolean mRightTextBold;
    private boolean mRightBottomTextBold;

    private Drawable mLeftTextBackground;
    private Drawable mCenterTextBackground;
    private Drawable mRightTextBackground;

    private Drawable mLeftTvDrawableLeft;
    private Drawable mLeftTvDrawableRight;

    private Drawable mCenterTvDrawableLeft;
    private Drawable mCenterTvDrawableRight;

    private Drawable mRightTvDrawableLeft;
    private Drawable mRightTvDrawableRight;

    private int mLeftTvDrawableWidth;
    private int mLeftTvDrawableHeight;

    private int mCenterTvDrawableWidth;
    private int mCenterTvDrawableHeight;

    private int mRightTvDrawableWidth;
    private int mRightTvDrawableHeight;

    private int mTextViewDrawablePadding;

    private static final int gravity_Left_Center = 0;
    private static final int gravity_Center = 1;
    private static final int gravity_Right_Center = 2;

    private static final int default_Gravity = 1;

    private int mLeftGravity;
    private int mCenterGravity;
    private int mRightGravity;

    private int mLeftTextGravity;
    private int mCenterTextGravity;
    private int mRightTextGravity;

    private static final int text_gravity_Left = 0;
    private static final int text_gravity_center = 1;
    private static final int text_gravity_right = 2;

    private static final int default_text_gravity = -1;

    private int mLeftViewWidth;


    private int mTopDividerLineMarginLR;
    private int mTopDividerLineMarginLeft;
    private int mTopDividerLineMarginRight;

    private int mBottomDividerLineMarginLR;
    private int mBottomDividerLineMarginLeft;
    private int mBottomDividerLineMarginRight;

    private int mDividerLineType;
    private int mDividerLineColor;
    private int mDividerLineHeight;

    private int mDefaultDividerLineColor = 0xFFE8E8E8;//分割线默认颜色

    /**
     * 分割线的类型
     */
    public static final int NONE = 0;
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int BOTH = 3;
    private static final int default_Divider = NONE;

    private int default_Margin = 10;

    private int mLeftViewMarginLeft;
    private int mLeftViewMarginRight;

    private int mCenterViewMarginLeft;
    private int mCenterViewMarginRight;

    private int mRightViewMarginLeft;
    private int mRightViewMarginRight;

    private boolean useRipple;
    private Drawable mBackground_drawable;

    private OnSuperTextViewClickListener superTextViewClickListener;

    private OnLeftTopTvClickListener leftTopTvClickListener;
    private OnLeftTvClickListener leftTvClickListener;
    private OnLeftBottomTvClickListener leftBottomTvClickListener;

    private OnCenterTopTvClickListener centerTopTvClickListener;
    private OnCenterTvClickListener centerTvClickListener;
    private OnCenterBottomTvClickListener centerBottomTvClickListener;

    private OnRightTopTvClickListener rightTopTvClickListener;
    private OnRightTvClickListener rightTvClickListener;
    private OnRightBottomTvClickListener rightBottomTvClickListener;

    private OnSwitchCheckedChangeListener switchCheckedChangeListener;
    private OnCheckBoxCheckedChangeListener checkBoxCheckedChangeListener;
    private OnEditTextChangeListener editTextChangeListener;

    private OnLeftImageViewClickListener leftImageViewClickListener;
    private OnRightImageViewClickListener rightImageViewClickListener;

    private static final int TYPE_CHECKBOX = 0;
    private static final int TYPE_SWITCH = 1;
    private static final int TYPE_EDIT = 2;

    private static int mRightViewType;

    private AppCompatCheckBox rightCheckBox;//右边checkbox
    private LayoutParams rightCheckBoxParams;//右边checkbox
    private Drawable rightCheckBoxBg;//checkBox的背景
    private int rightCheckBoxMarginRight;//右边checkBox的右边距
    private boolean isChecked;//是否默认选中


    private int centerSpaceHeight;//中间空间的高度


    private SwitchCompat mSwitch;
    private LayoutParams mSwitchParams;//右边switch
    private int rightSwitchMarginRight;
    private boolean switchIsChecked = true;

    private AppCompatEditText mEditText;
    private LayoutParams mEditParams;
    private int mEditMinWidth;
    private int mEditImeOption;
    private int mEditInputType;
    private int rightEditMarginRight;
    private int editActiveLineColor = -1;
    private int mEditTextSize;
    private int mEditCusorDrawableRes;
    private boolean mEditCusorVisible;

    private String mEditHintText;
    private String mTextOff;
    private String mTextOn;


    private int mSwitchMinWidth;
    private int mSwitchPadding;

    private int mThumbTextPadding;

    private Drawable mThumbResource;
    private Drawable mTrackResource;

    /////////////////////一下是shape相关属性
    private int defaultShapeColor = 0xffffffff;

    private int selectorPressedColor;
    private int selectorNormalColor;

    private int solidColor;

    private float cornersRadius;
    private float cornersTopLeftRadius;
    private float cornersTopRightRadius;
    private float cornersBottomLeftRadius;
    private float cornersBottomRightRadius;

    private int strokeWidth;
    private int strokeColor;

    private float strokeDashWidth;
    private float strokeDashGap;

    private boolean useShape;

    private boolean mLeftIconShowCircle;
    private boolean mRightIconShowCircle;

    private GradientDrawable gradientDrawable;

    private Paint mTopDividerPaint;
    private Paint mBottomDividerPaint;

    private boolean mIsShowTopDivider;
    private boolean mIsShowBottomDivider;

    private ShapeBuilder shapeBuilder;

    public ICareSuperTextView(Context context) {
        this(context, null);
    }

    public ICareSuperTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ICareSuperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        defaultSize = sp2px(context, defaultSize);
        default_Margin = dip2px(context, default_Margin);
        shapeBuilder = new ShapeBuilder();

        getAttr(attrs);
        initPaint();
        initView();
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ICareSuperTextView);

        /////////////////////////////////////////////////
        mLeftTextString = typedArray.getString(R.styleable.ICareSuperTextView_sLeftTextString);
        mLeftTopTextString = typedArray.getString(R.styleable.ICareSuperTextView_sLeftTopTextString);
        mLeftBottomTextString = typedArray.getString(R.styleable.ICareSuperTextView_sLeftBottomTextString);

        mCenterTextString = typedArray.getString(R.styleable.ICareSuperTextView_sCenterTextString);
        mCenterTopTextString = typedArray.getString(R.styleable.ICareSuperTextView_sCenterTopTextString);
        mCenterBottomTextString = typedArray.getString(R.styleable.ICareSuperTextView_sCenterBottomTextString);

        mRightTextString = typedArray.getString(R.styleable.ICareSuperTextView_sRightTextString);
        mRightTopTextString = typedArray.getString(R.styleable.ICareSuperTextView_sRightTopTextString);
        mRightBottomTextString = typedArray.getString(R.styleable.ICareSuperTextView_sRightBottomTextString);

        //////////////////////////////////////////////////

        mLeftTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sLeftTextColor);
        mLeftTopTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sLeftTopTextColor);
        mLeftBottomTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sLeftBottomTextColor);

        mCenterTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sCenterTextColor);
        mCenterTopTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sCenterTopTextColor);
        mCenterBottomTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sCenterBottomTextColor);

        mRightTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sRightTextColor);
        mRightTopTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sRightTopTextColor);
        mRightBottomTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sRightBottomTextColor);

        //////////////////////////////////////////////////


        mLeftTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftTextSize, defaultSize);
        mLeftTopTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftTopTextSize, defaultSize);
        mLeftBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftBottomTextSize, defaultSize);

        mCenterTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sCenterTextSize, defaultSize);
        mCenterTopTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sCenterTopTextSize, defaultSize);
        mCenterBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sCenterBottomTextSize, defaultSize);

        mRightTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightTextSize, defaultSize);
        mRightTopTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightTopTextSize, defaultSize);
        mRightBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightBottomTextSize, defaultSize);

        //////////////////////////////////////////////////
        mLeftTopLines = typedArray.getInt(R.styleable.ICareSuperTextView_sLeftTopLines, defaultMaxLines);
        mLeftLines = typedArray.getInt(R.styleable.ICareSuperTextView_sLeftLines, defaultMaxLines);
        mLeftBottomLines = typedArray.getInt(R.styleable.ICareSuperTextView_sLeftBottomLines, defaultMaxLines);

        mCenterTopLines = typedArray.getInt(R.styleable.ICareSuperTextView_sCenterTopLines, defaultMaxLines);
        mCenterLines = typedArray.getInt(R.styleable.ICareSuperTextView_sCenterLines, defaultMaxLines);
        mCenterBottomLines = typedArray.getInt(R.styleable.ICareSuperTextView_sCenterBottomLines, defaultMaxLines);

        mRightTopLines = typedArray.getInt(R.styleable.ICareSuperTextView_sRightTopLines, defaultMaxLines);
        mRightLines = typedArray.getInt(R.styleable.ICareSuperTextView_sRightLines, defaultMaxLines);
        mRightBottomLines = typedArray.getInt(R.styleable.ICareSuperTextView_sRightBottomLines, defaultMaxLines);

        //////////////////////////////////////////////////

        mLeftTopMaxEms = typedArray.getInt(R.styleable.ICareSuperTextView_sLeftTopMaxEms, defaultMaxEms);
        mLeftMaxEms = typedArray.getInt(R.styleable.ICareSuperTextView_sLeftMaxEms, defaultMaxEms);
        mLeftBottomMaxEms = typedArray.getInt(R.styleable.ICareSuperTextView_sLeftBottomMaxEms, defaultMaxEms);

        mCenterTopMaxEms = typedArray.getInt(R.styleable.ICareSuperTextView_sCenterTopMaxEms, defaultMaxEms);
        mCenterMaxEms = typedArray.getInt(R.styleable.ICareSuperTextView_sCenterMaxEms, defaultMaxEms);
        mCenterBottomMaxEms = typedArray.getInt(R.styleable.ICareSuperTextView_sCenterBottomMaxEms, defaultMaxEms);

        mRightTopMaxEms = typedArray.getInt(R.styleable.ICareSuperTextView_sRightTopMaxEms, defaultMaxEms);
        mRightMaxEms = typedArray.getInt(R.styleable.ICareSuperTextView_sRightMaxEms, defaultMaxEms);
        mRightBottomMaxEms = typedArray.getInt(R.styleable.ICareSuperTextView_sRightBottomMaxEms, defaultMaxEms);

        ////////////////////////////////////////////////

        mLeftGravity = typedArray.getInt(R.styleable.ICareSuperTextView_sLeftViewGravity, default_Gravity);
        mCenterGravity = typedArray.getInt(R.styleable.ICareSuperTextView_sCenterViewGravity, default_Gravity);
        mRightGravity = typedArray.getInt(R.styleable.ICareSuperTextView_sRightViewGravity, default_Gravity);

        mLeftTextGravity = typedArray.getInt(R.styleable.ICareSuperTextView_sLeftTextGravity, default_text_gravity);
        mCenterTextGravity = typedArray.getInt(R.styleable.ICareSuperTextView_sCenterTextGravity, default_text_gravity);
        mRightTextGravity = typedArray.getInt(R.styleable.ICareSuperTextView_sRightTextGravity, default_text_gravity);
        ////////////////////////////////////////////////

        mLeftTvDrawableLeft = typedArray.getDrawable(R.styleable.ICareSuperTextView_sLeftTvDrawableLeft);
        mLeftTvDrawableRight = typedArray.getDrawable(R.styleable.ICareSuperTextView_sLeftTvDrawableRight);
        mCenterTvDrawableLeft = typedArray.getDrawable(R.styleable.ICareSuperTextView_sCenterTvDrawableLeft);
        mCenterTvDrawableRight = typedArray.getDrawable(R.styleable.ICareSuperTextView_sCenterTvDrawableRight);
        mRightTvDrawableLeft = typedArray.getDrawable(R.styleable.ICareSuperTextView_sRightTvDrawableLeft);
        mRightTvDrawableRight = typedArray.getDrawable(R.styleable.ICareSuperTextView_sRightTvDrawableRight);

        mTextViewDrawablePadding = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sTextViewDrawablePadding, default_Margin);
        ////////////////////////////////////////////////

        mLeftTvDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftTvDrawableWidth, -1);
        mLeftTvDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftTvDrawableHeight, -1);

        mCenterTvDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sCenterTvDrawableWidth, -1);
        mCenterTvDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sCenterTvDrawableHeight, -1);

        mRightTvDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightTvDrawableWidth, -1);
        mRightTvDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightTvDrawableHeight, -1);

        mLeftViewWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftViewWidth, 0);
        ///////////////////////////////////////////////
        mTopDividerLineMarginLR = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sTopDividerLineMarginLR, 0);
        mTopDividerLineMarginLeft = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sTopDividerLineMarginLeft, 0);
        mTopDividerLineMarginRight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sTopDividerLineMarginRight, 0);

        mBottomDividerLineMarginLR = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sBottomDividerLineMarginLR, 0);
        mBottomDividerLineMarginLeft = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sBottomDividerLineMarginLeft, 0);
        mBottomDividerLineMarginRight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sBottomDividerLineMarginRight, 0);
        ///////////////////////////////////////////////
        mDividerLineType = typedArray.getInt(R.styleable.ICareSuperTextView_sDividerLineType, default_Divider);
        mDividerLineColor = typedArray.getColor(R.styleable.ICareSuperTextView_sDividerLineColor, mDefaultDividerLineColor);

        mDividerLineHeight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sDividerLineHeight, dip2px(mContext, 0.5f));
        ////////////////////////////////////////////////
        mLeftViewMarginLeft = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftViewMarginLeft, default_Margin);
        mLeftViewMarginRight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftViewMarginRight, default_Margin);
        mCenterViewMarginLeft = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sCenterViewMarginLeft, 0);
        mCenterViewMarginRight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sCenterViewMarginRight, 0);
        mRightViewMarginLeft = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightViewMarginLeft, default_Margin);
        mRightViewMarginRight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightViewMarginRight, default_Margin);
        ///////////////////////////////////////////////
        leftIconWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftIconWidth, 0);
        leftIconHeight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftIconHeight, 0);

        rightIconWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightIconWidth, 0);
        rightIconHeight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightIconHeight, 0);

        leftIconMarginLeft = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sLeftIconMarginLeft, default_Margin);
        rightIconMarginRight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightIconMarginRight, default_Margin);

        leftIconRes = typedArray.getDrawable(R.styleable.ICareSuperTextView_sLeftIconRes);
        rightIconRes = typedArray.getDrawable(R.styleable.ICareSuperTextView_sRightIconRes);
        ////////////////////////由于自定义方法数达到最大限度128个，暂时关闭不常用属性改为代码控制//////////////////////
        mLeftTopTextBold = typedArray.getBoolean(R.styleable.ICareSuperTextView_sLeftTopTextIsBold, false);
        mLeftTextBold = typedArray.getBoolean(R.styleable.ICareSuperTextView_sLeftTextIsBold, false);
        mLeftBottomTextBold = typedArray.getBoolean(R.styleable.ICareSuperTextView_sLeftBottomTextIsBold, false);

        mCenterTopTextBold = typedArray.getBoolean(R.styleable.ICareSuperTextView_sCenterTopTextIsBold, false);
        mCenterTextBold = typedArray.getBoolean(R.styleable.ICareSuperTextView_sCenterTextIsBold, false);
        mCenterBottomTextBold = typedArray.getBoolean(R.styleable.ICareSuperTextView_sCenterBottomTextIsBold, false);

        mRightTopTextBold = typedArray.getBoolean(R.styleable.ICareSuperTextView_sRightTopTextIsBold, false);
        mRightTextBold = typedArray.getBoolean(R.styleable.ICareSuperTextView_sRightTextIsBold, false);
        mRightBottomTextBold = typedArray.getBoolean(R.styleable.ICareSuperTextView_sRightBottomTextIsBold, false);

        mLeftTextBackground = typedArray.getDrawable(R.styleable.ICareSuperTextView_sLeftTextBackground);
        mCenterTextBackground = typedArray.getDrawable(R.styleable.ICareSuperTextView_sCenterTextBackground);
        mRightTextBackground = typedArray.getDrawable(R.styleable.ICareSuperTextView_sRightTextBackground);

        //////////////////////////////////////////////
        useRipple = typedArray.getBoolean(R.styleable.ICareSuperTextView_sUseRipple, true);
        mBackground_drawable = typedArray.getDrawable(R.styleable.ICareSuperTextView_sBackgroundDrawableRes);
        ///////////////////////////////////////////////
        mRightViewType = typedArray.getInt(R.styleable.ICareSuperTextView_sRightViewType, -1);
        ////////////////////////////////////////////////
        switch (mRightViewType){
            case TYPE_CHECKBOX:{
                isChecked = typedArray.getBoolean(R.styleable.ICareSuperTextView_sIsChecked, false);
                rightCheckBoxMarginRight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightCheckBoxMarginRight, default_Margin);
                rightCheckBoxBg = typedArray.getDrawable(R.styleable.ICareSuperTextView_sRightCheckBoxRes);
            }break;
            case TYPE_SWITCH:{
                rightSwitchMarginRight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sRightSwitchMarginRight, default_Margin);
                switchIsChecked = typedArray.getBoolean(R.styleable.ICareSuperTextView_sSwitchIsChecked, false);
                mTextOff = typedArray.getString(R.styleable.ICareSuperTextView_sTextOff);
                mTextOn = typedArray.getString(R.styleable.ICareSuperTextView_sTextOn);

                mSwitchMinWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sSwitchMinWidth, 0);
                mSwitchPadding = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sSwitchPadding, 0);
                mThumbTextPadding = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sThumbTextPadding, 0);

                mThumbResource = typedArray.getDrawable(R.styleable.ICareSuperTextView_sThumbResource);
                mTrackResource = typedArray.getDrawable(R.styleable.ICareSuperTextView_sTrackResource);
            }break;
            case TYPE_EDIT:{
                rightEditMarginRight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sEditMarginRight, 0);
                mEditMinWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sEditMinWidth, 0);
                mEditImeOption = typedArray.getInt(R.styleable.ICareSuperTextView_android_imeOptions, EditorInfo.IME_NULL);
                mEditInputType = typedArray.getInt(R.styleable.ICareSuperTextView_android_inputType, EditorInfo.TYPE_NULL);
                mRightEditTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sEditTextColor);
                mRightEditHintTextColor = typedArray.getColorStateList(R.styleable.ICareSuperTextView_sEditHintTextColor);
                mEditHintText = typedArray.getString(R.styleable.ICareSuperTextView_sEditHint);
                editActiveLineColor = typedArray.getColor(R.styleable.ICareSuperTextView_sEditActiveLineColor, editActiveLineColor);
                mEditTextSize = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sEditTextSize, defaultSize);
                mEditCusorDrawableRes = typedArray.getResourceId(R.styleable.ICareSuperTextView_sEditCursorDrawable,0);
                mEditCusorVisible = typedArray.getBoolean(R.styleable.ICareSuperTextView_sEditCursorVisible,true);
            }break;
        }

        centerSpaceHeight = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sCenterSpaceHeight, dip2px(mContext, 5));
        ////////////////////////////////////////////////////
        selectorPressedColor = typedArray.getColor(R.styleable.ICareSuperTextView_sShapeSelectorPressedColor, -1);
        selectorNormalColor = typedArray.getColor(R.styleable.ICareSuperTextView_sShapeSelectorNormalColor, -1);

        solidColor = typedArray.getColor(R.styleable.ICareSuperTextView_sShapeSolidColor, defaultShapeColor);

        cornersRadius = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sShapeCornersRadius, 0);
        cornersTopLeftRadius = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sShapeCornersTopLeftRadius, 0);
        cornersTopRightRadius = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sShapeCornersTopRightRadius, 0);
        cornersBottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sShapeCornersBottomLeftRadius, 0);
        cornersBottomRightRadius = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sShapeCornersBottomRightRadius, 0);

        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sShapeStrokeWidth, 0);
        strokeDashWidth = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sShapeStrokeDashWidth, 0);
        strokeDashGap = typedArray.getDimensionPixelSize(R.styleable.ICareSuperTextView_sShapeStrokeDashGap, 0);

        strokeColor = typedArray.getColor(R.styleable.ICareSuperTextView_sShapeStrokeColor, defaultShapeColor);

        useShape = typedArray.getBoolean(R.styleable.ICareSuperTextView_sUseShape, false);
        mLeftIconShowCircle = typedArray.getBoolean(R.styleable.ICareSuperTextView_sLeftIconShowCircle, false);
        mRightIconShowCircle = typedArray.getBoolean(R.styleable.ICareSuperTextView_sRightIconShowCircle, false);


        typedArray.recycle();
    }

    /**
     * 初始化Params
     *
     * @param params params
     * @return params
     */
    private LayoutParams getParams(LayoutParams params) {
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        return params;
    }

    /**
     * 初始化View
     */
    private void initView() {

        initICareSuperTextView();

        initShape();
        initLeftIcon();

        switch (mRightViewType) {
            case TYPE_CHECKBOX:
                initRightCheckBox();
                break;
            case TYPE_SWITCH:
                initRightSwitch();
                break;
        }

        initRightIcon();

        if(mRightViewType == TYPE_EDIT){
            initRightEditText();
        }

        initLeftTextView();
        initCenterTextView();
        initRightTextView();

    }



    private void initShape() {
        if (useShape) {
            shapeBuilder
                    .setShapeType(ShapeType.RECTANGLE)
                    .setShapeCornersRadius(cornersRadius)
                    .setShapeCornersTopLeftRadius(cornersTopLeftRadius)
                    .setShapeCornersTopRightRadius(cornersTopRightRadius)
                    .setShapeCornersBottomRightRadius(cornersBottomRightRadius)
                    .setShapeCornersBottomLeftRadius(cornersBottomLeftRadius)
                    .setShapeSolidColor(solidColor)
                    .setShapeStrokeColor(strokeColor)
                    .setShapeStrokeWidth(strokeWidth)
                    .setShapeStrokeDashWidth(strokeDashWidth)
                    .setShapeStrokeDashGap(strokeDashGap)
                    .setShapeUseSelector(selectorNormalColor != -1 || selectorPressedColor != -1)
                    .setShapeSelectorNormalColor(selectorNormalColor)
                    .setShapeSelectorPressedColor(selectorPressedColor)
                    .into(this);
        }
    }

    private void initPaint() {
        mTopDividerPaint = new Paint();
        mTopDividerPaint.setColor(mDividerLineColor);
        mTopDividerPaint.setAntiAlias(true);
        mTopDividerPaint.setStrokeWidth(mDividerLineHeight);


        mBottomDividerPaint = new Paint();
        mBottomDividerPaint.setColor(mDividerLineColor);
        mBottomDividerPaint.setAntiAlias(true);
        mBottomDividerPaint.setStrokeWidth(mDividerLineHeight);

    }


    /**
     * onDraw和dispatchDraw的区别请自行google
     *
     * @param canvas 画笔
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!useShape) {
            mIsShowTopDivider = (TOP == mDividerLineType || BOTH == mDividerLineType);
            mIsShowBottomDivider = (BOTTOM == mDividerLineType || BOTH == mDividerLineType);
            if (mIsShowTopDivider) {
                drawTopDivider(canvas);
            }
            if (mIsShowBottomDivider) {
                drawBottomDivider(canvas);
            }
        }
    }

    /**
     * 绘制上边的分割线
     *
     * @param canvas canvas
     */
    private void drawTopDivider(Canvas canvas) {
        drawDivider(canvas, true, mTopDividerLineMarginLR, mTopDividerLineMarginLeft, mTopDividerLineMarginRight, mTopDividerPaint);
    }

    /**
     * 绘制底部的分割线
     *
     * @param canvas canvas
     */
    private void drawBottomDivider(Canvas canvas) {
        drawDivider(canvas, false, mBottomDividerLineMarginLR, mBottomDividerLineMarginLeft, mBottomDividerLineMarginRight, mBottomDividerPaint);
    }

    /**
     * 绘制分割线
     *
     * @param canvas      canvas
     * @param marginLR    左右间距
     * @param marginLeft  左间距
     * @param marginRight 右间距
     * @param paint       画笔
     */
    private void drawDivider(Canvas canvas, boolean isTopDivider, int marginLR, int marginLeft, int marginRight, Paint paint) {

        if (marginLR != 0) {
            marginLeft = marginRight = marginLR;
        }
        canvas.drawLine(marginLeft, isTopDivider ? 0 : getHeight(), this.getWidth() - marginRight, isTopDivider ? 0 : getHeight(), paint);
    }


    private void initICareSuperTextView() {
        if (useRipple) {
//            this.setBackgroundResource(R.drawable.selector_white);
            this.setClickable(true);
        }

        if (mBackground_drawable != null) {
//            this.setBackgroundDrawable(mBackground_drawable);
        }
    }

    /**
     * 初始化左边图标
     */
    private void initLeftIcon() {
        if (leftIconIV == null) {
            leftIconIV = new CircleImageView(mContext);
        }
        leftImgParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftImgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        leftImgParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        if (leftIconHeight != 0 && leftIconWidth != 0) {
            leftImgParams.width = leftIconWidth;
            leftImgParams.height = leftIconHeight;
        }
//        leftIconIV.setScaleType(ImageView.ScaleType.FIT_CENTER);
        leftIconIV.setId(R.id.sLeftImgId);
        leftIconIV.setLayoutParams(leftImgParams);
        if (leftIconRes != null) {
            leftImgParams.setMargins(leftIconMarginLeft, 0, 0, 0);
            leftIconIV.setImageDrawable(leftIconRes);
        }
        setCircleImage(leftIconIV, mLeftIconShowCircle);
        addView(leftIconIV);
    }

    /**
     * 初始化右边图标
     */
    private void initRightIcon() {
        if (rightIconIV == null) {
            rightIconIV = new CircleImageView(mContext);
        }
        rightImgParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightImgParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);

        switch (mRightViewType) {
            case TYPE_CHECKBOX:
                rightImgParams.addRule(RelativeLayout.LEFT_OF, R.id.sRightCheckBoxId);
                break;
            case TYPE_SWITCH:
                rightImgParams.addRule(RelativeLayout.LEFT_OF, R.id.sRightSwitchId);
                break;
            default:
                rightImgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);

        }

        if (rightIconHeight != 0 && rightIconWidth != 0) {
            rightImgParams.width = rightIconWidth;
            rightImgParams.height = rightIconHeight;
        }

//        rightIconIV.setScaleType(ImageView.ScaleType.FIT_CENTER);
        rightIconIV.setId(R.id.sRightImgId);
        rightIconIV.setLayoutParams(rightImgParams);
        if (rightIconRes != null) {
            rightImgParams.setMargins(0, 0, rightIconMarginRight, 0);
            rightIconIV.setImageDrawable(rightIconRes);
        }
        setCircleImage(rightIconIV, mRightIconShowCircle);
        addView(rightIconIV);
    }

    /**
     * 初始化LeftTextView
     */
    private void initLeftTextView() {
        if (leftView == null) {
            leftView = initBaseView(R.id.sLeftViewId);
        }
        leftBaseViewParams = getParams(leftBaseViewParams);
        leftBaseViewParams.addRule(RelativeLayout.RIGHT_OF, R.id.sLeftImgId);
        leftBaseViewParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        if (mLeftViewWidth != 0) {
            leftBaseViewParams.width = mLeftViewWidth;
        }
        leftBaseViewParams.setMargins(mLeftViewMarginLeft, 0, mLeftViewMarginRight, 0);

        leftView.setLayoutParams(leftBaseViewParams);

        leftView.setCenterSpaceHeight(centerSpaceHeight);
        setDefaultColor(leftView, mLeftTopTextColor, mLeftTextColor, mLeftBottomTextColor);
        setDefaultSize(leftView, mLeftTopTextSize, mLeftTextSize, mLeftBottomTextSize);
        setDefaultLines(leftView, mLeftTopLines, mLeftLines, mLeftBottomLines);
        setDefaultMaxEms(leftView, mLeftTopMaxEms, mLeftMaxEms, mLeftBottomMaxEms);
        setDefaultTextIsBold(leftView, mLeftTopTextBold, mLeftTextBold, mLeftBottomTextBold);
        setDefaultGravity(leftView, mLeftGravity);
        setDefaultTextGravity(leftView, mLeftTextGravity);
        setDefaultDrawable(leftView.getCenterTextView(), mLeftTvDrawableLeft, mLeftTvDrawableRight, mTextViewDrawablePadding, mLeftTvDrawableWidth, mLeftTvDrawableHeight);
        setDefaultBackground(leftView.getCenterTextView(), mLeftTextBackground);
        setDefaultString(leftView, mLeftTopTextString, mLeftTextString, mLeftBottomTextString);

        addView(leftView);
    }


    /**
     * 初始化CenterTextView
     */
    private void initCenterTextView() {
        if (centerView == null) {
            centerView = initBaseView(R.id.sCenterViewId);
        }
        centerBaseViewParams = getParams(centerBaseViewParams);
        centerBaseViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        centerBaseViewParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);

        //默认情况下  中间的View整体剧中显示，设置左对齐或者右对齐的话使用下边属性
        if (mCenterGravity != default_Gravity) {
            centerBaseViewParams.addRule(RIGHT_OF, R.id.sLeftViewId);
            centerBaseViewParams.addRule(LEFT_OF, R.id.sRightViewId);
        }

        centerBaseViewParams.setMargins(mCenterViewMarginLeft, 0, mCenterViewMarginRight, 0);

        centerView.setLayoutParams(centerBaseViewParams);
        centerView.setCenterSpaceHeight(centerSpaceHeight);

        setDefaultColor(centerView, mCenterTopTextColor, mCenterTextColor, mCenterBottomTextColor);
        setDefaultSize(centerView, mCenterTopTextSize, mCenterTextSize, mCenterBottomTextSize);
        setDefaultLines(centerView, mCenterTopLines, mCenterLines, mCenterBottomLines);
        setDefaultMaxEms(centerView, mCenterTopMaxEms, mCenterMaxEms, mCenterBottomMaxEms);
//        setDefaultTextIsBold(centerView, mCenterTopTextBold, mCenterTextBold, mCenterBottomTextBold);
        setDefaultGravity(centerView, mCenterGravity);
        setDefaultTextGravity(centerView, mCenterTextGravity);
        setDefaultDrawable(centerView.getCenterTextView(), mCenterTvDrawableLeft, mCenterTvDrawableRight, mTextViewDrawablePadding, mCenterTvDrawableWidth, mCenterTvDrawableHeight);
        setDefaultBackground(centerView.getCenterTextView(), mCenterTextBackground);
        setDefaultString(centerView, mCenterTopTextString, mCenterTextString, mCenterBottomTextString);

        addView(centerView);
    }

    /**
     * 初始化RightTextView
     */
    private void initRightTextView() {
        if (rightView == null) {
            rightView = initBaseView(R.id.sRightViewId);
        }
        rightBaseViewParams = getParams(rightBaseViewParams);
        rightBaseViewParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);

        rightBaseViewParams.addRule(RelativeLayout.LEFT_OF, R.id.sRightImgId);
        rightBaseViewParams.setMargins(mRightViewMarginLeft, 0, mRightViewMarginRight, 0);

        rightView.setLayoutParams(rightBaseViewParams);
        rightView.setCenterSpaceHeight(centerSpaceHeight);

        setDefaultColor(rightView, mRightTopTextColor, mRightTextColor, mRightBottomTextColor);
        setDefaultSize(rightView, mRightTopTextSize, mRightTextSize, mRightBottomTextSize);
        setDefaultLines(rightView, mRightTopLines, mRightLines, mRightBottomLines);
        setDefaultMaxEms(rightView, mRightTopMaxEms, mRightMaxEms, mRightBottomMaxEms);
        setDefaultTextIsBold(rightView, mRightTopTextBold, mRightTextBold, mRightBottomTextBold);
        setDefaultGravity(rightView, mRightGravity);
        setDefaultTextGravity(rightView, mRightTextGravity);
        setDefaultDrawable(rightView.getCenterTextView(), mRightTvDrawableLeft, mRightTvDrawableRight, mTextViewDrawablePadding, mRightTvDrawableWidth, mRightTvDrawableHeight);
        setDefaultBackground(rightView.getCenterTextView(), mRightTextBackground);
        setDefaultString(rightView, mRightTopTextString, mRightTextString, mRightBottomTextString);

        addView(rightView);
    }


    /**
     * 初始化RightCheckBox
     */
    private void initRightCheckBox() {
        if (rightCheckBox == null) {
            rightCheckBox = new AppCompatCheckBox(mContext);
        }
        rightCheckBoxParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        rightCheckBoxParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        rightCheckBoxParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        rightCheckBoxParams.setMargins(0, 0, rightCheckBoxMarginRight, 0);
        rightCheckBox.setId(R.id.sRightCheckBoxId);
        rightCheckBox.setLayoutParams(rightCheckBoxParams);
        if (rightCheckBoxBg != null) {
            rightCheckBox.setGravity(CENTER_IN_PARENT);
            rightCheckBox.setButtonDrawable(rightCheckBoxBg);
        }
        rightCheckBox.setChecked(isChecked);
        rightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxCheckedChangeListener != null) {
                    checkBoxCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
                }
            }
        });
        addView(rightCheckBox);
    }

    /**
     * 初始化RightSwitch
     */
    private void initRightSwitch() {
        if (mSwitch == null) {
            mSwitch = new SwitchCompat(mContext);
        }
        mSwitchParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        mSwitchParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        mSwitchParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        mSwitchParams.setMargins(0, 0, rightSwitchMarginRight, 0);
        mSwitch.setId(R.id.sRightSwitchId);
        mSwitch.setLayoutParams(mSwitchParams);

        mSwitch.setChecked(switchIsChecked);
        if (!TextUtils.isEmpty(mTextOff)) {
            mSwitch.setTextOff(mTextOff);
            mSwitch.setShowText(true);
        }
        if (!TextUtils.isEmpty(mTextOn)) {
            mSwitch.setTextOn(mTextOn);
            mSwitch.setShowText(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mSwitchMinWidth != 0) {
                mSwitch.setSwitchMinWidth(mSwitchMinWidth);
            }
            if (mSwitchPadding != 0) {
                mSwitch.setSwitchPadding(mSwitchPadding);
            }
            if (mThumbResource != null) {
                mSwitch.setThumbDrawable(mThumbResource);
            }
            if (mThumbResource != null) {
                mSwitch.setTrackDrawable(mTrackResource);
            }
            if (mThumbTextPadding != 0) {
                mSwitch.setThumbTextPadding(mThumbTextPadding);
            }

        }
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchCheckedChangeListener != null) {
                    switchCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
                }
            }
        });

        addView(mSwitch);
    }


    private void initRightEditText() {

        if (mEditText == null) {
            mEditText = new AppCompatEditText(mContext);
            mEditText.setSingleLine(true);
            mEditText.setGravity(Gravity.END);
            mEditText.setImeOptions(mEditImeOption);
            mEditText.setInputType(mEditInputType);
            mEditText.setBackgroundDrawable(null);
            mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX,mEditTextSize);
            mEditText.setCursorVisible(mEditCusorVisible);
            //setFocusable(true);
            //setFocusableInTouchMode(true);
//            EditTextHelper.INSTANCE.setCursorDrawable(mEditText,mEditCusorDrawableRes);
        }

        if(mRightEditHintTextColor!=null){
            mEditText.setHintTextColor(mRightEditHintTextColor);
        }
        if(mRightEditTextColor!=null){
            mEditText.setTextColor(mRightEditTextColor);
        }
        if(mEditHintText!=null){
            mEditText.setHint(mEditHintText);
        }

        if(mEditMinWidth==0){
            mEditParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }else{
            mEditText.setMinWidth(mEditMinWidth);
            mEditParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }


        mEditParams.addRule(RelativeLayout.LEFT_OF, R.id.sRightImgId);
        mEditParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        mEditParams.setMargins(0, 0, rightEditMarginRight, 0);
        mEditText.setId(R.id.sRightEditTextId);
        mEditText.setLayoutParams(mEditParams);

        addView(mEditText);
        //setFocusable(false);
        //setFocusableInTouchMode(false);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (editTextChangeListener != null) {
                    editTextChangeListener.beforeTextChanged(s,start,count,after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editTextChangeListener != null) {
                    editTextChangeListener.onTextChanged(s,start,before,count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextChangeListener != null) {
                    editTextChangeListener.afterTextChanged(s);
                }
            }
        });



        if(editActiveLineColor!=-1){
            mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        setBottomDividerLineColor(editActiveLineColor);
                    }else{
                        setBottomDividerLineColor(mDividerLineColor);
                    }
                }
            });
        }



    }

    /////////////////////////////////////默认属性设置----begin/////////////////////////////////

    /**
     * 设置圆形ImageView属性
     *
     * @param circleImageView               view
     * @param disableCircularTransformation 是否允许圆形转换  默认true
     */
    private void setCircleImage(CircleImageView circleImageView, boolean disableCircularTransformation) {
        circleImageView.setDisableCircularTransformation(!disableCircularTransformation);
    }

    /**
     * 初始化BaseTextView
     *
     * @param id id
     * @return baseTextView
     */
    private BaseTextView initBaseView(int id) {
        BaseTextView baseTextView = new BaseTextView(mContext);
        baseTextView.setId(id);
        return baseTextView;
    }

    /**
     * 设置默认值
     *
     * @param baseTextView     baseTextView
     * @param topTextString    topTextString
     * @param leftTextString   leftTextString
     * @param bottomTextString bottomTextString
     */
    private void setDefaultString(BaseTextView baseTextView, String topTextString, String leftTextString, String bottomTextString) {
        if (baseTextView != null) {
            baseTextView.setTopTextString(topTextString);
            baseTextView.setCenterTextString(leftTextString);
            baseTextView.setBottomTextString(bottomTextString);
        }
    }

    /**
     * 设置默认
     *
     * @param baseTextView    baseTextView
     * @param topTextColor    topTextColor
     * @param textColor       textColor
     * @param bottomTextColor bottomTextColor
     */
    private void setDefaultColor(BaseTextView baseTextView, ColorStateList topTextColor, ColorStateList textColor, ColorStateList bottomTextColor) {
        if (baseTextView != null) {
            if (topTextColor == null) {
                topTextColor = ColorStateList.valueOf(defaultColor);
            }
            if (textColor == null) {
                textColor = ColorStateList.valueOf(defaultColor);
            }
            if (bottomTextColor == null) {
                bottomTextColor = ColorStateList.valueOf(defaultColor);
            }
            baseTextView.getTopTextView().setTextColor(topTextColor);
            baseTextView.getCenterTextView().setTextColor(textColor);
            baseTextView.getBottomTextView().setTextColor(bottomTextColor);
        }
    }

    /**
     * 设置默认字体大小
     *
     * @param baseTextView   baseTextView
     * @param leftTextSize   leftTextSize
     * @param topTextSize    topTextSize
     * @param bottomTextSize bottomTextSize
     */
    private void setDefaultSize(BaseTextView baseTextView, int topTextSize, int leftTextSize, int bottomTextSize) {
        if (baseTextView != null) {
            baseTextView.getTopTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, topTextSize);
            baseTextView.getCenterTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
            baseTextView.getBottomTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, bottomTextSize);
        }
    }

    /**
     * 设置默认maxEms
     *
     * @param baseTextView baseTextView
     * @param topMaxEms    topMaxEms
     * @param centerMaxEms centerMaxEms
     * @param bottomMaxEms bottomMaxEms
     */
    private void setDefaultMaxEms(BaseTextView baseTextView, int topMaxEms, int centerMaxEms, int bottomMaxEms) {
        if (baseTextView != null) {
            baseTextView.setMaxEms(topMaxEms, centerMaxEms, bottomMaxEms);
        }

    }

    /**
     * 设置默认lines
     *
     * @param baseTextView baseTextView
     * @param leftTopLines leftTopLines
     * @param leftLines    leftLines
     * @param bottomLines  bottomLines
     */
    private void setDefaultLines(BaseTextView baseTextView, int leftTopLines, int leftLines, int bottomLines) {
        if (baseTextView != null) {
            if (leftTopLines != 0) {
                baseTextView.getTopTextView().setMaxLines(leftTopLines);
            }
            if (leftLines != 0) {
                baseTextView.getCenterTextView().setMaxLines(leftLines);
            }
            if (bottomLines != 0) {
                baseTextView.getBottomTextView().setMaxLines(bottomLines);
            }
        }

    }

    /**
     * 设置文字对其方式
     *
     * @param baseTextView baseTextView
     * @param gravity      对其方式
     */
    private void setDefaultGravity(BaseTextView baseTextView, int gravity) {
        if (baseTextView != null) {
            setGravity(baseTextView, gravity);
        }
    }

    /**
     * 文字对其方式
     *
     * @param baseTextView textView
     * @param gravity      对其方式
     */
    private void setGravity(BaseTextView baseTextView, int gravity) {
        switch (gravity) {
            case gravity_Left_Center:
                baseTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case gravity_Center:
                baseTextView.setGravity(Gravity.CENTER);
                break;
            case gravity_Right_Center:
                baseTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
        }
    }

    /**
     * 设置文字对其方式
     *
     * @param baseTextView baseTextView
     * @param gravity      对其方式
     */
    private void setDefaultTextGravity(BaseTextView baseTextView, int gravity) {
        if (baseTextView != null) {
            switch (gravity) {
                case text_gravity_Left:
                    setTextGravity(baseTextView, Gravity.LEFT);
                    break;
                case text_gravity_center:
                    setTextGravity(baseTextView, Gravity.CENTER);
                    break;
                case text_gravity_right:
                    setTextGravity(baseTextView, Gravity.RIGHT);
                    break;
            }
        }
    }


    /**
     * 设置textView的drawable
     *
     * @param textView        对象
     * @param drawableLeft    左边图标
     * @param drawableRight   右边图标
     * @param drawablePadding 图标距离文字的间距
     */
    public void setDefaultDrawable(AppCompatTextView textView, Drawable drawableLeft, Drawable drawableRight, int drawablePadding, int drawableWidth, int drawableHeight) {
        if (drawableLeft != null || drawableRight != null) {
            textView.setVisibility(VISIBLE);
        }
        //可以指定drawable的宽高
        if (drawableWidth != -1 && drawableHeight != -1) {
            if (drawableLeft != null) {
                drawableLeft.setBounds(0, 0, drawableWidth, drawableHeight);
            }
            if (drawableRight != null) {
                drawableRight.setBounds(0, 0, drawableWidth, drawableHeight);
            }
            textView.setCompoundDrawables(drawableLeft, null, drawableRight, null);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null);
        }
        textView.setCompoundDrawablePadding(drawablePadding);
    }

    /**
     * 设置textView的背景，用户传入drawable实现圆角之类的样式
     *
     * @param textView
     * @param background
     */
    private void setDefaultBackground(AppCompatTextView textView, Drawable background) {
        if (background != null) {
            textView.setVisibility(VISIBLE);
            if (Build.VERSION.SDK_INT < 16) {
                textView.setBackgroundDrawable(background);
            } else {
                textView.setBackground(background);
            }
        }
    }


    /**
     * 左边点击事件
     *
     * @param baseTextView baseTextView
     */
    private void setDefaultLeftViewClickListener(BaseTextView baseTextView) {
        if (baseTextView != null) {
            if (leftTopTvClickListener != null) {
                baseTextView.getTopTextView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leftTopTvClickListener.onClickListener();
                    }
                });
            }

            if (leftTvClickListener != null) {
                baseTextView.getCenterTextView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leftTvClickListener.onClickListener();
                    }
                });
            }
            if (leftBottomTvClickListener != null) {
                baseTextView.getBottomTextView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leftBottomTvClickListener.onClickListener();
                    }
                });
            }
        }

    }

    /**
     * 中间点击事件
     *
     * @param baseTextView baseTextView
     */
    private void setDefaultCenterViewClickListener(BaseTextView baseTextView) {
        if (baseTextView != null) {
            if (centerTopTvClickListener != null) {
                baseTextView.getTopTextView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        centerTopTvClickListener.onClickListener();
                    }
                });
            }

            if (centerTvClickListener != null) {
                baseTextView.getCenterTextView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        centerTvClickListener.onClickListener();
                    }
                });
            }
            if (centerBottomTvClickListener != null) {
                baseTextView.getBottomTextView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        centerBottomTvClickListener.onClickListener();
                    }
                });
            }
        }

    }


    /**
     * 右边点击事件
     *
     * @param baseTextView baseTextView
     */
    private void setDefaultRightViewClickListener(BaseTextView baseTextView) {
        if (baseTextView != null) {
            if (rightTopTvClickListener != null) {
                baseTextView.getTopTextView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightTopTvClickListener.onClickListener();
                    }
                });
            }

            if (rightTvClickListener != null) {
                baseTextView.getCenterTextView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightTvClickListener.onClickListener();
                    }
                });
            }
            if (rightBottomTvClickListener != null) {
                baseTextView.getBottomTextView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightBottomTvClickListener.onClickListener();
                    }
                });
            }
        }

    }


    /**
     * 字体是否加粗
     *
     * @param baseTextView   baseTextView
     * @param topTextBold    上边字体加粗
     * @param centerTextBold 中间字体加粗
     * @param bottomTextBold 下边字体加粗
     */
    private void setDefaultTextIsBold(BaseTextView baseTextView, boolean topTextBold, boolean centerTextBold, boolean bottomTextBold) {
        if (baseTextView != null) {
            baseTextView.getTopTextView().getPaint().setFakeBoldText(topTextBold);
            baseTextView.getCenterTextView().getPaint().setFakeBoldText(centerTextBold);
            baseTextView.getBottomTextView().getPaint().setFakeBoldText(bottomTextBold);
        }
    }


    /////////////////////////////////////默认属性设置----end/////////////////////////////////


    /////////////////////////////////////对外暴露的方法---begin/////////////////////////////////

    /**
     * 设置左上字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    public ICareSuperTextView setLeftTopString(CharSequence string) {
        if (leftView != null) {
            leftView.setTopTextString(string);
        }
        return this;
    }

    /**
     * 设置左中字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    public ICareSuperTextView setLeftString(CharSequence string) {
        if (leftView != null) {
            leftView.setCenterTextString(string);
        }
        return this;
    }
    public ICareSuperTextView setEditString(CharSequence string) {
        if (mEditText != null) {
            mEditText.setText(string);
        }
        return this;
    }

    /**
     * 设置左下字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    public ICareSuperTextView setLeftBottomString(CharSequence string) {
        if (leftView != null) {
            leftView.setBottomTextString(string);
        }
        return this;
    }


    /**
     * 设置中上字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    public ICareSuperTextView setCenterTopString(CharSequence string) {
        if (centerView != null) {
            centerView.setTopTextString(string);
        }
        return this;
    }

    /**
     * 设置中间字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    public ICareSuperTextView setCenterString(CharSequence string) {
        if (centerView != null) {
            centerView.setCenterTextString(string);
        }
        return this;
    }

    /**
     * 设置中下字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    public ICareSuperTextView setCenterBottomString(CharSequence string) {
        if (centerView != null) {
            centerView.setBottomTextString(string);
        }
        return this;
    }

    /**
     * 设置右上字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    public ICareSuperTextView setRightTopString(CharSequence string) {
        if (rightView != null) {
            rightView.setTopTextString(string);
        }
        return this;
    }

    /**
     * 设置右中字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    public ICareSuperTextView setRightString(CharSequence string) {
        if (rightView != null) {
            rightView.setCenterTextString(string);
        }
        return this;
    }

    /**
     * 设置右下字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    public ICareSuperTextView setRightBottomString(CharSequence string) {
        if (rightView != null) {
            rightView.setBottomTextString(string);
        }
        return this;
    }

    /**
     * 设置左上文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftTopTextColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setLeftTopTextColor(colorStateList);
        return this;
    }

    /**
     * 设置左上文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftTopTextColor(ColorStateList color) {
        if (leftView != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            leftView.getTopTextView().setTextColor(color);
        }
        return this;
    }

    /**
     * 设置左中文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftTextColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setLeftTextColor(colorStateList);
        return this;
    }

    /**
     * 设置左中文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftTextColor(ColorStateList color) {
        if (leftView != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            leftView.getCenterTextView().setTextColor(color);
        }
        return this;
    }


    /**
     * 设置左下文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftBottomTextColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setLeftBottomTextColor(colorStateList);
        return this;
    }

    /**
     * 设置左下文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftBottomTextColor(ColorStateList color) {
        if (leftView != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            leftView.getBottomTextView().setTextColor(color);
        }
        return this;
    }

    /**
     * 设置中上文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterTopTextColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setCenterTopTextColor(colorStateList);
        return this;
    }

    /**
     * 设置中上文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterTopTextColor(ColorStateList color) {
        if (centerView != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            centerView.getTopTextView().setTextColor(color);
        }
        return this;
    }

    /**
     * 设置中间文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterTextColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setCenterTextColor(colorStateList);
        return this;
    }

    /**
     * 设置中间文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterTextColor(ColorStateList color) {
        if (centerView != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            centerView.getCenterTextView().setTextColor(color);
        }
        return this;
    }

    /**
     * 设置中下文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterBottomTextColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setCenterBottomTextColor(colorStateList);
        return this;
    }

    /**
     * 设置中下文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterBottomTextColor(ColorStateList color) {
        if (centerView != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            centerView.getBottomTextView().setTextColor(color);
        }
        return this;
    }

    /**
     * 设置右上文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setRightTopTextColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setRightTopTextColor(colorStateList);
        return this;
    }

    /**
     * 设置右上文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setRightTopTextColor(ColorStateList color) {
        if (rightView != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            rightView.getTopTextView().setTextColor(color);
        }
        return this;
    }

    /**
     * 设置右中文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setRightTextColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setRightTextColor(colorStateList);
        return this;
    }

    /**
     * 设置右中文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setRightTextColor(ColorStateList color) {
        if (rightView != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            rightView.getCenterTextView().setTextColor(color);
        }
        return this;
    }

    /**
     * 设置右下文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setRightBottomTextColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setRightBottomTextColor(colorStateList);
        return this;
    }

    /**
     * 设置右下文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    public ICareSuperTextView setRightBottomTextColor(ColorStateList color) {
        if (rightView != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            rightView.getBottomTextView().setTextColor(color);
        }
        return this;
    }

    /**
     * 设置左上文字字体为粗体
     *
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftTopTextIsBold(boolean isBold) {
        if (leftView != null) {
            leftView.setFakeBoldText(leftView.getTopTextView(), isBold);
        }
        return this;
    }

    /**
     * 设置左中文字字体为粗体
     *
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftTextIsBold(boolean isBold) {
        if (leftView != null) {
            leftView.setFakeBoldText(leftView.getCenterTextView(), isBold);
        }
        return this;
    }

    /**
     * 设置左下文字字体为粗体
     *
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftBottomTextIsBold(boolean isBold) {
        if (leftView != null) {
            leftView.setFakeBoldText(leftView.getBottomTextView(), isBold);
        }
        return this;
    }

    /**
     * 设置中上文字字体为粗体
     *
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterTopTextIsBold(boolean isBold) {
        if (centerView != null) {
            centerView.setFakeBoldText(centerView.getTopTextView(), isBold);
        }
        return this;
    }

    /**
     * 设置中中文字字体为粗体
     *
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterTextIsBold(boolean isBold) {
        if (centerView != null) {
            centerView.setFakeBoldText(centerView.getCenterTextView(), isBold);
        }
        return this;
    }

    /**
     * 设置中下文字字体为粗体
     *
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterBottomTextIsBold(boolean isBold) {
        if (centerView != null) {
            centerView.setFakeBoldText(centerView.getBottomTextView(), isBold);
        }
        return this;
    }

    /**
     * 设置右上文字字体为粗体
     *
     * @return SuperTextView
     */
    public ICareSuperTextView setRightTopTextIsBold(boolean isBold) {
        if (rightView != null) {
            rightView.setFakeBoldText(rightView.getTopTextView(), isBold);
        }
        return this;
    }

    /**
     * 设置右中文字字体为粗体
     *
     * @return SuperTextView
     */
    public ICareSuperTextView setRightTextIsBold(boolean isBold) {
        if (rightView != null) {
            rightView.setFakeBoldText(rightView.getCenterTextView(), isBold);
        }
        return this;
    }

    /**
     * 设置右下文字字体为粗体
     *
     * @return SuperTextView
     */
    public ICareSuperTextView setRightBottomTextIsBold(boolean isBold) {
        if (rightView != null) {
            rightView.setFakeBoldText(rightView.getBottomTextView(), isBold);
        }
        return this;
    }

    /**
     * 获取左上字符串
     *
     * @return 返回字符串
     */
    public String getLeftTopString() {
        return leftView != null ? leftView.getTopTextView().getText().toString().trim() : "";
    }

    /**
     * 获取左中字符串
     *
     * @return 返回字符串
     */
    public String getLeftString() {
        return leftView != null ? leftView.getCenterTextView().getText().toString().trim() : "";
    }

    /**
     * 获取左下字符串
     *
     * @return 返回字符串
     */
    public String getLeftBottomString() {
        return leftView != null ? leftView.getBottomTextView().getText().toString().trim() : "";
    }

    ////////////////////////////////////////////

    /**
     * 获取中上字符串
     *
     * @return 返回字符串
     */
    public String getCenterTopString() {
        return centerView != null ? centerView.getTopTextView().getText().toString().trim() : "";
    }

    /**
     * 获取中间字符串
     *
     * @return 返回字符串
     */

    public String getCenterString() {
        return centerView != null ? centerView.getCenterTextView().getText().toString().trim() : "";
    }

    /**
     * 获取中下字符串
     *
     * @return 返回字符串
     */
    public String getCenterBottomString() {
        return centerView != null ? centerView.getBottomTextView().getText().toString().trim() : "";
    }

    /**
     * 获取右上字符串
     *
     * @return 返回字符串
     */
    public String getRightTopString() {
        return rightView != null ? rightView.getTopTextView().getText().toString().trim() : "";
    }

    /**
     * 获取右中字符串
     *
     * @return 返回字符串
     */
    public String getRightString() {
        return rightView != null ? rightView.getCenterTextView().getText().toString().trim() : "";
    }

    /**
     * 获取右下字符串
     *
     * @return 返回字符串
     */
    public String getRightBottomString() {
        return rightView != null ? rightView.getBottomTextView().getText().toString().trim() : "";
    }

    /**
     * 获取左边ImageView
     *
     * @return ImageView
     */
    public ImageView getLeftIconIV() {
        leftImgParams.setMargins(leftIconMarginLeft, 0, 0, 0);
        return leftIconIV;
    }

    /**
     * 获取右边ImageView
     *
     * @return ImageView
     */
    public ImageView getRightIconIV() {
        rightImgParams.setMargins(0, 0, rightIconMarginRight, 0);
        return rightIconIV;
    }


    /**
     * 获取rightCheckBox
     *
     * @return rightCheckBox
     */
    public CheckBox getCheckBox() {
        return rightCheckBox;
    }

    /**
     * @param checked 是否选中
     * @return 返回值
     */
    public ICareSuperTextView setCbChecked(boolean checked) {
        isChecked = checked;
        if (rightCheckBox != null) {
            rightCheckBox.setChecked(checked);
        }
        return this;
    }

    /**
     * @param clickable 是否允许点击
     * @return 返回值
     */
    public ICareSuperTextView setCbClickable(boolean clickable) {
        if (rightCheckBox != null) {
            rightCheckBox.setClickable(clickable);
        }
        return this;
    }


    /**
     * 设置checkbox的背景图
     *
     * @param drawable drawable对象
     * @return 返回对象
     */
    public ICareSuperTextView setCbBackground(Drawable drawable) {
        rightCheckBoxBg = drawable;
        if (rightCheckBox != null) {
            rightCheckBox.setBackgroundDrawable(drawable);
        }
        return this;
    }

    /**
     * 获取checkbox状态
     *
     * @return 返回选择框当前选中状态
     */
    public boolean getCbisChecked() {
        boolean isChecked = false;
        if (rightCheckBox != null) {
            isChecked = rightCheckBox.isChecked();
        }
        return isChecked;
    }

    /**
     * @param checked Switch是否选中
     * @return 返回值
     */
    public ICareSuperTextView setSwitchIsChecked(boolean checked) {
        switchIsChecked = checked;
        if (mSwitch != null) {
            mSwitch.setChecked(checked);
        }
        return this;
    }

    /**
     * @param clickable Switch是否允许点击
     * @return 返回值
     */
    public ICareSuperTextView setSwitchClickable(boolean clickable) {
        if (mSwitch != null) {
            mSwitch.setClickable(clickable);
        }
        return this;
    }

    /**
     * 获取switch状态
     *
     * @return 返回switch当前选中状态
     */
    public boolean getSwitchIsChecked() {
        boolean isChecked = false;
        if (mSwitch != null) {
            isChecked = mSwitch.isChecked();
        }
        return isChecked;
    }

    /**
     * 获取switchView
     *
     * @return Switch
     */
    public SwitchCompat getSwitch() {
        return mSwitch;
    }

    /**
     * 获取Edittext View
     *
     * @return AppCompatEditText
     */
    public AppCompatEditText getEditText(){
        return mEditText;
    }

    public ICareSuperTextView setEditImeOptions(int option){
        if (mEditText != null) {
            mEditText.setImeOptions(option);
        }
        return this;
    }

    public ICareSuperTextView setEditInputType(int inputType){
        if (mEditText != null) {
            mEditText.setInputType(inputType);
        }
        return this;
    }

    public ICareSuperTextView setEditHintText(String hintText){
        if (mEditText != null) {
            mEditText.setHint(hintText);
        }
        return this;
    }


    public ICareSuperTextView setEditHintTextColor(int color){
        if (mEditText != null) {
            setEditHintTextColor(ColorStateList.valueOf(color));
        }
        return this;
    }

    public ICareSuperTextView setEditHintTextColor(ColorStateList color){
        if (mEditText != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            mEditText.setHintTextColor(color);
        }
        return this;
    }


    public ICareSuperTextView setEditTextColor(int color){
        if (mEditText != null) {
            setEditTextColor(ColorStateList.valueOf(color));
        }
        return this;
    }

    public ICareSuperTextView setEditTextColor(ColorStateList color){
        if (mEditText != null) {
            if (color == null) {
                throw new NullPointerException();
            }
            mEditText.setTextColor(color);
        }
        return this;
    }

    public ICareSuperTextView setEditCursorVisible(boolean visible){
        if (mEditText != null) {
            mEditText.setCursorVisible(visible);
        }
        return this;
    }


    public ICareSuperTextView setEditCursorDrawableRes(@DrawableRes int drawableRes){
        if (mEditText != null) {
           EditTextHelper.INSTANCE.setCursorDrawable(mEditText,drawableRes);
        }
        return this;
    }


    /**
     * 设置左边tv的左侧图片
     *
     * @param drawableLeft 左边图片资源
     */
    public ICareSuperTextView setLeftTvDrawableLeft(Drawable drawableLeft) {
        setDefaultDrawable(leftView.getCenterTextView(), drawableLeft, null, mTextViewDrawablePadding, mLeftTvDrawableWidth, mLeftTvDrawableHeight);
        return this;
    }

    /**
     * 设置左边tv的右侧图片
     *
     * @param drawableRight 右边图片资源
     */
    public ICareSuperTextView setLeftTvDrawableRight(Drawable drawableRight) {
        setDefaultDrawable(leftView.getCenterTextView(), null, drawableRight, mTextViewDrawablePadding, mLeftTvDrawableWidth, mLeftTvDrawableHeight);
        return this;
    }


    /**
     * 设置中间tv的左侧图片
     *
     * @param drawableLeft 左边图片资源
     */
    public ICareSuperTextView setCenterTvDrawableLeft(Drawable drawableLeft) {
        setDefaultDrawable(centerView.getCenterTextView(), drawableLeft, null, mTextViewDrawablePadding, mCenterTvDrawableWidth, mCenterTvDrawableHeight);
        return this;
    }


    /**
     * 设置中间tv的右侧图片
     *
     * @param drawableRight 右边图片资源
     */
    public ICareSuperTextView setCenterTvDrawableRight(Drawable drawableRight) {
        setDefaultDrawable(centerView.getCenterTextView(), null, drawableRight, mTextViewDrawablePadding, mCenterTvDrawableWidth, mCenterTvDrawableHeight);
        return this;
    }


    /**
     * 设置右边tv的左侧图片
     *
     * @param drawableLeft 左边图片资源
     */
    public ICareSuperTextView setRightTvDrawableLeft(Drawable drawableLeft) {
        setDefaultDrawable(rightView.getCenterTextView(), drawableLeft, null, mTextViewDrawablePadding, mRightTvDrawableWidth, mRightTvDrawableHeight);
        return this;
    }

    /**
     * 设置右边tv的右侧图片
     *
     * @param drawableRight 右边图片资源
     */
    public ICareSuperTextView setRightTvDrawableRight(Drawable drawableRight) {
        setDefaultDrawable(rightView.getCenterTextView(), null, drawableRight, mTextViewDrawablePadding, mRightTvDrawableWidth, mRightTvDrawableHeight);
        return this;
    }

    /**
     * 设置左边图标
     *
     * @param leftIcon 左边图标
     * @return 返回对象
     */
    public ICareSuperTextView setLeftIcon(Drawable leftIcon) {
        if (leftIconIV != null) {
            leftImgParams.setMargins(leftIconMarginLeft, 0, 0, 0);
            leftIconIV.setImageDrawable(leftIcon);
        }
        return this;
    }

    /**
     * 设置左边图标
     *
     * @param resId 左边图标资源id
     * @return 返回对象
     */
    public ICareSuperTextView setLeftIcon(int resId) {
        if (leftIconIV != null) {
            leftImgParams.setMargins(leftIconMarginLeft, 0, 0, 0);
            leftIconIV.setImageResource(resId);
        }
        return this;
    }

    /**
     * 设置右边图标
     *
     * @param rightIcon 右边图标
     * @return 返回对象
     */
    public ICareSuperTextView setRightIcon(Drawable rightIcon) {
        if (rightIconIV != null) {
            rightImgParams.setMargins(0, 0, rightIconMarginRight, 0);
            rightIconIV.setImageDrawable(rightIcon);
        }
        return this;
    }

    /**
     * 设置右边图标资源Id
     *
     * @param resId 右边图标
     * @return 返回对象
     */
    public ICareSuperTextView setRightIcon(int resId) {
        if (rightIconIV != null) {
            rightImgParams.setMargins(0, 0, rightIconMarginRight, 0);
            rightIconIV.setImageResource(resId);
        }
        return this;
    }

    /**
     * 设置背景
     *
     * @param drawable 背景资源
     * @return 对象
     */
    public ICareSuperTextView setSBackground(Drawable drawable) {
        if (drawable != null) {
            this.setBackgroundDrawable(drawable);
        }
        return this;
    }

    /**
     * 获取左上的TextView
     *
     * @return textView
     */
    public AppCompatTextView getLeftTopTextView() {
        if (leftView == null) {
            initLeftTextView();
        }
        return leftView.getTopTextView();
    }

    /**
     * 获取左中的TextView
     *
     * @return textView
     */
    public AppCompatTextView getLeftTextView() {
        if (leftView == null) {
            initLeftTextView();
        }
        return leftView.getCenterTextView();
    }

    /**
     * 获取左下的TextView
     *
     * @return textView
     */
    public AppCompatTextView getLeftBottomTextView() {
        if (leftView == null) {
            initLeftTextView();
        }
        return leftView.getBottomTextView();
    }

    /**
     * 获取中上的TextView
     *
     * @return textView
     */
    public AppCompatTextView getCenterTopTextView() {
        if (centerView == null) {
            initCenterTextView();
        }
        return centerView.getTopTextView();
    }

    /**
     * 获取中中的TextView
     *
     * @return textView
     */
    public AppCompatTextView getCenterTextView() {
        if (centerView == null) {
            initCenterTextView();
        }
        return centerView.getCenterTextView();
    }

    /**
     * 获取中下的TextView
     *
     * @return textView
     */
    public AppCompatTextView getCenterBottomTextView() {
        if (centerView == null) {
            initCenterTextView();
        }
        return centerView.getBottomTextView();
    }

    /**
     * 获取右上的TextView
     *
     * @return textView
     */
    public AppCompatTextView getRightTopTextView() {
        if (rightView == null) {
            initRightTextView();
        }
        return rightView.getTopTextView();
    }

    /**
     * 获取右中的TextView
     *
     * @return textView
     */
    public AppCompatTextView getRightTextView() {
        if (rightView == null) {
            initRightTextView();
        }
        return rightView.getCenterTextView();
    }

    /**
     * 获取右下的TextView
     *
     * @return textView
     */
    public AppCompatTextView getRightBottomTextView() {
        if (rightView == null) {
            initRightTextView();
        }
        return rightView.getBottomTextView();
    }

    /**
     * 设置左边textView文字对齐方式
     *
     * @param gravity 对齐方式
     * @return SuperTextView
     */
    public ICareSuperTextView setLeftTextGravity(int gravity) {
        setTextGravity(leftView, gravity);
        return this;
    }

    /**
     * 设置中间textView文字对齐方式
     *
     * @param gravity 对齐方式
     * @return SuperTextView
     */
    public ICareSuperTextView setCenterTextGravity(int gravity) {
        setTextGravity(centerView, gravity);
        return this;
    }

    /**
     * 设置右边textView文字对齐方式
     *
     * @param gravity 对齐方式
     * @return SuperTextView
     */
    public ICareSuperTextView setRightTextGravity(int gravity) {
        setTextGravity(rightView, gravity);
        return this;
    }

    /**
     * 文字对齐方式
     *
     * @param baseTextView view
     * @param gravity      对齐方式
     */
    private void setTextGravity(BaseTextView baseTextView, int gravity) {
        if (baseTextView != null) {
            baseTextView.getTopTextView().setGravity(gravity);
            baseTextView.getCenterTextView().setGravity(gravity);
            baseTextView.getBottomTextView().setGravity(gravity);
        }
    }

    /**
     * 代码动态设置分割线显示类型
     *
     * @param dividerType TOP、BOTTOM、BOTH、NONE
     * @return this
     */
    public ICareSuperTextView setDividerLineType(int dividerType) {
        mDividerLineType = dividerType;
        invalidate();
        return this;
    }

    public ICareSuperTextView setTopDividerLineColor(int lineColor) {
        mTopDividerPaint.setColor(lineColor);
        invalidate();
        return this;
    }

    public ICareSuperTextView setBottomDividerLineColor(int lineColor) {
        mBottomDividerPaint.setColor(lineColor);
        invalidate();
        return this;
    }
    /////////////////////////////////////对外暴露的方法---end/////////////////////////////////


    /**
     * 点击事件
     *
     * @param onSuperTextViewClickListener ClickListener
     * @return SuperTextView
     */
    public ICareSuperTextView setOnSuperTextViewClickListener(OnSuperTextViewClickListener onSuperTextViewClickListener) {
        this.superTextViewClickListener = onSuperTextViewClickListener;
        if (superTextViewClickListener != null) {
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    superTextViewClickListener.onClickListener(ICareSuperTextView.this);
                }
            });
        }
        return this;
    }

    public ICareSuperTextView setLeftTopTvClickListener(OnLeftTopTvClickListener leftTopTvClickListener) {
        this.leftTopTvClickListener = leftTopTvClickListener;
        setDefaultLeftViewClickListener(leftView);
        return this;
    }

    public ICareSuperTextView setLeftTvClickListener(OnLeftTvClickListener leftTvClickListener) {
        this.leftTvClickListener = leftTvClickListener;
        setDefaultLeftViewClickListener(leftView);
        return this;
    }

    public ICareSuperTextView setLeftBottomTvClickListener(OnLeftBottomTvClickListener leftBottomTvClickListener) {
        this.leftBottomTvClickListener = leftBottomTvClickListener;
        setDefaultLeftViewClickListener(leftView);
        return this;
    }

    public ICareSuperTextView setCenterTopTvClickListener(OnCenterTopTvClickListener centerTopTvClickListener) {
        this.centerTopTvClickListener = centerTopTvClickListener;
        setDefaultCenterViewClickListener(centerView);
        return this;
    }

    public ICareSuperTextView setCenterTvClickListener(OnCenterTvClickListener centerTvClickListener) {
        this.centerTvClickListener = centerTvClickListener;
        setDefaultCenterViewClickListener(centerView);
        return this;
    }

    public ICareSuperTextView setCenterBottomTvClickListener(OnCenterBottomTvClickListener centerBottomTvClickListener) {
        this.centerBottomTvClickListener = centerBottomTvClickListener;
        setDefaultCenterViewClickListener(centerView);
        return this;
    }

    public ICareSuperTextView setRightTopTvClickListener(OnRightTopTvClickListener rightTopTvClickListener) {
        this.rightTopTvClickListener = rightTopTvClickListener;
        setDefaultRightViewClickListener(rightView);
        return this;
    }

    public ICareSuperTextView setRightTvClickListener(OnRightTvClickListener rightTvClickListener) {
        this.rightTvClickListener = rightTvClickListener;
        setDefaultRightViewClickListener(rightView);
        return this;
    }

    public ICareSuperTextView setRightBottomTvClickListener(OnRightBottomTvClickListener rightBottomTvClickListener) {
        this.rightBottomTvClickListener = rightBottomTvClickListener;
        setDefaultRightViewClickListener(rightView);
        return this;
    }

    public ICareSuperTextView setLeftImageViewClickListener(OnLeftImageViewClickListener listener) {
        this.leftImageViewClickListener = listener;

        if (leftIconIV != null) {
            leftIconIV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftImageViewClickListener.onClickListener(leftIconIV);
                }
            });
        }
        return this;
    }

    public ICareSuperTextView setRightImageViewClickListener(final OnRightImageViewClickListener listener) {
        this.rightImageViewClickListener = listener;
        if (rightIconIV != null) {
            rightIconIV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightImageViewClickListener.onClickListener(rightIconIV);
                }
            });
        }
        return this;
    }

    public ICareSuperTextView setSwitchCheckedChangeListener(OnSwitchCheckedChangeListener switchCheckedChangeListener) {
        this.switchCheckedChangeListener = switchCheckedChangeListener;
        return this;
    }

    public ICareSuperTextView setCheckBoxCheckedChangeListener(OnCheckBoxCheckedChangeListener checkBoxCheckedChangeListener) {
        this.checkBoxCheckedChangeListener = checkBoxCheckedChangeListener;
        return this;
    }

    public ICareSuperTextView setEditTextChangeListener(OnEditTextChangeListener editTextChangeListener) {
        this.editTextChangeListener = editTextChangeListener;
        return this;
    }

    public ICareSuperTextView setLeftTextGroupClickListener(final OnLeftTextGroupClickListener leftTextGroupClickListener) {
        if (leftTextGroupClickListener != null) {
            leftView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftTextGroupClickListener.onClickListener(v);
                }
            });
        }
        return this;
    }

    public ICareSuperTextView setCenterTextGroupClickListener(final OnCenterTextGroupClickListener centerTextGroupClickListener) {
        if (centerTextGroupClickListener != null) {
            centerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    centerTextGroupClickListener.onClickListener(v);
                }
            });
        }
        return this;
    }

    public ICareSuperTextView setRightTextGroupClickListener(final OnRightTextGroupClickListener rightTextGroupClickListener) {
        if (rightTextGroupClickListener != null) {
            rightView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightTextGroupClickListener.onClickListener(v);
                }
            });
        }
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    public interface OnSuperTextViewClickListener {
        void onClickListener(ICareSuperTextView superTextView);
    }

    public interface OnLeftTopTvClickListener {
        void onClickListener();
    }

    public interface OnLeftTvClickListener {
        void onClickListener();
    }

    public interface OnLeftBottomTvClickListener {
        void onClickListener();
    }

    public interface OnCenterTopTvClickListener {
        void onClickListener();
    }

    public interface OnCenterTvClickListener {
        void onClickListener();
    }

    public interface OnCenterBottomTvClickListener {
        void onClickListener();
    }

    public interface OnRightTopTvClickListener {
        void onClickListener();
    }

    public interface OnRightTvClickListener {
        void onClickListener();
    }

    public interface OnRightBottomTvClickListener {
        void onClickListener();
    }

    public interface OnLeftImageViewClickListener {
        void onClickListener(ImageView imageView);
    }

    public interface OnRightImageViewClickListener {
        void onClickListener(ImageView imageView);
    }

    public interface OnSwitchCheckedChangeListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked);
    }

    public interface OnCheckBoxCheckedChangeListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked);
    }

    public interface OnEditTextChangeListener{

        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

    public interface OnLeftTextGroupClickListener {
        void onClickListener(View view);
    }

    public interface OnCenterTextGroupClickListener {
        void onClickListener(View view);
    }

    public interface OnRightTextGroupClickListener {
        void onClickListener(View view);
    }


    // TODO: 2019/6/4 以下是获取shapeBuilder进行相关shape属性的配置

    /**
     * 获取shapeBuilder进行配置
     *
     * @return ShapeBuilder
     */
    public ShapeBuilder getShapeBuilder() {
        return shapeBuilder;
    }

    /**
     * 单位转换工具类
     *
     * @param context 上下文对象
     * @param spValue 值
     * @return 返回值
     */
    private int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 单位转换工具类
     *
     * @param context  上下文对象
     * @param dipValue 值
     * @return 返回值
     */
    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}