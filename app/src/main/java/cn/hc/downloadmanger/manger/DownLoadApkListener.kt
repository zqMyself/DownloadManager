package cn.hc.downloadmanger.manger

/**
 * @ClassName: DownLoadApkListener
 * @Description: java类作用描述
 * @Author: zengqiang
 * @github: https://github.com/zqMyself
 * @Date: 2021-07-14 11:10
 */
interface DownLoadApkListener {
    fun call(url: String,state:Int,progress:Int)
}