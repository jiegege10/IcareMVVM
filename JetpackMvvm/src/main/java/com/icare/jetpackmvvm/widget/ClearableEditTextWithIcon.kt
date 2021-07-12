package com.icare.jetpackmvvm.widget

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import com.icare.jetpackmvvm.R
import com.icare.jetpackmvvm.util.CommonUtil

/**
 * @date: 2021/7/8 2:14 下午
 * @author: Mr.He
 * @param
 * @return
 */
class ClearableEditTextWithIcon : AppCompatEditText, OnTouchListener, TextWatcher {
    // 删除符号
    var deleteImage =  CommonUtil.getDrawable(context,R.mipmap.icon_delete_gray)
    var icon: Drawable? = null

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) { init() }


    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) { init() }

    constructor(context: Context?) : super(context!!) { init() }

    private fun init() {
        setOnTouchListener(this)
        addTextChangedListener(this)
        deleteImage.setBounds(0, 0, deleteImage.intrinsicWidth, deleteImage.intrinsicHeight)
        setPadding(0, 0, 0, 0)
        manageClearButton()
    }

    /**
     * 传入显示的图标资源id
     *
     * @param id
     */
    fun setIconResource(@DrawableRes id: Int) {
        icon = CommonUtil.getDrawable(context, id)
        icon?.setBounds(0, 0, icon!!.intrinsicWidth, icon!!.intrinsicHeight)
        manageClearButton()
    }
    /**
     * 传入删除图标资源id
     *
     * @param id
     */
    fun setDeleteImage(@DrawableRes id: Int) {
        deleteImage = CommonUtil.getDrawable(context, id)
        deleteImage.setBounds(0, 0, deleteImage.intrinsicWidth, deleteImage.intrinsicHeight)
        manageClearButton()
    }

    private fun manageClearButton() {
        if (this.text.toString() .isEmpty()) {
            removeClearButton()
        } else {
            addClearButton()
        }
    }

    private fun removeClearButton() {
        setCompoundDrawables(icon, this.compoundDrawables[1], null, this.compoundDrawables[3])
    }

    private fun addClearButton() {
        setCompoundDrawables(
            icon, this.compoundDrawables[1], deleteImage,
            this.compoundDrawables[3]
        )
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val et = this@ClearableEditTextWithIcon
        if (et.compoundDrawables[2] == null) {
            return false
        }
        if (event.action != MotionEvent.ACTION_UP) {
            return false
        }
        if (event.x > et.width - et.paddingRight - deleteImage.intrinsicWidth) {
            et.setText("")
            removeClearButton()
        }
        return false
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) {
            removeClearButton()
        } else {
            if (this.text.toString().isNotEmpty()) {
                addClearButton()
            }
        }
    }

    private fun setDrawable() {
        if (length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, deleteImage, null)
        }
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        manageClearButton()
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {
        setDrawable()
    }
}