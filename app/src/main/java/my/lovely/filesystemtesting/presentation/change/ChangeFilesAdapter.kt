package my.lovely.filesystemtesting.presentation.change

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import my.lovely.filesystemtesting.Const
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.domain.model.FileModel

class ChangeFilesAdapter() : RecyclerView.Adapter<ChangeFilesAdapter.FilesViewHolder>() {

    private lateinit var context: Context

    var changeFilesList = mutableListOf<FileModel>()

    fun setFileList(file: List<FileModel>) {
        this.changeFilesList = file.toMutableList()
        notifyDataSetChanged()
    }

    class FilesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
        val tvFileSize: TextView = itemView.findViewById(R.id.tvFileSize)
        val imFile: ImageView = itemView.findViewById(R.id.imFile)
        val tvFileDate: TextView = itemView.findViewById(R.id.tvFileChangeDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.file_change_item, parent, false)
        return FilesViewHolder(view)

    }

    override fun getItemCount(): Int {
        return changeFilesList.size
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val fileData = changeFilesList[position]
        if(fileData.name.length > 20){
            holder.tvFileName.text = fileData.name.substring(0,19) + "..."
        } else holder.tvFileName.text = fileData.name

        if(fileData.type == Const.DIRECTORY_TYPE){
            holder.tvFileSize.isVisible = false
        }else {
            holder.tvFileSize.text = "${fileData.size} bytes"
        }
        holder.tvFileDate.text = fileData.changeDate
        holder.imFile.setImageResource(fileData.image)
    }

}