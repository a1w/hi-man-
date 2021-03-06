package com.cstore.zhiyazhang.cstoremanagement.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.cstore.zhiyazhang.cstoremanagement.R

/**
 * Created by zhiya.zhang
 * on 2017/5/19 10:29.
 * 检测网络工具类
 */
class ConnectionDetector private constructor() {

    companion object {
        fun getConnectionDetector(): ConnectionDetector {
            return Inner.cd
        }
    }

    private object Inner {
        val cd = ConnectionDetector()
    }


    /**
     * 检测是否有网络
     * @return 是\否
     */
    val isOnline: Boolean
        get() {
            val connectivity = MyApplication.instance().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivity.activeNetworkInfo
            if (info!=null)if (info.isConnected) if (info.state == NetworkInfo.State.CONNECTED) return true
            MyToast.getShortToast(MyApplication.instance().applicationContext.getString(R.string.noInternet))
            return false
        }
}
