package cn.hc.downloadmanger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list= ArrayList<ApkBean>()
        var apkBean = ApkBean()
        apkBean.name = "微信"
        apkBean.url = "https://dldir1.qq.com/weixin/android/weixin807android1920_arm64.apk"
        list.add(apkBean)
        var apkBean2 = ApkBean()
        apkBean2.name = "百度地图"
        apkBean2.url = "https://downpack.baidu.com/baidumap_AndroidPhone_1012337a.apk"
        list.add(apkBean2)
        var adapter = ApplicationAdapter(this,list)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter  =adapter
        recycler_view.itemAnimator!!.changeDuration = 0
    }
}