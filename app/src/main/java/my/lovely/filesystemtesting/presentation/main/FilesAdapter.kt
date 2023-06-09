package my.lovely.filesystemtesting.presentation.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.domain.model.FileModel

class FilesAdapter() : RecyclerView.Adapter<FilesAdapter.FilesViewHolder>() {

    private lateinit var context: Context
    private lateinit var clickListener: OnItemClickListener
    private lateinit var shareListener: OnItemClickListener

    var filesList = mutableListOf<FileModel>()

    fun setFileList(file: List<FileModel>) {
        this.filesList = file.toMutableList()
        notifyDataSetChanged()
    }

    class FilesViewHolder(itemView: View, clickListener: OnItemClickListener, shareListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
        val tvFileSize: TextView = itemView.findViewById(R.id.tvFileSize)
        val imFile: ImageView = itemView.findViewById(R.id.imFile)
        val tvFileDate: TextView = itemView.findViewById(R.id.tvFileChangeDate)
        val btFileShare: ImageButton = itemView.findViewById(R.id.btShare)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
            btFileShare.setOnClickListener{
                shareListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.file_item, parent, false)
        return FilesViewHolder(view, clickListener, shareListener)
    }

    override fun getItemCount(): Int {
        return filesList.size
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val fileData = filesList[position]
        if(fileData.name.length > 20){
            holder.tvFileName.text = fileData.name.substring(0,19) + "..."
        } else holder.tvFileName.text = fileData.name

        if(fileData.type == "directory"){
            holder.tvFileSize.isVisible = false
            holder.btFileShare.isVisible = false
        }else {
            holder.tvFileSize.text = "${fileData.size} bytes"
        }
        holder.tvFileDate.text = fileData.changeDate
        holder.imFile.setImageResource(fileData.image
        )
    }


    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnDirectoryClickListener(listener: OnItemClickListener){
        clickListener = listener
    }

    fun setOnShareFileListener(listener: OnItemClickListener){
        shareListener = listener
    }

}