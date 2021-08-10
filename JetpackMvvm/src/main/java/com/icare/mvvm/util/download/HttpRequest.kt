/*
 * Copyright (C) 2015 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.icare.mvvm.util.download

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import java.io.File

/**
* @date: 2021/7/26 4:37 下午
* @author: Mr.He
* @param
* @return
*/
object HttpRequest {
    /**
     * 取消请求
     * @param url
     */
    fun cancel(url: String?) {
        if (!TextUtils.isEmpty(url)) {
            val call = OkHttpCallManager.getInstance().getCall(url)
            call?.cancel()
            OkHttpCallManager.getInstance().removeCall(url)
        }
    }

    /**
     * 下载文件
     * @param url
     * @param target 保存的文件
     * @param callback
     */
    @JvmOverloads
    fun download(content:Context,url: String, target: File, callback: FileDownloadCallback? = null) {
        OkHttpFinal.getInstance().init(OkHttpFinalConfiguration.Builder().build())
        XXPermissions.with(content).permission(arrayOf(Permission.MANAGE_EXTERNAL_STORAGE)).request(object : OnPermissionCallback {
            override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                if (!TextUtils.isEmpty(url) && target != null) {
                    val task = FileDownloadTask(url, target, callback)
                    task.execute()
                }
            }

            override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                Toast.makeText(content,"权限不足",Toast.LENGTH_SHORT).show()
            }

        })

    }
}