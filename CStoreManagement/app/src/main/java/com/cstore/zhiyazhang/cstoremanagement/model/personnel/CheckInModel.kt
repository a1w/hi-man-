package com.cstore.zhiyazhang.cstoremanagement.model.personnel

import android.graphics.Bitmap
import android.os.Message
import com.cstore.zhiyazhang.cstoremanagement.R
import com.cstore.zhiyazhang.cstoremanagement.bean.User
import com.cstore.zhiyazhang.cstoremanagement.sql.MySql
import com.cstore.zhiyazhang.cstoremanagement.utils.MyApplication
import com.cstore.zhiyazhang.cstoremanagement.utils.MyHandler
import com.cstore.zhiyazhang.cstoremanagement.utils.MyHandler.OnlyMyHandler.ERROR1
import com.cstore.zhiyazhang.cstoremanagement.utils.MyHandler.OnlyMyHandler.SUCCESS
import com.cstore.zhiyazhang.cstoremanagement.utils.MyTimeUtil
import com.cstore.zhiyazhang.cstoremanagement.utils.socket.SocketUtil

/**
 * Created by zhiya.zhang
 * on 2017/10/12 13:46.
 */
class CheckInModel:CheckInInterface{
    override fun checkInUser(uId: String, bmp: Bitmap, handler: MyHandler.OnlyMyHandler) {
        Thread(Runnable {
            val msg = Message()
            val ip = MyApplication.getIP()
            if (!SocketUtil.judgmentIP(ip, msg, handler)) return@Runnable
            if (!judgmentUser(ip, uId, msg, handler))return@Runnable
            val address="fileput C:\\rtcvs\\arr_photo\\${MyTimeUtil.tomorrowDate}\\${User.getUser().storeId+uId+MyTimeUtil.nowTimeString}.jpg\u0004"
            val data=SocketUtil.initSocket(ip,"").inquire(bmp,address)
            if (data=="0"){
                msg.what=SUCCESS
            }else{
                msg.what=ERROR1
            }
            msg.obj=data
            handler.sendMessage(msg)
        }).start()
    }

    /**
     * 检测输入的用户是否存在
     */
    private fun judgmentUser(ip:String, uId: String, msg: Message, handler: MyHandler.OnlyMyHandler): Boolean {
        val data=SocketUtil.initSocket(ip,MySql.signIn(uId)).inquire()
        return if (data==""||data=="[]"){
            msg.obj=MyApplication.instance().applicationContext.getString(R.string.idError)
            msg.what=MyHandler.ERROR1
            handler.sendMessage(msg)
            false
        }else if (data=="0"){
            msg.obj=MyApplication.instance().applicationContext.getString(R.string.socketError)
            msg.what=MyHandler.ERROR1
            handler.sendMessage(msg)
            false
        }else true
    }

}

interface CheckInInterface{
    /**
     * 签到
     */
    fun checkInUser(uId:String, bmp:Bitmap, handler:MyHandler.OnlyMyHandler)
}