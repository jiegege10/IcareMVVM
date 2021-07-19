package com.icare.jetpackmvvm.base.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.icare.jetpackmvvm.R
import com.icare.jetpackmvvm.base.viewmodel.BaseViewModel
import com.icare.jetpackmvvm.ext.getVmClazz
import com.icare.jetpackmvvm.network.manager.NetState
import com.icare.jetpackmvvm.network.manager.NetworkStateManager
import com.kaopiz.kprogresshud.KProgressHUD
import me.yokeyword.fragmentation.SupportActivity

/**
 *
 * @description:     ViewModelActivity基类，把ViewModel注入进来了
 * @author:         Mr.He
 * @createDate:     6/17/21 11:09 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:09 AM
 */
abstract class BaseVmActivity<VM : BaseViewModel> : SupportActivity() {
    val mWaitPorgressDialog by lazy { KProgressHUD.create(this) }
    private val TAG: String = this.javaClass.simpleName

    /**
     * 是否需要使用DataBinding 供子类BaseVmDbActivity修改，用户请慎动
     */
    private var isUserDb = false

    lateinit var mViewModel: VM

    abstract fun layoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun showLoading(message: String = "请求中...")

    abstract fun dismissLoading()
    var mImmersionBar: ImmersionBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isUserDb) {
            setContentView(layoutId())
        } else {
            initDataBind()
        }
        mImmersionBar =
            ImmersionBar.with(this).statusBarDarkFont(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.white)
                .navigationBarColor(R.color.white)
                .fitsSystemWindows(true)
        mImmersionBar!!.init()
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
        NetworkStateManager.instance.mNetworkStateCallback.observeInActivity(this, Observer {
            onNetworkStateChanged(it)
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
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    abstract fun tokenExpiredObserver(message: String)

    /**
     * 注册UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observeInActivity(this, Observer {
            showLoading(it)
        })
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observeInActivity(this, Observer {
            dismissLoading()
        })
        mViewModel.tokenExpiredChange.observeInActivity(this){
            tokenExpiredObserver(it)
        }
    }

    /**
     * 将非该Activity绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            //显示弹窗
            viewModel.loadingChange.showDialog.observeInActivity(this, Observer {
                showLoading(it)
            })
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observeInActivity(this, Observer {
                dismissLoading()
            })
            viewModel.loadingChange.toast.observeInActivity(this, Observer {
                showToast(it)
            })
        }
    }

    fun userDataBinding(isUserDb: Boolean) {
        this.isUserDb = isUserDb
    }


    /**
     * 显示提示框
     *
     * @param msg 提示框内容字符串
     */
    open fun showProgressDialog(msg: String ="请稍后...") {
        if (!isFinishing) {
            mWaitPorgressDialog!!
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(msg)
                .setCancellable(true)
            mWaitPorgressDialog!!.show()
        }
    }

    /**
     * 隐藏提示框
     */
    open fun hideProgressDialog() {
        mWaitPorgressDialog?.dismiss()
    }

    open fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    /**
     * 供子类BaseVmDbActivity 初始化Databinding操作
     */
    open fun initDataBind() {}
}