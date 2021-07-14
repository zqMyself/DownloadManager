package cn.lanru.lrapplication.module.utils.manger

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.FileProvider
import cn.hc.downloadmanger.MyApplication
import cn.hc.downloadmanger.SPUtils
import cn.hc.downloadmanger.manger.DownLoadApkListener
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


/**
 * @ClassName: DownLoadMangerUtils
 * @Description: java类作用描述
 * @Author: zengqiang
 * @github: https://github.com/zqMyself
 * @Date: 2021-07-09 14:44
 */
class DownloadManagerUtils {

    var downloadManager :DownloadManager?=null
    var id = 0L
    var url = ""
    var mHandler = Handler(Looper.getMainLooper())
    var timer : Timer?=null
    companion object
    {
        var listenerList = ArrayList<DownLoadApkListener>()
        fun setDownLoadApkListener(downLoadApkListener:DownLoadApkListener){
            listenerList.add(downLoadApkListener)
        }

        fun removeDownLoadApkListener(downLoadApkListener:DownLoadApkListener){
            listenerList.remove(downLoadApkListener)
        }
    }


    fun  notifyListener(state:Int,progress:Int){

        mHandler.post {
            var iterator = listenerList.iterator()
            while (iterator.hasNext()) {
                var listener = iterator.next()
                listener.call(url, state, progress)
            }
        }

    }

    fun downLoad(title:String,description:String,downLoadHtmlUrl:String){
        this.url = downLoadHtmlUrl
        if (SPUtils.getInstance().contains(url)){
            return
        }
        SPUtils.getInstance().put(url,url)
        var uri = Uri.parse(downLoadHtmlUrl)
        val request = DownloadManager.Request(uri)
        //设置漫游条件下是否可以下载
        request.setAllowedOverRoaming(false)
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        //设置通知标题
        request.setTitle(title)
        //设置通知标题message
        request.setDescription(description)
        request.setVisibleInDownloadsUi(true)
        request.allowScanningByMediaScanner()
        val index = downLoadHtmlUrl.lastIndexOf("/")
        val apkName: String = downLoadHtmlUrl.substring(index + 1, downLoadHtmlUrl.length)
        //设置文件存放路径
        val directory = getDiskCacheDir(MyApplication.context!!)
        val file = File(directory, "/download/$apkName")
        request.setDestinationUri(Uri.fromFile(file))
        if (downloadManager == null)
            downloadManager = MyApplication.context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        if (downloadManager != null) {
            var id = downloadManager!!.enqueue(request)
            this.id = id
            startTimer()
        }

    }


    private fun startTimer(){
        if (timer == null) {
            timer= Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    checkStatus(id)
                }
            }, 1000, 1000)
        }
    }

    fun getDiskCacheDir(context: Context): String? {
        var cachePath: String? = null
        cachePath =
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                context.externalCacheDir!!.path
            } else {
                context.cacheDir.path
            }
        return cachePath
    }
    private fun checkStatus(id:Long) {
        val query = DownloadManager.Query()

        //通过下载的id查找
        query.setFilterById(id)
        var cursor: Cursor?=null
        cursor = downloadManager!!.query(query)
        if (cursor.moveToFirst()) {
            val status: Int = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

            when (status) {
                DownloadManager.STATUS_PAUSED -> {
                    Log.e("tag","STATUS_PAUSED +" + DownloadManager.STATUS_PAUSED)
                }
                DownloadManager.STATUS_PENDING -> {
                    Log.e("tag","STATUS_PENDING +" + DownloadManager.STATUS_PENDING)
//                    checkStatus(id)

//                    notifyListener(id, DownloadManager.STATUS_PENDING, 0)

                }
                DownloadManager.STATUS_RUNNING -> {


//                    Log.e("tag","STATUS_RUNNING +" + DownloadManager.STATUS_RUNNING)
                    var bytesAndStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    var totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    var  dl_progress = ((bytesAndStatus * 100L) / totalSize)
                    notifyListener(DownloadManager.STATUS_RUNNING, dl_progress.toInt())
                    Log.e("tag","STATUS_PENDING  bytesAndStatus = $dl_progress")
                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    notifyListener( DownloadManager.STATUS_SUCCESSFUL, 100)
                    var name = ""
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        var fileUriIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                        var fileUri = cursor.getString(fileUriIdx);
                        if (fileUri != null) {
                            name = Uri.parse(fileUri).getPath()!!
                        }
                    } else {
                        name = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME))
                    }
                    if (timer !=null)
                        timer!!.cancel()
                    SPUtils.getInstance().remove(url)
                    //下载完成
                    cursor.close()
                    Log.e("tag","STATUS_SUCCESSFUL +" + DownloadManager.STATUS_SUCCESSFUL +",id="+id)
                    installApk(File(name))
                }
                DownloadManager.STATUS_FAILED -> {
                    Log.e("tag","STATUS_FAILED +" + DownloadManager.STATUS_FAILED)

                    cursor.close()
                }

            }
        }
    }

    private fun installApk(file:File) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true) //表明不是未知来源
            val uri = FileProvider.getUriForFile(MyApplication.context!!, MyApplication.context!!.packageName + ".fileprovider", file)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            MyApplication.context!!.startActivity(intent)

        }else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
            intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true) //表明不是未知来源
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            MyApplication.context!!.startActivity(intent)
        }
    }

}