/*
 * Tencent is pleased to support the open source community by making VasSonic available.
 *
 * Copyright (C) 2017 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *
 */
package com.icare.mvvm.widget.tencent

import com.tencent.sonic.sdk.SonicSessionClient
import android.webkit.WebView
import android.os.Bundle
import java.util.HashMap

/**
 * a implement of SonicSessionClient which need to connect webview and content data.
 */
class SonicSessionClientImpl : SonicSessionClient() {
    var webView: WebView? = null
        private set

    fun bindWebView(webView: WebView?) {
        this.webView = webView
    }

    override fun loadUrl(url: String, extraData: Bundle) {
        webView!!.loadUrl(url)
    }

    override fun loadDataWithBaseUrl(
        baseUrl: String,
        data: String,
        mimeType: String,
        encoding: String,
        historyUrl: String
    ) {
        webView!!.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl)
    }

    override fun loadDataWithBaseUrlAndHeader(
        baseUrl: String,
        data: String,
        mimeType: String,
        encoding: String,
        historyUrl: String,
        headers: HashMap<String, String>
    ) {
        loadDataWithBaseUrl(baseUrl, data, mimeType, encoding, historyUrl)
    }

    fun destroy() {
        if (null != webView) {
            webView!!.destroy()
            webView = null
        }
    }
}