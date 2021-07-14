package cn.hc.downloadmanger

import android.app.Application
import android.content.Context

/**
 * @ClassName: MyApplication
 * @Description: java类作用描述
 * @Author: zengqiang
 * @github: https://github.com/zqMyself
 * @Date: 2021-07-14 10:41
 */
class MyApplication : Application() {

    companion object{
        var context :Context?=null
    }
    override fun onCreate() {
        super.onCreate()
        context =this
    }
}