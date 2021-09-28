package com.icare.mvvm.ext

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Checkable
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.icare.mvvm.util.CommonUtil
import com.icare.mvvm.widget.RxTitle

/**
* @date: 2021/7/19 7:53 下午
* @author: Mr.He
* @param  快捷方法
* @return
*/


//绑定普通的Recyclerview
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

/**
 * 初始化普通的toolbar 只设置标题
 */
fun RxTitle.init(titleStr: String = "", context: Activity): RxTitle {
    setTitle(titleStr)
    setLeftFinish(context)
    return this
}
fun WebView.init() : WebView {
    settings.javaScriptEnabled = true
    settings.useWideViewPort = true
    settings.loadWithOverviewMode = true
    settings.setSupportZoom(true)
    settings.minimumFontSize = CommonUtil.dp2px(context, 28f)

    //设置默认编码
    settings.defaultTextEncodingName = "utf-8"
    ////设置自动加载图片
    settings.loadsImagesAutomatically = true
    settings.cacheMode = WebSettings.LOAD_NO_CACHE

    settings.javaScriptEnabled = true
    settings.displayZoomControls = false //隐藏原生的缩放控件
    settings.javaScriptCanOpenWindowsAutomatically = true
    settings.allowFileAccess = true

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }
    return this
}
//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int) {
    //等于0，关闭列表动画 否则开启
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}

// 扩展点击事件属性(重复点击时长)
var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

// 重复点击事件绑定
inline fun <T : View> T.setSingleClickListener(time: Long = 1000, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

/**
 * 设置TextView的上图片
 */
fun TextView.drawableTop(context:Context,@DrawableRes resId: Int) {
    val drawable = CommonUtil.getDrawable(context,resId).apply {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }
    this.setCompoundDrawables(
        compoundDrawables[0],
        drawable,
        compoundDrawables[2],
        compoundDrawables[3]
    )
}

/**
 * 设置TextView的上图片
 */
fun TextView.drawableEnd(context:Context,@DrawableRes resId: Int) {
    val drawable = CommonUtil.getDrawable(context,resId).apply {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }
    this.setCompoundDrawablesRelative(
        compoundDrawables[0],
        compoundDrawables[1],
        drawable,
        compoundDrawables[3]
    )
}

/**
 * 设置TextView的上图片
 */
fun TextView.drawableStart(context:Context,@DrawableRes resId: Int) {
    val drawable =  CommonUtil.getDrawable(context,resId).apply {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }
    this.setCompoundDrawablesRelative(
        drawable,
        compoundDrawables[1],
        compoundDrawables[2],
        compoundDrawables[3]
    )
}

