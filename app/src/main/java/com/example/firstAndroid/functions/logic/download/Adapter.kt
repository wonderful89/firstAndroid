package com.example.firstAndroid.functions.logic.download

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstAndroid.R
import com.example.firstAndroid.base.utils.runOnUiThread
import com.example.firstAndroid.functions.logic.download.downloader.DownloadManager
import com.example.firstAndroid.functions.logic.download.downloader.Downloader
import com.example.firstAndroid.functions.logic.download.downloader.Observer

class DownloadTaskViewHolder(itemView: View, var task: Downloader? = null) :
    RecyclerView.ViewHolder(itemView) {

}

class DownloadTaskAdapter(
    private val taskList: List<Downloader>,
    private val itemClick: (position: Int, item: Downloader) -> Unit
) : RecyclerView.Adapter<DownloadTaskViewHolder>(), Observer  { //
    companion object {
        const val tag = "DownloadTaskAdapter"
    }

    init {
        Log.i(tag, "init ===")
    }

    private var holderList: MutableList<DownloadTaskViewHolder> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadTaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_download_task_item, parent, false)
        val holder = DownloadTaskViewHolder(view, null)
        holderList.add(holder)
        return holder
    }

    override fun onBindViewHolder(holder: DownloadTaskViewHolder, position: Int) {
        Log.i(tag, "onBindViewHolder")
        val item = taskList[position]
        holder.task = item

        holder.itemView.findViewById<TextView>(R.id.num_title)?.text = "$position"
        holder.itemView.findViewById<TextView>(R.id.description)?.text = "${item.taskId}"
        holder.itemView.findViewById<TextView>(R.id.status)?.text = "ss--"
        DownloadManager.subjectTask(item.taskId, this)

        holder.itemView.findViewById<TextView>(R.id.start)?.setOnClickListener {
            DownloadManager.start(item.taskId)
        }

        holder.itemView.findViewById<TextView>(R.id.pause)?.setOnClickListener {
            DownloadManager.pause(item.taskId)
        }
    }

    override fun onViewRecycled(holder: DownloadTaskViewHolder) {
        super.onViewRecycled(holder)
        Log.i(tag, "onViewRecycled")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: DownloadTaskViewHolder) {
        Log.i(tag, "onViewAttachedToWindow")
        super.onViewAttachedToWindow(holder)
    }


    override fun onViewDetachedFromWindow(holder: DownloadTaskViewHolder) {
        Log.i(tag, "onViewDetachedFromWindow")
        super.onViewDetachedFromWindow(holder)
        taskList.forEach {
            DownloadManager.removeTaskObserver(it.taskId, this)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onCreate(taskId: String?) {
        Log.i(tag, "onCreate")
    }

    @SuppressLint("SetTextI18n")
    override fun onReady(taskId: String?) {
        Log.i(tag, "onReady")
        runOnUiThread {
            val holder = holderList.firstOrNull { it.task?.taskId == taskId}
            holder?.itemView?.findViewById<TextView>(R.id.status)?.text = "ready"
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onLoading(
        taskId: String?,
        speed: String?,
        totalSize: Long,
        loadedSize: Long
    ) {
        Log.i(tag, "onLoading, totalSize = $totalSize, loadedSize = $loadedSize")
        val item = taskList.firstOrNull { it.taskId == taskId }
        val holder = holderList.firstOrNull { it.task?.taskId == taskId}
        runOnUiThread {
            val progress = (loadedSize / totalSize * 100).toInt()
            holder?.itemView?.findViewById<TextView>(R.id.progress)?.text = "${progress}%"
        }
    }

    override fun onPause(taskId: String?, totalSize: Long, loadedSize: Long) {
        Log.i(tag, "onPause")
        runOnUiThread {
            val holder = holderList.firstOrNull { it.task?.taskId == taskId}
            val progress = (loadedSize / totalSize * 100).toInt()
            holder?.itemView?.findViewById<TextView>(R.id.status)?.text = "onPause: ${progress}%"
        }
    }

    override fun onFinish(taskId: String?) {
        Log.i(tag, "onFinish")
        runOnUiThread {
            val holder = holderList.firstOrNull { it.task?.taskId == taskId}
            holder?.itemView?.findViewById<TextView>(R.id.progress)?.text = "100%"
        }
    }

    override fun onError(
        taskId: String?,
        error: String?,
        totalSize: Long,
        loadedSize: Long
    ) {
        Log.i(tag, "onError")
    }
}