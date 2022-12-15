package com.icare.mvvm.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.CountDownTimer
import android.os.Environment
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.icare.mvvm.R
import com.icare.mvvm.base.BaseApp
import com.icare.mvvm.listener.OnPictureListener
import com.icare.mvvm.widget.GlideEngine
import com.icare.mvvm.widget.ImageFileCompressEngine
import com.icare.mvvm.widget.ImageFileCropEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectMimeType.ofImage
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.config.SelectModeConfig.SINGLE
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import java.io.File
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


    /**
     * @date: 2021/8/16 4:46 下午
     * @author: Mr.He
     * @param 打电话
     * @return
     */

    fun callPhone(content: Context, phoneNum: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data: Uri = Uri.parse("tel:$phoneNum")
        intent.data = data
        content.startActivity(intent)
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

    @SuppressLint("UseCompatLoadingForDrawables")
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
    fun countDown(
        textView: TextView,
        waitTime: Long = 60000,
        interval: Long = 1000,
        hint: String = "重新发送"
    ) {
        textView.isEnabled = false
        val timer: CountDownTimer = object : CountDownTimer(waitTime, interval) {
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

    //lifecycleScope
    @JvmStatic
    fun countDownCoroutines(
        textView: TextView,
        scope: CoroutineScope,
        waitTime: Long = 60000,
        hint: String = "重新发送"
    ): Job {
        return flow {
            for (i in waitTime downTo 0) {
                emit(i)
                delay(1000)
            }
        }.flowOn(Dispatchers.Main)
            .onStart {
                textView.isEnabled = false
            }
            .onCompletion {
                textView.isEnabled = true
                textView.text = hint
            }
            .onEach { textView.text = "${it}s后重发" }
            .launchIn(scope)
    }


    /**
     * @date: 2021/8/10 10:10 上午
     * @author: 打开相册
     * @param
     * @return
     */
    fun openAlbum(
        type: Int,
        activity: Activity,
        max: Int = 1,
        min: Int = 1,
        mode: Int = SelectModeConfig.SINGLE,
    ) {
        PictureSelector.create(activity)
            .openGallery(SelectMimeType.ofImage())// 全部.SelectMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(max)// 最大图片选择数量
            .setMinSelectNum(min)// 最小选择数量
            .setImageSpanCount(4)// 每行显示个数
            .setSelectionMode(mode)// 多选 or 单选
            .isSelectZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .setCropEngine(ImageFileCropEngine())// 是否裁剪
            .setCompressEngine(ImageFileCompressEngine())
            .forResult(type)

    }

    /**
     * @date: 2021/8/10 10:10 上午
     * @author: 打开相册
     * @param
     * @return
     */
    fun openCamera(
        type: Int,
        activity: Activity,
        ll: OnPictureListener
    ) {
        PictureSelector.create(activity)
            .openCamera(SelectMimeType.ofImage())
            .setCompressEngine(ImageFileCompressEngine())
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>) {
                    ll.onResult(result, type)

                }

                override fun onCancel() {}
            })

    }

    fun getPath(): String {
        val path = Environment.getExternalStorageDirectory().toString() + "/image/image/"
        val file = File(path)
        return if (file.mkdirs()) path else path
    }


    fun startSpinAnimation(
        it: View,
        from: Float = 0f,
        toDegrees: Float = -180f,
        duration: Long = 200
    ) {
        val animation: Animation = RotateAnimation(
            from,
            toDegrees,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = duration
        animation.repeatCount = 0 //动画的反复次数
//        animation.interpolator = AccelerateInterpolator()
        animation.fillAfter = true //设置为true，动画转化结束后被应用
        it.startAnimation(animation) //開始动画

    }

    fun stopSpinAnimation(
        it: View,
        from: Float = -180f,
        toDegrees: Float = 0f,
        duration: Long = 200
    ) {
        val animation: Animation = RotateAnimation(
            from,
            toDegrees,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = duration
        animation.repeatCount = 0 //动画的反复次数
        animation.fillAfter = true //设置为true，动画转化结束后被应用
        it.startAnimation(animation) //開始动画

    }

    fun bindViewPager2(magicIndicator: MagicIndicator, viewPager: ViewPager2) {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                magicIndicator.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                magicIndicator.onPageScrollStateChanged(state)
            }
        })
    }


}