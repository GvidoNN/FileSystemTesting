package my.lovely.filesystemtesting.domain.usecase

import android.util.Log
import my.lovely.filesystemtesting.R
import my.lovely.filesystemtesting.domain.model.FileModel
import my.lovely.filesystemtesting.domain.repository.FilesRepository
import javax.inject.Inject

class GetFilesUseCase @Inject constructor(private val filesRepository: FilesRepository) {

    suspend fun getMainFiles(path: String, typeSorted: Int): List<FileModel> {
        var startList = filesRepository.getMainFiles(path = path)
        val listWithIcons = setIcons(startList)
        val sortedList = sort(listWithIcons,typeSorted)
        val result = sortedList
        return result
    }

    private fun sort(array: List<FileModel>, typeSort: Int): List<FileModel>{
        var resultArray = listOf<FileModel>()
        when(typeSort){
            1 -> resultArray = array.sortedBy { it.name }
            2 -> resultArray = array.sortedBy { it.size }
            3 -> resultArray = array.sortedBy { it.changeDate }
            4 -> resultArray = array.sortedBy { it.extension }
        }
        return resultArray
    }

    private fun setIcons(array: List<FileModel>): List<FileModel>{
        for(i in array){
            when {
                i.name.endsWith(".txt") -> {
                    i.type = "txt"
                    i.image = R.drawable.txt
                }
                i.name.endsWith(".pdf") -> {
                    i.type = "pdf"
                    i.image = R.drawable.pdf
                }
                i.name.endsWith(".png") or i.name.endsWith(".PNG") -> {
                    i.type = "png"
                    i.image = R.drawable.png
                }
                i.name.endsWith(".jpg") or i.name.endsWith(".jpeg") or i.name.endsWith(".JPG") -> {
                    i.type = "jpg"
                    i.image = R.drawable.jpeg
                }
                i.name.endsWith(".doc") or  i.name.endsWith(".docx") -> {
                    i.type = "doc"
                    i.image = R.drawable.doc
                }
                i.name.endsWith(".mov") or  i.name.endsWith(".mp4") -> {
                    i.type = "mp4"
                    i.image = R.drawable.mp4
                }
                i.name.endsWith(".mp3")-> {
                    i.type = "mp3"
                    i.image = R.drawable.mp3
                }
                i.type == "directory" -> {
                    i.type = "directory"
                    i.image = R.drawable.directory
                }
                else -> {
                    i.type = "none"
                    i.image = R.drawable.file
                }

            }
        }
        return array
    }

}