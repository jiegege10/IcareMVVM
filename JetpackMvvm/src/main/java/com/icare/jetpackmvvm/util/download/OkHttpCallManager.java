package com.icare.jetpackmvvm.util.download;

import android.text.TextUtils;

import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
/**
* @date: 2021/7/26 4:37 下午
* @author: Mr.He
* @param 
* @return 
*/
class OkHttpCallManager {

    private ConcurrentHashMap<String, Call> callMap;
    private static OkHttpCallManager manager;

    private OkHttpCallManager() {
        callMap = new ConcurrentHashMap<>();
    }

    public static OkHttpCallManager getInstance() {
        if (manager == null) {
            manager = new OkHttpCallManager();
        }
        return manager;
    }

    public void addCall(String url, Call call) {
        if (call != null && !TextUtils.isEmpty(url)) {
            callMap.put(url, call);
        }
    }

    public Call getCall(String url) {
        if ( !TextUtils.isEmpty(url) ) {
            return callMap.get(url);
        }

        return null;
    }

    public void removeCall(String url) {
        if ( !TextUtils.isEmpty(url) ) {
            callMap.remove(url);
        }
    }

}
