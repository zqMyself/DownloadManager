package cn.hc.downloadmanger

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.hc.downloadmanger.manger.DownLoadApkListener
import cn.lanru.lrapplication.module.utils.manger.DownLoadMangerUtils

/**
 * @ClassName: ControlAdapter
 * @Description: java类作用描述
 * @Author: zengqiang
 * @github: https://github.com/zqMyself
 * @Date: 2021-06-08 11:28
 */
class ApplicationAdapter : RecyclerView.Adapter<ApplicationAdapter.MyHolder>, DownLoadApkListener {

    var post = -1
    var mContext :Context?=null
    var list :ArrayList<ApkBean>?=null
    inner  class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var mProgressBar: ProgressBar
        var item : View
        var tv_selected : TextView
        init {
            tvName = itemView.findViewById(R.id.tv_name)
            item = itemView.findViewById(R.id.item)
            mProgressBar = itemView.findViewById(R.id.ProgressBar)
            tv_selected = itemView.findViewById(R.id.tv_selected)
        }
    }

    constructor(mContext: Context, list: ArrayList<ApkBean>):super(){
        this.mContext = mContext
        this.list = list
        DownLoadMangerUtils.getInstance().setDownLoadApkListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val contentView = View.inflate(mContext,R.layout.item_application, null)

        return MyHolder(contentView)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.tvName.text = list!![position].name
        holder.tv_selected.tag= position

        holder.mProgressBar.progress = list!![position].progress
        holder.tv_selected.setOnClickListener {

            var tag = it.tag.toString().toInt()
            DownLoadMangerUtils.getInstance().downLoad(list!![tag].name,list!![tag].name,list!![tag].url)
        }
    }

    override fun call(url: String, state: Int, progress: Int) {

        for ((index,bean) in list!!.withIndex()){
            if (bean.url == url){
                bean.progress = progress
                notifyItemChanged(index)
            }
        }

    }


}