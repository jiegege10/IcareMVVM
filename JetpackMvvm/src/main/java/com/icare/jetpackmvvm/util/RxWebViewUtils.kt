package com.icare.jetpackmvvm.util
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout

object RxWebViewUtils {
    fun initWebView(webView: WebView) {
        with(webView) {
            settings.javaScriptEnabled = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.setSupportZoom(false)
            settings.minimumFontSize = CommonUtil.dp2px(context, 14f)
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    fun loadFullScreenHtml(webView: WebView, content: String) {
        val realContent = buildString {
            append("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><style>img,#content{width:100%} img{display: block;}*{margin:0;padding: 0}</style></head><body>")
            append(content).append("<p><br/></p>")
            append("</body></html>")
        }
        webView.loadDataWithBaseURL("", realContent, "text/html", "utf-8", null)
    }

    fun addWebView2Container(webView: WebView, container: FrameLayout) {
        webView.overScrollMode = View.OVER_SCROLL_NEVER
        container.addView(
            webView,
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        )
    }

    fun removeWebView(webView: WebView, container: FrameLayout) {
        container.removeAllViews()
        webView.stopLoading()
        webView.clearHistory()
        webView.destroy()
    }

    fun WebView.init() {
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.setSupportZoom(false)
        settings.minimumFontSize =CommonUtil.dp2px(context, 28f)
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }


}
