package com.icare.mvvm.base.activity

import android.annotation.TargetApi
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.webkit.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.icare.mvvm.R
import com.icare.mvvm.base.AccountExceptionEntity
import com.icare.mvvm.base.viewmodel.BaseViewModel
import com.icare.mvvm.ext.getVmClazz
import com.icare.mvvm.ext.singleTop
import com.icare.mvvm.network.manager.NetState
import com.icare.mvvm.network.manager.NetworkStateManager
import com.icare.mvvm.util.StyleableToast
import com.icare.mvvm.widget.tencent.SonicRuntimeImpl
import com.icare.mvvm.widget.tencent.SonicSessionClientImpl
import com.kaopiz.kprogresshud.KProgressHUD
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.tencent.sonic.sdk.*
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.BufferedInputStream
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.HashMap

/**
 *
 * @description:     ViewModelActivity基类，把ViewModel注入进来了
 * @author:         Mr.He
 * @createDate:     6/17/21 11:09 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:09 AM
 */
abstract class BaseVmActivity<VM : BaseViewModel> : SupportActivity() {
    //    open val mWaitPorgressDialog by lazy { KProgressHUD.create(this) }
    open var mWaitPorgressDialog: QMUITipDialog? = null

    private var sonicSession: SonicSession? = null

    private var mWebView: WebView? = null

    val MODE_DEFAULT = 0
    val MODE_SONIC_WITH_OFFLINE_CACHE = 2

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
    open val mImmersionBar: ImmersionBar by lazy {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true)
            .keyboardEnable(true)
            .statusBarColor(R.color.white)
            .navigationBarColor(R.color.white)

    }

    open fun initWebView(webView: WebView, url: String) {
        mWebView = webView
        window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(SonicRuntimeImpl(application), SonicConfig.Builder().build())
        }
        var sonicSessionClient: SonicSessionClientImpl? = null
        // if it's sonic mode , startup sonic session at first time
        if (MODE_DEFAULT != 0) { // sonic mode
            val sessionConfigBuilder = SonicSessionConfig.Builder()
            sessionConfigBuilder.setSupportLocalServer(true)

            // if it's offline pkg mode, we need to intercept the session connection
            if (MODE_SONIC_WITH_OFFLINE_CACHE == 0) {
                sessionConfigBuilder.setCacheInterceptor(object : SonicCacheInterceptor(null) {
                    override fun getCacheData(session: SonicSession): String? {
                        return null // offline pkg does not need cache
                    }
                })
                sessionConfigBuilder.setConnectionInterceptor(object :
                    SonicSessionConnectionInterceptor() {
                    override fun getConnection(
                        session: SonicSession,
                        intent: Intent
                    ): SonicSessionConnection {
                        return OfflinePkgSessionConnection(
                            this@BaseVmActivity,
                            session,
                            intent
                        )
                    }
                })
            }

            // create sonic session and run sonic flow
            sonicSession =
                SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build())
            if (null != sonicSession) {
                sonicSession!!.bindClient(SonicSessionClientImpl().also { sonicSessionClient = it })
            } else {
                // this only happen when a same sonic session is already running,
                // u can comment following codes to feedback as a default mode.
                // throw new UnknownError("create session fail!");
                Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show()
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if (sonicSession != null) {
                    sonicSession!!.sessionClient.pageFinish(url)
                }
            }


            @TargetApi(21)
            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                return shouldInterceptRequest(view, request.url.toString())
            }

            override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
                return if (sonicSession != null) {
                    sonicSession!!.sessionClient.requestResource(url) as WebResourceResponse
                } else null
            }
        }
        val webSettings = webView.settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webSettings.safeBrowsingEnabled = false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }
        // add java script interface
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.javaScriptEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        // init webview settings
        webSettings.allowContentAccess = true
        webSettings.databaseEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.setAppCacheEnabled(true)
        webSettings.savePassword = false
        webSettings.allowFileAccess = true
        webSettings.saveFormData = false
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        WebView.setWebContentsDebuggingEnabled(true)

        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient!!.bindWebView(webView)
            sonicSessionClient!!.clientReady()
        } else { // default mode
            webView.loadUrl(url)
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun accountException(bean: AccountExceptionEntity) {
        if (bean.reRegister) {
            EventBus.getDefault().unregister(this)
            tokenExpiredObserver(bean.mag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mWebView != null) {
            SonicEngine.getInstance().cleanCache()
            mWebView!!.clearCache(true)
            mWebView!!.destroy()
        }
        if (null != sonicSession) {
            sonicSession!!.destroy()
            sonicSession = null
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        if (!isUserDb) {
            setContentView(layoutId())
        } else {
            initDataBind()
        }
        mImmersionBar.init()
        init(savedInstanceState)
    }


    open fun setNoStatusBar() {
        mImmersionBar.transparentStatusBar().fitsSystemWindows(false)?.init()
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
    open fun showProgressDialog(msg: String = "请稍后...") {
        mWaitPorgressDialog =
            QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create()
        mWaitPorgressDialog?.show()
    }

    open fun showProgressDialog(
        msg: String = "请稍后...",
        onCancelListener: DialogInterface.OnCancelListener? = null
    ) {
        if (!isFinishing) {
            mWaitPorgressDialog =
                QMUITipDialog.Builder(this).setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(msg)
                    .create()
            mWaitPorgressDialog?.show()
            mWaitPorgressDialog?.setOnCancelListener {
                onCancelListener?.onCancel(it)
            }

        }
    }

    /**
     * 隐藏提示框
     */
    open fun hideProgressDialog() {
        mWaitPorgressDialog?.dismiss()
    }

    open fun showToast(msg: String) {
        StyleableToast.Builder(this)
            .text(msg)
            .cornerRadius(5)
            .show()
    }

    fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))

    }
    fun startHome(clz: Class<*>){

    }
    fun startSingleActivity(clz: Class<*>) {
        var intent = Intent(this, clz).singleTop()
        startActivity(intent)

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    /**
     * 供子类BaseVmDbActivity 初始化Databinding操作
     */
    open fun initDataBind() {}


    private class OfflinePkgSessionConnection(
        context: Context,
        session: SonicSession?,
        intent: Intent?
    ) :
        SonicSessionConnection(session, intent) {
        private val context: WeakReference<Context> = WeakReference(context)
        override fun internalConnect(): Int {
            val ctx = context.get()
            if (null != ctx) {
                try {
                    val offlineHtmlInputStream = ctx.assets.open("sonic-demo-index.html")
                    responseStream = BufferedInputStream(offlineHtmlInputStream)
                    return SonicConstants.ERROR_CODE_SUCCESS
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN
        }

        override fun internalGetResponseStream(): BufferedInputStream {
            return responseStream
        }

        override fun internalGetCustomHeadFieldEtag(): String {
            return CUSTOM_HEAD_FILED_ETAG
        }

        override fun disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        override fun getResponseCode(): Int {
            return 200
        }

        override fun getResponseHeaderFields(): Map<String, List<String>> {
            return HashMap(0)
        }

        override fun getResponseHeaderField(key: String): String {
            return ""
        }

    }


}