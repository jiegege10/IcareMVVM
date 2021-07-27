package com.icare.jetpackmvvm.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.icare.jetpackmvvm.R

class ShapeLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), IShapeDrawable<ShapeLinearLayout?> {
    private var mShape: Int
    private var mShapeWidth: Int
    private var mShapeHeight: Int
    private var mSolidColor: Int
    private var mTopLeftRadius: Int
    private var mTopRightRadius: Int
    private var mBottomLeftRadius: Int
    private var mBottomRightRadius: Int
    private var mStartColor: Int
    private var mCenterColor: Int
    private var mEndColor: Int
    private var mUseLevel: Boolean
    private var mAngle: Int
    private var mGradientType: Int
    private var mCenterX: Float
    private var mCenterY: Float
    private var mGradientRadius: Int
    private var mStrokeColor: Int
    private var mStrokeWidth: Int
    private var mDashWidth: Int
    private var mDashGap: Int
    override fun setShape(shape: Int): ShapeLinearLayout {
        mShape = shape
        return this
    }

    override fun getShape(): Int {
        return mShape
    }

    override fun setShapeWidth(width: Int): ShapeLinearLayout {
        mShapeWidth = width
        return this
    }

    override fun getShapeWidth(): Int {
        return mShapeWidth
    }

    override fun setShapeHeight(height: Int): ShapeLinearLayout {
        mShapeHeight = height
        return this
    }

    override fun getShapeHeight(): Int {
        return mShapeHeight
    }

    override fun setSolidColor(color: Int): ShapeLinearLayout {
        mSolidColor = color
        return this
    }

    override fun getSolidColor(): Int {
        return mSolidColor
    }

    override fun setTopLeftRadius(radius: Int): ShapeLinearLayout {
        mTopLeftRadius = radius
        return this
    }

    override fun getTopLeftRadius(): Int {
        return mTopLeftRadius
    }

    override fun setTopRightRadius(radius: Int): ShapeLinearLayout {
        mTopRightRadius = radius
        return this
    }

    override fun getTopRightRadius(): Int {
        return mTopRightRadius
    }

    override fun setBottomLeftRadius(radius: Int): ShapeLinearLayout {
        mBottomLeftRadius = radius
        return this
    }

    override fun getBottomLeftRadius(): Int {
        return mBottomLeftRadius
    }

    override fun setBottomRightRadius(radius: Int): ShapeLinearLayout {
        mBottomRightRadius = radius
        return this
    }

    override fun getBottomRightRadius(): Int {
        return mBottomRightRadius
    }

    override fun setStartColor(color: Int): ShapeLinearLayout {
        mStartColor = color
        return this
    }

    override fun getStartColor(): Int {
        return mStartColor
    }

    override fun setCenterColor(color: Int): ShapeLinearLayout {
        mCenterColor = color
        return this
    }

    override fun getCenterColor(): Int {
        return mCenterColor
    }

    override fun setEndColor(color: Int): ShapeLinearLayout {
        mEndColor = color
        return this
    }

    override fun getEndColor(): Int {
        return mEndColor
    }

    override fun setUseLevel(useLevel: Boolean): ShapeLinearLayout {
        mUseLevel = useLevel
        return this
    }

    override fun isUseLevel(): Boolean {
        return mUseLevel
    }

    override fun setAngle(angle: Int): ShapeLinearLayout {
        mAngle = angle
        return this
    }

    override fun getAngle(): Int {
        return mAngle
    }

    override fun setGradientType(type: Int): ShapeLinearLayout {
        mGradientType = type
        return this
    }

    override fun getGradientType(): Int {
        return mGradientType
    }

    override fun setCenterX(x: Float): ShapeLinearLayout {
        mCenterX = x
        return this
    }

    override fun getCenterX(): Float {
        return mCenterX
    }

    override fun setCenterY(y: Float): ShapeLinearLayout {
        mCenterY = y
        return this
    }

    override fun getCenterY(): Float {
        return mCenterY
    }

    override fun setGradientRadius(radius: Int): ShapeLinearLayout {
        mGradientRadius = radius
        return this
    }

    override fun getGradientRadius(): Int {
        return mGradientRadius
    }

    override fun setStrokeColor(color: Int): ShapeLinearLayout {
        mStrokeColor = color
        return this
    }

    override fun getStrokeColor(): Int {
        return mStrokeColor
    }

    override fun setStrokeWidth(width: Int): ShapeLinearLayout {
        mStrokeWidth = width
        return this
    }

    override fun getStrokeWidth(): Int {
        return mStrokeWidth
    }

    override fun setDashWidth(width: Int): ShapeLinearLayout {
        mDashWidth = width
        return this
    }

    override fun getDashWidth(): Int {
        return mDashWidth
    }

    override fun setDashGap(gap: Int): ShapeLinearLayout {
        mDashGap = gap
        return this
    }

    override fun getDashGap(): Int {
        return mDashGap
    }

    override fun into() {
        background = build()
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeLinearLayout)
        mShape =
            typedArray.getInt(R.styleable.ShapeLinearLayout_shape, IShapeDrawable.DEFAULT_SHAPE)
        mShapeWidth = typedArray.getDimensionPixelSize(
            R.styleable.ShapeLinearLayout_shape_width,
            IShapeDrawable.DEFAULT_SHAPE_WIDTH
        )
        mShapeHeight = typedArray.getDimensionPixelSize(
            R.styleable.ShapeLinearLayout_shape_height,
            IShapeDrawable.DEFAULT_SHAPE_HEIGHT
        )
        mSolidColor = typedArray.getColor(
            R.styleable.ShapeLinearLayout_shape_solidColor,
            IShapeDrawable.DEFAULT_SHAPE_SOLID_COLOR
        )
        val radius = typedArray.getDimensionPixelSize(
            R.styleable.ShapeLinearLayout_shape_radius,
            IShapeDrawable.DEFAULT_SHAPE_RADIUS
        )
        mTopLeftRadius = typedArray.getDimensionPixelSize(
            R.styleable.ShapeLinearLayout_shape_topLeftRadius,
            radius
        )
        mTopRightRadius = typedArray.getDimensionPixelSize(
            R.styleable.ShapeLinearLayout_shape_topRightRadius,
            radius
        )
        mBottomLeftRadius = typedArray.getDimensionPixelSize(
            R.styleable.ShapeLinearLayout_shape_bottomLeftRadius,
            radius
        )
        mBottomRightRadius = typedArray.getDimensionPixelSize(
            R.styleable.ShapeLinearLayout_shape_bottomRightRadius,
            radius
        )
        mStartColor = typedArray.getColor(
            R.styleable.ShapeLinearLayout_shape_startColor,
            IShapeDrawable.DEFAULT_SHAPE_START_COLOR
        )
        mCenterColor = typedArray.getColor(
            R.styleable.ShapeLinearLayout_shape_centerColor,
            IShapeDrawable.DEFAULT_SHAPE_CENTER_COLOR
        )
        mEndColor = typedArray.getColor(
            R.styleable.ShapeLinearLayout_shape_endColor,
            IShapeDrawable.DEFAULT_SHAPE_END_COLOR
        )
        mUseLevel = typedArray.getBoolean(
            R.styleable.ShapeLinearLayout_shape_useLevel,
            IShapeDrawable.DEFAULT_SHAPE_USE_LEVEL
        )
        mAngle = typedArray.getFloat(
            R.styleable.ShapeLinearLayout_shape_angle,
            IShapeDrawable.DEFAULT_SHAPE_ANGLE.toFloat()
        ).toInt()
        mGradientType = typedArray.getInt(
            R.styleable.ShapeLinearLayout_shape_gradientType,
            IShapeDrawable.DEFAULT_SHAPE_GRADIENT_TYPE
        )
        mCenterX = typedArray.getFloat(
            R.styleable.ShapeLinearLayout_shape_centerX,
            IShapeDrawable.DEFAULT_SHAPE_CENTER_X
        )
        mCenterY = typedArray.getFloat(
            R.styleable.ShapeLinearLayout_shape_centerX,
            IShapeDrawable.DEFAULT_SHAPE_CENTER_Y
        )
        mGradientRadius = typedArray.getDimensionPixelSize(
            R.styleable.ShapeLinearLayout_shape_gradientRadius,
            radius
        )
        mStrokeColor = typedArray.getColor(
            R.styleable.ShapeLinearLayout_shape_strokeColor,
            IShapeDrawable.DEFAULT_SHAPE_STROKE_COLOR
        )
        mStrokeWidth = typedArray.getDimensionPixelSize(
            R.styleable.ShapeLinearLayout_shape_strokeWidth,
            IShapeDrawable.DEFAULT_SHAPE_STROKE_WIDTH
        )
        mDashWidth = typedArray.getDimension(
            R.styleable.ShapeLinearLayout_shape_dashWidth,
            IShapeDrawable.DEFAULT_SHAPE_DASH_WIDTH.toFloat()
        ).toInt()
        mDashGap = typedArray.getDimension(
            R.styleable.ShapeLinearLayout_shape_dashGap,
            IShapeDrawable.DEFAULT_SHAPE_DASH_GAP.toFloat()
        ).toInt()
        typedArray.recycle()
        if (background == null) {
            into()
        }
    }
}