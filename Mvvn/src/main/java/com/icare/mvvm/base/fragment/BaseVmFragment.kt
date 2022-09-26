package com.icare.mvvm.base.fragment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.icare.mvvm.base.viewmodel.BaseViewModel
import com.icare.mvvm.ext.getVmClazz
import com.icare.mvvm.ext.singleTop
import com.icare.mvvm.network.manager.NetState
import com.icare.mvvm.network.manager.NetworkStateManager
import com.icare.mvvm.util.StyleableToast
import com.kaopiz.kprogresshud.KProgressHUD

import me.yokeyword.fragmentation.SupportFragment


/**
 *
 * @description:     ViewModelFragment基类，自动把ViewModel注入Fragment
 * @author:         Mr.He
 * @createDate:     6/17/21 11:10 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:10 AM
 */
abstract class BaseVmFragment<VM : BaseViewModel> : SupportFragment() {
    val mWaitPorgressDialog by lazy { KProgressHUD.create(requireContext()) }
    private val handler = Handler()

    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mViewModel: VM

    lateinit var mActivity: AppCompatActivity

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract fun layoutId(): Int


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        registorDefUIChange()
        initData()
    }

    /**
     * @date: 2021/7/20 2:38 下午
     * @author: Mr.He
     * @param 多权限声明
     * @return
     */
    open fun setPermissions(STORAGE: Array<String>, callback: OnPermissionCallback) {
        XXPermissions.with(this).permission(STORAGE).request(object : OnPermissionCallback {
            override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                callback.onGranted(permissions, all)
            }

            override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                callback.onDenied(permissions, never)

            }

        })
    }

    /**
     * @date: 2021/7/20 2:38 下午
     * @author: Mr.He
     * @param 单权限声明
     * @return
     */
    open fun setPermission(permission: String, callback: OnPermissionCallback) {
        XXPermissions.with(this).permission(permission).request(object : OnPermissionCallback {
            override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                callback.onGranted(permissions, all)
            }

            override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                callback.onDenied(permissions, never)

            }

        })
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    open fun showToast(msg: String) {
        StyleableToast.Builder(requireContext())
            .text(msg)
            .cornerRadius(5)
            .show()
    }

    fun startActivity(clz: Class<*>) {
        startActivity(Intent(requireContext(), clz))

    }
    fun startSingleActivity(clz: Class<*>) {
        var intent = Intent(requireContext(), clz).singleTop()
        startActivity(intent)

    }
    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            handler.postDelayed({
                lazyLoadData()
                //在Fragment中，只有懒加载过了才能开启网络变化监听
                NetworkStateManager.instance.mNetworkStateCallback.observeInFragment(
                    this,
                    Observer {
                        //不是首次订阅时调用方法，防止数据第一次监听错误
                        if (!isFirst) {
                            onNetworkStateChanged(it)
                        }
                    })
                isFirst = false
            }, lazyLoadTime())
        }
    }

    /**
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()


    /**
     * 显示提示框
     *
     * @param msg 提示框内容字符串
     */
    open fun showProgressDialog(msg: String = "请稍后...") {
        mWaitPorgressDialog!!
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel(msg)
            .setCancellable(true)
        mWaitPorgressDialog!!.show()
    }

    open fun showProgressDialog(
        msg: String = "请稍后...",
        onCancelListener: DialogInterface.OnCancelListener? = null
    ) {
        mWaitPorgressDialog
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel(msg)
            .setCancellable(true)
        mWaitPorgressDialog.show()
        mWaitPorgressDialog.setCancellable {
            onCancelListener?.onCancel(it)
        }
    }

    /**
     * 隐藏提示框
     */
    open fun hideProgressDialog() {
        mWaitPorgressDialog?.dismiss()
    }

    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        mViewModel.loadingChange.showDialog.observeInFragment(this, Observer {
            showLoading(it)
        })
        mViewModel.loadingChange.dismissDialog.observeInFragment(this, Observer {
            dismissLoading()
        })
    }

    /**
     * 将非该Fragment绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            //显示弹窗
            viewModel.loadingChange.showDialog.observeInFragment(this, Observer {
                showLoading(it)
            })
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observeInFragment(this, Observer {
                dismissLoading()
            })
        }
    }

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 300
    }
}