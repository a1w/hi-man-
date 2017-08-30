package com.cstore.zhiyazhang.cstoremanagement.utils

import com.cstore.zhiyazhang.cstoremanagement.R
import com.cstore.zhiyazhang.cstoremanagement.view.interfaceview.GenericView
import com.zhiyazhang.mykotlinapplication.utils.MyApplication

/**
 * Created by zhiya.zhang
 * on 2017/8/30 11:43.
 */
object PresenterUtil{
        fun judgmentInternet(gView:GenericView):Boolean{
            gView.showLoading()
            if (!ConnectionDetector.getConnectionDetector().isOnline) {
                gView.showPrompt(MyApplication.instance().applicationContext.getString(R.string.noInternet))
                gView.hideLoading()
                return false
            }else return true
        }
}