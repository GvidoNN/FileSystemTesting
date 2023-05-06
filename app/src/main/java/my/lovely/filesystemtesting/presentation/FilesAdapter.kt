package my.lovely.filesystemtesting.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.domain.model.FileModel

class FilesAdapter() : RecyclerView.Adapter<FilesAdapter.FilesViewHolder>() {

    private lateinit var context: Context
    private lateinit var clickListener: OnItemClickListener

    var filesList = mutableListOf<FileModel>()

    fun setFileList(file: List<FileModel>) {
        this.filesList = file.toMutableList()
        notifyDataSetChanged()
    }
//    , clickListener: OnItemClickListener
    class FilesViewHolder(itemView: View, clickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
        val tvFileSize: TextView = itemView.findViewById(R.id.tvFileSize)
        val imFile: ImageView = itemView.findViewById(R.id.imFile)
        val tvFileDate: TextView = itemView.findViewById(R.id.tvFileChangeDate)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.file_item, parent, false)
        return FilesViewHolder(view, clickListener)
//        , clickListener

    }

    override fun getItemCount(): Int {
        return filesList.size
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val fileData = filesList[position]
        holder.tvFileName.text = fileData.name
        holder.tvFileSize.text = fileData.size
        holder.tvFileDate.text = fileData.changeDate
        holder.imFile.setImageResource(fileData.image)
//        try {
//            val url = "https:" + bookData.edition.coverUrl
//            Glide.with(holder.itemView).load(url).into(holder.imCoverBook)
//        } catch (e: Exception) {
//            holder.imCoverBook.setImageResource(R.drawable.nocover)
//        }
//        try {
//            holder.tvTextName.text = bookData.edition.title
//        } catch (e: Exception) {
//            holder.tvTextName.text = context.getString(R.string.nan_title)
//        }
//        try {
//            holder.tvAuthorName.text = bookData.edition.authors[0].name
//        } catch (e: java.lang.Exception) {
//            holder.tvAuthorName.text = context.getString(R.string.nan_author)
//        }
//        var textSub = bookData.highlight.text[0]
//        holder.tvSubject.text = editSubjectText(textSub)
//        val isExpandable: Boolean = bookData.isExpandable
//        holder.tvSubject.isVisible = isExpandable
//        holder.imAddToFavourite.isVisible = !isExpandable
//
//        holder.constraintLayout.setOnClickListener {
//            isAnyItemExpanded(position)
//            bookData.isExpandable = !bookData.isExpandable
//            notifyItemChanged(position)
//        }
    }

//    private fun isAnyItemExpanded(position: Int) {
//        val temp = searchInsideList.indexOfFirst {
//            it.isExpandable
//        }
//        if (temp >= 0 && temp != position) {
//            searchInsideList[temp].isExpandable = false
//            notifyItemChanged(temp, 0)
//        }
//
//    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnDirectoryClickListener(listener: OnItemClickListener){
        clickListener = listener
    }
//
//    private fun editSubjectText(text: String): String{
//        val newtext = text.replace("{{{","| ").replace("}}}"," |")
//        return newtext
//    }

}