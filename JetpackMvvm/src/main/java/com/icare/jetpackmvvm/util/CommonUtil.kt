package com.icare.jetpackmvvm.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.icare.jetpackmvvm.base.BaseApp
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.lang.reflect.Field
import java.util.*


/**
  *
  * @description:     工具类
  * @author:         Mr.He
  * @createDate:     6/11/21 11:25 AM
  * @updateUser:     更新者：Mr.He
  * @updateDate:     6/11/21 11:25 AM
 */
object CommonUtil {

    init {
    }

    fun dp2px(context: Context, dpValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            context.resources.displayMetrics
        ).toInt()
    }
    /**
    * @Date: 6/11/21 10:21 AM
    * @Author: Mr.He
    * @Param
    * @Return 
    */
    fun sp2px(context: Context, spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spValue,
            context.resources.displayMetrics
        ).toInt()
    }
    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    fun initData(): List<String> {
        val approveList: MutableList<String> = ArrayList()
        approveList.add("http://img2.imgtn.bdimg.com/it/u=1939271907,257307689&fm=21&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        approveList.add("http://img0.imgtn.bdimg.com/it/u=2263418180,3668836868&fm=206&gp=0.jpg")
        return approveList
    }


    /**
     * 解决InputMethodManager引起的内存泄漏
     * 在Activity的onDestroy方法里调用
     */
    fun fixInputMethodManagerLeak(context: Context) {

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val arr = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        var field: Field? = null
        var objGet: Any? = null
        for (i in arr.indices) {
            val param = arr[i]
            try {
                field = imm.javaClass.getDeclaredField(param)
                if (field.isAccessible === false) {
                    field.isAccessible = true
                }
                objGet = field.get(imm)
                if (objGet != null && objGet is View) {
                    val view = objGet
                    if (view.context === context) {
                        // 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(imm, null) // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }

        }

    }


    fun getColor(context: Context, @ColorRes id: Int): Int {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getColor(context, id)
        } else {
            context.resources.getColor(id)
        }
    }
    fun getDrawable(context: Context, @DrawableRes id: Int): Drawable {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getDrawable(context, id)!!
        } else {
            context.resources.getDrawable(id)
        }
    }
    @JvmStatic
    fun getContext(): Context {
        var context = BaseApp.content
        if (context != null) {
            return context
        }
        throw NullPointerException("BaseApp.content 获取错误")
    }
    /**
     * 倒计时
     *
     * @param textView 控件
     * @param waitTime 倒计时总时长
     * @param interval 倒计时的间隔时间
     * @param hint     倒计时完毕时显示的文字
     */
    @JvmStatic
    fun countDown(textView: TextView, waitTime: Long, interval: Long, hint: String?) {
        textView.isEnabled = false
        val timer: CountDownTimer = object : CountDownTimer(waitTime, interval) {
            @SuppressLint("DefaultLocale")
            override fun onTick(millisUntilFinished: Long) {
                textView.text = String.format("剩下 %d S", millisUntilFinished / 1000)
            }

            override fun onFinish() {
                textView.isEnabled = true
                textView.text = hint
            }
        }
        timer.start()
    }
}