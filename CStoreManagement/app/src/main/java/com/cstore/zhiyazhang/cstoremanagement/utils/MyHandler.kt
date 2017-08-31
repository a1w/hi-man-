package com.cstore.zhiyazhang.cstoremanagement.utils

import android.os.Handler
import android.os.Message
import com.cstore.zhiyazhang.cstoremanagement.R
import com.cstore.zhiyazhang.cstoremanagement.model.MyListener
import com.zhiyazhang.mykotlinapplication.utils.MyApplication
import java.lang.ref.WeakReference

/**
 * Created by zhiya.zhang
 * on 2017/8/28 17:04.
 */
class MyHandler {
    companion object MyHandler : Handler() {
        val SUCCESS = 0
        val ERROR1 = 1
        val ERROR2 = 2
        private var isRun:Boolean=false

        private var mActivity: WeakReference<MyActivity>? = null
        private var mListener: MyListener? = null

        fun writeActivity(activity: MyActivity): MyHandler {
            mActivity = WeakReference<MyActivity>(activity)
            isRun=true
            return this
        }

        fun writeListener(myListener: MyListener): MyHandler {
            mListener = myListener
            isRun=true
            return this
        }

        fun cleanAL() {
            if (!isRun){//还在运行就不允许清空
                mActivity = null
                mListener = null
            }
        }

        override fun handleMessage(msg: Message) {
            try {
                if (mActivity != null && mListener != null) {
                    if (mActivity!!.get() == null) {
                        mListener!!.listenerFailed(MyApplication.instance().applicationContext.getString(R.string.system_error))
                        isRun=false
                        return
                    }
                    when (msg.what) {
                        SUCCESS -> {
                            mListener!!.listenerSuccess(msg.obj)
                            isRun=false
                        }
                        else -> {
                            mListener!!.listenerFailed(msg.obj as String)
                            isRun=false
                        }
                    }
                }
            } catch (e: Exception) {
                mListener!!.listenerFailed(e.message!!)
                isRun=false
                return
            }
        }
    }
}