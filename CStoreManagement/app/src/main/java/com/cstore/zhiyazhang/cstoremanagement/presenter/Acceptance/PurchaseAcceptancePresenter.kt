package com.cstore.zhiyazhang.cstoremanagement.presenter.acceptance

import android.content.Context
import com.cstore.zhiyazhang.cstoremanagement.R
import com.cstore.zhiyazhang.cstoremanagement.bean.AcceptanceBean
import com.cstore.zhiyazhang.cstoremanagement.model.MyListener
import com.cstore.zhiyazhang.cstoremanagement.model.acceptance.AcceptanceModel
import com.cstore.zhiyazhang.cstoremanagement.utils.MyActivity
import com.cstore.zhiyazhang.cstoremanagement.utils.MyApplication
import com.cstore.zhiyazhang.cstoremanagement.utils.MyHandler
import com.cstore.zhiyazhang.cstoremanagement.utils.PresenterUtil
import com.cstore.zhiyazhang.cstoremanagement.view.interfaceview.GenericView

/**
 * Created by zhiya.zhang
 * on 2017/9/11 16:51.
 */
class PurchaseAcceptancePresenter(private val gView: GenericView, private val context: Context, private val activity: MyActivity) {
    private val model = AcceptanceModel()

    fun getAcceptanceList(date: String) {
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.getAcceptanceList(date, MyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.requestSuccess(data)
                gView.hideLoading()
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(errorMessage)
                gView.hideLoading()
            }
        }))
    }

    fun updateAcceptance(date:String, ab:AcceptanceBean){
        if (!PresenterUtil.judgmentInternet(gView)) return
        model.updateAcceptance(date, ab, MyHandler.writeActivity(activity).writeListener(object : MyListener {
            override fun listenerSuccess(data: Any) {
                gView.showPrompt(MyApplication.instance().getString(R.string.saveDone))
                gView.updateDone(data)
            }

            override fun listenerFailed(errorMessage: String) {
                gView.showPrompt(errorMessage)
                gView.errorDealWith()
            }
        }))
    }
}