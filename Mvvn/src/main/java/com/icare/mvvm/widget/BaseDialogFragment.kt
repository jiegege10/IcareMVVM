package com.icare.mvvm.widget

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.icare.mvvm.R
import com.icare.mvvm.base.viewmodel.BaseViewModel
import com.icare.mvvm.ext.getVmClazz
import org.jetbrains.annotations.NotNull

abstract class BaseDialogFragment<VM : BaseViewModel, DB : ViewDataBinding>  : DialogFragment() {
    lateinit var mViewModel: VM
    //该类绑定的ViewDataBinding
    lateinit var mDatabind: DB

    private var mActivity: Activity? = null
    var mWindow: Window? = null
    var mWidthAndHeight: Array<Int?>? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = createViewModel()
        //全屏
        setStyle(STYLE_NORMAL, R.style.MyDialog)

    }
    /**
     * 创建观察者
     */
    abstract fun createObserver()
    abstract fun initView(savedInstanceState: Bundle?)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(savedInstanceState)
        createObserver()

    }
    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.setCanceledOnTouchOutside(true)
        mWindow = dialog!!.window
        mWidthAndHeight = getWidthAndHeight(mWindow)
        mDatabind = DataBindingUtil.inflate(inflater, setLayoutId(), container, false)
        mDatabind.lifecycleOwner = this
        return mDatabind.root
    }
    override fun onViewCreated(@NotNull view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .statusBarColor(R.color.app_dividing)
            .init()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mWidthAndHeight = getWidthAndHeight(mWindow)
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract fun setLayoutId(): Int


    companion object {
        fun getWidthAndHeight(window: Window?): Array<Int?>? {
            if (window == null) {
                return null
            }
            val integer = arrayOfNulls<Int>(2)
            val dm = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.windowManager.defaultDisplay.getRealMetrics(dm)
            } else {
                window.windowManager.defaultDisplay.getMetrics(dm)
            }
            integer[0] = dm.widthPixels
            integer[1] = dm.heightPixels
            return integer
        }
    }
}